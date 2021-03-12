package com.geico.mobile.android.ace.geicoappbusiness.navigation.deeplinking;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.assertions.AceWatchdog;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enumerating.AceEnumerator;
import com.geico.mobile.android.ace.coreframework.enumerating.AceMatcher;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceReaction;
import com.geico.mobile.android.ace.coreframework.patterns.AceDefaultingMap;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceNavigationFlow;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Modifies the current deep link strategy to return to the activity of the
 * supplied type after the next time we visit the dashboard.
 * <p>
 * Note: It is also used to redirect to specific tab in activity. For ex: AcePolicyTabActivity, AceBillingTabActivity, etc.
 * <p>
 * This class must only be accessed from the UI thread.
 *
 * @author Chris Delia, Lucid Frameworks Corporation
 */
public class AceDashboardActivityDestinationInstaller implements AceReaction<Class<? extends Activity>>, AceActionConstants {

    protected static final AceEnumerator ENUMERATOR = AceBasicEnumerator.DEFAULT;
    final AceSessionController sessionController;
    private final Map<String, Class<?>> handlersByAction;
    private final Set<String> safeDestinations;
    private final AceWatchdog watchdog;

    /**
     * Constructor
     *
     * @param registry provides access to application scoped facilities
     */
    public AceDashboardActivityDestinationInstaller(@NonNull AceRegistry registry) {
        handlersByAction = registry.getHandlersByAction();
        safeDestinations = registry.getDeepLinkActions();
        sessionController = registry.getSessionController();
        watchdog = registry.getWatchdog();
    }

    @NonNull
    protected Map<String, String> createActionMapAccommodatingTabbedActivitiesFor(String action) {
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

    protected AceReaction<Entry<String, Class<?>>> createActivityMatchReaction() {
        return new AceReaction<Entry<String, Class<?>>>() {

            @Override
            public void reactTo(Entry<String, Class<?>> entry) {
                setDeepLinkActionFrom(entry);
                getDeepLink().setInPolicyReaction(new AceDashboardActivityDestinationReaction(entry, sessionController));
            }
        };
    }

    protected String getActionRepresentedBy(String action) {
        return createActionMapAccommodatingTabbedActivitiesFor(action).get(action);
    }

    protected AceDeepLink getDeepLink() {
        return sessionController.getDeepLink();
    }

    @NonNull
    protected AceNavigationFlow getNavigationFlow() {
        return sessionController.getPolicySession().getNavigationFlow();
    }

    protected void installDashboardStartReaction(final Class<? extends Activity> activityClass) {
        Set<Entry<String, Class<?>>> entries = handlersByAction.entrySet();
        AceMatcher<Entry<String, Class<?>>> matcher = new AceDashboardActivityDestinationMatcher(sessionController, safeDestinations, activityClass);
        AceReaction<Entry<String, Class<?>>> reaction = createActivityMatchReaction();
        ENUMERATOR.reactToFirstMatch(entries, matcher, reaction);
    }

    protected void installGeniusLinkReaction() {
        final String action = sessionController.getGeniusLinkType();
        setDeepLinkActionTo(action);
        getDeepLink().setInPolicyReaction(context -> {
            if (sessionController.hasHandlerForAction(action)) {
                sessionController.startAction(context, action);
            }
        });
    }

    @Override
    public void reactTo(Class<? extends Activity> destinationActivityClass) {
        watchdog.assertUiThread();
        if (TextUtils.isEmpty(sessionController.getGeniusLinkType())) {
            installDashboardStartReaction(destinationActivityClass);
            return;
        }
        installGeniusLinkReaction();
    }

    protected void setDeepLinkActionFrom(@NonNull Entry<String, Class<?>> actionHandlerEntry) {
        String action = getActionRepresentedBy(actionHandlerEntry.getKey());
        setDeepLinkActionTo(action);
        setDeepLinkTabIndexValues();
    }

    protected void setDeepLinkActionTo(@NonNull String action) {
        getDeepLink().setDeepLinkAction(action);
    }

    protected void setDeepLinkTabIndexValues() {
        getDeepLink().setBillingTabIndex(getNavigationFlow().getBillingTabIndex());
        getDeepLink().setPolicyTabIndex(getNavigationFlow().getPolicyTabIndex());
    }
}