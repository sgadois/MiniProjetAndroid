package com.m2dl.mobe.miniprojetandroid.photo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.m2dl.mobe.miniprojetandroid.R;

import java.io.File;

/**
 * Created by seb on 14/03/17.
 */

public class PhotosActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    /**
     * GridView contenant les photos de l'utilisateur.
     */
    private GridView gridview;

    /**
     * Adapteur pour la gridview.
     */
    private PhotoAdapter photoAdapter;

    /**
     * Uri de la photo à visualiser.
     */
    private Uri imageUri;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        // Initialisation de la gridview.
        this.photoAdapter = new PhotoAdapter(this);
        this.gridview = (GridView) findViewById(R.id.grid_photos);
        this.gridview.setAdapter(this.photoAdapter);

        this.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                imageUri = photoAdapter.getItemUri(position);
                visualizePhoto();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.photoAdapter.notifyDataSetChanged();
        this.gridview.setAdapter(this.photoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_photo:
                takePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK) {
                    visualizePhoto();
                }
        }
    }

    /**
     * Permet de lancer un intent de prise de photo avec result .
     */
    public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String photoName = "Pic_" +
                getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles().length +
                ".jpg";
        File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), photoName);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        this.imageUri = Uri.fromFile(photo);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    /**
     * Permet de lancer l'activity de visualisation de photo.
     */
    public void visualizePhoto() {
        Intent intent = new Intent(this, VisualizePhotoActivity.class);
        intent.putExtra("imageUri", this.imageUri.toString());
        startActivity(intent);
    }



}
