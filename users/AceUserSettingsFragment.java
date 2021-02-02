package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel.AceUsersViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.view.AceUserSettingsCallback;
import com.geico.mobile.databinding.UserSettingsFragmentBinding;

/**
 * Fragment for User settings page where the user can edit his phone number or his primary vehicle information
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsFragment extends AceBaseFragment<UserSettingsFragmentBinding, AceUsersViewModel,
        AceUsersModel> implements AceUserSettingsCallback {

    private OnBackPressedCallback createOnBackPressedCallback() {
        return new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getViewModel().onSkipButtonClicked();
            }
        };
    }

    @AceConstantState.ActionBarCustomization
    @Override
    protected int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.NO_ACTION_BUTTONS;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.yourSettings;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_settings_fragment;
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        hideKeyboard();
    }

    @Override
    public void onContinueButtonClicked() {
        hideKeyboard();
        getViewModel().onContinueButtonClicked();
    }

    @Override
    public void onEditUserSettingsButtonClicked() {
        getViewModel().onEditUserSettingsButtonClicked();
    }

    @Override
    public void onFuelTypeSpinnerItemSelected(@NonNull AceOutOfGasTypeEnum selectedFuelType) {
        getViewModel().onFuelTypeSpinnerItemSelected(selectedFuelType);
    }

    @Override
    public void onSkipButtonClicked() {
        hideKeyboard();
        getViewModel().onSkipButtonClicked();
    }

    @Override
    public void onVehicleColorSpinnerItemSelected(@NonNull AceVehicleColor selectedVehicleColor) {
        getViewModel().onVehicleColorSpinnerItemSelected(selectedVehicleColor);
    }

    @Override
    public void onVehicleSpinnerItemSelected(@NonNull AceUserProfileVehicle selectedVehicle, int position) {
        getViewModel().onVehicleSpinnerItemSelected(selectedVehicle, position);
    }

    @Override
    protected void setOnBackPressedCallback() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), createOnBackPressedCallback());
    }

    @NonNull
    @Override
    protected String getViewTag() {
        return ViewTag.USER_SETTINGS_FRAGMENT;
    }
}
