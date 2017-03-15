package com.m2dl.mobe.miniprojetandroid.photo;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by seb on 14/03/17.
 */

public class PhotoAdapter extends BaseAdapter {

    /**
     * Contexte dans lequel l'adapter est instanciée.
     */
    private Context mContext;

    /**
     * Liste des Uris des images à afficher.
     */
    private List<File> data;

    public PhotoAdapter(Context c, List<File> data) {
        this.mContext = c;
        this.data = new ArrayList<>(data);
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
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
        new AsyncTaskRescaleImage(this.data.get(position).getAbsolutePath()).execute(holder);

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

    /**
     * Permet d'ajouter un fichier à la liste des fichiers de l'adapter.
     * @param file Le fichier a ajouter
     */
    public void addItem(File file) {
        this.data.add(file);
        notifyDataSetChanged();
    }
}
