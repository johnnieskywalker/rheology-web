package database.hibernate.service;


import database.hibernate.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {
    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void register(User user){
        // Acquire session
        Session session = sessionFactory.getCurrentSession();
        // Save employee, saving behavior get done in a transactional manner
        session.save(user);
    }

    @Transactional
    public boolean authenticateUser(String userName, String password) {
        Session session = sessionFactory.getCurrentSession();

        boolean passwordEquals = false;
        String userPassword=null;
            Query query = session.createQuery("select password from User where userName=userName");
            userPassword = (String) query.uniqueResult();
            passwordEquals=userPassword.equals(password);
        return passwordEquals;
    }

}
