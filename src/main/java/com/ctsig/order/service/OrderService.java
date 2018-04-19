package com.ctsig.order.service;


import com.ctsig.order.domain.Order;
import com.ctsig.order.dto.OrderDTO;

import java.util.List;

/**
 * 订单业务层接口
 *
 * @author wangan
 * @date 2018/1/4
 */
public interface OrderService {

    /**
     * 创建订单
     *
     * @param orderDTO order模型
     * @return OrderDTO
     */
    OrderDTO createOrder(OrderDTO orderDTO);

    /**
     * 订单状态更新
     *
     * @param orderId 订单id
     * @param status  订单状态
     * @return Order
     */
    Order update(Long orderId, String status);

    /**
     * 查询订单信息
     *
     * @param orderId
     * @return OrderDTO
     */
    OrderDTO findOrder(Long orderId);

    /**
     * 查询订单列表
     *
     * @param status
     * @param page
     * @param size
     * @return List<OrderDTO>
     */
    List<OrderDTO> findList(String status, Integer page, Integer size);
}
