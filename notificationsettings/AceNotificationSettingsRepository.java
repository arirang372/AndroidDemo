package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceResponse;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskType;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceStateEmitter;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushEnrollmentReaction;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentType;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingRegistrationRequestFactory;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushRegistrationPreferences;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AcePrepareToUpdateTextAlertsServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceUpdateAccountEmailServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceUpdateTextAlertsServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserInputValidityState;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.textAlerts.AceTextAlertPreferencesFromMit;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceNotificationSettingsUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AcePrepareToUpdateTextAlertsUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceUpdateAccountEmailUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceUpdateTextAlertUseCase;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.weather.AceWeatherAlertsEnrollmentHandler;
import com.geico.mobile.android.ace.geicoappmodel.AceEmailPreferences;
import com.geico.mobile.android.ace.geicoappmodel.AceTextAlertPreferences;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.enums.AceEnrollment;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AcePushEnrollmentHelper;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRequestPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.textalerts.AcePrepareToUpdateTextAlertsRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDisplayToast;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.emittedstate.AceBasicNotificationSettingsStateEmitter;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.emittedstate.AceNotificationSettingsStateEmitter;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AceInitializeNotificationSettingsModelFromTextPreferences;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AceNotificationSettingsLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AceNotificationSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AceNotificationSettingsModelFactory;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AcePushNotificationType;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository.servicecall.AceUpdateAccountEmailRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository.servicecall.AceUpdateTextAlertsRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository.validation.AceNotificationSettingsEmailValidationRulesFactory;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository.validation.AceNotificationSettingsPostValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository.validation.AceValidateNotificationSettingsPhoneInputs;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.repository.AcePrepareForWeatherAlertsRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.repository.AcePrepareForWeatherAlertsServiceReaction;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitMessagingEnrollmentRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareForWeatherAlertsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareForWeatherAlertsResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateTextAlertsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateTextAlertsResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPushNotificationsEnrollmentResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitTextAlertDeviceSubscription;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateAccountEmailRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateAccountEmailResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateTextAlertsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateTextAlertsResponse;

import java.util.List;

import static com.geico.mobile.android.ace.coreframework.patterns.AceVisitor.NOTHING;
import static com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceMessagingConstants.SERVICE_ENROLLMENT_NO;
import static com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceMessagingConstants.SERVICE_ENROLLMENT_YES;

/**
 * Provides data and handles business use cases for the Notification Settings feature.
 *
 * @author Mahmudul Hasan & Austin Morgan
 */
