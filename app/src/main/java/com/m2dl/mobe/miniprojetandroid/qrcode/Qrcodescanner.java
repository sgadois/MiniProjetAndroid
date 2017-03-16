package com.m2dl.mobe.miniprojetandroid.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.m2dl.mobe.miniprojetandroid.MainActivity;
import com.m2dl.mobe.miniprojetandroid.R;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Blue on 03/03/2017.
 */

public class Qrcodescanner extends AppCompatActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private Uri imageUri;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_main);
        final Activity activity = this;

        // Initialisation des boutons.
        Button btnTakePhoto = (Button) findViewById(R.id.buttonPhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                intent.putExtra("SCAN_FORMATS", "CODABAR");
                qrMoi.startActivityForResult(intent, 0);*/
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.addExtra("SCAN_MODE", "QR_CODE_MODE");
                integrator.setPrompt("Qr Code Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    public void activateScan(View v){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                if (new String(contents).contains("ru1")) {
                   // Log.d("SEARCH_EAN","I am here");
                    //setContentView(R.layout.qrcode_main);
                    String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                    TextView tx = (TextView) findViewById(R.id.textView2);
                    tx.setText(contents);
                    // Handle successful scan

                    Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
                    startActivity(intent);

                    /*Intent myIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(contents));
                    startActivity(myIntent1);*/
                }
            }
            if(resultCode == RESULT_CANCELED){
                //Log.d("SEARCH_EAN","bad scan");
                //setContentView(R.layout.qrcode_main);
                TextView tx = (TextView) findViewById(R.id.textView2);
                tx.setText("bad scan");
                //super.onActivityResult(requestCode, resultCode, data);
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * Permet de lancer un intent avec result de prise de photo.
     */
    /*public void takePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        String photoName = "Pic_" +
                getExternalFilesDir(Environment.DIRECTORY_PICTURES).listFiles().length +
                ".jpg";
        File photo = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), photoName);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        imageUri = Uri.fromFile(photo);

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }*/
}
