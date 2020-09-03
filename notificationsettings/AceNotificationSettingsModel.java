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
    public final ObservableBoolean isEnrolledInSpecialOffersEmails = new ObservableBoolean(false);
    public final ObservableField<String> phoneNumberOne = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> phoneNumberOneValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    public final ObservableField<String> phoneNumberThree = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> phoneNumberThreeValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    public final ObservableField<String> phoneNumberTwo = new ObservableField<>("");
    public final ObservableField<AceUserInputValidityState> phoneNumberTwoValidityState = new ObservableField<>(AceUserInputValidityState.UNKNOWN);
    private final ObservableBoolean isEnrolledInPolicyPushNotifications = new ObservableBoolean(false);
    private final ObservableBoolean isEnrolledInTelematicsPushNotifications = new ObservableBoolean(false);
    private final ObservableBoolean isEnrolledInWeatherPushNotifications = new ObservableBoolean(false);
    private final ObservableBoolean showTelematicsPushNotificationOptions = new ObservableBoolean(false);
    private final ObservableBoolean showWeatherPushNotificationOptions = new ObservableBoolean(false);
    private final ObservableBoolean showOnlyDriveEasyAlertOption = new ObservableBoolean(false);
    private AceEmailPreferences existingEmailPreferences = new AceEmailPreferences();
    private String existingFirstNumber = "";
    private String existingSecondNumber = "";
    private String existingThirdNumber = "";
    private AceUserInputValidityState phoneValidity = AceUserInputValidityState.UNKNOWN;
    @AcePushNotificationType
    private String pushNotificationType = AcePushNotificationType.NONE;

    public void setShouldShowOnlyDriveEasyAlertOption(boolean showOnlyDriveEasyAlertOption) {
        this.showOnlyDriveEasyAlertOption.set(showOnlyDriveEasyAlertOption);
    }

    @NonNull
    public ObservableBoolean shouldShowOnlyDriveEasyAlertOption() {
        return showOnlyDriveEasyAlertOption;
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

    @NonNull
    public String getPushNotificationType() {
        return pushNotificationType;
    }

    public void setPushNotificationType(@NonNull @AcePushNotificationType String pushNotificationType) {
        this.pushNotificationType = pushNotificationType;
    }

    public boolean haveEmailPreferencesChanged() {
        return isEnrolledInPolicyServiceEmails.get() != existingEmailPreferences.getService().isEnrolled() ||
                isEnrolledInProductEmails.get() != existingEmailPreferences.getProduct().isEnrolled() ||
                isEnrolledInSpecialOffersEmails.get() != existingEmailPreferences.getContests().isEnrolled() ||
                isEnrolledInNewsletterEmails.get() != existingEmailPreferences.getNewsletter().isEnrolled();
    }

    @NonNull
    public ObservableBoolean isEnrolledInPolicyPushNotifications() {
        return isEnrolledInPolicyPushNotifications;
    }

    @NonNull
    public ObservableBoolean isEnrolledInTelematicsPushNotifications() {
        return isEnrolledInTelematicsPushNotifications;
    }

    @NonNull
    public ObservableBoolean isEnrolledInWeatherPushNotifications() {
        return isEnrolledInWeatherPushNotifications;
    }

    public void setIsEnrolledInPolicyPushNotifications(boolean isEnrolledInPolicyPushNotifications) {
        this.isEnrolledInPolicyPushNotifications.set(isEnrolledInPolicyPushNotifications);
    }

    public void setIsEnrolledInTelematicsPushNotifications(boolean isEnrolledInTelematicsPushNotifications) {
        this.isEnrolledInTelematicsPushNotifications.set(isEnrolledInTelematicsPushNotifications);
    }

    public void setIsEnrolledInWeatherPushNotifications(boolean isEnrolledInWeatherPushNotifications) {
        this.isEnrolledInWeatherPushNotifications.set(isEnrolledInWeatherPushNotifications);
    }

    public void setShouldShowTelematicsPushNotificationOptions(boolean showTelematicsPushNotificationOptions) {
        this.showTelematicsPushNotificationOptions.set(showTelematicsPushNotificationOptions);
    }

    public void setShouldShowWeatherPushNotificationOptions(boolean showWeatherPushNotificationOptions) {
        this.showWeatherPushNotificationOptions.set(showWeatherPushNotificationOptions);
    }

    @NonNull
    public ObservableBoolean shouldShowTelematicsPushNotificationOptions() {
        return showTelematicsPushNotificationOptions;
    }

    @NonNull
    public ObservableBoolean shouldShowWeatherPushNotificationOptions() {
        return showWeatherPushNotificationOptions;
    }
}