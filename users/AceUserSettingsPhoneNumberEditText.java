package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.view;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoapppresentation.framework.widget.AcePhoneNumberFormattingEditText;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem;

public class AceUserSettingsPhoneNumberEditText extends AcePhoneNumberFormattingEditText {
    public AceUserSettingsPhoneNumberEditText(@NonNull Context context) {
        super(context);
    }

    public AceUserSettingsPhoneNumberEditText(@NonNull Context context, @NonNull AttributeSet attrs) {
        super(context, attrs);
    }

    public AceUserSettingsPhoneNumberEditText(@NonNull Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected TextWatcher createTextWatcher() {
        return new AceUserSettingsPhoneNumberFormattingTextWatcher(this);
    }

    void setUserSettingsSectionItem(@NonNull AceUserSettingsSectionItem sectionItem) {
        AceUserSettingsPhoneNumberFormattingTextWatcher textWatcher = (AceUserSettingsPhoneNumberFormattingTextWatcher) getTextWatcher();
        textWatcher.setUserSettingsSectionItem(sectionItem);
    }
}
