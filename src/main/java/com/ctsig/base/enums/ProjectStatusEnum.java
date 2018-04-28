package com.ctsig.base.enums;

import lombok.Getter;

/**
 * @author wangan
 * @date 2018/4/27
 */
@Getter
public enum ProjectStatusEnum {

    /**
     * 订单
     */
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),

    /**
     * 地址
     */
    USED(0, "已占用"),
    NOT_USED(1, "未占用"),;


    private Integer code;
    private String message;

    ProjectStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
