package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.connectivity.AceBaseConnectionStateVisitor;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceBaseInformationStateVisitor;
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceResponse;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsGetRelatedDriversServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsGetTripDetailsServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsRetrieveDriverDataServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTelematicsTripCorrectionsServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceBaseSessionStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceTelematicsDashboardTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceTelematicsReportCardTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceTelematicsScoreDetailsTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceTelematicsTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceTelematicsTripDetailsTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceTelematicsTripHistoryTrackable;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLogConstants;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceTelematicsScoreCardDisplayedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceTelematicsStreakCardDisplayedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceTelematicsTripCardDisplayedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceDriveEasyButtonSelectedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AcePreviousPageRenderedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTelematicsReportCardScoreDisplayedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTelematicsTripDetailsPageDisplayedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTelematicsTripDetailsTripTypeUpdatedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.telematics.AceTelematicsUseCase;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoappmodel.enums.telematics.AceBaseTelematicsFeatureModeVisitor;
import com.geico.mobile.android.ace.geicoappmodel.enums.telematics.AceTelematicsFeatureMode;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.telematics.AceTelematicsGetRelatedDriversRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.telematics.AceTelematicsRetrieveDriverDataRequestFactory;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceTelematicsBasicSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceTelematicsTripDetailsPageDisplayedSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceTelematicsTripHistoryPageDisplayedSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceTelematicsTripTypeUpdatedSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsModelLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsTripDetailsModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsDriver;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsRelatedDriver;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsTrip;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsTrip.TelematicsOperatorMode;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsTripDingDetails.TelematicsDingType;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsGetRelatedDriversRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsGetRelatedDriversResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsGetTripDetailsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsGetTripDetailsResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsRetrieveDriverDataRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsTripCorrectionsRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsTripCorrectionsResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.TELEMATICS_GET_RELATED_DRIVERS;
import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.TELEMATICS_GET_TRIP_DETAILS;
import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.TELEMATICS_RETRIEVE_DRIVER_DATA;
import static com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceTaskRequestType.TELEMATICS_TRIP_CORRECTIONS;
import static com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants.ACTION_ROADSIDE_ASSISTANCE_MAIN;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_DRIVE_EASY_CONTINUE_TO_THE_APP;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_DRIVE_EASY_NO_TRIPS;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_TELEMATICS_OPENED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_CONTINUE_TO_THE_APP;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_NO_TRIPS;

/**
 * Object that provides data required to display the telematics page.
 *
 * @author Nick Emerson, GEICO
 */
public class AceTelematicsRepository extends AceTierRepository<AceTelematicsModel> implements AceTelematicsUseCase {

    private final MutableLiveData<LatLng> lastTripLocation = new MutableLiveData<>();
    @EventType
    @NonNull
    private String eventType = EventType.DEFAULT;
    private AceTelematicsMapHandler mapHandler;

    public AceTelematicsRepository(@NonNull Application application) {
        super(application);
    }

    private List<AceTelematicsRelatedDriver> considerFilteringDrivers(@NonNull List<AceTelematicsRelatedDriver> relatedDrivers) {
        return getSessionController().getUserRole().isPolicyHolder() ? relatedDrivers : selectCurrentDriverFrom(relatedDrivers);
    }

