package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.util.Pair;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.connectivity.AceBaseConnectionStateVisitor;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enumerating.AceMatcher;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceBaseInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceEvent;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceListener;
import com.geico.mobile.android.ace.coreframework.eventhandling.monitoring.AceBasicEventMonitor;
import com.geico.mobile.android.ace.coreframework.eventhandling.monitoring.AceEventMonitor;
import com.geico.mobile.android.ace.coreframework.string.AceDelimitedStringBuilder;
import com.geico.mobile.android.ace.coreframework.string.AceStringBuilder;
import com.geico.mobile.android.ace.coreframework.webservices.contexts.AceBasicHttpServiceContext;
import com.geico.mobile.android.ace.coreframework.webservices.contexts.AceServiceContext;
import com.geico.mobile.android.ace.coreframework.webservices.task.AceTask;
import com.geico.mobile.android.ace.coreframework.webservices.task.AceTaskCallback;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.AceLiveDataTask;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceRequest;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskType;
import com.geico.mobile.android.ace.geicoappbusiness.logging.AceSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceActivateDeviceUnlock;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.matchers.AceIdMatcher;
import com.geico.mobile.android.ace.geicoappbusiness.metrics.AceMetrics;
import com.geico.mobile.android.ace.geicoappbusiness.opinionlab.AceOpinionLabSourceIndicator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceBaseRepository;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceCallService;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AcePostRequest;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierLiveDataTask;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierServiceContextFactory;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierTask;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceClaimFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceDashfolioFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.start.AceSessionExpiredStartStrategy;
import com.geico.mobile.android.ace.geicoappbusiness.telephony.AceDialerIntentLauncher;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.AceAlertFromMit;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.AcePolicyEligibleForVehicleCareDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.AceVehicleCareModeDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceClaimAlertSelectedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceContactUsPhoneEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceEcamsScreenUnlockEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceEcamsScreenUnlockOptionSelectEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceMenuActionEvent;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceClientRegistrationUseCase;
import com.geico.mobile.android.ace.geicoappmodel.AceClaim;
import com.geico.mobile.android.ace.geicoappmodel.AceClaimAlertNotification;
import com.geico.mobile.android.ace.geicoappmodel.AceDashboardClaimOrigin;
import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.enums.userconnectiontechnique.AceUserConnectionTechnique;
import com.geico.mobile.android.ace.geicoappmodel.response.AceAlert;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.AceContactUsEventLogger;
import com.geico.mobile.android.ace.mitsupport.contexts.AceBasicMitHttpServiceContext;
import com.geico.mobile.android.ace.mitsupport.eventhandling.AceEventFromMitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitAuthenticatedRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitClientRegistrationRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitClientRegistrationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitEventLogRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.REGISTER_CLIENT;
import static com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants.REFRESH_TOKEN_KEY;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_ALERT;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_CHANGE_USER_NO;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_CHANGE_USER_YES;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_NOT_NOW;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_YES;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_ENROLLMENT_CHANGE_USER_NO;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_ENROLLMENT_CHANGE_YES;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_ENROLLMENT_DIALOG;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_ENROLLMENT_NOT_NOW;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_ENROLLMENT_YES;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_UPDATE_DIALOG;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.SCREEN_UNLOCK_SIGNUP_MESSAGE_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.SCREEN_UNLOCK_SIGNUP_MESSAGE_DISPLAYED_EVENT_ID;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.SCREEN_UNLOCK_SIGNUP_MESSAGE_OPTION_SELECTED_EVENT_ID;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.SCREEN_UNLOCK_SWITCH_ACCOUNT_MESSAGE_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.SCREEN_UNLOCK_SWITCH_ACCOUNT_MESSAGE_DISPLAYED_EVENT_ID;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.SCREEN_UNLOCK_SWITCH_ACCOUNT_MESSAGE_OPTION_SELECTED_EVENT_ID;

/**
 * Base class holding common behavior for all repository objects that invoke TIER service calls.
 *
 * @author Richard Peirson, GEICO
 */
