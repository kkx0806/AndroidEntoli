package com.bluetea.entolidemo.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bluetea.entolidemo.R;
import com.bluetea.entolidemo.model.Configuration;
import com.bluetea.entolidemo.ui.listener.MonitorControlListener;


/**
 * Main page's single monitor fragment class
 */
public class MonitorFragment extends Fragment {

/*  +----------------------------------------------------------------------
    | Member variables
    +----------------------------------------------------------------------*/

    private static final String TAG = "MonitorFragment";
    private int m_nMonitorId;
    private MonitorControlListener m_listener;

    private boolean m_bLayoutSpinnerFirstSelection = true;
    private boolean m_bPipSizeSpinnerFirstSelection = true;
    private boolean m_bPipPositionSpinnerFirstSelection = true;
    private boolean m_bMainSpinnerFirstSelection = true;
    private boolean m_bSubSpinnerFirstSelection = true;



/*  +----------------------------------------------------------------------
    | UI controls
    +----------------------------------------------------------------------*/

    private TextView m_txtTitle;

    private ViewGroup[] m_arrLayouts;
    private TextView[] m_arrTxtMains;
    private TextView[] m_arrTxtSubs;

    private Spinner m_spLayout;

    private LinearLayout m_llPipParamter;
    private Spinner m_spPipSize;
    private Spinner m_spPipPosition;

    private Spinner m_spMonitorMain;
    private Spinner m_spMonitorSub;



/*  +----------------------------------------------------------------------
    | Overrides
    +----------------------------------------------------------------------*/

    public MonitorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View w_view = inflater.inflate(R.layout.fragment_monitor, container, false);
        initUI(w_view);
        return w_view;
    }



/*  +----------------------------------------------------------------------
    | Event handlers
    +----------------------------------------------------------------------*/

    private void onLayoutSpinnerItemClick(int p_nIndex) {
        //
        // Update UI.
        //
        m_arrLayouts[p_nIndex].bringToFront();
        if (p_nIndex == 1) {
            m_llPipParamter.setVisibility(View.VISIBLE);
        } else {
            m_llPipParamter.setVisibility(View.GONE);
        }

        if (p_nIndex == 0) {
            m_spMonitorSub.setVisibility(View.GONE);
        } else {
            m_spMonitorSub.setVisibility(View.VISIBLE);
        }

        //
        // Delegate the layout change event handling.
        //
        if (m_listener != null) {
            m_listener.onLayoutChanged(m_nMonitorId, p_nIndex);
        }
    }

    private void onPipSizeSpinnerItemClick(int p_nPipSizeId) {
        //
        // TODO: Update UI.
        //

        //
        // Delegate the pip size change event handling.
        //
        if (m_listener != null) {
            m_listener.onPipSizeChanged(m_nMonitorId, p_nPipSizeId);
        }
    }

    private void onPipPositionSpinnerItemClick(int p_nPipPositionId) {
        //
        // TODO: Update UI.
        //

        //
        // Delegate the pip position change event handling.
        //
        if (m_listener != null) {
            m_listener.onPipPositionChanged(m_nMonitorId, p_nPipPositionId);
        }
    }

    private void onMainSpinnerItemClick(int p_nIndex) {
        int w_nCurrentLayoutId = m_spLayout.getSelectedItemPosition();

        //
        // Update UI.
        //
        m_arrTxtMains[w_nCurrentLayoutId].setText(Configuration.InputSources.getTitle(p_nIndex));

        //
        // Delegate the main change event handling.
        //
        if (m_listener != null) {
            m_listener.onMainChanged(m_nMonitorId, p_nIndex);
        }
    }

    private void onSubSpinnerItemClick(int p_nIndex) {
        int w_nCurrentLayoutId = m_spLayout.getSelectedItemPosition();

        //
        // Update UI.
        //
        if (m_arrTxtSubs[w_nCurrentLayoutId] != null) {
            m_arrTxtSubs[w_nCurrentLayoutId].setText(Configuration.InputSources.getTitle(p_nIndex));
        }

        //
        // Delegate the sub change event handling.
        //
        if (m_listener != null) {
            m_listener.onSubChanged(m_nMonitorId, p_nIndex);
        }
    }



