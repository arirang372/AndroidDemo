package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;

/**
 * The callbacks for the user profile components
 *
 * @author John Sung, Geico
 */
public interface AceUsersCallback {

    void onEditButtonClicked();

    void onUsersListItemRowClicked(@NonNull AceUserProfilePerson profilePerson);
}
