package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;

/**
 * The callbacks for the login settings components
 *
 * @author John Sung, Geico
 */
public interface AceLoginSettingsCallback {

	default void onLoginSettingsRowClicked(int position) {
		//do nothing by default
	}

	default void onScreenUnlockSwitchToggled(boolean isSwitchOn) {
		//do nothing by default
	}

	default void onSecurityQuestionSelected(int securityQuestionPosition, @NonNull MitCodeDescriptionPair selectedQuestion) {
		//do nothing by default
	}

	default void onPasswordFieldFocusChanged(boolean hasFocus){
		//do nothing by default
	}
}
