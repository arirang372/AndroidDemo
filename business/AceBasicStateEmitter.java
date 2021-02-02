package com.geico.mobile.android.ace.geicoappbusiness.emittedstate;

import android.app.Activity;

import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceActionBarCustomizationState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceAutoScrollState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceBasicOnDemandEmittedState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceDialogVisibilityState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceErrorState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceNavigationState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AcePermissionState;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * A class used to provoke common UI behavioral changes, such as dialog visibility,
 * from code implemented in the repository architectural layer
 *
 * @author Kal Tadesse.
 */
public class AceBasicStateEmitter implements AceStateEmitter {

	private final AceBasicOnDemandEmittedState actionBarCustomizationState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState autoScrollState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState callNumberState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState callToMakeChangesState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState changeDeviceNotificationSettingsDialogState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState claimsUnderConstructionState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState destinationNotFoundState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState enrollWithDeviceUnlockState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState executeWithActivityState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState googlePlayServicesAvailabilityErrorState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState navigationState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState networkState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState paymentFailureState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState permissionState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState restoreEmailDraftState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState sendEmailUnavailableState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState serviceErrorState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState sessionExpiredState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState unableToDetermineYourLocationState = new AceBasicOnDemandEmittedState();
	private final AceBasicOnDemandEmittedState waitState = new AceBasicOnDemandEmittedState();

	@CallSuper
	@Override
	public void addObserver(@NonNull AceEmittedStateObserver observer, @NonNull LifecycleOwner viewLifecycleOwner) {
		autoScrollState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		actionBarCustomizationState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		callToMakeChangesState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		callNumberState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		changeDeviceNotificationSettingsDialogState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		claimsUnderConstructionState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		destinationNotFoundState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		enrollWithDeviceUnlockState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		executeWithActivityState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		navigationState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		networkState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		paymentFailureState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		permissionState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		restoreEmailDraftState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		sendEmailUnavailableState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		serviceErrorState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		sessionExpiredState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		unableToDetermineYourLocationState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
		waitState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
	}

	@Override
	public void emitActionBarCustomization(@ActionBarCustomization int customizationType) {
		actionBarCustomizationState.startObservingWithValue(new AceActionBarCustomizationState(customizationType));
	}

	@Override
	public void emitEnrollWithDeviceUnlockDialogVisibility(@Visibility int visibility) {
		enrollWithDeviceUnlockState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.ENROLL_WITH_DEVICE_UNLOCK, visibility));
	}

	@Override
	public void emitAutoscrollTo(@IdRes int layoutId) {
		autoScrollState.startObservingWithValue(new AceAutoScrollState(layoutId));
	}

	@Override
	public void emitCallNumberDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		callNumberState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.CALL_NUMBER, visibility, displayText));
	}

	@Override
	public void emitCallToMakeChangesDialogVisibility(@Visibility int visibility) {
		callToMakeChangesState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.CALL_TO_MAKE_CHANGES, visibility));
	}

	@Override
	public void emitChangeDeviceNotificationSettingsDialogVisibility(@Visibility int visibility) {
		changeDeviceNotificationSettingsDialogState.startObservingWithValue(
				new AceDialogVisibilityState(DialogTag.CHANGE_DEVICE_NOTIFICATION_SETTINGS, visibility));
	}

	@Override
	public void emitClaimsUnderConstructionDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		claimsUnderConstructionState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.CLAIMS_UNDER_CONSTRUCTION_ERROR, visibility, displayText));
	}

	@Override
	public void emitDestinationNotFoundVisibility(@Visibility int visibility) {
		destinationNotFoundState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.DESTINATION_NOT_FOUND, visibility));
	}

	@Override
	public void emitError(@NonNull String error) {
		serviceErrorState.startObservingWithValue(new AceErrorState(error));
	}

	@Override
	public void emitExecuteWithActivityState(@NonNull AceExecutableWith<Activity> action) {
		executeWithActivityState.startObservingWithValue(new AceExecuteWithActivityState(action));
	}

	@Override
	public void emitGooglePlayServicesAvailabilityErrorDialogVisibility(@Visibility int visibility) {
		googlePlayServicesAvailabilityErrorState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.GOOGLE_API_AVAILABILITY_ERROR, visibility));
	}

	@Override
	public void emitNavigation(@NavigateAction String navigateAction) {
		navigationState.startObservingWithValue(new AceNavigationState(navigateAction));
	}

	@Override
	public void emitNetworkDialogVisibility(@Visibility int visibility) {
		networkState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.NETWORK_UNAVAILABLE, visibility));
	}

	@Override
	public void emitPaymentFailureDialogVisibility(@Visibility int visibility) {
		paymentFailureState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.PAYMENT_FAILURE, visibility));
	}

	@Override
	public void emitPermissionState(@NonNull AcePermissionState state) {
		permissionState.startObservingWithValue(state);
	}

	@Override
	public void emitRestoreEmailDraftDialogVisibility(@Visibility int visibility) {
		restoreEmailDraftState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.RESTORE_EMAIL_DRAFT, visibility));
	}

	@Override
	public void emitSendEmailUnavailableDialogVisibility(@Visibility int visibility) {
		sendEmailUnavailableState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.SEND_EMAIL_UNAVAILABLE, visibility));
	}

	@Override
	public void emitServiceErrorDialogVisibility(@Visibility int visibility) {
		serviceErrorState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.GENERIC_SERVICE_ERROR, visibility));
	}

	@Override
	public void emitServiceErrorWithAlertsDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		serviceErrorState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.SERVICE_ERROR_WITH_ALERTS, visibility, displayText));
	}

	@Override
	public void emitSessionExpiredDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		sessionExpiredState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.SESSION_EXPIRED, visibility, displayText));
	}

	@Override
	public void emitUnableToDetermineYourLocationDialogVisibility(@Visibility int visibility) {
		unableToDetermineYourLocationState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.UNABLE_TO_DETERMINE_YOUR_LOCATION, visibility));
	}

	@Override
	public void emitWaitDialogVisibility(@Visibility int visibility) {
		waitState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.WAIT, visibility));
	}

	@Override
	public AceBasicOnDemandEmittedState getCallToMakeChanges() {
		return callToMakeChangesState;
	}

	@NonNull
	public AceBasicOnDemandEmittedState getNavigationState() {
		return navigationState;
	}

	@NonNull
	public AceBasicOnDemandEmittedState getServiceErrorState() {
		return serviceErrorState;
	}

	/**
	 * Removes the observers.
	 * @param viewLifecycleOwner the lifecycle owner of the view.
	 */
	@CallSuper
	@Override
	public void removeObservers(@NonNull LifecycleOwner viewLifecycleOwner) {
		actionBarCustomizationState.removeObservers(viewLifecycleOwner);
		changeDeviceNotificationSettingsDialogState.removeObservers(viewLifecycleOwner);
		claimsUnderConstructionState.removeObservers(viewLifecycleOwner);
		enrollWithDeviceUnlockState.removeObservers(viewLifecycleOwner);
		executeWithActivityState.removeObservers(viewLifecycleOwner);
		navigationState.removeObservers(viewLifecycleOwner);
		networkState.removeObservers(viewLifecycleOwner);
		permissionState.removeObservers(viewLifecycleOwner);
		restoreEmailDraftState.removeObservers(viewLifecycleOwner);
		sendEmailUnavailableState.removeObservers(viewLifecycleOwner);
		serviceErrorState.removeObservers(viewLifecycleOwner);
		sessionExpiredState.removeObservers(viewLifecycleOwner);
		waitState.removeObservers(viewLifecycleOwner);
	}
}