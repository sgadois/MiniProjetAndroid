package com.m2dl.mobe.miniprojetandroid.geolocalisation;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.m2dl.mobe.miniprojetandroid.R;
import com.m2dl.mobe.miniprojetandroid.models.Batiment;

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
     * Tag de la classe.
     */
    private static final String TAG = "GeolocalisationActivity";

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

    /**
     * La liste des items à afficher sur la map.
     */
    private ArrayList<OverlayItem> items;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geolocalisation);

        myMap = (MapView) findViewById(R.id.map);
        centerOnMe = (Button) findViewById(R.id.button_center_on_me);
        items = new ArrayList<OverlayItem>();

        initialiseMap();
        initialiseMyPosition();
        retrieveDonnees();

        centerOnMe.setOnClickListener(new CenterOnMeListener(mLocationOverlay,
                                                                mapController,
                                                                getApplicationContext()));
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
     * Permet de récupérer les données de la BD.
     */
    private void retrieveDonnees() {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference("batiments");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot batimentSnapshot : dataSnapshot.getChildren()) {
                    Batiment bat = batimentSnapshot.getValue(Batiment.class);
                    items.add(new OverlayItem(bat.getNom(),
                                bat.getDescription(),
                                new GeoPoint(bat.getLatitude(),
                                                bat.getLongitude())));
                }
                addItemsToMap();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadBatiment:onCancelled", databaseError.toException());
            }
        };
        database.addListenerForSingleValueEvent(postListener);
    }

    /**
     * Ajoute les items récupérer de la BD sur la map.
     */
    public void addItemsToMap() {
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
