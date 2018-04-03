package com.bluetea.entolidemo.telnet;

import android.os.Handler;

/**
 * Created by toltori on 6/7/16.
 */
public interface TelnetEventListener {
    public Handler getUiHandler();

    public void onConnected();

    public void onDisconnected();

    public void onLoginSuccess();

    public void onLoginFailure();

    public void onCommandResponse(String p_strResponse);
}
