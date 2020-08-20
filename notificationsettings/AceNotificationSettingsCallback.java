package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.view;

/**
 * API defining user interactions with the notification settings page.
 *
 * @author Austin Morgan
 */
public interface AceNotificationSettingsCallback {

	void onEmailProductsCheckBoxClick();

	void onNewsletterCheckBoxClick();

	void onPolicyServicesCheckBoxClick();

	void onPushNotificationEnrollCheckboxClick();

	void onSpecialOffersCheckBoxClick();

	void onTermsAndConditionsClick();

	void onWeatherNotificationEnrollCheckboxClicked();

	void onDriveEasyPushNotificationEnrollCheckboxClicked();
}
