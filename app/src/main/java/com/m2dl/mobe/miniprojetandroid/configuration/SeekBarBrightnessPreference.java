package com.m2dl.mobe.miniprojetandroid.configuration;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by seb on 16/03/17.
 */

public class SeekBarBrightnessPreference extends SeekBarPreference {
    @RequiresApi(api = Build.VERSION_CODES.M)
    public SeekBarBrightnessPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public void onClick(View v) {

        if (shouldPersist()) {

            mValue = mSeekBar.getProgress();
            persistInt(mSeekBar.getProgress());
            callChangeListener(Integer.valueOf(mSeekBar.getProgress()));
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            Settings.System.putInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, (mValue)*255/100);

        }

        ((AlertDialog) getDialog()).dismiss();
    }
}
