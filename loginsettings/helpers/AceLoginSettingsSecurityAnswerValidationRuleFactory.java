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

import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitFieldValidationConstants.VALID_SECURITY_ANSWER_EXPRESSION;

public class AceLoginSettingsSecurityAnswerValidationRuleFactory implements AceFactory<List<AceRule<String>>> {
    private static final Pattern VALID_SECURITY_ANSWER_PATTERN = Pattern.compile(VALID_SECURITY_ANSWER_EXPRESSION);
    private final AceLoginSettingsSectionDetailItem securityAnswerSectionDetailItem;
    private final AceConverter<Integer, String> resouceConverter;

    public AceLoginSettingsSecurityAnswerValidationRuleFactory(@NonNull AceLoginSettingsSectionDetailItem securityAnswerSectionDetailItem, @NonNull AceConverter<Integer, String> resourceConverter) {
        this.securityAnswerSectionDetailItem = securityAnswerSectionDetailItem;
        this.resouceConverter = resourceConverter;
        clearError();
    }

    void clearError() {
        this.securityAnswerSectionDetailItem.setErrorMessage("");
    }

    private AceRule<String> createBlankTextFieldRule() {
        return new AceBaseRule<String>() {
            @Override
            public boolean isApplicable(String context) {
                return TextUtils.isEmpty(context);
            }

            @Override
            public void applyTo(String context) {
                securityAnswerSectionDetailItem.setErrorMessage(resouceConverter.convert(securityAnswerSectionDetailItem.getSecurityQuestionPosition().get() == 0 ?
                        R.string.answerOneEmpty : R.string.answerTwoEmpty));
            }
        };
    }

    private AceRule<String> createInvalidAnswerCharacters() {
        return new AceBaseRule<String>() {
            @Override
            public boolean isApplicable(String context) {
                return !VALID_SECURITY_ANSWER_PATTERN.matcher(context).matches();
            }

            @Override
            public void applyTo(String context) {
                securityAnswerSectionDetailItem.setErrorMessage(resouceConverter.convert(R.string.answerInvalid));
            }
        };
    }

    private AceRule<String> createValidAnswerRule() {
        return new AceOtherwiseRule<String>() {

            @Override
            public void applyTo(@NonNull String context) {
                securityAnswerSectionDetailItem.setErrorMessage("");
            }
        };
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createBlankTextFieldRule(), createInvalidAnswerCharacters(), createValidAnswerRule());
    }
}
