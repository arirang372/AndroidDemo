package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;

/**
 * A class that represents the outage page section item
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageSectionItem {
    @IdRes
    private final int sectionItemImageResourceId;
    private final String sectionItemName;

    public AceTelematicsOutageSectionItem(@NonNull String sectionItemName, int sectionItemImageResourceId) {
        this.sectionItemName = sectionItemName;
        this.sectionItemImageResourceId = sectionItemImageResourceId;
    }

    @IdRes
    public int getSectionItemImageResourceId() {
        return sectionItemImageResourceId;
    }

    @NonNull
    public String getSectionItemName() {
        return sectionItemName;
    }
}
