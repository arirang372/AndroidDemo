package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.helpers;

import android.telephony.PhoneNumberUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsSectionItem;

import java.util.Arrays;
import java.util.List;

/**
 * A class that creates the rule for validating the user mobile phone number
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsPhoneValidationRuleFactory implements AceFactory<List<AceRule<String>>> {
    private final AceUserSettingsSectionItem mobilePhoneSectionItem;
    private final AceConverter<Integer, String> resourceConverter;
    private final AceUsersModel usersModel;

    public AceUserSettingsPhoneValidationRuleFactory(@NonNull AceUsersModel usersModel, @NonNull AceConverter<Integer, String> resourceConverter) {
        this.usersModel = usersModel;
        this.resourceConverter = resourceConverter;
        mobilePhoneSectionItem = usersModel.getUserSettingsListItems().isEmpty() ? null : usersModel.getUserSettingsListItems().get(0).getUserSettingsSectionItems().get(1);
        flushErrorMessage();
    }

    private String getUnformattedPhoneNumber(String phoneNumber) {
        return phoneNumber != null ? PhoneNumberUtils.stripSeparators(phoneNumber) : "";
    }

    private AceRule<String> createPhoneNumberLengthRule() {
        return new AceBaseRule<String>() {

            @Override
            public void applyTo(String context) {
                usersModel.setIsValidPhoneNumber(false);
                setIsValidPhoneNumber(mobilePhoneSectionItem, false);
                usersModel.setErrorMessages(resourceConverter.convert(R.string.invalidPhoneNumber));
            }

            @Override
            public boolean isApplicable(String context) {
                String unformattedPhoneNumber = getUnformattedPhoneNumber(context);
                return unformattedPhoneNumber.length() > 0 && unformattedPhoneNumber.length() < 10;
            }
        };
    }

    void setIsValidPhoneNumber(AceUserSettingsSectionItem mobilePhoneSectionItem, boolean isValidPhoneNumber) {
        if (mobilePhoneSectionItem != null) {
            mobilePhoneSectionItem.setIsValidPhoneNumber(isValidPhoneNumber);
        }
    }

    private AceRule<String> createInvalidStartNumberRule() {
        return new AceBaseRule<String>() {

            @Override
            public void applyTo(String context) {
                usersModel.setIsValidPhoneNumber(false);
                setIsValidPhoneNumber(mobilePhoneSectionItem, false);
                usersModel.setErrorMessages(resourceConverter.convert(R.string.invalidPhoneNumber));
            }

            @Override
            public boolean isApplicable(String context) {
                String unformattedPhoneNumber = getUnformattedPhoneNumber(context);
                return unformattedPhoneNumber.startsWith("0") || unformattedPhoneNumber.startsWith("1");
            }
        };
    }

    private AceRule<String> createValidPhoneNumberRule() {
        return new AceOtherwiseRule<String>() {

            @Override
            public void applyTo(String context) {
                usersModel.setIsValidPhoneNumber(true);
                setIsValidPhoneNumber(mobilePhoneSectionItem, true);
                flushErrorMessage();
            }
        };
    }

    void flushErrorMessage() {
        usersModel.setErrorMessages("");
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createPhoneNumberLengthRule(), createInvalidStartNumberRule(), createValidPhoneNumberRule());
    }
}
