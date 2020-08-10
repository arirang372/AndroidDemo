package com.geico.mobile.android.ace.geicoappbusiness.session;

import java.util.List;

import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceRunState;
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceRunState.AceRunStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.apprater.AceApplicationRaterMode;
import com.geico.mobile.android.ace.geicoappbusiness.esignature.AceEsignatureStatusContainer;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesFlow;
import com.geico.mobile.android.ace.geicoappbusiness.opinionlab.AceOpinionLabSourceIndicator;
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

import androidx.annotation.NonNull;

/**
 * API that Encapsulates data that once set is applicable for the entire
 * duration the app is running. A application session is created during app
 * launch and its scope may span several policy sessions and user sessions.
 * <p>
 * This API must only be accessed from the UI thread.
 *
 * @author Gopal Palanisamy
 */
public interface AceApplicationSession {

	/**
	 * Invoke the visitor method that corresponds to the current lifecycle phase
	 * and does require input.
	 *
	 * @param visitor that behave differently depending on lifecycle phase
	 * @param input any input required by the visitor, may be null
	 * @return any output returned by the visitor, may be null
	 */
	<I, O> O acceptVisitor(AceRunStateVisitor<I, O> visitor, I input);

	/**
	 * Invoke the visitor method that corresponds to the current lifecycle phase
	 * and does not require input.
	 *
	 * @param visitor that behave differently depending on lifecycle phase
	 * @return any output returned by the visitor, may be null
	 */
	<O> O acceptVisitor(AceRunStateVisitor<Void, O> visitor);

	/**
	 * @return {@link com.geico.mobile.android.ace.mitsupport.mitmodel.MitAuthenticatedRequest}
	 * on {@link AceApplicationSession} level
	 *
	 */
	@NonNull
	<T extends MitAuthenticatedRequest> T createNotAuthenticatedRequest(Class<T> requestType);

	/**
	 * Get an object that tracks how the app rater was invoked.
	 *
	 * @return an AceApplicationRaterMode, never null
	 */
	AceApplicationRaterMode getApplicationRaterMode();

	/**
	 * Get the result of the service that provides configuration details for web
	 * links, contact GEICO phone number, modes for features availability, and
	 * field validations.
	 *
	 * @return a AceCheckInResponse, never null
	 */
	AceCheckInResponse getCheckInResponse();

	/**
	 * Get current state of the client registration request
	 *
	 * @return an AceInformationState, never null
	 */
	AceInformationState getClientRegistrationRequestState();

	/**
	 * Return the list of Dashboard completed card types that was shown in the current session.
	 * <p>
	 * Value list
	 *<ul>
	 *<li>Automatic payment enrollment</li>
	 *<li>Esignature </li>
	 *<li>Missing Driver's license</li>
	 *<li>Paperless option enrollment</li>
	 *<li>Payment</li>
	 *</ul>
	 *
	 * @return List of completed card types
	 */
	List<String> getCompletedCardsShown();

	/**
	 * @return device credentials to be used during the unauthenticated, not
	 *         logged in, session
	 */
	MitCredentials getDeviceRegistrationCredentials();

	/**
	 * Returns an object that contains the state and behavior required to suppress certain dialogs at appropriate times throughout the application session.
	 * @return container controlling dialog suppression
	 */
	@NonNull
	AceDialogSuppressionContainer getDialogSuppressionContainer();

	/**
	 * Returns transient information about the DiVA-M conversation.
	 *
	 * @return data needed to conduct conversation with DiVA-M
	 */
	@NonNull
	AceDivaConversationFlow getDivaConversationFlow();

	/**
	 * Retrieve the saved draft of an email on the Contact Geico by Email page
	 *
	 * @return Object representing the most recently saved draft
	 */
	@NonNull
	AceEmailDraft getEmailDraft();

	/**
	 * @return model used during customer enrollment
	 */
	AceEnrollmentFlow getEnrollmentFlow();

	/**
	 * Get the object control access to the esignature status values associated with policies downloaded during this
	 * application session.
	 *
	 * @return object that accesses esignature status values
	 */
	@NonNull
	AceEsignatureStatusContainer getEsignatureStatusContainer();

	/**
	 * Get transient information about the experiment(s).
	 *
	 * @return model used to hold experiments (A\B testing campaigns)
	 */
	AceExperimentFlow getExperimentFlow();

	/**
	 * Get the model that backs the views where the customer is working on a
	 * FindRide flow to find rides to places.
	 *
	 * @return a AceRoadTrippersDestinationsFlow
	 */
	AceFindRideFlow getFindRideFlow();

