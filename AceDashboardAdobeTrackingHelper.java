package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.application.AceBasicContextRegistryLocator;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceDashfolioFlow;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsFacade;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceDashboardAnalyticsTrackable;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard.DashboardCardType;
import com.geico.mobile.android.ace.geicoapppresentation.application.AceGeicoAppRegistry;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceDashboardCard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_CROSS_SELL_OFFER_CLICK;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_INTERNAL_CAMPAIGN_CLICK;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.CROSS_SELL_OFFER;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.CROSS_SELL_TYPE_LOWER_CASE;

/**
 * Helper for tracking actions related to next steps and cross sell offers on the main dashboard.
 *
 * @author Mike Wideman & John Sung, Geico
 */
public class AceDashboardAdobeTrackingHelper {

    private static final List<String> crossSellOffers = Arrays.asList(DashboardCardType.HOME_OWNERS,
            DashboardCardType.TELEMATICS_NEXT_GEN_OFFER, DashboardCardType.UMBRELLA);
    private static final Map<String, String> contextValueMap = new HashMap<>();
    private static final int MAXIMUM_NEXT_STEP_INDEX = 5;

    static {
        contextValueMap.put(DashboardCardType.HOME_OWNERS, "Homeowners:3");
        contextValueMap.put(DashboardCardType.TELEMATICS_NEXT_GEN_OFFER, "TelematicsEnrollment:1");
        contextValueMap.put(DashboardCardType.UMBRELLA, "Umbrella:2");
    }

    private final AceAnalyticsFacade analyticsFacade;

    public AceDashboardAdobeTrackingHelper(@NonNull AceAnalyticsFacade analyticsFacade) {
        this.analyticsFacade = analyticsFacade;
    }

