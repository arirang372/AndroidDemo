package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository;

import android.Manifest;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceRunState;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceEvent;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceListener;
import com.geico.mobile.android.ace.coreframework.features.AceBaseFeatureModeVisitor;
import com.geico.mobile.android.ace.coreframework.patterns.AceExecutable;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.geicoappbusiness.derivations.AceSpecialtyVehiclesFromPolicy;
import com.geico.mobile.android.ace.geicoappbusiness.findgas.rules.AceFindGasFilterSettingsHolder;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants;
import com.geico.mobile.android.ace.geicoappbusiness.login.AcePostLoginDestinationDeterminer;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceRapidSessionFinisher;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor.AceViewExhibitorVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceMobilePagedActionEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceMailingAddressEditActionType;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceMailingAddressEditEvent;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicPersonProfileEditMonitor;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicUserProfileSynchronizer;
import com.geico.mobile.android.ace.geicoappbusiness.users.AcePersonProfileEditMonitor;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileDetailEvent;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSessionFinisher;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.enums.AceBaseHasOptionStateVisitor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceBaseOutOfGasTypeVisitor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.findgas.AceFindGasProduct;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AcePersonalPolicyProfile;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVisibilityState;
import com.geico.mobile.android.ace.geicoappmodel.visitors.AceBaseContactGeicoTypeVisitor;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.email.mvvm.view.AceSendAnEmailActivity;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository.AceEnrollWithDeviceUnlockHelper;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository.AceKeepMeLoggedInHelper;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceDashboardActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceRecommendations;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceRecommendationsDTO;
import com.geico.mobile.android.ace.geicoapppresentation.login.AceLoginActivity;
import com.geico.mobile.android.ace.geicoapppresentation.permission.AceLocationPermissionForWebLinkActivity;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers.AceUserProfileListItemClickRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPhoneValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsDriver;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_USERS;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_COA_START_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_CREATED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_EDIT;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SET;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SWITCH;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.COA_START_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_CREATED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_EDIT;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_SWITCH;

/**
 * Repository responsible for handling business logic for the User profile component.
 *
 * @author John Sung, Geico
 */
public class AceUsersRepository extends AceTierRepository<AceUsersModel> {

	public AceUsersRepository(@NonNull Application application) {
		super(application);
	}

	public void considerDisplayingUsersDialogs() {
		if (ViewTag.USERS_FRAGMENT.equals(getModel().viewTag)) {
			new AceEnrollWithDeviceUnlockHelper().considerDisplayingDialogWith(this);
			new AceKeepMeLoggedInHelper().considerDisplayingDialogWith(this);
		}
	}

