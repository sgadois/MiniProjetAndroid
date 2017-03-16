package com.m2dl.mobe.miniprojetandroid.configuration;

import android.app.AlertDialog;
import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by seb on 16/03/17.
 */

public class SeekBarBrightnessPreference extends SeekBarPreference {
    public SeekBarBrightnessPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {

        if (shouldPersist()) {

            mValue = mSeekBar.getProgress();
            persistInt(mSeekBar.getProgress());
            callChangeListener(Integer.valueOf(mSeekBar.getProgress()));

            Settings.System.putFloat(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, (float) mValue);
        }

        ((AlertDialog) getDialog()).dismiss();
    }
}
