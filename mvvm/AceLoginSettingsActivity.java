package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

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

    @LayoutRes
    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_activity;
    }

    @IdRes
    @Override
    protected int getNavigationHostFragmentId() {
        return R.id.loginSettingsNavigationHostFragment;
    }

    @Override
    protected Class<AceLoginSettingsViewModel> getViewModelClass() {
        return AceLoginSettingsViewModel.class;
    }

    @Override
    protected boolean shouldSuppressEmittedStateChanges() {
        return true;
    }

    @Override
    public void onFormButtonClicked(View view) {
        getViewModel().onFormButtonClicked();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getViewModel().resetSelectedSectionItemValue();
        return super.onOptionsItemSelected(item);
    }
}
