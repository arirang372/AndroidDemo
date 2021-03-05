package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceBaseInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.types.date.AceCalendarDate;
import com.geico.mobile.android.ace.coreframework.webservices.contexts.AceServiceContext;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceRequest;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceResponse;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskType;
import com.geico.mobile.android.ace.geicoappbusiness.digitalhug.AceDigitalHugSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.experiments.AceShowDashboardReportAClaimCardExperiment;
import com.geico.mobile.android.ace.geicoappbusiness.listeners.AceEventSubjectUnusedListener;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.matchers.AceNoticeSubTypeMatcher;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushEnrollmentReaction;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentType;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceClientRegistrationReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AcePrepareToUpdateTextAlertsServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsGetRelatedDriversServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsRefreshServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsRetrieveDriverDataServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePickySessionStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePickyUserSessionTypeVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceLoginFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceQuoteFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceWeatherFlow.AceWeatherAlertsEnrollmentState;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsConstants.TelematicsDriverType;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.telephony.AceDeviceTelephonySupportType;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.AceVehicleCareCardDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.quote.AceMoatSalesQuoteFromMit;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.textAlerts.AceTextAlertPreferencesFromMit;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceDriveEasyQuickActionDisplayedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AcePrepareToUpdateTextAlertsUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AcePushEnrollment;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceWeatherAlertsEnrollment;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.login.AceEnrollingForLoginWithRefreshTokenUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.logout.AceUserLogout;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.makepayment.AceMakePayment;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.quote.AceRetrieveMoatSalesQuotes;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.telematics.AceTelematicsGetRelatedDriversUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.telematics.AceTelematicsRefreshUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.telematics.AceTelematicsRetrieveDriverDataUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.weather.AcePrepareForWeatherAlertsEnrollmentServiceHandler;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.weather.AceRetrieveWeatherAlertsHandler;
import com.geico.mobile.android.ace.geicoappmodel.AceClaimAlertNotification;
import com.geico.mobile.android.ace.geicoappmodel.AceNotice;
import com.geico.mobile.android.ace.geicoappmodel.AcePaymentInformation;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard.DashboardCardType;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.enums.telematics.AceBaseTelematicsFeatureModeVisitor;
import com.geico.mobile.android.ace.geicoappmodel.enums.userconnectiontechnique.AceUserConnectionTechnique;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.backgroundservices.AceConsiderUpdatingPolicyChangesTransactionDate;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.AceDashboardLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.AceDashboardModel;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.AceDashboardModelFactory;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.AceDashboardTelematicsCurrentDriverModel;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository.rules.AceTelematicsButtonTypeFromDriverModel;
import com.geico.mobile.android.ace.geicoapppresentation.dashfolio.AceDashfolioGreetingNameFromSession;
import com.geico.mobile.android.ace.geicoapppresentation.emittedstate.dashboard.AceDashboardStateEmitter;
import com.geico.mobile.android.ace.geicoapppresentation.framework.helpers.AceVehicleRecallAlertActionTrackingHelper;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceDashboardCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceCrossSellCardFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceCrossSellMetricsHelper;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceCrossSellRepository;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceMoatSalesQuotesNavigationHelper;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceMoatSalesQuotesRecallServiceReaction;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceUpdateSelectedCrossSellCardHelper;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.pushnotification.AceMessagingEnrollmentRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.pushnotification.AcePushEnrollmentHelper;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.telematics.AceTelematicsGetRelatedDriversRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.telematics.AceTelematicsRetrieveDriverDataRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.textalerts.AcePrepareToUpdateTextAlertsRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceRecommendations;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.recall.mvvm.view.AceRecallActivity;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.services.AceMoatSalesQuotesRecallRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.initializers.AceModelInitializer;
import com.geico.mobile.android.ace.geicoapppresentation.learnmore.AceLearnMoreTutorialNavigationActivity;
import com.geico.mobile.android.ace.geicoapppresentation.login.policysession.AceVehicleCarePresentationConstants;
import com.geico.mobile.android.ace.geicoapppresentation.login.policysession.AceVehicleCareRecallAlertsDirector;
import com.geico.mobile.android.ace.geicoapppresentation.logout.AceRapidSessionFinisher;
import com.geico.mobile.android.ace.geicoapppresentation.makepayment.AceMakePaymentService;
import com.geico.mobile.android.ace.geicoapppresentation.messaging.urbanairship.AceMessageCenterActivity;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.AceRefreshTelematicsCredentials;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.repository.AceTelematicsAirshipEventsRuleApplicator;
import com.geico.mobile.android.ace.geicoapppresentation.transformers.AceMostRecentStoredAccountTextFromPolicy;
import com.geico.mobile.android.ace.geicoapppresentation.vehiclecare.AceVehicleCarePrerequisitesActivity;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.repository.AcePrepareForWeatherAlertsEnrollmentRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.repository.AcePrepareForWeatherAlertsEnrollmentServiceReaction;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.repository.AceRetrieveWeatherAlertsRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.repository.AceRetrieveWeatherAlertsServiceReaction;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.view.AceWeatherActivity;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitAlert;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitClientRegistrationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitMoatSalesQuotesRecallRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitMoatSalesQuotesRecallResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareForWeatherAlertsEnrollmentResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateTextAlertsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateTextAlertsResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPushNotificationsEnrollmentResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRetrieveWeatherAlertsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRetrieveWeatherAlertsResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitTextAlertDeviceSubscription;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsDriver;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsDriverDetails;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsRelatedDriver;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitBaseTelematicsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsGetRelatedDriversRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsGetRelatedDriversResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsRefreshResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsRetrieveDriverDataRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.REGISTER_CLIENT;
import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.TELEMATICS_GET_RELATED_DRIVERS;
import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.TELEMATICS_RETRIEVE_DRIVER_DATA;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_WEATHER_ALERT_LIST;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_DASHBOARD_CATASTROPHE_ALERT;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_DASHBOARD_CATASTROPHE_ALERT_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_DASHBOARD_CROSS_SELL_BUTTON_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_DASHBOARD_PAYMENT_POP_UP_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_DASHBOARD_PAYMENT_POP_UP_PAY_NOW_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_DASHBOARD_WEATHER_ALERT_CARD;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_TEST_TARGET_3;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_CATASTROPHE_ALERT;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_CATASTROPHE_ALERT_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_PAYMENT_POP_UP_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_PAYMENT_POP_UP_PAY_NOW;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.M5_DASHBOARD_WEATHER_ALERT_CARD;
import static com.geico.mobile.android.ace.geicoapppresentation.dashfolio.AceDigitalHugConstants.CHANGE_OF_ADDRESS;
import static com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceGetAQuoteConstants.CROSS_SELL_PROBABILITY_SERVICE_COMPLETED;
import static com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceGetAQuoteConstants.CROSS_SELL_PROBABILITY_SERVICE_FAILURE;
import static com.geico.mobile.android.ace.geicoapppresentation.messaging.urbanairship.AceMessageCenterActivity.MESSAGE_ID;
import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames.CATASTROPHE_ALERT;

