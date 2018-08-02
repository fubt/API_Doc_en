package com.fubt;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * HTTP工具类
 * 
 * @author jonathan 2018年4月12日 上午9:35:13
 * @since 1.0.0
 */
public class

HttpUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";

    public static String doGet(String url, Map<String, String> headers, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 设置header
            setHeaders(httpGet, headers);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doGet(String url, Map<String, String> param) {
        return doGet(url, null, param);
    }

    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    public static String doPost(String url, Map<String, String> headers, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            setHeaders(httpPost, headers);
            // 创建参数列表
            if (param != null) {
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(createNameValuePair(param));
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), DEFAULT_ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static String doPost(String url, Map<String, String> param) {
        return doPost(url, null, param);
    }

    public static String doPost(String url) {
        return doPost(url, null, null);
    }

    public static void setHeaders(HttpRequestBase httpRequestBase, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpRequestBase.addHeader(entry.getKey(), entry.getValue());

        }
    }

    public static List<NameValuePair> createNameValuePair(Map<String, String> params) {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String name = entry.getKey();
            String value = entry.getValue();

            nameValuePairs.add(new BasicNameValuePair(name, value));

        }
        return nameValuePairs;

    }

}
