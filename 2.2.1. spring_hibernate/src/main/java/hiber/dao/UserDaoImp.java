package hiber.dao;

import hiber.model.User;
import hiber.model.Car;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Transactional
   @Override
   public User findByCar(String model, int series) {
      String hql = "FROM User u JOIN FETCH u.car WHERE u.car.series = :series AND u.car.model = :model";
      Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("series", series);
      query.setParameter("model", model);
      try {
         return query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      }
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

}
