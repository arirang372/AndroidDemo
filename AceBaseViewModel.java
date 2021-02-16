package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.geico.mobile.android.ace.coreframework.eventhandling.AceCoreEventConstants;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceResponse;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRepository;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceErrorHandler;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceStateEmitter;
import com.geico.mobile.android.ace.geicoappbusiness.fullsite.AceSiteOpener;
import com.geico.mobile.android.ace.geicoappbusiness.logging.AceSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoappbusiness.opinionlab.AceOpinionLabSourceIndicator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceBaseRepository;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceBaseEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEcamsEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLogConstants;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceSwitchPolicyButtonSelectedEventLog;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceActivityStarter;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceDialogOpener;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceLogEvent;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceOnBackPressedCallback;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceRequestPermission;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceShowBottomNavigation;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceTrackAction;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceTrackPage;
import com.geico.mobile.android.ace.geicoappmodel.AceClaimAlertNotification;
import com.geico.mobile.android.ace.geicoappmodel.AceModel;
import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceErrorState;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.claimslist.service.AceClaimsListStarter;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AcePolicySelectorActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.AceDeviceScreenSizeType;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;

/**
 * Use the ViewModelProvider.Factory class if we want to pass any dependencies in your ViewModel<br/>
 * A ViewModel has a more extended Lifecycle than the UI element like Activity and/or Fragment.
 * Its life cycle ends at the ViewModel#onCleared() method, so if there are any subscriptions this ViewModel is making,
 * please make sure you clean those up in the #onCleared() method call.<br/>
 * States defined in this base class must be of global usage like getNetworkUnavailableDialogVisibility()
 * and getWaitDialogVisibility()
 * <br/>
 *
 * @author Kal Tadesse, Richard Peirson, Venky Maganhalli GEICO
 */

