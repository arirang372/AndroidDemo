package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

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

public class AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory implements AceFactory<List<AceRule<Integer>>> {
    private final AceLoginSettingsSectionDetailItem securityQuestionSectionDetailItem;
    private final AceConverter<Integer, String> resouceConverter;

    public AceLoginSettingsSecurityQuestionSpinnerValidationRuleFactory(@NonNull AceLoginSettingsSectionDetailItem securityQuestionSectionDetailItem, @NonNull AceConverter<Integer, String> resourceConverter) {
        this.resouceConverter = resourceConverter;
        this.securityQuestionSectionDetailItem = securityQuestionSectionDetailItem;
    }

    @NonNull
    @Override
    public List<AceRule<Integer>> create() {
        return Arrays.asList(createInvalidSpinnerSelectionRule(), createValidQuestionRule());
    }

    private AceRule<Integer> createInvalidSpinnerSelectionRule() {
        return new AceBaseRule<Integer>() {
            @Override
            public boolean isApplicable(Integer context) {
                return context.intValue() == 0;
            }

            @Override
            public void applyTo(Integer context) {
                securityQuestionSectionDetailItem.setErrorMessage(resouceConverter.convert(securityQuestionSectionDetailItem.getSecurityQuestionPosition().get() == 0 ?
                        R.string.questionOneNotSelected : R.string.questionTwoNotSelected));
            }
        };
    }

    private AceRule<Integer> createValidQuestionRule() {
        return new AceOtherwiseRule<Integer>() {

            @Override
            public void applyTo(@NonNull Integer context) {
                securityQuestionSectionDetailItem.setErrorMessage("");
            }
        };
    }
}
