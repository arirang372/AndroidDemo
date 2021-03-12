package com.geico.mobile.android.ace.geicoappbusiness.session.flows.model;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState.AceInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.patterns.AceVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants;
import com.geico.mobile.android.ace.geicoappbusiness.logging.AceMediaContentContextData;
import com.geico.mobile.android.ace.geicoappbusiness.navigation.deeplinking.AceBasicDeepLink;
import com.geico.mobile.android.ace.geicoappbusiness.navigation.deeplinking.AceDeepLink;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.AceFlowType;
import com.geico.mobile.android.ace.geicoappmodel.AceCallToAction;
import com.geico.mobile.android.ace.geicoappmodel.AceCard;
import com.geico.mobile.android.ace.geicoappmodel.AceClaimAlertNotification;
import com.geico.mobile.android.ace.geicoappmodel.AceDashboardClaimOrigin;
import com.geico.mobile.android.ace.geicoappmodel.AceNotice;
import com.geico.mobile.android.ace.geicoappmodel.enums.AceGpsAvailabilityState;
import com.geico.mobile.android.ace.geicoappmodel.enums.AceJustForYouCardType;
import com.geico.mobile.android.ace.geicoappmodel.enums.AceMainDashboardCardType;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitAlert;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Flow needed for to hold values related to Dashfolio page processing
 *
 * @author Paul Dienel
 */
public class AceDashfolioFlow extends AceBaseFlowModel {

    private static final float ALPHA_INDICATING_DISABLED = 0.2f;
    private static final float ALPHA_INDICATING_ENABLED = 1.0f;
    private final AceAccountLevelBillingSummary accountLevelBillingSummary = new AceAccountLevelBillingSummary();
    private final List<AceJustForYouCardType> crossSellCardType = new ArrayList<>();
    private final Set<String> pageVariants = new TreeSet<>();
    private final List<AceCard> portfolioCards = new ArrayList<>();
    private AceInformationState alertsInformationState = AceInformationState.UNAVAILABLE;
    private boolean areRecommendationCardsShown = false;
    private String backButtonAction = "";
    private AceCallToAction callToAction = new AceCallToAction();
    private AceDashboardClaimOrigin claimOrigin = AceDashboardClaimOrigin.UNKNOWN;
    private int contentDescriptionIndexForBilling = 0;
    private int contentDescriptionIndexForPolicy = 0;
    private String crossSellOfferDisplayedToTrack = "";
    private AceClaimAlertNotification currentAlert = new AceClaimAlertNotification();
    private AceNotice digitalHugNotice = new AceNotice();
    private AceGpsAvailabilityState gpsAvailabilityStatus = AceGpsAvailabilityState.UNDETERMINED;
    private boolean isTelematicsEnrolled;
    private AceJustForYouCardType justForYouCardType = AceJustForYouCardType.UNKNOWN;
    private AceJustForYouCardType justForYouExpandedCardType = AceJustForYouCardType.COLLAPSED;
    private AceMediaContentContextData mediaContentContextData = new AceMediaContentContextData("", "", "", "");
    private String nextPageAction = AceActionConstants.ACTION_DASHBOARD;
    private String nextStepsDisplayedToTrack = "";
    private AceInformationState outboundPolicySessionEstablishedState = AceInformationState.UNREQUESTED;
    private List<MitAlert> paymentAlerts = new ArrayList<>();
    private boolean policySessionEstablishedOutbound = false;
    private AceInformationState policySessionInformationState = AceInformationState.UNAVAILABLE;
    private AceInformationState portfolioCardsState = AceInformationState.UNAVAILABLE;
    private AceDeepLink postPolicyDownloadDeepLink = new AceBasicDeepLink();
    private String postPolicyDownloadGeniusLinkDestinationOverride = "";
    private AceInformationState prepareForDashfolioState = AceInformationState.UNREQUESTED;
    private List<String> promotedDigitalContextSectionRules = new ArrayList<>();
    private AceMainDashboardCardType propertyCardType = AceMainDashboardCardType.UNKNOWN;
    private boolean userTelematicsNextGenOfferEnrolling;
    private boolean telematicsNextGenOfferEnrollmentComplete;

    public boolean isTelematicsNextGenOfferEnrollmentComplete() {
        return telematicsNextGenOfferEnrollmentComplete;
    }

    public void setTelematicsNextGenOfferEnrollmentComplete(boolean telematicsNextGenOfferEnrollmentComplete) {
        this.telematicsNextGenOfferEnrollmentComplete = telematicsNextGenOfferEnrollmentComplete;
    }

