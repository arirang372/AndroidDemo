package com.geico.mobile.android.ace.geicoapppresentation.firststart;

import android.app.Activity;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.eventhandling.AceListener;
import com.geico.mobile.android.ace.coreframework.eventhandling.serviceerror.AceErrorNotificationStrategy;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceSessionStateConstants;
import com.geico.mobile.android.ace.geicoappbusiness.asynctasks.AceDelayedTimerStartingNowRunInUi;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceBaseGuestServicesPageVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceFirstStartDao;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceFirstStartSharedPreferences;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesFlow;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesPageEnum;
import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesPageEnum.AceGuestServicesPageVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.listeners.AceEventSubjectUnusedListener;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceFragment;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceEcamsGuestServicesPageViewEvent;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceAppLaunchSplunkLoggingContext;
import com.geico.mobile.android.ace.mitsupport.eventhandling.AceFragmentMitServiceHandler;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitClientRegistrationRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitClientRegistrationResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_ACCOUNT_ACTIVATED_QUESTION_PAGE_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_ACCOUNT_ACTIVATED_QUESTION_PAGE_DISPLAYED_EVENT_ID;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_POLICYHOLDER_QUESTION_PAGE_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_POLICYHOLDER_QUESTION_PAGE_DISPLAYED_EVENT_ID;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_POLICYHOLDER_SIGN_IN_TRANSITION_PAGE_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_POLICYHOLDER_SIGN_IN_TRANSITION_PAGE_DISPLAYED_EVENT_ID;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_WELCOME_TO_APP_PAGE_DISPLAYED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLogConstants.MOBILE_WELCOME_TO_APP_PAGE_DISPLAYED_EVENT_ID;
import static com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames.PRIVACY_POLICY_INFO_COLLECTION;

/**
 * This is the fragment holding the guest services questionnaire pages.
 *
 * @author Mahmudul Hasan - GEICO
 */
public class AceGuestServicesLandingFragment extends AceFragment {

    private static final int TRANSITION_DELAY_IN_MILLISECONDS = 4000;
    private final AceClientRegistrationHandler clientRegistrationHandler = new AceClientRegistrationHandler();
    private final AceListener<String> goToActivateAccountQuestionListener = new AceGoToActivateAccountQuestionListener();
    private final AceListener<String> goToPolicyHolderQuestionListener = new AceGoToPolicyHolderQuestionListener();
    private final AceListener<String> goToWelcomePageListener = new AceGoToWelcomePageListener();
    private final AceGuestServicesPageVisitor<Void, Void> guestServicesLandingPageMetricsLogger = new AceGuestServiceLandingPageMetricsLogger();
    private final List<Integer> layouts = initializePageLayouts();
    private final AceListener<String> letsGetYouLoggedInListener = new AceLetsGetYouLoggedInListener();
    private final AceListener<String> telematicsEnrollmentQuestionInListener = new AceTelematicsEnrollmentQuestionListener();
    private AceFirstStartDao firstStartDao;
    private AceGuestServicesViewPager viewPager;

    protected void acceptVisitor(@NonNull AceGuestServicesPageVisitor<Void, Void> visitor) {
        firstStartDao.getCurrentPage().acceptVisitor(visitor);
    }

    private void considerSendingClientRegistrationRequest() {
        if (TextUtils.isEmpty(getDeviceRegistrationCredentials().getBreadcrumbId())) {
            MitClientRegistrationRequest request = new MitClientRegistrationRequest();
            request.setMobileClientId(getMobileClientId());
            send(request, clientRegistrationHandler);
        }
    }

    private void considerSettingCurrentPage(AceGuestServicesPageEnum page) {
        if (viewPager != null) {
            viewPager.setCurrentItem(page.getIndex(), true);
        }
    }

