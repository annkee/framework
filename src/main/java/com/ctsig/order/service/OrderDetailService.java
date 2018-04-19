package com.ctsig.order.service;


import com.ctsig.order.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * 订单详情业务层接口
 *
 * @author wangan
 * @date 2018/1/4
 */
public interface OrderDetailService {

    /**
     * 查询订单详情列表
     *
     * @param orderId  订单id
     * @param pageable 分页插件
     * @return
     */
    Page<OrderDTO> findList(Long orderId, Pageable pageable);
}
