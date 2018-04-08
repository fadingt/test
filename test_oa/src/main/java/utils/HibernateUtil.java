package utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import domain.User;

public final class HibernateUtil {
	private static SessionFactory sessionFactory;
	static {
		Configuration cfg = new Configuration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();
	}

	public static Session getSession() {
		return sessionFactory.openSession();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void updateObject(Object obj) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public static void deleteObject(Object obj) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public static void addObject(Object obj) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Transaction tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
		} finally {
			if (session != null)
				session.close();
		}
	}

	public static <T> T getObjectById(Class<T> entityType, Serializable id) {
		T t = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			t = session.get(entityType, id);
			return t;
		} finally {
			if (session != null)
				session.close();
		}
	}

	public static <T> List<T> getObjectByProperty(Map<String, Object> property, Class<T> clazz) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			String sql = "from " + clazz.getSimpleName() + " as model where 1=1";
			Set<String> keySet = property.keySet();
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				sql = sql + " and " + key + " = :" + key;
			}
			// System.out.println(sql);
			Query<T> query = session.createQuery(sql, clazz);
			for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
				String key = iterator.next();
				Object value = property.get(key);
				query.setParameter(key, value);
			}

			List<T> list = query.list();
			return list;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public static Long getTableCount(Class<?> clazz) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			return (Long) session.createQuery("select count(*) from " + clazz.getSimpleName()).uniqueResult();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	/**
	 * unit test
	 */
	public static void main(String[] args) {

		Map<String, Object> tmap = new HashMap<>();
		// tmap.put("username", "王宇晖-A0002");
		// tmap.put("usercode", "A0002");
		tmap.put("state", new BigDecimal(1));
		List<User> list = HibernateUtil.getObjectByProperty(tmap, User.class);
		for (User user : list) {
			System.out.println(user.toString());
		}
	}

}