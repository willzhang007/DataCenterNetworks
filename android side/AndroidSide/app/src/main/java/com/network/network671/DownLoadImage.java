package com.network.network671;

import android.graphics.drawable.Drawable;
import android.os.Message;

import java.io.IOException;
import java.net.URL;


import android.os.Handler;

/**
 * Created by Lichao on 2015/11/12.
 */
public class DownLoadImage {
    private String imagePath;

    public DownLoadImage(String imagePath) {
        this.imagePath = imagePath;
    }

    public void loadImage(final ImageCallBack callBack) {
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                callBack.getDrawable((Drawable) msg.obj);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(imagePath).openStream(), "");
                    //callBack.getDrawable(drawable);
                    Message message = Message.obtain();
                    message.obj = drawable;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    //interface recall back method
    public interface ImageCallBack {
        public void getDrawable(Drawable drawable);
    }
}
