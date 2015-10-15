package com.meluo.meluo.netutils.userTool;

import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.meluo.meluo.netutils.utils.bitmapUtils.BitmapSamp;
import com.meluo.meluo.netutils.utils.cacheUtils.FileCache;
import com.meluo.meluo.netutils.utils.cacheUtils.MemaryCache;
import com.meluo.meluo.netutils.utils.httpUtis.HttpTools;

import java.util.HashMap;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-12
 */
public class BitmapRequest implements Request {

    private Context context;
    private int width;
    private int height;
    private String url;
    private Callback Callback;
    private HashMap<String, Object> mapHeader;
    int i = -1;
    private static Handler handler = new Handler();

    public BitmapRequest(Context context, String url, Callback Callback) {
        this.context = context;
        this.Callback = Callback;
        this.url = url;
    }

    /**
     * @param width  想要获取图片的宽度
     * @param height 想要获取图片的高度
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * 提交
     */
    @Override
    public void comment() {

        if (i == -1) {
            setCacheSize(2);
        }
        FileCache.newInstance(context);
        Bitmap bitmap = MemaryCache.gitBitmap(url,width,height);
        if (bitmap == null) {
            byte b[] = HttpTools.doGet(url,mapHeader);
            if (b == null || b.length == 0) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Callback.fail("获取的数据为空");
                    }
                });
                return;

            }
            MemaryCache.putBitmap(url, b);
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, new BitmapSamp().setBitmapSamp(b, width, height));
        }
        final Bitmap finalBitmap = bitmap;
        handler.post(new Runnable() {
            @Override
            public void run() {
                Callback.success(finalBitmap);
            }
        });

    }

    /**
     * 设置请求的消息头部信息 如密钥等
     * @param mheader
     */
    @Override
    public void setHeader(HashMap<String, Object> mheader) {
        mapHeader.putAll(mheader);
    }
    public interface Callback {
        public void success(Bitmap bitmap);
        public void fail(String mesg);
    }
    /**
     *
     * 设置cache尺寸 i*M;
     * @param i
     */
    public void setCacheSize(int i) {
        this.i = 9;
        MemaryCache.setLruCaheSize(i);
    }
    /**
     * 设置cache 最多缓存多少条数据
     * @param i
     */
    public void setCacheMax(int i) {
        this.i = 9;
        MemaryCache.setLruCacheMax(i);
    }

}
