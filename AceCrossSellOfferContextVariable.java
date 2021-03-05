package com.geico.mobile.android.ace.geicoapppresentation.analytics.context;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable;

/**
 * Sets a cross sell offer context variable from specific cross offers provided through policy download
 *
 * @author John Sung, Geico
 */
public class AceCrossSellOfferContextVariable extends AceAnalyticsContextVariable {
    @NonNull
    @Override
    public String valueFrom(@NonNull AceAnalyticsTrackable trackable, @NonNull String trackedTag, @NonNull AceRegistry registry) {
        return registry.getSessionController().getUserSession().getDashfolioFlow().getCrossSellOfferDisplayedToTrack();
    }
}
