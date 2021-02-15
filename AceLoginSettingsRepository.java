package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.repository;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.databinding.Observable;
import androidx.databinding.Observable.OnPropertyChangedCallback;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceBaseInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.features.AceBasicFeatureMode;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.coreframework.webservices.contexts.AceServiceContext;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings.AceChangePasswordReaction;
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
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.AceCurrentPasswordEntryErrorActivity;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.AceLoginSettingThankYouActivity;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceCanShowScreenUnlockSettings;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPasswordHintValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPasswordSectionPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPasswordStrengthMeterRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPasswordValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsSecurityAnswerValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsSecurityQuestionSectionPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsUserIdValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceReEnteredPasswordValidationRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceUserLoginSettingsFlowModification;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AceChangePasswordRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AcePrepareToUpdateUserLoginInformationRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AceUpdateSecurityQuestionAnswerRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AceUpdateUsernameRequestFactory;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitChangePasswordRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitChangePasswordResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;

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
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.RECOVERY_RESET_PWORD_TERMS_ID;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_PASSWORD;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_SECURITY_QUESTION;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_USER_ID;

/**
 * Repository responsible for handling business logic for the login settings component.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsRepository extends AceTierRepository<AceLoginSettingsModel> implements AceLoginSettingsUseCase {

    private final OnPropertyChangedCallback propertyChangedCallback = new OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(@NonNull Observable sender, int propertyId) {
            displayPasswordStrengthMeter(getSectionDetailItemWith(EDIT_PASSWORD, 0, 2));
        }
    };
    private int numberOfIncorrectPasswordEntries;

    public AceLoginSettingsRepository(@NonNull Application application) {
        super(application);
    }

    private void considerClearingAnswer(int securityQuestionPosition, MitCodeDescriptionPair selectedQuestion) {
        if (selectedQuestion.getCode().equals(getTemporaryQuestionCode(securityQuestionPosition)))
            return;
        getModel().getSelectedSectionItem().getSectionDetailListItems().get(securityQuestionPosition).getSectionDetailItems().get(1).setText("");
    }

    private void considerDisplayingTooManyInvalidPasswordEntriesPage() {
        if (numberOfIncorrectPasswordEntries < 3) {
            numberOfIncorrectPasswordEntries++;
            return;
        }
        startActivity(AceCurrentPasswordEntryErrorActivity.class);
    }

    private MitChangePasswordRequest createChangePasswordRequest() {
        MitChangePasswordRequest request = new AceChangePasswordRequestFactory().create();
        request.setCurrentPassword(getLoginSettingsFlow().getPassword());
        request.setUpdatedPassword(getLoginSettingsFlow().getUpdatedPassword().isEmpty() ?
                getLoginSettingsFlow().getPassword() : getLoginSettingsFlow().getUpdatedPassword());
        request.setUpdatedPasswordHint(getLoginSettingsFlow().getPasswordHint());
        request.setUserName(getLoginSettingsFlow().getUserId());
        return request;
    }

    @NonNull
    @Override
    protected AceModelLiveData<AceLoginSettingsModel> createModelLiveData() {
        return new AceModelLiveData<>(new AceLoginSettingsModel());
    }

    private MitPrepareToUpdateUserLoginInformationRequest createPrepareToUpdateUserLoginInformationRequest() {
        return new AcePrepareToUpdateUserLoginInformationRequestFactory().create();
    }

    private MitUpdateSecurityQuestionAnswerRequest createUpdateSecurityQuestionAnswerRequest() {
        MitUpdateSecurityQuestionAnswerRequest request = new AceUpdateSecurityQuestionAnswerRequestFactory().create();
        request.setUserName(getLoginSettingsFlow().getUserId());
        request.getSecurityQuestionAnswers().add(getLoginSettingsFlow().getAnswerOne());
        request.getSecurityQuestionAnswers().add(getLoginSettingsFlow().getAnswerTwo());
        return request;
    }

    private MitUpdateUsernameRequest createUpdateUsernameRequest() {
        MitUpdateUsernameRequest request = new AceUpdateUsernameRequestFactory().create();
        request.setUserName(getLoginSettingsFlow().getUserId());
        return request;
    }

    private void displayPasswordStrengthMeter(AceLoginSettingsSectionDetailItem passwordFooterSection) {
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsPasswordStrengthMeterRuleFactory(passwordFooterSection, this::getString,
                        getPolicy().getContact().getEmailAddress(), getPolicy().getNumber(), getLoginSettingsFlow().getUserId()).create(),
                getSectionDetailItemWith(EDIT_PASSWORD, 0, 1).getText().get());
        validatePasswordField(getSectionDetailItemWith(EDIT_PASSWORD, 0, 1), passwordFooterSection, R.string.newPasswordRequired);
    }

    private AceLoginSettingsSectionItem findLoginSettingsSectionItemWith(int position) {
        AceLoginSettingsListItem listItem = AceBasicEnumerator.DEFAULT.firstMatch(getModel().getLoginSettingsListItems(), element -> element.getLoginSettingsSectionItems().size() == 3, new AceLoginSettingsListItem());
        return AceBasicEnumerator.DEFAULT.firstMatch(listItem.getLoginSettingsSectionItems(), element -> element.getPosition().get() == position, new AceLoginSettingsSectionItem());
    }

    private AceLoginFlow getLoginFlow() {
        return getApplicationSession().getLoginFlow();
    }

    private AceUserLoginSettingsFlow getLoginSettingsFlow() {
        return getPolicySession().getLoginSettingsFlow();
    }

    private AceLoginSettingsSectionDetailItem getSectionDetailItemWith(@EditLoginSettingsType int loginSettingsType, int sectionDetailListItemPosition, int sectionIndex) {
        return findLoginSettingsSectionItemWith(loginSettingsType).getSectionDetailListItems().get(sectionDetailListItemPosition).getSectionDetailItems().get(sectionIndex);
    }

    private String getTemporaryQuestionCode(int securityQuestionPosition) {
        return securityQuestionPosition == 0 ? getLoginSettingsFlow().getTemporaryQuestionOneCode() : getLoginSettingsFlow().getTemporaryQuestionTwoCode();
    }

    public void handleFormButtonClickedAction() {
        switch (getModel().viewTag) {
            case ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT:
                handleUserIdAction();
                break;
            case ViewTag.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT:
                handleSecurityQuestionAction();
                break;
            case ViewTag.LOGIN_SETTINGS_PASSWORD_FRAGMENT:
                handlePasswordAction();
                break;
        }
    }

    public void handleLoginSettingsRowClickAction(int position) {
        switch (position) {
            case EDIT_USER_ID:
                emitNavigation(AceConstantState.NavigateAction.LOGIN_SETTINGS_USER_ID_FRAGMENT);
                break;
            case EDIT_PASSWORD:
                emitNavigation(AceConstantState.NavigateAction.LOGIN_SETTINGS_PASSWORD_FRAGMENT);
                break;
            case EDIT_SECURITY_QUESTION:
                emitNavigation(AceConstantState.NavigateAction.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT);
                break;
        }
    }

    private void handlePasswordAction() {
        if (!isValidPasswordAndHint()) return;
        getLoginSettingsFlow().setPassword(getSectionDetailItemWith(EDIT_PASSWORD, 0, 0).getText().get());
        getLoginSettingsFlow().setUpdatedPassword(getSectionDetailItemWith(EDIT_PASSWORD, 0, 1).getText().get());
        getLoginSettingsFlow().setPasswordHint(getSectionDetailItemWith(EDIT_PASSWORD, 1, 1).getText().get());
        callService(createChangePasswordRequest(), new AceChangePasswordReaction(this));
    }

    public void handlePasswordFieldFocusChangedAction(boolean hasFocus) {
        AceLoginSettingsSectionDetailItem passwordFooterSection = getSectionDetailItemWith(EDIT_PASSWORD, 0, 2);
        if (hasFocus) {
            passwordFooterSection.setShouldShowPasswordStringthMeter(true);
            displayPasswordStrengthMeter(passwordFooterSection);
            return;
        }
        passwordFooterSection.setShouldShowPasswordStringthMeter(false);
    }

    private void handleSecurityQuestionAction() {
        if (!isValidSecurityQuestionAndAnswers()) return;
        getLoginSettingsFlow().setTemporaryQuestionOneCode(getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 0, 0).getTemporaryQuestionOneCode());
        getLoginSettingsFlow().setTemporaryQuestionTwoCode(getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 1, 0).getTemporaryQuestionTwoCode());
        getLoginSettingsFlow().getAnswerOne().setAnswer(getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 0, 1).getText().get().trim());
        getLoginSettingsFlow().getAnswerTwo().setAnswer(getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 1, 1).getText().get().trim());
        callService(createUpdateSecurityQuestionAnswerRequest(), new AceUpdateSecurityQuestionAnswerReaction(this));
    }

    public void handleSecurityQuestionSelectedAction(int securityQuestionPosition, @NonNull MitCodeDescriptionPair selectedQuestion) {
        considerClearingAnswer(securityQuestionPosition, selectedQuestion);
        if (securityQuestionPosition == 0) {
            getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 0, 0).setTemporaryQuestionOneCode(selectedQuestion.getCode());
            getLoginSettingsFlow().getAnswerOne().setQuestionCode(selectedQuestion.getCode());
            return;
        }
        getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 1, 0).setTemporaryQuestionTwoCode(selectedQuestion.getCode());
        getLoginSettingsFlow().getAnswerTwo().setQuestionCode(selectedQuestion.getCode());
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

    private void handleUserIdAction() {
        if (!isValidUserId()) return;
        getLoginSettingsFlow().setUserId(getModel().getSelectedSectionItem().getText().get().trim());
        markAsWaiting();
        callService(createUpdateUsernameRequest(), new AceUpdateUserNameReaction(this));
    }

    private void initializeLoginSettingsModel() {
        new AceLoginSettingsPopulator(this::getString).populate(getRegistry(), getModel());
    }

    public void initializeModel(@NonNull String viewTag) {
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
                break;
            case ViewTag.LOGIN_SETTINGS_PASSWORD_FRAGMENT:
                AceLoginSettingsSectionItem passwordSection = findLoginSettingsSectionItemWith(EDIT_PASSWORD);
                new AceLoginSettingsPasswordSectionPopulator(this::getString).populate(getLoginSettingsFlow(), passwordSection);
                getModel().setSelectedSectionItem(passwordSection);
                getSectionDetailItemWith(EDIT_PASSWORD, 0, 1).getText().addOnPropertyChangedCallback(propertyChangedCallback);
                break;
        }
    }

    public void handleTermsOfUseLinkClickedAction() {
        logEvent(RECOVERY_RESET_PWORD_TERMS_ID);
        openFullSite(MitWebLinkNames.TERMS_OF_USE);
    }

    private boolean isValidPasswordAndHint() {
        AceLoginSettingsSectionDetailItem currentPasswordSection = getSectionDetailItemWith(EDIT_PASSWORD, 0, 0);
        AceLoginSettingsSectionDetailItem newPasswordSection = getSectionDetailItemWith(EDIT_PASSWORD, 0, 1);
        AceLoginSettingsSectionDetailItem passwordFooterSection = getSectionDetailItemWith(EDIT_PASSWORD, 0, 2);
        AceLoginSettingsSectionDetailItem reenterPasswordSection = getSectionDetailItemWith(EDIT_PASSWORD, 1, 0);
        AceLoginSettingsSectionDetailItem passwordHintSection = getSectionDetailItemWith(EDIT_PASSWORD, 1, 1);
        validatePasswordField(currentPasswordSection, passwordFooterSection, R.string.currentPasswordRequiredText);
        validatePasswordField(newPasswordSection, passwordFooterSection, R.string.newPasswordRequired);
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceReEnteredPasswordValidationRuleFactory(reenterPasswordSection, newPasswordSection, this::getString).create(),
                reenterPasswordSection.getText().get());
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsPasswordHintValidationRuleFactory(passwordHintSection, currentPasswordSection, this::getString).create(),
                passwordHintSection.getText().get());
        getModel().setErrorMessages(getModel().buildErrorMessage(currentPasswordSection.getErrorMessage().get(), newPasswordSection.getErrorMessage().get(), reenterPasswordSection.getErrorMessage().get(),
                passwordHintSection.getErrorMessage().get()));
        return TextUtils.isEmpty(getModel().getErrorMessages().get());
    }

    private boolean isValidSecurityQuestionAndAnswers() {
        AceLoginSettingsSectionDetailItem securityQuestionOne = getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 0, 0);
        AceLoginSettingsSectionDetailItem securityAnswerOne = getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 0, 1);
        AceLoginSettingsSectionDetailItem securityQuestionTwo = getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 1, 0);
        AceLoginSettingsSectionDetailItem securityAnswerTwo = getSectionDetailItemWith(EDIT_SECURITY_QUESTION, 1, 1);
        validateSecurityQuestion(securityQuestionOne);
        validateSecurityAnswer(securityAnswerOne);
        validateSecurityQuestion(securityQuestionTwo);
        validateSecurityAnswer(securityAnswerTwo);
        getModel().setErrorMessages(getModel().buildErrorMessage(securityQuestionOne.getErrorMessage().get(), securityAnswerOne.getErrorMessage().get(), securityQuestionTwo.getErrorMessage().get(),
                securityAnswerTwo.getErrorMessage().get()));
        return TextUtils.isEmpty(getModel().getErrorMessages().get());
    }

    private boolean isValidUserId() {
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsUserIdValidationRuleFactory(getModel(), this::getString, getPolicy().getNumber()).create(), getModel().getSelectedSectionItem().getText().get().trim());
        return getModel().getSelectedSectionItem().isValidField().get();
    }

    @Override
    public void onChangePasswordAnyFailure(@NonNull MitResponse response) {
        onUpdatePartialSuccess(response);
    }

    @Override
    public void onChangePasswordPartialSuccess(@NonNull MitChangePasswordResponse response) {
        onUpdatePartialSuccess(response);
        considerDisplayingTooManyInvalidPasswordEntriesPage();
    }

    @Override
    public void onChangePasswordSuccess(@NonNull MitChangePasswordResponse response) {
        getLoginSettingsFlow().setUpdatedSettingDescription(getString(R.string.passwordText));
        startActivity(AceLoginSettingThankYouActivity.class);
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

    private void onUpdatePartialSuccess(MitResponse response) {
        markAsNotWaiting();
        showServiceErrorWithAlertsDialog(response);
    }

    @Override
    public void onUpdateSecurityQuestionAnswerAnyFailure(@NonNull MitResponse response) {
        onUpdatePartialSuccess(response);
    }

    @Override
    public void onUpdateSecurityQuestionAnswerPartialSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response) {
        onUpdatePartialSuccess(response);
    }

    @Override
    public void onUpdateSecurityQuestionAnswerSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response) {
        markAsNotWaiting();
        getLoginSettingsFlow().setUpdatedSettingDescription(getString(R.string.securityQuestions));
        startActivity(AceLoginSettingThankYouActivity.class);
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

    private void resetNumberOfIncorrectPasswordEntries() {
        this.numberOfIncorrectPasswordEntries = 0;
    }

    public void resetSelectedSectionItemValue() {
        switch (getModel().viewTag) {
            case ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT:
                findLoginSettingsSectionItemWith(EDIT_USER_ID).setText(getLoginSettingsFlow().getUserId());
                break;
            case ViewTag.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT:
                new AceLoginSettingsSecurityQuestionSectionPopulator(this::getString).populate(getLoginSettingsFlow(), findLoginSettingsSectionItemWith(EDIT_SECURITY_QUESTION));
                break;
            case ViewTag.LOGIN_SETTINGS_PASSWORD_FRAGMENT:
                getLoginSettingsFlow().setPassword("");
                getLoginSettingsFlow().setUpdatedPassword("");
                getSectionDetailItemWith(EDIT_PASSWORD, 0, 1).getText().removeOnPropertyChangedCallback(propertyChangedCallback);
                resetNumberOfIncorrectPasswordEntries();
                new AceLoginSettingsPasswordSectionPopulator(this::getString).populate(getLoginSettingsFlow(), findLoginSettingsSectionItemWith(EDIT_PASSWORD));
                break;
        }
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

    private void validatePasswordField(AceLoginSettingsSectionDetailItem passwordSection, AceLoginSettingsSectionDetailItem passwordFooterSection, @StringRes int blankPasswordErrorMessageId) {
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsPasswordValidationRuleFactory(passwordSection, passwordFooterSection, this::getString,
                getPolicy().getContact().getEmailAddress(), getPolicy().getNumber(), getLoginSettingsFlow().getUserId(),
                blankPasswordErrorMessageId).create(), passwordSection.getText().get().trim());
    }

    private void validateSecurityAnswer(AceLoginSettingsSectionDetailItem securityAnswerSectionDetailItem) {
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsSecurityAnswerValidationRuleFactory(securityAnswerSectionDetailItem, this::getString)
                .create(), securityAnswerSectionDetailItem.getText().get());
    }

    private void validateSecurityQuestion(AceLoginSettingsSectionDetailItem securityQuestionSectionDetailItem) {
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory(securityQuestionSectionDetailItem, this::getString)
                .create(), securityQuestionSectionDetailItem.getSelectedSecurityQuestionPosition().get());
    }
}