/**
 * Object that provides data required to update the Home page
 * within the app and also implements the business use cases invoked from that page.
 * <p>
 * Individual Repositories should be STATELESS as much as possible.
 *
 * @author Stephen Borge, GEICO
 */
public class AceDashboardRepository
        extends AceTierRepository<AceDashboardModel>
        implements AceCrossSellRepository<AceDashboardModel>, AceEnrollingForLoginWithRefreshTokenUseCase, AceLoginConstants,
        AceMakePayment, AceModelInitializer, AcePrepareForWeatherAlertsEnrollmentServiceHandler, AcePrepareToUpdateTextAlertsUseCase, AcePushEnrollment, AceRetrieveMoatSalesQuotes,
        AceRetrieveWeatherAlertsHandler, AceTelematicsGetRelatedDriversUseCase, AceTelematicsRetrieveDriverDataUseCase,
        AceUserLogout, AceVehicleCarePresentationConstants, AceWeatherAlertsEnrollment, AceWeatherPickedForYouHandler {

    public AceDashboardRepository(@NonNull Application application) {
        super(application);
    }

    private void addClaimAlertCards(List<AceDashboardCard> dashboardCards) {
        dashboardCards.addAll(getClaimAlertCards());
    }

    private void addCrossSellCards(List<AceDashboardCard> dashboardCards) {
        dashboardCards.addAll(new AceCrossSellCardFactory().create(this));
    }

    @Override
    protected void configureViewBackingModel() {
        configureSwitchPolicyBanner();
        showBottomNavigationWith(R.id.dashboardBottomNavigationItem);
    }

    public void considerDisplayingDashboardDialogs() {
        new AceEnrollWithDeviceUnlockHelper().considerDisplayingDialogWith(this);
        new AceKeepMeLoggedInHelper().considerDisplayingDialogWith(this);
    }

    @Override
    public void considerEnrollingInPolicyPush() {
        new AcePushEnrollmentHelper(getRegistry(), this, getStateEmitter()).considerEnrollingInPushWithExistingRegistration(AcePushMessagingEnrollmentType.ENROLL_POLICY);
    }

    @Override
    public void considerEnrollingInWeatherAlerts() {
		/*
		  This service call is only for the pilot project to query 25k user limit. This will be removed when and if the project
		  moves forward from the pilot state.
		 */
        if (isAllowedToCallPrepareWeatherAlertEnrollmentService()) {
            callService(new AcePrepareForWeatherAlertsEnrollmentRequestFactory(getSessionController()).create(getSessionController()), new AcePrepareForWeatherAlertsEnrollmentServiceReaction(this));
            getPolicySession().getWeatherFlow().setIs25kUserLimitCheckedInThisPolicySession(true);
            return;
        }
        updateWeatherPickedForYouCardVisibility();
    }

    private void considerErsRequestConfirmationNavigation(@ViewTag @NonNull String viewTag, @NonNull Context context, AceClaimAlertNotification helper) {
        if (ViewTag.ERS_REQUEST_CONFIRMATION.equals(viewTag)) {
            navigateToErsRequestConfirmation(context, helper);
        }
    }

    private void considerLoggingCatastropheAlertDisplayed() {
        if (getModel().hasCatastropheAlert()) {
            trackAction(ANALYTICS_M5_DASHBOARD_CATASTROPHE_ALERT, M5_DASHBOARD_CATASTROPHE_ALERT);
        }
    }

    private void considerLoggingRecallCardDisplayed() {
        if (!getModel().getVehicleCareRecallVins().isEmpty()) {
            new AceVehicleRecallAlertActionTrackingHelper(getAnalyticsFacade(), getTrackable()).considerTrackingVehicleRecallAlertsAction(getVehicleCareFlow());
            new AceWhatIsNextMetricsHelper().logWhatIsNextCardDisplayed(DashboardCardType.VEHICLE_CARE_RECALL, this, getModel());
        }
    }

    public void considerNavigatingToDeepLinkDestination(@NonNull Activity activity) {
        if (!getSessionController().notDeepLinking() && !isDeeplinkingToWeatherAlertsListPage()) {
            new AceDeepLinkNavigationHelper().navigateToDestination(activity, this);
        }
    }

    public void considerNavigatingToMessageCenter() {
        String messageCenterItemId = getApplicationSession().getGeniusLinkFlow().getMessageCenterItemId();
        if (getSessionController().notDeepLinking() && !messageCenterItemId.isEmpty()) {
            navigateToMessageCenter(getApplication().getApplicationContext(), messageCenterItemId);
            getApplicationSession().getGeniusLinkFlow().setMessageCenterItemId("");
        }
    }

    private void considerNavigationBasedOnRecallQuotes() {
        startActivity(getQuoteFlow().getRecallQuoteListForSelectedCardType().isEmpty()
                ? AceLearnMoreTutorialNavigationActivity.class : AceRecallActivity.class);
    }

    @Override
    public void considerRetrievingDriverData() {
        getFeatureModeForTelematics().acceptVisitor(new AceBaseTelematicsFeatureModeVisitor<Void, Void>() {
            @Override
            protected Void visitAny(Void input) {
                if (!isCurrentUserAuthenticatedForTelematics()) {
                    return visitDisabled(input);
                }
                retrieveTelematicsDriverData();
                return NOTHING;
            }

            @Override
            public Void visitDisabled(Void input) {
                setTelematicsFlowDriverStateToUnavailable();
                return NOTHING;
            }
        });
    }

    private void considerRetrievingTelematicsRelatedDrivers() {
        getTelematicsFlow().acceptRelatedDriversInformationStateVisitor(new AceBaseInformationStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                if (shouldRetrieveTelematicsRelatedDrivers()) {
                    retrieveTelematicsRelatedDrivers();
                    return NOTHING;
                }
                return NOTHING;
            }

            @Override
            public Void visitCurrent(Void input) {
                onGetRelatedDriversSuccessWith(getTelematicsFlow().getRelatedDrivers());
                return NOTHING;
            }
        });

    }

    @Override
    public void considerRetrievingWeatherAlerts() {
        if (getRegistry().getFeatureConfiguration().getModeForWeatherAlerts().isEnabled() && isEnrolledToWeatherAlerts()) {
            callService(createRetrieveWeatherAlertsRequest(), new AceRetrieveWeatherAlertsServiceReaction(this));
        }
    }

    private void considerRunningVehicleCareAlerts() {
        new AceVehicleCareRecallAlertsDirector(getRegistry()).start();
    }

    private void considerSettingTextAlertsEligibility(boolean isEligibleForTextAlertsEnrollment) {
        getModel().setEligibleForTextAlertsEnrollment(isEligibleForTextAlertsEnrollment);
        if (isEligibleForTextAlertsEnrollment) {
            new AceWhatIsNextMetricsHelper().logWhatIsNextCardDisplayed(DashboardCardType.TEXT_ALERTS, this, getModel());
        }
    }

    private void considerTrackingExperimentCardSelected(@DashboardCardType String cardType) {
        if (DashboardCardType.POLICY_LINKING.equals(cardType)) {
            trackAction(ANALYTICS_TEST_TARGET_3, "2946406_SelectLinkPolicy");
        }
    }

    private void considerTrackingExperimentalCardDisplayed() {
        if (AceBasicEnumerator.DEFAULT.anySatisfy(getModel().getDashboardCards(), card -> DashboardCardType.POLICY_LINKING.equals(card.getCardType()))) {
            trackAction(ANALYTICS_TEST_TARGET_3, "2946406_DisplayLinkPolicy");
        }
    }

    private void considerTrackingNextStepsDisplayed() {
        if (!isAnyRequestInFlight()) {
            new AceDashboardAdobeTrackingHelper(getAnalyticsFacade()).considerTrackingNextStepsCrossSellDisplayed(getModel().getDashboardCards());
        }
    }

    private void considerTrackingWeatherCard() {
        if (!getPolicySession().getWeatherFlow().shouldShowPickedForYouWeatherCard()) return;
        trackAction(ANALYTICS_M5_DASHBOARD_WEATHER_ALERT_CARD, M5_DASHBOARD_WEATHER_ALERT_CARD);
    }

    public void considerTriggeringDashboardAirshipEvents() {
        new AceDashboardAirshipEventsRuleApplicator().executeWith(getRegistry());
    }

    private void considerTrimmingCardList(List<AceDashboardCard> cards) {
        if (cards.size() <= 6) return;
        cards.subList(6, cards.size()).clear();
    }

    public void considerUpdatingPolicyChangesTransactionDate() {
        new AceConsiderUpdatingPolicyChangesTransactionDate().executeWith(getRecommendations());
    }

    private void considerVehicleCareRecallAlertNavigation(@ViewTag @NonNull String viewTag, @NonNull Context context) {
        if (ViewTag.DASHBOARD.equals(viewTag)) {
            trackVehicleRecallAlertExitUrl();
            startActivity(context, AceVehicleCarePrerequisitesActivity.class);
        }
    }

    private AceEventSubjectUnusedListener createCrossSellProbabilityServiceStatusListener(String eventId) {
        return new AceEventSubjectUnusedListener(eventId) {
            @Override
            protected void onEvent() {
                updateDashboardCards();
                logCrossSellCardsDisplayed();
            }
        };
    }

    private MitTelematicsGetRelatedDriversRequest createGetRelatedDriversRequest() {
        return new AceTelematicsGetRelatedDriversRequestFactory(getSessionController()).create();
    }

    private MitMoatSalesQuotesRecallRequest createMoatSalesQuotesRequest() {
        return new AceMoatSalesQuotesRecallRequestFactory(getSessionController()).create(getSessionController());
    }

    private AceDashboardModel createModel() {
        return new AceDashboardModelFactory().create(getApplication(), getRegistry());
    }

    @NonNull
    @Override
    protected AceDashboardLiveData createModelLiveData() {
        return new AceDashboardLiveData(createModel(), this);
    }

    private MitPrepareToUpdateTextAlertsRequest createPrepareToUpdateTextAlertNotificationRequest() {
        return new AcePrepareToUpdateTextAlertsRequestFactory(getSessionController()).create();
    }

    private MitTelematicsRetrieveDriverDataRequest createRetrieveDriverDataRequest() {
        return new AceTelematicsRetrieveDriverDataRequestFactory(getSessionController()).create();
    }

    private MitRetrieveWeatherAlertsRequest createRetrieveWeatherAlertsRequest() {
        return new AceRetrieveWeatherAlertsRequestFactory(getSessionController()).create();
    }

    @NonNull
    @Override
    protected AceDashboardStateEmitter createStateEmitter() {
        return new AceDashboardStateEmitter();
    }

    @NonNull
    protected AceEventSubjectUnusedListener createVehicleCareRecallAlertServiceListener() {
        return new AceEventSubjectUnusedListener(AceVehicleCarePresentationConstants.VEHICLE_CARE_RECALL_ALERT_SERVICE_COMPLETED) {
            @Override
            protected void onEvent() {
                getModel().setVehicleCareRecallVins(getVehicleCareFlow().getVehicleCareVins());
                considerLoggingRecallCardDisplayed();
                trackVehicleRecallAlertExitUrl();
                updateDashboardCards();
            }
        };
    }

    private AceWeatherAlertsEnrollmentState getAceWeatherAlertsEnrollmentState() {
        return getPolicySession().getWeatherFlow().retrieveWeatherAlertEnrollmentState(getPolicySession().getPolicyNumber(), getApplication());
    }

    private AceNotice getChangeOfAddressNotice() {
        return AceBasicEnumerator.DEFAULT.firstMatch(getPolicy().getHeaderNotices(),
                new AceNoticeSubTypeMatcher(CHANGE_OF_ADDRESS), new AceNotice());
    }

    private List<AceDashboardCard> getClaimAlertCards() {
        return new AceClaimAlertsCardFactory().create(this);
    }

    @NonNull
    @Override
    public String getCrossSellSectionHeaderText() {
        return getString(R.string.featuredInsuranceOptions);
    }

    private AceDashboardTelematicsCurrentDriverModel getCurrentDriverModel() {
        return getModel().getTelematicsCurrentDriverModel();
    }

    private String getCurrentUserDriverId() {
        return getApplicationSession().getUserFlow().getPerson().getDriverClientId();
    }

    @NonNull
    public AceDashboardSharedPreferencesDao getDashboardSharedPreferencesDao() {
        return new AceBasicDashboardSharedPreferencesDao(getRegistry());
    }

    @NonNull
    public AceDigitalHugSharedPreferencesDao getDigitalHugSharedPreferencesDao() {
        return new AceDigitalHugSharedPreferencesDao(getRegistry());
    }

    private String getFormattedAmountDue() {
        return getPaymentDetails().getAmountDue().toString();
    }

    private String getFormattedCurrentDate() {
        return getPolicySession().getCurrentDateTime().getCurrentDate().asUsShortString();
    }

    private AceLoginFlow getLoginFlow() {
        return getApplicationSession().getLoginFlow();
    }

    public String getMakePaymentDialogMessage() {
        return getApplication().getString(R.string.makePaymentDialogMessage, getFormattedCurrentDate(), getFormattedAmountDue(), getMostRecentAccountText());
    }

    @Override
    @StringRes
    public int getMoatCardFirstButtonText() {
        return R.string.viewOptions;
    }

    @NonNull
    @Override
    public AceInformationState getMoatSalesQuoteInformationState() {
        return getQuoteFlow().getMoatSalesQuoteInformationState();
    }

    private String getMostRecentAccountText() {
        return new AceMostRecentStoredAccountTextFromPolicy(getApplication()).transform(getPolicy());
    }

    @NonNull
    @Override
    public List<MitAlert> getPaymentAlerts() {
        return getDashfolioFlow().getPaymentAlerts();
    }

    private AcePaymentInformation getPaymentDetails() {
        return getPolicy().getPaymentDetails();
    }

    @NonNull
    @Override
    public AceQuoteFlow getQuoteFlow() {
        return getPolicySession().getQuoteFlow();
    }

    @NonNull
    @Override
    public AceRecommendations getRecommendations() {
        return new AceRecommendations(getRegistry());
    }

    @NonNull
    @Override
    public AceDashboardModel getResetModel() {
        return createModel();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public AceDashboardStateEmitter getStateEmitter() {
        return super.getStateEmitter();
    }

    @VisibleForTesting
    protected AceTelematicsRefreshUseCase getTelematicsRefreshHandler() {
        return new AceRefreshTelematicsCredentials(this, this);
    }

    @Override
    @SuppressWarnings("squid:S1185")
// re-implement in this package so AcePolicySessionEstablisherFromSelector has access
    protected <O extends MitResponse, I extends MitRequest> void handleServiceFailure(AceServiceContext<I, O> serviceContext) {
        super.handleServiceFailure(serviceContext);
    }

    @Override
    public void initializeModel() {
        resetGreeting();
        new AceShowDashboardReportAClaimCardExperiment(getRegistry(), getModel().isShowReportAClaimCardExperiment()).start();
    }

    private boolean isAllowedToCallPrepareWeatherAlertEnrollmentService() {
        return getSessionController().isInPolicySession() && getFeatureConfiguration().getModeForWeatherAlerts().isEnabled() &&
                !isEnrolledToWeatherAlerts() &&
                !getPolicySession().getWeatherFlow().is25kUserLimitCheckedInThisPolicySession();
    }

    /**
     * The current user must have completed telematics two-factor authentication
     * to retrieve the data.
     */
    private boolean isCurrentUserAuthenticatedForTelematics() {
        String telematicsDriverId = getTelematicsPreferences().getDriverId();
        return !TextUtils.isEmpty(telematicsDriverId) && telematicsDriverId.equals(getCurrentUserDriverId());
    }

    private boolean isDeeplinkingToWeatherAlertsListPage() {
        return ACTION_WEATHER_ALERT_LIST.equals(getApplicationSession().getGeniusLinkFlow().getGeniusLinkDestinationOverride());
    }

    public boolean isDeviceTelephonyEnabled() {
        return AceDeviceTelephonySupportType.SUPPORT_TELEPHONY.isApplicable(getApplication().getApplicationContext());
    }

    private boolean isDriverCreatedInPastFiveDays(@NonNull AceTelematicsDriverDetails driverDetails) {
        return driverDetails.isDriverCreatedInPastFiveDays(getPolicySession().getCurrentDateTime().getCurrentDate());
    }

    private boolean isEnrolledToWeatherAlerts() {
        return getAceWeatherAlertsEnrollmentState().isEnrolledToWeatherAlerts();
    }

    public boolean isNotEnrolledInVehicleCare() {
        return new AceVehicleCareCardDerivation().deriveValueFrom(getRegistry());
    }

    private boolean isPolicyEligibleForLoyaltyGreeting() {
        return getFeatureConfiguration().getModeForCustomerLoyaltyMessageOnDashboard().isEnabled() &&
                AceCalendarDate.createToday().isEarlierThan(getPolicy().getEffectiveDate().addDays(15));
    }

    public boolean isTelematicsTwoFactorAuthenticationIncomplete() {
        return new AceTelematicsSharedPreferencesDao(getRegistry()).isMissingStartupCredentials();
    }

    protected void logClaimAlertCardsDisplayed() {
        new AceWhatIsNextMetricsHelper().logWhatIsNextCardsDisplayed(getClaimAlertCards(), this, getModel());
    }

    private void logCrossSellCardsDisplayed() {
        new AceCrossSellMetricsHelper().logCrossSellCardsDisplayed(getModel().getDashboardCards(), AceDashboardRepository.this, getPolicySession());
        new AceCrossSellMetricsHelper().trackWeatherCardDisplayed(getModel().getDashboardCards(), AceDashboardRepository.this);
    }

    public void logCrossSellOfferSelected(@DashboardCardType String cardType, @NonNull String eventType) {
        trackAction(ANALYTICS_M5_DASHBOARD_CROSS_SELL_BUTTON_SELECTED, "M5Dashboard:CrossSellButtonSelected");
        new AceCrossSellMetricsHelper().logCrossSellOfferSelected(cardType, this, eventType);
        new AceDashboardAdobeTrackingHelper(getAnalyticsFacade()).trackCrossSellOfferSelected(getTrackable(), cardType);
    }

    private void logDisplayOfBillingQuickAction() {
        Map<String, String> quickActionTextToEventName = new HashMap() {{
            put(getString(R.string.viewBilling), M5_DASHBOARD_QUICK_ACTION_VIEW_BILLING_DISPLAYED);
            put(getString(R.string.viewUpcomingPayment), M5_DASHBOARD_QUICK_ACTION_VIEW_UPCOMING_PAYMENT_DISPLAYED);
            put(getString(R.string.viewUpcomingWithdrawal), M5_DASHBOARD_QUICK_ACTION_VIEW_UPCOMING_WITHDRAWAL_DISPLAYED);
        }};
        logEvent(quickActionTextToEventName.get(getModel().getPayMyBillCardText().get()));
    }

    public void logDisplayedDashboardMetrics() {
        logDisplayOfBillingQuickAction();
        logWhatIsNextCardsDisplayed();
        logEvent(M5_DASHBOARD_DISPLAYED);
        logCrossSellCardsDisplayed();
        considerLoggingCatastropheAlertDisplayed();
    }

    private void logDriveEasyQuickActionDisplayed() {
        logEvent(new AceDriveEasyQuickActionDisplayedEvent(new AceTelematicsButtonTypeFromDriverModel().transform(getCurrentDriverModel())));
    }

    @Override
    public void logWeatherPickedForYouCardEvent() {
        logEvent(M5_DASHBOARD_WEATHER_ALERT_DISPLAYED);
    }

    public void logWhatIsNextCardSelected(@DashboardCardType String cardType) {
        int cardIndex = AceBasicEnumerator.DEFAULT.indexOfFirstMatch(getModel().getDashboardCards(), card -> card.getCardType().equals(cardType));
        new AceWhatIsNextMetricsHelper().logWhatIsNextCardSelected(cardType, this, getModel());
        new AceDashboardAdobeTrackingHelper(getAnalyticsFacade()).considerTrackingNextStepSelected(getTrackable(), cardType, cardIndex);
        considerTrackingExperimentCardSelected(cardType);
    }

    private void logWhatIsNextCardsDisplayed() {
        new AceWhatIsNextMetricsHelper().logWhatIsNextCardsDisplayed(getModel().getDashboardCards(), this, getModel());
        considerTrackingExperimentalCardDisplayed();
    }

    @Override
    public void logout() {
        new AceRapidSessionFinisher(getRegistry(), AceDashboardRepository.this).logout();
    }

    public void logoutSingleVehiclePolicy() {
        getSessionController().acceptVisitor(new AcePickyUserSessionTypeVisitor<Void>() {
            @Override
            public Void visitSingleVehiclePolicy(Void input) {
                logout();
                return NOTHING;
            }
        });
    }

    @Override
    public void makePayment() {
        trackAction(ANALYTICS_M5_DASHBOARD_PAYMENT_POP_UP_PAY_NOW_SELECTED, M5_DASHBOARD_PAYMENT_POP_UP_PAY_NOW);
        new AceMakePaymentService(this).makePayment();
    }

    public void manageCards() {
        registerClient();
        prepareToUpdateTextAlerts();
        retrieveDashboardHeaderContent();
        considerRunningVehicleCareAlerts();
        updateDashboardCards();
    }

    @Override
    public void navigateTo(@NonNull @ViewTag String viewTag, @NonNull Context context, Object helper) {
        considerErsRequestConfirmationNavigation(viewTag, context, (AceClaimAlertNotification) helper);
        considerVehicleCareRecallAlertNavigation(viewTag, context);
    }

    public void navigateToCrossSell(@NonNull AceBaseActivity<?, ?> activity, @NonNull AceDashboardCard card) {
        new AceMoatSalesQuotesNavigationHelper().navigateToCrossSell(activity, card, this);
    }

    @Override
    public void navigateToLearnMoreTutorialView() {
        startActivity(getApplication(), AceLearnMoreTutorialNavigationActivity.class);
    }

    private void navigateToMessageCenter(Context context, String messageId) {
        Intent intent = new Intent(context, AceMessageCenterActivity.class);
        intent.putExtra(MESSAGE_ID, messageId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onClientRegistrationFailure() {
        onTelematicsDataUnavailable();
    }

    @Override
    public void onClientRegistrationSuccess() {
        considerRetrievingDriverData();
    }

    @Override
    public void onGetRelatedDriversFailure(@NonNull MitResponse response) {
        getTelematicsFlow().setRelatedDrivers(new ArrayList<>());
        getTelematicsFlow().setRelatedDriversInformationState(AceInformationState.UNAVAILABLE);
        getModel().setRelatedDrivers(new ArrayList<>());
    }

    @Override
    public void onGetRelatedDriversSuccess(@NonNull MitTelematicsGetRelatedDriversResponse response) {
        getTelematicsFlow().setRelatedDriversInformationState(AceInformationState.CURRENT);
        getTelematicsFlow().setRelatedDrivers(response.getRelatedDrivers());
        onGetRelatedDriversSuccessWith(response.getRelatedDrivers());
    }

    private void onGetRelatedDriversSuccessWith(List<AceTelematicsRelatedDriver> relatedDrivers) {
        getModel().setRelatedDrivers(relatedDrivers);
        updateDashboardCards();
        new AceTelematicsAirshipEventsRuleApplicator().executeWith(getRegistry());
        new AceWhatIsNextMetricsHelper().considerLoggingDisplayedDriveEasyCard(getModel().getDashboardCards(), this, getModel());
    }

    @Override
    public void onKeepMeLoggedInDiscarded() {
        getLoginSettingsDao().storeDiscardRefreshTokenBeforeNextLogin(true);
        getLoginFlow().setUserConnectionTechnique(AceUserConnectionTechnique.LOGIN_EACH_TIME);
    }

    @Override
    public void onKeepMeLoggedInSelected() {
        AceLoginSettingsDao loginSettingsDao = getLoginSettingsDao();
        loginSettingsDao.write(REFRESH_TOKEN_KEY, getLoginFlow().getMostRecentRefreshToken());
        loginSettingsDao.storeDiscardRefreshTokenBeforeNextLogin(false);
        loginSettingsDao.storeReasonForRetainingRefreshToken(AceLoginConstants.AUTOMATIC_LOGIN);
        getLoginFlow().setUserConnectionTechnique(AceUserConnectionTechnique.KEEP_ME_LOGGED_IN);
    }

    @Override
    public void onMoatSalesQuotesRecallFailure(@NonNull MitResponse response) {
        getQuoteFlow().setMoatSalesQuoteInformationState(AceInformationState.UNAVAILABLE);
        considerNavigationBasedOnRecallQuotes();
    }

    @Override
    public void onMoatSalesQuotesRecallSuccess(@NonNull MitMoatSalesQuotesRecallResponse response) {
        getQuoteFlow().setMoatSalesQuoteInformationState(AceInformationState.CURRENT);
        getQuoteFlow().setSalesQuoteResponse(new AceMoatSalesQuoteFromMit().transform(response));
        getQuoteFlow().populateRentalQuoteInformationState();
        considerNavigationBasedOnRecallQuotes();
    }

    @Override
    public void onPilotWeatherAlertsUserQueryFailure(@NonNull MitResponse response) {
        //Do nothing.
    }

    @Override
    public void onPilotWeatherAlertsUserQuerySuccess(@NonNull MitPrepareForWeatherAlertsEnrollmentResponse response) {
        getPolicySession().getWeatherFlow().setWeatherEnrollmentAllowedRegarding25kUseLimit(response.isEnrollmentAllowed());
        updateWeatherPickedForYouCardVisibility();
    }

    @Override
    public void onPrepareToUpdateTextAlertsFailure(@NonNull MitPrepareToUpdateTextAlertsResponse response) {
        getModel().setEligibleForTextAlertsEnrollment(false);
    }

    @Override
    public void onPrepareToUpdateTextAlertsSuccess(@NonNull MitPrepareToUpdateTextAlertsResponse response) {
        List<MitTextAlertDeviceSubscription> subscriptions = response.getSubscriptions();
        getPolicySession().setTextPreferences(new AceTextAlertPreferencesFromMit().transformAll(subscriptions));
        considerSettingTextAlertsEligibility(subscriptions.isEmpty());
        updateDashboardCards();
    }

    @Override
    public void onRetrieveDriverDataFailure(@NonNull MitResponse response) {
        onTelematicsDataUnavailable();
    }

    @Override
    public void onRetrieveDriverDataSuccess(@NonNull AceTelematicsDriver driver) {
        getTelematicsFlow().setDriverInformationState(AceInformationState.CURRENT);
        getTelematicsFlow().setDriver(driver);
        onRetrieveDriverDataSuccessWith(driver);
    }

    private void onRetrieveDriverDataSuccessWith(AceTelematicsDriver driver) {
        getCurrentDriverModel().setScoreDetails(driver.getScoreDetails());
        getCurrentDriverModel().setIsDriverCreatedInPastFiveDays(isDriverCreatedInPastFiveDays(driver.getDriverDetails()));
        getCurrentDriverModel().setInformationState(AceInformationState.CURRENT);
        getCurrentDriverModel().setIsRegistered(driver.getDriverDetails().isRegistered());
        getTelematicsPreferences().setDriverType(driver.getDriverDetails().isPrimaryDesignation() ? TelematicsDriverType.PRIMARY : TelematicsDriverType.SECONDARY);
        considerRetrievingTelematicsRelatedDrivers();
        updateDashboardCards();
        logDriveEasyQuickActionDisplayed();
    }

    @Override
    public void onTelematicsCredentialExpired(@NonNull AceResponse<? extends MitResponse> response) {
        getTelematicsRefreshHandler().onTelematicsCredentialExpired(response);
    }

    private void onTelematicsDataUnavailable() {
        setTelematicsFlowDriverStateToUnavailable();
        getCurrentDriverModel().setInformationState(AceInformationState.UNAVAILABLE);
        logDriveEasyQuickActionDisplayed();
    }

    @Override
    public void onTelematicsRefreshFailure(@NonNull AceResponse<? extends MitResponse> response) {
        if (!(response.memento instanceof AceRequest)) {
            markAsServiceError();
            return;
        }
        String requestType = ((AceRequest<?, ?>) response.memento).requestType;
        switch (requestType) {
            case TELEMATICS_GET_RELATED_DRIVERS:
                onGetRelatedDriversFailure(response.response);
                break;
            case TELEMATICS_RETRIEVE_DRIVER_DATA:
                onRetrieveDriverDataFailure(response.response);
                break;
            default:
                break;
        }
    }

    @Override
    public void onWeatherAlertsRetrievalFailure(@NonNull MitResponse response) {
        // Do nothing
    }

    @Override
    public void onWeatherAlertsRetrievalSuccess(@NonNull MitRetrieveWeatherAlertsResponse response) {
        new AceUpdateWeatherFlowFromRetrieveWeatherAlertsResponse().populate(response, getPolicySession().getWeatherFlow());
        if (isDeeplinkingToWeatherAlertsListPage()) {
            getSessionController().getDeepLink().setDeepLinkAction("");
            getApplicationSession().getGeniusLinkFlow().setGeniusLinkDestinationOverride("");
            startActivity(getApplication(), AceWeatherActivity.class);
        }
    }

    public void openCatastropheAlert() {
        trackAction(ANALYTICS_M5_DASHBOARD_CATASTROPHE_ALERT_SELECTED, M5_DASHBOARD_CATASTROPHE_ALERT_SELECTED);
        openFullSite(CATASTROPHE_ALERT);
    }

    @Override
    public void openDialog(@NonNull @DialogTag String dialogTag) {
        switch (dialogTag) {
            case DialogTag.ENROLL_WITH_DEVICE_UNLOCK:
                getStateEmitter().emitEnrollWithDeviceUnlockDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE);
                break;
            case DialogTag.KEEP_ME_LOGGED_IN:
                getStateEmitter().emitKeepMeLoggedInDialogVisibility(Visibility.VISIBLE);
                break;
            case DialogTag.MAKE_PAYMENT:
                trackAction(ANALYTICS_M5_DASHBOARD_PAYMENT_POP_UP_DISPLAYED, M5_DASHBOARD_PAYMENT_POP_UP_DISPLAYED);
                getStateEmitter().emitMakePaymentDialogVisibility(Visibility.VISIBLE);
                break;
            default:
                break;
        }
    }

    @Override
    public void postTelematicsRequest(@NonNull MitBaseTelematicsRequest request, @NonNull Class<? extends MitResponse> responseType, @AceTaskRequestType @NonNull String requestType) {
        getTelematicsRefreshHandler().postTelematicsRequest(request, responseType, requestType);
    }

    private void prepareToUpdateTextAlerts() {
        markAsWaiting();
        postRequest(createPrepareToUpdateTextAlertNotificationRequest(),
                MitPrepareToUpdateTextAlertsResponse.class,
                AceTaskRequestType.PREPARE_TO_UPDATE_TEXT_ALERTS);
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("SwitchIntDef")
    @Override
    public void reactToTaskResponse(@NonNull AceResponse<?> response) {
        switch (response.requestType) {
            case AceTaskRequestType.ENROLL_IN_PUSH:
                new AcePushEnrollmentReaction(this)
                        .reactTo((AceResponse<MitPushNotificationsEnrollmentResponse>) response);
                break;
            case AceTaskRequestType.PREPARE_TO_UPDATE_TEXT_ALERTS:
                new AcePrepareToUpdateTextAlertsServiceReaction(this)
                        .reactTo((AceResponse<MitPrepareToUpdateTextAlertsResponse>) response);
                break;
            case REGISTER_CLIENT:
                new AceClientRegistrationReaction(this)
                        .reactTo((AceResponse<MitClientRegistrationResponse>) response);
                break;
            case AceTaskRequestType.TELEMATICS_GET_RELATED_DRIVERS:
                new AceTelematicsGetRelatedDriversServiceReaction(this)
                        .reactTo((AceResponse<MitTelematicsGetRelatedDriversResponse>) response);
                break;
            case AceTaskRequestType.TELEMATICS_REFRESH:
                new AceTelematicsRefreshServiceReaction(getTelematicsRefreshHandler())
                        .reactTo((AceResponse<MitTelematicsRefreshResponse>) response);
                break;
            case AceTaskRequestType.TELEMATICS_RETRIEVE_DRIVER_DATA:
                new AceTelematicsRetrieveDriverDataServiceReaction(this)
                        .reactTo((AceResponse<AceTelematicsDriver>) response);
                break;
            default:
                break;
        }
    }

    public void registerListeners() {
        registerListener(createCrossSellProbabilityServiceStatusListener(CROSS_SELL_PROBABILITY_SERVICE_COMPLETED));
        registerListener(createCrossSellProbabilityServiceStatusListener(CROSS_SELL_PROBABILITY_SERVICE_FAILURE));
        registerListener(createVehicleCareRecallAlertServiceListener());
    }

    public void resetGreeting() {
        getModel().setGreetingSubject(new AceDashfolioGreetingNameFromSession().transform(getSessionController()));
        getModel().setIsUsersBirthday(getApplicationSession().getUserFlow().getPerson().getDriver().isBirthdayWeek());
        if (getSessionController().isInPolicySession()) {
            getModel().setPolicyEligibleForLoyaltyGreeting(isPolicyEligibleForLoyaltyGreeting());
            getModel().setPolicyYearsInForce(getPolicy().getTenureInYears());
        }
    }

    @SuppressWarnings("WeakerAccess")
        // package-private scope to avoid synthetic accessor
    void retrieveDashboardHeaderContent() {
        acceptVisitor(new AcePickySessionStateVisitor<Void>() {
            @Override
            public Void visitInPolicySession(Void input) {
                new AceDashboardHeaderContentService(AceDashboardRepository.this).retrieveDashboardHeaderContent();
                return NOTHING;
            }
        });
    }

    @Override
    public void retrieveMoatSalesQuotes() {
        markAsWaiting();
        callService(createMoatSalesQuotesRequest(), new AceMoatSalesQuotesRecallServiceReaction(this));
    }

    private void retrieveTelematicsDriverData() {
        getTelematicsFlow().acceptDriverInformationStateVisitor(new AceBaseInformationStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                getModel().getTelematicsCurrentDriverModel().setIsRegistered(false);
                getTelematicsFlow().setDriverInformationState(AceInformationState.REQUESTED);
                postTelematicsRequest(createRetrieveDriverDataRequest(),
                        AceTelematicsDriver.class,
                        AceTaskRequestType.TELEMATICS_RETRIEVE_DRIVER_DATA);
                return NOTHING;
            }

            @Override
            public Void visitCurrent(Void input) {
                onRetrieveDriverDataSuccessWith(getTelematicsFlow().getDriver());
                return NOTHING;
            }
        });
    }

    private void retrieveTelematicsRelatedDrivers() {
        markAsWaiting();
        postTelematicsRequest(createGetRelatedDriversRequest(),
                MitTelematicsGetRelatedDriversResponse.class,
                AceTaskRequestType.TELEMATICS_GET_RELATED_DRIVERS);
        getTelematicsFlow().setRelatedDriversInformationState(AceInformationState.REQUESTED);
    }

    @Override
    public void sendPushEnrollmentRequest(@NonNull AcePushMessagingEnrollmentType type) {
        postRequest(new AceMessagingEnrollmentRequestFactory(getSessionController()).create(type),
                MitPushNotificationsEnrollmentResponse.class,
                getPolicySession().getUserId(),
                AceTaskRequestType.ENROLL_IN_PUSH,
                AceTaskType.TIER_TASK);
    }

    private void setEsignatureCardAlreadyShownInCurrentSession() {
        new AceEsignatureStatusHelper().considerUpdatingEsignatureStatusWith(getSessionController());
    }

    private void setTelematicsFlowDriverStateToUnavailable() {
        getTelematicsFlow().setDriverInformationState(AceInformationState.UNAVAILABLE);
        getTelematicsFlow().setDriver(new AceTelematicsDriver());
    }

    private boolean shouldRetrieveTelematicsRelatedDrivers() {
        AceTelematicsDriverDetails driverDetails = getTelematicsFlow().getDriver().getDriverDetails();
        return driverDetails.isRegistered() && driverDetails.isPrimaryDesignation();
    }

    public void trackVehicleRecallAlertExitUrl() {
        new AceVehicleRecallAlertActionTrackingHelper(getAnalyticsFacade(), getTrackable()).trackVehicleRecallAlertExitUrl(getVehicleCareRecallUrl());
    }

    public void trackWhatIsNextCardAction(@DashboardCardType String cardType) {
        new AceWhatIsNextCardAnalyticsHelper().trackWhatIsNextCardAction(cardType, this);
    }

    @Override
    public void updateCardsInDashboard() {
        updateDashboardCards();
    }

    public void updateCrossSellCardToFlow(@NonNull AceDashboardCard card) {
        new AceUpdateSelectedCrossSellCardHelper().updateCrossSellCardToFlow(getRecommendationsFacade(), card);
    }

    @SuppressWarnings("WeakerAccess")
        // package-private scope to avoid synthetic accessor
    void updateDashboardCards() {
        List<AceDashboardCard> dashboardCards = new AceDashboardCardsFromRepository(getApplication()).transform(this);
        considerTrimmingCardList(dashboardCards);
        addClaimAlertCards(dashboardCards);
        addCrossSellCards(dashboardCards);
        Collections.sort(dashboardCards);
        getModel().setDashboardCards(dashboardCards);
        considerTrackingNextStepsDisplayed();
    }

    @SuppressLint("SwitchIntDef")
    public void updateSelectedWhatIsNextCardDetails(@NonNull AceDashboardCard card) {
        switch (card.getCardType()) {
            case DashboardCardType.ESIGNATURE:
                setEsignatureCardAlreadyShownInCurrentSession();
                break;
            case DashboardCardType.CHANGE_OF_ADDRESS:
                getDigitalHugSharedPreferencesDao().considerRememberingDigitalHugNotice(getChangeOfAddressNotice());
                break;
            default:
                break;
        }
    }

    public void updateWeatherFlowForWeatherCardSelected(boolean value) {
        getPolicySession().getWeatherFlow().storeWeatherCardIsSelected(getPolicySession().getPolicyNumber(), value, getApplication());
    }

    private void updateWeatherPickedForYouCardVisibility() {
        new AceWeatherEnrollmentRuleApplicator(this).executeWith(getRegistry());
        considerTrackingWeatherCard();
    }
}