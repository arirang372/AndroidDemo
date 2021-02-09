package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitFieldValidationConstants.VALID_PASSWORD_HINT_EXPRESSION;

public class AceLoginSettingsPasswordHintValidationRuleFactory implements AceFactory<List<AceRule<String>>> {
    private static final Pattern VALID_PASSWORD_HINT_PATTERN = Pattern.compile(VALID_PASSWORD_HINT_EXPRESSION);
    private final AceConverter<Integer, String> resouceConverter;
    private final AceLoginSettingsSectionDetailItem passwordHintSection;
    private final AceLoginSettingsSectionDetailItem currentPasswordSection;

    public AceLoginSettingsPasswordHintValidationRuleFactory(@NonNull AceLoginSettingsSectionDetailItem passwordHintSection,
                                                             @NonNull AceLoginSettingsSectionDetailItem currentPasswordSection,
                                                             @NonNull AceConverter<Integer, String> resourceConverter) {
        this.passwordHintSection = passwordHintSection;
        this.currentPasswordSection = currentPasswordSection;
        this.resouceConverter = resourceConverter;
        clearError();
    }

    void clearError() {
        passwordHintSection.setErrorMessage("");
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createBlankPasswordHintRule(), createHintIdenticalToPasswordRule(), createInvalidHintCharactersRule(),
                createValidHintRule());
    }

    private AceRule<String> createBlankPasswordHintRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordHintSection.setErrorMessage(resouceConverter.convert(R.string.passwordHintError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return TextUtils.isEmpty(context);
            }
        };
    }

    private AceRule<String> createHintIdenticalToPasswordRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordHintSection.setErrorMessage(resouceConverter.convert(R.string.passwordHintIdenticalToPasswordError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return context.equals(currentPasswordSection.getText().get());
            }
        };
    }

    private AceRule<String> createInvalidHintCharactersRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordHintSection.setErrorMessage(resouceConverter.convert(R.string.passwordHintInvalidCharacterError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !VALID_PASSWORD_HINT_PATTERN.matcher(context).matches();
            }
        };
    }

    private AceRule<String> createValidHintRule() {
        return new AceOtherwiseRule<String>() {

            @Override
            public void applyTo(@NonNull String context) {
                clearError();
            }
        };
    }
}
