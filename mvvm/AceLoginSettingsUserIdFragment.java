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
import com.geico.mobile.databinding.NewLoginSettingsUserIdFragmentBinding;

/**
 * Fragment for Login Settings User Id page where the user can edit user id
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsUserIdFragment extends AceBaseFragment<NewLoginSettingsUserIdFragmentBinding,
        AceLoginSettingsViewModel, AceLoginSettingsModel> {

    @Override
    protected @AceConstantState.ActionBarCustomization int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.FORM;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.loginSettingsUpdateUserId;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().initializeModel(ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT);
    }

    @LayoutRes
    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_login_settings_user_id_fragment;
    }

    @Override
    protected String getViewTag() {
        return ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT;
    }
}
