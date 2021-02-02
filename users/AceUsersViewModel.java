package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceLifecycleObserverFactory;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository.AceUsersRepository;

/**
 * ViewModel for handling UI tasks for the User profile component
 *
 * @author John Sung, Geico
 */
public class AceUsersViewModel extends AceBaseViewModel<AceUsersModel, AceUsersRepository> implements
        AceLifecycleObserverFactory<AceUsersLifecycleObserver> {

    public AceUsersViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    public AceUsersLifecycleObserver createLifecycleObserver() {
        return new AceUsersLifecycleObserver(this);
    }

    public void considerDisplayingDeviceUnlockDialog() {
        getRepository().considerDisplayingDeviceUnlockDialog();
    }

    @NonNull
    @Override
    protected AceUsersRepository createRepository(@NonNull Application application) {
        return new AceUsersRepository(application);
    }

    public void initializeModel() {
        getRepository().initializeModel();
    }

    public void onBackPressed() {
        getRepository().handleBackPressedAction();
    }

    public void onContinueButtonClicked() {
        getRepository().handleContinueButtonAction();
        onBackPressed();
    }

    public void onEditButtonClicked() {
        getRepository().handleEditButtonClickAction();
    }

    public void onEditUserSettingsButtonClicked() {
        getRepository().handleEditUserSettingsButtonAction();
    }

    public void onFuelTypeSpinnerItemSelected(@NonNull AceOutOfGasTypeEnum selectedFuelType) {
        getRepository().handleSelectedFuelTypeSpinnerItem(selectedFuelType);
    }

    public void onSkipButtonClicked() {
        getRepository().handleSkipButtonAction();
    }

    public void onUsersListItemRowClicked(@NonNull AceUserProfilePerson profilePerson) {
        getRepository().handleListItemRowClickAction(profilePerson);
    }

    public void onVehicleColorSpinnerItemSelected(@NonNull AceVehicleColor selectedVehicleColor) {
        getRepository().handleSelectedVehicleColorAction(selectedVehicleColor);
    }

    public void onVehicleSpinnerItemSelected(@NonNull AceUserProfileVehicle selectedVehicle, int position) {
        getRepository().handleSelectedVehicleAction(selectedVehicle, position);
    }

    public void recordMetricsForCompletingEditUserSettings() {
        getRepository().recordMetricsForCompletingEditUserSettings();
    }

    public void registerListeners() {
        getRepository().registerListeners();
    }

    public void unregisterListeners() {
        getRepository().unregisterListeners();
    }
}
