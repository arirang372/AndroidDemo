package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceFragmentLifecycleObserver;

/**
 * Contains lifecycle-observing behavior for the login settings component.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsLifecycleObserver extends AceFragmentLifecycleObserver<AceLoginSettingsViewModel> {

    public AceLoginSettingsLifecycleObserver(@NonNull AceLoginSettingsViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        getViewModel().resetNumberOfIncorrectPasswordEntries();
    }
}
