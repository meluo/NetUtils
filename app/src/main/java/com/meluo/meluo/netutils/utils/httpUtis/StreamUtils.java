package com.meluo.meluo.netutils.utils.httpUtis;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-12
 */
public class StreamUtils {

    private StreamUtils() {

    }

    /**
     *
     * @param obj 需要关闭的对象
     */
    public static void close(Object obj) {
        if (obj != null) {

            try {
                if (obj instanceof InputStream) {
                    ((InputStream) obj).close();
                } else if (obj instanceof OutputStream) {
                    ((OutputStream) obj).close();
                } else if (obj instanceof Reader) {
                    ((Reader) obj).close();
                } else if (obj instanceof Writer) {
                    ((Writer) obj).close();
                }else if(obj instanceof HttpURLConnection){
                    ((HttpURLConnection) obj).disconnect();
                }

            } catch (IOException e) {

            }
        }
    }

    /**
     *
     * @param in 需要转化的文件输入流
     * @return byte[] 输入流转化的字节数组
     * @throws IOException
     */
    public static byte[] readStream(InputStream in) throws IOException {
        byte[] ret = null;
        if (in != null) {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;

            while ((len = in.read(bytes)) != -1) {
                bout.write(bytes, 0, len);
            }
            bytes = null;
            ret = bout.toByteArray();
            bout.close();
        }


        return ret;
    }

    /**
     *
     * @param map 请求头部需要添加的map 集合
     * @param connection 网络的连接
     * @param charsetName  网络连接的编码方式
     * @throws UnsupportedEncodingException
     */
    public static void setRequestHeader(Map<String,Object>map,HttpURLConnection connection,String charsetName) throws UnsupportedEncodingException {

        if(map!=null||map.size()>0) {
            for (String key : map.keySet()) {
                String value = URLEncoder.encode(map.get(key).toString(), charsetName);
                connection.setRequestProperty(key, value);
            }
        }
        connection.setRequestProperty("Charset",charsetName);
    }

    /**
     *
     * @param map 请求的map 集合
     * @param out  请求的输出流
     * @param charsetName 设置编码方式
     * @throws IOException
     */
    public static void setRequestBody(Map<String,Object> map,OutputStream out,String charsetName) throws IOException {

        StringBuffer stringBuffer=new StringBuffer();
        for(String key:map.keySet()){
           stringBuffer.append(key+"="+map.get(key));
        }
        out.write(stringBuffer.toString().getBytes(charsetName));
    }

}
