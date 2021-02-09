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
import com.geico.mobile.android.ace.coreframework.string.AceBasicStringSimilarityDetector;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitFieldValidationConstants.MUST_CONTAIN_DIGIT_EXPRESSION;
import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitFieldValidationConstants.MUST_CONTAIN_LETTER_EXPRESSION;
import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitFieldValidationConstants.VALID_PASSWORD_SPECIAL_CHARACTERS_EXPRESSION;

public class AceLoginSettingsPasswordValidationRuleFactory implements AceFactory<List<AceRule<String>>> {
    private static final Pattern MUST_CONTAIN_DIGIT_PATTERN = Pattern.compile(MUST_CONTAIN_DIGIT_EXPRESSION);
    private static final Pattern MUST_CONTAIN_LETTER_PATTERN = Pattern.compile(MUST_CONTAIN_LETTER_EXPRESSION);
    private static final Pattern VALID_SPECIAL_CHARACTER_PATTERN = Pattern.compile(VALID_PASSWORD_SPECIAL_CHARACTERS_EXPRESSION);
    private final AceLoginSettingsSectionDetailItem passwordSection;
    private final AceConverter<Integer, String> resourceConverter;
    private final String email;
    private final String policyNumber;
    private final String userId;
    private final int blankPasswordErrorMessageId;

    public AceLoginSettingsPasswordValidationRuleFactory(
            @NonNull AceLoginSettingsSectionDetailItem passwordSection,
            @NonNull AceConverter<Integer, String> resourceConverter,
            @NonNull String email, @NonNull String policyNumber, @NonNull String userId, @StringRes int blankPasswordErrorMessageId) {
        this.passwordSection = passwordSection;
        this.resourceConverter = resourceConverter;
        this.email = email;
        this.policyNumber = policyNumber;
        this.userId = userId;
        this.blankPasswordErrorMessageId = blankPasswordErrorMessageId;
        clearError();
    }

    void clearError() {
        passwordSection.setErrorMessage("");
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createBlankPasswordRule(blankPasswordErrorMessageId),
                createEasyToGuessRule(),
                createInvalidSpecialCharacterRule(),
                createMissingAlphaCharacterRule(),
                createMissingNumericCharacterRule(),
                createPasswordLengthRule(),
                createSimilarToUserPolicyOrEmailRule(),
                createValidPasswordRule());
    }

    private AceRule<String> createBlankPasswordRule(@StringRes final int errorMessageId) {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(errorMessageId));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return TextUtils.isEmpty(context);
            }
        };
    }


    private AceRule<String> createEasyToGuessRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(R.string.passwordEasyToGuessError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                // TODO: promote this to Tier service so that both plat form (Android and IOS) can re use - MRV
                // none of these have valid character combinations except for password1, but
                // analysts wanted to keep it as is to be consistent with ECAMS javascript
                final List<String> easyToGuessPasswords = Arrays.asList("12345678", "123456789", "babygirl", "chocolate",
                        "cocacola", "corvette", "einstein", "iloveyou", "michelle", "Password", "password1", "princess",
                        "starwars", "sunshine", "superman");
                return easyToGuessPasswords.contains(context);
            }
        };
    }


    private AceRule<String> createPasswordLengthRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(R.string.passwordInvalidLengthError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return context.length() < 8 || context.length() > 16;
            }
        };
    }

    private AceRule<String> createSimilarToUserPolicyOrEmailRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(R.string.passwordSimilarToUserPolicyOrEmailError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return new AceBasicStringSimilarityDetector(3)
                        .similarToAny(context, Arrays.asList(email, policyNumber, userId));
            }
        };
    }

    private AceRule<String> createMissingAlphaCharacterRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(R.string.passwordMissingAlphaError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !context.isEmpty() && !MUST_CONTAIN_LETTER_PATTERN.matcher(context).matches();
            }
        };
    }

    private AceRule<String> createMissingNumericCharacterRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(R.string.passwordMissingNumericError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !context.isEmpty() && !MUST_CONTAIN_DIGIT_PATTERN.matcher(context).matches();
            }
        };
    }

    private AceRule<String> createInvalidSpecialCharacterRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                passwordSection.setErrorMessage(resourceConverter.convert(R.string.passwordInvalidSpecialCharacterError));
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !VALID_SPECIAL_CHARACTER_PATTERN.matcher(context).matches();
            }
        };
    }

    private AceRule<String> createValidPasswordRule() {
        return new AceOtherwiseRule<String>() {

            @Override
            public void applyTo(@NonNull String context) {
                clearError();
            }
        };
    }
}
