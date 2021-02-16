package com.geico.mobile.android.ace.geicoapppresentation.action;

import android.content.Context;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.AceUsersActivity;

/**
 * Action that will show the page allowing the user to switch to a different user profile.
 *
 * @author Richard Peirson, GEICO
 */
public class AceSwitchProfileAction extends AceAction {

	public AceSwitchProfileAction(@NonNull Context context, @NonNull String currentUserName) {
		super(context, R.string.switchUserFormat, currentUserName);
	}

	@Override
	public void executeWith(@NonNull AceBaseActivity helper) {
		startActivity(helper, AceUsersActivity.class);
	}
}