package com.geico.mobile.android.ace.geicoappbusiness.emittedstate;

import android.app.Activity;

import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceBasicOnDemandEmittedState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AcePermissionState;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LifecycleOwner;

/**
 * An api for different basic state value emissions that are common to different
 * parts of the app. For example, network state, service error state, wait state.
 *
 * @author Kal Tadesse
 */
public interface AceStateEmitter {

	/**
	 * Add's this observer to each of the emitted state change streams held by this emitter.
	 * Implementations should ensure that each emitted state change is accessible only when created.
	 * @see <a href="https://proandroiddev.com/5-common-mistakes-when-using-architecture-components-403e9899f4cb">Possible Solutions</a>
	 *
	 * @param observer to be added to all streams of emitted states
	 * @param viewLifecycleOwner the lifecycle owner of the view.
	 */
	void addObserver(@NonNull AceEmittedStateObserver observer, @NonNull LifecycleOwner viewLifecycleOwner);

	default void emitEnrollWithDeviceUnlockDialogVisibility(@Visibility int visibility){
		// do nothing by default but implementations may override
	}

	default void emitActionBarCustomization(@ActionBarCustomization int customizationType) {
		// do nothing by default but implementations may override
	}

	default void emitAutoscrollTo(@IdRes int layoutId) {
		// do nothing by default but implementations may override
	}

	default void emitCallNumberDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		// do nothing by default but implementations may override
	}

	default void emitCallToMakeChangesDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitChangeDeviceNotificationSettingsDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitClaimsUnderConstructionDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		// do nothing by default but implementations may override
	}

	default void emitDestinationNotFoundVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitError(@NonNull String error) {
		// do nothing by default but implementations may override
	}

	default void emitExecuteWithActivityState(@NonNull AceExecutableWith<Activity> action) {
		// do nothing by default but implementations may override
	}

	default void emitGooglePlayServicesAvailabilityErrorDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitNavigation(@NavigateAction String navigateAction) {
		// do nothing by default but implementations may override
	}

	default void emitNetworkDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitPaymentFailureDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitPermissionState(@NonNull AcePermissionState permissionState) {
		//do nothing by default but implementations may override
	}

	default void emitPostDatedPaymentDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitRestoreEmailDraftDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitSendEmailUnavailableDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitServiceErrorDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitServiceErrorWithAlertsDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		// do nothing by default but implementations may override
	}

	default void emitSessionExpiredDialogVisibility(@Visibility int visibility, @NonNull String displayText) {
		// do nothing by default but implementations may override
	}

	default void emitUnableToDetermineYourLocationDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	default void emitWaitDialogVisibility(@Visibility int visibility) {
		// do nothing by default but implementations may override
	}

	@VisibleForTesting
	AceBasicOnDemandEmittedState getCallToMakeChanges();

	@NonNull
	AceBasicOnDemandEmittedState getNavigationState();

	@NonNull
	AceBasicOnDemandEmittedState getServiceErrorState();

	/**
	 * Removes the observers.
	 * @param viewLifecycleOwner the lifecycle owner of the view.
	 */
	void removeObservers(@NonNull LifecycleOwner viewLifecycleOwner);
}