public abstract class AceTierRepository<M extends AceViewBackingModel> extends AceBaseRepository<M> implements
        AceCallService<MitRequest>,
        AceClientRegistrationUseCase,
        AceMetrics,
        AcePostRequest,
        AceTaskCallback<MitRequest, MitResponse, AceBasicMitHttpServiceContext<MitRequest, MitResponse>> {

    private final AceEventMonitor eventMonitor;
    private final AceTierTask task = new AceTierTask();
    private final AceLiveDataTask tierLiveDataTask = createTierLiveDataTask();

    public AceTierRepository(@NonNull Application application) {
        super(application);
        eventMonitor = createEventMonitor();
    }

    /**
     * Alerts are emitted as an {@link com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceErrorState}, however,
     * we are not always intending to emit what we get back from a tier service call to display to the user. For example, during the
     * account creation process we can get {@link com.geico.mobile.android.ace.mitsupport.webservices.AceMitServiceConstants#MIT_SSN_REQUIRED_ALERT}
     * but we want to suppress that while the user is entering the social security number. Hence, we will have to use {@link AceBasicEnumerator#reject(Collection, AceMatcher)}
     * in order to determine if we are able to display the alerts to the user.
     * <p>
     * The rejected {@link List} represents the emittable alerts.
     */
    private void appendErrorText(List<AceAlert> alerts, AceStringBuilder builder) {
        buildErrorText(alerts, builder);
        getStateEmitter().emitError(builder.toString());
    }

    private void buildErrorText(List<AceAlert> alerts, AceStringBuilder builder) {
        for (AceAlert alert : AceBasicEnumerator.DEFAULT.reject(alerts, new AceIdMatcher<>(getAlertIdsNotToBeEmitted()))) {
            builder.append(alert.getMessage());
            trackError(alert.getId());
        }
    }

    public <R extends AceServiceReaction, X> void callService(@NonNull MitRequest request, @NonNull R serviceReaction,
                                                              @NonNull final X memento) {
        addServiceReaction(serviceReaction);
        acceptVisitor(new AceBaseConnectionStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                markAsNotConnected();
                markAsNotWaiting();
                return NOTHING;
            }

            @Override
            public Void visitConnected(Void input) {
                markAsConnected();
                runTaskWith(request, memento);
                return NOTHING;
            }
        });
    }

    protected AceLiveDataTask considerDecoratingTaskWithHttpPayloadLogging(AceLiveDataTask task) {
        return getBuildEnvironment().acceptVisitor(new AceHttpPayloadLoggingDetermination(
                getRegistry().getPrettyDtoEncoder(), getRegistry().getLogger()), task);
    }

    protected MitClientRegistrationRequest createClientRegistrationRequest() {
        return new AceClientRegistrationRequestFactory().create();
    }

    MitAuthenticatedRequest createEcamsEventLogRequest(AceEcamsEventLog eventLog) {
        return eventLog.create(getRegistry().getEcamsEventLogModel());
    }

    MitEventLogRequest createEventLogRequest(AceEventLog eventLog) {
        return eventLog.create(getRegistry().getEventLogModel());
    }

    private AceEventMonitor createEventMonitor() {
        return new AceBasicEventMonitor(getRegistry());
    }

    private AceBasicMitHttpServiceContext<MitRequest, MitResponse> createServiceContext(MitRequest request, Object memento) {
        return new AceTierServiceContextFactory().create(new Pair<>(request, memento));
    }

    private AceLiveDataTask createTierLiveDataTask() {
        return considerDecoratingTaskWithHttpPayloadLogging(new AceTierLiveDataTask<>());
    }

    /**
     * This method is overridden by respective repositories in order to have alert ids that represent
     * alerts not to be emitted as an {@link com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceErrorState}
     * By default it returns an empty {@link List} - which stands for all alert that we get back from the
     * service calls are displayable to the user.
     * <p>
     * see {@link this#appendErrorText(List, AceStringBuilder)} for implementation details.
     *
     * @return {@link List}
     */
    protected List<String> getAlertIdsNotToBeEmitted() {
        return new ArrayList<>();
    }

    protected AceDashfolioFlow getDashfolioFlow() {
        return getSessionController().getUserSession().getDashfolioFlow();
    }

    private int getDeviceUnlockSelectedEventId() {
        return userWasEnrolledForDeviceUnlock() ? SCREEN_UNLOCK_SWITCH_ACCOUNT_MESSAGE_OPTION_SELECTED_EVENT_ID : SCREEN_UNLOCK_SIGNUP_MESSAGE_OPTION_SELECTED_EVENT_ID;
    }

    //Default task to all repositories.
    @Override
    @SuppressWarnings("unused")
    protected AceLiveDataTask getLiveDataTask(@AceTaskType String taskType) {
        return tierLiveDataTask;
    }

    @NonNull
    @Override
    public String getMessageForDeviceUnlockDialog() {
        if (userWasEnrolledForDeviceUnlock()) {
            return getApplication().getString(R.string.enrollForScreenUnlockDescriptionChangeUser, getLoginSettingsDao().getLastFingerprintUserEnteredId(),
                    getLoginSettingsDao().getLastUserEnteredId());
        }
        return getString(R.string.enrollForScreenUnlockDescription);
    }

    @NonNull
    @Override
    public String getString(@StringRes int stringResource) {
        return getApplication().getResources().getString(stringResource);
    }

    @NonNull
    @Override
    public final AceTask<MitRequest, MitResponse, AceBasicMitHttpServiceContext<MitRequest, MitResponse>> getTask() {
        return this.task;
    }

    protected String getVehicleCareRecallUrl() {
        String webLinkName = getFullSiteOpener().generateWebLinkMap().containsKey(MitWebLinkNames.VEHICLE_CARE_V2) ? MitWebLinkNames.VEHICLE_CARE_V2 : MitWebLinkNames.VEHICLE_CARE;
        return getFullSiteOpener().lookUpLink(webLinkName).getUrl();
    }

    protected <O extends MitResponse, I extends MitRequest> void handleServiceFailure(AceServiceContext<I, O> serviceContext) {
        showServiceErrorDialog();
        if (serviceContext.getResponse().getAlerts().isEmpty()) return;
        trackError(serviceContext.getResponse().getAlerts().get(0).getId());
    }

    public <O extends MitResponse, I extends MitRequest> void handleSessionExpired(O response) {
        getSessionController().setSessionStartStrategy(new AceSessionExpiredStartStrategy());
        getSessionController().beLoggedOut();
        showSessionExpiredDialog(response);
    }

    protected <O extends MitResponse, I extends MitRequest> void handleSessionExpired(AceServiceContext<I, O> serviceContext) {
        handleSessionExpired(serviceContext.getResponse());
    }

    // protected scope to allow subclass access
    @SuppressWarnings("WeakerAccess")
    protected boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    protected boolean isPolicyEligibleForVehicleCare() {
        return new AceVehicleCareModeDerivation().deriveValueFrom(getRegistry())
                && new AcePolicyEligibleForVehicleCareDerivation().deriveValueFrom(getRegistry().getSessionController().getPolicySession().getPolicy());
    }

    @Override
    public void launchDialerWith(@StringRes int phoneNumberResourceId) {
        new AceContactUsEventLogger(new AceContactUsPhoneEvent(), getPolicySession().getContactUsFlow(), "tel:" + getString(phoneNumberResourceId), "", getString(com.geico.mobile.R.string.callToMakeChanges)).executeWith(this);
        new AceDialerIntentLauncher(getApplication()).executeWith(phoneNumberResourceId);
    }

    @Override
    public void logEvent(@NonNull String eventName) {
        new AceEventLogger().logEvent(eventName, this);
    }

    @Override
    public void logEvent(@NonNull AceEventLog eventLog) {
        new AceEventLogger().logEvent(eventLog, this);
    }

    @Override
    public void logEvent(@NonNull AceSplunkLoggingContext loggingContext) {
        new AceSplunkLogger(loggingContext, getRegistry()).executeWith(this);
    }

    @Override
    public void logEvent(@NonNull AceEcamsEventLog eventLog) {
        new AceEcamsEventLogger().logEvent(eventLog, this);
    }

    @Override
    public void logEvent(int ecamsEventId) {
        new AceEcamsEventLogger().logEvent(ecamsEventId, this);
    }

    @Override
    public void logEvent(int ecamsEventId, @NonNull String detailKey, @NonNull String detailValue) {
        new AceEcamsEventLogger().logEvent(ecamsEventId, detailKey, detailValue, this);
    }

    @Override
    public void markAsServiceError() {
        markAsServiceError(getStateEmitter());
    }

    protected void navigateToErsRequestConfirmation(Context context, AceClaimAlertNotification alertNotification) {
        setClaimsAlertNeedRefreshing();
        prepareClaimFlowBeforeNavigation(alertNotification);
        getRegistry().getRoadsideAssistanceFacade().considerRevisitingConfirmation(context, alertNotification);
        logEvent(new AceClaimAlertSelectedEvent());
    }

    @Override
    public void onActivateDeviceUnlockDiscarded() {
        AceLoginSettingsDao loginSettingsDao = getLoginSettingsDao();
        loginSettingsDao.setOfferSeenForEnrollWithDeviceUnlock();
        if (loginSettingsDao.retrieveUserId().equalsIgnoreCase(loginSettingsDao.retrieveFingerprintUserId())) {
            loginSettingsDao.storeDiscardRefreshTokenBeforeNextLogin(true);
        }
        recordMetricsOnDeviceUnlockDiscarded();
    }

    @Override
    public void onActivateDeviceUnlockSelected() {
        new AceActivateDeviceUnlock(getApplicationSession().getLoginFlow()).executeWith(getLoginSettingsDao());
        recordMetricsOnDeviceUnlockSelected();
    }

    @Override
    public void onCleared() {
        getTaskHandler().cancel(getTask());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCompleted(@NonNull AceBasicMitHttpServiceContext<MitRequest, MitResponse> serviceContext) {
        getServiceReactionFrom(serviceContext).reactTo(serviceContext);
    }

    @Override
    public void openFullSite(@NonNull Activity activity, @NonNull AceClaimAlertNotification alertNotification, @NonNull String webLinkName) {
        setClaimsAlertNeedRefreshing();
        AceClaim selectedClaim = new AceClaim();
        selectedClaim.setClaimNumber(alertNotification.getClaimNumber());
        selectedClaim.setLossDate(alertNotification.getLossDate());
        getPolicySession().getClaimFlow().setClaim(selectedClaim);
        getPolicy().setClaimsState(AceInformationState.OUTDATED);
        openFullSite(activity, webLinkName);
    }

    @Override
    public void postRequest(@NonNull AceRequest request) {
        acceptVisitor(new AceBaseConnectionStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                markAsNotConnected();
                markAsNotWaiting();
                return NOTHING;
            }

            @Override
            public Void visitConnected(Void input) {
                markAsConnected();
                getRequestMutableLiveData().postValue(request);
                return NOTHING;
            }
        });
    }

    private void prepareClaimFlowBeforeNavigation(AceClaimAlertNotification item) {
        AceClaimFlow flow = getPolicySession().getClaimFlow();
        flow.getClaim().setClaimNumber(item.getClaimNumber());
        flow.setCallToActions(item.getCallToActions());
        flow.setType(item.getAlertType());
        flow.setClaimOrigin(AceDashboardClaimOrigin.NOTIFICATION_CARD);
    }

    private void recordMetricsOnDeviceUnlockDiscarded() {
        boolean userWasEnrolledForScreenUnlock = userWasEnrolledForDeviceUnlock();
        logEvent(new AceEcamsScreenUnlockOptionSelectEvent(userWasEnrolledForScreenUnlock ? getString(R.string.no) : getString(R.string.notNow), getDeviceUnlockSelectedEventId()));
        String contextVariable = userWasEnrolledForScreenUnlock ? SCREEN_UNLOCK_ENROLLMENT_CHANGE_USER_NO : SCREEN_UNLOCK_ENROLLMENT_NOT_NOW;
        String actionValue = userWasEnrolledForScreenUnlock ? ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_CHANGE_USER_NO : ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_NOT_NOW;
        trackAction(actionValue, contextVariable);
    }

    private void recordMetricsOnDeviceUnlockSelected() {
        boolean userWasEnrolledForScreenUnlock = userWasEnrolledForDeviceUnlock();
        logEvent(new AceEcamsScreenUnlockOptionSelectEvent(getString(R.string.yes), getDeviceUnlockSelectedEventId()));
        String contextVariable = userWasEnrolledForScreenUnlock ? SCREEN_UNLOCK_ENROLLMENT_CHANGE_YES : SCREEN_UNLOCK_ENROLLMENT_YES;
        String actionValue = userWasEnrolledForScreenUnlock ? ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_CHANGE_USER_YES : ANALYTICS_SCREEN_UNLOCK_ENROLLMENT_YES;
        trackAction(actionValue, contextVariable);
    }

    public void recordScreenUnlockMetrics() {
        trackAction(ANALYTICS_ALERT, userWasEnrolledForDeviceUnlock() ? SCREEN_UNLOCK_UPDATE_DIALOG : SCREEN_UNLOCK_ENROLLMENT_DIALOG);
        logEvent(userWasEnrolledForDeviceUnlock() ? new AceEcamsScreenUnlockEvent(SCREEN_UNLOCK_SWITCH_ACCOUNT_MESSAGE_DISPLAYED, SCREEN_UNLOCK_SWITCH_ACCOUNT_MESSAGE_DISPLAYED_EVENT_ID) :
                new AceEcamsScreenUnlockEvent(SCREEN_UNLOCK_SIGNUP_MESSAGE_DISPLAYED, SCREEN_UNLOCK_SIGNUP_MESSAGE_DISPLAYED_EVENT_ID));
    }

    @Override
    public void registerClient() {
        getApplicationSession().getClientRegistrationRequestState().acceptVisitor(new AceBaseInformationStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                postRequest(createClientRegistrationRequest(), MitClientRegistrationResponse.class, REGISTER_CLIENT);
                return NOTHING;
            }

            @Override
            public Void visitCurrent(Void input) {
                onClientRegistrationSuccess();
                return NOTHING;
            }
        });
    }

    /**
     * @deprecated because the event framework should not be used for MVVM components unless absolutely needed to interact with legacy code
     */
    @Deprecated
    protected void registerListener(AceListener<?> listener) {
        eventMonitor.registerListener(listener);
    }

    protected <O extends MitResponse, I extends MitRequest> boolean requestCredentialsMatchCurrentPolicySession(AceServiceContext<I, O> serviceContext) {
        I request = serviceContext.getRequest();
        return request instanceof MitAuthenticatedRequest && getSessionController().isSameSession(((MitAuthenticatedRequest) request).getCredentials());
    }

    // default scope to avoid synthetic accessor generation
    @SuppressWarnings("WeakerAccess")
    <I extends MitRequest, X> void runTaskWith(I request, X memento) {
        getTaskHandler().run(getTask(), createServiceContext(request, memento), this);
    }

    protected void setClaimsAlertNeedRefreshing() {
        getDashfolioFlow().setAlertsInformationState(AceInformationState.OUTDATED);
    }

    protected void showErrorFrom(MitResponse response) {
        List<AceAlert> alerts = new AceAlertFromMit().transformAll(response.getAlerts());
        appendErrorText(alerts, new AceDelimitedStringBuilder("\n\n"));
    }

    private void showServiceErrorDialog() {
        getStateEmitter().emitServiceErrorDialogVisibility(Visibility.VISIBLE);
    }

    protected void showServiceErrorWithAlertsDialog(AceBasicHttpServiceContext<MitRequest, MitResponse> serviceContext) {
        showServiceErrorWithAlertsDialog(serviceContext.getResponse());
    }

    protected void showServiceErrorWithAlertsDialog(String message) {
        getStateEmitter().emitServiceErrorWithAlertsDialogVisibility(Visibility.VISIBLE, message);
    }

    protected void showServiceErrorWithAlertsDialog(MitResponse response) {
        List<AceAlert> alerts = new AceAlertFromMit().transformAll(response.getAlerts());
        AceDelimitedStringBuilder builder = new AceDelimitedStringBuilder("\n\n");
        buildErrorText(alerts, builder);
        showServiceErrorWithAlertsDialog(builder.toString());
    }

    private void showSessionExpiredDialog(MitResponse response) {
        AceEvent<String, String> event = AceEventFromMitResponse.DEFAULT.transform(response);
        trackError(event.getId());
        getStateEmitter().emitSessionExpiredDialogVisibility(Visibility.VISIBLE, event.getSubject());
    }

    @Override
    public void startActivity(@NonNull Class<? extends Activity> destinationActivity, @NonNull AceOpinionLabSourceIndicator labSourceIndicator, @NonNull String eventName) {
        logEvent(new AceMenuActionEvent(eventName));
        super.startActivity(destinationActivity, labSourceIndicator, eventName);
    }

    @Override
    public void trackError(@NonNull String error) {
        if (isEmpty(error)) return;
        getTrackable().trackError(error);
    }

    public void unregisterListeners() {
        eventMonitor.unregisterListeners();
    }

    @Override
    public boolean userWasEnrolledForDeviceUnlock() {
        String fingerprintUserId = getLoginSettingsDao().getLastFingerprintUserEnteredId();
        return !fingerprintUserId.isEmpty() && !getLoginSettingsDao().getLastUserEnteredId().equalsIgnoreCase(fingerprintUserId);
    }

    @Override
    public void onKeepMeLoggedInDiscarded() {
        getLoginSettingsDao().storeDiscardRefreshTokenBeforeNextLogin(true);
        getApplicationSession().getLoginFlow().setUserConnectionTechnique(AceUserConnectionTechnique.LOGIN_EACH_TIME);
    }

    @Override
    public void onKeepMeLoggedInSelected() {
        AceLoginSettingsDao loginSettingsDao = getLoginSettingsDao();
        loginSettingsDao.write(REFRESH_TOKEN_KEY, getApplicationSession().getLoginFlow().getMostRecentRefreshToken());
        loginSettingsDao.storeDiscardRefreshTokenBeforeNextLogin(false);
        loginSettingsDao.storeReasonForRetainingRefreshToken(AceLoginConstants.AUTOMATIC_LOGIN);
        getApplicationSession().getLoginFlow().setUserConnectionTechnique(AceUserConnectionTechnique.KEEP_ME_LOGGED_IN);
    }
}