	/**
	 * Get the response from the service that provides the list of frequently
	 * asked questions.
	 *
	 * @return response a AceFrequentlyAskedQuestionsResponse, will not be null
	 */
	AceFrequentlyAskedQuestions getFrequentlyAskedQuestions();

	/**
	 * Get the model that backs when the customer has entered the app through the Genius Link
	 *
	 * @return AceGeniusLinkFlow
	 */
	AceGeniusLinkFlow getGeniusLinkFlow();

	/**
	 * Returns the information about guest services views shared between the activity and the fragment
	 *
	 * @return AceGuestServicesFlow
	 */
	AceGuestServicesFlow getGuestServicesFlow();

	/**
	 * Get the model that backs the views\operations where the customer is
	 * engaging with the Help Center function.
	 *
	 * @return an AceHelpCenterFlow
	 */
	@NonNull
	AceHelpCenterFlow getHelpCenterFlow();

	/**
	 * Returns transient information about the Hybrid WebView.
	 *
	 * @return data containing information about the current hybrid web view
	 */
	@NonNull
	AceHybridWebViewFlow getHybridWebViewFlow();

	/**
	 * Get a key that uniquely identifies an single run of the client app, from
	 * the time it first starts executing until the time it is stopped. Unlike
	 * the breadcrumbId it may remain the same over multiple logins even with
	 * userNames.
	 * <p>
	 * This is the counterpart to the browser session scoped ApplicationSession
	 * Cookie (Aka visitorKey) in Edge. It will tie metrics and logs together
	 * over native and hybrid sessions with Edge.
	 * <p>
	 * This value for this field will be used in the traceId that is part of the
	 * coordinated Enterprise logging effort.
	 * <p>
	 * This value is sent to Tier as MitRequest.applicationSessionId
	 *
	 * @return e.g., "1b4f3fe3-695a-44aa-8dbe-be67d0cbf9b0"
	 */
	String getId();

	/**
	 * Get transient information about the authentication process.
	 *
	 * @return an AceLoginFlow, never null
	 */
	AceLoginFlow getLoginFlow();

	/**
	 * Get the response from the service that provides the list of vehicle colors and gas types.
	 *
	 * @return response a AceLookupVehicleDetails, cannot be null
	 */
	AceLookupVehicleDetails getLookupVehicleDetails();

	/**
	 * Get the value of the mobile client id determined prior to initializing
	 * the registry.
	 *
	 * @return mobileClientIdAtStartup value
	 */
	String getMobileClientIdAtStartup();

	/**
	 * Get the data needed for onboarding
	 *
	 * @return onboardingFlow value
	 */
	AceOnboardingFlow getOnboardingFlow();

	/**
	 * Retrieve opinion lab comment source
	 * @return OpinionLabSourceIndicator (REPORT_BUG, FEEDBACK or APPRATER)
	 */
	AceOpinionLabSourceIndicator getOpinionLabSourceIndicator();

	/**
	 * @return model used during a planned outage
	 */
	AceOutageFlow getOutageFlow();

	/**
	 * Get the model that backs the views where the customer is working on a
	 * parking flow to find parking.
	 *
	 * @return a AceParkingFlow
	 */
	AceParkingFlow getParkingFlow();

	/**
	 * Get the model that backs the views that relate to Easy Photo Estimate
	 * @return the flow handling data related to Easy Photo Estimate
	 */
	@NonNull
	AcePhotoEstimateFlow getPhotoEstimateFlow();

	/**
	 * Gets the policy detail page navigation action listed on NavigateAction on AceConstantState.java
	 * e.g. NavigateAction.COVERAGE_FRAGMENT, NavigateAction.DISCOUNT_FRAGMENT, NavigateAction.DRIVER_DETAIL_FRAGMENT, etc
	 *
	 * @return String policy detail page navigation action
	 */
	String getPolicyDetailPageNavigationAction();

	/**
	 * Get transient information about the reset password flow.
	 *
	 * @return a reset password flow.
	 */
	AceResetPasswordFlow getResetPasswordFlow();

	/**
	 * Get the model that backs the views where the customer is getting
	 * a ride share ride.
	 *
	 * @return a AceRideShareFlow object
	 */
	@NonNull
	AceRideShareFlow getRideShareFlow();

	/**
	 * Get the model that backs the views where the customer is working on a
	 * roadTrippers flow to find places.
	 *
	 * @return a AceRoadTrippersDestinationsFlow
	 */
	AceRoadTrippersFlow getRoadTrippersFlow();

