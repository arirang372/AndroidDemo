package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel.AceLoginSettingsViewModel;
import com.geico.mobile.databinding.NewLoginSettingsActivityBinding;

/**
 * Activity for the login settings component.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsActivity extends AceBaseActivity<NewLoginSettingsActivityBinding,
        AceLoginSettingsViewModel> {

    @Override
    protected int getNavigationHostFragmentId() {
        return R.id.loginSettingsNavigationHostFragment;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_activity;
    }

    @Override
    protected Class<AceLoginSettingsViewModel> getViewModelClass() {
        return AceLoginSettingsViewModel.class;
    }

    @Override
    protected boolean shouldSuppressEmittedStateChanges() {
        return true;
    }
}
