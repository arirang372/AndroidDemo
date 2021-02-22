package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model;


import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;

/**
 * A model for the Telematics ubi offer detail item
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferDetailItem {
    private final ObservableField<String> title = new ObservableField<>("");
    private final ObservableField<String> subtitle = new ObservableField<>("");
    @DrawableRes
    private int sectionImageResourceId;

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

    @DrawableRes
    public int getSectionImageResourceId() {
        return sectionImageResourceId;
    }

    public void setSectionImageResourceId(@DrawableRes int sectionImageResourceId) {
        this.sectionImageResourceId = sectionImageResourceId;
    }
}
