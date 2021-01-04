package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view;

import android.content.DialogInterface;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceTwoButtonDialog;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel.AceUsersViewModel;

/**
 * Dialog informing the user that they must call GEICO to make the requested changes to their policy.
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsCallToMakeChangesDialog extends AceTwoButtonDialog<AceUsersViewModel> {
    @Override
    protected String getMessage() {
        return getApplication().getString(R.string.callToMakeChangesMessage);
    }

    @Override
    protected int getNegativeButtonText() {
        return R.string.cancel;
    }

    @Override
    protected int getPositiveButtonText() {
        return R.string.call;
    }

    @Override
    protected String getTitle() {
        return getApplication().getString(R.string.callToMakeChanges);
    }

    @Override
    protected void onPositiveButtonClicked(DialogInterface dialogInterface, int buttonClicked) {
        super.onPositiveButtonClicked(dialogInterface, buttonClicked);
        getViewModel().displayDialer(getString(R.string.geicoGeneralPhoneNumber));
    }
}
