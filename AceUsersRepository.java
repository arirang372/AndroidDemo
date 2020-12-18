package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor.AceViewExhibitorVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceMobilePagedActionEvent;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicPersonProfileEditMonitor;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceBasicUserProfileSynchronizer;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileDetailEvent;
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSessionFinisher;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVisibilityState;
import com.geico.mobile.android.ace.geicoappmodel.visitors.AceBaseContactGeicoTypeVisitor;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.email.mvvm.view.AceSendAnEmailActivity;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceDashboardActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.users.AceUserSetUpActivity;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers.AceUserProfileListItemClickRuleFactory;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_USERS;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SET;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SWITCH;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_SWITCH;

/**
 * Repository responsible for handling business logic for the User profile component.
 *
 * @author John Sung, Geico
 */
public class AceUsersRepository extends AceTierRepository<AceUsersModel> {

	public AceUsersRepository(@NonNull Application application) {
		super(application);
	}

	private void considerResettingDriverDataInformationState() {
		if (getPolicy().getPolicyLocation().isDuckCreek()) {
			getApplicationSession().getTelematicsFlow().setDriverInformationState(AceInformationState.UNREQUESTED);
		}
	}

	@NonNull
	@Override
	protected AceModelLiveData<AceUsersModel> createModelLiveData() {
		return new AceModelLiveData<>(new AceUsersModelFactory(getPolicy().getNumber()).create(getUserFlow()));
	}

	@VisibleForTesting
	protected AceUserFlow getUserFlow() {
		return getApplicationSession().getUserFlow();
	}

	public void handleBackPressedAction() {
		getUserFlow().setViewState(AceViewExhibitor.VIEW_SHOWED_TO_USER);
		getSessionController().getUserSession().getDashfolioFlow().setNextPageAction(ACTION_USERS);
		getUserFlow().getViewState().acceptVisitor(new AceViewExhibitorVisitor<Void, Void>() {
			@Override
			public Void visitOtherwise(Void input) {
				return NOTHING;
			}

			@Override
			public Void visitViewNotShowedToUser(Void input) {
				new AceUserProfileSessionFinisher(getRegistry()).execute();
				return NOTHING;
			}

			@Override
			public Void visitViewShowedToUser(Void input) {
				return NOTHING;
			}
		});
	}

	public void handleEditButtonClickAction() {
		logStartEditUserEvent();
		startActivity(AceUserSetUpActivity.class);
		logEvent(new AceUserProfileDetailEvent(FINISH_EDIT_USER_PROFILE));
	}

	public void handleListItemRowClickAction(@NonNull AceUserProfilePerson profilePerson) {
		AceSimpleRuleEngine.DEFAULT.applyFirst(createUserProfileListItemClickRuleFactory().create(), profilePerson);
	}

	@VisibleForTesting
	protected AceUserProfileListItemClickRuleFactory createUserProfileListItemClickRuleFactory(){
		return new AceUserProfileListItemClickRuleFactory(getRegistry(), this);
	}

	public void handleSelectedUserHasNeverBeenSetUpRule(@NonNull AceUserProfilePerson person) {
		logEvent(new AceMobilePagedActionEvent(START_NEW_USER_PROFILE, getString(R.string.newUser)));
		updateUserProfileFrom(person);
		if (getUserFlow().getFullName().isEmpty()) {
			recordMetricsForFinishSwitchUser();
		}
		considerResettingDriverDataInformationState();
		getApplicationSession().setMailingAddressVisibilityState(AceUserProfileVisibilityState.SHOWN_FOR_FRESH_INSTALL);
		startActivity(AceUserSetUpActivity.class);
	}

	public void handleSelectedUserOtherwiseRule(@NonNull AceUserProfilePerson person) {
		updateUserProfileFrom(person);
		recordMetricsForFinishSwitchUser();
		considerResettingDriverDataInformationState();
		startActivity(AceDashboardActivity.class);
	}

	private void logStartEditUserEvent() {
		logEvent(new AceMobilePagedActionEvent(START_EDIT_USER_PROFILE, "Edit User"));
		new AceBasicPersonProfileEditMonitor(getSessionController()).recordValues();
	}

	public void navigateUserByDestination() {
		getUserFlow().getDestination().acceptVisitor(new AceBaseContactGeicoTypeVisitor<Void, Void>() {
			@Override
			public Void visitAnyView(Void input) {
				return NOTHING;
			}

			@Override
			public Void visitChat(Void input) {
				startActivity(AceContactGeicoActivity.class);
				return NOTHING;
			}

			@Override
			public Void visitEmail(Void input) {
				startActivity(AceSendAnEmailActivity.class);
				return NOTHING;
			}

			@Override
			public Void visitUnknown(Void input) {
				startActivity(AceDashboardActivity.class);
				return NOTHING;
			}
		});
	}

	@VisibleForTesting
	protected void recordMetricsForFinishSwitchUser() {
		trackAction(ANALYTICS_USER_PROFILE_SWITCH, USER_PROFILE_SWITCH);
		logEvent(new AceUserProfileDetailEvent(FINISH_SWITCH_USER_PROFILE));
	}

	@VisibleForTesting
	protected void updateUserProfileFrom(AceUserProfilePerson personProfile) {
		new AceBasicUserProfileSynchronizer(getRegistry()).updateUserProfileFrom(personProfile);
		trackAction(ANALYTICS_USER_PROFILE_SET, getUserFlow().getUserProfileMetricsData());
	}
}