    public boolean isUserTelematicsNextGenOfferEnrolling() {
        return userTelematicsNextGenOfferEnrolling;
    }

    public void setUserTelematicsNextGenOfferEnrolling(boolean userTelematicsNextGenOfferEnrolling) {
        this.userTelematicsNextGenOfferEnrolling = userTelematicsNextGenOfferEnrolling;
    }

    public <O> O acceptAlertsStateVisitor(@NonNull AceInformationStateVisitor<Void, O> visitor) {
        return alertsInformationState.acceptVisitor(visitor);
    }

    public <O> O acceptPolicySessionInformationStateVisitor(@NonNull AceInformationStateVisitor<Void, O> visitor) {
        return policySessionInformationState.acceptVisitor(visitor);
    }

    public <I, O> O acceptPortfolioCardsStateVisitor(@NonNull AceInformationStateVisitor<I, O> visitor, @Nullable I input) {
        return portfolioCardsState.acceptVisitor(visitor, input);
    }

    public <O> O acceptPortfolioCardsStateVisitor(@NonNull AceInformationStateVisitor<Void, O> visitor) {
        return acceptPortfolioCardsStateVisitor(visitor, AceVisitor.NOTHING);
    }

    public void addPageVariant(String variant) {
        pageVariants.add(variant);
    }

    public boolean areRecommendationCardsShown() {
        return areRecommendationCardsShown;
    }

    public void clearPageVariants() {
        pageVariants.clear();
    }

    public AceAccountLevelBillingSummary getAccountLevelBillingSummary() {
        return accountLevelBillingSummary;
    }

    public String getBackButtonAction() {
        return backButtonAction;
    }

    public void setBackButtonAction(String backButtonAction) {
        this.backButtonAction = backButtonAction;
    }

    public AceCallToAction getCallToAction() {
        return callToAction;
    }

    public void setCallToAction(AceCallToAction callToAction) {
        this.callToAction = callToAction;
    }

    @NonNull
    public AceDashboardClaimOrigin getClaimOrigin() {
        return claimOrigin;
    }

    public void setClaimOrigin(@NonNull AceDashboardClaimOrigin claimOrigin) {
        this.claimOrigin = claimOrigin;
    }

    public int getContentDescriptionIndexForBilling() {
        return this.contentDescriptionIndexForBilling;
    }

    public void setContentDescriptionIndexForBilling(int contentDescriptionIndexForBilling) {
        this.contentDescriptionIndexForBilling = contentDescriptionIndexForBilling;
    }

    public int getContentDescriptionIndexForPolicy() {
        return this.contentDescriptionIndexForPolicy;
    }

    public void setContentDescriptionIndexForPolicy(int contentDescriptionIndexForPolicy) {
        this.contentDescriptionIndexForPolicy = contentDescriptionIndexForPolicy;
    }

    @NonNull
    public List<AceJustForYouCardType> getCrossSellCardType() {
        return crossSellCardType;
    }

    @NonNull
    public String getCrossSellOfferDisplayedToTrack() {
        return crossSellOfferDisplayedToTrack;
    }

    public void setCrossSellOfferDisplayedToTrack(@NonNull String crossSellOfferDisplayedToTrack) {
        this.crossSellOfferDisplayedToTrack = crossSellOfferDisplayedToTrack;
    }

    @NonNull
    public AceClaimAlertNotification getCurrentAlert() {
        return currentAlert;
    }

    public void setCurrentAlert(@NonNull AceClaimAlertNotification currentAlert) {
        this.currentAlert = currentAlert;
    }

    @NonNull
    @Override
    public AceFlowType getFlowType() {
        return AceFlowType.DASHFOLIO;
    }

    public AceGpsAvailabilityState getGpsAvailabilityStatus() {
        return gpsAvailabilityStatus;
    }

    public void setGpsAvailabilityStatus(AceGpsAvailabilityState locationStatus) {
        this.gpsAvailabilityStatus = locationStatus;
    }

    public AceJustForYouCardType getJustForYouCardType() {
        return justForYouCardType;
    }

    public void setJustForYouCardType(AceJustForYouCardType justForYouCardType) {
        this.justForYouCardType = justForYouCardType;
    }

    public AceJustForYouCardType getJustForYouExpandedCardType() {
        return justForYouExpandedCardType;
    }

    public void setJustForYouExpandedCardType(AceJustForYouCardType justForYouExpandedCardType) {
        this.justForYouExpandedCardType = justForYouExpandedCardType;
    }