	private void considerPerformingChangeOfAddress() {
		if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
			getRegistry().getPermissionCategoryManager().setAction(MitWebLinkNames.UPDATE_ADDRESS);
			startActivity(AceLocationPermissionForWebLinkActivity.class);
			return;
		}
		openFullSite(MitWebLinkNames.UPDATE_ADDRESS);
	}

	private void considerResettingDriverDataInformationState() {
		if (getPolicy().getPolicyLocation().isDuckCreek()) {
			getApplicationSession().getTelematicsFlow().setDriverInformationState(AceInformationState.UNREQUESTED);
		}
	}

	private void considerResettingSpinnerPositions(int position) {
		if (position == 0) {
			AceUserSettingsSectionItem fuelTypeSectionItem = getUserSettingSectionItemBy(1, 1);
			fuelTypeSectionItem.setSelectedFuelTypePosition(0);
			handleSelectedFuelTypeSpinnerItem(AceOutOfGasTypeEnum.REGULAR_UNLEADED);
			AceUserSettingsSectionItem vehicleColorSectionItem = getUserSettingSectionItemBy(1, 2);
			vehicleColorSectionItem.setSelectedVehicleColorPosition(0);
			handleSelectedVehicleColorAction(AceVehicleColor.UNKNOWN_COLOR);
		}
	}

	private void considerUpdatingPhoneNumberAndWorkAddress() {
		if (isValidPhoneNumber()) {
			getUserFlow().getPerson().setMobilePhoneNumber(getUserSettingSectionItemBy(0, 1).getText().get());
		}
		getUserFlow().getPerson().setWorkAddress(getUserFlow().getTemporaryWorkAddress());
	}

	@VisibleForTesting
	protected AceListener<AceEvent<String, String>> createListenerForReturnToLoginPage() {
		return new AceListener<AceEvent<String, String>>() {
			@Override
			public String getEventId() {
				return AceLoginConstants.FINISH_SESSION_AND_LET_USER_SEE_LOGIN_PAGE;
			}

			@Override
			public void onEvent(AceEvent<String, AceEvent<String, String>> event) {
				navigateUserToLoginPage(event.getSubject());
			}
		};
	}

	@NonNull
	@Override
	protected AceModelLiveData<AceUsersModel> createModelLiveData() {
		return new AceModelLiveData<>(new AceUsersModelFactory(getPolicy().getNumber()).create(getUserFlow()));
	}

	@VisibleForTesting
	protected AcePersonProfileEditMonitor createPersonProfileEditMonitor() {
		return new AceBasicPersonProfileEditMonitor(getSessionController());
	}

	@VisibleForTesting
	protected AceExecutableWith<AceSessionController> createPostLoginDestinationDeterminer() {
		return new AcePostLoginDestinationDeterminer();
	}

	@VisibleForTesting
	protected AceRecommendationsDTO createRecommentationsDTO() {
		return new AceRecommendations(getRegistry());
	}

	@VisibleForTesting
	protected AceUserProfileListItemClickRuleFactory createUserProfileListItemClickRuleFactory() {
		return new AceUserProfileListItemClickRuleFactory(getRegistry(), this);
	}

	@VisibleForTesting
	protected AceUserSettingsPhoneValidationRuleFactory createUserSettingsPhoneValidationRuleFactory() {
		return new AceUserSettingsPhoneValidationRuleFactory(getModel(), this::getString);
	}

	@VisibleForTesting
	protected AceUserSettingsPopulator createUserSettingsPopulator() {
		return new AceUserSettingsPopulator(this::getString);
	}

	private void determinePostLoginDestination() {
		createPostLoginDestinationDeterminer().executeWith(getSessionController());
		getSessionController().startAction(getApplication(), getPolicySession().getPostLoginAction());
	}

	private void finishUserSetUp() {
		considerUpdatingPhoneNumberAndWorkAddress();
		getUserFlow().setViewState(AceViewExhibitor.VIEW_SHOWED_TO_USER);
	}

	@VisibleForTesting
	protected AcePersonalPolicyProfile getPersonalPolicyProfileFrom(String policyNumber) {
		return getUserFlow().getPerson().getPersonalPolicyProfile(policyNumber);
	}

	@VisibleForTesting
	protected AceUserProfileVehicle getPrimaryVehicle() {
		return getUserFlow().getPerson().getPrimaryVehicle(getPolicy().getNumber());
	}

	@VisibleForTesting
	protected AceUserFlow getUserFlow() {
		return getApplicationSession().getUserFlow();
	}

	private AceUserSettingsSectionItem getUserSettingSectionItemBy(int listItemIndex, int sectionItemIndex) {
		return getModel().getUserSettingsListItems().get(listItemIndex).getUserSettingsSectionItems().get(sectionItemIndex);
	}

	public void handleBackPressedAction() {
		getUserFlow().setViewState(AceViewExhibitor.VIEW_SHOWED_TO_USER);
		getSessionController().getUserSession().getDashfolioFlow().setNextPageAction(ACTION_USERS);
		getUserFlow().getViewState().acceptVisitor(new AceViewExhibitorVisitor<Void, Void>() {
			@Override
			public Void visitOtherwise(Void input) {
				return NOTHING;
			}

			@Override
			public Void visitViewNotShowedToUser(Void input) {
				new AceUserProfileSessionFinisher(getRegistry()).execute();
				return NOTHING;
			}

			@Override
			public Void visitViewShowedToUser(Void input) {
				return NOTHING;
			}
		});
	}

	public void handleContinueButtonAction() {
		if (!isValidPhoneNumber()) return;
		finishUserSetUp();
		determinePostLoginDestination();
	}

	public void handleEditButtonClickAction() {
		logStartEditUserEvent();
		emitNavigation(AceConstantState.NavigateAction.USER_SETTINGS_FRAGMENT);
		logEvent(new AceUserProfileDetailEvent(FINISH_EDIT_USER_PROFILE));
	}

	public void handleEditUserSettingsButtonAction() {
		logEvent(START_MAIL_ADDRESS_EDIT);
		logEvent(new AceMailingAddressEditEvent(AceMailingAddressEditActionType.PAGE));
		trackAction(ANALYTICS_COA_START_SELECTED, COA_START_SELECTED);
		createRecommentationsDTO().rememberAddressChangeInProgress();
		if (isSpecialtyVehicle()) {
			openDialog(DialogTag.CALL_TO_MAKE_CHANGES);
			return;
		}
		considerPerformingChangeOfAddress();
	}

	public void handleListItemRowClickAction(@NonNull AceUserProfilePerson profilePerson) {
		AceSimpleRuleEngine.DEFAULT.applyFirst(createUserProfileListItemClickRuleFactory().create(), profilePerson);
	}

	public void handleSelectedFuelTypeSpinnerItem(@NonNull AceOutOfGasTypeEnum selectedFuelType) {
		getPrimaryVehicle().setPreferredFuelType(selectedFuelType);
		AceFindGasFilterSettingsHolder findGasSettings = getRegistry().getFindGasFacade().getSettings();
		selectedFuelType.acceptVisitor(new AceBaseOutOfGasTypeVisitor<Void, Void>() {
			@Override
			public Void visitAnyType(Void input) {
				findGasSettings.setFuelCriteria(AceFindGasProduct.REGULAR);
				return NOTHING;
			}

			@Override
			public Void visitDiesel(Void input) {
				findGasSettings.setFuelCriteria(AceFindGasProduct.DIESEL);
				return NOTHING;
			}

			@Override
			public Void visitElectric(Void input) {
				findGasSettings.setFuelCriteria(AceFindGasProduct.ELECTRIC);
				return NOTHING;
			}

			@Override
			public Void visitPremiumUnleaded(Void input) {
				findGasSettings.setFuelCriteria(AceFindGasProduct.PREMIUM);
				return NOTHING;
			}
		});
		getRegistry().getFindGasFacade().saveSettings(findGasSettings);
	}

	public void handleSelectedUserHasNeverBeenSetUpRule(@NonNull AceUserProfilePerson person) {
		logEvent(new AceMobilePagedActionEvent(START_NEW_USER_PROFILE, getString(R.string.newUser)));
		updateUserProfileFrom(person);
		if (getUserFlow().getFullName().isEmpty()) {
			recordMetricsForFinishSwitchUser();
		}
		considerResettingDriverDataInformationState();
		getApplicationSession().setMailingAddressVisibilityState(AceUserProfileVisibilityState.SHOWN_FOR_FRESH_INSTALL);
		emitNavigation(AceConstantState.NavigateAction.USER_SETTINGS_FRAGMENT);
	}

	public void handleSelectedUserOtherwiseRule(@NonNull AceUserProfilePerson person) {
		updateUserProfileFrom(person);
		recordMetricsForFinishSwitchUser();
		considerResettingDriverDataInformationState();
		startActivity(AceDashboardActivity.class);
	}

	public void handleSelectedVehicleAction(@NonNull AceUserProfileVehicle selectedVehicle, int position) {
		getPersonalPolicyProfileFrom(getPolicy().getNumber()).setPrimaryVehicle(selectedVehicle);
		considerResettingSpinnerPositions(position);
		synchronizeUserProfile();
	}

	public void handleSelectedVehicleColorAction(@NonNull AceVehicleColor selectedVehicleColor) {
		getPrimaryVehicle().setColor(selectedVehicleColor);
	}

	public void handleSkipButtonAction() {
		isValidPhoneNumber();
		finishUserSetUp();
		determinePostLoginDestination();
	}

	public void initializeModel() {
		createUserSettingsPopulator().populate(getRegistry(), getModel());
	}

	private boolean isFinishSessionFromCancelledPolicyAlert(String id) {
		return getFeatureConfiguration().getModeForCancelledPolicyPage().acceptVisitor(new AceBaseFeatureModeVisitor<Void, Boolean>() {

			@Override
			protected Boolean visitAnyEnabledMode(Void input) {
				return "READ_ONLY_ACCESS".equals(id);
			}

			@Override
			public Boolean visitDisabled(Void input) {
				return false;
			}
		});
	}

	@VisibleForTesting
	protected boolean isSpecialtyVehicle() {
		return !(new AceSpecialtyVehiclesFromPolicy().deriveValueFrom(getPolicy()).isEmpty() || getPolicy().isCyclePolicy());
	}

	private boolean isValidPhoneNumber() {
		AceSimpleRuleEngine.DEFAULT.applyFirst(createUserSettingsPhoneValidationRuleFactory().create(), getUserSettingSectionItemBy(0, 1).getText().get().trim());
		return getModel().isValidPhoneNumber().get();
	}

	private void logStartEditUserEvent() {
		logEvent(new AceMobilePagedActionEvent(START_EDIT_USER_PROFILE, "Edit User"));
		createPersonProfileEditMonitor().recordValues();
	}

	public void navigateUserByDestination() {
		getUserFlow().getDestination().acceptVisitor(new AceBaseContactGeicoTypeVisitor<Void, Void>() {
			@Override
			public Void visitAnyView(Void input) {
				return NOTHING;
			}

			@Override
			public Void visitChat(Void input) {
				startActivity(AceContactGeicoActivity.class);
				return NOTHING;
			}

			@Override
			public Void visitEmail(Void input) {
				startActivity(AceSendAnEmailActivity.class);
				return NOTHING;
			}

			@Override
			public Void visitUnknown(Void input) {
				startActivity(AceDashboardActivity.class);
				return NOTHING;
			}
		});
	}

	@VisibleForTesting
	protected void navigateUserToLoginPage(@NonNull AceEvent<String, String> event) {
		getApplicationSession().setFinishSessionFromCancelledPolicyAlert(isFinishSessionFromCancelledPolicyAlert(event.getId()));
		resetPolicyDetailPageNavigation();
		resetTelematicsFlowFields();
		createRapidSessionFinisher().execute();
		getSessionController().beLoggedOut(); // Ensures the user is logged out for the Keep Me Logged In users
		getApplicationSession().setRunState(AceRunState.RUNNING); // Ensures the user will see the login page
		getApplicationSession().getLoginFlow().setPostLoginError(event.getSubject());
		startActivity(AceLoginActivity.class);
	}

	@VisibleForTesting
	protected AceExecutable createRapidSessionFinisher(){
		return new AceRapidSessionFinisher(getRegistry());
	}

	@Override
	public void openDialog(@NonNull @DialogTag String dialogTag) {
		switch (dialogTag) {
			case DialogTag.CALL_TO_MAKE_CHANGES:
				getStateEmitter().emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE);
				break;
			case DialogTag.ENROLL_WITH_DEVICE_UNLOCK:
				getStateEmitter().emitEnrollWithDeviceUnlockDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE);
				break;
			case DialogTag.KEEP_ME_LOGGED_IN:
				getStateEmitter().emitKeepMeLoggedInDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE);
				break;
		}
	}

	//TODO::remove deprecated AceHasOptionState logic https://geico.visualstudio.com/IBU%20Mobile/_workitems/edit/3418577
	public void recordMetricsForCompletingEditUserSettings() {
		new AceBasicPersonProfileEditMonitor(getSessionController()).acceptVisitor(new AceBaseHasOptionStateVisitor<Void, Void>() {
			@Override
			protected Void visitAnyType(Void input) {
				trackAction(ANALYTICS_USER_PROFILE_CREATED, USER_PROFILE_CREATED);
				logEvent(new AceUserProfileDetailEvent(FINISH_NEW_USER_PROFILE));
				return NOTHING;
			}

			@Override
			public Void visitYes(Void input) {
				trackAction(ANALYTICS_USER_PROFILE_EDIT, USER_PROFILE_EDIT);
				logEvent(new AceUserProfileDetailEvent(FINISH_EDIT_USER_PROFILE, getUserFlow().getPreEditValues().getMonitorState().toString()));
				return NOTHING;
			}
		});
		getUserFlow().resetPreEditValues();
	}

	@VisibleForTesting
	protected void recordMetricsForFinishSwitchUser() {
		trackAction(ANALYTICS_USER_PROFILE_SWITCH, USER_PROFILE_SWITCH);
		logEvent(new AceUserProfileDetailEvent(FINISH_SWITCH_USER_PROFILE));
	}

	public void registerListeners() {
		if (ViewTag.USER_SETTINGS_FRAGMENT.equals(getModel().viewTag))
			registerListener(createListenerForReturnToLoginPage());
	}

	private void resetPolicyDetailPageNavigation() {
		getApplicationSession().setPolicyDetailPageNavigationAction("");
		getApplicationSession().setSelectedDriverClientId("");
		getApplicationSession().setSelectedVehicleId("");
	}

	private void resetTelematicsFlowFields() {
		getApplicationSession().getTelematicsFlow().setDriverInformationState(AceInformationState.UNREQUESTED);
		getApplicationSession().getTelematicsFlow().setDriver(new AceTelematicsDriver());
	}

	private void synchronizeUserProfile() {
		considerUpdatingPhoneNumberAndWorkAddress();
		updateUserProfileFrom(getUserFlow().getPerson());
	}

	@VisibleForTesting
	protected void updateUserProfileFrom(AceUserProfilePerson personProfile) {
		new AceBasicUserProfileSynchronizer(getRegistry()).updateUserProfileFrom(personProfile);
		trackAction(ANALYTICS_USER_PROFILE_SET, getUserFlow().getUserProfileMetricsData());
	}
}
