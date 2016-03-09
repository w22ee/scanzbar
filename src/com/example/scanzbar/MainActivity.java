package com.example.scanzbar;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
    private static final String DATABASE = "url_share";
    private static final String URL_SAVE = "url_save";
    public String url = "";
    private WebView resultView;

    private String scanResult;
    private Context mContext;
    private SharedPreferences sp;
    private Button setting_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        resultView = (WebView) findViewById(R.id.webview_res);
        setting_btn = (Button) findViewById(R.id.setting_btn);

        sp = getSharedPreferences(DATABASE, Activity.MODE_PRIVATE);
        url = sp.getString(URL_SAVE, "");

        Button scanBarCodeButton = (Button) this.findViewById(R.id.button1);
        mContext = MainActivity.this;
        scanBarCodeButton.setOnClickListener(this);
        setting_btn.setOnClickListener(this);
        setWebview();
    }

    private void setWebview() {
        WebSettings webSettings = resultView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        resultView.setWebChromeClient(new WebChromeClient() {

        });

        resultView.setWebViewClient(new WebViewClient() {

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        url = sp.getString(URL_SAVE, "");
    }


    private void loadViewView(String code) {
        resultView.loadUrl(url + code);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Bundle bundle = data.getExtras();
            scanResult = bundle.getString("result");
            loadViewView(scanResult);
        }
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.button1:
                Intent openCameraIntent = new Intent(MainActivity.this, CameraTestActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.setting_btn:
                Intent intent = new Intent();
                intent.setClass(mContext, SettingsActivity.class);
                startActivity(intent);
            default:
                break;
        }

    }
}
