package com.meluo.meluo.netutils.utils.httpUtis;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-12
 */

public final class HttpTools {

    public static final String GET="GET";
    public static final String POST="POST";

    private HttpTools(){

    }

    /**
     *
     * @param url 网络请求的url
     * @return 返回byte[] 是网络请求结果
     */
    public static byte[] doGet(String url,HashMap<String,Object> headmap) {
        byte [] ret=null;
        if (url != null) {
            HttpURLConnection conn=null;
            try {
                URL ur=new URL(url);
                conn= (HttpURLConnection) ur.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                StreamUtils.setRequestHeader(headmap, conn, "utf-8");
                conn.connect();

                int code = conn.getResponseCode();
                if (code==200){
                   ret= StreamUtils.readStream(conn.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

               StreamUtils.close(conn);
            }
        }

        return ret;
    }

    /**
     *
     * 访问网络的POST 请求
     * @param url 请求的Url 地址
     * @param bodymap 提交网络请求的参数
     * @param headmap 提交网络的头部消息参数
     * @return  返回网络请求的结果的byte 数组
     */
    public static byte[] doPost(String url,HashMap<String,Object> bodymap,HashMap<String,Object> headmap,String charsetName){
        byte [] ret=null;
       HttpURLConnection conn=null;
        try {
            URL u=new URL(url);
            conn=(HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            StreamUtils.setRequestHeader(headmap, conn, charsetName);
            conn.connect();
            StreamUtils.setRequestBody(bodymap,conn.getOutputStream(),charsetName);
            if(conn.getResponseCode()==200){
                ret=StreamUtils.readStream(conn.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            StreamUtils.close(conn);
        }
        return ret;
    }

}
