package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that holds all of the data needed for telematics outage page
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageModel {
    private final List<AceTelematicsOutageListItem> outageListItems = new ArrayList<>();

    @NonNull
    public List<AceTelematicsOutageListItem> getOutageListItems() {
        return outageListItems;
    }
}
