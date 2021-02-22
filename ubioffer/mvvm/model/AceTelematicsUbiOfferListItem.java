package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import java.util.List;


/**
 * A model for the Telematics ubi offer list item
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferListItem {
    private final ObservableField<String> title = new ObservableField<>("");
    private final ObservableField<String> subtitle = new ObservableField<>("");
    private final ObservableList<AceTelematicsUbiOfferDetailItem> ubiOfferDetailItems = new ObservableArrayList<>();
    private final ObservableBoolean showTopDriveEasyLogo = new ObservableBoolean(false);
    private final ObservableBoolean showEnrollInDriveEasyButton = new ObservableBoolean(false);

    public void setShouldShowTopDriveEasyLogo(boolean showTopDriveEasyLogo) {
        this.showTopDriveEasyLogo.set(showTopDriveEasyLogo);
    }

    @NonNull
    public ObservableBoolean shouldShowTopDriveEasyLogo() {
        return showTopDriveEasyLogo;
    }

    public void setShouldShowEnrollInDriveEasyButton(boolean showEnrollInDriveEasyButton) {
        this.showEnrollInDriveEasyButton.set(showEnrollInDriveEasyButton);
    }

    @NonNull
    public ObservableBoolean shouldShowEnrollInDriveEasyButton() {
        return showEnrollInDriveEasyButton;
    }

    @NonNull
    public ObservableField<String> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(@NonNull String subtitle) {
        this.subtitle.set(subtitle);
    }

    @NonNull
    public ObservableField<String> getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title.set(title);
    }

    @NonNull
    public ObservableList<AceTelematicsUbiOfferDetailItem> getUbiOfferDetailItems() {
        return ubiOfferDetailItems;
    }

    public void setUbiOfferDetailItems(@NonNull List<AceTelematicsUbiOfferDetailItem> ubiOfferDetailItems) {
        this.ubiOfferDetailItems.clear();
        this.ubiOfferDetailItems.addAll(ubiOfferDetailItems);
    }
}
