package com.bluetea.entolidemo.ui.listener;

/**
 * Created by KKX on 6/24/16.
 */
public interface MonitorControlListener {
    public void onLayoutChanged(int p_nMonitorId, int p_nLayoutId);
    public void onPipSizeChanged(int p_nMonitorId, int p_nPipSizeId);
    public void onPipPositionChanged(int p_nMonitorId, int p_nPipPositionId);
    public void onMainChanged(int p_nMonitorId, int p_nInputId);
    public void onSubChanged(int p_nMonitorId, int p_nInputId);
}