    private String buildNextStepsContextValue(List<AceDashboardCard> dashboardCards) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i <= MAXIMUM_NEXT_STEP_INDEX && i < dashboardCards.size(); i++) {
            AceDashboardCard card = dashboardCards.get(i);
            String contextValue = getContextValue(card.getCardType());
            if (!contextValue.isEmpty()) {
                stringBuilder.append(getContextValue(card.getCardType(), i) + ",");
            }
        }
        String string = stringBuilder.toString();
        return string.length() > 0 ? string.substring(0, string.length() - 1) : "";
    }

    private String buildCrossOfferContextValue(List<AceDashboardCard> crossSellOfferCards) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < crossSellOfferCards.size(); i++) {
            builder.append(contextValueMap.get(crossSellOfferCards.get(i).getCardType()));
            if (i < crossSellOfferCards.size() - 1) {
                builder.append("|");
            }
        }
        return builder.toString();
    }

    public void considerTrackingNextStepSelected(@NonNull AceAnalyticsTrackable trackable, @DashboardCardType @NonNull String nextStepCardType, int index) {
        if (!"".equals(getContextValue(nextStepCardType)) && index <= MAXIMUM_NEXT_STEP_INDEX && index >= 0) {
            analyticsFacade.trackAction(trackable, ANALYTICS_INTERNAL_CAMPAIGN_CLICK, getContextValue(nextStepCardType, index));
        }
    }

    private String buildCrossOfferSelectedContextValue(@DashboardCardType String cardType) {
        StringBuilder builder = new StringBuilder();
        if (crossSellOffers.contains(cardType)) {
            builder.append(contextValueMap.get(cardType));
        }
        return builder.toString();
    }

    public void trackCrossSellOfferSelected(@NonNull AceAnalyticsTrackable trackable, @NonNull String dashboardCardType) {
        Map<String, String> contextVariables = new HashMap<>();
        String contextValue = buildCrossOfferSelectedContextValue(dashboardCardType);
        contextVariables.put(CROSS_SELL_OFFER, contextValue);
        contextVariables.put(CROSS_SELL_TYPE_LOWER_CASE, contextValue);
        if (!TextUtils.isEmpty(contextValue))
            analyticsFacade.trackAction(trackable, ANALYTICS_CROSS_SELL_OFFER_CLICK, contextVariables);
    }

    public void considerTrackingNextStepsCrossSellDisplayed(@NonNull List<AceDashboardCard> dashboardCards) {
        String contextValue = buildNextStepsContextValue(dashboardCards);
        if (!getDashfolioFlow().getNextStepsDisplayedToTrack().equals(contextValue)) {
            getDashfolioFlow().setCrossSellOfferDisplayedToTrack(contextValue);
            getDashfolioFlow().setNextStepsDisplayedToTrack(buildNextStepsContextValue(dashboardCards));
            Map<String, String> contextVariables = new HashMap<>();
            contextVariables.put(CROSS_SELL_OFFER, buildCrossOfferContextValue(AceBasicEnumerator.DEFAULT.select(dashboardCards, element -> crossSellOffers.contains(element.getCardType()))));
            analyticsFacade.trackPageShown(new AceDashboardAnalyticsTrackable(analyticsFacade), contextVariables);
        }
    }

    private String getContextValue(@DashboardCardType String cardType, int index) {
        // Index tracked in Adobe should be 1-indexed, but our model has the header at index 0,
        // so we send the 0-indexed value to Adobe as-is.
        return "WN:" + getContextValue(cardType) + ":" + index;
    }

    private String getContextValue(@DashboardCardType String nextStepCardType) {
        switch (nextStepCardType) {
            case DashboardCardType.CHANGE_OF_ADDRESS:
                return "ChangeAddress";
            case DashboardCardType.CLAIMS_ACTIVE_ROADSIDE:
            case DashboardCardType.CLAIMS_ADDITIONAL_ESTIMATE_RECEIVED:
            case DashboardCardType.CLAIMS_AWAITING_PHOTOS:
            case DashboardCardType.CLAIMS_ESTIMATE_RECEIVED:
            case DashboardCardType.CLAIMS_FORMS_AVAILABLE:
            case DashboardCardType.CLAIMS_INSPECTION_REMINDER:
            case DashboardCardType.CLAIMS_INSPECT_DAMAGE:
            case DashboardCardType.CLAIMS_NEED_ADDITIONAL_PHOTOS:
            case DashboardCardType.CLAIMS_REPAIRS_COMPLETE:
            case DashboardCardType.CLAIMS_REPAIRS_STARTED:
            case DashboardCardType.CLAIMS_REPORT_DAMAGE:
            case DashboardCardType.CLAIMS_SCHEDULE_INSPECTION:
                return "ClaimAlert";
            case DashboardCardType.ENROLL_IN_AUTOMATIC_PAYMENT:
                return "AutoPayEnroll";
            case DashboardCardType.ESIGNATURE:
                return "Eforms";
            case DashboardCardType.GET_A_QUOTE:
                return "GetQuote";
            case DashboardCardType.GIVE_BACK:
                return "Giveback";
            case DashboardCardType.MISSING_DRIVERS_LICENSE:
                return "MissingDrvrLicense";
            case DashboardCardType.PAPERLESS_BILLING:
            case DashboardCardType.PAPERLESS_OPTIONS:
            case DashboardCardType.PAPERLESS_POLICY:
                return "PaperlessEnroll";
            case DashboardCardType.PAYMENT_DUE:
                return "PaymentDue";
            case DashboardCardType.PAYMENT_OVER_DUE:
            case DashboardCardType.PAYMENT_PENDING_CANCELLATION:
                return "PaymentOverdue";
            case DashboardCardType.POLICY_LINKING:
                return "PolicyLink";
            case DashboardCardType.PUSH_ENROLLMENT:
                return "Push";
            case DashboardCardType.RENEWAL_UPDATE:
                return "U410";
            case DashboardCardType.SAVED_QUOTES:
                return "SavedQuote";
            case DashboardCardType.TELEMATICS:
                return "DriveEasyRegister";
            case DashboardCardType.TELEMATICS_REGISTRATION:
                return "DriveEasyPhone";
            case DashboardCardType.TEXT_ALERTS:
                return "TextAlert";
            case DashboardCardType.VEHICLE_CARE:
                return "VehicleCare";
            case DashboardCardType.VEHICLE_CARE_RECALL:
                return "RecallAlert";
            default:
                return "";
        }
    }

    private AceDashfolioFlow getDashfolioFlow() {
        return ((AceGeicoAppRegistry) AceBasicContextRegistryLocator.DEFAULT.locateRegistry()).getSessionController().getUserSession().getDashfolioFlow();
    }
}
