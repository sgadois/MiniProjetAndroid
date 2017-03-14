package com.m2dl.mobe.miniprojetandroid.photo;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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

public class TakePhotoActivity extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    /**
     * Uri de la photo prise.
     */
    private Uri imageUri;

    /**
     * La criticité de l'image.
     */
    private String criticite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

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

        this.takePhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if(resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ImageView imageView = (ImageView) findViewById(R.id.imagePhoto);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
                        imageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT).show();
                        Log.e("Camera", e.toString());
                    }
                }
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

    public void uploadPhoto() {
        Uri file = Uri.fromFile(new File(this.imageUri.getPath()));
        Login.getInstance().signIn("sgadois@gmail.com", "azerty", this);

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        riversRef.putFile(file);
    }
}
