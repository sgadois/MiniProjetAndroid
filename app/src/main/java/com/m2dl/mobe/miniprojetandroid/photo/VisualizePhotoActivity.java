package com.m2dl.mobe.miniprojetandroid.photo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.m2dl.mobe.miniprojetandroid.R;
import com.m2dl.mobe.miniprojetandroid.login.Login;
import com.m2dl.mobe.miniprojetandroid.models.Photo;

import java.io.File;

/**
 * Created by seb on 09/03/17.
 */

public class VisualizePhotoActivity extends AppCompatActivity implements LocationListener {

    /**
     * La criticité de l'image.
     */
    private String criticite;

    /**
     * Chemin de la photo visualisé.
     */
    private String imagePath;

    /**
     * La localisation actuelle de l'utilisateur.
     */
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ImageView imageView = (ImageView) findViewById(R.id.imagePhoto);
        imageView.setImageBitmap(null);

        // Récupération de l'intent extra.
        String extra = getIntent().getStringExtra("imagePath");
        if (extra != null) {
            imagePath = getIntent().getStringExtra("imagePath");
            try {
                // Récupération de la taille de l'écran pour optimiser l'affichage de l'image.
                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;

                // Optimisation de l'espace mémoire occupé par l'image.
                Bitmap bitmap = ImageRescaling.decodeSampledBitmapFromResource(imagePath, height, width);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                Log.e("Camera", e.toString());
            }
        }

        // Initialisation du Spinner.
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.criticite, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                criticite = adapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Nothing to do.
            }
        });

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_upload, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upload:
                uploadPhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Permet d'uploader la photo sur firebase
     */
    public void uploadPhoto() {
        if(location == null) {
            Toast.makeText(this, "Wait for location", Toast.LENGTH_SHORT).show();
        } else {
            Uri file = Uri.fromFile(new File(this.imagePath));
            Login.getInstance().signIn("sgadois@gmail.com", "azerty", this);

            // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imagesRef = storageRef.child("images/" +
                                                            Login.getInstance().getTokenId() + "/" +
                                                            file.getLastPathSegment());
            imagesRef.putFile(file);

            // Create the reference in database
            Photo photo = new Photo(Login.getInstance().getTokenId(),
                                        file.getLastPathSegment(),
                                        this.location.getLatitude(),
                                        this.location.getLongitude(),
                                        criticite);
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
            databaseRef.child("images")
                    .child(photo.getIdUtilisateur())
                    .child(photo.getNom()
                            .replaceFirst("[.][^.]+$", ""))
                    .setValue(photo);
            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
