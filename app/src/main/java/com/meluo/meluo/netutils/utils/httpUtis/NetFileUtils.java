package com.meluo.meluo.netutils.utils.httpUtis;

import java.io.File;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-17
 */
public class NetFileUtils {




    private boolean upload(String filepath){

        long fileSize=0;
        String filetype;
        File file=new File(filepath);
        if(file.exists()){
            fileSize=file.length();//获取文件大小
            filetype=filepath.substring(filepath.lastIndexOf(".")+1);//获取文件类型
        }

      return true;
    }



}
