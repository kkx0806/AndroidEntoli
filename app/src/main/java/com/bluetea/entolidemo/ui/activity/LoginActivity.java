package com.bluetea.entolidemo.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.telnet.TelnetEndPoint2;
import com.bluetea.entolidemo.telnet.TelnetEventListener;
import com.bluetea.entolidemo.model.Configuration;

public class LoginActivity extends AppCompatActivity implements TelnetEventListener {

/*  +----------------------------------------------------------------------
    | Member variables
    +----------------------------------------------------------------------*/

    private static final String TAG = "LoginActivity";
    private TelnetEndPoint2 m_telnet;



/*  +----------------------------------------------------------------------
    | UI controls
    +----------------------------------------------------------------------*/

    private LinearLayout m_llLogin;
    private EditText m_txtUsername;
    private EditText m_txtPassword;

    private ProgressDialog m_dlgProgress;



/*  +----------------------------------------------------------------------
    | Overrides
    +----------------------------------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initUI();
        startActivity(new Intent(this, HelpActivity.class));
    }



/*  +----------------------------------------------------------------------
    | Event handlers
    +----------------------------------------------------------------------*/

    public void onLoginBtn_Click(View view) {
        startActivity(new Intent(this, HomeActivity.class));
//        return;

        if (!m_telnet.isConnected()) {

            // Input verify.

//            if (m_txtUsername.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Please input username!", Toast.LENGTH_LONG).show();
//                return;
//            }
//            if (m_txtPassword.getText().toString().isEmpty()) {
//                Toast.makeText(this, "Please input password!", Toast.LENGTH_LONG).show();
//                return;
//            }

            final int w_nPort = Configuration.getInstance(this).PortNumber;
            final String w_strHostname = Configuration.getInstance(this).HostName;
            final String w_strUsername = m_txtUsername.getText().toString();
            final String w_strPassword = m_txtPassword.getText().toString();

            //
            // Connect to the remote telnet server asynchronously.
            //
            m_dlgProgress = ProgressDialog.show(this, "Connecting to server", "Please wait for a moment...");
            boolean w_bSuccess = m_telnet.connectAsync(w_strHostname, w_nPort, w_strUsername, w_strPassword, this);
            if (!w_bSuccess) {
                Toast.makeText(this, "Already connected!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this, "Already connected!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, HomeActivity.class));
        }
    }

    public void onTurnOffBtn_Click(View view) {
        Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_SHORT).show();
    }



/*  +----------------------------------------------------------------------
    | Helpers
    +----------------------------------------------------------------------*/

    private void initData() {
        m_telnet = TelnetEndPoint2.getInstance();
    }

    private void initUI() {
        m_llLogin = (LinearLayout) findViewById(R.id.ll_login);
        m_txtUsername = (EditText) findViewById(R.id.txt_username);
        m_txtPassword = (EditText) findViewById(R.id.txt_password);
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
        if (m_dlgProgress != null) {
            m_dlgProgress.hide();
        }
        //startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onDisconnected() {
        Log.d(TAG, "TelnetEventListener.onDisconnected() called");
    }

    @Override
    public void onLoginSuccess() {
        Log.d(TAG, "TelnetEventListener.onLoginSuccess() called");
        if (m_dlgProgress != null) {
            m_dlgProgress.hide();
        }

        //startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, HomeActivity.class));
    }

    @Override
    public void onLoginFailure() {
        Log.d(TAG, "TelnetEventListener.onLoginFailure() called");
        if (m_dlgProgress != null) {
            m_dlgProgress.hide();
        }
        Toast.makeText(this, "Login Failure!", Toast.LENGTH_SHORT).show();
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
