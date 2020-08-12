package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model;

import android.content.Context;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.action.AceLoginSettingsAction;
import com.geico.mobile.android.ace.geicoapppresentation.action.AceNotificationPreferencesAction;

/**
 * Settings Category holding the collection of preference actions
 * appropriate before a policy is currently downloaded.
 *
 * @author John Sung, GEICO
 */
public class AcePreferencesCategory extends AceSettingsCategory {

	public AcePreferencesCategory(@NonNull Context context) {
		super(context.getString(R.string.settings)
				, context.getDrawable(R.drawable.settings_settings_icon)
				, actions(new AceNotificationPreferencesAction(context)));
	}
}
