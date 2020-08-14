package com.geico.mobile.android.ace.geicoapppresentation.firststart;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceLifecycleDirector;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceListener;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.asynctasks.AceDelayedTimerStartingNowRunInUi;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceBaseGuestServicesPageVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceFirstStartDao;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceFirstStartSharedPreferences;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesFlow;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesPageEnum;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesPageEnum.AceGuestServicesPageVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.listeners.AceEventSubjectListener;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceEcamsGuestServicesOptionSelectedEvent;
import com.geico.mobile.android.ace.geicoapppresentation.framework.AceBaseActionBarFeatures;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitLogPortfolioEventsRequest;

import org.json.JSONObject;

import androidx.annotation.NonNull;

import static com.geico.mobile.android.ace.geicoapppresentation.geniuslinks.AceBranchLinkHandler.BRANCH_LINK_CLICK_EVENT;
import static com.geico.mobile.android.ace.geicoapppresentation.geniuslinks.AceBranchLinkHandler.FIRST_INSTALL_AFTER_BRANCH_LINK_CLICKED;

/**
 * This is the activity holding the AceGuestServicesLandingFragment, which shows the views for the Guest Services pages that
 * come up before the Login page, if the app is newly installed. These pages determines whether to land into the Login
 * page or to go to the Account Activation page or land into the new Guest Services intent page, if the user is not a
 * policy holder.
 *
 * @author Abhishek Kunchakarra, Mahmudul Hasan - GEICO
 */
public class AceGuestServicesLandingActivity extends AceBaseGuestServicesFirstStartActivity {

	protected class AceFirstInstallAfterBranchLinkClickedListener extends AceEventSubjectListener<JSONObject> {

		public AceFirstInstallAfterBranchLinkClickedListener() {
			super(FIRST_INSTALL_AFTER_BRANCH_LINK_CLICKED);
		}

		@Override
		protected void onEventHandle(JSONObject subject) {
			startNonPolicyAction(ACTION_GENIUS_LINK_LANDING);
			publish(BRANCH_LINK_CLICK_EVENT, subject);
		}
	}

	protected class AceGoToLoginPageAfterDelay extends AceDelayedTimerStartingNowRunInUi {

		public AceGoToLoginPageAfterDelay() {
			super(TRANSITION_TO_LOGIN_PAGE_DELAY_IN_MILLISECONDS);
		}

		@Override
		protected void runInUiWhenTimerHasExpired() {
			startNonPolicyAction(ACTION_LOGIN);
		}
	}

	private static final int TRANSITION_TO_LOGIN_PAGE_DELAY_IN_MILLISECONDS = 1500;
	private AceBaseActionBarFeatures actionBarCustomizer;
	private AceLifecycleDirector branchLinkHandler;
	private final AceListener<JSONObject> firstInstallAfterBranchLinkClickedListener = new AceFirstInstallAfterBranchLinkClickedListener();
	private AceFirstStartDao firstStartDao;
	private AceLoginSettingsDao loginSettingsDao;

	protected void acceptVisitor(@NonNull AceGuestServicesPageVisitor visitor) {
		getApplicationSession().getGuestServicesFlow().acceptVisitor(visitor);
	}

