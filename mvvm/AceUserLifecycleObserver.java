package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceFragmentLifecycleObserver;

/**
 * Contains lifecycle-observing behavior for the user component.
 *
 * @author John Sung, Geico
 */
public class AceUserLifecycleObserver extends AceFragmentLifecycleObserver<AceUsersViewModel> {
    public AceUserLifecycleObserver(@NonNull AceUsersViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getViewModel().initializeModel();
    }
}
