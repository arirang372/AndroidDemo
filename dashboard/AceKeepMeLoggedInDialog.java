package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view;

import android.content.DialogInterface;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.viewmodel.AceDashboardViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceTwoButtonDialog;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;

/**
 * The two-button dialog prompting the user to enroll in automatic login to the mobile app.
 *
 * @author Gopal Palanisamy
 */
public class AceKeepMeLoggedInDialog extends AceTwoButtonDialog<AceBaseViewModel> {

	@Override
	protected String getMessage() {
		return getString(R.string.keepMeLoggedInDisclaimer);
	}

	@Override
	protected int getNegativeButtonText() {
		return R.string.notNow;
	}

	@Override
	protected int getPositiveButtonText() {
		return R.string.iAccept;
	}

	@Override
	protected String getTitle() {
		return getString(R.string.stayLoggedInQuestion);
	}

	@Override
	protected void onNegativeButtonClicked(DialogInterface dialogInterface, int buttonClicked) {
		super.onNegativeButtonClicked(dialogInterface, buttonClicked);
		getViewModel().onKeepMeLoggedInDiscarded();
	}

	@Override
	protected void onPositiveButtonClicked(DialogInterface dialogInterface, int buttonClicked) {
		super.onPositiveButtonClicked(dialogInterface, buttonClicked);
		getViewModel().onKeepMeLoggedInSelected();
	}
}