package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import java.util.List;

/**
 * A model that contains login settings list item contents
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsListItem {
    private final ObservableField<String> subtitle = new ObservableField<>("");
    private final ObservableField<String> title = new ObservableField<>("");
    private final ObservableList<AceLoginSettingsSectionItem> loginSettingsSectionItems =
            new ObservableArrayList<>();

    @NonNull
    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title.set(title);
    }

    @NonNull
    public ObservableField<String> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NonNull String subtitle) {
        this.subtitle.set(subtitle);
    }

    public AceLoginSettingsListItem withSubtitle(@NonNull String subtitle) {
        setSubtitle(subtitle);
        return this;
    }

    @NonNull
    public ObservableList<AceLoginSettingsSectionItem> getLoginSettingsSectionItems() {
        return loginSettingsSectionItems;
    }

    public void setLoginSettingsSectionItems(@NonNull List<AceLoginSettingsSectionItem> loginSettingsSectionItems) {
        this.loginSettingsSectionItems.clear();
        this.loginSettingsSectionItems.addAll(loginSettingsSectionItems);
    }
}
