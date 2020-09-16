package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage;

/**
 * A class that represents the outage page header
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageHeaderItem extends AceTelematicsOutageListItem {
    private final int headerImageResourceId;

    public AceTelematicsOutageHeaderItem(int headerImageResourceId) {
        this.headerImageResourceId = headerImageResourceId;
    }

    public int getHeaderImageResourceId() {
        return headerImageResourceId;
    }
}
