package com.zhangzhigang.tb.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpGet;

public abstract class HttpClientUtil {

    public static final String UTF_8 = "UTF-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    public static String post(String url, Map<String, Object> paramObj) {
        // 返回报文
        String responseText = null;

        // HttpClient
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(url);
        try {
            // 请求参数设置
            if (null != paramObj) {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                for (String key : paramObj.keySet()) {
                    params.add(new BasicNameValuePair(key, String.valueOf(paramObj.get(key))));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, UTF_8));
            }
            HttpEntity entity = httpClient.execute(httpPost).getEntity();

            // 响应报文
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return responseText;
    }

    public static String get(String url, Map<String, Object> paramObj) {
        // 返回报文
        String responseText = null;

        // HttpClient
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpGet get = new HttpGet(url);
        try {
            // 请求参数设置
            if (null != paramObj) {
                List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                for (String key : paramObj.keySet()) {
                    params.add(new BasicNameValuePair(key, String.valueOf(paramObj.get(key))));
                    get.getParams().setParameter(key, String.valueOf(paramObj.get(key)));
                }
//                get.setEntity(new UrlEncodedFormEntity(params, UTF_8));
            }
            HttpEntity entity = httpClient.execute(get).getEntity();

            // 响应报文
            if (entity != null && entity.getContentLength() != -1) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (ClientProtocolException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        return responseText;
    }

    public static void main(String[] args) {
		String url = "http://www.sso.com:8060/verify";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", "123456");
		String result = post(url, params);
		if (!StringUtils.isEmpty(result)) {
			JSONObject json = JSONObject.parseObject(result);
			System.out.println(json.get("username"));
		}
	}
}
