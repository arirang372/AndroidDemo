package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushDao;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceSubjectPreference.SubjectPreferenceName;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappmodel.AceEmailPreferences;
import com.geico.mobile.android.ace.geicoappmodel.AceModelFactory;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.helpers.AceDriveEasyModeDeterminer;

/**
 * Initializes the model backing the notification settings view.
 *
 * @author Austin Morgan & Mahmudul Hasan
 */
public class AceNotificationSettingsModelFactory implements AceModelFactory<AceNotificationSettingsModel, AceRegistry> {

    private final AcePushDao pushDao;
    private final boolean shouldDisplayWeatherCheckbox;

    public AceNotificationSettingsModelFactory(@NonNull AcePushDao pushDao, boolean shouldDisplayWeatherCheckbox) {
        this.pushDao = pushDao;
        this.shouldDisplayWeatherCheckbox = shouldDisplayWeatherCheckbox;
    }

    @NonNull
    @Override
    public AceNotificationSettingsModel create(@NonNull AceRegistry registry) {
        AcePolicySession policySession = registry.getSessionController().getPolicySession();
        AceNotificationSettingsModel model = new AceNotificationSettingsModel();
        boolean isDriveEasyMode = new AceDriveEasyModeDeterminer().transform(registry);
        AceEmailPreferences emailPreferences = policySession.getPolicy().getContact().getEmailPreferences();
        model.setExistingEmailPreferences(new AceEmailPreferences(emailPreferences));
        model.emailAddress.set(policySession.getPolicy().getContact().getEmailAddress());
        model.isEnrolledInNewsletterEmails.set(emailPreferences.getNewsletter().isEnrolled());
        model.isEnrolledInPolicyServiceEmails.set(emailPreferences.getService().isEnrolled());
        model.isEnrolledInProductEmails.set(emailPreferences.getProduct().isEnrolled());
        model.setIsEnrolledInPolicyPushNotifications(isEnrolledInPushNotificationPreference(registry, SubjectPreferenceName.POLICY, isDriveEasyMode));
        model.isEnrolledInSpecialOffersEmails.set(emailPreferences.getContests().isEnrolled());
        model.setIsEnrolledInWeatherPushNotifications(isEnrolledInPushNotificationPreference(registry, SubjectPreferenceName.WEATHER, isDriveEasyMode));
        model.setIsEnrolledInTelematicsPushNotifications(isEnrolledInPushNotificationPreference(registry, SubjectPreferenceName.TELEMATICS, isDriveEasyMode));
        model.setShouldShowTelematicsPushNotificationOptions(registry.getSessionController().getApplicationSession().getTelematicsFlow()
                .getDriver().getDriverDetails().isRegistered());
        model.setShouldShowWeatherPushNotificationOptions(shouldShowWeatherNotificationCheckbox(policySession));
        new AceInitializeNotificationSettingsModelFromTextPreferences(model).
                executeWith(policySession.getTextPreferences());
        model.setShouldShowOnlyDriveEasyAlertOption(isDriveEasyMode);
        return model;
    }

    private String getStoredPolicyNumber(AceRegistry registry) {
        return new AceTelematicsSharedPreferencesDao(registry).getTelematicsCredentials().getPolicyNumber();
    }

    private boolean isEnrolledInPushNotificationPreference(AceRegistry registry, @SubjectPreferenceName String pushPreferenceName, boolean isDriveEasyMode) {
        return pushDao.retrieveEnrollmentForPolicy(isDriveEasyMode ? getStoredPolicyNumber(registry) :
                registry.getSessionController().getPolicySession().getPolicyNumber()).isPreferenceEnrolled(pushPreferenceName);
    }

    private boolean shouldShowWeatherNotificationCheckbox(AcePolicySession policySession) {
        return policySession.getWeatherFlow().shouldShowPickedForYouWeatherCard() || shouldDisplayWeatherCheckbox;
    }
}