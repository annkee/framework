package com.ctsig.order.dao;


import com.ctsig.order.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 订单数据访问层
 *
 * @author wangan
 * @date 2018/1/4
 */
public interface OrderDao extends JpaRepository<Order, String> {
    /**
     * 通过id查询订单
     *
     * @param id 订单id
     * @return
     */
    Order findById(Long id);

    /**
     * 根据订单状态查询列表，按创建时间倒叙
     *
     * @param pageable 分页插件
     * @return List<Order>
     */
    @Override
    Page<Order> findAll(Pageable pageable);
}
