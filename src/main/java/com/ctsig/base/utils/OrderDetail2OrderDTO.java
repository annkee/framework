package com.ctsig.base.utils;

import com.ctsig.order.domain.OrderDetail;
import com.ctsig.order.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 数据格式处理
 *
 * @author wangan
 * @date 2018/1/5
 */
public class OrderDetail2OrderDTO {

    public static OrderDTO convert(OrderDetail orderDetail) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderDetail, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderDetail> orderDetailList) {

        return orderDetailList.stream().map(e -> convert(e)).collect(Collectors.toList());
    }
}
