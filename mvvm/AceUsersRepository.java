package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository;

import android.Manifest;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.geicoappbusiness.derivations.AceSpecialtyVehiclesFromPolicy;
import com.geico.mobile.android.ace.geicoappbusiness.findgas.rules.AceFindGasFilterSettingsHolder;
import com.geico.mobile.android.ace.geicoappbusiness.login.AcePostLoginDestinationDeterminer;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappbusiness.telephony.AceDialerIntentLauncher;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor.AceViewExhibitorVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceMobilePagedActionEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceContactUsPhoneEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceMailingAddressEditActionType;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceMailingAddressEditEvent;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicPersonProfileEditMonitor;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicUserProfileSynchronizer;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileDetailEvent;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSessionFinisher;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceBaseOutOfGasTypeVisitor;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.findgas.AceFindGasProduct;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVisibilityState;
import com.geico.mobile.android.ace.geicoappmodel.visitors.AceBaseContactGeicoTypeVisitor;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.AceContactUsEventLogger;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.email.mvvm.view.AceSendAnEmailActivity;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceDashboardActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceRecommendations;
import com.geico.mobile.android.ace.geicoapppresentation.permission.AceLocationPermissionForWebLinkActivity;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers.AceUserProfileListItemClickRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.helpers.AceUserSettingsPhoneValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.helpers.AceUserSettingsPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsSectionItem;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_USERS;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_COA_START_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SET;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SWITCH;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.COA_START_SELECTED;
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

    private void considerResettingDriverDataInformationState() {
        if (getPolicy().getPolicyLocation().isDuckCreek()) {
            getApplicationSession().getTelematicsFlow().setDriverInformationState(AceInformationState.UNREQUESTED);
        }
    }

    @NonNull
    @Override
    protected AceModelLiveData<AceUsersModel> createModelLiveData() {
        return new AceModelLiveData<>(new AceUsersModelFactory(getPolicy().getNumber()).create(getUserFlow()));
    }

    @VisibleForTesting
    protected AceUserProfileListItemClickRuleFactory createUserProfileListItemClickRuleFactory() {
        return new AceUserProfileListItemClickRuleFactory(getRegistry(), this);
    }

    @VisibleForTesting
    protected AceUserFlow getUserFlow() {
        return getApplicationSession().getUserFlow();
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

    public void handleEditButtonClickAction() {
        logStartEditUserEvent();
        emitNavigation(AceConstantState.NavigateAction.USER_SETTINGS_FRAGMENT);
        logEvent(new AceUserProfileDetailEvent(FINISH_EDIT_USER_PROFILE));
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

    public void handleEditUserSettingsButtonAction() {
        logEvent(START_MAIL_ADDRESS_EDIT);
        logEvent(new AceMailingAddressEditEvent(AceMailingAddressEditActionType.PAGE));
        trackAction(ANALYTICS_COA_START_SELECTED, COA_START_SELECTED);
        new AceRecommendations(getRegistry()).rememberAddressChangeInProgress();
        boolean isSpecialtyVehicle = !(new AceSpecialtyVehiclesFromPolicy().deriveValueFrom(getPolicy()).isEmpty() || getPolicy().isCyclePolicy());
        if (isSpecialtyVehicle) {
            openDialog(AceConstantState.DialogTag.CALL_TO_MAKE_CHANGES);
            return;
        }
        performChangeOfAddress();
    }

    private void performChangeOfAddress() {
        if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            getRegistry().getPermissionCategoryManager().setAction(MitWebLinkNames.UPDATE_ADDRESS);
            startActivity(AceLocationPermissionForWebLinkActivity.class);
            return;
        }
        openFullSite(MitWebLinkNames.UPDATE_ADDRESS);
    }

    @Override
    public void openDialog(@NonNull @AceConstantState.DialogTag String dialogTag) {
        switch (dialogTag) {
            case AceConstantState.DialogTag.CALL_TO_MAKE_CHANGES:
                getStateEmitter().emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void displayDialer(@NonNull String phoneNumber) {
        new AceContactUsEventLogger(new AceContactUsPhoneEvent(), getPolicySession().getContactUsFlow(), "tel:" + phoneNumber, "", getString(com.geico.mobile.R.string.callToMakeChanges)).executeWith(this);
        new AceDialerIntentLauncher(getApplication()).executeWith(R.string.geicoGeneralPhoneNumber);
    }

    public void handleContinueButtonAction() {
        if (!isValidPhoneNumber()) return;
        finishUserSetUp();
        determinePostLoginDestination();
    }

    public void handleSkipButtonAction() {
        isValidPhoneNumber();
        finishUserSetUp();
        determinePostLoginDestination();
    }

    private void finishUserSetUp() {
        updatePhoneNumberAndWorkAddress();
        getUserFlow().setViewState(AceViewExhibitor.VIEW_SHOWED_TO_USER);
    }

    private void determinePostLoginDestination() {
        new AcePostLoginDestinationDeterminer().executeWith(getSessionController());
        getSessionController().startAction(getApplication(), getPolicySession().getPostLoginAction());
    }

    public void handleSelectedVehicleAction(@NonNull AceUserProfileVehicle selectedVehicle, int position) {
        getUserFlow().getPerson().getPersonalPolicyProfile(getPolicy().getNumber()).setPrimaryVehicle(selectedVehicle);
        if (position == 0) {
            AceUserSettingsSectionItem fuelTypeSectionItem = getModel().getUserSettingsListItems().get(1)
                    .getUserSettingsSectionItems().get(1);
            fuelTypeSectionItem.setSelectedFuelTypePosition(0);
            handleSelectedFuelTypeSpinnerItem(AceOutOfGasTypeEnum.REGULAR_UNLEADED);
            AceUserSettingsSectionItem vehicleColorSectionItem = getModel().getUserSettingsListItems().get(1)
                    .getUserSettingsSectionItems().get(2);
            vehicleColorSectionItem.setSelectedVehicleColorPosition(0);
            handleSelectedVehicleColorAction(AceVehicleColor.UNKNOWN_COLOR);
        }
        synchronizeUserProfile();
    }

    private boolean isValidPhoneNumber() {
        AceUserSettingsSectionItem mobilePhoneSectionItem = getModel().getUserSettingsListItems().get(0).getUserSettingsSectionItems().get(1);
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceUserSettingsPhoneValidationRuleFactory(getModel(), this::getString).create(),
                mobilePhoneSectionItem.getText().get().trim());
        return getModel().isValidPhoneNumber().get();
    }

    private void synchronizeUserProfile() {
        updatePhoneNumberAndWorkAddress();
        updateUserProfileFrom(getUserFlow().getPerson());
    }

    private void updatePhoneNumberAndWorkAddress() {
        if (isValidPhoneNumber()) {
            AceUserSettingsSectionItem mobilePhoneSectionItem = getModel().getUserSettingsListItems().get(0).getUserSettingsSectionItems().get(1);
            getUserFlow().getPerson().setMobilePhoneNumber(mobilePhoneSectionItem.getText().get());
        }
        getUserFlow().getPerson().setWorkAddress(getUserFlow().getTemporaryWorkAddress());
    }

    public void handleSelectedVehicleColorAction(@NonNull AceVehicleColor selectedVehicleColor) {
        getPrimaryVehicle().setColor(selectedVehicleColor);
    }

    private AceUserProfileVehicle getPrimaryVehicle() {
        return getUserFlow().getPerson().getPrimaryVehicle(getPolicy().getNumber());
    }

    public void handleListItemRowClickAction(@NonNull AceUserProfilePerson profilePerson) {
        AceSimpleRuleEngine.DEFAULT.applyFirst(createUserProfileListItemClickRuleFactory().create(), profilePerson);
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

    public void initializeModel() {
        new AceUserSettingsPopulator(this::getString).populate(getRegistry(), getModel());
    }

    public void handleSelectedUserOtherwiseRule(@NonNull AceUserProfilePerson person) {
        updateUserProfileFrom(person);
        recordMetricsForFinishSwitchUser();
        considerResettingDriverDataInformationState();
        startActivity(AceDashboardActivity.class);
    }

    private void logStartEditUserEvent() {
        logEvent(new AceMobilePagedActionEvent(START_EDIT_USER_PROFILE, "Edit User"));
        new AceBasicPersonProfileEditMonitor(getSessionController()).recordValues();
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
    protected void recordMetricsForFinishSwitchUser() {
        trackAction(ANALYTICS_USER_PROFILE_SWITCH, USER_PROFILE_SWITCH);
        logEvent(new AceUserProfileDetailEvent(FINISH_SWITCH_USER_PROFILE));
    }

    @VisibleForTesting
    protected void updateUserProfileFrom(AceUserProfilePerson personProfile) {
        new AceBasicUserProfileSynchronizer(getRegistry()).updateUserProfileFrom(personProfile);
        trackAction(ANALYTICS_USER_PROFILE_SET, getUserFlow().getUserProfileMetricsData());
    }
}
