package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;


import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel.AceLoginSettingsViewModel;
import com.geico.mobile.databinding.NewLoginSettingsFragmentBinding;

/**
 * Fragment for Login settings page where the user can see the list of the items(e.g. user id, password, and security questions)
 * user can edit
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsFragment extends AceBaseFragment<NewLoginSettingsFragmentBinding, AceLoginSettingsViewModel,
        AceLoginSettingsModel> implements AceLoginSettingsCallback {

    @Override
    protected @AceConstantState.ActionBarCustomization int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.STANDARD;
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.loginSettings;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_fragment;
    }

    @Override
    public void onLoginSettingsRowClicked(int position) {
        getViewModel().onLoginSettingsRowClicked(position);
    }

    @Override
    public void onScreenUnlockSwitchToggled(boolean isSwitchOn) {
        getViewModel().onScreenUnlockSwitchToggled(isSwitchOn);
    }
}
