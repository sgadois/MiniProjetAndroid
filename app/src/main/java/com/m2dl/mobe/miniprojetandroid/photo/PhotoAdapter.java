package com.m2dl.mobe.miniprojetandroid.photo;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;

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
        return this.mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles()[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        ViewHolder holder;

        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);

            convertView = imageView;

            holder = new ViewHolder(imageView, position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            imageView = (ImageView) convertView;
            ((ImageView)convertView).setImageBitmap(null);
        }

        // Lancement de la tache asynchrone de redimensionnement des images.
        new AsyncTaskRescaleImage(mContext).execute(holder);

        return imageView;
    }

    /**
     * Récupère l'Uri de l'image à la position demandée.
     * @param position La position demandée.
     * @return Uri L'Uri de l'image.
     */
    public Uri getItemUri(int position) {
        return Uri.fromFile((File) getItem(position));
    }


}