	/**
	 * Get the model that backs the views where the user views saved ID cards
	 *
	 * @return an AceSavedIdCardsFlow
	 */
	AceSavedIdCardsFlow getSavedIdCardsFlow();

	/**
	 * Gets the driver client id from currently selected Driver on DriverDetailFragment
	 *
	 */
	String getSelectedDriverClientId();
	/**
	 * Gets the vehicle id from currently selected Vehicle on VehicleDetailFragment
	 *
	 */
	String getSelectedVehicleId();
	/**
	 * Gets configuration information for customer satisfaction surveys.
	 *
	 * @return an AceSurveyConfiguration
	 */
	AceSurveyConfiguration getSurveyConfiguration();

	/**
	 * Get transient information about the third party view claim flow.
	 *
	 * @return a view claim flow.
	 */
	AceThirdPartyClaimantFlow getThirdPartyClaimantFlow();

	/**
	 * Get the current strategy for migrating the user to the the latest version of the App.
	 *
	 * @return e.g., NO_UPGRADE, MUST_UPGRADE_TO_LOGIN, MUST_UPGRADE_ON_START_UP...
	 */
	AceUpgradeMode getUpgradeMode();

	/**
	 * Get the model that backs the views where the user profiles can impact
	 * behavior.
	 *
	 * @return an AceUserFlow
	 */
	AceUserFlow getUserFlow();

	/**
	 * Get the capacity of the current device user.
	 *
	 * @return for example: AceUserRole.DRIVER
	 */
	AceUserRole getUserRole();

	/**
	 * Get the response from the service that describes the linked web
	 * destinations.
	 *
	 * @return response a MitWebLinkConfigurationResponse, will not be null
	 */
	MitWebLinkConfigurationResponse getWebLinkConfigurationResponse();

	/**
	 * Get an object that models how the app will access specific web pages.
	 *
	 * @return a AceWebLinkSettings, never null
	 */
	AceWebLinkSettings getWebLinkSettings();

	/**
	 * Get the currently active weblink url.
	 * Intended for metrics use.
	 *
	 * @return url
	 */
	@NonNull
	String getWebLinkUrl();

	/**
	 * Checks if the Keep me logged in enrollment dialog has already been displayed
	 *
	 * @return true/false
	 */
	boolean hasKeepMeLoggedInDialogHasBeenDisplayed();

	boolean isAlertFromReadOnlyAccess();

	/**
	 * Get the flag that determines whether the login page has not yet been viewed
	 *
	 */
	boolean isComingFromIntentPage();

	boolean isFinishSessionFromCancelledPolicyAlert();

	/**
	 * Get first run state.
	 *
	 * @return boolean, never null
	 */
	boolean isFirstRun();

	/**
	 * Answer whether the user is returning back to the app from a webView
	 *
	 * @return true/false
	 */
	boolean isReturningToMobileFromWebView();

	/**
	 * Answer whether the system is becoming operational.
	 * <p>
	 * During the starting phase the application is showing the splash screen
	 * and letting the check in service complete.
	 *
	 * @return true/false
	 */
	boolean isStarting();

	/**
	 * Answer whether the system is off.
	 *
	 * @return true/false
	 */
	boolean isStopped();

	/**
	 * Answer whether the user will be forced to install the most recent version
	 * of the app the next time they login.
	 *
	 * @return true/false
	 */
	boolean isUpgradeRequiredToLogin();

	/**
	 * Answer a number that is incremented each time this method is called.  It is used to keep track of the order in which certain fragments are opened.
	 *
	 * @return 1, 2, 3
	 */
	int nextOpenedSequenceValue();

	/**
	 * Clears completed cards that was shown in the previous policy session.
	 */
	void resetCompletedCardsShown();

	void setAlertFromReadOnlyAccess(boolean readOnlyAccessAlert);

	/**
	 * Set an object that tracks how the app rater was invoked.
	 *
	 * @param applicationRaterMode an AceApplicationRaterMode, never null
	 */
	void setApplicationRaterMode(AceApplicationRaterMode applicationRaterMode);

	/**
	 * Set the result of the service that provides configuration details for web
	 * links, contact GEICO phone number, modes for features availability, and
	 * field validations.
	 * <p>
	 * It provides configuration details for web links, contact GEICO phone
	 * number, modes for features availability, and field validations.
	 *
	 * @param checkInResponse must not be null
	 */
	void setCheckInResponse(AceCheckInResponse checkInResponse);

	/**
	 * Set information state of client registration service
	 */
	void setClientRegistrationRequestState(AceInformationState informationState);

