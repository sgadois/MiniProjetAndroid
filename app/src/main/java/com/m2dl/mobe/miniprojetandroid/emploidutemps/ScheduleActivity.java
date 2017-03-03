package com.m2dl.mobe.miniprojetandroid.emploidutemps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.m2dl.mobe.miniprojetandroid.R;
import com.m2dl.mobe.miniprojetandroid.configuration.URLSched;

public class ScheduleActivity extends AppCompatActivity {
    private WebView mWebView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        URLSched url = new URLSched("https://edt.univ-tlse3.fr/FSI/FSImentionM/Info/g31090.html");
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl(url.getUrl());
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setSupportZoom(true);
    }
}
