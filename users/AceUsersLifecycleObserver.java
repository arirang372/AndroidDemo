package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceFragmentLifecycleObserver;

/**
 * Contains lifecycle-observing behavior for the user component.
 *
 * @author John Sung, Geico
 */
public class AceUsersLifecycleObserver extends AceFragmentLifecycleObserver<AceUsersViewModel> {

    public AceUsersLifecycleObserver(@NonNull AceUsersViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getViewModel().initializeModel();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        super.onDestroy(owner);
        getViewModel().recordMetricsForCompletingEditUserSettings();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        getViewModel().unregisterListeners();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        getViewModel().registerListeners();
        getViewModel().considerDisplayingUsersDialogs();
    }
}
