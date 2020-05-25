package com.zhangzhigang.sso.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.jedis.JedisScriptReturnConverter;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Base64;
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
    
    public static String post(String url, String jsessionId) {
        // 返回报文
        String responseText = null;

        // HttpClient
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(new BasicClientCookie("JSESSIONID", jsessionId));
        CloseableHttpClient httpClient = HttpClientBuilder.create()
        									.setDefaultCookieStore(cookieStore)
        									.build();
        HttpPost httpPost = new HttpPost(url);
        try {
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

    public static void sendHttpRequest(String httpUrl, String jsessionId) throws Exception {
    	URL url = new URL(httpUrl);
    	HttpURLConnection  conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("POST");
    	conn.setDoOutput(true);
    	byte[] encodedCookieBytes = Base64.getEncoder().encode(jsessionId.getBytes());
    	jsessionId =  new String(encodedCookieBytes);
    	conn.addRequestProperty("Cookie", "SESSION=" + jsessionId);
    	conn.connect();
    	conn.getInputStream();
    	conn.disconnect();
    }
    
    public static void main(String[] args) throws Exception {
		String url = "http://www.tm.com:8062/logOut";
//		String result = post(url, "FA697663743AD40D557C565E38A51B39");
		sendHttpRequest(url,"18C4C9640C77D213DE55F7DBA0D47FE1");
	}
}
