package com.bluetea.entolidemo.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.telnet.TelnetEndPoint2;
import com.bluetea.entolidemo.telnet.TelnetEventListener;
import com.bluetea.entolidemo.ui.fragment.BrightnessFragment;
import com.bluetea.entolidemo.ui.fragment.CameraFragment;
import com.bluetea.entolidemo.ui.fragment.LightingFragment;

/**
 * Created by KKX on 8/20/16.
 */
public class HomeActivity extends AppCompatActivity  implements TelnetEventListener {

    private static final String TAG = "HomeActivity";
    private TelnetEndPoint2 m_telnet;

    //save selected button
    RelativeLayout selMainButton;
    RelativeLayout main1Bt, main2Bt, main3Bt, main4Bt;

    @Override
    protected void onCreate(Bundle savedStatus) {
        super.onCreate(savedStatus);
        setContentView(R.layout.activity_home);

        replaceFragment(BrightnessFragment.getInstance());

        ImageView homeBt = (ImageView)findViewById(R.id.home_ic);
        homeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(BrightnessFragment.getInstance());
            }
        });

        main1Bt = (RelativeLayout)findViewById(R.id.main_btn1);
        main1Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selMainButton, main1Bt);
                selMainButton = main1Bt;
                replaceFragment(LightingFragment.getInstance());
            }
        });
        main2Bt = (RelativeLayout)findViewById(R.id.main_btn2);
        main2Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selMainButton, main2Bt);
                selMainButton = main2Bt;
                replaceFragment(CameraFragment.getInstance());
            }
        });
        main3Bt = (RelativeLayout)findViewById(R.id.main_btn3);
        main3Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selMainButton, main3Bt);
                selMainButton = main3Bt;
            }
        });
        main4Bt = (RelativeLayout)findViewById(R.id.main_btn4);
        main4Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtConf(selMainButton, main4Bt);
                selMainButton = main4Bt;
            }
        });

        //inital buttons select
//        selMainButton = main1Bt;
//        main1Bt.setBackgroundResource(R.drawable.select_button_back);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.frameView, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void selectBtConf(View selectedBt, View selectableBt)   {
        if(selectedBt!=null) {
            selectedBt.setBackgroundResource(0);
        }
        selectableBt.setBackgroundResource(R.drawable.select_button_back);
    }

    /**
     * Execute a remote command by separate thread, and shows the return data to the textview.
     *
     * @param p_strCommand A command string to execute.
     */
    private void executeRemoteCommand(String p_strCommand) {
        m_telnet.executeRemoteCommandAsync(p_strCommand);
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
    }
}
