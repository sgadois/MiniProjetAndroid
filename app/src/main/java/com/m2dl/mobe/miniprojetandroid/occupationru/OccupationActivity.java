package com.m2dl.mobe.miniprojetandroid.occupationru;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.m2dl.mobe.miniprojetandroid.R;

/**
 * Created by Blue on 03/03/2017.
 */

public class OccupationActivity extends AppCompatActivity {
    OccupationRU oru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        oru = new OccupationRU();
        setContentView(R.layout.activity_main);
        oru.makeGraph(this);

    }
}
