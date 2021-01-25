package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitFieldValidationConstants.VALID_USERNAME_EXPRESSION;

public class AceLoginSettingsUserIdValidationRuleFactory implements AceFactory<List<AceRule<String>>> {
    private static final Pattern VALID_USERNAME_PATTERN = Pattern.compile(VALID_USERNAME_EXPRESSION);
    private final AceLoginSettingsModel loginSettingsModel;
    private final AceConverter<Integer, String> resouceConverter;
    private final String policyNumber;

    public AceLoginSettingsUserIdValidationRuleFactory(@NonNull AceLoginSettingsModel loginSettingsModel, @NonNull AceConverter<Integer, String> resourceConverter, @NonNull String policyNumber) {
        this.loginSettingsModel = loginSettingsModel;
        this.resouceConverter = resourceConverter;
        this.policyNumber = policyNumber;
        clearError();
    }

    void clearError() {
        loginSettingsModel.setErrorMessages("");
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createBlankUserIdRule(), createUserIdMinimumLengthRule(), createInvalidUserIdCharactersRule(), createSameAsPolicyNumberRule(), createValidUserIdRule());
    }

    private AceRule<String> createBlankUserIdRule() {
        return new AceBaseRule<String>() {
            @Override
            public boolean isApplicable(String context) {
                return TextUtils.isEmpty(context);
            }

            @Override
            public void applyTo(String context) {
                loginSettingsModel.setErrorMessages(resouceConverter.convert(R.string.userIdBlankError));
                loginSettingsModel.getSelectedSectionItem().setIsValidField(false);
            }
        };
    }

    private AceRule<String> createUserIdMinimumLengthRule() {
        return new AceBaseRule<String>() {
            @Override
            public boolean isApplicable(String context) {
                return context.length() < 6 || context.length()> 100;
            }

            @Override
            public void applyTo(String context) {
                loginSettingsModel.setErrorMessages(resouceConverter.convert(R.string.userIdEntryError));
                loginSettingsModel.getSelectedSectionItem().setIsValidField(false);
            }
        };
    }

    private AceRule<String> createInvalidUserIdCharactersRule() {
        return new AceBaseRule<String>() {
            @Override
            public boolean isApplicable(String context) {
                return !VALID_USERNAME_PATTERN.matcher(context).matches();
            }

            @Override
            public void applyTo(String context) {
                loginSettingsModel.setErrorMessages(resouceConverter.convert(R.string.userIdEntryError));
                loginSettingsModel.getSelectedSectionItem().setIsValidField(false);
            }
        };
    }

    private AceRule<String> createSameAsPolicyNumberRule() {
        return new AceBaseRule<String>() {
            @Override
            public boolean isApplicable(String context) {
                return policyNumber.equals(context);
            }

            @Override
            public void applyTo(String context) {
                loginSettingsModel.setErrorMessages(resouceConverter.convert(R.string.userIdEntryError));
                loginSettingsModel.getSelectedSectionItem().setIsValidField(false);
            }
        };
    }

    private AceRule<String> createValidUserIdRule() {
        return new AceOtherwiseRule<String>() {

            @Override
            public void applyTo(@NonNull String context) {
                loginSettingsModel.getSelectedSectionItem().setIsValidField(true);
                clearError();
            }
        };
    }
}
