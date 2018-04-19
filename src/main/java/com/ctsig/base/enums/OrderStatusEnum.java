package com.ctsig.base.enums;

import lombok.Getter;

/**
 * @author wangan
 * @date 2017/1/4
 */
@Getter
public enum OrderStatusEnum {

    NEW("0", "新订单"),
    FINISHED("1", "完结"),
    CANCEL("2", "已取消"),;

    private String code;
    private String message;

    OrderStatusEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
