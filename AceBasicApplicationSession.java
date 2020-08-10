package com.geico.mobile.android.ace.geicoappbusiness.session;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceRunState;
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceRunState.AceRunStateVisitor;
import com.geico.mobile.android.ace.coreframework.patterns.AceVisitor;
import com.geico.mobile.android.ace.coreframework.patterns.valueholder.AceChangeableValueHolder;
import com.geico.mobile.android.ace.geicoappbusiness.apprater.AceApplicationRaterMode;
import com.geico.mobile.android.ace.geicoappbusiness.esignature.AceBasicEsignatureStatusContainer;
import com.geico.mobile.android.ace.geicoappbusiness.esignature.AceEsignatureStatusContainer;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesFlow;
import com.geico.mobile.android.ace.geicoappbusiness.opinionlab.AceOpinionLabSourceIndicator;
import com.geico.mobile.android.ace.geicoappbusiness.session.dialogsuppression.AceBasicDialogSuppressionContainer;
import com.geico.mobile.android.ace.geicoappbusiness.session.dialogsuppression.AceDialogSuppressionContainer;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceDivaConversationFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceEnrollmentFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceExperimentFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceFindRideFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceGeniusLinkFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceHelpCenterFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceHybridWebViewFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceLoginFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceOnboardingFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceOutageFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceParkingFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AcePhotoEstimateFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceResetPasswordFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceRideShareFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceRoadTrippersFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceSavedIdCardsFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceThirdPartyClaimantFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappmodel.AceLookupVehicleDetails;
import com.geico.mobile.android.ace.geicoappmodel.contactgeico.AceEmailDraft;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoappmodel.enums.upgrade.AceUpgradeMode;
import com.geico.mobile.android.ace.geicoappmodel.enums.users.AceUserRole;
import com.geico.mobile.android.ace.geicoappmodel.faqs.AceFrequentlyAskedQuestions;
import com.geico.mobile.android.ace.geicoappmodel.response.AceCheckInResponse;
import com.geico.mobile.android.ace.geicoappmodel.surveys.AceSurveyConfiguration;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitAuthenticatedRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCredentials;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkConfigurationResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Encapsulates data that once set is applicable for the entire duration the app
 * is running.
 * <p>
 * It is created during app launch and its scope may span several policy
 * sessions and user sessions.
 * <p>
 * This class is mutable and therefore only safe on the UI thread.
 *
 * @author Gopal Palanisamy
 */
public class AceBasicApplicationSession implements AceApplicationSession {

