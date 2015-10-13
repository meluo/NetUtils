package com.meluo.meluo.netutils;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.meluo.meluo.netutils.manageRequest.RequestQueue;
import com.meluo.meluo.netutils.userTool.BitmapRequest;
import com.meluo.meluo.netutils.userTool.StringRequest;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.text1);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        String url = "http://www.baidu.com";
        StringRequest request = new StringRequest(this, url, new StringRequest.Callback() {
            @Override
            public void success(String str, String url) {
                textView.setText(str);
            }

            @Override
            public void fail(String mesg) {
                textView.setText(mesg);
            }
        });

        RequestQueue.getInstance().addRequest(request);
        String path="http://desk.fd.zol-img.com.cn/g5/M00/08/04/ChMkJ1YYut2ITmS1AAnqUpOxiT4AADgnQN7HxAACepq145.jpg";
      BitmapRequest request2 = new BitmapRequest(this, path, new BitmapRequest.Callback() {
        @Override
        public void success(Bitmap bitmap) {
          imageView.setImageBitmap(bitmap);
        }
        @Override
        public void fail(String mesg) {
            imageView.setImageResource(android.R.drawable.ic_delete);
        }
      });
    request2.setSize(10,20);

    RequestQueue.getInstance().addRequest(request2);
}
}
