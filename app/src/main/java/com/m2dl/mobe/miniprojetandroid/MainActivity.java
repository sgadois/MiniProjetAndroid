package com.m2dl.mobe.miniprojetandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.m2dl.mobe.miniprojetandroid.occupationru.OccupationRU;

public class MainActivity extends AppCompatActivity {
    OccupationRU oru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oru = new OccupationRU();
        setContentView(R.layout.activity_main);
        oru.makeGraph(this);

    }


}
