package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

/**
 * A class that displays each section detail description on the login settings page.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsSectionItem {
    private final ObservableField<Integer> position = new ObservableField<>(0);
    private final ObservableField<String> label = new ObservableField<>("");
    private final ObservableField<String> text = new ObservableField<>("");
    private final ObservableBoolean screenUnlockEnabled = new ObservableBoolean(false);

    @NonNull
    public ObservableBoolean getScreenUnlockEnabled() {
        return screenUnlockEnabled;
    }

    public void setScreenUnlockEnabled(boolean screenUnlockEnabled) {
        this.screenUnlockEnabled.set(screenUnlockEnabled);
    }

    public AceLoginSettingsSectionItem withScreenUnlockEnabled(boolean screenUnlockEnabled) {
        setScreenUnlockEnabled(screenUnlockEnabled);
        return this;
    }

    @NonNull
    public ObservableField<Integer> getPosition() {
        return this.position;
    }

    public void setPosition(@NonNull int position) {
        this.position.set(position);
    }

    @NonNull
    public ObservableField<String> getLabel() {
        return this.label;
    }

    public void setLabel(@NonNull String label) {
        this.label.set(label);
    }

    public AceLoginSettingsSectionItem withLabel(@NonNull String label) {
        setLabel(label);
        return this;
    }

    @NonNull
    public ObservableField<String> getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text.set(text);
    }
}
