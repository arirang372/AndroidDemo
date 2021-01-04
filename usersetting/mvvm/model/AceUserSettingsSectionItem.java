package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that displays each section detail description on the user settings page.
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsSectionItem {
    private final ObservableField<String> label = new ObservableField<>("");
    private final ObservableField<String> text = new ObservableField<>("");
    private final ObservableBoolean showUserSettingsText = new ObservableBoolean(false);
    private final ObservableBoolean showUserSettingsEditText = new ObservableBoolean(false);
    private final ObservableBoolean showVehicleSpinner = new ObservableBoolean(false);
    private final ObservableBoolean showFuelTypeSpinner = new ObservableBoolean(false);
    private final ObservableBoolean showVehicleColorSpinner = new ObservableBoolean(false);
    private final ObservableBoolean showEditUserSettingsButton = new ObservableBoolean(false);
    private final ObservableField<Integer> editUserSettingButtonResource = new ObservableField<>(0);
    private final ObservableList<AceVehicleColor> vehicleColors = new ObservableArrayList<>();
    private final ObservableList<AceOutOfGasTypeEnum> fuelTypes = new ObservableArrayList<>();
    private final ObservableList<AceUserProfileVehicle> vehicles = new ObservableArrayList<>();
    private final ObservableField<Integer> selectedFuelTypePosition = new ObservableField<>(0);
    private final ObservableBoolean validPhoneNumber = new ObservableBoolean(false);
   // private final ObservableField<String> errorMessages = new ObservableField<>("");
    private final List<String> fuelTypeCodes = Arrays.asList(AceOutOfGasTypeEnum.REGULAR_UNLEADED.getCode(),
            AceOutOfGasTypeEnum.PREMIUM_UNLEADED.getCode(), AceOutOfGasTypeEnum.DIESEL.getCode(),
            AceOutOfGasTypeEnum.ELECTRIC.getCode());
    private final ObservableField<String> primaryVehicleFuelCode = new ObservableField<>("");
    private final List<AceVehicleColor> matchingVehicleColors = new ArrayList<>();
    private final ObservableField<AceVehicleColor> primaryVehicleColor = new ObservableField<>(AceVehicleColor.UNKNOWN_COLOR);
    private final ObservableField<AceUserProfileVehicle> primaryVehicle = new ObservableField<>(new AceUserProfileVehicle());
    private final ObservableField<Integer> selectedVehiclePosition = new ObservableField<>(0);
    private final ObservableField<Integer> selectedVehicleColorPosition = new ObservableField<>(0);

    @NonNull
    public ObservableField<Integer> getSelectedVehiclePosition() {
        return selectedVehiclePosition;
    }

    @NonNull
    public ObservableField<Integer> getSelectedVehicleColorPosition() {
        return selectedVehicleColorPosition;
    }

    public void setSelectedVehicleColorPosition(int position) {
        this.selectedVehicleColorPosition.set(position);
    }

    @NonNull
    public ObservableField<AceUserProfileVehicle> getPrimaryVehicle() {
        return primaryVehicle;
    }

    public void setPrimaryVehicle(@NonNull AceUserProfileVehicle primaryVehicle) {
        this.primaryVehicle.set(primaryVehicle);
    }

    @NonNull
    public ObservableField<AceVehicleColor> getPrimaryVehicleColor() {
        return primaryVehicleColor;
    }

    public void setPrimaryVehicleColor(@NonNull AceVehicleColor primaryVehicleColor) {
        this.primaryVehicleColor.set(primaryVehicleColor);
    }

    @NonNull
    public List<AceVehicleColor> getMatchingVehicleColors() {
        return matchingVehicleColors;
    }

    @NonNull
    public ObservableField<String> getPrimaryVehicleFuelCode() {
        return primaryVehicleFuelCode;
    }

    public void setPrimaryVehicleFuelCode(@NonNull String fuelCode) {
        this.primaryVehicleFuelCode.set(fuelCode);
    }

    @NonNull
    public List<String> getFuelTypeCodes() {
        return fuelTypeCodes;
    }

//    @NonNull
//    public ObservableField<String> getErrorMessages() {
//        return errorMessages;
//    }
//
//    public void setErrorMessages(@NonNull String errorMessages) {
//        this.errorMessages.set(errorMessages);
//    }

    public void setIsValidPhoneNumber(boolean validPhoneNumber) {
        this.validPhoneNumber.set(validPhoneNumber);
    }

    @NonNull
    public ObservableBoolean isValidPhoneNumber() {
        return validPhoneNumber;
    }

    @NonNull
    public ObservableField<Integer> getSelectedFuelTypePosition() {
        return selectedFuelTypePosition;
    }

    public void setSelectedFuelTypePosition(int position) {
        this.selectedFuelTypePosition.set(position);
    }

    @NonNull
    public ObservableList<AceUserProfileVehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(@NonNull List<AceUserProfileVehicle> vehicles) {
        this.vehicles.clear();
        this.vehicles.addAll(vehicles);
    }

    @NonNull
    public ObservableField<Integer> getEditUserSettingButtonResource() {
        return this.editUserSettingButtonResource;
    }

    public void setEditUserSettingButtonResource(@IdRes @NonNull Integer resourceId) {
        this.editUserSettingButtonResource.set(resourceId);
    }

    @NonNull
    public ObservableList<AceVehicleColor> getVehicleColors() {
        return vehicleColors;
    }

    public void setVehicleColors(@NonNull List<AceVehicleColor> vehicleColors) {
        this.vehicleColors.clear();
        this.vehicleColors.addAll(vehicleColors);
        matchingVehicleColors.clear();
        matchingVehicleColors.addAll(this.vehicleColors);
        matchingVehicleColors.set(0, AceVehicleColor.UNKNOWN_COLOR);
        matchingVehicleColors.set(matchingVehicleColors.size() - 1, AceVehicleColor.UNKNOWN_COLOR);
    }

    @NonNull
    public ObservableList<AceOutOfGasTypeEnum> getFuelTypes() {
        return fuelTypes;
    }

    public void setFuelTypes(@NonNull List<AceOutOfGasTypeEnum> fuelTypes) {
        this.fuelTypes.clear();
        this.fuelTypes.addAll(fuelTypes);
    }

    public void setShouldShowUserSettingsText(boolean showUserSettingsText) {
        this.showUserSettingsText.set(showUserSettingsText);
    }

    public void setShouldShowUserSettingsEditText(boolean showUserSettingsEditText) {
        this.showUserSettingsEditText.set(showUserSettingsEditText);
    }

    public void setShouldShowVehicleSpinner(boolean showUserSettingsSpinner) {
        this.showVehicleSpinner.set(showUserSettingsSpinner);
    }

    public void setShouldShowFuelTypeSpinner(boolean showUserSettingsFuelTypeSpinner) {
        this.showFuelTypeSpinner.set(showUserSettingsFuelTypeSpinner);
    }

    public void setShouldShowVehicleColorSpinner(boolean showUserSettingsVehicleColorSpinner) {
        this.showVehicleColorSpinner.set(showUserSettingsVehicleColorSpinner);
    }


    public void setShouldShowEditUserSettingsButton(boolean showUserSettingsButton) {
        this.showEditUserSettingsButton.set(showUserSettingsButton);
    }

    @NonNull
    public ObservableField<String> getLabel() {
        return this.label;
    }

    public void setLabel(@NonNull String label) {
        this.label.set(label);
    }

    @NonNull
    public ObservableField<String> getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text.set(text);
    }

    @NonNull
    public ObservableBoolean shouldShowUserSettingsText() {
        return showUserSettingsText;
    }

    @NonNull
    public ObservableBoolean shouldShowUserSettingsEditText() {
        return showUserSettingsEditText;
    }

    @NonNull
    public ObservableBoolean shouldShowVehicleSpinner() {
        return showVehicleSpinner;
    }

    @NonNull
    public ObservableBoolean shouldShowVehicleColorSpinner() {
        return showVehicleColorSpinner;
    }

    @NonNull
    public ObservableBoolean shouldShowFuelTypeSpinner() {
        return showFuelTypeSpinner;
    }

    @NonNull
    public ObservableBoolean shouldShowEditUserSettingsButton() {
        return showEditUserSettingsButton;
    }
}
