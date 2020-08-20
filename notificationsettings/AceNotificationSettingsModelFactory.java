package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushDao;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;
import com.geico.mobile.android.ace.geicoappmodel.AceEmailPreferences;
import com.geico.mobile.android.ace.geicoappmodel.AceModelFactory;

import static com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceMessagingConstants.DEVICE_ENROLLMENT_YES;

/**
 * Initializes the model backing the notification settings view.
 *
 * @author Austin Morgan & Mahmudul Hasan
 */
public class AceNotificationSettingsModelFactory implements AceModelFactory<AceNotificationSettingsModel, AceSessionController> {

    private final boolean isEnrolledInWeatherPushNotification;
    private final boolean isEnrolledInDriveEasyPushNotification;
    private final AcePushDao pushDao;
    private final boolean shouldDisplayWeatherCheckbox;

    public AceNotificationSettingsModelFactory(@NonNull AcePushDao pushDao, boolean isEnrolledInWeatherPushNotification,
                                               boolean shouldDisplayWeatherCheckbox, boolean isEnrolledInDriveEasyPushNotification) {
        this.pushDao = pushDao;
        this.isEnrolledInWeatherPushNotification = isEnrolledInWeatherPushNotification;
        this.isEnrolledInDriveEasyPushNotification = isEnrolledInDriveEasyPushNotification;
        this.shouldDisplayWeatherCheckbox = shouldDisplayWeatherCheckbox;
    }

    @NonNull
    @Override
    public AceNotificationSettingsModel create(@NonNull AceSessionController sessionController) {
        AcePolicySession policySession = sessionController.getPolicySession();
        AceNotificationSettingsModel model = new AceNotificationSettingsModel();
        AceEmailPreferences emailPreferences = policySession.getPolicy().getContact().getEmailPreferences();
        model.setExistingEmailPreferences(new AceEmailPreferences(emailPreferences));
        model.emailAddress.set(policySession.getPolicy().getContact().getEmailAddress());
        model.isEnrolledInNewsletterEmails.set(emailPreferences.getNewsletter().isEnrolled());
        model.isEnrolledInPolicyServiceEmails.set(emailPreferences.getService().isEnrolled());
        model.isEnrolledInProductEmails.set(emailPreferences.getProduct().isEnrolled());
        model.isEnrolledInPushNotifications.set(isEnrolledInPushNotifications(policySession.getPolicy().getNumber()));
        model.isEnrolledInSpecialOffersEmails.set(emailPreferences.getContests().isEnrolled());
        model.isEnrolledInWeatherAlertsNotification.set(isEnrolledInWeatherPushNotification);
        model.setIsEnrolledInDriveEasyPushNotification(isEnrolledInDriveEasyPushNotification);
        model.setShouldShowDriveEasyPushNotificationOptions(sessionController.getApplicationSession().getTelematicsFlow()
                .getDriver().getDriverDetails().isRegistered());
        model.shouldShowWeatherNotificationCheckbox.set(shouldShowWeatherNotificationCheckbox(policySession));
        new AceInitializeNotificationSettingsModelFromTextPreferences(model).
                executeWith(policySession.getTextPreferences());
        return model;
    }

    private boolean shouldShowWeatherNotificationCheckbox(AcePolicySession policySession){
        return policySession.getWeatherFlow().shouldShowPickedForYouWeatherCard() || shouldDisplayWeatherCheckbox;
    }

    private boolean isEnrolledInPushNotifications(String policyNumber) {
        return DEVICE_ENROLLMENT_YES.equals(pushDao.retrieveEnrollmentForPolicy(policyNumber).getEnrollment());
    }
}