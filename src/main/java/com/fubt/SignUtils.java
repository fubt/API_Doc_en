package com.fubt;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

/**
 * 签名工具，使用hmacSha1
 * 
 * @author jonathan 2018年4月3日 下午6:23:09
 * @since 1.0.0
 */
public class SignUtils {

    /**
     * 创建签名
     * 
     * @param params
     * @return
     */
    public static String createSign(Map<String, String> params, String secretKey) {
        // 1.编码所有value值,防止出现乱码
        Map<String, String> encodeUrlMap = encodeParamValues(params);

        // 2.key排序
        Map<String, String> sortedMap = sortedByKey(encodeUrlMap);

        // 3.拼装url name=Jonathan&six=1
        String urlParam = UrlUtils.createQueryString(sortedMap);

        // 4.签名
        byte[] data = HmacUtils.hmacSha1(secretKey.getBytes(), urlParam.getBytes());

        return Hex.encodeHexString(data);
    }

    /**
     * 参数值进行urlDecode编码
     * 
     * @param params
     * @return
     */
    public static Map<String, String> encodeParamValues(Map<String, String> params) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            params.put(entry.getKey(), UrlUtils.encode(entry.getValue()));

        }
        return params;
    }

    /**
     * 参数排序
     * 
     * @param params
     * @return
     */
    public static Map<String, String> sortedByKey(Map<String, String> params) {

        String[] keys = params.keySet().toArray(new String[params.size()]);
        Arrays.sort(keys);

        Map<String, String> sortedKeyMap = Maps.newLinkedHashMap();

        for (String key : keys) {
            // sign、timestamp不需要进行签名排序
            if (StringUtils.equals("sign", key) || StringUtils.equals("timestamp", key)) {
                continue;
            }
            sortedKeyMap.put(key, params.get(key));
        }
        return sortedKeyMap;
    }

}
