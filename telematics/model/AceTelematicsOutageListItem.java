package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage;

import androidx.annotation.NonNull;

/**
 * A class that represents the outage page list item
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageListItem {
    private final AceTelematicsOutageSection section;

    public AceTelematicsOutageListItem() {
        this(new AceTelematicsOutageSection(""));
    }

    public AceTelematicsOutageListItem(@NonNull AceTelematicsOutageSection section) {
        this.section = section;
    }

    @NonNull
    public AceTelematicsOutageSection getSection() {
        return this.section;
    }
}
