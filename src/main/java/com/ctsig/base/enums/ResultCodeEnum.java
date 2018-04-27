package com.ctsig.base.enums;

import lombok.Getter;


/**
 * 返回格式代码
 *
 * @author wangan
 * @date 2018/1/4
 */
@Getter
public enum ResultCodeEnum {


    Success(200, "成功"),
    ParamError(504025, "参数错误"),
    OrderUpdateError(504026, "订单修改失败"),
    OrderNotExist(504027, "订单不存在"),
    SecurityAuthOK(200001, "验证通过"),
    ArithmeticException(504001, "系统异常：计算错误"),
    IspUnknownErr(200002, "服务暂不可用"),
    CodeErrorService(500, "业务逻辑错误"),
    QueryIdNotEmpty(503005, "查询id不能为空"),
    ObjectNotEmpty(503009, "对象参数不能为空"),
    HttpConnectTimeOut(503010, "http请求连接超时"),

    UriBuilderException(503011, "构建URI异常"),
    RedisException(504018, "redis 异常"),
    SystemException(504, "系统异常"),
    NullPointerException(504002, "输入错误，缺少输入值"),
    ClassCastException(504003, "系统异常：类型转换错误"),
    ArrayIndexOutOfBoundsException(504005, "系统异常：集合超出范围"),
    SQLException(504008, "系统异常：数据库异常"),
    OtherException(504999, "系统异常：未知异常"),
    MyBatisSystemException(504012, "数据库异常：mybatis操作数据库异常"),
    IOException(504009, "系统异常：文件读写错误"),
    NumberFormatException(504007, "系统异常：输入数字错误"),;

    private Integer code;
    private String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
