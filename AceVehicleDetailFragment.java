package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModelTypes;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel.AcePolicyViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.vehicles.mvvm.helpers.AceVehicleDetailSectionRowNames;
import com.geico.mobile.android.ace.geicoapppresentation.policy.vehicles.mvvm.view.AceVehicleDetailSectionCallback;
import com.geico.mobile.databinding.VehicleDetailBinding;

/**
 * A vehicle detail fragment.
 *
 * @author Kal Tadesse
 */
public class AceVehicleDetailFragment extends AceBaseFragment<VehicleDetailBinding, AcePolicyViewModel, AcePolicyModel> implements AceVehicleDetailSectionCallback {

    @Override
    protected AceDialogMonitor createDialogMonitorFrom(@NonNull FragmentManager fragmentManager) {
        return new AcePolicyDialogMonitor(fragmentManager);
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @Override
    protected @ActionBarCustomization int getActionBarCustomizationType() {
        return ActionBarCustomization.STANDARD;
    }

    @Override
    protected String getFragmentTitle() {
        return getViewModel().getTitle();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.vehicle_detail;
    }

    @Override
    protected String getViewTag() {
        return ViewTag.VEHICLE_DETAIL_FRAGMENT_TAG;
    }

    @Override
    public void onCallToMakeChangesClicked(@NonNull Context activity) {
        getViewModel().onCallToMakeChangesClicked();
    }

    @Override
    public void onEditVehicleRemoveCallback(@NonNull Context activity) {
        getViewModel().onEditVehicleRemoveClicked();
    }

    @Override
    public void onEditVehicleReplaceCallback(@NonNull Context activity) {
        getViewModel().onEditVehicleReplaceClicked();
    }

    @Override
    public void onEditVehicleSectionCallback(@NonNull Context activity, @NonNull @AceVehicleDetailSectionRowNames String destination) {
        getViewModel().onEditVehicleSectionClicked(destination);
    }
}
