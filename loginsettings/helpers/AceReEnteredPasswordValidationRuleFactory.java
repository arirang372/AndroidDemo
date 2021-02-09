package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;

import java.util.Arrays;
import java.util.List;

public class AceReEnteredPasswordValidationRuleFactory implements AceFactory<List<AceRule<String>>> {
    private final AceLoginSettingsSectionDetailItem reEnteredPasswordSection;
    private final AceLoginSettingsSectionDetailItem passwordSection;
    private final AceConverter<Integer, String> resourceConverter;

    public AceReEnteredPasswordValidationRuleFactory(@NonNull AceLoginSettingsSectionDetailItem reEnteredPasswordSection,
                                                     @NonNull AceLoginSettingsSectionDetailItem passwordSection,
                                                     @NonNull AceConverter<Integer, String> resourceConverter) {
        this.reEnteredPasswordSection = reEnteredPasswordSection;
        this.passwordSection = passwordSection;
        this.resourceConverter = resourceConverter;
        clearError();
    }

    void clearError() {
        reEnteredPasswordSection.setErrorMessage("");
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createReEnteredPasswordRule(R.string.pleaseConfirmYourPassword),
                createReEnteredPasswordNotSameAsNewPasswordRule(),
                createValidReEnteredPasswordRule());
    }

    private AceRule<String> createReEnteredPasswordRule(@StringRes final int errorMessageId) {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                reEnteredPasswordSection.setErrorMessage(resourceConverter.convert(errorMessageId));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return TextUtils.isEmpty(context);
            }
        };
    }

    private AceRule<String> createReEnteredPasswordNotSameAsNewPasswordRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                reEnteredPasswordSection.setErrorMessage(resourceConverter.convert(R.string.passwordReEnterNotSameError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !context.equals(passwordSection.getText().get());
            }
        };
    }

    private AceRule<String> createValidReEnteredPasswordRule() {
        return new AceOtherwiseRule<String>() {

            @Override
            public void applyTo(@NonNull String context) {
                clearError();
            }
        };
    }
}
