package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

/**
 * The callbacks for the login settings components
 *
 * @author John Sung, Geico
 */
public interface AceLoginSettingsCallback {
    void onLoginSettingsRowClicked(int position);

    void onScreenUnlockSwitchToggled(boolean isSwitchOn);
}
