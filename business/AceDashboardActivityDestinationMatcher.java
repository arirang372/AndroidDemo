package com.geico.mobile.android.ace.geicoappbusiness.navigation.deeplinking;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.enumerating.AceMatcher;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;

import java.util.Map;
import java.util.Set;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_TELEMATICS_NEXTGEN_OFFER;

public class AceDashboardActivityDestinationMatcher implements AceMatcher<Map.Entry<String, Class<?>>> {
    private final Class<? extends Activity> activityClass;
    private final Set<String> safeDestinations;
    private final boolean isUserTelematicsNextGenEnrolling;

    public AceDashboardActivityDestinationMatcher(@NonNull AceSessionController sessionController, @NonNull Set<String> safeDestinations, @NonNull Class<? extends Activity> activityClass) {
        this.activityClass = activityClass;
        this.safeDestinations = safeDestinations;
        isUserTelematicsNextGenEnrolling = sessionController.getUserSession().getDashfolioFlow().isUserTelematicsNextGenOfferEnrolling();
    }

    @Override
    public boolean isMatch(Map.Entry<String, Class<?>> element) {
        if (isUserTelematicsNextGenEnrolling) {
            return element.getKey().equals(ACTION_TELEMATICS_NEXTGEN_OFFER);
        }
        return activityClass.equals(element.getValue()) && isSafeDestination(element.getKey());
    }

    protected boolean isSafeDestination(String action) {
        return safeDestinations.contains(action);
    }
}
