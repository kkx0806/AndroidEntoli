package com.bluetea.entolidemo.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.bluetea.entolidemo.R;

/**
 * Created by KKX on 8/23/16.
 */
public class BrightnessDialog extends Dialog {

    Activity activity;
    private SeekBar seekBarBrightness;
    public TextView tv_progress;

    public BrightnessDialog(Activity activity)   {
        super(activity);
        this.activity = activity;
    }

    public BrightnessDialog(Context context) {
        super(context);
    }

    public BrightnessDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BrightnessDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_brightness);

        TextView doneBt = (TextView) findViewById(R.id.done_bt);

        doneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        TextView cancelBt = (TextView) findViewById(R.id.cancel_bt);

        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_progress =(TextView) findViewById(R.id.tv_progress);

        final CustomProgressBar progressBrightness = (CustomProgressBar) findViewById(R.id.progressBarBrightness);
        seekBarBrightness = (SeekBar) findViewById(R.id.seekBarBrightness);
        progressBrightness.setSeekBar(seekBarBrightness);
        seekBarBrightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressBrightness.setProgress(progress);
                tv_progress.setText(String.valueOf(progress+"%"));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //down, up button
        ImageView downBrightnessBtn = (ImageView)findViewById(R.id.btn_progress_down);
        downBrightnessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressBrightness.getProgress();
                if(progress_val>0) {
                    progress_val = progress_val-1;
                }
                progressBrightness.setProgress(progress_val);
                tv_progress.setText(String.valueOf(progress_val)+"%");
            }
        });
        ImageView upIntensityBtn = (ImageView)findViewById(R.id.btn_progress_up);
        upIntensityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressBrightness.getProgress();
                if(progress_val<progressBrightness.getMax()) {
                    progress_val = progress_val+1;
                }
                progressBrightness.setProgress(progress_val);
                tv_progress.setText(String.valueOf(progress_val)+"%");
            }
        });
    }
}
