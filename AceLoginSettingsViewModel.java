package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.repository.AceLoginSettingsRepository;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;

/**
 * ViewModel for handling UI tasks for the login settings component
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsViewModel extends AceBaseViewModel<AceLoginSettingsModel, AceLoginSettingsRepository> {

    public AceLoginSettingsViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    protected AceLoginSettingsRepository createRepository(@NonNull Application application) {
        return new AceLoginSettingsRepository(application);
    }

    public void initializeModel(@NonNull String viewTag) {
        getRepository().initializeModel(viewTag);
    }

    public void onFormButtonClicked() {
        getRepository().handleFormButtonClickedAction();
    }

    public void onLoginSettingsRowClicked(int position) {
        getRepository().handleLoginSettingsRowClickAction(position);
    }

    public void onPasswordFieldFocusChanged(boolean hasFocus) {
        getRepository().handlePasswordFieldFocusChangedAction(hasFocus);
    }

    public void onScreenUnlockSwitchToggled(boolean isSwitchOn) {
        getRepository().handleSwitchUnlockSwitchToggledAction(isSwitchOn);
    }

    public void onSecurityQuestionSelected(int securityQuestionPosition, @NonNull MitCodeDescriptionPair selectedQuestion) {
        getRepository().handleSecurityQuestionSelectedAction(securityQuestionPosition, selectedQuestion);
    }

    public void resetSelectedSectionItemValue() {
        getRepository().resetSelectedSectionItemValue();
    }

    public void onTermsOfUseLinkClicked() {
        getRepository().handleTermsOfUseLinkClickedAction();
    }
}
