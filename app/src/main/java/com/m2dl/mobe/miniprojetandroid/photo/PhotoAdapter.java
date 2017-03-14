package com.m2dl.mobe.miniprojetandroid.photo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by seb on 14/03/17.
 */

public class PhotoAdapter extends BaseAdapter {

    private Context mContext;

    public PhotoAdapter(Context c) {
        this.mContext = c;
    }

    @Override
    public int getCount() {
        return this.mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles().length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap image = optimizeImage(this.mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles()[position].getAbsolutePath());
        imageView.setImageBitmap(image);
        return imageView;
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
