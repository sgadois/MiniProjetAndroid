package com.m2dl.mobe.miniprojetandroid.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by seb on 13/03/17.
 */

public class Login {

    /**
     * Tag de la classe.
     */
    private static final String TAG = "Login";

    /**
     * Instance de la classe d'authentification de firebase.
     */
    private static FirebaseAuth mAuth;

    /**
     * Listener d'authentification.
     */
    private static FirebaseAuth.AuthStateListener mAuthListener;

    /**
     * Instance de la classe Login.
     */
    private static Login instance = null;

    /**
     *  L'uid de l'utilisateur connecté.
     */
    private static String userUid;

    /**
     * Constructeur.
     */
    private Login() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    /**
     * Permet de s'authentifier à firebase.
     * @param email L'email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     * @param activity L'Activity dans lequel la méthode est utilisée.
     */
    public void signIn(String email, String password, final Activity activity) {
        if(!email.isEmpty() && !password.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                userUid = null;
                                Toast.makeText(activity, "Authentification échouée", Toast.LENGTH_SHORT).show();
                            } else {
                                userUid = task.getResult().getUser().getUid();
                                Toast.makeText(activity, "Authentification réussie ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    /**
     * Récupère l'id de l'utilisateur courant.
     * @return String l'id.
     */
    public String getTokenId() {
        return userUid;
    }

    /**
     * Récupération du singleton.
     * @return Login
     */
    public static Login getInstance() {
        if(instance == null) {
            instance = new Login();
        }
        return instance;
    }
}
