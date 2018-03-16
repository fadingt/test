package dao.impl;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import dao.UserDao;
import domain.User;
import utils.HibernateUtil;

@Repository
public class HibernateUserDaoImpl implements UserDao {
	@Override
	public void updateUser(User user) {
		utils.HibernateUtil.updateObject(user);
	}

	@Override
	public void deleteUser(User user) {
		utils.HibernateUtil.deleteObject(user);
	}

	@Override
	public void addUser(User user) {
		utils.HibernateUtil.addObject(user);
	}

	@Override
	public User getUserById(int userid) {
		return utils.HibernateUtil.getObjectById(User.class, userid);
	}

	@Override
	public List<User> getUserByName(String username) {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query<User> query = session.createQuery("from User u where username=:username", User.class);
			query.setParameter("username", username);
			// query.setFirstResult(0);
			// query.setMaxResults(10);
			List<User> users = query.list();
			return users;
		} finally {
			session.close();
		}
	}

	@Override
	public Long getCount() {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			return (Long) session.createQuery("select count(*) from User").uniqueResult();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	/*
	 * 1.拼出sql语句 2.拼出查询条件
	 * 
	 * (non-Javadoc)
	 * 
	 * @see dao.UserDao#getUser(java.lang.String, java.lang.String[])
	 */

	@Override
	public List<User> getUser(String sql, String[] criteria) {
		Session session = null;
		try {
			session = utils.HibernateUtil.getSession();
			Query<User> query = session.createQuery(sql, User.class);
			for (int i = 0; i < criteria.length; i++) {
				query.setParameter(i, criteria[i]);
			}
			List<User> users = query.list();
			return users;
		} finally {
			session.close();
		}
	}
	public static void main(String[] args) {
//		Session session = null;
//		Transaction tx = null;
//		try {
//			session = utils.HibernateUtil.getSession();
//			tx = session.beginTransaction();
//			String sql = "from Unit u";
//			Query query = session.createQuery(sql);
//			List units = query.list();
//			for(Unit unit : units){
//				System.out.println(unit.toString());
//			}
//			tx.commit();
//		} finally {
//			if(session != null){
//				session.close();
//			}
//		}
		test2();
	}

	public static void test2() {
		Session session = null;
		Transaction tx = null;
		try {
			session = utils.HibernateUtil.getSession();
			tx = session.beginTransaction();
//			Unit unit = session.get(Unit.class, "10716");
//			System.out.println(unit.toString());
			User user = session.get(User.class, 31521);
			Hibernate.initialize(user.getUnit());
			System.out.println(user.getUnit().getUnitname());
			System.out.println(user.toString());
			tx.commit();
		} finally {
			if(session != null){
				session.close();
			}
		}
	}

	public static void test1() {
		Session session = null;
		try {
			session = utils.HibernateUtil.getSession();
			String sql = "from User u where u.username = ?";
			Query<User> query = session.createQuery(sql,User.class);
			query.setParameter(0, "刘兴宇-A6853");
			List<User> users = query.list();
			for(User user : users){
				System.out.println(user.toString());
			}
			
		} catch (Exception e) {
			if(session!=null){
				session.close();
			}
		}
	}
}
