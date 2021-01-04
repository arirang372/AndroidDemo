package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import java.util.List;

/**
 * A model that contains user settings list item contents
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsListItem {
    private final ObservableField<String> title = new ObservableField<>("");
    private final ObservableField<String> subtitle = new ObservableField<>("");
    private final ObservableBoolean showSubtitle = new ObservableBoolean(false);
    private final ObservableList<AceUserSettingsSectionItem> userSettingsSectionItems = new ObservableArrayList<>();
    private final ObservableBoolean showSkipButton = new ObservableBoolean(false);

    public void setShouldShowSubtitle(boolean showSubtitle) {
        this.showSubtitle.set(showSubtitle);
    }

    public ObservableList<AceUserSettingsSectionItem> getUserSettingsSectionItems() {
        return userSettingsSectionItems;
    }

    public void setUserSettingsSectionItems(@NonNull List<AceUserSettingsSectionItem> userSettingsSectionItems) {
        this.userSettingsSectionItems.clear();
        this.userSettingsSectionItems.addAll(userSettingsSectionItems);
    }

    public void setShouldShowSkipButton(boolean showSkipButton) {
        this.showSkipButton.set(showSkipButton);
    }

    @NonNull
    public ObservableBoolean shouldShowSubtitle() {
        return showSubtitle;
    }

    @NonNull
    public ObservableBoolean shouldShowSkipButton() {
        return showSkipButton;
    }

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
}
