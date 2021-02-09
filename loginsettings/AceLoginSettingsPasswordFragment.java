package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel.AceLoginSettingsViewModel;
import com.geico.mobile.databinding.NewLoginSettingsPasswordFragmentBinding;

public class AceLoginSettingsPasswordFragment extends AceBaseFragment<NewLoginSettingsPasswordFragmentBinding,
        AceLoginSettingsViewModel, AceLoginSettingsModel> implements AceLoginSettingsCallback {


    @AceConstantState.ActionBarCustomization
    @Override
    protected int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.FORM;
    }

    @NonNull
    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.createANewPassword;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().initializeModel(getViewTag());
    }

    @Override
    public void onPasswordFieldFocusChanged(boolean hasFocus){
        getViewModel().onPasswordFieldFocusChanged(hasFocus);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_password_fragment;
    }

    @NonNull
    @Override
    protected String getViewTag() {
        return ViewTag.LOGIN_SETTINGS_PASSWORD_FRAGMENT;
    }
}
