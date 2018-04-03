package com.bluetea.entolidemo.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.telnet.TelnetEndPoint2;
import com.bluetea.entolidemo.telnet.TelnetEventListener;
import com.bluetea.entolidemo.ui.fragment.MonitorFragment;
import com.bluetea.entolidemo.ui.listener.MonitorControlListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TelnetEventListener {

/*  +----------------------------------------------------------------------
    | Member variables
    +----------------------------------------------------------------------*/

    private static final String TAG = "MainActivity";
    private TelnetEndPoint2 m_telnet;
    private Handler m_hLiveHeartBeat = new Handler() {
        public void handleMessage(Message msg) {
            executeRemoteCommandInBg("IAMALIVE 20");
            m_hLiveHeartBeat.sendEmptyMessageDelayed(0, 15000);
        }
    };



/*  +----------------------------------------------------------------------
    | UI controls
    +----------------------------------------------------------------------*/

    private ProgressDialog m_dlgProgress;
    private Button[] m_arrInputBtns;
    private MonitorFragment[] m_arrMonitorFragments;



/*  +----------------------------------------------------------------------
    | Overrides
    +----------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        // Initialize the UI.
        //
        initUI();
        initData();
    }

    @Override
    protected void onDestroy() {
        m_hLiveHeartBeat.removeMessages(0);
        super.onDestroy();
    }



/*  +----------------------------------------------------------------------
    | Event handlers
    +----------------------------------------------------------------------*/

    public void onInputBtn_Click(View view) {
        int w_nInputId = 0;
        for (int i = 0; i < m_arrInputBtns.length; i++) {
            if (m_arrInputBtns[i] == view) {
                w_nInputId = i;
                break;
            }
        }

        Intent w_i = new Intent(this, InputControlActivity.class);
        w_i.putExtra("input_id", w_nInputId);
        startActivity(w_i);
    }

    public MonitorControlListener m_monitorControlListener = new MonitorControlListener() {
        @Override
        public void onLayoutChanged(int p_nMonitorId, int p_nLayoutId) {
            //
            // Set monitors layout.
            //
            String w_strCmd = "";
            switch (p_nLayoutId) {
                case 0:
                    w_strCmd = String.format(Locale.ENGLISH, "PIP %dM 0", p_nMonitorId + 1);
                    break;
                case 1:
                    w_strCmd = String.format(Locale.ENGLISH, "PIP %dM 1", p_nMonitorId + 1);
                    break;
                case 2:
                    w_strCmd = String.format(Locale.ENGLISH, "POP %dM 1", p_nMonitorId + 1);
                    break;
                case 3:
                    w_strCmd = String.format(Locale.ENGLISH, "PBP %dM 1", p_nMonitorId + 1);
                    break;
                default:
            }
            executeRemoteCommand(w_strCmd);
        }

        @Override
        public void onPipSizeChanged(int p_nMonitorId, int p_nPipSizeId) {
            //
            // Set monitors pip sub size.
            //
            String w_strCmd = String.format(Locale.ENGLISH, "PIPSIZE %dS %d", p_nMonitorId + 1, p_nPipSizeId);
            executeRemoteCommand(w_strCmd);
        }

        @Override
        public void onPipPositionChanged(int p_nMonitorId, int p_nPipPositionId) {
            //
            // Set monitors pip sub position.
            //
            String w_strCmd = String.format(Locale.ENGLISH, "PIPPOS %dS %d", p_nMonitorId + 1, p_nPipPositionId);
            executeRemoteCommand(w_strCmd);
        }

        @Override
        public void onMainChanged(int p_nMonitorId, int p_nInputId) {
            //
            // Set monitor's main input channel.
            //
            String w_strCmd = String.format(Locale.ENGLISH, "INPUTSEL %dM %d", p_nMonitorId + 1, p_nInputId);
            executeRemoteCommand(w_strCmd);
        }

        @Override
        public void onSubChanged(int p_nMonitorId, int p_nInputId) {
            //
            // Set monitor's subordinate input channel.
            //
            String w_strCmd = String.format(Locale.ENGLISH, "INPUTSEL %dS %d", p_nMonitorId + 1, p_nInputId);
            executeRemoteCommand(w_strCmd);
        }
    };



/*  +----------------------------------------------------------------------
    | Helpers
    +----------------------------------------------------------------------*/

    private void initUI() {
        m_arrInputBtns = new Button[10];
        m_arrInputBtns[0] = (Button) findViewById(R.id.btn_input1);
        m_arrInputBtns[1] = (Button) findViewById(R.id.btn_input2);
        m_arrInputBtns[2] = (Button) findViewById(R.id.btn_input3);
        m_arrInputBtns[3] = (Button) findViewById(R.id.btn_input4);
        m_arrInputBtns[4] = (Button) findViewById(R.id.btn_input5);
        m_arrInputBtns[5] = (Button) findViewById(R.id.btn_input6);
        m_arrInputBtns[6] = (Button) findViewById(R.id.btn_input7);
        m_arrInputBtns[7] = (Button) findViewById(R.id.btn_input8);
        m_arrInputBtns[8] = (Button) findViewById(R.id.btn_input9);
        m_arrInputBtns[9] = (Button) findViewById(R.id.btn_input10);

        m_arrMonitorFragments = new MonitorFragment[5];
        m_arrMonitorFragments[0] = (MonitorFragment) getFragmentManager().findFragmentById(R.id.fr_monitor1);
        m_arrMonitorFragments[1] = (MonitorFragment) getFragmentManager().findFragmentById(R.id.fr_monitor2);
        m_arrMonitorFragments[2] = (MonitorFragment) getFragmentManager().findFragmentById(R.id.fr_monitor3);
        m_arrMonitorFragments[3] = (MonitorFragment) getFragmentManager().findFragmentById(R.id.fr_monitor4);
        m_arrMonitorFragments[4] = (MonitorFragment) getFragmentManager().findFragmentById(R.id.fr_monitor5);

        for (int i = 0; i < m_arrMonitorFragments.length; i++) {
            m_arrMonitorFragments[i].setMonitorId(i);
            m_arrMonitorFragments[i].setMonitorControlListener(m_monitorControlListener);
        }
    }

    private void initData() {
        m_telnet = TelnetEndPoint2.getInstance();
        m_telnet.setListener(this);
        m_hLiveHeartBeat.sendEmptyMessage(0);
    }

    /**
     * Execute a remote command by separate thread, and shows the return data to the textview.
     *
     * @param p_strCommand A command string to execute.
     */
    private void executeRemoteCommand(String p_strCommand) {
        Log.i(TAG, "Send: " + p_strCommand);
        /*
        if (m_dlgProgress == null) {
            m_dlgProgress = ProgressDialog.show(this, getString(R.string.msg_exe_cmd),
                    getString(R.string.msg_please_wait));
        }
        */
        m_telnet.executeRemoteCommandAsync(p_strCommand);
    }

    private void executeRemoteCommandInBg(String p_strCommand) {
        Log.i(TAG, "Send: " + p_strCommand);
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
        Log.i(TAG, "Receive: " + p_strResponse);

        if (m_dlgProgress != null) {
            m_dlgProgress.hide();
            m_dlgProgress = null;
        }
    }
}
