package com.geico.mobile.android.ace.geicoapppresentation.action;

import android.content.Context;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.AceUsersActivity;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_USER_SET_UP;

/**
 * Action that will show the page collecting updates to the current user's profile.
 *
 * @author Richard Peirson, GEICO
 */
public class AceEditProfileAction extends AceAction {

    public AceEditProfileAction(@NonNull Context context) {
        super(context, R.string.editProfile);
    }

    @Override
    public void executeWith(@NonNull AceBaseActivity helper) {
        logEvent(helper, "M5_SETTINGS_EDIT_PROFILE_SELECTED");
        trackAction(helper.getSettingsViewModel(), ANALYTICS_M5_SETTINGS_MENU_EDIT_PROFILE, "M5SettingsMenu:EditProfile");
        startActivity(helper, AceUsersActivity.class, ACTION_USER_SET_UP);
    }
}