package com.geico.mobile.android.ace.geicoappbusiness.repository;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;

import com.geico.mobile.android.ace.coreframework.application.AceBasicContextRegistryLocator;
import com.geico.mobile.android.ace.coreframework.connectivity.AceConnectionState.AceConnectionStateVisitor;
import com.geico.mobile.android.ace.coreframework.connectivity.AceConnectionStateDetermination;
import com.geico.mobile.android.ace.coreframework.environment.AceEnvironment;
import com.geico.mobile.android.ace.coreframework.environment.AceEnvironmentVisitor;
import com.geico.mobile.android.ace.coreframework.rules.AceRuleEngine;
import com.geico.mobile.android.ace.coreframework.webservices.contexts.AceServiceContext;
import com.geico.mobile.android.ace.coreframework.webservices.task.AceBasicTaskHandler;
import com.geico.mobile.android.ace.coreframework.webservices.task.AceTaskHandler;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.AceBasicQueueLiveData;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.AceLiveDataTask;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceRequest;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceResponse;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskType;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRepository;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceBasicStateEmitter;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceStateEmitter;
import com.geico.mobile.android.ace.geicoappbusiness.features.AceFeatureConfiguration;
import com.geico.mobile.android.ace.geicoappbusiness.framework.AceBottomNavigationHidingSessionStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.fullsite.AceConsiderOpeningFullSite;
import com.geico.mobile.android.ace.geicoappbusiness.fullsite.AceFullSiteOpener;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.opinionlab.AceOpinionLabSourceIndicator;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappbusiness.recommendations.AceRecommendationsFacade;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.transformation.AceBasicLiveDataTransformationCreator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.transformation.AceLiveDataTransformationCreator;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionStateEnum.AceSessionStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceEnrollmentFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceNavigationFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceTelematicsFlow;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceVehicleCareFlow;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsPreferences;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsSharedPreferences;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsFacade;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceActionTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceOnBackPressedCallback;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceTrackPage;
import com.geico.mobile.android.ace.geicoappmodel.AceResetModel;
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy;
import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;
import com.geico.mobile.android.ace.geicoappmodel.AceWebLink;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AcePermissionState;
import com.geico.mobile.android.ace.geicoappmodel.enums.telematics.AceTelematicsFeatureMode;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoappmodel.permission.AcePermissionsRequest;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVisibilityState;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class holding common behavior for all repository objects in the app
 *
 * @author Richard Peirson, GEICO
 */
