package utils;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
	private static SessionFactory sf;
	static {
		Configuration cfg = new Configuration();
		cfg.configure();
		sf = cfg.buildSessionFactory();
	}

	public static Session getSession() {
		return sf.openSession();
	}

	public static SessionFactory getSessionFactory() {
		return sf;
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
		System.out.println("adduser");
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
		T T = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			T = session.get(entityType, id);
			return T;
		} finally {
			if (session != null)
				session.close();
		}
	}

}
