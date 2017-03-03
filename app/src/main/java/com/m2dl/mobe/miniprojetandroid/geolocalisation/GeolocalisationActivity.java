package com.m2dl.mobe.miniprojetandroid.geolocalisation;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.m2dl.mobe.miniprojetandroid.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

/**
 * Created by seb on 03/03/17.
 */

public class GeolocalisationActivity extends Activity {

    private MapView myMap;

    private Button centerOnMe;

    private IMapController mapController;

    private MyLocationNewOverlay mLocationOverlay;


    private ArrayList<OverlayItem> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();
        setContentView(R.layout.activity_geolocalisation);

        myMap = (MapView) findViewById(R.id.map);
        centerOnMe = (Button) findViewById(R.id.button_center_on_me);

        centerOnMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mLocationOverlay.getMyLocation() != null){
                    mapController.setCenter(mLocationOverlay.getMyLocation());
                    mapController.setZoom(19);
                }
            }
        });

        myMap.setTileSource(TileSourceFactory.MAPNIK);

        myMap.setBuiltInZoomControls(true);
        myMap.setMultiTouchControls(true);

        mapController = myMap.getController();
        mapController.setZoom(17);


        GeoPoint startPoint = new GeoPoint(43.562547, 1.468853);
        mapController.setCenter(startPoint);
        mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(context), myMap);
        mLocationOverlay.enableMyLocation();
        myMap.getOverlays().add(mLocationOverlay);

        //your items
        items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("RU2", "Restaurant universitaire 2", new GeoPoint(43.560978, 1.471735))); // Lat/Lon decimal degrees
        items.add(new OverlayItem("RU1", "Restaurant universitaire 1", new GeoPoint(43.562254, 1.463346))); // Lat/Lon decimal degrees

        //the overlay
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        //do something
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, context);
        mOverlay.setFocusItemsOnTap(true);

        myMap.getOverlays().add(mOverlay);
    }

    public void onResume() {
        super.onResume();
    }
}
