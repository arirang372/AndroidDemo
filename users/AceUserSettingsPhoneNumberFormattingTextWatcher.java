package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.view;

import android.text.Editable;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoapppresentation.policy.AcePhoneNumberFormattingTextWatcher;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem;

public class AceUserSettingsPhoneNumberFormattingTextWatcher extends AcePhoneNumberFormattingTextWatcher {
    private AceUserSettingsSectionItem sectionItem;

    public AceUserSettingsPhoneNumberFormattingTextWatcher(@NonNull EditText phoneNumberView) {
        super(phoneNumberView);
    }

    public void setUserSettingsSectionItem(@NonNull AceUserSettingsSectionItem sectionItem) {
        this.sectionItem = sectionItem;
    }

    @Override
    protected void setPhoneNumberText(String phoneNumber) {
        super.setPhoneNumberText(phoneNumber);
        if (sectionItem != null)
            sectionItem.setText(phoneNumber);
    }

    @Override
    public synchronized void afterTextChanged(Editable editable) {
        if (sectionItem != null)
            sectionItem.setText(editable.toString());
    }
}
