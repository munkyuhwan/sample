package com.MyHttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class VolleyRequestClass {


    private Activity mCurrentActivity;



    public VolleyRequestClass(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    public void multipartVolley(final Map<String, String> jsonDatas, final String dataRequestURL) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, dataRequestURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Log.d("tag", String.valueOf(response));

                        try {
                            JSONArray obj = new JSONArray(new String(response.data));
                            Log.d("tag","name: "+obj.getJSONObject(0).getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            errorToast();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                        errorToast();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params = jsonDatas;
                return params;
            }

        };
        Volley.newRequestQueue(mCurrentActivity).add(volleyMultipartRequest);
    }

    private void errorToast() {
        mCurrentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mCurrentActivity, "네트워크 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void successToast() {
        mCurrentActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mCurrentActivity, "데이터가 전송되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startSendData(final Map<String, String> jsonDatas, String dataRequestURL) {
        multipartVolley( jsonDatas, dataRequestURL);
    }



}