    private final List<String> completedCardsShown = new ArrayList<>();
    private final AceDialogSuppressionContainer dialogSuppressionContainer = new AceBasicDialogSuppressionContainer();
    private final AceDivaConversationFlow divaConversationFlow = new AceDivaConversationFlow();
    private final AceEnrollmentFlow enrollmentFlow = new AceEnrollmentFlow();
    private final AceEsignatureStatusContainer esignatureStatusContainer = new AceBasicEsignatureStatusContainer();
    private final AceExperimentFlow experimentFlow = new AceExperimentFlow();
    private final AceFindRideFlow findRideFlow = new AceFindRideFlow();
    private final AceGeniusLinkFlow geniusLinkFlow = new AceGeniusLinkFlow();
    private final AceGuestServicesFlow guestServicesFlow = new AceGuestServicesFlow();
    private final AceHelpCenterFlow helpCenterFlow = new AceHelpCenterFlow();
    private final AceHybridWebViewFlow hybridWebViewFlow = new AceHybridWebViewFlow();
    private final String id = UUID.randomUUID().toString();
    private final AceLoginFlow loginFlow = new AceLoginFlow();
    private final AceOnboardingFlow onboardingFlow = new AceOnboardingFlow();
    private final AceOutageFlow outageFlow = new AceOutageFlow();
    private final AceParkingFlow parkingFlow = new AceParkingFlow();
    private final AceResetPasswordFlow passwordFlow = new AceResetPasswordFlow();
    private final AcePhotoEstimateFlow photoEstimateFlow = new AcePhotoEstimateFlow();
    private final AceRideShareFlow rideShareFlow = new AceRideShareFlow();
    private final AceRoadTrippersFlow roadTrippersFlow = new AceRoadTrippersFlow();
    private final AceChangeableValueHolder<AceRunState> runStateHolder;
    private final AceSavedIdCardsFlow savedIdCardsFlow = new AceSavedIdCardsFlow();
    private final AceSurveyConfiguration surveyConfiguration = new AceSurveyConfiguration();
    private final AceThirdPartyClaimantFlow thirdPartyClaimantFlow = new AceThirdPartyClaimantFlow();
    private final AceUserFlow userFlow = new AceUserFlow();
    private final AceWebLinkSettings webLinkSettings = new AceWebLinkSettings();
    private boolean alertFromReadOnlyAccess;
    private AceApplicationRaterMode applicationRaterMode = AceApplicationRaterMode.MANUALLY_INITIATED;
    private AceCheckInResponse checkInResponse = new AceCheckInResponse();
    private AceInformationState clientRegistrationRequestState = AceInformationState.UNREQUESTED;
    private MitCredentials deviceRegistrationCredentials = new MitCredentials();
    private AceEmailDraft emailDraft = new AceEmailDraft();
    private boolean finishSessionFromCancelledPolicyAlert;
    private AceFrequentlyAskedQuestions frequentlyAskedQuestions = new AceFrequentlyAskedQuestions();
    private boolean isComingFromIntentPage = false;
    private boolean isFirstRun = false;
    private boolean keepMeLoggedInDialogHasBeenDisplayed = false;
    private AceLookupVehicleDetails lookupVehicleDetails = new AceLookupVehicleDetails();
    private String mobileClientIdAtStartup = "";
    private int openedSequence;
    private AceOpinionLabSourceIndicator opinionLabSourceIndicator = AceOpinionLabSourceIndicator.UNKNOWN;
    private String policyDetailPageNavigationAction = "";
    private boolean returningToMobileFromWebView = false;
    private String selectedDriverClientId = "";
    private String selectedVehicleId = "";
    private String webLinkUrl = "";

    public AceBasicApplicationSession(@NonNull AceChangeableValueHolder<AceRunState> runStateHolder) {
        this.runStateHolder = runStateHolder;
    }

    @Override
    public <I, O> O acceptVisitor(AceRunStateVisitor<I, O> visitor, I input) {
        return getRunState().acceptVisitor(visitor, input);
    }

    @Override
    public <O> O acceptVisitor(AceRunStateVisitor<Void, O> visitor) {
        return acceptVisitor(visitor, AceVisitor.NOTHING);
    }

