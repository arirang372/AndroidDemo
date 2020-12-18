package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.patterns.AceCustomFactory;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoapppresentation.users.AceUsersVehicleDescriptionFactory;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;

/**
 * Responsible for creating a view-backing AceUsersModel.
 *
 * @author John Sung, Geico
 */
public class AceUsersModelFactory implements AceCustomFactory<AceUsersModel, AceUserFlow> {

	private final String policyNumber;

	public AceUsersModelFactory(@NonNull String policyNumber) {
		this.policyNumber = policyNumber;
	}

	@Override
	public AceUsersModel create(@NonNull AceUserFlow options) {
		AceUsersModel usersModel = new AceUsersModel();
		AceBasicEnumerator.DEFAULT.reactToEach(options.getCurrentPolicyPersonProfiles(), subject -> {
			subject.setShouldShowVehicleDescription(!getVehicleDescription(options.getPerson()).isEmpty());
			subject.setShouldShowUserPhone(!options.getPerson().getMobilePhoneNumber().isEmpty());
			subject.setShouldShowEditButton(options.getPerson() == subject);
			subject.setVehicleDescription(getVehicleDescription(subject));
		});
		usersModel.setProfiles(options.getCurrentPolicyPersonProfiles());
		return usersModel;
	}

	@VisibleForTesting
	protected String getVehicleDescription(AceUserProfilePerson userProfilePerson) {
		return getVehicleDescription(userProfilePerson.getPrimaryVehicle(policyNumber));
	}

	private String getVehicleDescription(AceUserProfileVehicle vehicle) {
		return new AceUsersVehicleDescriptionFactory(vehicle).create();
	}
}
