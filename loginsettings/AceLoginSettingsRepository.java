package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.repository;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceBaseInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.features.AceBasicFeatureMode;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.coreframework.webservices.contexts.AceServiceContext;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings.AcePrepareToUpdateUserLoginInformationReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings.AceUpdateSecurityQuestionAnswerReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings.AceUpdateUserNameReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceLoginFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserLoginSettingsFlow;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceScreenUnlockEnableDisableToggleEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTouchIdEnableToggleSelection;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings.AceLoginSettingsUseCase;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.enums.userconnectiontechnique.AceUserConnectionTechnique;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.AceLoginSettingThankYouActivity;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceCanShowScreenUnlockSettings;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsSecurityAnswerValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsSecurityQuestionSectionPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsUserIdValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceUserLoginSettingsFlowModification;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AcePrepareToUpdateUserLoginInformationRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AceUpdateSecurityQuestionAnswerRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AceUpdateUsernameRequestFactory;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameResponse;

import static com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants.FINGERPRINT;
import static com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants.PREFS_USER_ID_KEY;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_SCREEN_UNLOCK_TOGGLE_DISABLE;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_SCREEN_UNLOCK_TOGGLE_ENABLE;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_TOUCH_ID_DISABLED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_TOUCH_ID_ENABLED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_TOGGLE_DISABLE;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.SCREEN_UNLOCK_TOGGLE_ENABLE;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.TOUCH_ID_DISABLED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.TOUCH_ID_ENABLED;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_PASSWORD;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_SECURITY_QUESTION;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_USER_ID;

