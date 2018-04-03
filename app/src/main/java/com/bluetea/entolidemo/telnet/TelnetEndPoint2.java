package com.bluetea.entolidemo.telnet;

import android.os.Handler;

import mobiletelnetsdk.feng.gao.TelnetAPIs;
import mobiletelnetsdk.feng.gao.TelnetNotification;

/**
 * Created by toltori on 6/4/16.
 * <p/>
 * Singletone class to manage Telnet channel and command send/recv.
 */
public class TelnetEndPoint2 implements TelnetNotification {

/*  +----------------------------------------------------------------------
    | Static members for singletone.
    +----------------------------------------------------------------------*/

    private static TelnetEndPoint2 m_endpoint = null;

    public static TelnetEndPoint2 getInstance() {
        if (m_endpoint == null) {
            System.loadLibrary("mobiletelnetsdkjni");

            m_endpoint = new TelnetEndPoint2();
            TelnetAPIs.TelnetSetDataHandler(m_endpoint);
        }

        return m_endpoint;
    }



/*  +----------------------------------------------------------------------
    | Member variables
    +----------------------------------------------------------------------*/

    private static final String LOGIN_SUCCESS_TOKEN = "ControlOR Simple Virtual Terminal";
    private static final String LOGIN_FAILURE_TOKEN = "Login failure";

    private TelnetEventListener m_listener;
    private Handler m_uiHandler;
    private Thread m_thPumpMessage = null;
    private String m_strUsername, m_strPassword;


/*  +----------------------------------------------------------------------
    | Public members
    +----------------------------------------------------------------------*/

    /**
     * Check the SSH connection.
     */
    public boolean isConnected() {
        return m_thPumpMessage != null;
    }

    /**
     * Set the telnet event listener instance.
     *
     * @param p_listener : Telnet event listener instance.
     */
    public void setListener(TelnetEventListener p_listener) {
        m_listener = p_listener;
        m_uiHandler = m_listener.getUiHandler();
    }

    /**
     * Connect and login to the Telnet server asynchronously.
     *
     * @param p_strHostname Hostname.
     * @param p_nPort       Port number.
     * @param p_listener    : Telnet event listener object.
     */
    public boolean connectAsync(final String p_strHostname, final int p_nPort, String p_strUsername,
                                String p_strPassword, TelnetEventListener p_listener) {
        if (m_thPumpMessage == null) {
            //
            // Save parameters.
            //
            m_listener = p_listener;
            m_uiHandler = m_listener.getUiHandler();
            m_strUsername = p_strUsername;
            m_strPassword = p_strPassword;

            //
            // Start telnet message pumping.
            //
            TelnetAPIs.TelnetInit(p_strHostname, p_nPort);
            m_thPumpMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    TelnetAPIs.TelnetPumpMessage();
                }
            });
            m_thPumpMessage.start();
            return true;
        }
        return false;
    }

    /**
     * Logout and disconnect from the SSH server.
     */
    public boolean disconnect() {
        if (m_thPumpMessage != null) {
            //
            // Logout from the controller.
            //
            TelnetAPIs.TelnetInternalCmd(1, ""); // CMD_LOGOUT.

            //
            // Stop telnet channel.
            //
            m_thPumpMessage = null;
            return true;
        }
        return false;
    }

    /**
     * Execute a single command via Telnet channel.
     *
     * @param p_strCommand : Command to execute.
     * @return : Command execute result string.
     */
    public boolean executeRemoteCommandAsync(final String p_strCommand) {
        if (m_thPumpMessage == null) {
            return false;
        }

        TelnetAPIs.TelnetSend(p_strCommand);

        return true;
    }

    @Override
    public void notificationHandler(final String p_strResponse) {
        if (p_strResponse.contains("Connecting to")) {
            return;
        }

        if (p_strResponse.contains("Username: ")) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TelnetAPIs.TelnetSend(m_strUsername);
        } else if (p_strResponse.equals("Password: ")) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            TelnetAPIs.TelnetSend(m_strPassword);
        } else if (p_strResponse.contains(LOGIN_SUCCESS_TOKEN)) {
            //
            // Login success!
            //
            if (m_listener != null) {
                m_uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        m_listener.onLoginSuccess();
                    }
                });
            }
        } else if (p_strResponse.contains(LOGIN_FAILURE_TOKEN) || p_strResponse.contains("Failed to connect")) {
            //
            // Login failure!
            //
            if (m_listener != null) {
                m_uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        m_listener.onLoginFailure();
                    }
                });
            }
        } else if (!p_strResponse.equals("\r\n# ")) {
            //
            // General command response.
            //
            if (m_listener != null) {
                m_uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        m_listener.onCommandResponse(p_strResponse);
                    }
                });
            }
        }
    }

    @Override
    public void notificationConnectionStatus(int i) {
        if (i == 1) { // Connected.
            if (m_listener != null) {
                m_uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        m_listener.onConnected();
                    }
                });
            }
            TelnetAPIs.TelnetSend("hello"); // Send dumy message to request user login.
        }
    }
}
