package com.meluo.meluo.netutils.userTool;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.meluo.meluo.netutils.utils.httpUtis.HttpTools;
import com.meluo.meluo.netutils.utils.httpUtis.StreamUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-13
 */
public class StringRequest implements Request {

    private Callback callback;
    private Context context;
    private String url;
    private String method;
    private HashMap<String, Object> mapHeader;
    private String charset = "utf-8";
    private HashMap<String, Object> map;
    private static Handler handler = new Handler();

    /**
     * get 方式提交
     * @param context 上下文
     * @param url  地址
     * @param callback  回调接口
     */
    public StringRequest(Context context, String url, Callback callback) {
        this.callback = callback;
        this.context = context;
        this.url = url;
        this.method = HttpTools.GET;
        mapHeader = new HashMap<>();
    }

    /**
     * POST 请求方式提交
     * @param context
     * @param url
     * @param map
     * @param callback
     */
    public StringRequest(Context context, String url, HashMap<String, Object> map,Callback callback) {
        this.callback = callback;
        this.context = context;
        this.url = url;
        this.map = map;
        this.method = HttpTools.POST;
        mapHeader = new HashMap<>();
    }

    /**
     * 会在网络线程执行
     */
    @Override
    public void comment() {

        byte b[] = null;
        if (HttpTools.GET.equals(method)) {
            b = HttpTools.doGet(url, mapHeader);
        } else if (HttpTools.POST.equals(method)) {
            b = HttpTools.doPost(url, map, mapHeader, charset);
        }
        if (b == null) {

            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.fail("ʧ��");
                }
            });

        } else {
            final byte[] finalB = b;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.success(new String(finalB), url);
                }
            });

        }
    }

    /**
     * 设置消息头 如密钥等
     */
    @Override
    public void setHeader(HashMap<String, Object> mheader) {
        mapHeader.putAll(mheader);
    }


    public interface Callback {
        public void success(String str, String url);

        public void fail(String mesg);
    }
}
