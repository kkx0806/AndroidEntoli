package com.bluetea.entolidemo.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.telnet.TelnetEndPoint2;
import com.bluetea.entolidemo.telnet.TelnetEventListener;

public class InputControlActivity extends AppCompatActivity implements TelnetEventListener {

/*  +----------------------------------------------------------------------
    | Member variables
    +----------------------------------------------------------------------*/

    private static final String TAG = "InputControlActivity";
    private TelnetEndPoint2 m_telnet;
    private int m_nInputId;
    private int m_nPanH; // [0~100]
    private int m_nPanV; // [0~100]


/*  +----------------------------------------------------------------------
    | UI controls
    +----------------------------------------------------------------------*/

    private SeekBar m_sbZoom;
    private TextView m_txtZoom;
    private SeekBar m_sbContrast;
    private TextView m_txtContrast;
    private SeekBar m_sbBrightness;
    private TextView m_txtBrightness;
    private SeekBar m_sbRed;
    private TextView m_txtRed;
    private SeekBar m_sbGreen;
    private TextView m_txtGreen;
    private SeekBar m_sbBlue;
    private TextView m_txtBlue;
    private RadioGroup m_rgGammas;
    private RadioButton m_rdo1_8, m_rdo2_0, m_rdo2_2, m_rdo2_4, m_rdo2_6, m_rdoVideo;

    private ProgressDialog m_dlgProgress;



/*  +----------------------------------------------------------------------
    | Overrides
    +----------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_control);

        //
        // Initialize the UI.
        //
        initUI();
        initData();
    }


/*  +----------------------------------------------------------------------
    | Event handlers
    +----------------------------------------------------------------------*/

    public void onDefaultBtn_Click(View view) {

    }

    public void onLeftBtn_Click(View view) {
        //
        // Pan left.
        //
        m_nPanH -= 1;
        if (m_nPanH < 0)
            m_nPanH = 0;
        String w_strCmd = String.format("PANH IN%d %d", m_nInputId, m_nPanH);
        executeRemoteCommand(w_strCmd);
    }

    public void onRightBtn_Click(View view) {
        //
        // Pan right.
        //
        m_nPanH += 1;
        if (m_nPanH > 100)
            m_nPanH = 100;
        String w_strCmd = String.format("PANH IN%d %d", m_nInputId, m_nPanH);
        executeRemoteCommand(w_strCmd);
    }

    public void onUpBtn_Click(View view) {
        //
        // Pan up.
        //
        m_nPanV += 1;
        if (m_nPanV > 100)
            m_nPanV = 100;
        String w_strCmd = String.format("PANV IN%d %d", m_nInputId, m_nPanH);
        executeRemoteCommand(w_strCmd);
    }

    public void onDownBtn_Click(View view) {
        //
        // Pan down.
        //
        m_nPanV -= 1;
        if (m_nPanV < 0)
            m_nPanV = 0;
        String w_strCmd = String.format("PANV IN%d %d", m_nInputId, m_nPanH);
        executeRemoteCommand(w_strCmd);
    }

    public void onCommand1Btn_Click(View view) {
        final String w_strCommand = "INPUTSEL 1M 0"; // Camera1 -> Monitor1's Main.
        if (!m_telnet.isConnected()) {
            Toast.makeText(this, "Please connect to the server!", Toast.LENGTH_LONG).show();
            return;
        }

        executeRemoteCommand(w_strCommand);
    }

    public void onPanLeftBtn_Click(View view) {
        final String w_strCommand = "PANH 1M -10"; // Monitor1's Main Camera Pan Left of 10.
        if (!m_telnet.isConnected()) {
            Toast.makeText(this, "Please connect to the server!", Toast.LENGTH_LONG).show();
            return;
        }

        executeRemoteCommand(w_strCommand);
    }

    public void onPanRightBtn_Click(View view) {
        final String w_strCommand = "PANH 1M 10"; // Monitor1's Main Camera Pan Right of 10.
        if (!m_telnet.isConnected()) {
            Toast.makeText(this, "Please connect to the server!", Toast.LENGTH_LONG).show();
            return;
        }

        executeRemoteCommand(w_strCommand);
    }



/*  +----------------------------------------------------------------------
    | Helpers
    +----------------------------------------------------------------------*/

    private void initUI() {
        m_sbZoom = (SeekBar) findViewById(R.id.sb_zoom);
        m_txtZoom = (TextView) findViewById(R.id.txt_zoom);
        m_sbContrast = (SeekBar) findViewById(R.id.sb_contrast);
        m_txtContrast = (TextView) findViewById(R.id.txt_contrast);
        m_sbBrightness = (SeekBar) findViewById(R.id.sb_brightness);
        m_txtBrightness = (TextView) findViewById(R.id.txt_brightness);
        m_sbRed = (SeekBar) findViewById(R.id.sb_red);
        m_txtRed = (TextView) findViewById(R.id.txt_red);
        m_sbGreen = (SeekBar) findViewById(R.id.sb_green);
        m_txtGreen = (TextView) findViewById(R.id.txt_green);
        m_sbBlue = (SeekBar) findViewById(R.id.sb_blue);
        m_txtBlue = (TextView) findViewById(R.id.txt_blue);
        m_rdo1_8 = (RadioButton) findViewById(R.id.rdo_1_8);
        m_rdo2_0 = (RadioButton) findViewById(R.id.rdo_2_0);
        m_rdo2_2 = (RadioButton) findViewById(R.id.rdo_2_2);
        m_rdo2_4 = (RadioButton) findViewById(R.id.rdo_2_4);
        m_rdoVideo = (RadioButton) findViewById(R.id.rdo_video);
        m_rgGammas = (RadioGroup) findViewById(R.id.rg_gammas);

        m_sbZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                    // Zoom control.
                    //
                    m_txtZoom.setText(String.format("%d", progress));
                    String w_strCmd = String.format("ZOOM IN%d %d", m_nInputId, progress);
                    executeRemoteCommand(w_strCmd);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        m_sbContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                    // Contrast control.
                    //
                    m_txtContrast.setText(String.format("%d", progress));
                    String w_strCmd = String.format("CONTRAST IN%d %d", m_nInputId, progress);
                    executeRemoteCommand(w_strCmd);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        m_sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                    // Brightness control.
                    //
                    m_txtBrightness.setText(String.format("%d", progress));
                    String w_strCmd = String.format("BRIGHT IN%d %d", m_nInputId, progress);
                    executeRemoteCommand(w_strCmd);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        m_sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                    // Red control.
                    //
                    m_txtRed.setText(String.format("%d", progress));
                    String w_strCmd = String.format("CRED IN%d %d", m_nInputId, progress);
                    executeRemoteCommand(w_strCmd);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        m_sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                    // Green control.
                    //
                    m_txtGreen.setText(String.format("%d", progress));
                    String w_strCmd = String.format("CGREEN IN%d %d", m_nInputId, progress);
                    executeRemoteCommand(w_strCmd);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        m_sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //
                    // Blue control.
                    //
                    m_txtBlue.setText(String.format("%d", progress));
                    String w_strCmd = String.format("CBLUE IN%d %d", m_nInputId, progress);
                    executeRemoteCommand(w_strCmd);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void initData() {
        m_telnet = TelnetEndPoint2.getInstance();
        Intent w_i = getIntent();
        m_nInputId = w_i.getIntExtra("input_id", 0);
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
