package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;

/**
 * A class that displays each section detail description on the user settings page.
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsSectionItem {

	private final ObservableField<Integer> editUserSettingButtonResource = new ObservableField<>(0);
	private final List<String> fuelTypeCodes = Arrays.asList(AceOutOfGasTypeEnum.REGULAR_UNLEADED.getCode(),
			AceOutOfGasTypeEnum.PREMIUM_UNLEADED.getCode(), AceOutOfGasTypeEnum.DIESEL.getCode(),
			AceOutOfGasTypeEnum.ELECTRIC.getCode());
	private final ObservableList<AceOutOfGasTypeEnum> fuelTypes = new ObservableArrayList<>();
	private final ObservableField<String> label = new ObservableField<>("");
	private final ObservableField<String> labelContentDescription = new ObservableField<>("");
	private final List<AceVehicleColor> matchingVehicleColors = new ArrayList<>();
	private final ObservableField<AceUserProfileVehicle> primaryVehicle = new ObservableField<>(new AceUserProfileVehicle());
	private final ObservableField<AceVehicleColor> primaryVehicleColor = new ObservableField<>(AceVehicleColor.UNKNOWN_COLOR);
	private final ObservableField<String> primaryVehicleFuelCode = new ObservableField<>("");
	private final ObservableInt selectedFuelTypePosition = new ObservableInt(0);
	private final ObservableInt selectedVehicleColorPosition = new ObservableInt(0);
	private final ObservableInt selectedVehiclePosition = new ObservableInt(0);
	private final ObservableBoolean showEditUserSettingsButton = new ObservableBoolean(false);
	private final ObservableBoolean showFuelTypeSpinner = new ObservableBoolean(false);
	private final ObservableBoolean showUserSettingsEditText = new ObservableBoolean(false);
	private final ObservableBoolean showUserSettingsText = new ObservableBoolean(false);
	private final ObservableBoolean showVehicleColorSpinner = new ObservableBoolean(false);
	private final ObservableBoolean showVehicleSpinner = new ObservableBoolean(false);
	private final ObservableField<String> text = new ObservableField<>("");
	private final ObservableBoolean validPhoneNumber = new ObservableBoolean(false);
	private final ObservableList<AceVehicleColor> vehicleColors = new ObservableArrayList<>();
	private final ObservableList<AceUserProfileVehicle> vehicles = new ObservableArrayList<>();
	private final ObservableField<Drawable> editUserSettingButtonDrawable = new ObservableField<>();

	public void setEditUserSettingButtonResource(@NonNull Drawable drawable){
		this.editUserSettingButtonDrawable.set(drawable);
	}

	@NonNull
	public ObservableField<Drawable> getEditUserSettingButtonDrawable(){
		return editUserSettingButtonDrawable;
	}

	@NonNull
	public ObservableField<Integer> getEditUserSettingButtonResource() {
		return this.editUserSettingButtonResource;
	}

	@NonNull
	public List<String> getFuelTypeCodes() {
		return fuelTypeCodes;
	}

	@NonNull
	public ObservableList<AceOutOfGasTypeEnum> getFuelTypes() {
		return fuelTypes;
	}

	@NonNull
	public ObservableField<String> getLabel() {
		return this.label;
	}

	@NonNull
	public ObservableField<String> getLabelContentDescription() {
		return this.labelContentDescription;
	}

	@NonNull
	public List<AceVehicleColor> getMatchingVehicleColors() {
		return matchingVehicleColors;
	}

	@NonNull
	public ObservableField<AceUserProfileVehicle> getPrimaryVehicle() {
		return primaryVehicle;
	}

	@NonNull
	public ObservableField<AceVehicleColor> getPrimaryVehicleColor() {
		return primaryVehicleColor;
	}

	@NonNull
	public ObservableField<String> getPrimaryVehicleFuelCode() {
		return primaryVehicleFuelCode;
	}

	@NonNull
	public ObservableInt getSelectedFuelTypePosition() {
		return selectedFuelTypePosition;
	}

	@NonNull
	public ObservableInt getSelectedVehicleColorPosition() {
		return selectedVehicleColorPosition;
	}

	@NonNull
	public ObservableInt getSelectedVehiclePosition() {
		return selectedVehiclePosition;
	}

	@NonNull
	public ObservableField<String> getText() {
		return text;
	}

	@NonNull
	public ObservableList<AceVehicleColor> getVehicleColors() {
		return vehicleColors;
	}

	@NonNull
	public ObservableList<AceUserProfileVehicle> getVehicles() {
		return vehicles;
	}

	@NonNull
	public ObservableBoolean isValidPhoneNumber() {
		return validPhoneNumber;
	}

	public void setEditUserSettingButtonResource(@IdRes @NonNull Integer resourceId) {
		this.editUserSettingButtonResource.set(resourceId);
	}

	public void setFuelTypes(@NonNull List<AceOutOfGasTypeEnum> fuelTypes) {
		this.fuelTypes.clear();
		this.fuelTypes.addAll(fuelTypes);
	}

	public void setIsValidPhoneNumber(boolean validPhoneNumber) {
		this.validPhoneNumber.set(validPhoneNumber);
	}

	public void setLabel(@NonNull String label) {
		this.label.set(label);
		this.labelContentDescription.set(label + " Label");
	}

	public void setPrimaryVehicle(@NonNull AceUserProfileVehicle primaryVehicle) {
		this.primaryVehicle.set(primaryVehicle);
	}

	public void setPrimaryVehicleColor(@NonNull AceVehicleColor primaryVehicleColor) {
		this.primaryVehicleColor.set(primaryVehicleColor);
	}

	public void setPrimaryVehicleFuelCode(@NonNull String fuelCode) {
		this.primaryVehicleFuelCode.set(fuelCode);
	}

	public void setSelectedFuelTypePosition(int position) {
		this.selectedFuelTypePosition.set(position);
	}

	public void setSelectedVehicleColorPosition(int position) {
		this.selectedVehicleColorPosition.set(position);
	}

	public void setShouldShowEditUserSettingsButton(boolean showUserSettingsButton) {
		this.showEditUserSettingsButton.set(showUserSettingsButton);
	}

	public void setShouldShowFuelTypeSpinner(boolean showUserSettingsFuelTypeSpinner) {
		this.showFuelTypeSpinner.set(showUserSettingsFuelTypeSpinner);
	}

	public void setShouldShowUserSettingsEditText(boolean showUserSettingsEditText) {
		this.showUserSettingsEditText.set(showUserSettingsEditText);
	}

	public void setShouldShowUserSettingsText(boolean showUserSettingsText) {
		this.showUserSettingsText.set(showUserSettingsText);
	}

	public void setShouldShowVehicleColorSpinner(boolean showUserSettingsVehicleColorSpinner) {
		this.showVehicleColorSpinner.set(showUserSettingsVehicleColorSpinner);
	}

	public void setShouldShowVehicleSpinner(boolean showUserSettingsSpinner) {
		this.showVehicleSpinner.set(showUserSettingsSpinner);
	}

	public void setText(@NonNull String text) {
		this.text.set(text);
	}

	public void setVehicleColors(@NonNull List<AceVehicleColor> vehicleColors) {
		this.vehicleColors.clear();
		this.vehicleColors.addAll(vehicleColors);
		matchingVehicleColors.clear();
		matchingVehicleColors.addAll(this.vehicleColors);
		matchingVehicleColors.set(0, AceVehicleColor.UNKNOWN_COLOR);
		matchingVehicleColors.set(matchingVehicleColors.size() - 1, AceVehicleColor.UNKNOWN_COLOR);
	}

	public void setVehicles(@NonNull List<AceUserProfileVehicle> vehicles) {
		this.vehicles.clear();
		this.vehicles.addAll(vehicles);
	}

	@NonNull
	public ObservableBoolean shouldShowEditUserSettingsButton() {
		return showEditUserSettingsButton;
	}

	@NonNull
	public ObservableBoolean shouldShowFuelTypeSpinner() {
		return showFuelTypeSpinner;
	}

	@NonNull
	public ObservableBoolean shouldShowUserSettingsEditText() {
		return showUserSettingsEditText;
	}

	@NonNull
	public ObservableBoolean shouldShowUserSettingsText() {
		return showUserSettingsText;
	}

	@NonNull
	public ObservableBoolean shouldShowVehicleColorSpinner() {
		return showVehicleColorSpinner;
	}

	@NonNull
	public ObservableBoolean shouldShowVehicleSpinner() {
		return showVehicleSpinner;
	}
}
