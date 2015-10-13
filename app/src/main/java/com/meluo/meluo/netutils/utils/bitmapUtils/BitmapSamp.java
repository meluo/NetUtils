package com.meluo.meluo.netutils.utils.bitmapUtils;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-12
 */

import android.graphics.BitmapFactory;

/**
 * ͼƬ��ѹ��ȡ��
 */
public class BitmapSamp {

    public static BitmapFactory.Options op;


    public static void setBitmapSamp(byte data[],int width,int height){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        int picW = options.outWidth;
        int picH = options.outHeight;

        int reqW = width;

        int reqH = height;
        options.inSampleSize =
                calculateInSampleSize(options, reqW, reqH);
        options.inJustDecodeBounds = false;
        op= options;

    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        // ������Ŀ�ȡ��߶� > 0 ʱ�򣬽������ţ�
        // ����ͼƬ���������ţ�
        if (reqWidth > 0 && reqHeight > 0) {
            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;
                while ((halfHeight / inSampleSize) >= reqHeight
                        && (halfWidth / inSampleSize) >= reqWidth) {
                    inSampleSize *= 2;
                }
            }
        }

        return inSampleSize;
    }
}
