package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceFragmentLifecycleObserver;

/**
 * Contains lifecycle-observing behavior for the policy component.
 *
 * @author Mike Wideman
 */
public class AcePolicyLifecycleObserver extends AceFragmentLifecycleObserver<AcePolicyViewModel> {

    public AcePolicyLifecycleObserver(@NonNull AcePolicyViewModel viewModel) {
        super(viewModel);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        getViewModel().considerNavigatingToDestination();
        AceSimpleRuleEngine.DEFAULT.applyFirst(new AcePolicyDetailPageNavigationRuleFactory(getViewModel()).create(owner), owner);
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        getViewModel().unregisterListeners();
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        getViewModel().logEvent("M5_POLICY_DASHBOARD_DISPLAYED");
        getViewModel().registerListeners();
        getViewModel().updateVehicleCareRecallAlerts();
        getViewModel().resetTrackableToPolicyDashboard();
    }
}