    private void considerSettingLinkedPrivacyPolicyText(TextView view) {
        if (view == null) return;
        view.setText(Html.fromHtml(getLinkedPrivacyPolicyText()));
        view.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void disableViewPagerSwipe() {
        viewPager.enableSwipe(false);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.guest_services_welcome_page;
    }

    private String getLinkedPrivacyPolicyText() {
        String url = getFullSiteOpener().lookUpLink(PRIVACY_POLICY_INFO_COLLECTION).getUrl();
        return String.format(Locale.US, getString(R.string.privacyPolicyNotice), url);
    }

    private void goToGuestServicesActivateAccountView() {
        moveToPage(AceGuestServicesPageEnum.ACTIVATE_ACCOUNT_PAGE);
    }

    private void goToGuestServicesPolicyHolderQuestionView() {
        moveToPage(AceGuestServicesPageEnum.POLICY_HOLDER_PAGE);
    }

    private void goToGuestServicesTelematicsEnrollmentQuestionView() {
        moveToPage(AceGuestServicesPageEnum.TELEMATICS_ENROLLMENT_PAGE);
    }

    private List<Integer> initializePageLayouts() {
        return Arrays.asList(
                R.layout.guest_services_hello_there_view,
                R.layout.guest_services_policy_holder_question_view,
                R.layout.guest_services_activate_account_view,
                R.layout.guest_services_telematics_enrollment_question_view,
                R.layout.guest_services_lets_get_you_logged_in_view
        );
    }

    private void initializeStartingPage() {
        acceptVisitor(new AceGuestServicesPageDeterminer());
    }

    private void logGuestServicesEcamsEvent(String eventDataText, int eventTypeId) {
        logEcamsEventUnpublished(new AceEcamsGuestServicesPageViewEvent(eventDataText, eventTypeId));
    }

    private void moveToPage(AceGuestServicesPageEnum page) {
        if (page != AceGuestServicesPageEnum.LETS_GET_YOU_LOGGED_IN_PAGE) {
            firstStartDao.setCurrentPage(page);
        }
        page.acceptVisitor(guestServicesLandingPageMetricsLogger);
        getApplicationSession().getGuestServicesFlow().setCurrentPage(page);
        considerSettingCurrentPage(page);
        considerSettingLinkedPrivacyPolicyText(findViewById(R.id.guestServicesPrivacyNotice));
    }

    @Override
    public void onResume() {
        super.onResume();
        considerSendingClientRegistrationRequest();
        setupViewPager();
        disableViewPagerSwipe();
        initializeStartingPage();
    }

    @Override
    protected void registerListeners() {
        super.registerListeners();
        registerListener(clientRegistrationHandler);
        registerListener(goToActivateAccountQuestionListener);
        registerListener(goToPolicyHolderQuestionListener);
        registerListener(goToWelcomePageListener);
        registerListener(letsGetYouLoggedInListener);
        registerListener(telematicsEnrollmentQuestionInListener);
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.guestServicesViewPager);
        viewPager.setAdapter(new AceGuestServicesViewPagerAdapter());
    }

    @Override
    protected void wireUpDependencies(AceRegistry registry) {
        super.wireUpDependencies(registry);
        this.firstStartDao = new AceFirstStartSharedPreferences(registry);
    }

    public class AceAccountActivatedQuestionTrackable implements AceAnalyticsTrackable {

        @Override
        public Activity getActivity() {
            return AceGuestServicesLandingFragment.this.getActivity();
        }
    }

    protected class AceClientRegistrationHandler extends AceFragmentMitServiceHandler<MitClientRegistrationRequest,
            MitClientRegistrationResponse> {

        AceClientRegistrationHandler() {
            super(AceGuestServicesLandingFragment.this, MitClientRegistrationResponse.class, AceErrorNotificationStrategy
                    .SHOW_SERVICE_ERROR_THEN_STAY);
        }

        private void considerPublishingInitialLaunchSplunkLogEvent() {
            considerLoggingSessionStateBasedSplunkEventWith(new AceAppLaunchSplunkLoggingContext(getSessionStateDescription(), AceSessionStateConstants.INITIAL_LAUNCH));
        }

        @Override
        public void onAnyFailure(MitClientRegistrationResponse response) {
            super.onAnyFailure(response);
            getApplicationSession().setClientRegistrationRequestState(AceInformationState.UNAVAILABLE);
        }

        @Override
        public void onAnySuccess(@NonNull MitClientRegistrationResponse response) {
            super.onAnySuccess(response);
            getApplicationSession().setClientRegistrationRequestState(AceInformationState.CURRENT);
            getApplicationSession().setDeviceRegistrationCredentials(response.getClientCredentials());
            considerPublishingInitialLaunchSplunkLogEvent();
        }
    }

    protected class AceGoToActivateAccountQuestionListener extends AceEventSubjectUnusedListener {

        AceGoToActivateAccountQuestionListener() {
            super(AceGuestServicesFlow.GUEST_SERVICES_EVENT_ACTIVATE_ACCOUNT);
        }

        @Override
        protected void onEvent() {
            goToGuestServicesActivateAccountView();
        }
    }

    protected class AceGoToPolicyHolderQuestionListener extends AceEventSubjectUnusedListener {

        AceGoToPolicyHolderQuestionListener() {
            super(AceGuestServicesFlow.GUEST_SERVICES_EVENT_POLICY_HOLDER_QUESTIONS);
        }

        @Override
        protected void onEvent() {
            goToGuestServicesPolicyHolderQuestionView();
        }
    }

    protected class AceGoToWelcomePageListener extends AceEventSubjectUnusedListener {

        AceGoToWelcomePageListener() {
            super(AceGuestServicesFlow.GUEST_SERVICES_EVENT_WELCOME);
        }

        @Override
        protected void onEvent() {
            firstStartDao.setCurrentPage(AceGuestServicesPageEnum.WELCOME_PAGE);
        }
    }

    protected class AceGuestServiceLandingPageMetricsLogger extends AceBaseGuestServicesPageVisitor<Void, Void> {

