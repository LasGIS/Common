package net.codejava.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;

public class Many2ManyTester {

    public static Session getCurrentSessionFromConfig() {
        // SessionFactory in Hibernate 5 example
        final Configuration config = new Configuration();
        config.configure();
        // local SessionFactory bean created
        final SessionFactory sessionFactory = config.buildSessionFactory();
        final Session session = sessionFactory.getCurrentSession();
        return session;
    }

    public static void main(String[] args) {
        // loads configuration and mappings
        final Configuration configuration = new Configuration().configure();

        // builds a session factory from the service registry
        final SessionFactory sessionFactory = configuration.buildSessionFactory();

        // obtains the session
        final Session session = sessionFactory.openSession();
        session.beginTransaction();

//        testSaveAllNew(session);
		testSaveWithExistingUserAndGroup(session);
//		testDeleteUserGroup(session);

        session.getTransaction().commit();
        session.close();
    }

    private static void testSaveAllNew(Session session) {
        User user = new User("tommy", "ymmot", "tommy@gmail.com");
        Group group = new Group("Coders");

        UserGroup userGroup = new UserGroup();
        userGroup.setGroup(group);
        userGroup.setUser(user);
        userGroup.setActivated(true);
        userGroup.setRegisteredDate(new Date());

        session.save(userGroup);
    }

    private static void testSaveWithExistingUserAndGroup(Session session) {
        // this user is obtained from the database with ID 34
        final User user = session.get(User.class, 7L);

        // this group is obtained from the database with ID 17
        final Group group = session.get(Group.class, 6L);

        UserGroup userGroup = new UserGroup();
        userGroup.setGroup(group);
        userGroup.setUser(user);
        userGroup.setActivated(true);
        userGroup.setRegisteredDate(new Date());

        session.save(userGroup);
    }

    private static void testDeleteUserGroup(Session session) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(3);
        session.delete(userGroup);
    }
}
