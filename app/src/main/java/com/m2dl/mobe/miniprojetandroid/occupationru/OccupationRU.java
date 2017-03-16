package com.m2dl.mobe.miniprojetandroid.occupationru;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.m2dl.mobe.miniprojetandroid.R;
import com.m2dl.mobe.miniprojetandroid.models.Batiment;
import com.m2dl.mobe.miniprojetandroid.models.OccupationPonctuel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;
import static android.content.ContentValues.TAG;
import static com.m2dl.mobe.miniprojetandroid.R.id.graph;

/**
 * Created by Blue on 03/03/2017.
 */

public class OccupationRU {

    private HashMap<String,LineGraphSeries<DataPoint>> graphs;
    private SimpleDateFormat hoursDateFormat;
    private SimpleDateFormat daysDateFormat;
    private SimpleDateFormat dbDateFormat;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    public OccupationRU(final AppCompatActivity ma){
        graphs = new HashMap<>();
        hoursDateFormat = new SimpleDateFormat("HH:mm");
        daysDateFormat = new SimpleDateFormat("dd/MM");
        dbDateFormat = new SimpleDateFormat("dd:MM");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("occupation");

        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("batiments").getRef();
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Spinner spinner = (Spinner) ma.findViewById(R.id.spinner);
                    String ru  = spinner.getSelectedItem().toString();

                    for (DataSnapshot occupSnapshot : dataSnapshot.getChildren()) {

                        ArrayList<DataPoint> arrayDP = new ArrayList<>();
                        Batiment b = new Batiment();
                        b.setNom((String) occupSnapshot.child("nom").getValue());

                        for(DataSnapshot op : occupSnapshot.child("occupationPonctuels").getChildren()){
                            String heure = op.getKey().substring(0,2) + ':' + op.getKey().substring(2,4);
                            int val= Integer.parseInt( op.getValue().toString());
                            Date time = hoursDateFormat.parse(heure);
                            arrayDP.add(new DataPoint(time, val));
                        }
                        Date d = new Date((long)( arrayDP.get(arrayDP.size() - 1).getX()*1000));
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
                        for(int i=0; i< arrayDP.size();i++){
                            series.appendData(arrayDP.get(i),true,10);
                        }
                        graphs.remove((String) occupSnapshot.child("nom").getValue());
                        graphs.put((String) occupSnapshot.child("nom").getValue(),series);


                    }
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

    }

    public void insertPoints(String ru, AppCompatActivity ma)  {
        //Spinner spinner = (Spinner) ma.findViewById(R.id.spinner);
        //String ru  = spinner.getSelectedItem().toString();
        GraphView graph = (GraphView) ma.findViewById(R.id.graph);

       if(ru!="" && graphs.size() > 0 && !graphs.get(ru).isEmpty()) {
            graph.removeAllSeries();
            graph.addSeries(graphs.get(ru));
        }
    }

    public void makeGraph(AppCompatActivity ma){


        GraphView graph = (GraphView) ma.findViewById(R.id.graph);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Temps au " + daysDateFormat.format(new Date()));

        graph.getGridLabelRenderer().setVerticalAxisTitle("Nombre de personnes");


        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(ma, hoursDateFormat));


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
}
