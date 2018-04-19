package com.ctsig.order.domain;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;


/**
 * 订单
 *
 * @author wangan
 * @date 2018/1/4
 */
@Entity(name = "o_order")
@Data
@DynamicUpdate
public class Order implements Serializable {

    private static final long serialVersionUID = 6650874080995293158L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 商品总金额 double(14,2)
     */
    private Double productSumAmount;

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

    public Order() {
    }

    public Order(Long id, Long userId, Double productSumAmount, Double productSumDiscountAmount,
                 Double extraAmount, Double realPaymentAmount, String extraAmountType,
                 String payType, String orderStatus, String createTime, String remark) {
        this.id = id;
        this.userId = userId;
        this.productSumAmount = productSumAmount;
        this.productSumDiscountAmount = productSumDiscountAmount;
        this.extraAmount = extraAmount;
        this.realPaymentAmount = realPaymentAmount;
        this.extraAmountType = extraAmountType;
        this.payType = payType;
        this.orderStatus = orderStatus;
        this.createTime = createTime;
        this.remark = remark;
    }
}
