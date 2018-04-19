package com.ctsig.order.dao;

import com.ctsig.order.domain.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 订单详情接口
 *
 * @author wangan
 * @date 2018/1/4
 */
public interface OrderDetailDao extends JpaRepository<OrderDetail, String> {
    /**
     * 分页查询订单详细列表
     *
     * @param orderId  订单id
     * @param pageable 分页插件
     * @return List<OrderDetail>
     */
    Page<OrderDetail> findByOrderId(Long orderId, Pageable pageable);

}
