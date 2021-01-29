package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import java.util.List;

/**
 * A class that displays each section detail description on the login settings page.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsSectionItem {

    private final ObservableField<String> label = new ObservableField<>("");
    private final ObservableInt position = new ObservableInt(0);
    private final ObservableBoolean screenUnlockEnabled = new ObservableBoolean(false);
    private final ObservableField<String> text = new ObservableField<>("");
    private final ObservableBoolean isValid = new ObservableBoolean(false);
    private final ObservableList<AceLoginSettingsSectionDetailListItem> sectionDetailListItems
            = new ObservableArrayList<>();

    @NonNull
    public ObservableList<AceLoginSettingsSectionDetailListItem> getSectionDetailListItems() {
        return sectionDetailListItems;
    }

    public void setIsValidField(boolean isValid) {
        this.isValid.set(isValid);
    }

    @NonNull
    public ObservableBoolean isValidField() {
        return this.isValid;
    }

    @NonNull
    public ObservableField<String> getLabel() {
        return this.label;
    }

    public void setLabel(@NonNull String label) {
        this.label.set(label);
    }

    @NonNull
    public ObservableInt getPosition() {
        return this.position;
    }

    public void setPosition(@NonNull int position) {
        this.position.set(position);
    }

    @NonNull
    public ObservableField<String> getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text.set(text);
    }

    @NonNull
    public ObservableBoolean isScreenUnlockEnabled() {
        return screenUnlockEnabled;
    }

    public void setScreenUnlockEnabled(boolean screenUnlockEnabled) {
        this.screenUnlockEnabled.set(screenUnlockEnabled);
    }

    @NonNull
    public AceLoginSettingsSectionItem withLabel(@NonNull String label) {
        setLabel(label);
        return this;
    }

    @NonNull
    public AceLoginSettingsSectionItem withScreenUnlockEnabled(boolean screenUnlockEnabled) {
        setScreenUnlockEnabled(screenUnlockEnabled);
        return this;
    }

    @NonNull
    public AceLoginSettingsSectionItem withSectionDetailListItems(List<AceLoginSettingsSectionDetailListItem>
                                                                          sectionDetailListItems) {
        this.sectionDetailListItems.clear();
        this.sectionDetailListItems.addAll(sectionDetailListItems);
        return this;
    }
}
