package com.MyHttpRequest;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequestClass {

    private Activity mCurrentActivity;

    public VolleyRequestClass(Activity currentActivity) {
        this.mCurrentActivity = currentActivity;
    }

    public void multipartVolley(final Map<String, String> jsonDatas, final String dataRequestURL) {

        // Volley http Post로 전송
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, dataRequestURL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        // 전송 response
                        successToast();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 오류 리스
                        error.printStackTrace();
                        errorToast();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // 전송 데이터 추가
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
        //http 요청함수 콜
        multipartVolley( jsonDatas, dataRequestURL);
    }



}
