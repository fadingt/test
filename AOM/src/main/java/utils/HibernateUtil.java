package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.*;

/**
 * @author fadingt
 * @version 1.0.0
 * @date 11/3/2020 2:36 PM
 */
public final class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        Configuration cfg = new Configuration();
        cfg.configure("/conf/hibernate_mysql_200123_paasAom.cfg.xml");
//        cfg.configure("/conf/hibernate_mysql_200123_ITDMGT.cfg.xml");
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

    // TODO: 11/3/2020 事务失败时的回滚处理 和如何通知失败结果
    public static void deleteObject(Object obj) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.delete(obj);
            tx.commit();
        }
    }

    // TODO: 11/3/2020 事务失败时的回滚处理 和如何通知失败结果
    public static void saveObject(Object obj) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.save(obj);
            tx.commit();
        }
    }

    public static void saveOrUpdateObject(Object obj) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(obj);
            tx.commit();
        }
    }

    public static <T> T getObjectById(Class<T> entityType, Serializable id) {
        try (Session session = HibernateUtil.getSession()) {
            // TODO: 12/2/2020 是否懒加载问题 sesson.get() session.load
            return session.get(entityType, id);
        }
    }

    public static <T> List<T> listObjectByProperty(Map<String, Object> filter, Class<T> clazz) {
        return listObjectByProperty(filter, clazz, 0, Integer.MAX_VALUE);
    }

    public static <T> List<T> listByHql(String hql) {
        try (Session session = HibernateUtil.getSession()) {
            Query<T> query = session.createQuery(hql);
            return query.list();
        }
    }

    /**
     * @param filter conditions when getting results from DataBase, get all if the filter is null
     * @param from   startPosition – position of the first result, numbered from 0
     * @param len    maximum number of results to retrieve
     * @return list results that meets the filter
     */
    // TODO: 11/3/2020 当起始位置from较大时 不要全表扫描 优化逻辑和效率
    public static <T> List<T> listObjectByProperty(Map<String, Object> filter, Class<T> clazz, int from, int len) {
        if (from < 0) {
            from = 0;
        }
        try (Session session = HibernateUtil.getSession()) {
            StringBuilder sql = new StringBuilder("from " + clazz.getSimpleName() + " where 1=1");
            if (filter == null) {
                Query<T> query = session.createQuery(sql.toString(), clazz);
                query.setFirstResult(from);
                query.setMaxResults(len);
                return query.list();
            } else {
                Set<String> keySet = filter.keySet();
                for (String key : keySet) {
                    sql.append(" and ").append(key).append(" = :").append(key);
                }
                Query<T> query = session.createQuery(sql.toString(), clazz);
                for (String key : keySet) {
                    Object value = filter.get(key);
                    query.setParameter(key, value);
                }
                query.setFirstResult(from);
                query.setMaxResults(len);
                return query.list();
            }
        }
    }

    /**
     * 返回表的数据量
     *
     * @param clazz POJO类 用于获取类名拼接HQL查询;如果空则抛出IllegalArgumentException
     * @return 表的数据量;如果没有结果则返回0
     * @see Query#uniqueResult()
     * @see HibernateUtil#countTable(Class, Map)
     */
    public static int countTable(Class<?> clazz) {
        return countTable(clazz, null);
    }

    /**
     * 按filter条件获取表的数据量
     *
     * @param clazz  POJO类,用于获取类名拼接在HQL语句中
     * @param filter 查询条件。条件将被拼接在where语句中,比如key="ID" value=1 则查询id=1的数据; 当filter==null时,返回全表的总条数
     * @return 表的数据量;如果没有结果则返回0
     * @see Query#uniqueResult()
     * @see HibernateUtil#countTable(Class)
     */
    // TODO: 11/3/2020  IllegalArgumentException当POJO类没有映射关系时
    public static int countTable(Class<?> clazz, Map<String, Object> filter) {
        Optional.ofNullable(clazz).orElseThrow(() -> new IllegalArgumentException("Class shouldn't be null"));
        Optional<Integer> result;
        StringBuilder sql = new StringBuilder("select count(*) from ").append(clazz.getSimpleName()).append(" where 1=1");
        Query<?> query;
        if (!Optional.ofNullable(filter).isPresent()) {
            try (Session session = HibernateUtil.getSession()) {
                query = session.createQuery(sql.toString());
                result = Optional.of(((Long) query.uniqueResult()).intValue());
            }
        } else {
            try (Session session = HibernateUtil.getSession()) {
                Set<String> keySet = filter.keySet();
                for (String key : keySet) {
                    sql.append(" and ").append(key).append(" = :").append(key);
                }
                query = session.createQuery(sql.toString());
                for (String key : keySet) {
                    Object value = filter.get(key);
                    query.setParameter(key, value);
                }
                result = Optional.of(((Long) query.uniqueResult()).intValue());
            }
        }
        return result.orElse(0);
    }
}