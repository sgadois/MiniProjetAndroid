package com.m2dl.mobe.miniprojetandroid;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.m2dl.mobe.miniprojetandroid.configuration.ConfigurationActivity;
import com.m2dl.mobe.miniprojetandroid.emploidutemps.ScheduleActivity;
import com.m2dl.mobe.miniprojetandroid.geolocalisation.GeolocalisationActivity;
import com.m2dl.mobe.miniprojetandroid.login.Login;
import com.m2dl.mobe.miniprojetandroid.occupationru.OccupationActivity;
import com.m2dl.mobe.miniprojetandroid.qrcode.Qrcodescanner;
import com.m2dl.mobe.miniprojetandroid.photo.PhotosActivity;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.geoloc:
                startActivity(new Intent(this, GeolocalisationActivity.class));
                return true;
            case R.id.occupRU:
                startActivity(new Intent(this, OccupationActivity.class));
                return true;
            case R.id.anomalie:
                startActivity(new Intent(this, PhotosActivity.class));
                return true;
            case R.id.schedule:
                startActivity(new Intent(this, ScheduleActivity.class));
                return true;
            case R.id.qrcode:
                startActivity(new Intent(this, Qrcodescanner.class));
                return true;
            case R.id.config:
                startActivity(new Intent(this, ConfigurationActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
