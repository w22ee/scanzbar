package com.example.scanzbar;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements OnClickListener {
    private static final String DATABASE = "url_share";
    private static final String URL_SAVE = "url_save";
    public String url = "";
    private TextView editresult;
    private EditText url_et;
    private String scanResult;
    private Context mContext;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        url_et = (EditText) findViewById(R.id.url_et);
        editresult = (TextView) findViewById(R.id.editText1);
        sp = getSharedPreferences(DATABASE, Activity.MODE_PRIVATE);
        url = sp.getString(URL_SAVE, "");

        url_et.setText(url);
        editresult.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            editresult.setTextIsSelectable(true);
        }
        Button scanBarCodeButton = (Button) this.findViewById(R.id.button1);
        mContext = MainActivity.this;
        scanBarCodeButton.setOnClickListener(this);
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor sPeditor = sp.edit();
        sPeditor.putString(URL_SAVE, url_et.getText().toString());
        sPeditor.commit();
        super.onStop();

    }

    private void showTost(String scanResult) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(url_et.getText().toString() + scanResult, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String info = response.getString("result");
                    editresult.setText(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Bundle bundle = data.getExtras();
            scanResult = bundle.getString("result");
            showTost(scanResult);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button1) {
            Intent openCameraIntent = new Intent(MainActivity.this, CameraTestActivity.class);
            startActivityForResult(openCameraIntent, 0);
        }
    }
}
