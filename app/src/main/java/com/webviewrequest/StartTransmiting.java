package com.webviewrequest;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.MyHttpRequest.VolleyRequestClass;

import java.util.HashMap;
import java.util.Map;

class StartTransmiting extends AsyncTask<Void, Void, Void> {

    String name;
    String age;
    Activity activity;
    String pUrl;
    String seconds;
    Context mContext;

    public StartTransmiting(String name, String age, Activity activity, String pUrl, String seconds) {
        this.name = name;
        this.age = age;
        this.activity = activity;
        this.pUrl = pUrl;
        this.seconds = seconds;
    }

    public StartTransmiting(String name, String age, Context context, String pUrl, String seconds) {
        this.name = name;
        this.age = age;
        this.mContext = context;
        this.pUrl = pUrl;
        this.seconds = seconds;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        while(true) {

            Map<String, String > data = new HashMap<>();
            data.put("name", name);
            data.put("age", age);

            // 서버 요청 클래스
            VolleyRequestClass volley = new VolleyRequestClass(mContext);
            // 해시맵으로 된 데이터를 전송
            volley.startSendData(data, pUrl);
            Log.e("tag","=============================");
            Log.e("tag","background running");
            try {
                Thread.sleep(Integer.parseInt(seconds)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (isCancelled()) {
                break;
            }

        }
        return null;
    }
}
