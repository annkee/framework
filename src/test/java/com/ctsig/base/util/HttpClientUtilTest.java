package com.ctsig.base.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

/**
 * @author wangan
 * @date 2018/4/19
 */
public class HttpClientUtilTest {

    @Test
    public void get() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(2000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(2000).build();
        httpGet.setConfig(requestConfig);
        long a = System.currentTimeMillis();
        CloseableHttpResponse response = httpclient.execute(httpGet);
        System.out.println(System.currentTimeMillis() - a);
        System.out.println("得到的结果:" + response.getStatusLine());//得到请求结果
        HttpEntity entity = response.getEntity();//得到请求回来的数据

    }


    @Test
    public void getMy() throws Exception {
        String s = HttpClientUtil.get("http://192.168.8.2/webpages/login.html");
    }
}