package com.m2dl.mobe.miniprojetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.m2dl.mobe.miniprojetandroid.geolocalisation.GeolocalisationActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, GeolocalisationActivity.class);
        startActivity(intent);

    }


}
