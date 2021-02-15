package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel.AceLoginSettingsViewModel;
import com.geico.mobile.databinding.NewLoginSettingsPasswordFragmentBinding;

/**
 * Fragment for Login Settings update password page where the user can update the password and password hint.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsPasswordFragment extends AceBaseFragment<NewLoginSettingsPasswordFragmentBinding,
        AceLoginSettingsViewModel, AceLoginSettingsModel> implements AceLoginSettingsCallback {

    @AceConstantState.ActionBarCustomization
    @Override
    protected int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.FORM;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.createANewPassword;
    }

    @LayoutRes
    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_password_fragment;
    }

    @NonNull
    @Override
    protected String getViewTag() {
        return ViewTag.LOGIN_SETTINGS_PASSWORD_FRAGMENT;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().initializeModel(getViewTag());
    }

    @Override
    public void onPasswordFieldFocusChanged(boolean hasFocus) {
        getViewModel().onPasswordFieldFocusChanged(hasFocus);
    }

    @Override
    public void onTermsOfUseLinkClicked() {
        getViewModel().onTermsOfUseLinkClicked();
    }
}
