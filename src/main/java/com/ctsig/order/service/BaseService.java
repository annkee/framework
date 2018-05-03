package com.ctsig.order.service;

import com.ctsig.base.vo.ResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service层接口封装 @Service不能注入到实现类，否则报错
 *
 * @param <T>
 * @author wangan
 * @date 2018/01/16
 */
@Service
public interface BaseService<T> {

    /**
     * 根据Id查询实体
     *
     * @param id id
     * @return ResultVO
     */
    ResultVO getEntityById(final Object id);

    /**
     * 新增实体
     *
     * @param entity 实体
     * @return ResultVO
     */
    ResultVO addEntity(final T entity);

    /**
     * 更新实体
     *
     * @param entity 实体
     * @return ResultVO
     */
    ResultVO updateEntity(final T entity);

    /**
     * 根据Id删除实体
     *
     * @param id id
     * @return ResultVO
     */
    ResultVO deleteEntityById(final Object id);

    /**
     * 查询所有
     *
     * @return List<T>
     */
    List<T> selectAll();

    /**
     * 查询
     *
     * @param example
     * @return List<T>
     */
    List<T> selectByExample(Object example);

}
