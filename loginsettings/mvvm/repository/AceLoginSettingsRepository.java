package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceBaseInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.features.AceBasicFeatureMode;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings.AcePrepareToUpdateUserLoginInformationReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceLoginFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserLoginSettingsFlow;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceScreenUnlockEnableDisableToggleEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTouchIdEnableToggleSelection;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings.AcePrepareToUpdateUserLoginInformationUseCase;
import com.geico.mobile.android.ace.geicoappmodel.enums.userconnectiontechnique.AceUserConnectionTechnique;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceCanShowScreenUnlockSettings;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsPopulator;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceUserLoginSettingsFlowModification;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service.AcePrepareToUpdateUserLoginInformationRequestFactory;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;

import static com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants.FINGERPRINT;
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
        implements AcePrepareToUpdateUserLoginInformationUseCase {
    public AceLoginSettingsRepository(@NonNull Application application) {
        super(application);
    }

    public void initializeModel() {
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
    }

    @NonNull
    @Override
    protected AceModelLiveData<AceLoginSettingsModel> createModelLiveData() {
        return new AceModelLiveData<>(new AceLoginSettingsModel());
    }

    private MitPrepareToUpdateUserLoginInformationRequest createPrepareToUpdateUserLoginInformationRequest() {
        return new AcePrepareToUpdateUserLoginInformationRequestFactory().create();
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

    private void initializeLoginSettingsModel() {
        new AceLoginSettingsPopulator(this::getString).populate(getRegistry(), getModel());
    }

    public void handleLoginSettingsRowClickAction(int position) {
        switch (position) {
            case EDIT_USER_ID:
                //TODO::will implement it on
                break;
            case EDIT_PASSWORD:
                //TODO::will implement it on
            case EDIT_SECURITY_QUESTION:
                //TODO::will implement it on
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

    private AceUserLoginSettingsFlow getLoginSettingsFlow() {
        return getPolicySession().getLoginSettingsFlow();
    }

    private AceLoginFlow getLoginFlow() {
        return getApplicationSession().getLoginFlow();
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
}
