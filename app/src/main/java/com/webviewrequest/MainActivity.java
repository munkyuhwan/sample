package com.webviewrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MyHttpRequest.VolleyRequestClass;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebview;
    private EditText webviewUrl, postUrl, nameText, ageText, seconds;
    private Button loadWebView, postBtn, stopBtn;
    private StartTransmiting st;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mWebview = (WebView) findViewById(R.id.webView);

        this.webviewUrl = (EditText) findViewById(R.id.webview_url);
        this.postUrl = (EditText)findViewById(R.id.url);
        this.nameText = (EditText) findViewById(R.id.name_value);
        this.ageText = (EditText)findViewById(R.id.age_value);
        this.seconds = (EditText)findViewById(R.id.seconds);

        this.postBtn = (Button) findViewById(R.id.post_btn);
        this.stopBtn = (Button)findViewById(R.id.stop_btn);
        this.loadWebView = (Button)findViewById(R.id.url_load);

        this.postBtn.setOnClickListener(this);
        this.stopBtn.setOnClickListener(this);
        this.loadWebView.setOnClickListener(this);
        webviewSetting();

    }


    public void webviewSetting() {

        mWebview.setWebChromeClient(new WebChromeClient());

        mWebview.setWebViewClient(new WebViewClient());
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mWebview.getSettings().setAppCacheEnabled(true);

        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setSupportMultipleWindows(true);
        mWebview.getSettings().setAllowFileAccess(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebview.getSettings().setAllowContentAccess(true);
        mWebview.getSettings().setAllowFileAccessFromFileURLs(true);
        mWebview.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);

        mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mWebview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.getSettings().setEnableSmoothTransition(true);

        mWebview.setWebContentsDebuggingEnabled(true);

        // mWebview.getSettings().setGeolocationDatabasePath(getFilesDir().getPath());
        mWebview.getSettings().setGeolocationEnabled(true);
        mWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebview.getSettings().setSupportMultipleWindows(false);

        mWebview.getSettings().setAppCacheEnabled(false);
        mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebview.getSettings().setDatabaseEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        mWebview.getSettings().setAppCacheEnabled(true);
    }

    private boolean validationCheck() {

        if (nameText.getText() == null || nameText.getText().toString().equals("") ) {
            Toast.makeText(this, "name값을 입력하세요",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ageText.getText() == null || ageText.getText().toString().equals("") ) {
            Toast.makeText(this, "age값을 입력하세요",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (postUrl.getText() == null || postUrl.getText().toString().equals("") ) {
            Toast.makeText(this, "url값을 입력하세요",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (seconds.getText() == null || seconds.getText().toString().equals("") ) {
            Toast.makeText(this, "전송간격을 입력하세요",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_btn:
                if (validationCheck()) {
                    // asyncTask 인스턴스화(백그라운드 시작)
                    // name, age, url, 요청 간격 값으로 초기화
                    st = new StartTransmiting(nameText.getText().toString(), ageText.getText().toString(), this, postUrl.getText().toString(), seconds.getText().toString());
                    st.execute();
                }
                break;
            case R.id.stop_btn:
                if (st != null) {
                    //asyncTask 종료
                    st.cancel(true);
                }
                break;
            case R.id.url_load:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mWebview.loadUrl(webviewUrl.getText().toString());
                    }
                });
                break;

            default:
                break;


        }
    }

    class StartTransmiting extends AsyncTask<Void, Void, Void> {

        String name;
        String age;
        Activity activity;
        String pUrl;
        String seconds;
        public StartTransmiting(String name, String age, Activity activity, String pUrl, String seconds) {
            this.name = name;
            this.age = age;
            this.activity = activity;
            this.pUrl = pUrl;
            this.seconds = seconds;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            while(true) {

                Map<String, String >data = new HashMap<>();
                data.put("name", name);
                data.put("age", age);

                // 서버 요청 클래스
                VolleyRequestClass volley = new VolleyRequestClass(activity);
                // 해시맵으로 된 데이터를 전송
                volley.startSendData(data, pUrl);

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


}
