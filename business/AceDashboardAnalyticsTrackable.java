package com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsFacade;

/**
 * An Dashboard fragment trackable, which tracks crossSellOffer and nextstep internalCampaign.impression
 *
 * @author John Sung, Geico
 */
public class AceDashboardAnalyticsTrackable extends AceBaseAnalyticsTrackable {
    public AceDashboardAnalyticsTrackable(@NonNull AceAnalyticsFacade analyticsFacade) {
        super(analyticsFacade);
    }
}
