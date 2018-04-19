package com.ctsig.order.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 订单详情
 *
 * @author wangan
 * @date 2018/1/4
 */
@Data
@Entity(name = "o_order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 4861608412478292785L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品金额 double(14,2)
     */
    private Double productAmount;

    /**
     * 商品优惠金额 double(14,2)
     */
    private Double productDiscountAmount;

    /**
     * 商品实际付款金额 double(14,2)
     */
    private Double productPaymentAmount;

    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 实施地址
     */
    private String implementAddress;
}
