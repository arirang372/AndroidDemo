package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSectionItem;

/**
 * API defining user interactions with the Telematics outage page
 *
 * @author John Sung, Geico
 */
public interface AceTelematicsOutageCallback {
    void onOutageSectionItemClick(@NonNull AceTelematicsOutageSectionItem sectionItem);
}
