package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.AceCoverage;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.coverages.mvvm.view.AcePolicyCoverageCallback;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel.AcePolicyViewModel;
import com.geico.mobile.databinding.PolicyCoverageFragmentBinding;

/**
 * Fragment for the coverage detail page
 * Keep this class minimal.
 *
 * @author John Sung, Geico
 */
public class AceCoverageFragment extends AceBaseFragment<PolicyCoverageFragmentBinding, AcePolicyViewModel, AcePolicyModel>
        implements AcePolicyCoverageCallback {

    @Override
    protected AceDialogMonitor createDialogMonitorFrom(@NonNull FragmentManager fragmentManager) {
        return new AcePolicyDialogMonitor(fragmentManager);
    }

    @Override
    protected @AceConstantState.ActionBarCustomization int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.STANDARD;
    }

    @Override
    @StringRes
    protected int getFragmentTitleResourceId() {
        return R.string.coverage;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.policy_coverage_fragment;
    }

    @Override
    protected String getViewTag() {
        return ViewTag.COVERAGE_FRAGMENT_TAG;
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @Override
    public void onCallToMakeChangesClicked() {
        getViewModel().onCallToMakeChangesClicked();
    }

    @Override
    public void onEditCoverageClicked() {
        getViewModel().onEditCoverageClicked();
    }

    @Override
    public void onLearnMoreButtonClicked(@NonNull AceCoverage coverage) {
        getViewModel().onLearnMoreButtonClicked(coverage);
    }

    @Override
    public void onProofOfInsuranceClicked() {
        getViewModel().onProofOfInsuranceClicked();
    }
}
