package com.fubt;

import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jonathan 2018年4月3日 下午6:41:45
 * @since 1.0.0
 */
public class UrlUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * 解码URL
     * 
     * @param param 参数
     * @param encoding 编码
     * @return
     */
    public static String decode(String param, String encoding) {
        try {
            param = URLDecoder.decode(param, encoding);
        } catch (UnsupportedEncodingException e) {
            // ignore.
        }
        return param;
    }

    /**
     * 解码URL
     * 
     * @param param
     * @return
     */
    public static String decode(String param) {
        return decode(param, DEFAULT_ENCODING);
    }

    /**
     * URL编码
     * 
     * @param param
     * @param encoding
     * @return
     */
    public static String encode(String param, String encoding) {
        try {
            param = URLEncoder.encode(param, encoding);
        } catch (UnsupportedEncodingException e) {
            // ignore.
        }
        return param;
    }

    /**
     * URL编码
     * 
     * @param param
     * @param encoding
     * @return
     */
    public static String encode(String param) {
        return encode(param, DEFAULT_ENCODING);
    }

    /**
     * Map拼装成querystring参数 如：name=jonathan&address=beijing
     * 
     * @param params
     * @return
     */
    public static String createQueryString(Map<String, String> params) {

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("&");

        }

        String urlParam = sb.toString();
        if (urlParam.length() > 0 && urlParam.contains("&")) {
            urlParam = urlParam.substring(0, urlParam.length() - 1);
        }
        return urlParam;

    }

    public static String joinUrl(String url, String queryString, String sign) {
        return url + "?" + queryString + "&sign=" + sign + "&timestamp=" + System.currentTimeMillis();
    }

    public static void main(String[] args) {
        String secretKey = "q0YO4abUxWMWgILexX2JW9TwXfLLa0SfRSmY0QSq"; //
        Map<String, String> params = Maps.newHashMap();//346326
        params.put("symbol", "fuc_fbt");
        params.put("type", "entruts");
        params.put("size", "2");
        params.put("timestamp",System.currentTimeMillis()+"");
        String param = SignUtils.createSign(params, secretKey);
        params.put("sign",param);
        String url = "https://rest.fubt.top";
        String urlput = "/v1/order/orders/entrust";
        Map<String ,String> map = new HashMap();
        map.put("AccessKeyId","OeNEem5le/DOSXGhIAOS");
        System.out.println(params);
        String rstule = HttpUtils.doGet(url + urlput,map, params);
        System.out.println(rstule);


    }

}
