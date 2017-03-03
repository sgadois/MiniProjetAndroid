package com.m2dl.mobe.miniprojetandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.m2dl.mobe.miniprojetandroid.emploidutemps.ScheduleActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialisation des activités à appeler
        final Intent schedule = new Intent(this, ScheduleActivity.class);

        Button mafac = (Button) findViewById(R.id.mafac);
        mafac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO fac intent
            }
        });
        Button mescours = (Button) findViewById(R.id.mescours);
        mescours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(schedule);
            }
        });
        Button config = (Button) findViewById(R.id.config);
        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO config intent
            }
        });
        Button quitter = (Button) findViewById(R.id.close);
        quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void finish() {
        super.finish();

    }
}
