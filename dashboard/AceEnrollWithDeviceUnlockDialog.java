package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view;

import android.content.DialogInterface;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceTwoButtonDialog;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;

/**
 * The two-button dialog prompting the user to enroll in automatic login to the mobile app.
 *
 * @author Gopal Palanisamy
 */
public class AceEnrollWithDeviceUnlockDialog extends AceTwoButtonDialog<AceBaseViewModel> {

    @Override
    protected String getMessage() {
        return getViewModel().getMessageForDeviceUnlockDialog();
    }

    @Override
    protected int getNegativeButtonText() {
        return getViewModel().userWasEnrolledForDeviceUnlock() ? R.string.no : R.string.notNow;
    }

    @Override
    protected int getPositiveButtonText() {
        return R.string.yes;
    }

    @Override
    protected String getTitle() {
        return getString(getViewModel().userWasEnrolledForDeviceUnlock() ? R.string.loginUpdate : R.string.enrollForScreenUnlockTitle);
    }

    @Override
    protected void onNegativeButtonClicked(DialogInterface dialogInterface, int buttonClicked) {
        super.onNegativeButtonClicked(dialogInterface, buttonClicked);
        getViewModel().onActivateDeviceUnlockDiscarded();
    }

    @Override
    protected void onPositiveButtonClicked(DialogInterface dialogInterface, int buttonClicked) {
        super.onPositiveButtonClicked(dialogInterface, buttonClicked);
        getViewModel().onActivateDeviceUnlockSelected();
    }
}