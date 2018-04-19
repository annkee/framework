package com.ctsig.order.service.impl;

import com.ctsig.base.utils.OrderDetail2OrderDTO;
import com.ctsig.order.dao.OrderDetailDao;
import com.ctsig.order.domain.OrderDetail;
import com.ctsig.order.dto.OrderDTO;
import com.ctsig.order.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 订单详情业务逻辑实现
 *
 * @author wangan
 * @date 2018/1/5
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public Page<OrderDTO> findList(Long orderId, Pageable pageable) {
        Page<OrderDetail> orderDetailPage = orderDetailDao.findByOrderId(orderId, pageable);

        List<OrderDTO> orderDTOList = OrderDetail2OrderDTO.convert(orderDetailPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderDetailPage.getTotalElements());
    }
}
