package com.ctsig.order.dao.impl;

import com.ctsig.base.enums.ResultCodeEnum;
import com.ctsig.base.exception.BaseException;
import com.ctsig.order.dao.BaseDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * 实现BaseDao
 *
 * @author wangan
 * @date 2018/5/3
 */
@Repository
@Slf4j
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public boolean save(T entity) {

        boolean flag;
        try {
            entityManager.persist(entity);
            flag = true;
        } catch (Exception e) {
            log.error("BaseDaoImpl save error: e={}", e.getMessage());
            throw new BaseException(ResultCodeEnum.SQLException);
        }
        return flag;
    }

    @Override
    public Object findById(Object o, Long id) {
        return entityManager.find(o.getClass(), id);
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public List<T> findBySql(String tableName, String field, Object o) {
        String sql = "from " + tableName + " u WHERE u." + field + "=?";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, o);
        List<T> list = query.getResultList();
        entityManager.close();
        return list;
    }

    @Override
    public Object findObjectBySql(String tableName, String field, Object o) {
        String sql = "from " + tableName + " u WHERE u." + field + "=?";
        log.debug("findObjectBySql sql={}", sql);
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, o);

        entityManager.close();
        return query.getSingleResult();
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public List<T> findByMoreField(String tableName, LinkedHashMap<String, Object> map) {
        String sql = "from " + tableName + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> fieldList = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            fieldList.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);
        log.debug("findByMoreField sql={}", sql);
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < fieldList.size(); i++) {
            query.setParameter(i + 1, map.get(fieldList.get(i)));
        }
        List<T> listRe = query.getResultList();
        entityManager.close();
        return listRe;
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public List<T> findPagesByMoreField(String tableName, LinkedHashMap<String, Object> map, int page, int size) {
        String sql = "from " + tableName + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> fieldList = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            fieldList.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);

        log.debug("findPagesByMoreField sql={}", sql);

        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < fieldList.size(); i++) {
            query.setParameter(i + 1, map.get(fieldList.get(i)));
        }
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        List<T> listRe = query.getResultList();
        entityManager.close();
        return listRe;
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public List<T> findpages(String tablename, String filed, Object o, int start, int pageNumer) {
        String sql = "from " + tablename + " u WHERE u." + filed + "=?";
        System.out.println(sql + "--------page--sql语句-------------");
        List<T> list = new ArrayList<>();
        try {
            Query query = entityManager.createQuery(sql);
            query.setParameter(1, o);
            query.setFirstResult((start - 1) * pageNumer);
            query.setMaxResults(pageNumer);
            list = query.getResultList();
            entityManager.close();
        } catch (Exception e) {
            System.out.println("------------分页错误---------------");
        }

        return list;
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public boolean update(T entity) {
        boolean flag = false;
        try {
            entityManager.merge(entity);
            flag = true;
        } catch (Exception e) {
            System.out.println("---------------更新出错---------------");
        }
        return flag;
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public Integer updateMoreFiled(String tablename, LinkedHashMap<String, Object> map) {
        String sql = "UPDATE " + tablename + " AS u SET ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        for (int i = 0; i < list.size() - 1; i++) {
            if (map.get(list.get(i)).getClass().getTypeName() == "java.lang.String") {
                System.out.println("-*****" + map.get(list.get(i)) + "------------" + list.get(i));
                sql += "u." + list.get(i) + "='" + map.get(list.get(i)) + "' , ";
            } else {
                sql += "u." + list.get(i) + "=" + map.get(list.get(i)) + " , ";
            }
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += "where u.id=? ";
        System.out.println(sql + "--------sql语句-------------");
        int resurlt = 0;
        try {
            Query query = entityManager.createQuery(sql);
            query.setParameter(1, map.get("id"));
            resurlt = query.executeUpdate();
        } catch (Exception e) {
            System.out.println("更新出错-----------------------");
            e.printStackTrace();

        }
        return resurlt;
    }

    @Transactional(rollbackFor = BaseException.class)
    @Override
    public boolean delete(T entity) {
        boolean flag = false;
        try {
            entityManager.remove(entityManager.merge(entity));
            flag = true;
        } catch (Exception e) {
            System.out.println("---------------删除出错---------------");
        }
        return flag;
    }

    @Override
    public Object findCount(String tablename, LinkedHashMap<String, Object> map) {
        String sql = "select count(u) from " + tablename + " u WHERE ";
        Set<String> set = null;
        set = map.keySet();
        List<String> list = new ArrayList<>(set);
        List<Object> filedlist = new ArrayList<>();
        for (String filed : list) {
            sql += "u." + filed + "=? and ";
            filedlist.add(filed);
        }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql + "--------sql语句-------------");
        Query query = entityManager.createQuery(sql);
        for (int i = 0; i < filedlist.size(); i++) {
            query.setParameter(i + 1, map.get(filedlist.get(i)));
        }
        return query.getSingleResult();
    }
}
