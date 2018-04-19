package com.ctsig.base.enums;

import lombok.Getter;

/**
 * 地址占用情况
 *
 * @author wangan
 * @date 2017/1/5
 */
@Getter
public enum AddressStatusEnum {
    /**
     * 0 已占用
     */
    USED(0, "已占用"),

    /**
     * 1 未占用
     */
    NOT_USED(1, "未占用"),;

    private Integer code;

    private String message;

    AddressStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