public class AceNotificationSettingsRepository extends AceTierRepository<AceNotificationSettingsModel>
        implements AceNotificationSettingsUseCase, AcePrepareToUpdateTextAlertsUseCase, AceDisplayToast, AceUpdateTextAlertUseCase,
        AceUpdateAccountEmailUseCase, AceWeatherAlertsEnrollmentHandler {

    private AcePushRegistrationPreferences pushRegistrationPreferences;

    public AceNotificationSettingsRepository(@NonNull Application application) {
        super(application);
        pushRegistrationPreferences = new AcePushRegistrationPreferences(getRegistry());
    }

    private boolean areDeviceNotificationsDisabled() {
        return !NotificationManagerCompat.from(getApplication()).areNotificationsEnabled();
    }

    @Override
    public void considerEnrollingInPush() {
        new AcePushEnrollmentHelper(getRegistry(), this, getStateEmitter())
                .considerEnrollingInPush(getModel().isEnrolledInPushNotifications.get());
    }

    public void considerEnrollingInWeatherAlertsNotification() {
        if (areDeviceNotificationsDisabled() && !isEnrolledInWeatherPushNotification()) {
            getModel().isEnrolledInWeatherAlertsNotification.set(false);
            getStateEmitter().emitChangeDeviceNotificationSettingsDialogVisibility(Visibility.VISIBLE);
            return;
        }
        considerEnrollingIntoWeatherAlerts();
        considerEnrollingIntoWeatherPush();
    }

    public void enrollDriveEasyPushNotification() {
        getModel().setPushNotificationType(AcePushNotificationType.DRIVEEASY_PUSH_NOTIFICATION);
        pushRegistrationPreferences.storeTelematicsPushPreferenceForPolicy(getPolicySession().getPolicyNumber(), getPushNotificationEnrollmentText(getModel().isEnrolledInDriveEasyPushNotification().get()));
        considerEnrollingInPush();
    }

    private void considerEnrollingIntoWeatherAlerts() {
        if (!isEnrolledToWeatherAlerts()) {
            callService(createPrepareForWeatherAlertsRequest(), new AcePrepareForWeatherAlertsServiceReaction(this));
        }
    }

    private void considerEnrollingIntoWeatherPush() {
        getModel().setPushNotificationType(AcePushNotificationType.WEATHER_PUSH_NOTIFICATION);
        pushRegistrationPreferences.storeWeatherPushPreferenceForPolicy(getPolicySession().getPolicyNumber(), getPushNotificationEnrollmentText(getModel().isEnrolledInWeatherAlertsNotification.get()));
        considerEnrollingInPush();
    }

    private List<AceRule<AceNotificationSettingsModel>> createEmailValidationRules() {
        return new AceNotificationSettingsEmailValidationRulesFactory(getPolicy().getContact().getEmailAddress())
                .create();
    }

    private MitMessagingEnrollmentRequest createMessagingEnrollmentRequest(@NonNull AcePushMessagingEnrollmentType type) {
        MitMessagingEnrollmentRequest request = new AcePushMessagingRegistrationRequestFactory(getRegistry()).create(type);
        new AceTierRequestPopulator<Void, MitMessagingEnrollmentRequest>().populate(NOTHING, request);
        return request;
    }

    public AceNotificationSettingsModel createModel() {
        return new AceNotificationSettingsModelFactory(getRegistry().getPushDao(),
                isEnrolledInWeatherPushNotification(), shouldShowWeatherEnrollmentCheckbox(), isEnrolledInDriveEasyPushNotification()).create(getSessionController());
    }

    @NonNull
    @Override
    protected AceNotificationSettingsLiveData createModelLiveData() {
        return new AceNotificationSettingsLiveData(createModel(), this);
    }

    private List<AceRule<AceNotificationSettingsModel>> createNotificationSettingsPostValidationRules() {
        return new AceNotificationSettingsPostValidationRuleFactory(getApplication().getResources(), this).create();
    }

    private MitPrepareForWeatherAlertsRequest createPrepareForWeatherAlertsRequest() {
        return new AcePrepareForWeatherAlertsRequestFactory(getSessionController()).create(getSessionController().getPolicySession().getPolicy());
    }

    private MitPrepareToUpdateTextAlertsRequest createPrepareToUpdateTextAlertNotificationRequest() {
        return new AcePrepareToUpdateTextAlertsRequestFactory(getSessionController()).create();
    }

    @Override
    protected AceStateEmitter createStateEmitter() {
        return new AceBasicNotificationSettingsStateEmitter();
    }

    private MitUpdateAccountEmailRequest createUpdateAccountEmailRequest() {
        return new AceUpdateAccountEmailRequestFactory(getSessionController()).create(getModel());
    }

    private MitUpdateTextAlertsRequest createUpdateTextAlertsRequest() {
        return new AceUpdateTextAlertsRequestFactory(getSessionController()).create(getModel());
    }

    @Override
    public void discardChanges() {
        getPolicy().getContact().setEmailPreferences(new AceEmailPreferences(getModel().getExistingEmailPreferences()));
        getModelLiveData().resetValue();
        resetInputValidity();
    }

    @Override
    public void displayToast(@NonNull String text) {
        Toast.makeText(getApplication(), text, Toast.LENGTH_SHORT).show();
    }

    private AceEmailPreferences getEmailPreferences() {
        return getPolicy().getContact().getEmailPreferences();
    }

    private AceEnrollment getEnrollmentStateFrom(boolean isEnrolled) {
        return isEnrolled ? AceEnrollment.ENROLLED : AceEnrollment.NOT_ENROLLED;
    }

    @StringRes
    private int getPushEnrollmentSuccessMessage(@AcePushNotificationType String pushNotificationType, boolean isEnrolledInPushNotification) {
        switch (pushNotificationType){
            case AcePushNotificationType.DRIVEEASY_PUSH_NOTIFICATION:
                return isEnrolledInPushNotification ?
                        R.string.driveEasyPushNotificationRegisteredMessage :
                        R.string.driveEasyPushNotificationNotNowMessage;
            case AcePushNotificationType.NONE:
            default:
                return isEnrolledInPushNotification ?
                        R.string.pushNotificationRegisteredMessage :
                        R.string.pushNotificationNotNowMessage;
        }
    }

    @NonNull
    @Override
    public AceNotificationSettingsModel getResetModel() {
        return createModel();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public AceNotificationSettingsStateEmitter getStateEmitter() {
        return super.getStateEmitter();
    }

    private String getPushNotificationEnrollmentText(boolean pushNotificationEnrolled) {
        return pushNotificationEnrolled ? SERVICE_ENROLLMENT_YES : SERVICE_ENROLLMENT_NO;
    }

    private boolean isEnrolledInWeatherPushNotification() {
        return getPolicySession().getWeatherFlow().isEnrolledInWeatherPushNotificationForPolicy(getPolicySession().getPolicyNumber(), getApplication());
    }

    private boolean isEnrolledInDriveEasyPushNotification() {
        return new AcePushRegistrationPreferences(getRegistry()).isEnrolledDriveEasyNotification(getPolicySession().getPolicyNumber());
    }

    private boolean isEnrolledToWeatherAlerts() {
        return getPolicySession().getWeatherFlow().retrieveWeatherAlertEnrollmentState(getPolicySession().getPolicyNumber(), getApplication()).isEnrolledToWeatherAlerts();
    }

    @Override
    public <O extends MitResponse> void onNotAuthorized(@NonNull O response) {
        handleSessionExpired(response);
    }

    @Override
    public void onPrepareToUpdateTextAlertsFailure(@NonNull MitPrepareToUpdateTextAlertsResponse response) {
        getModel().errorText.set(response.getAlerts().get(0).getMessage());
    }

    @Override
    public void onPrepareToUpdateTextAlertsSuccess(@NonNull MitPrepareToUpdateTextAlertsResponse response) {
        updateTextPreferencesFromResponse(response.getSubscriptions());
    }

    @Override
    public void onPushEnrollmentFailure(@NonNull MitResponse response) {
        showResultDialog(false, R.string.urbanAirshipRegistrationIssue);
    }

    @Override
    public void onPushEnrollmentSuccess(@NonNull AceResponse<MitPushNotificationsEnrollmentResponse> response) {
        switch (getModel().getPushNotificationType()) {
            case AcePushNotificationType.DRIVEEASY_PUSH_NOTIFICATION:
                pushRegistrationPreferences.storeIsEnrolledDriveEasyNotification(getPolicySession().getPolicyNumber(), getModel().isEnrolledInDriveEasyPushNotification().get());
                showResultDialog(true, getPushEnrollmentSuccessMessage(getModel().getPushNotificationType(), getModel().isEnrolledInDriveEasyPushNotification().get()));
                return;
            case AcePushNotificationType.WEATHER_PUSH_NOTIFICATION:
                getPolicySession().getWeatherFlow().storeWeatherPushNotificationEnrollment(getPolicySession().getPolicyNumber(), getModel().isEnrolledInWeatherAlertsNotification.get(), getApplication());
                return;
            default:
                break;
        }
        showResultDialog(true, getPushEnrollmentSuccessMessage(getModel().getPushNotificationType(), getModel().isEnrolledInPushNotifications.get()));
    }

    @Override
    public void onUpdateAccountEmailFailure(@NonNull MitUpdateAccountEmailResponse response) {
        resetInputValidity();
        showErrorFrom(response);
        getPolicySession().getAccountInfoFlow().setUpdateDetailsServiceState(AceInformationState.UNAVAILABLE);
    }

    @Override
    public void onUpdateAccountEmailPartialSuccess(@NonNull MitUpdateAccountEmailResponse response) {
        resetInputValidity();
        showErrorMessage(R.string.incorrectEmailAddressMessage);
    }

    @Override
    public void onUpdateAccountEmailSuccess(@NonNull MitUpdateAccountEmailResponse response) {
        resetInputValidity();
        updateExistingEmailInformation();
        displayToast(getString(R.string.emailUpdated));
        getStateEmitter().emitActionBarCustomization(ActionBarCustomization.STANDARD);
    }

    @Override
    public void onUpdateTextAlertFailure(@NonNull MitUpdateTextAlertsResponse response) {
        resetInputValidity();
        showErrorFrom(response);
        getPolicySession().getAccountInfoFlow().setUpdateDetailsServiceState(AceInformationState.UNAVAILABLE);
    }

    @Override
    public void onUpdateTextAlertSuccess(@NonNull MitUpdateTextAlertsResponse response) {
        resetInputValidity();
        displayToast("Text Alerts Updated");
        updateTextPreferencesFromResponse(response.getSubscriptions());
        logEvent(TEXT_MSG_ALERT_EDIT_COMPLETED);
        getStateEmitter().emitActionBarCustomization(ActionBarCustomization.STANDARD);
    }

    @Override
    public void onWeatherAlertsEnrollmentFailure(@NonNull MitResponse response) {
        // Do nothing
    }

    @Override
    public void onWeatherAlertsEnrollmentSuccess(@NonNull MitPrepareForWeatherAlertsResponse response) {
        new AceUpdateWeatherFlowFromPrepareForWeatherAlertResponse(getApplication(), getPolicySession().getPolicyNumber()).populate(response, getPolicySession().getWeatherFlow());
    }

    @Override
    public void prepareToUpdateTextAlerts() {
        markAsWaiting();
        postRequest(createPrepareToUpdateTextAlertNotificationRequest(),
                MitPrepareToUpdateTextAlertsResponse.class,
                AceTaskRequestType.PREPARE_TO_UPDATE_TEXT_ALERTS);
    }

    @Override
    @SuppressLint("SwitchIntDef")
    @SuppressWarnings({"unchecked"})
    public void reactToTaskResponse(AceResponse<?> response) {
        switch (response.requestType) {
            case AceTaskRequestType.ENROLL_IN_PUSH:
                new AcePushEnrollmentReaction(this)
                        .reactTo((AceResponse<MitPushNotificationsEnrollmentResponse>) response);
                break;
            case AceTaskRequestType.PREPARE_TO_UPDATE_TEXT_ALERTS:
                new AcePrepareToUpdateTextAlertsServiceReaction(this)
                        .reactTo((AceResponse<MitPrepareToUpdateTextAlertsResponse>) response);
                break;
            case AceTaskRequestType.UPDATE_ACCOUNT_EMAIL:
                new AceUpdateAccountEmailServiceReaction(this)
                        .reactTo((AceResponse<MitUpdateAccountEmailResponse>) response);
                break;
            case AceTaskRequestType.UPDATE_TEXT_ALERTS:
                new AceUpdateTextAlertsServiceReaction(this)
                        .reactTo((AceResponse<MitUpdateTextAlertsResponse>) response);
                break;
            default:
                break;
        }
    }

    private void resetInputValidity() {
        AceNotificationSettingsModel model = getModel();
        model.setPhoneValidity(AceUserInputValidityState.UNKNOWN);
        model.phoneNumberOneValidityState.set(AceUserInputValidityState.UNKNOWN);
        model.phoneNumberTwoValidityState.set(AceUserInputValidityState.UNKNOWN);
        model.phoneNumberThreeValidityState.set(AceUserInputValidityState.UNKNOWN);
        model.emailAddressValidityState.set(AceUserInputValidityState.UNKNOWN);
    }

    @Override
    public void sendPushEnrollmentRequest(@NonNull AcePushMessagingEnrollmentType type) {
        markAsWaiting();
        postRequest(createMessagingEnrollmentRequest(type), MitPushNotificationsEnrollmentResponse.class,
                getPolicySession().getUserId(), AceTaskRequestType.ENROLL_IN_PUSH, AceTaskType.TIER_TASK);
    }

    private boolean shouldShowWeatherEnrollmentCheckbox() {
        return getRegistry().getFeatureConfiguration().getModeForWeatherAlerts().isEnabled() && isEnrolledToWeatherAlerts();
    }

    private void showErrorMessage(@StringRes int errorMessageId) {
        getModel().errorText.set(getString(errorMessageId));
    }

    private void showResultDialog(boolean isSuccess, @StringRes int displayText) {
        getStateEmitter().emitPushEnrollmentCompleteDialogVisibility(Visibility.VISIBLE, isSuccess, getString(displayText));
    }

    @Override
    public void toggleEmailProductsNotificationsEnrollment() {
        getEmailPreferences().setProduct(getEnrollmentStateFrom(getModel().isEnrolledInProductEmails.get()));
    }

    @Override
    public void toggleNewsletterEnrollment() {
        getEmailPreferences().setNewsletter(getEnrollmentStateFrom(getModel().isEnrolledInNewsletterEmails.get()));
    }

    @Override
    public void togglePolicyServicesNotificationsEnrollment() {
        getEmailPreferences().setService(getEnrollmentStateFrom(getModel().isEnrolledInPolicyServiceEmails.get()));
    }

    @Override
    public void toggleSpecialOffersNotificationsEnrollment() {
        getEmailPreferences().setContests(getEnrollmentStateFrom(getModel().isEnrolledInSpecialOffersEmails.get()));
    }

    @Override
    public void updateAccountEmail() {
        markAsWaiting();
        postRequest(createUpdateAccountEmailRequest(),
                MitUpdateAccountEmailResponse.class,
                AceTaskRequestType.UPDATE_ACCOUNT_EMAIL);
    }

    private void updateExistingEmailInformation() {
        getPolicy().getContact().setEmailAddress(getModel().emailAddress.get());
        getModel().setExistingEmailPreferences(new AceEmailPreferences(getEmailPreferences()));
    }

    @Override
    public void updateTextAlerts() {
        markAsWaiting();
        postRequest(createUpdateTextAlertsRequest(),
                MitUpdateTextAlertsResponse.class,
                AceTaskRequestType.UPDATE_TEXT_ALERTS);
    }

    private void updateTextPreferencesFromResponse(List<MitTextAlertDeviceSubscription> subscriptions) {
        List<AceTextAlertPreferences> preferences = new AceTextAlertPreferencesFromMit().transformAll(subscriptions);
        new AceInitializeNotificationSettingsModelFromTextPreferences(getModel()).executeWith(preferences);
        getPolicySession().setTextPreferences(preferences);
    }

    @Override
    public void validateUserInput() {
        getRuleEngine().applyFirst(createEmailValidationRules(), getModel());
        new AceValidateNotificationSettingsPhoneInputs().executeWith(getModel());
        getRuleEngine().applyFirst(createNotificationSettingsPostValidationRules(), getModel());
    }
}