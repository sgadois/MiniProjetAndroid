package com.m2dl.mobe.miniprojetandroid;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.m2dl.mobe.miniprojetandroid.emploidutemps.ScheduleActivity;
import com.m2dl.mobe.miniprojetandroid.geolocalisation.GeolocalisationActivity;
import com.m2dl.mobe.miniprojetandroid.occupationru.OccupationActivity;
import com.m2dl.mobe.miniprojetandroid.qrcode.Qrcodescanner;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.geoloc:
                startActivity(new Intent(this, GeolocalisationActivity.class));
                return true;
            case R.id.occupRU:
                startActivity(new Intent(this, OccupationActivity.class));
                return true;
            case R.id.schedule:
                startActivity(new Intent(this, ScheduleActivity.class));
                return true;
            case R.id.qrcode:
                startActivity(new Intent(this, Qrcodescanner.class));
                return true;
            case R.id.config:
                //TODO
                return true;
            default:
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}
