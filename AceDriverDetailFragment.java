package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.drivers.mvvm.helpers.AceDriverDetailDefinitions;
import com.geico.mobile.android.ace.geicoapppresentation.policy.drivers.mvvm.view.AceDriverDetailCallback;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel.AcePolicyViewModel;
import com.geico.mobile.databinding.PolicyDriverDetailFragmentBinding;

/**
 * A driver detail fragment.
 *
 * @author John Sung, Geico
 */
public class AceDriverDetailFragment extends AceBaseFragment<PolicyDriverDetailFragmentBinding, AcePolicyViewModel, AcePolicyModel>
        implements AceDriverDetailCallback {

    @Override
    protected AceDialogMonitor createDialogMonitorFrom(@NonNull FragmentManager fragmentManager) {
        return new AcePolicyDialogMonitor(fragmentManager);
    }

    @Override
    protected @ActionBarCustomization int getActionBarCustomizationType() {
        return ActionBarCustomization.STANDARD;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.driverInfo;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.policy_driver_detail_fragment;
    }

    @Override
    protected String getViewTag() {
        return ViewTag.DRIVER_DETAIL_FRAGMENT_TAG;
    }

    @Override
    public void onActivateThisDriverClicked() {
        onEditButtonClicked(AceDriverDetailDefinitions.AceDriverDetailEditableSectionNames.ACTIVATE_THIS_DRIVER);
    }

    @Override
    public void onCallToMakeChangesButtonClicked() {
        getViewModel().onCallToMakeChangesClicked();
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @Override
    public void onEditButtonClicked(@NonNull @AceDriverDetailDefinitions.AceDriverDetailEditableSectionNames String editableSectionName) {
        getViewModel().onEditButtonClicked(editableSectionName);
    }

    @Override
    public void onEditLicenseNumberClicked() {
        onEditButtonClicked(AceDriverDetailDefinitions.AceDriverDetailEditableSectionNames.EDIT_LICENSE_NUMBER);
    }

    @Override
    public void onRemoveDriverClicked() {
        getViewModel().onEditButtonClicked(AceDriverDetailDefinitions.AceDriverDetailEditableSectionNames.REMOVE_DRIVER);
    }
}