public abstract class AceBaseRepository<M extends AceViewBackingModel>
        implements AceOnBackPressedCallback, AceRepository<M>, AceVerifyUser, AceResetModel<M>, AceTrackPage {

    private final Application application;
    private final AceModelLiveData<M> modelLiveData;
    private final LiveData<AceResponse> responseLiveData;
    private final Map<Class, AceServiceReaction> serviceReactionsByRequestType = new HashMap<>();
    private final AceStateEmitter stateEmitter = createStateEmitter();
    private final AceTaskHandler taskHandler;
    private final AceLiveDataTransformationCreator<AceRequest, LiveData<AceResponse>> transformationCreator;
    private AceAnalyticsTrackable trackable = AceActionTrackable.DEFAULT;

    public AceBaseRepository(@NonNull Application application) {
        this.application = application;
        this.modelLiveData = createModelLiveData();
        //TODO:: we will delete this handler instance
        this.taskHandler = AceBasicTaskHandler.INSTANCE;
        transformationCreator = createLiveDataTransformation();
        responseLiveData = transformationCreator.create();
        configureViewBackingModel();
    }

    @Override
    public <I, O> O acceptVisitor(@NonNull AceConnectionStateVisitor<I, O> visitor, I input) {
        return new AceConnectionStateDetermination().create(getApplication()).acceptVisitor(visitor, input);
    }

    @Override
    public <I, O> O acceptVisitor(@NonNull AceEnvironmentVisitor<I, O> visitor, I input) {
        return getRegistry().getEnvironmentHolder().getValue().acceptVisitor(visitor, input);
    }

    @Override
    public <I, O> O acceptVisitor(@NonNull AceSessionStateVisitor<I, O> visitor, I input) {
        return getSessionController().acceptVisitor(visitor, input);
    }

    protected void addServiceReaction(@NonNull AceServiceReaction serviceReaction) {
        getServiceReactionsByRequestType().put(serviceReaction.getRequestType(), serviceReaction);
    }

    @Override
    public void configureSwitchPolicyBanner() {
        getModel().showSwitchPolicyBanner.set(getSessionController().getAuthorizedPolicies().size() > 1);
        getModel().switchPolicyBannerText.set(getSwitchPolicyBannerText());
    }

    protected void configureViewBackingModel() {
        // do nothing
    }

    protected AceLiveDataTransformationCreator<AceRequest, LiveData<AceResponse>> createLiveDataTransformation() {
        return new AceBasicLiveDataTransformationCreator(this::getLiveDataTask);
    }

    //TODO:: this may be changed to abstract - Kal Tadesse
    @NonNull
    @SuppressWarnings("unchecked")
    protected AceModelLiveData<M> createModelLiveData() {
        return new AceModelLiveData(new AceViewBackingModel());
    }

    protected AceExecutableWith<Activity> createOpenFullSiteExecutable(String webLinkName) {
        return new AceConsiderOpeningFullSite(getFullSiteOpener(), webLinkName);
    }

    protected AceStateEmitter createStateEmitter() {
        return new AceBasicStateEmitter();
    }

    protected void emitExecuteWithActivityState(AceExecutableWith<Activity> action) {
        getStateEmitter().emitExecuteWithActivityState(action);
    }

    protected void emitNavigation(@NavigateAction String action) {
        getStateEmitter().emitNavigation(action);
    }

    protected AceAnalyticsFacade getAnalyticsFacade() {
        return getRegistry().getAnalyticsFacade();
    }

    @NonNull
    @Override
    public Application getApplication() {
        return application;
    }

    public AceApplicationSession getApplicationSession() {
        return getSessionController().getApplicationSession();
    }

    @NonNull
    @Override
    public AceTelematicsSharedPreferences createTelematicsSharedPreferences() {
        return new AceTelematicsSharedPreferencesDao(getRegistry());
    }

    protected AceEnvironment getBuildEnvironment() {
        return getRegistry().getBuildEnvironment();
    }

    @NonNull
    public String getCrossSellSectionHeaderText() {
        return "";
    }

    protected AceEnrollmentFlow getEnrollmentFlow() {
        return getApplicationSession().getEnrollmentFlow();
    }

    @NonNull
    public AceFeatureConfiguration getFeatureConfiguration() {
        return getRegistry().getFeatureConfiguration();
    }

    protected AceTelematicsFeatureMode getFeatureModeForTelematics() {
        return getFeatureConfiguration().getModeForTelematics();
    }

    @NonNull
    protected AceFullSiteOpener getFullSiteOpener() {
        return getRegistry().getFullSiteOpener();
    }

    protected String getLastPageRendered() {
        return getSessionController().getApplicationMetrics().getLastPageRendered();
    }

    protected AceLiveDataTask getLiveDataTask(@AceTaskType String taskType) {
        throw new RuntimeException("This Method Should be overridden in order to use ResponseLiveData.");
    }

    @NonNull
    public AceLoginSettingsDao getLoginSettingsDao() {
        return new AceLoginSharedPreferencesDao(getRegistry());
    }

    @StringRes
    public int getMoatCardFirstButtonText() {
        return 0;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <L extends AceModelLiveData<M>> L getModelLiveData() {
        return (L) modelLiveData;
    }

    private AceNavigationFlow getNavigationFlow() {
        return getPolicySession().getNavigationFlow();
    }

    @NonNull
    public AceVehiclePolicy getPolicy() {
        return getSessionController().getPolicySession().getPolicy();
    }

    @NonNull
    public final AcePolicySession getPolicySession() {
        return getSessionController().getPolicySession();
    }

    protected String getPreviousPageRendered() {
        return getSessionController().getApplicationMetrics().getPreviousPageRendered();
    }

    protected AceRecommendationsFacade getRecommendationsFacade() {
        return getRegistry().getRecommendationsFacade();
    }

    protected AceRegistry getRegistry() {
        return AceBasicContextRegistryLocator.DEFAULT.locateRegistry();
    }

    protected AceBasicQueueLiveData<AceRequest> getRequestMutableLiveData() {
        return transformationCreator.getRequestMutableLiveData();
    }

    @NonNull
    public M getResetModel() {
        return getModel();
    }

    @NonNull
    @Override
    public AceRuleEngine getRuleEngine() {
        return getRegistry().getRuleEngine();
    }

    protected AceServiceReaction getServiceReactionFrom(@NonNull AceServiceContext serviceContext) {
        return getServiceReactionsByRequestType().get(serviceContext.getRequest().getClass());
    }

    @NonNull
    @Override
    public Map<Class, AceServiceReaction> getServiceReactionsByRequestType() {
        return serviceReactionsByRequestType;
    }

    @NonNull
    public AceSessionController getSessionController() {
        return getRegistry().getSessionController();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <E extends AceStateEmitter> E getStateEmitter() {
        return (E) stateEmitter;
    }

    @NonNull
    public String getString(@StringRes int stringRes) {
        return application.getString(stringRes);
    }

    public int getColor(@ColorRes int colorRes) {
        return application.getResources().getColor(colorRes);
    }

    @NonNull
    public String getString(@StringRes int stringResource, @NonNull Object... formatArgs) {
        return application.getString(stringResource, formatArgs);
    }

    private String getSwitchPolicyBannerText() {
        return (getPolicySession().getPolicyNumber().length() < 6) ? ""
                : getPolicy().getPortfolioPolicyType().displayString() +
                " (" + getPolicySession().getPolicyNumber().substring(6) + ")";
    }

    protected AceTaskHandler getTaskHandler() {
        return taskHandler;
    }

    @NonNull
    @Override
    public LiveData<AceResponse> getTaskResponseLiveData() {
        return responseLiveData;
    }

    @NonNull
    public AceTelematicsFlow getTelematicsFlow() {
        return getApplicationSession().getTelematicsFlow();
    }

    protected AceTelematicsPreferences getTelematicsPreferences() {
        return new AceTelematicsSharedPreferencesDao(getRegistry());
    }

    protected AceAnalyticsTrackable getTrackable() {
        return trackable;
    }

    protected void setTrackable(@NonNull AceAnalyticsTrackable trackable) {
        this.trackable = trackable;
    }

    protected AceVehicleCareFlow getVehicleCareFlow() {
        return getPolicySession().getVehicleCareFlow();
    }

    private Uri getWebLinkUri(String webLinkName) {
        AceWebLink webLink = getFullSiteOpener().lookUpLink(webLinkName);
        return Uri.parse(webLink.getUrl());
    }

    @Override
    public void hideBottomNavigation() {
        getModel().showBottomNavigation.set(false);
    }

    @Override
    public void hideBottomNavigationWhenNotInPolicySession() {
        acceptVisitor(new AceBottomNavigationHidingSessionStateVisitor(), getModel());
    }

    @Override
    public void hideBottomNavigationWhileKeyboardIsShown(@NonNull View bottomNavigation, int windowHeight) {
        getModel().isKeyboardVisible.set(new AceKeyboardVisibilityDerivation(bottomNavigation, windowHeight).deriveValueFrom(getModel()));
    }

    protected boolean isAnyRequestInFlight() {
        return transformationCreator.getRequestMutableLiveData().isAnyRequestInFlight();
    }

    public boolean isLegacyPolicy() {
        return getPolicy().getPolicyLocation().isOasis();
    }

    protected boolean isPermissionGranted(@NonNull String permission) {
        return ContextCompat.checkSelfPermission(getApplication(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onCleared() {
        //do nothing by default but implementations may want to use to clean up system resources.
    }

    public void onSettingsClick() {
        getApplicationSession().setMailingAddressVisibilityState(AceUserProfileVisibilityState.CAN_SHOW);
    }

    @Override
    public void openFullSite(@NonNull String webLinkName) {
        getStateEmitter().emitExecuteWithActivityState(createOpenFullSiteExecutable(webLinkName));
    }

    @Override
    public void openFullSite(@NonNull Activity activity, @NonNull String webLinkName) {
        getFullSiteOpener().openFullSite(activity, webLinkName);
    }

    @Override
    public void openUri(@NonNull String webLinkName) {
        startActivity(getApplication(), getWebLinkUri(webLinkName));
    }

    @Override
    public void requestPermission(int requestCode, @NonNull String... permissions) {
        getStateEmitter().emitPermissionState(new AcePermissionState(new AcePermissionsRequest(requestCode, permissions)));
    }

    public void setNextPageDashfolioAction(String action) {
        getSessionController().getUserSession().getDashfolioFlow().setNextPageAction(action);
    }

    @Override
    public void showBottomNavigation() {
        getModel().showBottomNavigation.set(true);
        getModel().bottomNavigationSelection.set(getNavigationFlow().getHighlightedBottomNavigationIconId());
        hideBottomNavigationWhenNotInPolicySession();
    }

    @Override
    public void showBottomNavigationWith(@IdRes int highlightedIcon) {
        getNavigationFlow().setHighlightedBottomNavigationIconId(highlightedIcon);
        showBottomNavigation();
    }

    @Override
    public void startActivity(@NonNull Class<? extends Activity> destinationActivity, @NonNull AceOpinionLabSourceIndicator labSourceIndicator, @NonNull String eventName) {
        getApplicationSession().setOpinionLabSourceIndicator(labSourceIndicator);
        startActivity(destinationActivity);
    }

    public void startActivity(@NonNull Class<? extends Activity> destinationActivity) {
        startActivity(getApplication(), destinationActivity);
    }

    /**
     * Start an activity based on the supplied action that does not require the
     * user to be logged in.
     *
     * @param action Intent action, such as "ACTION_FULL_SITE_TRANSFER"
     * @deprecated TODO:: once this research branch is merged to develop, please create a ticket to remove this method.
     * TODO:: It is not a good practice at all to bury this functionality deep in any class.
     * TODO:: It is up to the UI layer to carry out this functionality. When time permits, we will be moving the
     * TODO:: startActivity to UI layer, ideally, using {@link androidx.lifecycle.LiveData}
     * TODO:: Kal Tadesse will circle back on this.
     */
    @Deprecated
    protected void startNonPolicyAction(@NonNull String action) {
        getSessionController().startNonPolicyAction(getApplication(), action);
    }

    @Override
    public void trackAction(@NonNull String action) {
        getAnalyticsFacade().trackAction(getTrackable(), action);
    }

    @Override
    public void trackAction(@NonNull String action, @NonNull String contextValue) {
        getAnalyticsFacade().trackAction(getTrackable(), action, contextValue);
    }

    /**
     * Subclasses that want to track a page must call the setTrackable method
     * and map the assigned trackable in the AceAnalyticsConstants file.
     * <p>
     * This method is deprecated - page tracking is now handled by lifecycle observer in AceBaseFragment.
     */
    @Deprecated
    @Override
    public void trackPageShown() {
        getAnalyticsFacade().trackPageShown(getTrackable());
    }

    /**
     * Deprecated - page tracking is now handled by lifecycle observer in AceBaseFragment.
     */
    @Deprecated
    protected void trackPageShown(AceAnalyticsTrackable trackable) {
        setTrackable(trackable);
        trackPageShown();
    }
}