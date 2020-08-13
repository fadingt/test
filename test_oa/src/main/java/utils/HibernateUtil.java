package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
//		cfg.configure("/hibernate_mysql_200123_paasAom.cfg.xml");
        cfg.configure("/hibernate_mysql_200123_ITDMGT.cfg.xml");
        sessionFactory = cfg.buildSessionFactory();
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void updateObject(Object obj) {
		try (Session session = HibernateUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		}
    }

    public static void deleteObject(Object obj) {
		try (Session session = HibernateUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		}
    }

    public static void addObject(Object obj) {
		try (Session session = HibernateUtil.getSession()) {
			Transaction tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
		}
    }

    public static <T> T getObjectById(Class<T> entityType, Serializable id) {
        T t;
		try (Session session = HibernateUtil.getSession()) {
			t = session.get(entityType, id);
			return t;
		}
    }

    public static <T> List<T> getObjectByProperty(Map<String, Object> property, Class<T> clazz) {
		try (Session session = HibernateUtil.getSession()) {
			StringBuilder sql = new StringBuilder("from " + clazz.getSimpleName() + " as model where 1=1");
			Set<String> keySet = property.keySet();
			for (String key : keySet) {
				sql.append(" and ").append(key).append(" = :").append(key);
			}
			Query<T> query = session.createQuery(sql.toString(), clazz);
			for (String key : keySet) {
				Object value = property.get(key);
				query.setParameter(key, value);
			}
			return query.list();
		}
    }

    public static Long getTableCount(Class<?> clazz) {
		try (Session session = HibernateUtil.getSession()) {
			return (Long) session.createQuery("select count(*) from " + clazz.getSimpleName()).uniqueResult();
		}
    }

}