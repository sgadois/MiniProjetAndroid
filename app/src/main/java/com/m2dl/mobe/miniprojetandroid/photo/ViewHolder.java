package com.m2dl.mobe.miniprojetandroid.photo;

import android.widget.ImageView;

/**
 * Created by seb on 15/03/17.
 */

public class ViewHolder {

    /**
     * La vue de l'image dans la gridView.
     */
    private ImageView image;

    /**
     * La position de l'image dans la gridview.
     */
    private int position;

    public ViewHolder(ImageView image, int position) {
        this.image = image;
        this.position = position;
    }

    public ImageView getImage() {
        return image;
    }

    public int getPosition() {
        return position;
    }
}