        @Override
        public Void visitActivateAccountPage(Void input) {
            logGuestServicesEcamsEvent(MOBILE_ACCOUNT_ACTIVATED_QUESTION_PAGE_DISPLAYED, MOBILE_ACCOUNT_ACTIVATED_QUESTION_PAGE_DISPLAYED_EVENT_ID);
            trackPageShown(new AceAccountActivatedQuestionTrackable());
            return NOTHING;
        }

        @Override
        protected Void visitAny(Void input) {
            return NOTHING;
        }

        @Override
        public Void visitLetsGetYouLoggedInPage(Void input) {
            logGuestServicesEcamsEvent(MOBILE_POLICYHOLDER_SIGN_IN_TRANSITION_PAGE_DISPLAYED, MOBILE_POLICYHOLDER_SIGN_IN_TRANSITION_PAGE_DISPLAYED_EVENT_ID);
            trackPageShown(new AceLetsGetYouLoggedInTrackable());
            return NOTHING;
        }

        @Override
        public Void visitPolicyHolderPage(Void input) {
            logGuestServicesEcamsEvent(MOBILE_POLICYHOLDER_QUESTION_PAGE_DISPLAYED, MOBILE_POLICYHOLDER_QUESTION_PAGE_DISPLAYED_EVENT_ID);
            trackPageShown(new AcePolicyHolderQuestionTrackable());
            return NOTHING;
        }
    }

    protected class AceGuestServicesPageDeterminer implements AceGuestServicesPageVisitor<Void, Void> {

        void transitionFromWelcomePageToNextPage() {
            new AceDelayedTimerStartingNowRunInUi(TRANSITION_DELAY_IN_MILLISECONDS) {
                @Override
                protected void runInUiWhenTimerHasExpired() {
                    if (getActivity() == null) return;
                    logGuestServicesEcamsEvent(MOBILE_WELCOME_TO_APP_PAGE_DISPLAYED, MOBILE_WELCOME_TO_APP_PAGE_DISPLAYED_EVENT_ID);
                    trackPageShown(new AceWelcomePageTrackable());
                    goToGuestServicesPolicyHolderQuestionView();
                }
            };
        }

        @Override
        public Void visitActivateAccountPage(Void input) {
            goToGuestServicesActivateAccountView();
            return NOTHING;
        }

        @Override
        public Void visitIntentPage(Void input) {
            startNonPolicyAction(ACTION_GUEST_SERVICES_FIRST_START);
            return NOTHING;
        }

        @Override
        public Void visitLetsGetYouLoggedInPage(Void input) {
            return NOTHING;
        }

        @Override
        public Void visitPolicyHolderPage(Void input) {
            goToGuestServicesPolicyHolderQuestionView();
            return NOTHING;
        }

        @Override
        public Void visitTelematicsEnrollmentPage(Void input) {
            goToGuestServicesTelematicsEnrollmentQuestionView();
            return NOTHING;
        }

        @Override
        public Void visitWelcomePage(Void input) {
            transitionFromWelcomePageToNextPage();
            return NOTHING;
        }
    }

    protected class AceGuestServicesViewPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return layouts.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ViewGroup layout = (ViewGroup) inflater.inflate(layouts.get(position), container, false);
            container.addView(layout);
            return layout;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            viewPager.remeasureView((View) object);
        }
    }

    protected class AceLetsGetYouLoggedInListener extends AceEventSubjectUnusedListener {

        AceLetsGetYouLoggedInListener() {
            super(AceGuestServicesFlow.GUEST_SERVICES_EVENT_LETS_GET_YOU_LOGGED_IN);
        }

        @Override
        protected void onEvent() {
            moveToPage(AceGuestServicesPageEnum.LETS_GET_YOU_LOGGED_IN_PAGE);
        }
    }

    protected class AceTelematicsEnrollmentQuestionListener extends AceEventSubjectUnusedListener {

        AceTelematicsEnrollmentQuestionListener() {
            super(AceGuestServicesFlow.GUEST_SERVICES_EVENT_TELEMATICS_ENROLLMENT_QUESTIONS);
        }

        @Override
        protected void onEvent() {
            moveToPage(AceGuestServicesPageEnum.TELEMATICS_ENROLLMENT_PAGE);
        }
    }

    public class AceLetsGetYouLoggedInTrackable implements AceAnalyticsTrackable {

        @Override
        public Activity getActivity() {
            return AceGuestServicesLandingFragment.this.getActivity();
        }
    }

    public class AcePolicyHolderQuestionTrackable implements AceAnalyticsTrackable {

        @Override
        public Activity getActivity() {
            return AceGuestServicesLandingFragment.this.getActivity();
        }
    }

    public class AceWelcomePageTrackable implements AceAnalyticsTrackable {

        @Override
        public Activity getActivity() {
            return AceGuestServicesLandingFragment.this.getActivity();
        }
    }
}
