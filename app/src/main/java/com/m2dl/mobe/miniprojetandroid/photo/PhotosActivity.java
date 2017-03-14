package com.m2dl.mobe.miniprojetandroid.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.m2dl.mobe.miniprojetandroid.R;

/**
 * Created by seb on 14/03/17.
 */

public class PhotosActivity extends AppCompatActivity {

    /**
     * GridView contenant les photos de l'utilisateur.
     */
    private GridView gridview;

    /**
     * Adapteur pour la gridview.
     */
    private PhotoAdapter photoAdapter;

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
                // TODO: item clicked
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
                startActivity(new Intent(PhotosActivity.this, TakePhotoActivity.class ));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
