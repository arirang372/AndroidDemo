package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserInputValidityState;
import com.geico.mobile.android.ace.geicoappmodel.AceEmailPreferences;
import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;

/**
 * Model backing the Notification Settings view.
 *
 * @author Mahmudul Hasan & Austin Morgan
 */
public class AceNotificationSettingsModel extends AceViewBackingModel {

    public final ObservableField<String> emailAddress = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> emailAddressValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    public final ObservableField<String> errorText = new ObservableField<>("");
    public final ObservableBoolean isEnrolledInNewsletterEmails = new ObservableBoolean(false);
    public final ObservableBoolean isEnrolledInPolicyServiceEmails = new ObservableBoolean(false);
    public final ObservableBoolean isEnrolledInProductEmails = new ObservableBoolean(false);
    public final ObservableBoolean isEnrolledInPushNotifications = new ObservableBoolean(false);
    public final ObservableBoolean isEnrolledInSpecialOffersEmails = new ObservableBoolean(false);
    public final ObservableBoolean isEnrolledInWeatherAlertsNotification = new ObservableBoolean(false);
    public final ObservableBoolean shouldShowWeatherNotificationCheckbox = new ObservableBoolean(false);
    public final ObservableField<String> phoneNumberOne = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> phoneNumberOneValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    public final ObservableField<String> phoneNumberThree = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> phoneNumberThreeValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    public final ObservableField<String> phoneNumberTwo = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> phoneNumberTwoValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    private final ObservableBoolean enrolledInDriveEasyPushNotification = new ObservableBoolean(false);
    private final ObservableBoolean showDriveEasyPushNotificationOptions = new ObservableBoolean(false);
    private AceEmailPreferences existingEmailPreferences = new AceEmailPreferences();
    private String existingFirstNumber = "";
    private String existingSecondNumber = "";
    private String existingThirdNumber = "";
    private AceUserInputValidityState phoneValidity = AceUserInputValidityState.UNKNOWN;
    @AcePushNotificationType
    private String pushNotificationType = AcePushNotificationType.NONE;

    @NonNull
    public String getPushNotificationType() {
        return pushNotificationType;
    }

    public void setPushNotificationType(@NonNull @AcePushNotificationType String pushNotificationType) {
        this.pushNotificationType = pushNotificationType;
    }

    public void setIsEnrolledInDriveEasyPushNotification(boolean enrolledInDriveEasyPushNotification) {
        this.enrolledInDriveEasyPushNotification.set(enrolledInDriveEasyPushNotification);
    }

    public ObservableBoolean isEnrolledInDriveEasyPushNotification() {
        return enrolledInDriveEasyPushNotification;
    }

    public void setShouldShowDriveEasyPushNotificationOptions(boolean showDriveEasyAlertOptions) {
        this.showDriveEasyPushNotificationOptions.set(showDriveEasyAlertOptions);
    }

    @NonNull
    public ObservableBoolean shouldShowDriveEasyPushNotificationOptions() {
        return showDriveEasyPushNotificationOptions;
    }

    @NonNull
    public AceEmailPreferences getExistingEmailPreferences() {
        return existingEmailPreferences;
    }

    public void setExistingEmailPreferences(@NonNull AceEmailPreferences existingEmailPreferences) {
        this.existingEmailPreferences = existingEmailPreferences;
    }

    @NonNull
    public String getExistingFirstNumber() {
        return existingFirstNumber;
    }

    public void setExistingFirstNumber(@NonNull String existingFirstNumber) {
        this.existingFirstNumber = existingFirstNumber;
    }

    @NonNull
    public String getExistingSecondNumber() {
        return existingSecondNumber;
    }

    public void setExistingSecondNumber(@NonNull String existingSecondNumber) {
        this.existingSecondNumber = existingSecondNumber;
    }

    @NonNull
    public String getExistingThirdNumber() {
        return existingThirdNumber;
    }

    public void setExistingThirdNumber(@NonNull String existingThirdNumber) {
        this.existingThirdNumber = existingThirdNumber;
    }

    @NonNull
    public AceUserInputValidityState getPhoneValidity() {
        return phoneValidity;
    }

    public void setPhoneValidity(@NonNull AceUserInputValidityState phoneValidity) {
        this.phoneValidity = phoneValidity;
    }

    public boolean haveEmailPreferencesChanged() {
        return isEnrolledInPolicyServiceEmails.get() != existingEmailPreferences.getService().isEnrolled() ||
                isEnrolledInProductEmails.get() != existingEmailPreferences.getProduct().isEnrolled() ||
                isEnrolledInSpecialOffersEmails.get() != existingEmailPreferences.getContests().isEnrolled() ||
                isEnrolledInNewsletterEmails.get() != existingEmailPreferences.getNewsletter().isEnrolled();
    }
}