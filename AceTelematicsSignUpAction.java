package com.geico.mobile.android.ace.geicoapppresentation.action;

import android.content.Context;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;

/**
 * Action that will show the telematics sign up page
 *
 * @author John Sung, Geico
 */
public class AceTelematicsSignUpAction extends AceAction {
    public AceTelematicsSignUpAction(@NonNull Context context) {
        super(context, R.string.driveEasy);
    }

    @Override
    public void executeWith(@NonNull AceBaseActivity helper) {
        //TODO::implement this on ADO #3522943
    }
}
