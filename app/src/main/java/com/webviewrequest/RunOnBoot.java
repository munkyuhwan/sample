package com.webviewrequest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.MyHttpRequest.VolleyRequestClass;

import java.util.HashMap;
import java.util.Map;

public class RunOnBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            Log.e("tag","=============================");
            Log.e("tag","on boot receved");

            StartTransmiting st = new StartTransmiting("name","age",context,"http://192.168.35.89:8888","3");
            st.execute();

        }

    }
}
