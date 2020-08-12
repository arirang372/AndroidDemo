package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.repository;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.metrics.AceApplicationMetrics;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionStateEnum.AceSessionStateVisitor;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceContactUsCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceMultiUserProfileCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AcePerksCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AcePolicyDownloadedCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AcePreferencesCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceSettingsCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceSingleUserProfileCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceSwitchPolicyCategory;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceUserSessionOnlyCategory;

import java.util.ArrayList;

/**
 * Make changes to the model that are dependent upon the current session state.
 *
 * @author Richard Peirson, GEICO
 */
class AceUpdateModelBasedOnSessionState implements AceSessionStateVisitor<AceSettingsRepository, Void> {

    private void addCategoryToModel(AceSettingsRepository repository, AceSettingsCategory category) {
        repository.getModel().settingsCategories.get().add(category);
    }

    private void considerAddingSwitchPolicyCategoryToModel(AceSettingsRepository repository) {
        String policyType = getCurrentPolicyType(repository);
        if (policyType.isEmpty()) return;
        addCategoryToModel(repository, new AceSwitchPolicyCategory(repository.getApplication(), policyType));
    }

    @NonNull
    private AceApplicationMetrics getApplicationMetrics(AceSettingsRepository input) {
        return input.getSessionController().getApplicationMetrics();
    }

    private String getCurrentPolicyType(AceSettingsRepository repository) {
        return new AceCurrentPolicyTypeDeterminer().deriveValueFrom(repository);
    }

    private String getCurrentUserName(AceSettingsRepository repository) {
        return new AceCurrentUserNameDeterminer().deriveValueFrom(repository.getSessionController());
    }

    private AceSettingsCategory getUserProfileCategory(AceSettingsRepository repository) {
        String name = getCurrentUserName(repository);
        return name.isEmpty()
                ? new AceSingleUserProfileCategory(repository.getApplication())
                : new AceMultiUserProfileCategory(repository.getApplication(), name);
    }

    @Override
    public Void visitInInsiteSession(AceSettingsRepository input) {
        addCategoryToModel(input, new AceContactUsCategory(input.getApplication()));
        return NOTHING;
    }

    @Override
    public Void visitInPolicySession(AceSettingsRepository input) {
        input.getModel().settingsCategories.set(new ArrayList<>());
        input.getModel().logoutVisibility.set(true);
        input.getModel().setShouldShowLoginButton(false);
        AceApplicationMetrics metrics = getApplicationMetrics(input);
        if (ViewTag.POLICY_SELECTOR.equals(metrics.getLastPageRendered())) {
            addCategoryToModel(input, new AceUserSessionOnlyCategory(input.getApplication()));
            addCategoryToModel(input, new AceContactUsCategory(input.getApplication()));
            String policyType = input.getPolicySession().getPolicy().getPortfolioPolicyType().displayString();
            addCategoryToModel(input, new AceSwitchPolicyCategory(input.getApplication(), policyType));
            return NOTHING;
        }
        addCategoryToModel(input, getUserProfileCategory(input));
        addCategoryToModel(input, new AcePolicyDownloadedCategory(input.getApplication()));
        addCategoryToModel(input, new AceContactUsCategory(input.getApplication()));
        considerAddingSwitchPolicyCategoryToModel(input);
        return NOTHING;
    }

    @Override
    public Void visitInUserSessionOnly(AceSettingsRepository input) {
        input.getModel().logoutVisibility.set(true);
        input.getModel().setShouldShowLoginButton(false);
        addCategoryToModel(input, new AceUserSessionOnlyCategory(input.getApplication()));
        addCategoryToModel(input, new AcePerksCategory(input.getApplication()));
        addCategoryToModel(input, new AceContactUsCategory(input.getApplication()));
        return NOTHING;
    }

    @Override
    public Void visitNotAuthenticated(AceSettingsRepository input) {
        input.getModel().setShouldShowLoginButton(true);
        addCategoryToModel(input, new AcePerksCategory(input.getApplication()));
        addCategoryToModel(input, new AcePreferencesCategory(input.getApplication()));
        addCategoryToModel(input, new AceContactUsCategory(input.getApplication()));
        input.acceptVisitor(new AceUpdateModelBasedOnEnvironment(), input);
        return NOTHING;
    }
}