	protected void considerConfiguringActionBar() {
		if (getSupportActionBar() != null) {
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blueJay)));
			getSupportActionBar().setElevation(0);
			considerShowingUpArrow();
		}
	}

	protected void considerShowingUpArrow() {
		acceptVisitor(new AceBaseGuestServicesPageVisitor<Void, Void>() {
			@Override
			public Void visitActivateAccountPage(Void input) {
				showUpArrow();
				return NOTHING;
			}

			@Override
			protected Void visitAny(Void input) {
				hideUpArrow();
				return NOTHING;
			}
		});
	}

	@Override
	protected int getLayoutResourceId() {
		return R.layout.guest_services_landing_activity;
	}

	protected void goToLoginPage() {
		new AceGoToLoginPageAfterDelay();
	}

	protected void hideUpArrow() {
		actionBarCustomizer.applyCustomization("", false, false, false, false);
	}

	@Override
	public void logEcamsEventUnpublished(@NonNull AceEcamsEventLog eventLog) {
		MitLogPortfolioEventsRequest request = createRequestFrom(eventLog);
		send(request, UNPUBLISHED);
	}

	public void onActivateAccountNoClicked(View view) {
		logEcamsEventUnpublished(new AceEcamsGuestServicesOptionSelectedEvent("No", MOBILE_ACCOUNT_ACTIVATED_QUESTION_PAGE_SELECTED_EVENT_ID));
		trackAccountActivationQuestionAction(ANALYTICS_ACCOUNT_ACTIVATED_QUESTION_NO, "AccountActivatedQuestion:No", "no");
		startNonPolicyAction(ACTION_ACTIVATE_ACCOUNT);
	}

	public void onActivateAccountYesClicked(View view) {
		logEcamsEventUnpublished(new AceEcamsGuestServicesOptionSelectedEvent("Yes", MOBILE_ACCOUNT_ACTIVATED_QUESTION_PAGE_SELECTED_EVENT_ID));
		trackAccountActivationQuestionAction(ANALYTICS_ACCOUNT_ACTIVATED_QUESTION_YES, "AccountActivatedQuestion:Yes", "yes");
		publish(AceGuestServicesFlow.GUEST_SERVICES_EVENT_TELEMATICS_ENROLLMENT_QUESTIONS);
	}


	public void onTelematicsEnrollmentYesClicked(View view) {
		//take user to 2FA of DriveEasy...
		getApplicationSession().getTelematicsFlow().setGuestServicesOnboarding(true);
		startNonPolicyAction(ACTION_TELEMATICS_REGISTRATION);
	}

	public void onTelematicsEnrollmentNoClicked(View view) {
		publish(AceGuestServicesFlow.GUEST_SERVICES_EVENT_LETS_GET_YOU_LOGGED_IN);
		hideUpArrow();
		goToLoginPage();
	}

	@Override
	protected void onBackPressedHook() {
		acceptVisitor(new AceBaseGuestServicesPageVisitor<Void, Void>() {
			@Override
			public Void visitActivateAccountPage(Void input) {
				hideUpArrow();
				publish(AceGuestServicesFlow.GUEST_SERVICES_EVENT_POLICY_HOLDER_QUESTIONS);
				return NOTHING;
			}

			@Override
			public Void visitTelematicsEnrollmentPage(Void input) {
				hideUpArrow();
				publish(AceGuestServicesFlow.GUEST_SERVICES_EVENT_ACTIVATE_ACCOUNT);
				return NOTHING;
			}

			@Override
			protected Void visitAny(Void input) {
				publish(AceGuestServicesFlow.GUEST_SERVICES_EVENT_WELCOME);
				AceGuestServicesLandingActivity.super.onBackPressedHook();
				return NOTHING;
			}

			@Override
			public Void visitLetsGetYouLoggedInPage(Void input) {
				//Do nothing
				return NOTHING;
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		actionBarCustomizer = createActionBarCustomizer();
		firstStartDao.setHandlingBranchLinkFromGuestServices(true);
		if (!firstStartDao.isFirstInstallDoneAfterBranchLinkClicked()) {
			branchLinkHandler.restart();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		this.setIntent(intent);
	}

	public void onPolicyHolderNoClicked(View view) {
		logEcamsEventUnpublished(new AceEcamsGuestServicesOptionSelectedEvent("No", MOBILE_POLICYHOLDER_QUESTION_PAGE_SELECTED_EVENT_ID));
		trackPolicyHolderQuestionAction(ANALYTICS_POLICY_HOLDER_QUESTION_NO, "PolicyholderQuestion:No", "no");
		saveFirstStartPageAction();
		startNonPolicyAction(ACTION_GUEST_SERVICES_FIRST_START);
	}

	public void onPolicyHolderYesClicked(View view) {
		logEcamsEventUnpublished(new AceEcamsGuestServicesOptionSelectedEvent("Yes", MOBILE_POLICYHOLDER_QUESTION_PAGE_SELECTED_EVENT_ID));
		trackPolicyHolderQuestionAction(ANALYTICS_POLICY_HOLDER_QUESTION_YES, "PolicyholderQuestion:Yes", "yes");
		publish(AceGuestServicesFlow.GUEST_SERVICES_EVENT_ACTIVATE_ACCOUNT);
		considerShowingUpArrow();
	}

	@Override
	protected void onResumeFragments() {
		super.onResumeFragments();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
		considerConfiguringActionBar();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if ("".equals(loginSettingsDao.retrieveUserId())) {
			return;
		}
		startNonPolicyAction(ACTION_LOGIN);
		finish();
	}

	@Override
	protected void onStop() {
		firstStartDao.setHandlingBranchLinkFromGuestServices(false);
		super.onStop();
	}

	@Override
	public void registerListeners() {
		super.registerListeners();
		registerListener(firstInstallAfterBranchLinkClickedListener);
	}

	protected void saveFirstStartPageAction() {
		firstStartDao.setCurrentPage(AceGuestServicesPageEnum.INTENT_PAGE);
	}

	protected void showUpArrow() {
		actionBarCustomizer.applyCustomization("", true, true, false, false);
	}

	protected void trackAccountActivationQuestionAction(String actionName, String contextName, String contextValue) {
		Map<String, String> contextVariables = new HashMap<>();
		contextVariables.put(GENERAL_ACTION_NAME, contextName);
		contextVariables.put(contextName, contextValue);
		trackAction(actionName, contextVariables);
	}

	protected void trackPolicyHolderQuestionAction(String actionName, String contextName, String contextValue) {
		Map<String, String> contextVariables = new HashMap<>();
		contextVariables.put(GENERAL_ACTION_NAME, contextName);
		contextVariables.put(contextName, contextValue);
		trackAction(actionName, contextVariables);
	}

	@Override
	protected void wireUpDependencies(AceRegistry registry) {
		super.wireUpDependencies(registry);
		branchLinkHandler = registry.getBranchLinkHandler();
		firstStartDao = new AceFirstStartSharedPreferences(registry);
		loginSettingsDao = new AceLoginSharedPreferencesDao(registry);
	}
}
