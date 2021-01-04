package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.view;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingListener;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnItemSelectedComponentListener;
import androidx.databinding.adapters.AdapterViewBindingAdapter.OnNothingSelected;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.ui.widget.AceBaseTextWatcher;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsSectionItem;

import java.util.List;

/**
 * A binding adapter that is used for binding User setting components such as recycler view and spinner
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsBindingAdapter {
    private AceUserSettingsBindingAdapter() {

    }

    @BindingAdapter(value = "phoneTextWatcher")
    public static void addTextWatcher(@NonNull AppCompatEditText editText,
                                      @NonNull AceUserSettingsSectionItem sectionItem) {
        editText.addTextChangedListener(new AceBaseTextWatcher() {
            @Override
            public void afterTextChanged(@NonNull Editable s) {
                sectionItem.setText(s.toString());
            }
        });
    }

    @BindingAdapter(value = "isValidPhoneNumber")
    public static void setPhoneNumberErrorMessage(@NonNull AppCompatEditText editText,
                                                  @NonNull boolean isValidPhoneNumber) {
        if (isValidPhoneNumber) {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            return;
        }
        Drawable errorDrawable = editText.getContext().getDrawable(R.drawable.warning);
        errorDrawable.setTint(editText.getContext().getResources().getColor(R.color.alertsColor));
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, errorDrawable, null);
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

    // A binding adapter that enables us to bind a selected item position that can be stored in the model
    // referenced from https://stackoverflow.com/questions/37293998/how-to-2-way-bind-spinner-selected-value-to-value-in-arraylist/39900814
    @BindingAdapter("android:selectedItemPosition")
    public static void setSelectedItemPosition(@NonNull AdapterView view, int position) {
        if (view.getSelectedItemPosition() != position) {
            view.setSelection(position);
        }
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

    @BindingAdapter("android:selectedValueAttrChanged")
    public static void setSelectedValueListener(@NonNull AdapterView view,
                                                @NonNull InverseBindingListener attrChanged) {
        if (attrChanged == null) {
            view.setOnItemSelectedListener(null);
            return;
        }
        view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attrChanged.onChange();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                attrChanged.onChange();
            }
        });
    }

    @BindingAdapter("android:selectedValue")
    public static void setSelectedValue(@NonNull AdapterView<?> view, @NonNull Object selectedValue) {
        Adapter adapter = view.getAdapter();
        if (adapter == null) {
            return;
        }
        int position = AdapterView.INVALID_POSITION;
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i) == selectedValue) {
                position = i;
                break;
            }
        }
        view.setSelection(position);
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

    @BindingAdapter(value = {"fuelTypes", "fuelCodes", "selectedFuelCode", "callback"})
    public static void setFuelTypes(@NonNull AppCompatSpinner spinner,
                                    @NonNull List<AceOutOfGasTypeEnum> fuelTypes,
                                    @NonNull List<String> fuelCodes,
                                    @NonNull String selectedFuelCode,
                                    @NonNull AceUserSettingsCallback callback) {
        spinner.setAdapter(new AceFuelTypeSpinnerAdapter(fuelTypes, callback));
        spinner.setSelection(Math.max(0, fuelCodes.indexOf(selectedFuelCode)), false);
    }
}
