package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;

/**
 * A model for the login settings details.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsModel extends AceViewBackingModel {

    private final ObservableField<String> errorMessages = new ObservableField<>("");
    private final ObservableList<AceLoginSettingsListItem> loginSettingsListItems =
            new ObservableArrayList<>();
    private AceLoginSettingsSectionItem selectedSectionItem = new AceLoginSettingsSectionItem();

    @NonNull
    public AceLoginSettingsSectionItem getSelectedSectionItem() {
        return selectedSectionItem;
    }

    public void setSelectedSectionItem(@NonNull AceLoginSettingsSectionItem selectedSectionItem) {
        this.selectedSectionItem = selectedSectionItem;
    }

    @NonNull
    public ObservableField<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(@NonNull String errorMessage) {
        this.errorMessages.set(errorMessage);
    }

    @NonNull
    public ObservableList<AceLoginSettingsListItem> getLoginSettingsListItems() {
        return loginSettingsListItems;
    }
}