/*  +----------------------------------------------------------------------
    | Helpers
    +----------------------------------------------------------------------*/

    public void setMonitorId(int p_nMonitorId) {
        m_nMonitorId = p_nMonitorId;
        m_txtTitle.setText(String.format("Monitor %d", m_nMonitorId));
    }

    public void setMonitorControlListener(MonitorControlListener p_listener) {
        m_listener = p_listener;
    }

    private void initUI(View p_view) {
        m_txtTitle = (TextView) p_view.findViewById(R.id.txt_title);

        m_arrLayouts = new ViewGroup[4];
        m_arrLayouts[0] = (ViewGroup) p_view.findViewById(R.id.rl_layout_single);
        m_arrLayouts[1] = (ViewGroup) p_view.findViewById(R.id.rl_layout_pip);
        m_arrLayouts[2] = (ViewGroup) p_view.findViewById(R.id.ll_layout_pop);
        m_arrLayouts[3] = (ViewGroup) p_view.findViewById(R.id.ll_layout_pbp);

        m_arrTxtMains = new TextView[4];
        m_arrTxtSubs = new TextView[4];
        m_arrTxtMains[0] = (TextView) p_view.findViewById(R.id.txt_monitor_main1);
        m_arrTxtMains[1] = (TextView) p_view.findViewById(R.id.txt_monitor_main2);
        m_arrTxtMains[2] = (TextView) p_view.findViewById(R.id.txt_monitor_main3);
        m_arrTxtMains[3] = (TextView) p_view.findViewById(R.id.txt_monitor_main4);
        m_arrTxtSubs[0] = null; // There is no sub in Single mode.
        m_arrTxtSubs[1] = (TextView) p_view.findViewById(R.id.txt_monitor_sub2);
        m_arrTxtSubs[2] = (TextView) p_view.findViewById(R.id.txt_monitor_sub3);
        m_arrTxtSubs[3] = (TextView) p_view.findViewById(R.id.txt_monitor_sub4);

        m_spLayout = (Spinner) p_view.findViewById(R.id.sp_layout);
        m_llPipParamter = (LinearLayout) p_view.findViewById(R.id.ll_pip_parameter);
        m_spPipSize = (Spinner) p_view.findViewById(R.id.sp_pip_size);
        m_spPipPosition = (Spinner) p_view.findViewById(R.id.sp_pip_position);

        m_spMonitorMain = (Spinner) p_view.findViewById(R.id.sp_monitor_main);
        m_spMonitorSub = (Spinner) p_view.findViewById(R.id.sp_monitor_sub);

        String[] w_arrLayouts = new String[]{
                "Single", "PIP", "POP", "PBP"
        };
        ArrayAdapter<String> w_layoutAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, w_arrLayouts);
        m_spLayout.setAdapter(w_layoutAdapter);

        String[] w_arrPipSizes = new String[]{
                "Small", "Medium", "Large"
        };
        ArrayAdapter<String> w_sizeAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, w_arrPipSizes);
        m_spPipSize.setAdapter(w_sizeAdapter);

        String[] w_arrPipPositions = new String[]{
                "Top Left", "Top Right", "Bottom Left", "Bottom Right"
        };
        ArrayAdapter<String> w_positionAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, w_arrPipPositions);
        m_spPipPosition.setAdapter(w_positionAdapter);

        ArrayAdapter<String> w_inputSourcesAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, Configuration.InputSources.getTitleArray());
        m_spMonitorMain.setAdapter(w_inputSourcesAdapter);
        m_spMonitorSub.setAdapter(w_inputSourcesAdapter);

        m_spLayout.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (!m_bLayoutSpinnerFirstSelection)
                    onLayoutSpinnerItemClick(index);
                m_bLayoutSpinnerFirstSelection = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        m_spPipSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (!m_bPipSizeSpinnerFirstSelection)
                    onPipSizeSpinnerItemClick(index);
                m_bPipSizeSpinnerFirstSelection = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        m_spPipPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (!m_bPipPositionSpinnerFirstSelection)
                    onPipPositionSpinnerItemClick(index);
                m_bPipPositionSpinnerFirstSelection = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        m_spMonitorMain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (!m_bMainSpinnerFirstSelection)
                    onMainSpinnerItemClick(index);
                m_bMainSpinnerFirstSelection = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        m_spMonitorSub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int index, long l) {
                if (!m_bSubSpinnerFirstSelection)
                    onSubSpinnerItemClick(index);
                m_bSubSpinnerFirstSelection = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
