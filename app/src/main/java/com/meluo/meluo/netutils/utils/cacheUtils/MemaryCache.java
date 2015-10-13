package com.meluo.meluo.netutils.utils.cacheUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.meluo.meluo.netutils.utils.bitmapUtils.BitmapSamp;
import com.meluo.meluo.netutils.utils.httpUtis.StreamUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-12
 */
public class MemaryCache {

    private static LruCache<String,Bitmap> lruCache;//һ������ ʹ��lru �㷨
    private static Map<String,SoftReference<Bitmap>> softCache;//�������� �����û���

    /**
     *
     * @param i ����Lrucahe �Ĵ�С size=i*1M
     */
    public static void setLruCaheSize(int i){
        if(lruCache==null) {
            softCache=new HashMap<String,SoftReference<Bitmap>>();
            lruCache = new LruCache<String, Bitmap>(i * 1024 * 1024) {

                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }


                @Override
                protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {

                    if(evicted)
                        softCache.put(key, new SoftReference<Bitmap>(oldValue));
                    super.entryRemoved(evicted, key, oldValue, newValue);
                }
            };
        }else {
            throw new IllegalStateException("lrucache �Ѿ�ʵ������ �볢������������");
        }
    }

    /**
     *
     * @param i ����Lrucache ����Ŷ��ٸ�����
     */
    public static void setLruCacheMax(int i){

       if(lruCache==null){
           softCache=new HashMap<String,SoftReference<Bitmap>>();
           lruCache=new LruCache<String,Bitmap>(i){
               @Override
               protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
                   softCache.put(key, new SoftReference<Bitmap>(oldValue));
                   super.entryRemoved(evicted, key, oldValue, newValue);
               }
           };

       }else {
           throw new IllegalStateException("lrucache �Ѿ�ʵ������ �볢������������");
       }
    }

    /**
     * ����url �ӻ����ж�ȡ����
     */
    public static Bitmap gitBitmap(String url){
        //��lrucache ��ȡͼƬ
        Bitmap bitmap=lruCache.get(url);
        if(bitmap==null){
           SoftReference<Bitmap> reference=softCache.get(url);
            if(reference!=null){
                bitmap=reference.get();
                if(bitmap!=null){
                    lruCache.put(url,bitmap);
                    softCache.remove(url);
                }
            }else{
                byte b[]=FileCache.getInstance().load(url);
                if(b!=null) {
                    bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                    lruCache.put(url, bitmap);
                }
            }
        }
        return bitmap;
    }

    public static void putBitmap(String url,byte []b){
        //TODO ���ö��β���
        Bitmap bitmap=BitmapFactory.decodeByteArray(b,0,b.length,BitmapSamp.op);
        lruCache.put(url,bitmap);

        FileCache.getInstance().save(url,b);
    }






}
