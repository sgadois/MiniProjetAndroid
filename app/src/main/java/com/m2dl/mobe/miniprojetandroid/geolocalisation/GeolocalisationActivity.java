package com.m2dl.mobe.miniprojetandroid.geolocalisation;

import android.app.Activity;
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

    /**
     * La map.
     */
    private MapView myMap;

    /**
     *  Le bouton de recentrage sur la position de l'utilisateur.
     */
    private Button centerOnMe;

    /**
     *  Le controller de la map.
     */
    private IMapController mapController;

    /**
     *  L'overlay de la position de l'utilisateur.
     */
    private MyLocationNewOverlay mLocationOverlay;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        initialiseMap();
        initialiseMyPosition();
        initialiseDonnees();
    }

    public void onResume() {
        super.onResume();
    }

    /**
     * Permet d'initialiser la configuration de la map.
     */
    private void initialiseMap() {
        myMap.setTileSource(TileSourceFactory.MAPNIK);

        myMap.setBuiltInZoomControls(true);
        myMap.setMultiTouchControls(true);

        mapController = myMap.getController();
        mapController.setZoom(17);


        GeoPoint startPoint = new GeoPoint(43.562547, 1.468853);
        mapController.setCenter(startPoint);
    }

    /**
     * Permet d'initialiser le curseur de position.
     */
    private void initialiseMyPosition() {
        mLocationOverlay =
                new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), myMap);
        mLocationOverlay.enableMyLocation();
        myMap.getOverlays().add(mLocationOverlay);
    }

    /**
     * Permet de placer les données de la BD sur la map.
     */
    private void initialiseDonnees() {
        ArrayList<OverlayItem> items = new ArrayList<OverlayItem>();
        items.add(new OverlayItem("RU2", "Restaurant universitaire 2", new GeoPoint(43.560978, 1.471735)));
        items.add(new OverlayItem("RU1", "Restaurant universitaire 1", new GeoPoint(43.562254, 1.463346)));

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
                }, getApplicationContext());
        mOverlay.setFocusItemsOnTap(true);

        myMap.getOverlays().add(mOverlay);
    }
}
