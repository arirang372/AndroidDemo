package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.LoginSettingsPasswordStrength;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AceLoginSettingsPasswordStrengthMeterRuleFactory implements AceFactory<List<AceRule<String>>> {
    private final List<String> emailPolicyUserValues = new ArrayList<>();
    private final AceLoginSettingsSectionDetailItem footerSection;
    // none of these have valid character combinations except for password1, but
    // analysts wanted to keep it as is to be consistent with ECAMS javascript
    private final List<String> easyToGuessPasswords = Arrays.asList("12345678", "123456789", "babygirl", "chocolate",
            "cocacola", "corvette", "einstein", "iloveyou", "michelle", "Password", "password1", "princess",
            "starwars", "sunshine", "superman");

    public AceLoginSettingsPasswordStrengthMeterRuleFactory(
            @NonNull AceLoginSettingsSectionDetailItem footerSection,
            @NonNull String email,
            @NonNull String policyNumber,
            @NonNull String userId) {
        this.footerSection = footerSection;
        emailPolicyUserValues.add(email);
        emailPolicyUserValues.add(policyNumber);
        emailPolicyUserValues.add(userId);
    }

    @NonNull
    @Override
    public List<AceRule<String>> create() {
        return Arrays.asList(createEasyToGuessRule(), createInvalidCharacterRule(),
                createInvalidLengthRule(), createNoDigitFoundRule(), createNoLetterFoundRule(),
                createAvoidEmailPolicyUserValuesRule(), createOtherwiseRule());
    }

    private AceRule<String> createEasyToGuessRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.MEDIUM_PASSWORD);
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return easyToGuessPasswords.contains(context);
            }
        };
    }

    private AceRule<String> createInvalidCharacterRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.WEAK_PASSWORD);
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return context.matches("^.*[^\\w!@#$%^&*()-]+.*$");
            }
        };
    }

    private AceRule<String> createInvalidLengthRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.WEAK_PASSWORD);
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !context.matches("^.{8,16}$");
            }
        };
    }

    private AceRule<String> createNoDigitFoundRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.WEAK_PASSWORD);
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !context.matches("^.*[0-9]+.*$");
            }
        };
    }

    private AceRule<String> createNoLetterFoundRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.WEAK_PASSWORD);
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return !context.matches("^.*[a-zA-Z]+.*$");
            }
        };
    }

    private AceRule<String> createAvoidEmailPolicyUserValuesRule() {
        return new AceBaseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.WEAK_PASSWORD);
            }

            @Override
            public boolean isApplicable(@NonNull String context) {
                return emailPolicyUserValues.contains(context);
            }
        };
    }

    private AceRule<String> createOtherwiseRule() {
        return new AceOtherwiseRule<String>() {
            @Override
            public void applyTo(@NonNull String context) {
                setPasswordStrengthAs(LoginSettingsPasswordStrength.STRONG_PASSWORD);
            }
        };
    }

    private void setPasswordStrengthAs(@LoginSettingsPasswordStrength int passwordStrength) {
        String emptyColor = "#F2F0EC";
        String mediumPasswordColor = "#FFB209";
        String strongPasswordColor = "#79C13E";
        switch (passwordStrength) {
            case LoginSettingsPasswordStrength.MEDIUM_PASSWORD:
                footerSection.setShouldShowPasswordAsStrong(false);
                footerSection.setShouldShowPasswordAsMedium(true);
                footerSection.setMediumPasswordText("Medium");
                footerSection.setStrongPasswordText("");
                footerSection.setWeakPasswordText("");
                footerSection.setMediumPasswordColor(mediumPasswordColor);
                footerSection.setStrongPasswordColor(emptyColor);
                footerSection.setWeakPasswordColor(mediumPasswordColor);
                break;
            case LoginSettingsPasswordStrength.STRONG_PASSWORD:
                footerSection.setShouldShowPasswordAsStrong(true);
                footerSection.setShouldShowPasswordAsMedium(false);
                footerSection.setMediumPasswordText("");
                footerSection.setStrongPasswordText("Strong");
                footerSection.setWeakPasswordText("");
                footerSection.setMediumPasswordColor(strongPasswordColor);
                footerSection.setStrongPasswordColor(strongPasswordColor);
                footerSection.setWeakPasswordColor(strongPasswordColor);
                break;
            case LoginSettingsPasswordStrength.WEAK_PASSWORD:
                footerSection.setShouldShowPasswordAsStrong(false);
                footerSection.setShouldShowPasswordAsMedium(false);
                footerSection.setMediumPasswordText("");
                footerSection.setStrongPasswordText("");
                footerSection.setWeakPasswordText("Weak");
                footerSection.setMediumPasswordColor(emptyColor);
                footerSection.setStrongPasswordColor(emptyColor);
                footerSection.setWeakPasswordColor("#B6091A");
                break;
        }
    }
}
