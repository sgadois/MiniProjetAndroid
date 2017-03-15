package com.m2dl.mobe.miniprojetandroid.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;

/**
 * Created by seb on 15/03/17.
 */

public class AsyncTaskRescaleImage extends AsyncTask<ViewHolder, Void, Bitmap> {

    /**
     * Conteneur de la vue d'une image de la gridview.
     */
    private ViewHolder viewHolder;

    /**
     * Contexte dans lequel la tache est instanciée.
     */
    private Context context;

    public AsyncTaskRescaleImage(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(ViewHolder... params) {
    viewHolder = params[0];
    Bitmap bm = optimizeImage(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                        .listFiles()[viewHolder.getPosition()]
                                        .getAbsolutePath());
    return bm;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        viewHolder.getImage().setImageBitmap(result);
    }

    /**
     * Permet d'optimiser l'espace utiliser par une image en la redimensionnant.
     * @param pathToImage   Le chemin de l'image.
     * @return Bitmap
     */
    private Bitmap optimizeImage(String pathToImage) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/75, photoH/75);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        return BitmapFactory.decodeFile(pathToImage, bmOptions);
    }
}

