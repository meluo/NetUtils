package com.meluo.meluo.netutils.manageRequest;

import android.os.Handler;

import com.meluo.meluo.netutils.userTool.Request;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created
 * Author:meluo
 * Email:kongmuo@126.com
 * Date:15-10-13
 */
public class RequestQueue  {

    private static ExecutorService pool= Executors.newFixedThreadPool(5);
    public static RequestQueue requestQueue;
    public static RequestQueue getInstance(){
        if(requestQueue==null){
            requestQueue=new RequestQueue();

        }
        return requestQueue;
    }

    public void addRequest(Request request){
       pool.execute(new Net(request));
    }


class Net implements Runnable{
    private Request request;
    public Net(Request request) {
        this.request = request;
    }
    @Override
    public void run() {
        request.comment();
    }
}


}
