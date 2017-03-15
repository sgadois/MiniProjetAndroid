package com.m2dl.mobe.miniprojetandroid.photo;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by seb on 15/03/17.
 */

public class AsyncTaskRescaleImage extends AsyncTask<ViewHolder, Void, Bitmap> {

    /**
     * Conteneur de la vue d'une image de la gridview.
     */
    private ViewHolder viewHolder;

    /**
     * Le chemin de l'image.
     */
    private String imagePath;

    public AsyncTaskRescaleImage(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    protected Bitmap doInBackground(ViewHolder... params) {
        viewHolder = params[0];
        Bitmap bm = ImageRescaling.decodeSampledBitmapFromResource(imagePath, 200, 200);
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        viewHolder.getImage().setImageBitmap(result);
    }
}

