package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceLifecycleObserverFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.repository.AceLoginSettingsRepository;

/**
 * ViewModel for handling UI tasks for the login settings component
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsViewModel extends AceBaseViewModel<AceLoginSettingsModel,
        AceLoginSettingsRepository> implements AceLifecycleObserverFactory<AceLoginSettingsLifecycleObserver> {

    public AceLoginSettingsViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    protected AceLoginSettingsRepository createRepository(@NonNull Application application) {
        return new AceLoginSettingsRepository(application);
    }

    public void initializeModel() {
        getRepository().initializeModel();
    }

    @NonNull
    @Override
    public AceLoginSettingsLifecycleObserver createLifecycleObserver() {
        return new AceLoginSettingsLifecycleObserver(this);
    }

    public void onLoginSettingsRowClicked(int position) {
        getRepository().handleLoginSettingsRowClickAction(position);
    }

    public void onScreenUnlockSwitchToggled(boolean isSwitchOn) {
        getRepository().handleSwitchUnlockSwitchToggledAction(isSwitchOn);
    }
}
