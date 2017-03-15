package com.m2dl.mobe.miniprojetandroid.photo;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.m2dl.mobe.miniprojetandroid.R;
import com.m2dl.mobe.miniprojetandroid.login.Login;

import java.io.File;

/**
 * Created by seb on 09/03/17.
 */

public class VisualizePhotoActivity extends AppCompatActivity {

    /**
     * La criticité de l'image.
     */
    private String criticite;

    /**
     * Chemin de la photo visualisé.
     */
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        ImageView imageView = (ImageView) findViewById(R.id.imagePhoto);
        imageView.setImageBitmap(null);

        // Récupération de l'intent extra.
        String extra = getIntent().getStringExtra("imagePath");
        if(extra != null) {
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
        Uri file = Uri.fromFile(new File(this.imagePath));
        Login.getInstance().signIn("sgadois@gmail.com", "azerty", this);

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imagesRef = storageRef.child("images/"+file.getLastPathSegment());
        imagesRef.putFile(file);
    }
}
