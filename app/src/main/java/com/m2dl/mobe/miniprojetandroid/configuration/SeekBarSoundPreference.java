package com.m2dl.mobe.miniprojetandroid.configuration;

import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by seb on 16/03/17.
 */

public class SeekBarSoundPreference extends SeekBarPreference {

    public SeekBarSoundPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onClick(View v) {

        if (shouldPersist()) {

            mValue = mSeekBar.getProgress();
            persistInt(mSeekBar.getProgress());
            callChangeListener(Integer.valueOf(mSeekBar.getProgress()));
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_RING, Integer.valueOf(mSeekBar.getProgress())*ToneGenerator.MAX_VOLUME/Integer.valueOf(mSeekBar.getMax()));
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP,200);
            Settings.System.putFloat(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, (float) mValue);
        }

        ((AlertDialog) getDialog()).dismiss();
    }
}
