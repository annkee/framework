package com.ctsig.order.service.impl;

import com.ctsig.base.util.ResultVoUtil;
import com.ctsig.base.vo.ResultVO;
import com.ctsig.order.service.BaseService;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * 基础service 实现层封装
 *
 * @param <T> 实体
 * @author wangan
 * @date 2018/1/16
 */
public class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected JpaRepository<T, ID> jpaRepository;

    @Override
    public ResultVO getEntityById(Object id) {

        //不清楚这个为什么主键会返回Integer，这个selectByPrimaryKey(id),返回的主键属性的type是Integer,
        // 数据库里对应的是bigint(11),如果实体类定义主键是Long则报错：类型转换error
        return ResultVoUtil.success();
    }

    @Override
    public ResultVO addEntity(T entity) {

        return ResultVoUtil.success();
    }

    @Override
    public ResultVO updateEntity(T entity) {
        return ResultVoUtil.success();
    }

    @Override
    public ResultVO deleteEntityById(Object id) {
        return ResultVoUtil.success();
    }

    @Override
    public List<T> selectAll() {
        List<T> all = jpaRepository.findAll();
        return null;
    }

    @Override
    public List<T> selectByExample(Object example) {

        return null;
    }
}