public abstract class AceBaseViewModel<M extends AceViewBackingModel, R extends AceRepository<M>>
        extends AndroidViewModel
        implements AceAnalyticsActionConstants, AceActivityStarter, AceCoreEventConstants, AceDialogOpener,
        AceErrorHandler, AceEventLogConstants, AceLogEvent, AceModel, AceOnBackPressedCallback,
        AceOnCreateView, AceRequestPermission, AceShowBottomNavigation, AceSiteOpener, AceTrackAction, AceTrackPage {

    private final R repository;

    public AceBaseViewModel(@NonNull Application application) {
        super(application);
        repository = createRepository(application);
    }

    public void configureSwitchPolicyBanner() {
        getRepository().configureSwitchPolicyBanner();
    }

    public void considerHidingBottomNavigationWhileKeyboardIsShown(@NonNull View bottomNavigation, int windowHeight) {
        if (AceDeviceScreenSizeType.determineScreenSize(getApplication()).isMobile()) {
            getRepository().hideBottomNavigationWhileKeyboardIsShown(bottomNavigation, windowHeight);
        }
    }

    public void considerMetricsForSwitchPolicyButtonDisplayed() {
        if (getModel().showSwitchPolicyBanner.get()) {
            logEvent(new AceBaseEventLog(M5_SWITCH_POLICY_BUTTON_DISPLAYED));
        }
    }

    /**
     * Create a repository instance that will provide this viewModel with access to the data layer.
     *
     * @param application GEICO mobile
     * @return repository instance
     */
    @NonNull
    protected abstract R createRepository(@NonNull Application application);

    protected void displayAlertMessage(@NonNull @SuppressWarnings("unused") String error) {
        //subclasses should override this to display alert messages.
    }

    protected void emitNavigation(@NavigateAction String action) {
        getStateEmitter().emitNavigation(action);
    }

    @Override
    public String getDisplayString() {
        return toString();
    }

    @NonNull
    public String getMessageForDeviceUnlockDialog() {
        return getRepository().getMessageForDeviceUnlockDialog();
    }

    @NonNull
    public M getModel() {
        return getModelLiveData().getValue();
    }

    @NonNull
    public <L extends AceModelLiveData<M>> L getModelLiveData() {
        return getRepository().getModelLiveData();
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    public R getRepository() {
        return repository;
    }

    @NonNull
    public AceStateEmitter getStateEmitter() {
        return getRepository().getStateEmitter();
    }

    @NonNull
    protected String getString(@StringRes int resourceId, Object... formatArguments) {
        return getApplication().getString(resourceId, formatArguments);
    }

    /**
     * Generally, only one View (generally an Activity or sometimes a Dialog) should observe
     * the task response LiveData of a given repository to avoid duplicate service reactions.
     * When a new AceResponse is posted to this LiveData instance, all observers will react.
     */
    @NonNull
    public LiveData<AceResponse> getTaskResponseLiveData() {
        return getRepository().getTaskResponseLiveData();
    }

    public void onKeepMeLoggedInSelected() {
        getRepository().onKeepMeLoggedInSelected();
    }

    public void onKeepMeLoggedInDiscarded() {
        getRepository().onKeepMeLoggedInDiscarded();
    }

    @ViewTag
    protected String getViewTag() {
        return getModel().viewTag;
    }

    public void handleNavigationItemSelected(@IdRes int itemId) {
        trackAction(ANALYTICS_M5_FOOTER_BUTTON_SELECTED, "M5Dashboard:FooterButtonSelected");
        new AceBottomNavigationDestinationExecutable(this).executeWith(itemId);
    }

    @Override
    public void hideBottomNavigation() {
        getRepository().hideBottomNavigation();
    }

    public void hideBottomNavigationWhenNotInPolicySession() {
        getRepository().hideBottomNavigationWhenNotInPolicySession();
    }

    public void hideKeyboard() {
        getStateEmitter().emitExecuteWithActivityState(activity -> ((AceBaseActivity<?, ?>) activity).hideKeyboard());
    }

    protected boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    protected boolean isPermissionGranted(int originalRequestCode, int resultRequestCode, int[] grantResults) {
        return originalRequestCode == resultRequestCode &&
                grantResults.length == 1 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    public void launchDialer(@StringRes int phoneNumberResourceId) {
        getRepository().launchDialerWith(phoneNumberResourceId);
    }

    @Override
    public void logEvent(@NonNull AceEcamsEventLog eventLog) {
        getRepository().logEvent(eventLog);
    }

    @Override
    public void logEvent(@NonNull AceEventLog eventLog) {
        repository.logEvent(eventLog);
    }

    @Override
    public void logEvent(@NonNull AceSplunkLoggingContext splunkLoggingContext) {
        repository.logEvent(splunkLoggingContext);
    }

    @Override
    public void logEvent(@NonNull String eventName) {
        repository.logEvent(eventName);
    }

    @Override
    public void markAsNotWaiting() {
        getRepository().markAsNotWaiting();
    }

    @Override
    public void navigateTo(@NonNull @ViewTag String viewTag, @NonNull Context context, Object helper) {
        getRepository().navigateTo(viewTag, context, helper);
    }

    public void onActivateDeviceUnlockDiscarded() {
        getRepository().onActivateDeviceUnlockDiscarded();
    }

    public void onActivateDeviceUnlockSelected() {
        getRepository().onActivateDeviceUnlockSelected();
    }

    @Override
    public void onError(@NonNull AceErrorState errorState) {
        displayAlertMessage(errorState.error);
    }

    @Override
    public void openDialog(@NonNull @DialogTag String dialogTag) {
        getRepository().openDialog(dialogTag);
    }

    @Override
    public void openFullSite(@NonNull String webLinkName) {
        getRepository().openFullSite(webLinkName);
    }

    @Override
    public void openFullSite(@NonNull Activity activity, @NonNull String webLinkName) {
        getRepository().openFullSite(activity, webLinkName);
    }

    @Override
    public void openFullSite(@NonNull Activity activity, @NonNull String userId, @NonNull String policyNumber, @NonNull String webLinkName) {
        getRepository().openFullSite(activity, userId, policyNumber, webLinkName);
    }

    @Override
    public void openFullSite(@NonNull Activity activity, @NonNull AceClaimAlertNotification alertNotification, @NonNull String webLinkName) {
        getRepository().openFullSite(activity, alertNotification, webLinkName);
    }

    @Override
    public void openFullSite(@NonNull Intent intentWithWebLinkNameExtra) {
        getRepository().openFullSite(intentWithWebLinkNameExtra);
    }

    @Override
    public void openUri(@NonNull String webLink) {
        getRepository().openUri(webLink);
    }

    public void reactToTaskResponse(@NonNull Object response) {
        getRepository().reactToTaskResponse((AceResponse) response);
    }

    @Override
    public void requestPermission(int requestCode, @NonNull String... permissions) {
        getRepository().requestPermission(requestCode, permissions);
    }

    @Override
    public void showBottomNavigation() {
        getRepository().showBottomNavigation();
    }

    @Override
    public void showBottomNavigationWith(@IdRes int highlightedIcon) {
        getRepository().showBottomNavigationWith(highlightedIcon);
    }

    @Override
    public void startActivity(@NonNull Class<? extends Activity> destinationActivity, @NonNull AceOpinionLabSourceIndicator labSourceIndicator, @NonNull String eventName) {
        getRepository().startActivity(destinationActivity, labSourceIndicator, eventName);
    }

    public void startActivity(@NonNull Class<? extends Activity> destinationActivity) {
        startActivity(getApplication(), destinationActivity);
    }

    public void startClaimsListService() {
        new AceClaimsListStarter((AceTierRepository<?>) getRepository()).execute();
    }

    public void switchPolicySelectedOnPage(@NonNull String pageName) {
        trackAction(AceAnalyticsActionConstants.ANALYTICS_SWITCH_POLICY_BUTTON, "M5:SwitchPolicyButtonSelected");
        logEvent(new AceSwitchPolicyButtonSelectedEventLog(pageName));
        startActivity(getApplication(), AcePolicySelectorActivity.class);
    }

    @Override
    public void trackAction(@NonNull String action, @NonNull String contextValue) {
        repository.trackAction(action, contextValue);
    }

    @Override
    public void trackAction(@NonNull String action) {
        repository.trackAction(action);
    }

    @Deprecated
    @Override
    @SuppressWarnings("unchecked")
    public void trackPageShown() {
        ((AceBaseRepository<M>) getRepository()).trackPageShown();
    }

    public boolean userWasEnrolledForDeviceUnlock() {
        return getRepository().userWasEnrolledForDeviceUnlock();
    }
}