package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.viewmodel;

import android.app.Activity;
import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.permission.AceOpenApplicationSettings;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentType;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceLifecycleObserverFactory;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AcePushEnrollment;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceWeatherAlertsEnrollment;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.login.AceEnrollingForLoginWithRefreshTokenUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.logout.AceUserLogout;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.makepayment.AceMakePayment;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard.DashboardCardType;
import com.geico.mobile.android.ace.geicoapppresentation.billing.AceMakePaymentActivity;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.AceDashboardModel;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository.AceDashboardRepository;
import com.geico.mobile.android.ace.geicoapppresentation.dashfolio.AcePayMyBillTextTransformer;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceDashboardCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceCrossSellCardsCallback;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceFragmentLifecycleObserver;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;

import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_PAYMENT_POP_UP_CANCEL;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_PAYMENT_POP_UP_EDIT_PAYMENT_INFO;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.PUSH_ALERT_CANCEL;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.PUSH_ALERT_SETTINGS;

/**
 * Abstract class that provides common data required to populate the dashboard-related pages
 *
 * @author Stephen Borge, GEICO
 */
public abstract class AceDashboardViewModel<R extends AceDashboardRepository, L extends AceFragmentLifecycleObserver>
        extends AceBaseViewModel<AceDashboardModel, R>
        implements AceAnalyticsActionConstants, AceCrossSellCardsCallback, AceLifecycleObserverFactory<L>,
        AceEnrollingForLoginWithRefreshTokenUseCase, AceMakePayment, AcePushEnrollment, AceUserLogout, AceWeatherAlertsEnrollment {

    public AceDashboardViewModel(@NonNull Application application) {
        super(application);
    }

    public void considerDisplayingDashboardDialogs() {
        getRepository().considerDisplayingDashboardDialogs();
    }

    @Override
    public void considerEnrollingInPolicyPush() {
        getRepository().considerEnrollingInPolicyPush();
    }

    @Override
    public void considerEnrollingInWeatherAlerts() {
        getRepository().considerEnrollingInWeatherAlerts();
    }

    public void considerNavigatingToDeepLinkDestination(@NonNull Activity activity) {
        getRepository().considerNavigatingToDeepLinkDestination(activity);
    }

    public void considerNavigatingToMessageCenter() {
        getRepository().considerNavigatingToMessageCenter();
    }

    @Override
    public void considerRetrievingWeatherAlerts() {
        getRepository().considerRetrievingWeatherAlerts();
    }

    public void considerTriggeringDashboardAirshipEvents() {
        getRepository().considerTriggeringDashboardAirshipEvents();
    }

    @Override
    public void considerUpdatingPolicyChangesTransactionDate() {
        getRepository().considerUpdatingPolicyChangesTransactionDate();
    }

    public String getMakePaymentDialogMessage() {
        return getRepository().getMakePaymentDialogMessage();
    }

    @Override
    public void navigateToTelematicsNextGenOffer() {
        openFullSite(MitWebLinkNames.TELEMATICS_NEXTGEN_OFFER);
    }

    @Override
    public void logCrossSellOfferSelected(@DashboardCardType String cardType, @NonNull String eventType) {
        getRepository().logCrossSellOfferSelected(cardType, eventType);
    }

    public void logDisplayedDashboardMetrics() {
        getRepository().logDisplayedDashboardMetrics();
    }

    public void logWhatIsNextCardSelected(@DashboardCardType String cardType) {
        getRepository().logWhatIsNextCardSelected(cardType);
    }

    @Override
    public void logout() {
        getRepository().logout();
    }

    public void logoutSingleVehiclePolicy() {
        getRepository().logoutSingleVehiclePolicy();
    }

    public void manageCards() {
        getRepository().manageCards();
    }

    @Override
    public void navigateToCrossSell(@NonNull AceBaseActivity activity, @NonNull AceDashboardCard card) {
        getRepository().navigateToCrossSell(activity, card);
    }

    public void navigateToMakePayment() {
        trackAction(ANALYTICS_M5_DASHBOARD_PAYMENT_POP_UP_EDIT_PAYMENT_SELECTED, M5_DASHBOARD_PAYMENT_POP_UP_EDIT_PAYMENT_INFO);
        startActivity(getApplication(), AceMakePaymentActivity.class);
    }

    @Override
    public void onBackPressed() {
        getRepository().onBackPressed();
    }

    @Override
    protected void onCleared() {
        getRepository().onCleared();
    }

    @Override
    public void onCreateView() {
        getRepository().initializeModel();
    }

    @Override
    public void onKeepMeLoggedInDiscarded() {
        getRepository().onKeepMeLoggedInDiscarded();
    }

    @Override
    public void onKeepMeLoggedInSelected() {
        getRepository().onKeepMeLoggedInSelected();
    }

    public void onPaymentCancel() {
        trackAction(ANALYTICS_M5_DASHBOARD_PAYMENT_POP_UP_CANCEL_SELECTED, M5_DASHBOARD_PAYMENT_POP_UP_CANCEL);
    }

    @Override
    public void onPushAlertCancel() {
        trackAction(ANALYTICS_PUSH_ALERT_CANCEL, PUSH_ALERT_CANCEL);
    }

    public void openCatastropheAlert() {
        getRepository().openCatastropheAlert();
    }

    @Override
    public void openDeviceNotificationSettings() {
        trackAction(ANALYTICS_PUSH_ALERT_SETTINGS, PUSH_ALERT_SETTINGS);
        new AceOpenApplicationSettings().executeWith(getApplication());
    }

    void resetGreeting() {
        getRepository().resetGreeting();
    }

    @Override
    public void sendPushEnrollmentRequest(@NonNull AcePushMessagingEnrollmentType type) {
        getRepository().sendPushEnrollmentRequest(type);
    }

    public void trackWhatIsNextCardAction(@DashboardCardType String cardType) {
        getRepository().trackWhatIsNextCardAction(cardType);
    }

    public void updateBillingQuickActionText() {
        getModel().setPayMyBillCardText(getString(new AcePayMyBillTextTransformer().transform(getRepository().getSessionController().getPolicySession().getPolicy())));
    }

    @Override
    public void updateSelectedCrossSellCardToFlow(@NonNull AceDashboardCard card) {
        getRepository().updateCrossSellCardToFlow(card);
    }

    public void updateSelectedWhatIsNextCardDetails(@NonNull AceDashboardCard card) {
        getRepository().updateSelectedWhatIsNextCardDetails(card);
    }

    @Override
    public void updateWeatherFlowForWeatherCardSelected(boolean value) {
        getRepository().updateWeatherFlowForWeatherCardSelected(value);
    }
}