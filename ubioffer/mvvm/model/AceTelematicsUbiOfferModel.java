package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;

/**
 * A model for the Telematics ubi offer details.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferModel extends AceViewBackingModel {
    private final ObservableList<AceTelematicsUbiOfferListItem> ubiOfferListItems = new ObservableArrayList<>();

    @NonNull
    public ObservableList<AceTelematicsUbiOfferListItem> getUbiOfferListItems() {
        return ubiOfferListItems;
    }
}
