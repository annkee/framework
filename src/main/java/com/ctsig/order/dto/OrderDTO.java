package com.ctsig.order.dto;

import com.ctsig.order.domain.OrderDetail;
import lombok.Data;

import java.util.List;


/**
 * 订单数据传输对象
 *
 * @author wangan
 * @date 2017/1/4
 */
@Data
public class OrderDTO {

    /**
     * 订单id
     */
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 商品数量
     */
    private Integer productCount;

    /**
     * 服务地址信息
     */
    private String implementAddress;

    /**
     * 订单来源
     */
    private String orderOrigin;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品金额 double(14,2)
     */
    private Double productAmount;

    /**
     * 总优惠金额 double(14,2)
     */
    private Double productSumDiscountAmount;

    /**
     * 额外费用金额 double(14,2)
     */
    private Double extraAmount;

    /**
     * 实付款金额 double(14,2)
     */
    private Double realPaymentAmount;

    /**
     * 额外费用类型 0：快递费 1：服务超时费 2：补差价
     */
    private String extraAmountType;

    /**
     * 支付方式 0:月结算 1：支付宝 2：微信：3：银联 4：积分抵现 5：优惠券抵扣 6：线下
     */
    private String payType;

    /**
     * 订单支付状态 0：待支付  1：已支付 2：取消 3：月结
     */
    private String orderStatus;

    /**
     * 下单时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单详情
     */
    private List<OrderDetail> orderDetailList;
}
