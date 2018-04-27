package com.ctsig.base.util;


import java.util.Random;


/**
 * 订单号生成规则
 *
 * @author wangan
 * @date 2017/1/4
 */
public class KeyUtil {

    public static synchronized Long genUniqueKey() {

        Random random = new Random();
        Integer number = random.nextInt(90000) + 10000;

        return Long.valueOf(System.currentTimeMillis() + number);

    }
}
