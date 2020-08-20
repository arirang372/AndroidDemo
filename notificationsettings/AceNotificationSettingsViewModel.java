package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceLifecycleObserverFactory;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceNotificationSettingsUseCase;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AceNotificationSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.repository.AceNotificationSettingsRepository;

import androidx.annotation.NonNull;

import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.PUSH_ALERT_CANCEL;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.PUSH_ALERT_SETTINGS;

/**
 * This is the ViewModel class for the Notification Settings module.
 *
 * @author Mahmudul Hasan, GEICO.
 */
public class AceNotificationSettingsViewModel extends AceBaseViewModel<AceNotificationSettingsModel, AceNotificationSettingsRepository>
		implements AceNotificationSettingsUseCase, AceLifecycleObserverFactory<AceNotificationSettingsLifecycleObserver> {

	public AceNotificationSettingsViewModel(@NonNull Application application) {
		super(application);
	}

	@Override
	public void considerEnrollingInPush() {
		getRepository().considerEnrollingInPush();
	}

	@NonNull
	@Override
	public AceNotificationSettingsLifecycleObserver createLifecycleObserver() {
		return new AceNotificationSettingsLifecycleObserver();
	}

	@NonNull
	@Override
	protected AceNotificationSettingsRepository createRepository(@NonNull Application application) {
		return new AceNotificationSettingsRepository(application);
	}

	@Override
	public void discardChanges() {
		getRepository().discardChanges();
	}

	@Override
	public void onPushAlertCancel() {
		trackAction(ANALYTICS_PUSH_ALERT_CANCEL, PUSH_ALERT_CANCEL);
		getModel().isEnrolledInPushNotifications.set(false);
	}

	public void onWeatherNotificationEnrollCheckboxClicked() {
		getRepository().considerEnrollingInWeatherAlertsNotification();
	}

	public void onDriveEasyPushNotificationEnrollCheckboxClicked(){
		getRepository().enrollDriveEasyPushNotification();
	}

	@Override
	public void openDeviceNotificationSettings() {
		trackAction(ANALYTICS_PUSH_ALERT_SETTINGS, PUSH_ALERT_SETTINGS);
		getModel().isEnrolledInPushNotifications.set(false);
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse("package:" + getApplication().getPackageName()));
		startActivity(getApplication(), intent);
	}

	@Override
	public void prepareToUpdateTextAlerts() {
		getRepository().prepareToUpdateTextAlerts();
	}

	@Override
	public void toggleEmailProductsNotificationsEnrollment() {
		getRepository().toggleEmailProductsNotificationsEnrollment();
	}

	@Override
	public void toggleNewsletterEnrollment() {
		getRepository().toggleNewsletterEnrollment();
	}

	@Override
	public void togglePolicyServicesNotificationsEnrollment() {
		getRepository().togglePolicyServicesNotificationsEnrollment();
	}

	@Override
	public void toggleSpecialOffersNotificationsEnrollment() {
		getRepository().toggleSpecialOffersNotificationsEnrollment();
	}

	@Override
	public void validateUserInput() {
		getRepository().validateUserInput();
	}
}
