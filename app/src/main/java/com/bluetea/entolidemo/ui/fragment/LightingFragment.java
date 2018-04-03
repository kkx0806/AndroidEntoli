package com.bluetea.entolidemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.telnet.TelnetEndPoint2;
import com.bluetea.entolidemo.telnet.TelnetEventListener;
import com.bluetea.entolidemo.ui.views.CustomProgressBar;

/**
 * Created by KKX on 8/22/16.
 */
public class LightingFragment extends Fragment implements TelnetEventListener {

    static LightingFragment instance;

    //save selected button
    LinearLayout selBtRound1;
    LinearLayout selBtRound2;
    LinearLayout monitor1Bt, monitor2Bt;
    LinearLayout preset1Bt, preset2Bt, preset3Bt;
    View w_view;

    private static final String TAG = "LightingFragment";
    private TelnetEndPoint2 m_telnet;
    private int m_nInputId;

    public static LightingFragment getInstance()  {
        if (instance == null)
            instance = new LightingFragment();

        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        w_view = inflater.inflate(R.layout.fragment_lighting, container, false);
        initData();

        //*** For monitor Buttons
        monitor1Bt = (LinearLayout)w_view.findViewById(R.id.btn_monitor1);
        monitor1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, monitor1Bt);
                selBtRound1 = monitor1Bt;
                m_nInputId = 1;
                String w_strCmd = String.format("C%dON", m_nInputId);
                executeRemoteCommand(w_strCmd);
                final ToggleButton tempPowerOnBtn = (ToggleButton)w_view.findViewById(R.id.toggleButtonPower);
                tempPowerOnBtn.setChecked(true);
            }
        });
        monitor2Bt = (LinearLayout)w_view.findViewById(R.id.btn_monitor2);
        monitor2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound1, monitor2Bt);
                selBtRound1 = monitor2Bt;
                m_nInputId = 2;
                String w_strCmd = String.format("C%dON", m_nInputId);
                executeRemoteCommand(w_strCmd);
                final ToggleButton tempPowerOnBtn = (ToggleButton)w_view.findViewById(R.id.toggleButtonPower);
                tempPowerOnBtn.setChecked(true);
            }
        });
        selBtRound1 = monitor1Bt;
        monitor1Bt.setBackgroundResource(R.drawable.select_button_back);
        m_nInputId = 1;
        String w_strCmd = String.format("C%dON", m_nInputId);
        executeRemoteCommand(w_strCmd);
        final ToggleButton tempPowerOnBtn = (ToggleButton)w_view.findViewById(R.id.toggleButtonPower);
        tempPowerOnBtn.setChecked(true);

        //*** For PRESET Buttons
        preset1Bt = (LinearLayout) w_view.findViewById(R.id.btn_preset1);
        preset1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound2, preset1Bt);
                selBtRound2 = preset1Bt;
            }
        });
        preset2Bt = (LinearLayout) w_view.findViewById(R.id.btn_preset2);
        preset2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound2, preset2Bt);
                selBtRound2 = preset2Bt;
            }
        });
        preset3Bt = (LinearLayout) w_view.findViewById(R.id.btn_preset3);
        preset3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selBtRound2, preset3Bt);
                selBtRound2 = preset3Bt;
            }
        });

        //** Focus **
        final TextView tv_progressFocus =(TextView) w_view.findViewById(R.id.tv_progressFocus);
        final CustomProgressBar progressFocus = (CustomProgressBar) w_view.findViewById(R.id.progressBarFocus);
        SeekBar seekBarFocus = (SeekBar) w_view.findViewById(R.id.seekBarFocus);
        progressFocus.setSeekBar(seekBarFocus);
        seekBarFocus.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressFocus.setProgress(progress);
                tv_progressFocus.setText(String.valueOf(progress));
                String focus_str = "W";
                if(progress>2) focus_str = "S";
                String w_strCmd = String.format("C%dF%s", m_nInputId, focus_str);
                executeRemoteCommand(w_strCmd);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //down, up button
        ImageView downFocusBtn = (ImageView)w_view.findViewById(R.id.btn_focus_down);
        downFocusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressFocus.getProgress();
                if(progress_val>0) {
                    progress_val = progress_val-1;
                }
                progressFocus.setProgress(progress_val);
                tv_progressFocus.setText(String.valueOf(progress_val));
            }
        });
        ImageView upFocusBtn = (ImageView)w_view.findViewById(R.id.btn_focus_up);
        upFocusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressFocus.getProgress();
                if(progress_val<progressFocus.getMax()) {
                    progress_val = progress_val+1;
                }
                progressFocus.setProgress(progress_val);
                tv_progressFocus.setText(String.valueOf(progress_val));
            }
        });

        // ** Color **
        final TextView tv_progressColor =(TextView) w_view.findViewById(R.id.tv_progressColor);
        final CustomProgressBar progressColor = (CustomProgressBar) w_view.findViewById(R.id.progressBarColor);
        SeekBar seekBarColor = (SeekBar) w_view.findViewById(R.id.seekBarColor);
        progressColor.setSeekBar(seekBarColor);
        seekBarColor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressColor.setProgress(progress);
                tv_progressColor.setText(String.valueOf(progress));
                String w_strCmd = String.format("C%dT%d", m_nInputId, progress);
                executeRemoteCommand(w_strCmd);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //down, up button
        ImageView downColorBtn = (ImageView)w_view.findViewById(R.id.btn_color_down);
        downColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressColor.getProgress();
                if(progress_val>0) {
                    progress_val = progress_val-1;
                }
                progressColor.setProgress(progress_val);
                tv_progressColor.setText(String.valueOf(progress_val));
            }
        });
        ImageView upColorBtn = (ImageView)w_view.findViewById(R.id.btn_color_up);
        upColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressColor.getProgress();
                if(progress_val<progressColor.getMax()) {
                    progress_val = progress_val+1;
                }
                progressColor.setProgress(progress_val);
                tv_progressColor.setText(String.valueOf(progress_val));
            }
        });

        // ** Intensity **
        final TextView tv_progressIntensity =(TextView) w_view.findViewById(R.id.tv_progressIntensity);
        final CustomProgressBar progressIntensity = (CustomProgressBar) w_view.findViewById(R.id.progressBarIntensity);
        SeekBar seekBarIntensity = (SeekBar) w_view.findViewById(R.id.seekBarIntensity);
        progressIntensity.setSeekBar(seekBarIntensity);
        seekBarIntensity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressIntensity.setProgress(progress);
                tv_progressIntensity.setText(String.valueOf(progress*20)+"%");
                String w_strCmd = String.format("C%dI%d", m_nInputId, progress);
                executeRemoteCommand(w_strCmd);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //down, up button
        ImageView downIntensityBtn = (ImageView)w_view.findViewById(R.id.btn_intensity_down);
        downIntensityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressIntensity.getProgress();
                if(progress_val>0) {
                    progress_val = progress_val-1;
                }
                progressIntensity.setProgress(progress_val);
                tv_progressIntensity.setText(String.valueOf(progress_val*20)+"%");
            }
        });
        ImageView upIntensityBtn = (ImageView)w_view.findViewById(R.id.btn_intensity_up);
        upIntensityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer progress_val = progressIntensity.getProgress();
                if(progress_val<progressIntensity.getMax()) {
                    progress_val = progress_val+1;
                }
                progressIntensity.setProgress(progress_val);
                tv_progressIntensity.setText(String.valueOf(progress_val*20)+"%");
            }
        });

        //Power on, off
        final ToggleButton powerOnBtn = (ToggleButton)w_view.findViewById(R.id.toggleButtonPower);
        powerOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isChecked = ((ToggleButton) v).isChecked();
                String w_strCmd = String.format("C%dOF", m_nInputId);
                if(isChecked){
                    w_strCmd = String.format("C%dON", m_nInputId);
                }
                executeRemoteCommand(w_strCmd);
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

    private void initData() {
        m_telnet = TelnetEndPoint2.getInstance();
//        Intent w_i = getActivity().getIntent();
//        m_nInputId = w_i.getIntExtra("input_id", 1);
    }

    /**
     * Execute a remote command by separate thread, and shows the return data to the textview.
     *
     * @param p_strCommand A command string to execute.
     */
    private void executeRemoteCommand(String p_strCommand) {
        m_telnet.executeRemoteCommandAsync(p_strCommand);

        TextView commandTxt = (TextView)w_view.findViewById(R.id.txt_command_show);
        commandTxt.setText("Command: "+p_strCommand);
    }

    @Override
    public void onConnected() {
        Log.d(TAG, "TelnetEventListener.onConnected() called");
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "TelnetEventListener.onDisconnected() called");
    }

    @Override
    public void onLoginSuccess() {
        Log.d(TAG, "TelnetEventListener.onLoginSuccess() called");
    }

    @Override
    public void onLoginFailure() {
        Log.d(TAG, "TelnetEventListener.onLoginFailure() called");
    }

    @Override
    public Handler getUiHandler() {
        return new Handler();
    }

    @Override
    public void onCommandResponse(String p_strResponse) {
        Log.i(TAG, p_strResponse);
    }
}
