package com.m2dl.mobe.miniprojetandroid.occupationru;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.m2dl.mobe.miniprojetandroid.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by Blue on 03/03/2017.
 */

public class OccupationActivity extends AppCompatActivity {
    private OccupationRU oru;
    private OccupationActivity moi = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.occupation_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("");
        list.add("ru1");
        list.add("ru2");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(0);
        oru = new OccupationRU(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private int firsttime=0;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(firsttime!=0){
                    oru.insertPoints(parentView.getItemAtPosition(position).toString(),moi);
                }
                else
                    firsttime++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        oru.makeGraph(this);
    }
}
