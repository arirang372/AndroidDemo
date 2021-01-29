package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import java.util.List;

public class AceLoginSettingsSectionDetailListItem {
    private final ObservableField<String> title = new ObservableField<>("");
    private final ObservableList<AceLoginSettingsSectionDetailItem> sectionDetailItems =
            new ObservableArrayList<>();

    @NonNull
    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title.set(title);
    }

    @NonNull
    public ObservableList<AceLoginSettingsSectionDetailItem> getSectionDetailItems() {
        return sectionDetailItems;
    }

    public void setSectionDetailItems(@NonNull List<AceLoginSettingsSectionDetailItem> sectionDetailItems) {
        this.sectionDetailItems.clear();
        this.sectionDetailItems.addAll(sectionDetailItems);
    }

    @NonNull
    public AceLoginSettingsSectionDetailListItem withSectionDetailItems(@NonNull List<AceLoginSettingsSectionDetailItem>
                                                                                sectionDetailItems) {
        setSectionDetailItems(sectionDetailItems);
        return this;
    }
}
