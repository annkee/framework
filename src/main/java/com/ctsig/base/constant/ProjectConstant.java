package com.ctsig.base.constant;

/**
 * 常量类
 *
 * @author wangan
 * @date 2018/3/21
 */
public class ProjectConstant {

    /**
     * redis
     */
    public static final String NX = "NX";
    public static final String EX = "EX";

    /**
     * json或者xml post请求
     */
    public static final int JSON = 1;
    public static final int XML = 2;

    /**
     * http
     */
    public static final int HTTP_RESPONSE_TIME_OUT = 2000;
    public static final int HTTP_CONNECT_TIME_OUT = 1000;
    public static final int HTTP_CONNECT_RETRY_NUM = 2;
    public static final int HTTP_MAX_CONNECT_NUM = 200;
    public static final int HTTP_ROUTE_MAX_CONNECT_NUM = 100;


}
