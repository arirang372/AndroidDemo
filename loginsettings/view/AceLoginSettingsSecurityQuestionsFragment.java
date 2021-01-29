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
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;
import com.geico.mobile.databinding.NewLoginSettingsSecurityQuestionsFragmentBinding;

public class AceLoginSettingsSecurityQuestionsFragment extends AceBaseFragment<NewLoginSettingsSecurityQuestionsFragmentBinding,
        AceLoginSettingsViewModel, AceLoginSettingsModel> implements AceLoginSettingsCallback {

    @Override
    protected @AceConstantState.ActionBarCustomization int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.FORM;
    }

    @LayoutRes
    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_security_questions_fragment;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().initializeModel(getViewTag());
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.loginSettingsUpdateSecurityQuestions;
    }

    @NonNull
    @Override
    protected String getViewTag() {
        return ViewTag.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT;
    }

    @Override
    public void onSecurityQuestionSelected(int securityQuestionPosition, @NonNull MitCodeDescriptionPair selectedQuestion) {
        getViewModel().onSecurityQuestionSelected(securityQuestionPosition, selectedQuestion);
    }
}
