package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.discounts.mvvm.model.AceAppliedDiscountSavingModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.discounts.mvvm.view.AcePolicyDiscountCallback;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModel;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModelTypes;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel.AcePolicyViewModel;
import com.geico.mobile.databinding.PolicyCoverageFragmentBinding;

/**
 * Fragment for the discount detail page
 * Keep this class minimal.
 *
 * @author John Sung, Geico
 */
public class AceDiscountFragment extends AceBaseFragment<PolicyCoverageFragmentBinding, AcePolicyViewModel, AcePolicyModel>
        implements AcePolicyDiscountCallback {

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
        return R.string.discounts;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.policy_discount_fragment;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getViewModel().initializeModel(AcePolicyModelTypes.DISCOUNT);
    }

    @Override
    public void onGetAdditionalDiscountsClicked() {
        getViewModel().onGetAdditionalDiscountsClicked();
    }

    @Override
    public void onLearnMoreButtonClicked(@NonNull AceAppliedDiscountSavingModel discountSaving) {
        getViewModel().onLearnMoreButtonClicked(discountSaving);
    }

    @Override
    protected String getViewTag() {
        return ViewTag.DISCOUNT_FRAGMENT_TAG;
    }
}
