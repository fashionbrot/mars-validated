package com.github.fashion.test.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * @author fashionbrot
 * @version 0.1.0
 * @date 2019/12/8 22:45
 */
@Slf4j
public class HttpClientUtil {


    /**
     * 发送GET请求
     * @param url               url
     * @param headers           header
     * @param paramValues       value
     * @param encoding          encoding
     * @param readTimeoutMs     readTimeoutMs
     * @param connectTimeout    connectTimeout
     * @return HttpResult
     */
    public static  HttpResult httpGet(String url, List<String> headers, List<String> paramValues,String encoding, int readTimeoutMs,int connectTimeout,boolean enableLog) {

        String encodedContent = encodingParams(paramValues, encoding);
        url += (null == encodedContent) ? "" : ("?" + encodedContent);

        HttpURLConnection conn = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout( readTimeoutMs);

            setHeaders(conn, headers, encoding);
            conn.connect();

            int respCode = conn.getResponseCode();
            String resp = null;

            if (HttpURLConnection.HTTP_OK == respCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            if (inputStream != null) {
                resp = toString(inputStream, encoding);
            }
            return new HttpResult(respCode, resp);
        } catch (Exception e){
            if (enableLog) {
                log.error("httpGet error url:{} msg:{}", url, e.getMessage());
            }
            return new HttpResult(500, "Server Internal Error");
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error");
                }
            }
        }
    }


    /**
     * GET 请求
     * @param url           url
     * @param headers       header
     * @param paramValues   value
     * @return HttpResult
     */
    public static  HttpResult httpGet(String url, List<String> headers, List<String> paramValues,boolean enableLog){
        return httpGet(url,headers,paramValues,GlobalConstants.ENCODE_UTF8,5000,5000,enableLog);
    }


    /**
     * 发送POST
     * @param url               url
     * @param headers           header
     * @param paramValues       value
     * @param encoding          encoding
     * @param readTimeoutMs     readTimeoutMs
     * @param connectTimeout    connectTimeout
     * @return HttpResult
     */
    static public HttpResult httpPost(String url, List<String> headers, List<String> paramValues,
                                      String encoding, int readTimeoutMs,int connectTimeout,boolean enableLog){
        String encodedContent = encodingParams(paramValues, encoding);

        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(connectTimeout);
            conn.setReadTimeout(readTimeoutMs);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            setHeaders(conn, headers, encoding);

            outputStream = conn.getOutputStream();
            if (outputStream != null) {
                outputStream.write(encodedContent != null ? encodedContent.getBytes(encoding) : "?1=1".getBytes());
                outputStream.flush();
            }
            int respCode = conn.getResponseCode();


            if (HttpURLConnection.HTTP_OK == respCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }

            String resp = null;
            if (inputStream != null) {
                resp = toString(inputStream, encoding);
            }

            return new HttpResult(respCode, resp);
        }catch (Exception e){
            if (enableLog) {
                log.error("httpPost error url:{} msg:{}", url, e.getMessage());
            }
            return new HttpResult(500, "Server Internal Error");
        } finally {
            if (null != conn) {
                conn.disconnect();
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("inputStream close error");
                }
            }
            if (outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    log.error("outputStream close error");
                }
            }
        }
    }

    /**
     * 发送POST
     * @param url           URL
     * @param headers       header
     * @param paramValues   value
     * @return HttpResult
     */
    public static  HttpResult httpPost(String url, List<String> headers, List<String> paramValues,boolean enableLog) {
        return httpPost(url, headers, paramValues, GlobalConstants.ENCODE_UTF8, 5000,5000,enableLog);
    }

    private static void setHeaders(HttpURLConnection conn, List<String> headers, String encoding) {
        if (null != headers) {
            for (Iterator<String> iter = headers.iterator(); iter.hasNext(); ) {
                conn.addRequestProperty(iter.next(), iter.next());
            }
        }
        conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);
    }


    private static String encodingParams(List<String> paramValues, String encoding) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isEmpty(paramValues)) {
            return null;
        }
        for (Iterator<String> iter = paramValues.iterator(); iter.hasNext(); ) {
            sb.append(iter.next()).append("=");
            String param = iter.next();
            try {
                sb.append(URLEncoder.encode(param, encoding));
            } catch (UnsupportedEncodingException e) {
                log.error("encodingParams error param:{} encoding:{}",param,encoding);
            }
            if (iter.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }


    private static  String toString(InputStream input, String encoding) throws Exception {
        return CharStreamUtil.toString(new InputStreamReader(input,encoding==null? GlobalConstants.ENCODE_UTF8:encoding));
    }



}
