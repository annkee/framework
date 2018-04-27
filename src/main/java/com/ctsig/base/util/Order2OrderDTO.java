package com.ctsig.base.util;

import com.ctsig.order.domain.Order;
import com.ctsig.order.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 数据格式处理
 *
 * @author wangan
 * @date 2018/1/8
 */
public class Order2OrderDTO {

    public static OrderDTO convert(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(order, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<Order> orderList) {

        return orderList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
