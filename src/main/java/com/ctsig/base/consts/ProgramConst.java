package com.ctsig.base.consts;

/**
 * 常量类
 *
 * @author wangan
 * @date 2018/3/21
 */
public class ProgramConst {

    /**
     * redis相关常量
     */
    public static final String NX = "NX";
    public static final String EX = "EX";

    /**
     * json或者xml post请求
     */
    public static final int JSON = 1;
    public static final int XML = 2;


    /**
     * 响应超时时间
     */
    public static final int TIME_OUT = 2000;
    /**
     * 连接超时时间
     */
    public static final int CONNECT_TIME_OUT = 1000;

    /**
     * 请求重试次数
     */
    public static final int EXECUTION_NUM = 2;

    /**
     * 最大连接数
     */
    public static final int MAX_TOTAL = 200;

    /**
     * 每个路由最大连接数
     */
    public static final int DEFAULT_MAX_PRR_ROUTE = 100;


}
