package com.ctsig.order.service.impl;

import com.ctsig.base.enums.ResultCodeEnum;
import com.ctsig.base.exception.BaseException;
import com.ctsig.base.util.KeyUtil;
import com.ctsig.base.util.Order2OrderDTO;
import com.ctsig.order.dao.OrderDao;
import com.ctsig.order.dao.OrderDetailDao;
import com.ctsig.order.domain.Order;
import com.ctsig.order.domain.OrderDetail;
import com.ctsig.order.dto.OrderDTO;
import com.ctsig.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 订单业务逻辑实现
 *
 * @author wangan
 * @date 2018/1/4
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public OrderDTO createOrder(OrderDTO orderDTO) {

        double orderAmount = 0.00;
        double discountAmount = 0.00;
        Long orderId = KeyUtil.genUniqueKey();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {

            //1.订单详情入库
            orderDetail.setId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailDao.save(orderDetail);
            orderAmount += orderDetail.getProductCount() * orderDetail.getProductAmount();
            discountAmount += orderDetail.getProductDiscountAmount();
        }

        //2.订单入库
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setId(orderId);
        order.setProductSumAmount(orderAmount);
        order.setExtraAmount(0.00);
        order.setExtraAmountType("0");
        order.setProductSumDiscountAmount(discountAmount);
        orderDao.save(order);

        return orderDTO;
    }

    @Override
    public List<OrderDTO> findList(String status, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");

        Pageable pageable = new PageRequest(page, size, sort);
        Page<Order> orderPage = orderDao.findAll(pageable);
        List<Order> orderList = orderPage.getContent();
        List<OrderDTO> orderDTOList = Order2OrderDTO.convert(orderList);
        for (OrderDTO orderDTO : orderDTOList) {

            Page<OrderDetail> detailPage = orderDetailDao.findByOrderId(orderDTO.getId(), null);
            orderDTO.setOrderDetailList(detailPage.getContent());

        }

        return orderDTOList;
    }

    @Override
    public OrderDTO findOrder(Long orderId) {
        Order order;
        try {

            order = orderDao.findById(orderId);
        } catch (Exception e) {
            log.error("findOrder error: e={}", e.getMessage());
            throw new BaseException(ResultCodeEnum.SQLException);
        }

        Page<OrderDetail> detailPage = orderDetailDao.findByOrderId(orderId, null);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);

        orderDTO.setOrderDetailList(detailPage.getContent());
        return orderDTO;


    }

    /**
     * Transactional 注解异常回滚操作
     *
     * @param orderId 订单id
     * @param status  订单状态
     * @return Order
     */
    @Override
    @Transactional(rollbackFor = BaseException.class)
    public Order update(Long orderId, String status) {

        //1.微服务调用，去查询一下实施状态
        Order order = orderDao.findById(orderId);
        if (order != null) {

            order.setOrderStatus(status);
            // 异常在最里面处理掉，防止往上游走，也有可能会有多个控制器调用此业务，所以在此处就处理免得在多个控制器进行多次处理
            try {
                Order save = orderDao.save(order);
                return save;
            } catch (Exception e) {
                log.error("update error: e={}", e.getMessage());
                throw new BaseException(ResultCodeEnum.SQLException);
            }


        }

        return null;
    }
}
