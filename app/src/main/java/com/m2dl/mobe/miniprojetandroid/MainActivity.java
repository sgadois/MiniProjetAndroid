package com.m2dl.mobe.miniprojetandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m2dl.mobe.miniprojetandroid.geolocalisation.GeolocalisationActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, GeolocalisationActivity.class);
        startActivity(intent);

    }
}