    @Override
    public void considerGettingRelatedDrivers() {
        getTelematicsFlow().acceptRelatedDriversInformationStateVisitor(new AceBaseInformationStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                markAsWaiting();
                getTelematicsFlow().setRelatedDriversInformationState(AceInformationState.REQUESTED);
                postRequest(createGetRelatedDriversRequest(),
                        MitTelematicsGetRelatedDriversResponse.class,
                        AceTaskRequestType.TELEMATICS_GET_RELATED_DRIVERS);
                return NOTHING;
            }

            @Override
            public Void visitCurrent(Void input) {
                showFamilyReportCardWith(getTelematicsFlow().getRelatedDrivers());
                return NOTHING;
            }
        });
    }

    public void considerGettingTripDetails(int selectedTripIndex) {
        ((AceTelematicsModelLiveData) getModelLiveData()).setSelectedTrip(selectedTripIndex);
        AceTelematicsTrip selectedTrip = getSelectedTrip();
        if (selectedTrip.getRoutePoints().isEmpty()) {
            getTripDetails(selectedTrip.getTripId());
            return;
        }
        emitTripDetailsNavigation();
    }

    private void considerLoggingScoreDisplayed(AceTelematicsRelatedDriver driver) {
        if (!driver.hasScore()) return;
        logEvent(new AceTelematicsReportCardScoreDisplayedEvent(driver.getGeicoDriverId(), driver.getRoundedScore()));
    }

    public void considerNavigatingToRoadsideHelp() {
        acceptVisitor(new AceBaseConnectionStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void callback) {
                openDialog(AceConstantState.DialogTag.NETWORK_UNAVAILABLE);
                return NOTHING;
            }

            @Override
            public Void visitConnected(Void callback) {
                startNonPolicyAction(ACTION_ROADSIDE_ASSISTANCE_MAIN);
                return NOTHING;
            }
        });
    }

    private void considerPostingLastTripCoordinates(AceTelematicsTrip trip) {
        if (!trip.isValid()) {
            trackAction(ANALYTICS_DRIVE_EASY_NO_TRIPS, DRIVE_EASY_NO_TRIPS);
            return;
        }
        lastTripLocation.postValue(new LatLng(trip.getStartLatitude(), trip.getStartLongitude()));
    }

    @Override
    public void considerRetrievingDriverData() {
        getFeatureMode().acceptVisitor(new AceBaseTelematicsFeatureModeVisitor<Void, Void>() {
            @Override
            protected Void visitAny(Void input) {
                considerRetrievingDriverDataByInformationState();
                return NOTHING;
            }

            @Override
            public Void visitDisabled(Void input) {
                emitTelematicsDisabledNavigation();
                return NOTHING;
            }
        });
    }

    private void considerRetrievingDriverDataByInformationState() {
        getTelematicsFlow().acceptDriverInformationStateVisitor(new AceBaseInformationStateVisitor<Void, Void>() {
            @Override
            protected Void visitAnyState(Void input) {
                postRequest(createRetrieveDriverDataRequest(),
                        AceTelematicsDriver.class,
                        TELEMATICS_RETRIEVE_DRIVER_DATA);
                return NOTHING;
            }

            @Override
            public Void visitCurrent(Void input) {
                onRetrieveDriverDataSuccess(getTelematicsFlow().getDriver());
                return NOTHING;
            }
        });
    }

    public void considerTrackingPageMetrics() {
        String viewTag = getModel().viewTag;
        if (viewTag.equals(getLastPageRendered())) return;
        switch (viewTag) {
            case ViewTag.TELEMATICS_DASHBOARD:
                trackPageShown(new AceTelematicsDashboardTrackable(getAnalyticsFacade()));
                break;
            case ViewTag.TELEMATICS_REPORT_CARD:
                trackPageShown(new AceTelematicsReportCardTrackable(getAnalyticsFacade()));
                logEvent(new AceTelematicsBasicSplunkLoggingContext(TELEMATICS_REPORT_CARD_PAGE_DISPLAYED));
                break;
            case ViewTag.TELEMATICS_SCORE_DETAILS:
                trackPageShown(new AceTelematicsScoreDetailsTrackable(getAnalyticsFacade()));
                logEvent(new AceTelematicsBasicSplunkLoggingContext(TELEMATICS_SCORE_DETAILS_PAGE_DISPLAYED));
                break;
            case ViewTag.TELEMATICS_TRIP_DETAILS:
                trackPageShown(new AceTelematicsTripDetailsTrackable(getAnalyticsFacade()));
                logEvent(new AceTelematicsTripDetailsPageDisplayedEvent(getSelectedTrip()));
                logEvent(new AceTelematicsTripDetailsPageDisplayedSplunkLoggingContext(getSelectedTrip().getOperatorMode(), getLastPageRendered()));
                break;
            case ViewTag.TELEMATICS_TRIP_HISTORY:
                trackPageShown(new AceTelematicsTripHistoryTrackable(getAnalyticsFacade()));
                logEvent(new AcePreviousPageRenderedEvent(TELEMATICS_TRIP_HISTORY_PAGE_DISPLAYED));
                logEvent(new AceTelematicsTripHistoryPageDisplayedSplunkLoggingContext(getLastPageRendered()));
                break;
            default:
                trackPageShown(new AceTelematicsTrackable(getAnalyticsFacade()));
        }
    }

    private MitTelematicsGetRelatedDriversRequest createGetRelatedDriversRequest() {
        return new AceTelematicsGetRelatedDriversRequestFactory(getSessionController()).create();
    }

    @NonNull
    @Override
    protected AceTelematicsModelLiveData createModelLiveData() {
        return new AceTelematicsModelLiveData(new AceTelematicsModel());
    }

    private MitTelematicsRetrieveDriverDataRequest createRetrieveDriverDataRequest() {
        return new AceTelematicsRetrieveDriverDataRequestFactory(getSessionController()).create();
    }

    private MitTelematicsTripCorrectionsRequest createTelematicsTripCorrectionsRequest() {
        return new AceTelematicsTripCorrectionsRequestFactory(getSessionController()).create(getModel());
    }

    private MitTelematicsGetTripDetailsRequest createTripDetailsRequest(String tripId) {
        return new AceTelematicsGetTripDetailsRequestFactory(getSessionController()).create(tripId);
    }

    private void determineScoreCardAttributes() {
        new AceDetermineTelematicsScoreCardAttributes(getModel(), getApplication().getResources()).execute();
    }

    private void determineStreakCardAttributes() {
        new AceDetermineTelematicsStreakCardAttributes(getApplication().getResources()).executeWith(getModel());
    }

    private void emitTelematicsDisabledNavigation() {
        emitNavigation(NavigateAction.TELEMATICS_DASHBOARD_DISABLED);
        logPageVariantEvent(EventType.DEFAULT);
    }

    private void emitTripDetailsNavigation() {
        emitNavigation(NavigateAction.TELEMATICS_TRIP_DETAILS);
    }

    private String getCurrentUserDriverId() {
        return getTelematicsPreferences().getDriverId();
    }

    private AceTelematicsFeatureMode getFeatureMode() {
        return getFeatureConfiguration().getModeForTelematics();
    }

    @NonNull
    public LiveData<LatLng> getLastTripLocation() {
        return lastTripLocation;
    }

    private AceTelematicsTrip getSelectedTrip() {
        return getModel().getDriver().getSelectedTrip();
    }

    @Override
    public void getTripDetails(@NonNull String tripId) {
        markAsWaiting();
        postRequest(createTripDetailsRequest(tripId), MitTelematicsGetTripDetailsResponse.class, TELEMATICS_GET_TRIP_DETAILS);
    }

    private AceTelematicsTripDetailsModel getTripDetailsModel() {
        return getModel().getTripDetailsModel();
    }

    private int getTripIndexFrom(String tripId) {
        return AceBasicEnumerator.DEFAULT.indexOfFirstMatch(getTrips(), trip -> trip.getTripId().equals(tripId));
    }

    private List<AceTelematicsTrip> getTrips() {
        return getModel().getDriver().getTrips();
    }

    public void handleUpdatedDingType(@NonNull @TelematicsDingType String updatedSelection) {
        getTripDetailsModel().setSelectedDingType(updatedSelection);
        if (isTripDetailsMapReady()) mapHandler.reactToSelection(updatedSelection);
    }

    public void initializeMap(@NonNull GoogleMap map) {
        mapHandler = new AceBasicTelematicsMapHandler(map, getApplication().getResources(), getSelectedTrip(), this);
    }

    public boolean isTripDetailsMapReady() {
        return mapHandler != null;
    }

    @Override
    public void launchDriveEasy() {
        openUri(MitWebLinkNames.DRIVE_EASY_CONTINUE_TO_THE_APP);
        trackAction(ANALYTICS_TELEMATICS_OPENED);
        trackAction(ANALYTICS_DRIVE_EASY_CONTINUE_TO_THE_APP, DRIVE_EASY_CONTINUE_TO_THE_APP);
        logEvent(new AceDriveEasyButtonSelectedEvent(eventType));
    }

    private void logPageVariantEvent(@EventType @NonNull String eventType) {
        logEvent(eventType.equals(EventType.DASHBOARD_ENABLED) ?
                AceEventLogConstants.TELEMATICS_DASHBOARD_DISPLAYED :
                eventType);
        this.eventType = eventType;
    }

    private void logReportCardScoresDisplayed(List<AceTelematicsRelatedDriver> relatedDrivers) {
        AceBasicEnumerator.DEFAULT.reactToEach(relatedDrivers, this::considerLoggingScoreDisplayed);
    }

    @Override
    public void onGetRelatedDriversFailure(@NonNull MitResponse response) {
        emitTelematicsDisabledNavigation();
        markAsServiceError();
        getTelematicsFlow().setRelatedDriversInformationState(AceInformationState.UNAVAILABLE);
        getTelematicsFlow().setRelatedDrivers(new ArrayList<>());
    }

    @Override
    public void onGetRelatedDriversSuccess(@NonNull MitTelematicsGetRelatedDriversResponse response) {
        getTelematicsFlow().setRelatedDriversInformationState(AceInformationState.CURRENT);
        getTelematicsFlow().setRelatedDrivers(response.getRelatedDrivers());
        showFamilyReportCardWith(response.getRelatedDrivers());
    }

    @Override
    public void onGetTripDetailsFailure(@NonNull MitResponse response) {
        emitTelematicsDisabledNavigation();
        markAsServiceError();
    }

    @Override
    public void onGetTripDetailsSuccess(@NonNull AceTelematicsTrip detailedTrip) {
        getTrips().set(getTripIndexFrom(detailedTrip.getTripId()), detailedTrip);
        updateFlowDriverFromModel();
        markAsNotWaiting();
        emitTripDetailsNavigation();
    }

    @Override
    public <O extends MitResponse> void onNotAuthorized(@NonNull O response) {
        handleSessionExpired(response);
    }

    @Override
    public void onRetrieveDriverDataFailure(@NonNull MitResponse response) {
        emitTelematicsDisabledNavigation();
    }

    @Override
    public void onRetrieveDriverDataSuccess(@NonNull AceTelematicsDriver driver) {
        getModel().setDriver(driver);
        getModel().setShouldShowLogInForMoreOptions(!isUserLoggedIn());
        determineScoreCardAttributes();
        determineStreakCardAttributes();
        sortTripsByStartTime();
        AceTelematicsTrip lastTrip = driver.getLastTrip();
        considerPostingLastTripCoordinates(lastTrip);
        showBottomNavigationWith(R.id.dashboardBottomNavigationItem);
        emitNavigation(NavigateAction.TELEMATICS_DASHBOARD);
        logEvent(new AceTelematicsTripCardDisplayedEvent(lastTrip));
        logPageVariantEvent(EventType.DASHBOARD_ENABLED);
        logEvent(new AceTelematicsScoreCardDisplayedEvent(driver.getScoreDetails()));
        logEvent(new AceTelematicsStreakCardDisplayedEvent(driver.getStreakDetails()));
    }

    private boolean isUserLoggedIn() {
        return getRegistry().getSessionController().acceptVisitor(new AceBaseSessionStateVisitor<Void, Boolean>() {
            @Override
            protected Boolean visitAnyState(Void input) {
                return false;
            }

            @Override
            public Boolean visitInPolicySession(Void input) {
                return true;
            }
        });
    }

    @Override
    public void onTripCorrectionFailure(@NonNull MitResponse response) {
        getTripDetailsModel().setSelectedOperatorMode(getModel().getDriver().getSelectedTrip().getOperatorMode());
        emitTelematicsDisabledNavigation();
        markAsServiceError();
        getTripDetailsModel().setSelectedOperatorMode(getSelectedTrip().getOperatorMode());
    }

    @Override
    public void onTripCorrectionSuccess(@NonNull AceResponse<MitTelematicsTripCorrectionsResponse> response) {
        @TelematicsOperatorMode String operatorMode = Objects.requireNonNull(getTripDetailsModel().getSelectedOperatorMode().get());
        logEvent(new AceTelematicsTripDetailsTripTypeUpdatedEvent(operatorMode));
        logEvent(new AceTelematicsTripTypeUpdatedSplunkLoggingContext(operatorMode));
        getSelectedTrip().getCorrections().setOperatorMode(operatorMode);
        updateFlowDriverFromModel();
        handleUpdatedDingType(TelematicsDingType.UNSET);
    }

    @Override
    public void openDialog(@NonNull @AceConstantState.DialogTag String dialogTag) {
        if (AceConstantState.DialogTag.NETWORK_UNAVAILABLE.equals(dialogTag)) {
            getStateEmitter().emitNetworkDialogVisibility(Visibility.VISIBLE);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void reactToTaskResponse(AceResponse<?> response) {
        switch (response.requestType) {
            case TELEMATICS_GET_RELATED_DRIVERS:
                new AceTelematicsGetRelatedDriversServiceReaction(this)
                        .reactTo((AceResponse<MitTelematicsGetRelatedDriversResponse>) response);
                break;
            case TELEMATICS_GET_TRIP_DETAILS:
                new AceTelematicsGetTripDetailsServiceReaction(this)
                        .reactTo((AceResponse<MitTelematicsGetTripDetailsResponse>) response);
                break;
            case TELEMATICS_RETRIEVE_DRIVER_DATA:
                new AceTelematicsRetrieveDriverDataServiceReaction(this)
                        .reactTo((AceResponse<AceTelematicsDriver>) response);
                break;
            case TELEMATICS_TRIP_CORRECTIONS:
                new AceTelematicsTripCorrectionsServiceReaction(this)
                        .reactTo((AceResponse<MitTelematicsTripCorrectionsResponse>) response);
                break;
            default:
                break;
        }
    }

    private List<AceTelematicsRelatedDriver> selectCurrentDriverFrom(List<AceTelematicsRelatedDriver> relatedDrivers) {
        AceTelematicsRelatedDriver driver = AceBasicEnumerator.DEFAULT.firstMatch(relatedDrivers, relatedDriver
                -> getCurrentUserDriverId().equals(relatedDriver.getGeicoDriverId()), new AceTelematicsRelatedDriver());
        return Collections.singletonList(driver);
    }

    private void showFamilyReportCardWith(List<AceTelematicsRelatedDriver> relatedDrivers) {
        getModel().setRelatedDrivers(considerFilteringDrivers(relatedDrivers));
        showBottomNavigationWith(R.id.dashboardBottomNavigationItem);
        emitNavigation(NavigateAction.TELEMATICS_FAMILY_REPORT_CARD);
        logEvent(TELEMATICS_REPORT_CARD_PAGE_DISPLAYED);
        logReportCardScoresDisplayed(relatedDrivers);
    }

    /**
     * Sorts from most to least recent.
     */
    private void sortTripsByStartTime() {
        Collections.sort(getTrips(), (trip1, trip2) -> trip2.getStartTime().compareTo(trip1.getStartTime()));
    }

    @Override
    public void submitTripCorrection() {
        markAsWaiting();
        postRequest(createTelematicsTripCorrectionsRequest(),
                MitTelematicsTripCorrectionsResponse.class,
                AceTaskRequestType.TELEMATICS_TRIP_CORRECTIONS);
    }

    private void updateFlowDriverFromModel() {
        getTelematicsFlow().setDriver(getModel().getDriver());
    }

    @StringDef(value = {EventType.DASHBOARD_ENABLED, EventType.DEFAULT})
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    private @interface EventType {

        String DASHBOARD_ENABLED = TELEMATICS_DASHBOARD;
        String DEFAULT = TELEMATICS_PAGE;
    }
}