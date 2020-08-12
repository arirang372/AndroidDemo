package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.view;

/**
 * Handles user interactions with the settings UI
 *
 * @author Kal Tadesse, Austin Morgan
 */
public interface AceSettingsCallback {

	default void onCloseSettingsDialogClick() {
		//do nothing by default
	}

	default void onConfigurationChanged() {
		//do nothing by default
	}

	default void onFeedbackClick() {
		//do nothing by default
	}

	default void onLogoutClick() {
		//do nothing by default
	}

	default void onLoginClick() {
		//do nothing by default
	}
}