	/**
	 * @param credentials - sets device credentials to be used during the
	 *            unauthenticated, not logged in, session.
	 */
	void setDeviceRegistrationCredentials(MitCredentials credentials);

	/**
	 * Saves a draft of an incomplete email entered on the Contact Geico by Email page
	 *
	 * @param emailDraft object representing the entered text, category, and user ID for the draft
	 */
	void setEmailDraft(@NonNull AceEmailDraft emailDraft);

	void setFinishSessionFromCancelledPolicyAlert(boolean finishSessionFromCancelledPolicyAlert);

	/**
	 * Set the response from the service that provides the list of frequently
	 * asked questions.
	 *
	 * @param response must not be null
	 */
	void setFrequentlyAskedQuestionsResponse(AceFrequentlyAskedQuestions response);

	/**
	 * Set the flag that determines whether the login page has not yet been viewed
	 *
	 * @param isComingFromIntentPage - is the boolean flag that is set
	 */
	void setIsComingFromIntentPage(boolean isComingFromIntentPage);

	/**
	 * Set first run state.
	 *
	 * @param isFirstRun must not be null
	 */
	void setIsFirstRun(boolean isFirstRun);

	/**
	 * Sets the flag that keeps track on if the Keep me logged in enrollment dialog to be displayed
	 *
	 * @param keepMeLoggedInDialogHasBeenDisplayed - is the boolean flag that is set
	 */
	void setKeepMeLoggedInDialogHasBeenDisplayed(boolean keepMeLoggedInDialogHasBeenDisplayed);

	/**
	 * Set the response from the service that provides the list of vehicle colors and gas types
	 *
	 * @param lookupVehicleDetails must not be null
	 */
	void setLookupVehicleDetails(AceLookupVehicleDetails lookupVehicleDetails);

	/**
	 * Set the value of the mobile client id determined prior to initializing
	 * the registry.
	 *
	 * @param mobileClientIdAtStartup value
	 */
	void setMobileClientIdAtStartup(String mobileClientIdAtStartup);

	/**
	 * Stores opinion lab comment source
	 * @param sourceIndicator value
	 */
	void setOpinionLabSourceIndicator(AceOpinionLabSourceIndicator sourceIndicator);

	/**
	 * Sets the policy detail page navigation action listed on NavigationAction, AceConstantState.java
	 *
	 * @param policyDetailPageNavigationAction for example: NavigateAction.COVERAGE_FRAGMENT, NavigateAction.DISCOUNT_FRAGMENT, etc
	 */
	void setPolicyDetailPageNavigationAction(@NonNull @NavigateAction String policyDetailPageNavigationAction);

	/**
	 * Set true if returning back to the app from a webView
	 *
	 * @param isReturningToMobile value
	 */
	void setReturnToMobileFromWebViewState(boolean isReturningToMobile);

	/**
	 * Set the lifecycle phase of the GEICO App.
	 *
	 * @param runState STARTING, RUNNING, STOPPING, STOPPED
	 */
	void setRunState(@NonNull AceRunState runState);

	/**
	 * Sets the driver client id from currently selected Driver on DriverDetailFragment
	 *
	 * @param selectedDriverClientId
	 */
	void setSelectedDriverClientId(@NonNull String selectedDriverClientId);

	/**
	 * Sets the vehicle id from currently selected Vehicle on VehicleDetailFragment
	 *
	 * @param selectedVehicleId
	 */
	void setSelectedVehicleId(@NonNull String selectedVehicleId);

	/**
	 * Set the response from the service that describes the linked web
	 * destinations.
	 *
	 * @param response must not be null
	 */
	void setWebLinkConfigurationResponse(MitWebLinkConfigurationResponse response);

	/**
	 * Sets the weblink url that the app is getting ready to display to the user.
	 * Intended for metrics use.
	 *
	 * @param webLinkUrl that the app is in the process of displaying to the user
	 */
	void setWebLinkUrl(@NonNull String webLinkUrl);

	/**
	 * Set a unique identifier of the specific configuration of the web links
	 * that should be used the current tier environment. This is used to
	 * determine when a new configuration needs to be retrieved from tier.
	 *
	 * @param version for example: "4HquK3MYQpqsC1LIUKNUHC10+B
	 */
	void setWebLinkVersionFromTier(String version);

	/**
	 * Answer whether the user should be told to install the most recent version
	 * of the app as soon as the GEICO app begins.
	 *
	 * @return true/false
	 */
	boolean shouldShowUpgradeDialogAtStartup();
}