package com.m2dl.mobe.miniprojetandroid.photo;

import android.app.Activity;
import android.net.Uri;
import android.view.View;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.m2dl.mobe.miniprojetandroid.login.Login;

import java.io.File;

/**
 * Created by seb on 14/03/17.
 */

public class UploadImageListener implements View.OnClickListener {

    /**
     * L'activité dans lequel le listener est instancié.
     */
    private Activity parent;

    /**
     * L'URI de l'image à uploader.
     */
    private Uri imageUri;

    /**
     * Le niveau de criticité de l'image.
     */
    private String criticite;

    /**
     * Constructeur
     * @param a Activity parent.
     */
    public UploadImageListener(Activity a) {
        this.parent = a;
    }

    @Override
    public void onClick(View v) {
        Uri file = Uri.fromFile(new File(this.imageUri.getPath()));
        Login.getInstance().signIn("sgadois@gmail.com", "azerty", this.parent);

        // Create a storage reference from our app
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        riversRef.putFile(file);
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setCriticite(String criticite) {
        this.criticite = criticite;
    }
}
