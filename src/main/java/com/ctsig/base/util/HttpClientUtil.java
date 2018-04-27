package com.ctsig.base.util;

import com.alibaba.fastjson.JSONObject;
import com.ctsig.base.consts.ProgramConst;
import com.ctsig.base.enums.ResultCodeEnum;
import com.ctsig.base.exception.BaseException;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * http请求工具
 *
 * @author wangan
 * @date 2018/3/15
 */
@Slf4j
public class HttpClientUtil {

    /**
     * 普通get请求
     *
     * @param url 地址
     * @return String
     */
    public static String get(String url) {
        HttpGet httpGet = new HttpGet(url);
        return httpResult(httpGet);
    }


    /**
     * 携带参数get请求
     *
     * @param url
     * @param params
     * @return String
     */
    public static String get(String url, Map<String, Object> params) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(url);
        uriBuilder.setParameters(params2NVPs(params));
        URI build;
        try {
            build = uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("build error: e={}", e.getMessage());
            throw new BaseException(ResultCodeEnum.UriBuilderException);
        }
        HttpGet httpGet = new HttpGet(build);
        return httpResult(httpGet);

    }

    /**
     * 携带头信息，参数的get请求
     *
     * @param url
     * @param headers
     * @param params
     * @return String
     */
    public static String get(String url, Map<String, Object> headers, Map<String, Object> params) {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setPath(url);
        uriBuilder.setParameters(params2NVPs(params));
        URI build;
        try {
            build = uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("build error: e={}", e.getMessage());
            throw new BaseException(ResultCodeEnum.UriBuilderException);
        }
        HttpGet httpGet = new HttpGet(build);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> param : headers.entrySet()) {
                httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
            }
        }
        return httpResult(httpGet);

    }


    /**
     * 普通post请求
     *
     * @param url
     * @return String
     */
    public static String post(String url) {
        HttpPost httpPost = new HttpPost(url);
        return httpResult(httpPost);
    }

    /**
     * post方式提交表单
     *
     * @param url     地址
     * @param hashSet 头信息
     * @param param   参数
     * @return String
     */
    public static String post(String url, JSONObject param, HashSet<BasicHeader> hashSet) {
        String result = "";
        // 创建httpClient实例.
        CloseableHttpClient httpclient = HttpClientBuilder.create()
                .setDefaultHeaders(hashSet)
                .build();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);

        try {
            StringEntity entity = new StringEntity(param.toString(), "utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(httpEntity, "UTF-8");
            }
            response.close();
            // 关闭连接,释放资源
            httpclient.close();
        } catch (IOException e) {
            log.error("post error: e={}", e.getMessage());
            throw new BaseException(ResultCodeEnum.IOException);
        }

        return result;
    }


    /**
     * 携带头信息，参数的post请求，参数编码UTF-8
     *
     * @param url
     * @param headers
     * @param params
     * @return String
     */
    public static String post(String url, Map<String, Object> headers, Map<String, Object> params) {

        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params2NVPs(params), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("postMap error: e={}", e.getMessage());

        }
        return httpResult(httpPost);

    }


    /**
     * json或者xml形式的post请求，参数编码UTF-8
     * 1：json，2：xml
     *
     * @param url
     * @param param
     * @return String
     */
    public static String postJsonOrXml(Integer type, String url, String param) {

        boolean bool = type == null || type != 1 || type != 2 || Strings.isNullOrEmpty(url) || Strings.isNullOrEmpty(param);

        if (bool) {
            log.error("postJsonOrXml error: type={}, url={}, param={}", type, url, param);
            throw new BaseException(ResultCodeEnum.ParamError);
        }
        HttpPost httpPost = new HttpPost(url);
        StringEntity stringEntity = null;
        if (type == ProgramConst.JSON) {

            stringEntity = new StringEntity(param, ContentType.create("application/json",
                    "UTF-8"));
        } else if (type == ProgramConst.XML) {
            stringEntity = new StringEntity(param, ContentType.create("application/xml",
                    "UTF-8"));
        }

        httpPost.setEntity(stringEntity);
        return httpResult(httpPost);
    }

    /**
     * 上传
     *
     * @param url
     * @param files
     * @return String
     */
    public static String upload(String url, List<File> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (int i = 0; i < files.size(); i++) {
            builder.addBinaryBody("file_" + i, files.get(i), ContentType.DEFAULT_BINARY, files.get(i).getName());
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpResult(httpPost);
    }

    /**
     * 上传
     *
     * @param url
     * @param headers
     * @param files
     * @return String
     */
    public static String upload(String url, Map<String, Object> headers, List<File> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (int i = 0; i < files.size(); i++) {
            builder.addBinaryBody("file_" + i, files.get(i), ContentType.DEFAULT_BINARY, files.get(i).getName());
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpResult(httpPost);
    }

    /**
     * 上传
     *
     * @param url     必须
     * @param headers 头文件非必须，不设置传null
     * @param params  参数非必须，不设置传null
     * @param files   必须
     * @return String
     */
    public static String upload(String url, Map<String, Object> headers, Map<String, Object> params, List<File> files) {
        if (files == null || files.size() == 0) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        for (int i = 0; i < files.size(); i++) {
            builder.addBinaryBody("file_" + i, files.get(i), ContentType.DEFAULT_BINARY, files.get(i).getName());
        }
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()), ContentType.DEFAULT_BINARY);
            }
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);
        return httpResult(httpPost);
    }


    /**
     * 参数转换
     *
     * @param params
     * @return List<NameValuePair>
     */
    private static List<NameValuePair> params2NVPs(Map<String, Object> params) {
        List<NameValuePair> nvpList = new ArrayList<NameValuePair>();
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                nvpList.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
            }
        }
        return nvpList;
    }

    /**
     * 请求结果处理
     *
     * @param request
     * @return String
     */
    private static String httpResult(HttpRequestBase request) {

        //创建httpClient
        CloseableHttpClient httpClient = createHttpClient();

        //配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(ProgramConst.HTTP_CONNECT_TIME_OUT)
                .setConnectTimeout(ProgramConst.HTTP_CONNECT_TIME_OUT).setSocketTimeout(ProgramConst.HTTP_RESPONSE_TIME_OUT).build();
        request.setConfig(requestConfig);

        CloseableHttpResponse response;
        String result = null;
        try {
            response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                response.close();
            }
        } catch (IOException e) {
            log.error("httpResult error: e={}", e.getMessage());
            throw new BaseException(ResultCodeEnum.HttpConnectTimeOut);
        }
        return result;
    }


    /**
     * 创建HttpClient
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient createHttpClient() {

        ConnectionSocketFactory connectionSocketFactory = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslSF = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", connectionSocketFactory).register("https", sslSF).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

        // 最大连接数
        connectionManager.setMaxTotal(ProgramConst.HTTP_MAX_CONNECT_NUM);

        // 每个路由基础的连接
        connectionManager.setDefaultMaxPerRoute(ProgramConst.HTTP_ROUTE_MAX_CONNECT_NUM);

        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            if (executionCount >= ProgramConst.HTTP_CONNECT_RETRY_NUM) {
                return false;
            }
            // 连接丢失，重试
            if (exception instanceof NoHttpResponseException) {
                return true;
            }

            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();

            // 请求幂等，再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                .setRetryHandler(httpRequestRetryHandler).build();
        return httpClient;
    }

}