/**
 * Repository responsible for handling business logic for the login settings component.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsRepository extends AceTierRepository<AceLoginSettingsModel>
        implements AceLoginSettingsUseCase {

    public AceLoginSettingsRepository(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    protected AceModelLiveData<AceLoginSettingsModel> createModelLiveData() {
        return new AceModelLiveData<>(new AceLoginSettingsModel());
    }

    private MitPrepareToUpdateUserLoginInformationRequest createPrepareToUpdateUserLoginInformationRequest() {
        return new AcePrepareToUpdateUserLoginInformationRequestFactory().create();
    }

    private MitUpdateUsernameRequest createUpdateUsernameRequest() {
        MitUpdateUsernameRequest request = new AceUpdateUsernameRequestFactory().create();
        request.setUserName(getLoginSettingsFlow().getUserId());
        return request;
    }

    private MitUpdateSecurityQuestionAnswerRequest createUpdateSecurityQuestionAnswerRequest() {
        MitUpdateSecurityQuestionAnswerRequest request = new AceUpdateSecurityQuestionAnswerRequestFactory().create();
        request.setUserName(getLoginSettingsFlow().getUserId());
        request.getSecurityQuestionAnswers().add(getLoginSettingsFlow().getAnswerOne());
        request.getSecurityQuestionAnswers().add(getLoginSettingsFlow().getAnswerTwo());
        return request;
    }

    private AceLoginFlow getLoginFlow() {
        return getApplicationSession().getLoginFlow();
    }

    private AceUserLoginSettingsFlow getLoginSettingsFlow() {
        return getPolicySession().getLoginSettingsFlow();
    }

    public void handleLoginSettingsRowClickAction(int position) {
        switch (position) {
            case EDIT_USER_ID:
                emitNavigation(AceConstantState.NavigateAction.LOGIN_SETTINGS_USER_ID_FRAGMENT);
                break;
            case EDIT_PASSWORD:
                //TODO::will implement it on https://geico.visualstudio.com/IBU%20Mobile/_workitems/edit/3052855
                break;
            case EDIT_SECURITY_QUESTION:
                emitNavigation(AceConstantState.NavigateAction.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT);
                break;
        }
    }

    public void handleSwitchUnlockSwitchToggledAction(boolean isSwitchOn) {
        AceLoginSettingsSectionItem screenUnlockSettings = getModel().getLoginSettingsListItems().get(0).getLoginSettingsSectionItems().get(0);
        screenUnlockSettings.setScreenUnlockEnabled(isSwitchOn);
        screenUnlockSettings.setLabel(isSwitchOn ? getString(R.string.disableScreenUnlock) : getString(R.string.enableScreenUnlock));
        updateLoginSettingsPersistentData(isSwitchOn);
        getApplicationSession().setKeepMeLoggedInDialogHasBeenDisplayed(true);
        getLoginFlow().setUserConnectionTechnique(AceUserConnectionTechnique.LOGIN_EACH_TIME);
        recordMetrics(isSwitchOn, new AceCanShowScreenUnlockSettings().deriveValueFrom(getRegistry()));
    }

    private void initializeLoginSettingsModel() {
        new AceLoginSettingsPopulator(this::getString).populate(getRegistry(), getModel());
    }

    public void initializeModel(String viewTag) {
        switch (viewTag) {
            case ViewTag.LOGIN_SETTINGS_FRAGMENT:
                getLoginSettingsFlow().flushLoginSettingFlow();
                getLoginSettingsFlow().getPrepareRequestState()
                        .acceptVisitor(new AceBaseInformationStateVisitor<Void, Void>() {

                            @Override
                            protected Void visitAnyState(Void input) {
                                markAsWaiting();
                                callService(createPrepareToUpdateUserLoginInformationRequest(),
                                        new AcePrepareToUpdateUserLoginInformationReaction(AceLoginSettingsRepository.this));
                                return NOTHING;
                            }

                            @Override
                            public Void visitCurrent(Void input) {
                                initializeLoginSettingsModel();
                                return NOTHING;
                            }
                        });
                break;
            case ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT:
                getModel().setSelectedSectionItem(findLoginSettingsSectionItemWith(EDIT_USER_ID));
                isValidUserId();
                break;
            case ViewTag.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT:
                getModel().setSelectedSectionItem(findLoginSettingsSectionItemWith(EDIT_SECURITY_QUESTION));
                isValidSecurityQuestionAndAnswers();
        }
    }

    private AceLoginSettingsSectionItem findLoginSettingsSectionItemWith(int position) {
        AceLoginSettingsListItem listItem = AceBasicEnumerator.DEFAULT.firstMatch(getModel().getLoginSettingsListItems(), element -> element.getLoginSettingsSectionItems().size() == 3, new AceLoginSettingsListItem());
        return AceBasicEnumerator.DEFAULT.firstMatch(listItem.getLoginSettingsSectionItems(), element -> element.getPosition().get() == position, new AceLoginSettingsSectionItem());
    }

    private String getTemporaryQuestionCode(int securityQuestionPosition) {
        return securityQuestionPosition == 0 ? getLoginSettingsFlow().getTemporaryQuestionOneCode() : getLoginSettingsFlow().getTemporaryQuestionTwoCode();
    }

    public void handleSecurityQuestionSelectedAction(int securityQuestionPosition, @NonNull MitCodeDescriptionPair selectedQuestion) {
        considerClearingAnswer(securityQuestionPosition, selectedQuestion);
        if (securityQuestionPosition == 0) {
            getSecurityQuestionSectionDetailItem(0, 0).setTemporaryQuestionOneCode(selectedQuestion.getCode());
            getLoginSettingsFlow().getAnswerOne().setQuestionCode(selectedQuestion.getCode());
            return;
        }
        getSecurityQuestionSectionDetailItem(1, 0).setTemporaryQuestionTwoCode(selectedQuestion.getCode());
        getLoginSettingsFlow().getAnswerTwo().setQuestionCode(selectedQuestion.getCode());
    }

    private void considerClearingAnswer(int securityQuestionPosition, MitCodeDescriptionPair selectedQuestion) {
        if (selectedQuestion.getCode().equals(getTemporaryQuestionCode(securityQuestionPosition)))
            return;
        getModel().getSelectedSectionItem().getSectionDetailListItems().get(securityQuestionPosition).getSectionDetailItems().get(1).setText("");
    }

    public void handleFormButtonClickedAction() {
        switch (getModel().viewTag) {
            case ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT:
                handleUserIdAction();
                break;
            case ViewTag.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT:
                handleSecurityQuestionAction();
                break;
        }
    }

    public void resetSelectedSectionItemValue() {
        switch (getModel().viewTag) {
            case ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT:
                findLoginSettingsSectionItemWith(EDIT_USER_ID).setText(getLoginSettingsFlow().getUserId());
                break;
            case ViewTag.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT:
                new AceLoginSettingsSecurityQuestionSectionPopulator(this::getString).populate(getLoginSettingsFlow(), findLoginSettingsSectionItemWith(EDIT_SECURITY_QUESTION));
                break;
        }
    }

    private void handleUserIdAction() {
        if (!isValidUserId()) return;
        getLoginSettingsFlow().setUserId(getModel().getSelectedSectionItem().getText().get().trim());
        markAsWaiting();
        callService(createUpdateUsernameRequest(), new AceUpdateUserNameReaction(this));
    }

    private void handleSecurityQuestionAction() {
        if (!isValidSecurityQuestionAndAnswers()) return;
        getLoginSettingsFlow().setTemporaryQuestionOneCode(getSecurityQuestionSectionDetailItem(0, 0).getTemporaryQuestionOneCode());
        getLoginSettingsFlow().setTemporaryQuestionTwoCode(getSecurityQuestionSectionDetailItem(1, 0).getTemporaryQuestionTwoCode());
        getLoginSettingsFlow().getAnswerOne().setAnswer(getSecurityQuestionSectionDetailItem(0, 1).getText().get().trim());
        getLoginSettingsFlow().getAnswerTwo().setAnswer(getSecurityQuestionSectionDetailItem(1, 1).getText().get().trim());
        callService(createUpdateSecurityQuestionAnswerRequest(), new AceUpdateSecurityQuestionAnswerReaction(this));
    }

    private boolean isValidSecurityQuestionAndAnswers() {
        AceLoginSettingsSectionDetailItem securityQuestionOne = getSecurityQuestionSectionDetailItem(0, 0);
        AceLoginSettingsSectionDetailItem securityAnswerOne = getSecurityQuestionSectionDetailItem(0, 1);
        AceLoginSettingsSectionDetailItem securityQuestionTwo = getSecurityQuestionSectionDetailItem(1, 0);
        AceLoginSettingsSectionDetailItem securityAnswerTwo = getSecurityQuestionSectionDetailItem(1, 1);
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory(securityQuestionOne, this::getString)
                .create(), securityQuestionOne.getSelectedSecurityQuestionPosition().get());
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsSecurityAnswerValidationRuleFactory(securityAnswerOne, this::getString)
                .create(), securityAnswerOne.getText().get());
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory(securityQuestionTwo, this::getString)
                .create(), securityQuestionTwo.getSelectedSecurityQuestionPosition().get());
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsSecurityAnswerValidationRuleFactory(securityAnswerTwo, this::getString)
                .create(), securityAnswerTwo.getText().get());
        getModel().setErrorMessages(getModel().buildErrorMessage(securityQuestionOne.getErrorMessage().get(), securityAnswerOne.getErrorMessage().get(), securityQuestionTwo.getErrorMessage().get(),
                securityAnswerTwo.getErrorMessage().get()));
        return TextUtils.isEmpty(getModel().getErrorMessages().get());
    }

    private AceLoginSettingsSectionDetailItem getSecurityQuestionSectionDetailItem(int securityQuestionPosition, int sectionIndex) {
        return findLoginSettingsSectionItemWith(EDIT_SECURITY_QUESTION).getSectionDetailListItems().get(securityQuestionPosition).getSectionDetailItems().get(sectionIndex);
    }

    private boolean isValidUserId() {
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsUserIdValidationRuleFactory(getModel(), this::getString, getPolicy().getNumber()).create(), getModel().getSelectedSectionItem().getText().get().trim());
        return getModel().getSelectedSectionItem().isValidField().get();
    }

    @Override
    public <I extends MitRequest, O extends MitResponse> void onNotAuthorized(@NonNull AceServiceContext<I, O> serviceContext) {
        if (!requestCredentialsMatchCurrentPolicySession(serviceContext)) return;
        handleSessionExpired(serviceContext);
    }

    @Override
    public void onPrepareToUpdateUserLoginInformationAnyFailure(@NonNull MitResponse response) {
        markAsNotWaiting();
        getLoginSettingsFlow().setPrepareRequestState(AceInformationState.UNAVAILABLE);
    }

    @Override
    public void onPrepareToUpdateUserLoginInformationSuccess(@NonNull MitPrepareToUpdateUserLoginInformationResponse response) {
        markAsNotWaiting();
        new AceUserLoginSettingsFlowModification(response).modify(getLoginSettingsFlow());
        initializeLoginSettingsModel();
    }

    private void recordMetrics(boolean isSwitchOn, boolean isScreenUnlockAvailable) {
        if (isSwitchOn) {
            String actionValue = isScreenUnlockAvailable ? ANALYTICS_SCREEN_UNLOCK_TOGGLE_ENABLE : ANALYTICS_TOUCH_ID_ENABLED;
            String contextVariable = isScreenUnlockAvailable ? SCREEN_UNLOCK_TOGGLE_ENABLE : TOUCH_ID_ENABLED;
            trackAction(actionValue, contextVariable);
            logEvent(isScreenUnlockAvailable ? new AceScreenUnlockEnableDisableToggleEvent("Enabled") : new AceTouchIdEnableToggleSelection("Yes"));
            return;
        }
        String actionValue = isScreenUnlockAvailable ? ANALYTICS_SCREEN_UNLOCK_TOGGLE_DISABLE : ANALYTICS_TOUCH_ID_DISABLED;
        String contextVariable = isScreenUnlockAvailable ? SCREEN_UNLOCK_TOGGLE_DISABLE : TOUCH_ID_DISABLED;
        trackAction(actionValue, contextVariable);
        logEvent(isScreenUnlockAvailable ? new AceScreenUnlockEnableDisableToggleEvent("Disabled") : new AceTouchIdEnableToggleSelection("No"));
    }

    private void updateLoginSettingsPersistentData(boolean isSwitchOn) {
        AceLoginSettingsDao loginSettingsDao = new AceLoginSharedPreferencesDao(getRegistry());
        loginSettingsDao.storeTouchIdEnabled(isSwitchOn ? AceBasicFeatureMode.INITIAL.getCode() : AceBasicFeatureMode.DISABLED.getCode());
        loginSettingsDao.storeFingerprintEnrollmentOfferSeen(true);
        if (isSwitchOn) {
            updateValuesForEnabledFingerprint(loginSettingsDao);
            loginSettingsDao.enrollForLoginThroughDeviceUnlock();
            return;
        }
        getLoginFlow().setUserIdDisplayText(loginSettingsDao.getLastFingerprintUserEnteredId());
        loginSettingsDao.storeFingerprintUserId("");
        loginSettingsDao.storeFingerprintUserEnteredId("");
        loginSettingsDao.storeDiscardRefreshTokenBeforeNextLogin(true);
        loginSettingsDao.unenrollForLoginThroughDeviceUnlock();
        loginSettingsDao.setOfferSeenForEnrollWithDeviceUnlock();
    }

    private void updateValuesForEnabledFingerprint(AceLoginSettingsDao loginSettingsDao) {
        String userId = getLoginSettingsFlow().getUserId();
        boolean userIdHasChangedSinceLogin = !userId.equalsIgnoreCase(loginSettingsDao.retrieveUserId());
        loginSettingsDao.storeCredentials(userId, getLoginFlow().getMostRecentRefreshToken());
        loginSettingsDao.storeDiscardRefreshTokenBeforeNextLogin(false);
        loginSettingsDao.storeFingerprintUserId(userId);
        loginSettingsDao.storeReasonForRetainingRefreshToken(FINGERPRINT);
        loginSettingsDao.unenrollForLoginThroughDeviceUnlock();
        getLoginFlow().setUserConnectionTechnique(AceUserConnectionTechnique.KEEP_ME_LOGGED_IN);
        if (userIdHasChangedSinceLogin) {
            loginSettingsDao.storeFingerprintUserEnteredId(userId);
            return;
        }
        loginSettingsDao.storeFingerprintUserEnteredId(loginSettingsDao.getLastUserEnteredId());
        loginSettingsDao.storeUserEnteredId(userId);
    }

    @Override
    public void onUpdateUserNamePartialSuccess(@NonNull MitUpdateUsernameResponse response) {
        onUpdatePartialSuccess(response);
    }

    @Override
    public void onUpdateUserNameSuccess(@NonNull MitUpdateUsernameResponse response) {
        markAsNotWaiting();
        new AceLoginSharedPreferencesDao(getRegistry()).write(PREFS_USER_ID_KEY, getLoginSettingsFlow().getUserId());
        getLoginSettingsFlow().setUpdatedSettingDescription(getString(R.string.userIdText));
        startActivity(AceLoginSettingThankYouActivity.class);
    }

    @Override
    public void onUpdateSecurityQuestionAnswerSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response) {
        markAsNotWaiting();
        getLoginSettingsFlow().setUpdatedSettingDescription(getString(R.string.securityQuestions));
        startActivity(AceLoginSettingThankYouActivity.class);
    }

    @Override
    public void onUpdateSecurityQuestionAnswerPartialSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response) {
        onUpdatePartialSuccess(response);
    }

    private void onUpdatePartialSuccess(MitResponse response) {
        markAsNotWaiting();
        showServiceErrorWithAlertsDialog(response);
    }
}
