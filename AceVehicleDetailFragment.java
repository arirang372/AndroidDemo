package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModelTypes;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel.AcePolicyViewModel;
import com.geico.mobile.databinding.VehicleDetailBinding;

/**
 * A vehicle detail fragment.
 *
 * @author Kal Tadesse
 */
public class AceVehicleDetailFragment extends AceBaseFragment<VehicleDetailBinding, AcePolicyViewModel, AcePolicyModel> {

    @Override
    protected AceDialogMonitor createDialogMonitorFrom(@NonNull FragmentManager fragmentManager) {
        return new AcePolicyDialogMonitor(fragmentManager);
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
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().initializeModel(AcePolicyModelTypes.VEHICLE);
    }

    @Override
    protected String getViewTag() {
        return ViewTag.VEHICLE_FRAGMENT_TAG;
    }
}
