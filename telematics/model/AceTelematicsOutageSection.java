package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents the outage page section
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageSection {

    private final String sectionTitle;
    private final List<AceTelematicsOutageSectionItem> sectionItems = new ArrayList<>();

    public AceTelematicsOutageSection(@NonNull String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    @NonNull
    public String getSectionTitle() {
        return sectionTitle;
    }

    @NonNull
    public List<AceTelematicsOutageSectionItem> getSectionItems() {
        return sectionItems;
    }
}
