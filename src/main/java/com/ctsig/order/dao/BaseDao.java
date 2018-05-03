package com.ctsig.order.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 封装dao
 *
 * @author wangan
 * @date 2018/5/3
 */
public interface BaseDao<T, ID extends Serializable> {

    /**
     * 保存数据对象
     *
     * @param entity
     * @return boolean
     */
    boolean save(T entity);

    /**
     * 根据id查询
     *
     * @param id
     * @param t
     * @return
     */
    T findById(T t, Long id);

    /**
     * 根据表名，字段，参数查询，拼接sql语句
     *
     * @param tableName 表名
     * @param field     字段名
     * @param o         字段参数
     * @return List<T>
     */
    List<T> findBySql(String tableName, String field, Object o);

    /**
     * 根据表名，字段，参数查询，拼接sql语句
     *
     * @param tableName 表名
     * @param field     字段名
     * @param o         字段参数
     * @return Object
     */
    Object findObjectBySql(String tableName, String field, Object o);

    /**
     * 多个字段的查询
     *
     * @param tableName 表名
     * @param map       将你的字段传入map中
     * @return List<T>
     */
    List<T> findByMoreField(String tableName, LinkedHashMap<String, Object> map);

    /**
     * 多字段查询分页
     *
     * @param tableName 表名
     * @param map       以map存储key,value
     * @param page      第几页
     * @param size      一个页面的条数
     * @return
     */
    List<T> findPagesByMoreField(String tableName, LinkedHashMap<String, Object> map, int page, int size);

    /**
     * 一个字段的分页
     *
     * @param tableName 表名
     * @param field     字段名
     * @param o         字段参数
     * @param page      第几页
     * @param size      一个页面多少条数据
     * @return List<T>
     */
    List<T> findPages(String tableName, String field, Object o, int page, int size);

    /**
     * 根据表的id删除数据
     *
     * @param entity
     */
    boolean delete(T entity);

    /**
     * 更新对象
     *
     * @param entity
     * @return boolean
     */
    boolean update(T entity);

    /**
     * 根据传入的map遍历key,value拼接字符串，以id为条件更新
     *
     * @param tableName 表名
     * @param map       传入参数放入map中
     * @return Integer
     */
    Integer updateMoreField(String tableName, LinkedHashMap<String, Object> map);


    /**
     * 根据条件查询总条数
     *
     * @param tableName 表名
     * @param map       传入参数放入map中
     * @return Object
     */
    Object findCount(String tableName, LinkedHashMap<String, Object> map);
}
