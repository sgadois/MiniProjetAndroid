package com.m2dl.mobe.miniprojetandroid.emploidutemps;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import com.m2dl.mobe.miniprojetandroid.R;

public class ScheduleActivity extends AppCompatActivity {
    private WebView mWebView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        String url = sharedPrefs.getString("urlschedule","");
        if(url.isEmpty() || url.equals("http://example.com, http://mon-emploi-du-temps.fr")){
            Toast.makeText(this, R.string.pref_error_url,
                    Toast.LENGTH_LONG).show();
        }
        else {
            mWebView = (WebView) findViewById(R.id.webview);
            mWebView.loadUrl(url);
            mWebView.getSettings().setBuiltInZoomControls(true);
            mWebView.getSettings().setSupportZoom(true);
        }
    }
}
