package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsListItem;

import java.util.List;

/**
 * A model for the User profile details.
 *
 * @author John Sung, Geico
 */
public class AceUsersModel extends AceViewBackingModel {

    private final ObservableList<AceUserProfilePerson> profiles = new ObservableArrayList<>();
    private final ObservableList<AceUserSettingsListItem> userSettingsListItems = new ObservableArrayList<>();
    private final ObservableField<String> errorMessages = new ObservableField<>("");
    private final ObservableBoolean validPhoneNumber = new ObservableBoolean(false);

    public void setIsValidPhoneNumber(boolean validPhoneNumber) {
        this.validPhoneNumber.set(validPhoneNumber);
    }

    @NonNull
    public ObservableBoolean isValidPhoneNumber() {
        return validPhoneNumber;
    }

    @NonNull
    public ObservableList<AceUserSettingsListItem> getUserSettingsListItems() {
        return userSettingsListItems;
    }

    @NonNull
    public ObservableList<AceUserProfilePerson> getProfiles() {
        return profiles;
    }

    public void setProfiles(@NonNull List<AceUserProfilePerson> profiles) {
        this.profiles.clear();
        this.profiles.addAll(profiles);
    }

    @NonNull
    public ObservableField<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(@NonNull String errorMessages) {
        this.errorMessages.set(errorMessages);
    }
}