    @NonNull
    @Override
    public <T extends MitAuthenticatedRequest> T createNotAuthenticatedRequest(Class<T> requestType) {
        try {
            T request = requestType.newInstance();
            request.setCredentials(deviceRegistrationCredentials);
            return request;
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

    @Override
    public AceApplicationRaterMode getApplicationRaterMode() {
        return applicationRaterMode;
    }

    @Override
    public void setApplicationRaterMode(AceApplicationRaterMode applicationRaterMode) {
        this.applicationRaterMode = applicationRaterMode;
    }

    @Override
    public AceCheckInResponse getCheckInResponse() {
        return checkInResponse;
    }

    @Override
    public void setCheckInResponse(AceCheckInResponse checkInResponse) {
        this.checkInResponse = checkInResponse;
    }

    @Override
    public AceInformationState getClientRegistrationRequestState() {
        return clientRegistrationRequestState;
    }

    @Override
    public void setClientRegistrationRequestState(AceInformationState clientRegistrationRequestState) {
        this.clientRegistrationRequestState = clientRegistrationRequestState;
    }

    @NonNull
    @Override
    public List<String> getCompletedCardsShown() {
        return completedCardsShown;
    }

    @Override
    public MitCredentials getDeviceRegistrationCredentials() {
        return deviceRegistrationCredentials;
    }

    @Override
    public void setDeviceRegistrationCredentials(MitCredentials credentials) {
        this.deviceRegistrationCredentials = credentials;
    }

    @NonNull
    @Override
    public AceDialogSuppressionContainer getDialogSuppressionContainer() {
        return dialogSuppressionContainer;
    }

    @NonNull
    @Override
    public AceDivaConversationFlow getDivaConversationFlow() {
        return divaConversationFlow;
    }

    @NonNull
    @Override
    public AceEmailDraft getEmailDraft() {
        return emailDraft;
    }

    @Override
    public void setEmailDraft(@NonNull AceEmailDraft emailDraft) {
        this.emailDraft = emailDraft;
    }

    @Override
    public AceEnrollmentFlow getEnrollmentFlow() {
        return enrollmentFlow;
    }

    @NonNull
    @Override
    public AceEsignatureStatusContainer getEsignatureStatusContainer() {
        return esignatureStatusContainer;
    }

    @Override
    public AceExperimentFlow getExperimentFlow() {
        return experimentFlow;
    }

    @Override
    public AceFindRideFlow getFindRideFlow() {
        return findRideFlow;
    }

    @Override
    public AceFrequentlyAskedQuestions getFrequentlyAskedQuestions() {
        return frequentlyAskedQuestions;
    }

    @Override
    public AceGeniusLinkFlow getGeniusLinkFlow() {
        return geniusLinkFlow;
    }

    @NonNull
    @Override
    public AceGuestServicesFlow getGuestServicesFlow() {
        return guestServicesFlow;
    }

    @NonNull
    @Override
    public AceHelpCenterFlow getHelpCenterFlow() {
        return helpCenterFlow;
    }

    @NonNull
    @Override
    public AceHybridWebViewFlow getHybridWebViewFlow() {
        return hybridWebViewFlow;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    @NonNull
    public AceLoginFlow getLoginFlow() {
        return loginFlow;
    }

    @Override
    public AceLookupVehicleDetails getLookupVehicleDetails() {
        return lookupVehicleDetails;
    }

    @Override
    public void setLookupVehicleDetails(AceLookupVehicleDetails lookupVehicleDetails) {
        this.lookupVehicleDetails = lookupVehicleDetails;
    }

    @Override
    public String getMobileClientIdAtStartup() {
        return mobileClientIdAtStartup;
    }

    @Override
    public void setMobileClientIdAtStartup(String mobileClientIdAtStartup) {
        this.mobileClientIdAtStartup = mobileClientIdAtStartup;
    }

    @Override
    public AceOnboardingFlow getOnboardingFlow() {
        return onboardingFlow;
    }

    @Override
    public AceOpinionLabSourceIndicator getOpinionLabSourceIndicator() {
        return opinionLabSourceIndicator;
    }

    @Override
    public void setOpinionLabSourceIndicator(AceOpinionLabSourceIndicator opinionLabSourceIndicator) {
        this.opinionLabSourceIndicator = opinionLabSourceIndicator;
    }

    @NonNull
    @Override
    public AceOutageFlow getOutageFlow() {
        return outageFlow;
    }

    @Override
    public AceParkingFlow getParkingFlow() {
        return parkingFlow;
    }

    @NonNull
    @Override
    public AcePhotoEstimateFlow getPhotoEstimateFlow() {
        return photoEstimateFlow;
    }

    @NonNull
    @Override
    public String getPolicyDetailPageNavigationAction() {
        return policyDetailPageNavigationAction;
    }

    @Override
    public void setPolicyDetailPageNavigationAction(@NonNull @NavigateAction String policyDetailPageNavigationAction) {
        this.policyDetailPageNavigationAction = policyDetailPageNavigationAction;
    }

    @Override
    public AceResetPasswordFlow getResetPasswordFlow() {
        return passwordFlow;
    }

    @Override
    @NonNull
    public AceRideShareFlow getRideShareFlow() {
        return rideShareFlow;
    }

    @Override
    public AceRoadTrippersFlow getRoadTrippersFlow() {
        return roadTrippersFlow;
    }

    private AceRunState getRunState() {
        return runStateHolder.getValue();
    }

    @Override
    public void setRunState(@NonNull AceRunState runState) {
        runStateHolder.setValue(runState);
    }

    @Override
    public AceSavedIdCardsFlow getSavedIdCardsFlow() {
        return savedIdCardsFlow;
    }

    @NonNull
    @Override
    public String getSelectedDriverClientId() {
        return selectedDriverClientId;
    }

    @Override
    public void setSelectedDriverClientId(@NonNull String selectedDriverClientId) {
        this.selectedDriverClientId = selectedDriverClientId;
    }

    @Override
    public String getSelectedVehicleId() {
        return selectedVehicleId;
    }

    @Override
    public void setSelectedVehicleId(@NonNull String selectedVehicleId) {
        this.selectedVehicleId = selectedVehicleId;
    }

    @Override
    public AceSurveyConfiguration getSurveyConfiguration() {
        return surveyConfiguration;
    }

    @Override
    public AceThirdPartyClaimantFlow getThirdPartyClaimantFlow() {
        return thirdPartyClaimantFlow;
    }

    @Override
    public AceUpgradeMode getUpgradeMode() {
        return checkInResponse.getUpgradeMode();
    }

    @Override
    public AceUserFlow getUserFlow() {
        return userFlow;
    }

    @Override
    public AceUserRole getUserRole() {
        return userFlow.getUserRole();
    }

    @Override
    public MitWebLinkConfigurationResponse getWebLinkConfigurationResponse() {
        return webLinkSettings.getWebLinkConfigurationResponse();
    }

    @Override
    public void setWebLinkConfigurationResponse(MitWebLinkConfigurationResponse response) {
        webLinkSettings.setWebLinkConfigurationResponse(response);
    }

    @Override
    public AceWebLinkSettings getWebLinkSettings() {
        return webLinkSettings;
    }

    @NonNull
    @Override
    public String getWebLinkUrl() {
        return webLinkUrl;
    }

    @Override
    public void setWebLinkUrl(@NonNull String webLinkUrl) {
        this.webLinkUrl = webLinkUrl;
    }

    @Override
    public boolean hasKeepMeLoggedInDialogHasBeenDisplayed() {
        return this.keepMeLoggedInDialogHasBeenDisplayed;
    }

    @Override
    public boolean isAlertFromReadOnlyAccess() {
        return alertFromReadOnlyAccess;
    }

    @Override
    public void setAlertFromReadOnlyAccess(boolean readOnlyAccessAlert) {
        this.alertFromReadOnlyAccess = readOnlyAccessAlert;
    }

    @Override
    public boolean isComingFromIntentPage() {
        return isComingFromIntentPage;
    }

    @Override
    public boolean isFinishSessionFromCancelledPolicyAlert() {
        return finishSessionFromCancelledPolicyAlert;
    }

    @Override
    public void setFinishSessionFromCancelledPolicyAlert(boolean finishSessionFromCancelledPolicyAlert) {
        this.finishSessionFromCancelledPolicyAlert = finishSessionFromCancelledPolicyAlert;
    }

    @Override
    public boolean isFirstRun() {
        return isFirstRun;
    }

    @Override
    public boolean isReturningToMobileFromWebView() {
        return returningToMobileFromWebView;
    }

    @Override
    public boolean isStarting() {
        return getRunState().isStarting();
    }

    @Override
    public boolean isStopped() {
        return getRunState().isStopped();
    }

    @Override
    public boolean isUpgradeRequiredToLogin() {
        return getUpgradeMode().isUpgradeRequiredToLogin();
    }

    @Override
    public int nextOpenedSequenceValue() {
        return ++openedSequence;
    }

    @Override
    public void resetCompletedCardsShown() {
        getCompletedCardsShown().clear();
    }

    @Override
    public void setFrequentlyAskedQuestionsResponse(AceFrequentlyAskedQuestions frequentlyAskedQuestionsResponse) {
        this.frequentlyAskedQuestions = frequentlyAskedQuestionsResponse;

    }

    @Override
    public void setIsComingFromIntentPage(boolean isComingFromIntentPage) {
        this.isComingFromIntentPage = isComingFromIntentPage;
    }

    @Override
    public void setIsFirstRun(boolean isFirstRun) {
        this.isFirstRun = isFirstRun;
    }

    @Override
    public void setKeepMeLoggedInDialogHasBeenDisplayed(boolean keepMeLoggedInDialogHasBeenDisplayed) {
        this.keepMeLoggedInDialogHasBeenDisplayed = keepMeLoggedInDialogHasBeenDisplayed;
    }

    @Override
    public void setReturnToMobileFromWebViewState(boolean isReturningToMobile) {
        this.returningToMobileFromWebView = isReturningToMobile;
    }

    @Override
    public void setWebLinkVersionFromTier(String version) {
        webLinkSettings.setWebLinkVersionFromTier(version);
    }

    @Override
    public boolean shouldShowUpgradeDialogAtStartup() {
        return getUpgradeMode().shouldShowUpgradeDialogAtStartup();
    }
}