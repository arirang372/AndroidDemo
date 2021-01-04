package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.view;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;

/**
 * The callbacks for the user settings components
 *
 * @author John Sung, Geico
 */
public interface AceUserSettingsCallback {
    void onSkipButtonClicked();

    void onContinueButtonClicked();

    void onEditUserSettingsButtonClicked();

    void onFuelTypeSpinnerItemSelected(@NonNull AceOutOfGasTypeEnum selectedFuelType);

    void onVehicleSpinnerItemSelected(@NonNull AceUserProfileVehicle selectedVehicle, int position);

    void onVehicleColorSpinnerItemSelected(@NonNull AceVehicleColor selectedVehicleColor);
}