    @NonNull
    public AceMediaContentContextData getMediaContentContextData() {
        return mediaContentContextData;
    }

    public void setMediaContentContextData(@NonNull AceMediaContentContextData mediaContentContextData) {
        this.mediaContentContextData = mediaContentContextData;
    }

    @NonNull
    public String getNextPageAction() {
        return nextPageAction;
    }

    public void setNextPageAction(@NonNull String nextPageAction) {
        this.nextPageAction = nextPageAction;
    }

    @NonNull
    public String getNextStepsDisplayedToTrack() {
        return nextStepsDisplayedToTrack;
    }

    public void setNextStepsDisplayedToTrack(@NonNull String nextStepsDisplayedToTrack) {
        this.nextStepsDisplayedToTrack = nextStepsDisplayedToTrack;
    }

    @NonNull
    public AceNotice getNotice() {
        return digitalHugNotice;
    }

    public void setNotice(@NonNull AceNotice notice) {
        this.digitalHugNotice = notice;
    }

    public AceInformationState getOutboundPolicySessionEstablishedState() {
        return outboundPolicySessionEstablishedState;
    }

    public void setOutboundPolicySessionEstablishedState(AceInformationState outboundPolicySessionEstablishedState) {
        this.outboundPolicySessionEstablishedState = outboundPolicySessionEstablishedState;
    }

    public String getPageVariantsAsString() {
        return TextUtils.join(",", pageVariants);
    }

    @NonNull
    public List<MitAlert> getPaymentAlerts() {
        return paymentAlerts;
    }

    public void setPaymentAlerts(@NonNull List<MitAlert> paymentAlerts) {
        this.paymentAlerts = paymentAlerts;
    }

    @NonNull
    public List<AceCard> getPortfolioCards() {
        return portfolioCards;
    }

    public AceDeepLink getPostPolicyDownloadDeepLink() {
        return postPolicyDownloadDeepLink;
    }

    public void setPostPolicyDownloadDeepLink(AceDeepLink postPolicyDownloadDeepLink) {
        this.postPolicyDownloadDeepLink = postPolicyDownloadDeepLink;
    }

    public String getPostPolicyDownloadGeniusLinkDestinationOverride() {
        return postPolicyDownloadGeniusLinkDestinationOverride;
    }

    public void setPostPolicyDownloadGeniusLinkDestinationOverride(String postPolicyDownloadGeniusLinkDestinationOverride) {
        this.postPolicyDownloadGeniusLinkDestinationOverride = postPolicyDownloadGeniusLinkDestinationOverride;
    }

    public AceInformationState getPrepareForDashfolioState() {
        return prepareForDashfolioState;
    }

    public void setPrepareForDashfolioState(AceInformationState prepareForDashfolioState) {
        this.prepareForDashfolioState = prepareForDashfolioState;
    }

    public List<String> getPromotedDigitalContextSectionRules() {
        return promotedDigitalContextSectionRules;
    }

    public void setPromotedDigitalContextSectionRules(List<String> promotedDigitalContextSectionRules) {
        this.promotedDigitalContextSectionRules = promotedDigitalContextSectionRules;
    }

    public AceMainDashboardCardType getPropertyCardType() {
        return propertyCardType;
    }

    public void setPropertyCardType(AceMainDashboardCardType propertyCardType) {
        this.propertyCardType = propertyCardType;
    }

    public boolean isPolicySessionEstablishedOutbound() {
        return policySessionEstablishedOutbound;
    }

    public void setPolicySessionEstablishedOutbound(boolean policySessionEstablishedOutbound) {
        this.policySessionEstablishedOutbound = policySessionEstablishedOutbound;
    }

    public boolean isTelematicsEnrolled() {
        return this.isTelematicsEnrolled;
    }

    public void setAlertsInformationState(AceInformationState alertsInformationState) {
        this.alertsInformationState = alertsInformationState;
    }

    public void setIsTelematicsEnrolled(boolean isTelematicsEnrolled) {
        this.isTelematicsEnrolled = isTelematicsEnrolled;
    }

    public void setPolicySessionInformationState(@NonNull AceInformationState policySessionInformationState) {
        this.policySessionInformationState = policySessionInformationState;
    }

    public void setPortfolioCardsState(@NonNull AceInformationState portfolioCardsState) {
        this.portfolioCardsState = portfolioCardsState;
    }

    public void updateRecommendationCardsShown() {
        areRecommendationCardsShown = !getJustForYouCardType().isUnknown();
    }
}