package com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.android.ace.coreframework.patterns.AceCustomFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.model.AcePolicyModelTypes;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view.AceCoverageFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view.AceDiscountFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view.AceDriverDetailFragment;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view.AceVehicleDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class AcePolicyDetailPageNavigationRuleFactory implements AceCustomFactory<List<AceRule>, LifecycleOwner> {
    private final AcePolicyViewModel viewModel;

    public AcePolicyDetailPageNavigationRuleFactory(@NonNull AcePolicyViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public List<AceRule> create(LifecycleOwner options) {
        List<AceRule> rules = new ArrayList<>();
        rules.add(createNavigateToCoveragePageRule());
        rules.add(createNavigateToVehiclePageRule());
        rules.add(createNavigateToDiscountPageRule());
        rules.add(createNavigateToDriverPageRule());
        return rules;
    }

    private AceRule<LifecycleOwner> createNavigateToCoveragePageRule() {
        return new AceBaseRule<LifecycleOwner>() {

            @Override
            public void applyTo(@NonNull LifecycleOwner lifecycleOwner) {
                viewModel.initializeModel(AcePolicyModelTypes.COVERAGE);
            }

            @Override
            public boolean isApplicable(@NonNull LifecycleOwner lifecycleOwner) {
                return lifecycleOwner instanceof AceCoverageFragment;
            }
        };
    }

    private AceRule<LifecycleOwner> createNavigateToVehiclePageRule() {
        return new AceBaseRule<LifecycleOwner>() {

            @Override
            public void applyTo(@NonNull LifecycleOwner lifecycleOwner) {
                viewModel.initializeModel(AcePolicyModelTypes.VEHICLE);
            }

            @Override
            public boolean isApplicable(@NonNull LifecycleOwner lifecycleOwner) {
                return lifecycleOwner instanceof AceVehicleDetailFragment;
            }
        };
    }

    private AceRule<LifecycleOwner> createNavigateToDiscountPageRule() {
        return new AceBaseRule<LifecycleOwner>() {

            @Override
            public void applyTo(@NonNull LifecycleOwner lifecycleOwner) {
                viewModel.initializeModel(AcePolicyModelTypes.DISCOUNT);
            }

            @Override
            public boolean isApplicable(@NonNull LifecycleOwner lifecycleOwner) {
                return lifecycleOwner instanceof AceDiscountFragment;
            }
        };
    }

    private AceRule<LifecycleOwner> createNavigateToDriverPageRule() {
        return new AceBaseRule<LifecycleOwner>() {

            @Override
            public void applyTo(@NonNull LifecycleOwner lifecycleOwner) {
                viewModel.initializeModel(AcePolicyModelTypes.DRIVER);
            }

            @Override
            public boolean isApplicable(@NonNull LifecycleOwner lifecycleOwner) {
                return lifecycleOwner instanceof AceDriverDetailFragment;
            }
        };
    }
}
