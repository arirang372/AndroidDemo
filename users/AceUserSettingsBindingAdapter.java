package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.view;

import java.util.List;

import android.graphics.Typeface;
import android.text.Editable;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnItemSelectedComponentListener;
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnNothingSelected;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.ui.widget.AceBaseTextWatcher;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoapppresentation.framework.widget.AcePhoneNumberFormattingEditText;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem;

/**
 * A binding adapter that is used for binding User setting components such as recycler view and spinner
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsBindingAdapter {

	@BindingAdapter(value = "phoneTextWatcher")
	public static void setUserSettingsSectionItem(@NonNull AceUserSettingsPhoneNumberEditText editText,
			@NonNull AceUserSettingsSectionItem sectionItem) {
		if(sectionItem.getLabel().get().equals("Mobile Phone"))
			editText.setUserSettingsSectionItem(sectionItem);
	}

	@BindingAdapter(value = {"fuelTypes", "fuelCodes", "selectedFuelCode", "callback"})
	public static void setFuelTypes(@NonNull AppCompatSpinner spinner,
			@NonNull List<AceOutOfGasTypeEnum> fuelTypes,
			@NonNull List<String> fuelCodes,
			@NonNull String selectedFuelCode,
			@NonNull AceUserSettingsCallback callback) {
		spinner.setAdapter(new AceFuelTypeSpinnerAdapter(fuelTypes, callback));
		spinner.setSelection(Math.max(0, fuelCodes.indexOf(selectedFuelCode)), false);
	}

	@BindingAdapter(value = {"android:onItemSelected", "android:onNothingSelected",
			"android:selectedItemPositionAttrChanged"}, requireAll = false)
	public static void setOnItemSelectedListener(@NonNull AdapterView view, @NonNull AdapterViewBindingAdapter.OnItemSelected selected,
			@NonNull OnNothingSelected nothingSelected, @NonNull InverseBindingListener attrChanged) {
		if (selected == null && nothingSelected == null && attrChanged == null) {
			view.setOnItemSelectedListener(null);
			return;
		}
		view.setOnItemSelectedListener(
				new OnItemSelectedComponentListener(selected, nothingSelected, attrChanged));
	}

	@BindingAdapter({"userSettingsListItems", "callback"})
	public static void setUserSettingsListItems(@NonNull RecyclerView recyclerView,
			@NonNull List<AceUserSettingsListItem> userSettingsListItems,
			@NonNull AceUserSettingsCallback callback) {
		recyclerView.setAdapter(new AceUserSettingsListItemAdapter(userSettingsListItems, callback));
	}

	@BindingAdapter({"userSettingsSections", "callback"})
	public static void setUserSettingsSections(@NonNull RecyclerView recyclerView,
			@NonNull List<AceUserSettingsSectionItem> userSettingsSections,
			@NonNull AceUserSettingsCallback callback) {
		recyclerView.setAdapter(new AceUserSettingsSectionItemAdapter(userSettingsSections, callback));
	}

	@BindingAdapter({"vehicleColors", "matchingVehicleColors", "selectedVehicleColor", "callback"})
	public static void setVehicleColors(@NonNull AppCompatSpinner spinner,
			@NonNull List<AceVehicleColor> vehicleColors,
			@NonNull List<AceVehicleColor> matchingVehicleColors,
			@NonNull AceVehicleColor selectedVehicleColor,
			@NonNull AceUserSettingsCallback callback) {
		spinner.setAdapter(new AceVehicleColorSpinnerAdapter(vehicleColors, callback));
		AceVehicleColor matchedColor = AceBasicEnumerator.DEFAULT.firstMatch(matchingVehicleColors,
				element -> element.getName().equals(selectedVehicleColor.getName()), AceVehicleColor.UNKNOWN_COLOR);
		spinner.setSelection(Math.max(0, matchingVehicleColors.indexOf(matchedColor)), false);
	}

	@BindingAdapter({"vehicles", "selectedVehicle", "callback"})
	public static void setVehicles(@NonNull AppCompatSpinner spinner,
			@NonNull List<AceUserProfileVehicle> vehicles,
			@NonNull AceUserProfileVehicle selectedVehicle,
			@NonNull AceUserSettingsCallback callback) {
		spinner.setAdapter(new AceVehicleSpinnerAdapter(vehicles, callback));
		spinner.setSelection(Math.max(0, vehicles.indexOf(selectedVehicle)), false);
	}

	@BindingAdapter("customTextStyle")
	public static void setCustomTextStyle(@NonNull TextView textView, @NonNull String itemIndex){
		if(Integer.parseInt(itemIndex) == 1){
			textView.setTypeface(null, Typeface.NORMAL);
			return;
		}
		textView.setTypeface(null, Typeface.BOLD);
	}

	private AceUserSettingsBindingAdapter() {

	}
}
