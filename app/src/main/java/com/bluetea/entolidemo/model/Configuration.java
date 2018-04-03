package com.bluetea.entolidemo.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KKX on 6/20/16.
 * Configuration manager of the app.
 */
public class Configuration {

/*  +----------------------------------------------------------------------
    | Static members for singletone.
    +----------------------------------------------------------------------*/

    private static Configuration m_configuration = null;

    public static Configuration getInstance(Context p_context) {
        if (m_configuration == null) {
            m_configuration = new Configuration(p_context);
        }

        return m_configuration;
    }



/*  +----------------------------------------------------------------------
    | Private Members
    +----------------------------------------------------------------------*/

    private Context m_context;



/*  +----------------------------------------------------------------------
    | Public Members
    +----------------------------------------------------------------------*/

    public String HostName;
    public int PortNumber;



/*  +----------------------------------------------------------------------
    | Overrides
    +----------------------------------------------------------------------*/

    public Configuration(Context p_context) {
        m_context = p_context;
        load();
    }



/*  +----------------------------------------------------------------------
    | Interfaces
    +----------------------------------------------------------------------*/

    public boolean save() {
        if (m_context != null) {
            SharedPreferences w_sp = m_context.getSharedPreferences("Configuration", Activity.MODE_PRIVATE);
            SharedPreferences.Editor w_e = w_sp.edit();
            w_e.putString("HostName", HostName);
            w_e.putInt("PortNumber", PortNumber);
            w_e.commit();
            return true;
        }

        return false;
    }



/*  +----------------------------------------------------------------------
    | Helpers
    +----------------------------------------------------------------------*/

    private boolean load() {
        if (m_context != null) {
            SharedPreferences w_sp = m_context.getSharedPreferences("Configuration", Activity.MODE_PRIVATE);
            //IPS1000
//            HostName = w_sp.getString("HostName", "10.32.32.100");
//            PortNumber = w_sp.getInt("PortNumber", 9009);
            //Brainboxes
            HostName = w_sp.getString("HostName", "10.32.32.102");
            PortNumber = w_sp.getInt("PortNumber", 9001);
            return true;
        }

        return false;
    }

    /**
     * Created by KKX on 6/24/16.
     */
    public static class InputSources {
        private static String[] m_arrTitles = new String[] {
                "CVBS1", "CVBS2",
                "Y/C1", "Y/C2",
                "RGB1/DVI1", "RGB2/DVI2", "RGB3/DVI3", "RGB4/DVI4",
                "SDI1", "SDI2"
        };

        public static String[] getTitleArray() {
            return m_arrTitles;
        }

        public static String getTitle(int p_nId) {
            return p_nId < m_arrTitles.length ? m_arrTitles[p_nId] : "";
        }
    }
}
