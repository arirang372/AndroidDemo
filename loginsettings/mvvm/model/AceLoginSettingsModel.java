package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;

/**
 * A model for the login settings details.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsModel extends AceViewBackingModel {
    private final ObservableList<AceLoginSettingsListItem> loginSettingsListItems =
            new ObservableArrayList<>();

    @NonNull
    public ObservableList<AceLoginSettingsListItem> getLoginSettingsListItems() {
        return loginSettingsListItems;
    }
}
