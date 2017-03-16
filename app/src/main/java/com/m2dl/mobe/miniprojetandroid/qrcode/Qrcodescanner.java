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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
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
        activateScan();

        // Initialisation des boutons.
       /* Button btnTakePhoto = (Button) findViewById(R.id.buttonPhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               /* Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                intent.putExtra("SCAN_FORMATS", "CODABAR");
                qrMoi.startActivityForResult(intent, 0);

            }
        });*/
    }

    public void activateScan(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.addExtra("SCAN_MODE", "QR_CODE_MODE");
        integrator.setPrompt("Qr Code Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                String format = data.getStringExtra("SCAN_RESULT_FORMAT");
                TextView tx = (TextView) findViewById(R.id.textView2);
                if(URLUtil.isValidUrl(contents)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contents));
                    startActivity(intent);
                    tx.setText(contents);

                }
                else{
                    tx.setText(contents+" -> url invalid (doit contenir http://)");
                }

            }
            if(resultCode == RESULT_CANCELED){
                TextView tx = (TextView) findViewById(R.id.textView2);
                tx.setText("bad scan");

            }
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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
                activateScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
