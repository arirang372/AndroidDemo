package com.geico.mobile.android.ace.geicoappbusiness.navigation.deeplinking;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.eventhandling.AceReaction;
import com.geico.mobile.android.ace.coreframework.patterns.AceDefaultingMap;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceNavigationFlow;

import java.util.Map;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_BILLING;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_CAMERA_PERMISSION_FOR_WEB_LINK;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_CHANGE_PAYMENT_PLAN;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_COVERAGE_DESCRIPTION;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_DASHBOARD;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_DISCOUNT_DESCRIPTION;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_DRIVER_DETAIL;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_NEW_PAYMENT_ACCOUNT;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_NEW_PAYMENT_METHOD;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_PERMISSION_WEBLINK_LOCATION_REQUEST;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_POLICY_DASHBOARD;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_POLICY_LEGACY_TAB;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_TELEMATICS_NEXTGEN_OFFER;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_VEHICLE_DETAIL_DISPLAY;

public class AceDashboardActivityDestinationReaction implements AceReaction<Activity> {
    private final Map.Entry<String, Class<?>> entry;
    private final AceSessionController sessionController;

    public AceDashboardActivityDestinationReaction(@NonNull Map.Entry<String, Class<?>> entry, @NonNull AceSessionController sessionController) {
        this.entry = entry;
        this.sessionController = sessionController;
    }

    @Override
    public void reactTo(@NonNull Activity subject) {
        String action = getActionRepresentedBy(entry.getKey());
        if (ACTION_TELEMATICS_NEXTGEN_OFFER.equals(action)) {
            action = getActionConstant(action);
            sessionController.getUserSession().getDashfolioFlow().setUserTelematicsNextGenOfferEnrolling(false);
        }
        sessionController.getUserSession().getDashfolioFlow().setTelematicsNextGenOfferEnrollmentComplete(false);
        sessionController.getDeepLink().setDeepLinkAction(action);
        restoreTabIndexValuesFromDeepLink();
        sessionController.startAction(subject, action);
        considerResettingDeepLinkAction(action);
    }

    private void considerResettingDeepLinkAction(String action) {
        if (!ACTION_DASHBOARD.equals(action)) {
            sessionController.getDeepLink().setDeepLinkAction("");
        }
    }

    private String getActionConstant(String action) {
        if (!sessionController.getUserSession().getDashfolioFlow().isTelematicsNextGenOfferEnrollmentComplete()) {
            return ACTION_DASHBOARD;
        }
        return action;
    }

    private void restoreTabIndexValuesFromDeepLink() {
        getNavigationFlow().setBillingTabIndex(sessionController.getDeepLink().getBillingTabIndex());
        getNavigationFlow().setPolicyTabIndex(sessionController.getDeepLink().getPolicyTabIndex());
    }

    private AceNavigationFlow getNavigationFlow() {
        return sessionController.getPolicySession().getNavigationFlow();
    }

    private String getActionRepresentedBy(String action) {
        return createActionMapAccommodatingTabbedActivitiesFor(action).get(action);
    }

    private Map<String, String> createActionMapAccommodatingTabbedActivitiesFor(String action) {
        // safeDestinations that are to be remapped to billing or policy tab action
        Map<String, String> map = AceDefaultingMap.withDefault(action);
        map.put(ACTION_CAMERA_PERMISSION_FOR_WEB_LINK, ACTION_POLICY_DASHBOARD);
        map.put(ACTION_CHANGE_PAYMENT_PLAN, ACTION_BILLING);
        map.put(ACTION_COVERAGE_DESCRIPTION, ACTION_POLICY_LEGACY_TAB);
        map.put(ACTION_DISCOUNT_DESCRIPTION, ACTION_POLICY_LEGACY_TAB);
        map.put(ACTION_DRIVER_DETAIL, ACTION_POLICY_DASHBOARD);
        map.put(ACTION_NEW_PAYMENT_METHOD, ACTION_BILLING);
        map.put(ACTION_NEW_PAYMENT_ACCOUNT, ACTION_BILLING);
        map.put(ACTION_PERMISSION_WEBLINK_LOCATION_REQUEST, ACTION_POLICY_DASHBOARD);
        map.put(ACTION_VEHICLE_DETAIL_DISPLAY, ACTION_POLICY_DASHBOARD);
        return map;
    }
}
