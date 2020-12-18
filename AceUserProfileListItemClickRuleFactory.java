package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.coreframework.rules.AceBaseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicUserProfileSynchronizer;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSynchronizer;
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository.AceUsersRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates the rule for clicking each user profile item
 *
 * @author John Sung, Geico
 */
public class AceUserProfileListItemClickRuleFactory implements AceFactory<List<AceRule<AceUserProfilePerson>>> {

    private final AceVehiclePolicy policy;
    private final AceUsersRepository repository;
    private final AceUserFlow userFlow;
    private final AceUserProfileSynchronizer userProfileSynchronizer;

    public AceUserProfileListItemClickRuleFactory(@NonNull AceRegistry registry, @NonNull AceUsersRepository repository) {
        userFlow = getUserFlowFrom(registry);
        userProfileSynchronizer = createUserProfileSynchronizer(registry);
        policy = getPolicyFrom(registry);
        this.repository = repository;
    }

    @VisibleForTesting
    protected AceUserFlow getUserFlowFrom(AceRegistry registry) {
        return registry.getSessionController().getApplicationSession().getUserFlow();
    }

    @NonNull
    @Override
    public List<AceRule<AceUserProfilePerson>> create() {
        List<AceRule<AceUserProfilePerson>> rules = new ArrayList<>();
        rules.add(createSelectedUserHasNeverBeenSetUpRule());
        rules.add(createSelectedUserSameAsFlowUserRule());
        rules.add(createSelectedUserOtherwiseRule());
        return rules;
    }

    private AceRule<AceUserProfilePerson> createSelectedUserHasNeverBeenSetUpRule() {
        return new AceBaseRule<AceUserProfilePerson>() {

            @Override
            public void applyTo(AceUserProfilePerson person) {
                repository.handleSelectedUserHasNeverBeenSetUpRule(person);
            }

            @Override
            public boolean isApplicable(AceUserProfilePerson person) {
                return !userProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(person)
                        && policy.getDrivers().size() > 1;
            }
        };
    }

    private AceRule<AceUserProfilePerson> createSelectedUserOtherwiseRule() {
        return new AceOtherwiseRule<AceUserProfilePerson>() {
            @Override
            public void applyTo(AceUserProfilePerson person) {
                repository.handleSelectedUserOtherwiseRule(person);
            }
        };
    }

    private AceRule<AceUserProfilePerson> createSelectedUserSameAsFlowUserRule() {
        return new AceBaseRule<AceUserProfilePerson>() {
            @Override
            public void applyTo(AceUserProfilePerson person) {
                repository.navigateUserByDestination();
            }

            @Override
            public boolean isApplicable(AceUserProfilePerson person) {
                return userFlow.getPerson().equals(person);
            }
        };
    }

    @VisibleForTesting
    protected AceUserProfileSynchronizer createUserProfileSynchronizer(AceRegistry registry) {
        return new AceBasicUserProfileSynchronizer(registry);
    }

    @VisibleForTesting
    protected AceVehiclePolicy getPolicyFrom(AceRegistry registry) {
        return registry.getSessionController().getPolicySession().getPolicy();
    }
}
