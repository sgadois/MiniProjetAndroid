package com.m2dl.mobe.miniprojetandroid.occupationru;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.m2dl.mobe.miniprojetandroid.R;

import org.osmdroid.views.overlay.OverlayItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by Blue on 03/03/2017.
 */

public class OccupationRU {

    private GraphView graph;
    private SimpleDateFormat hoursDateFormat;
    private SimpleDateFormat daysDateFormat;
    private SimpleDateFormat dbDateFormat;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public void insertPoints()  {

        //ArrayList<DataPoint> arrayDP = new ArrayList<>();
        //try {
        //TEMPORAIRE , à supprimer dès que l'on arrive à utiliser firebase
        //DatabaseReference today = myRef.child(dbDateFormat.format(new Date()))
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference("occupation").orderByChild("heure").getRef();
            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        ArrayList<DataPoint> arrayDP = new ArrayList<>();
                        for (DataSnapshot occupSnapshot : dataSnapshot.getChildren()) {
                            OccupationPonctuel op = occupSnapshot.getValue(OccupationPonctuel.class);

                                arrayDP.add(new DataPoint(hoursDateFormat.parse(op.getHeure()),op.getNombrePersonne()));


                        }
                        Date d = new Date((long)( arrayDP.get(arrayDP.size() - 1).getX()*1000));
                        if(d.getHours()<20){
                            Date actual = new Date(d.getTime());
                            for(int i = 0; actual.getHours() < 20; i++ ){
                                switch(actual.getHours()){
                                    case 12 :
                                        arrayDP.add( new DataPoint(hoursDateFormat.parse("12:00"),200));
                                    case 19 :
                                        arrayDP.add( new DataPoint(hoursDateFormat.parse("19:00"),200));
                                    case 20 :
                                        arrayDP.add( new DataPoint(hoursDateFormat.parse("20:00"),100));
                                }
                                actual.setHours(actual.getHours()+1);
                            }
                        }
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                        for(int i=0; i< arrayDP.size();i++){
                            series.appendData(arrayDP.get(i),true,10);
                        }
                        graph.removeAllSeries();
                        graph.addSeries(series);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "LoadOccupationPonctuel:onCancelled", databaseError.toException());
                }
            };
            database.addListenerForSingleValueEvent(postListener);

       /* myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<OccupationPonctuel> value = (ArrayList<OccupationPonctuel>) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });*/
          /*  arrayDP.add( new DataPoint(hoursDateFormat.parse(c));
            arrayDP.add( new DataPoint(hoursDateFormat.parse("15:58"), 0));
            arrayDP.add( new DataPoint(hoursDateFormat.parse("16:58"), 0));
            arrayDP.add( new DataPoint(hoursDateFormat.parse("17:58"), 100));
            arrayDP.add( new DataPoint(hoursDateFormat.parse("18:58"), 190));
            Date d = new Date((long)( arrayDP.get(arrayDP.size() - 1).getX()*1000));

            if(d.getHours()<20){
                Date actual = new Date(d.getTime());
                for(int i = 0; actual.getHours() < 20; i++ ){
                    switch(actual.getHours()){
                        case 12 :
                            arrayDP.add( new DataPoint(hoursDateFormat.parse("12:00"),200));
                        case 19 :
                            arrayDP.add( new DataPoint(hoursDateFormat.parse("19:00"),200));
                        case 20 :
                            arrayDP.add( new DataPoint(hoursDateFormat.parse("20:00"),100));
                        /*default :
                            arrayDP.add( new DataPoint(hoursDateFormat.parse(String.valueOf(actual.getHours())+":00"),0));
                    }
                    actual.setHours(actual.getHours()+1);
                }
            }*/
        /*} catch (ParseException e) {
            e.printStackTrace();
        }*/



        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0; i< arrayDP.size();i++){
            series.appendData(arrayDP.get(i),true,10);
        }
        graph.addSeries(series);*/


    }

    public void makeGraph(AppCompatActivity ma){

        hoursDateFormat = new SimpleDateFormat("HH:mm");
        daysDateFormat = new SimpleDateFormat("dd/MM");
        dbDateFormat = new SimpleDateFormat("dd:MM");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("occupation");
        initDonnees();
        graph = (GraphView) ma.findViewById(R.id.graph);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Temps au "+daysDateFormat.format(new Date()));

        graph.getGridLabelRenderer().setVerticalAxisTitle("Nombre de personnes");


        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(ma,hoursDateFormat));
        insertPoints();

        graph.getGridLabelRenderer().setTextSize(60f);
        graph.getGridLabelRenderer().reloadStyles();
        try {
            graph.getViewport().setMinX(hoursDateFormat.parse("09:00").getTime());
            graph.getViewport().setMaxX(hoursDateFormat.parse("22:00").getTime());
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(300);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling
    }

    public void initDonnees(){
        ArrayList<OccupationPonctuel> listOccup= new ArrayList<>();
        listOccup.add(new OccupationPonctuel("15:58", 0));
        listOccup.add(new OccupationPonctuel("16:58", 0));
        listOccup.add(new OccupationPonctuel("17:58", 100));
        listOccup.add(new OccupationPonctuel("15:58", 0));
        listOccup.add(new OccupationPonctuel("18:58", 190));
        myRef.setValue(listOccup);
    }

}
