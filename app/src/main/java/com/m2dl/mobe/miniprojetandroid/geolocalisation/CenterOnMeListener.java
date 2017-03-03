package com.m2dl.mobe.miniprojetandroid.geolocalisation;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Created by seb on 03/03/17.
 */

public class CenterOnMeListener implements View.OnClickListener {

    private MyLocationNewOverlay myLocationNewOverlay;

    private IMapController mapController;

    private Context context;

    public CenterOnMeListener(MyLocationNewOverlay myLocationNewOverlay, IMapController mapController, Context context) {
        this.myLocationNewOverlay = myLocationNewOverlay;
        this.mapController = mapController;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (myLocationNewOverlay.getMyLocation() != null){
            mapController.setCenter(myLocationNewOverlay.getMyLocation());
            mapController.setZoom(19);
        } else {
            Toast.makeText(context, "Recherche de la position", Toast.LENGTH_SHORT).show();
        }
    }
}
