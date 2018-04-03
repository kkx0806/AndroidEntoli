package com.bluetea.entolidemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.ui.views.BrightnessDialog;

/**
 * Created by KKX on 8/22/16.
 */
public class BrightnessFragment extends Fragment {

    static BrightnessFragment instance;

    //save selected button
    RelativeLayout selBtRound1;
    LinearLayout selBtRound2;
    RelativeLayout selBtRound3;
    RelativeLayout camera1Bt, camera2Bt, camera3Bt, camera4Bt, camera5Bt;
    LinearLayout monitor1Bt, monitor2Bt, monitor3Bt;
    RelativeLayout preset1Bt, preset2Bt, preset3Bt, preset4Bt, preset5Bt;

    public static BrightnessFragment getInstance()  {
        if (instance == null)
            instance = new BrightnessFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View w_view = inflater.inflate(R.layout.fragment_brightness, container, false);
        final LinearLayout monitor3_sub_btns = (LinearLayout)w_view.findViewById(R.id.layout_monitor3_btns);

        camera1Bt = (RelativeLayout)w_view.findViewById(R.id.btn_camera1);
        camera1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, camera1Bt);
                selBtRound1 = camera1Bt;
            }
        });
        camera2Bt = (RelativeLayout)w_view.findViewById(R.id.btn_camera2);
        camera2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, camera2Bt);
                selBtRound1 = camera2Bt;
            }
        });
        camera3Bt = (RelativeLayout)w_view.findViewById(R.id.btn_camera3);
        camera3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, camera3Bt);
                selBtRound1 = camera3Bt;
            }
        });
        camera4Bt = (RelativeLayout)w_view.findViewById(R.id.btn_camera4);
        camera4Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, camera4Bt);
                selBtRound1 = camera4Bt;
            }
        });
        camera5Bt = (RelativeLayout)w_view.findViewById(R.id.btn_camera5);
        camera5Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, camera5Bt);
                selBtRound1 = camera5Bt;
            }
        });

        //inital buttons select
        selBtRound1 = camera1Bt;
        camera1Bt.setBackgroundResource(R.drawable.select_button_back);

        //*** For monitor Buttons
        monitor1Bt = (LinearLayout)w_view.findViewById(R.id.btn_monitor1);
        monitor1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound2, monitor1Bt);
                selBtRound2 = monitor1Bt;
                monitor3_sub_btns.setVisibility(View.GONE);
            }
        });
        monitor2Bt = (LinearLayout)w_view.findViewById(R.id.btn_monitor2);
        monitor2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound2, monitor2Bt);
                selBtRound2 = monitor2Bt;

                monitor3_sub_btns.setVisibility(View.GONE);
           }
        });
        monitor3Bt = (LinearLayout)w_view.findViewById(R.id.btn_monitor3);

        monitor3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound2, monitor3Bt);
                selBtRound2 = monitor3Bt;
                monitor3_sub_btns.setVisibility(View.VISIBLE);
            }
        });

        //Brightness dialog show
        RelativeLayout monitor3_brightnessBt = (RelativeLayout)w_view.findViewById(R.id.btn_monitor3_brightness);
        monitor3_brightnessBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrightnessDialog brightnessDialog = new BrightnessDialog(getActivity());
                brightnessDialog.show();
            }
        });

        //*** For PRESET Buttons
        preset1Bt = (RelativeLayout) w_view.findViewById(R.id.btn_preset1);
        preset1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound3, preset1Bt);
                selBtRound3 = preset1Bt;
            }
        });
        preset2Bt = (RelativeLayout) w_view.findViewById(R.id.btn_preset2);
        preset2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound3, preset2Bt);
                selBtRound3 = preset2Bt;
            }
        });
        preset3Bt = (RelativeLayout) w_view.findViewById(R.id.btn_preset3);
        preset3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound3, preset3Bt);
                selBtRound3 = preset3Bt;
            }
        });
        preset4Bt = (RelativeLayout) w_view.findViewById(R.id.btn_preset4);
        preset4Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound3, preset4Bt);
                selBtRound3 = preset4Bt;
            }
        });
        preset5Bt = (RelativeLayout) w_view.findViewById(R.id.btn_preset5);
        preset5Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound3, preset5Bt);
                selBtRound3 = preset5Bt;
            }
        });

        return w_view;
    }


    private void selectBtConf(View selectedBt, View selectableBt)   {
        if(selectedBt!=null) {
            selectedBt.setBackgroundResource(R.drawable.button_back);
        }
        selectableBt.setBackgroundResource(R.drawable.select_button_back);
    }

}
