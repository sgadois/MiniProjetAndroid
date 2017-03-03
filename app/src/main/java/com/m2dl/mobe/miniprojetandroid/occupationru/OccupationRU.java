package com.m2dl.mobe.miniprojetandroid.occupationru;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.m2dl.mobe.miniprojetandroid.MainActivity;
import com.m2dl.mobe.miniprojetandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Blue on 03/03/2017.
 */

public class OccupationRU {

    private GraphView graph;
    private SimpleDateFormat hoursDateFormat;

    public void insertPoints()  {

        //TEMPORAIRE , à supprimer dès que l'on arrive à utiliser firebase
        ArrayList<DataPoint> arrayDP = new ArrayList<>();
        try {
            arrayDP.add( new DataPoint(hoursDateFormat.parse("14:58"), 100));
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
                            arrayDP.add( new DataPoint(hoursDateFormat.parse(String.valueOf(actual.getHours())+":00"),0));*/
                    }
                    actual.setHours(actual.getHours()+1);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }



        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int i=0; i< arrayDP.size();i++){
            series.appendData(arrayDP.get(i),true,10);
        }
        graph.addSeries(series);


    }

    public void makeGraph(MainActivity ma){

        hoursDateFormat = new SimpleDateFormat("HH:mm");

        graph = (GraphView) ma.findViewById(R.id.graph);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Temps");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Nombre");

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
}
