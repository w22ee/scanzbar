package com.example.scanzbar;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lixi on 16/3/9.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {
    private static final String DATABASE = "url_share";
    private static final String URL_SAVE = "url_save";
    public String url = "";
    private SharedPreferences sp;
    private EditText url_et;
    private Button comfrim_btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_settings);
        sp = getSharedPreferences(DATABASE, Activity.MODE_PRIVATE);
        url_et = (EditText) findViewById(R.id.url_et);
        comfrim_btn = (Button) findViewById(R.id.comfrim_btn);
        comfrim_btn.setOnClickListener(this);
        url = sp.getString(URL_SAVE, "");
        url_et.setText(url);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.comfrim_btn) {
            SharedPreferences.Editor sPeditor = sp.edit();
            sPeditor.putString(URL_SAVE, url_et.getText().toString());
            sPeditor.commit();
            Toast.makeText(SettingsActivity.this,"储存成功",Toast.LENGTH_SHORT).show();
        }
    }
}