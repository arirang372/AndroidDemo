package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.viewmodel;

import android.app.Application;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.logging.AceSplunkLoggingConstants;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceLifecycleObserverFactory;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTelematicsTripEventTypeSelectedEvent;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceTelematicsTripSelectedEvent;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.idcards.AceSavedIdCardsActivity;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceTelematicsButtonSelectedSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoapppresentation.logging.AceTelematicsDashboardDisplayedSplunkLoggingContext;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsModelLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsScoreDetailsModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.AceTelematicsTripDetailsModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.repository.AceTelematicsRepository;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsTrip;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsTrip.TelematicsOperatorMode;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsTripDingDetails.TelematicsDingType;
import com.geico.mobile.databinding.TelematicsTripDetailsFragmentBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_DRAWER_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_LEARN_MORE;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_PANE_TOGGLE_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_SCORE_CHANGE_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_TRIP_DETAIL_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_TRIP_EDIT_COMPLETED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_TRIP_EDIT_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_TRIP_EVENT_SELECTED;
import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.DRIVE_EASY_TRIP_HISTORY_SELECTED;

/**
 * Provides data for and invokes the business use cases of the telematics page.
 *
 * @author Nick Emerson, GEICO
 */
public class AceTelematicsViewModel extends AceBaseViewModel<AceTelematicsModel, AceTelematicsRepository>
        implements AceLifecycleObserverFactory<AceTelematicsLifecycleObserver> {

    public AceTelematicsViewModel(@NonNull Application application) {
        super(application);
        getRepository().considerRetrievingDriverData();
    }

    private void adjustMapViewPadding(GoogleMap map, TelematicsTripDetailsFragmentBinding viewDataBinding) {
        Rect mapViewBounds = new Rect();
        MapView mapView = viewDataBinding.mapView;
        mapView.getDrawingRect(mapViewBounds);
        ViewGroup viewGroup = (ViewGroup) viewDataBinding.getRoot();
        viewGroup.offsetDescendantRectToMyCoords(mapView, mapViewBounds);
        Rect cardViewBounds = new Rect();
        viewDataBinding.driverCard.getDrawingRect(cardViewBounds);
        viewGroup.offsetDescendantRectToMyCoords(viewDataBinding.driverCard, cardViewBounds);
        int startPadding = viewGroup.getResources().getDimensionPixelSize(R.dimen.large_margin);
        map.setPadding(startPadding, 0, 0, mapViewBounds.bottom - cardViewBounds.top);
    }

    private void considerLoggingEventTypeSelected(@TelematicsDingType @NonNull String eventType, String updatedSelection) {
        if (TelematicsDingType.UNSET.equals(updatedSelection)) return;
        logEvent(new AceTelematicsTripEventTypeSelectedEvent(TELEMATICS_TRIP_DETAILS_EVENT_TYPE_SELECTED, eventType));
        trackAction(ANALYTICS_DRIVE_EASY_TRIP_EVENT_SELECTED, DRIVE_EASY_TRIP_EVENT_SELECTED);
    }

    public void considerTrackingPageMetrics() {
        getRepository().considerTrackingPageMetrics();
    }

    @NonNull
    @Override
    public AceTelematicsLifecycleObserver createLifecycleObserver() {
        return new AceTelematicsLifecycleObserver(this);
    }

    @NonNull
    @Override
    protected AceTelematicsRepository createRepository(@NonNull Application application) {
        return new AceTelematicsRepository(application);
    }

    private void disableMapToolbar(@NonNull GoogleMap map) {
        map.getUiSettings().setMapToolbarEnabled(false);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public AceTelematicsModelLiveData getModelLiveData() {
        return (AceTelematicsModelLiveData) super.getModelLiveData();
    }

    private AceTelematicsScoreDetailsModel getScoreDetailsModel() {
        return getModel().getScoreDetailsModel();
    }

    private AceTelematicsTrip getSelectedTrip() {
        return getModel().getDriver().getSelectedTrip();
    }

    private AceTelematicsTripDetailsModel getTripDetailsModel() {
        return getModel().getTripDetailsModel();
    }

    private void initializeMapView(MapView mapView, OnMapReadyCallback callback, Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(callback);
    }

    private boolean isCurrentUserNotDriverOfSelectedTrip() {
        return !TelematicsOperatorMode.DRIVER.equals(getSelectedTrip().getOperatorMode());
    }

    private void observeLastTripCardLocation(GoogleMap map, LifecycleOwner owner) {
        getRepository().getLastTripLocation().observe(owner, latLng -> setLastTripCardMarkers(map, latLng));
    }

    public void onAllTripsClick() {
        emitNavigation(NavigateAction.TELEMATICS_TRIP_HISTORY);
        trackAction(ANALYTICS_DRIVE_EASY_TRIP_HISTORY_SELECTED, DRIVE_EASY_TRIP_HISTORY_SELECTED);
    }

    public void onContinueToAppClick() {
        getRepository().launchDriveEasy();
    }

    public void onOutageSectionItemClick(@NonNull AceTelematicsOutageSectionItem sectionItem) {
        getRepository().navigateToOutageSectionDestination(sectionItem);
    }

    public void onDashboardCreated(@NonNull MapView mapView, @NonNull OnMapReadyCallback callback, @Nullable Bundle savedInstanceState) {
        initializeMapView(mapView, callback, savedInstanceState);
        mapView.setClickable(false);
        logEvent(new AceTelematicsDashboardDisplayedSplunkLoggingContext(getSelectedTrip().getTripId()));
    }

    public void onDashboardMapReady(@NonNull GoogleMap map, @NonNull LifecycleOwner owner) {
        disableMapToolbar(map);
        map.getUiSettings().setAllGesturesEnabled(false);
        observeLastTripCardLocation(map, owner);
    }

    public void onEventTypeClick(@NonNull @TelematicsDingType String eventType) {
        if (!getRepository().isTripDetailsMapReady() || isCurrentUserNotDriverOfSelectedTrip())
            return;
        String currentSelection = getTripDetailsModel().getSelectedDingType().get();
        String updatedSelection = shouldMarkEventTypeSelected(currentSelection, eventType) ?
                eventType : TelematicsDingType.UNSET;
        if (updatedSelection.equals(currentSelection)) return;
        considerLoggingEventTypeSelected(eventType, updatedSelection);
        getRepository().handleUpdatedDingType(updatedSelection);
    }

    public void onFamilyReportCardClick() {
        getRepository().considerGettingRelatedDrivers();
        trackAction(ANALYTICS_DRIVE_EASY_DRAWER_SELECTED, DRIVE_EASY_DRAWER_SELECTED);
        logEvent(TELEMATICS_REPORT_CARD_PAGE_SELECTED);
    }

    public void onGetIdCardsClick() {
        trackAction(ANALYTICS_DRIVE_EASY_DRAWER_SELECTED, DRIVE_EASY_DRAWER_SELECTED);
        logEvent(new AceTelematicsButtonSelectedSplunkLoggingContext(AceSplunkLoggingConstants.TELEMATICS_LOG_IN_FOR_MORE_SELECTED, "ID_CARDS"));
        startActivity(AceSavedIdCardsActivity.class);
    }

    public void onGetRoadsideHelpClick() {
        trackAction(ANALYTICS_DRIVE_EASY_DRAWER_SELECTED, DRIVE_EASY_DRAWER_SELECTED);
        logEvent(new AceTelematicsButtonSelectedSplunkLoggingContext(AceSplunkLoggingConstants.TELEMATICS_LOG_IN_FOR_MORE_SELECTED, "ROADSIDE_ASSISTANCE"));
        getRepository().considerNavigatingToRoadsideHelp();
    }

    public void onLastTripDetailsClick() {
        getModelLiveData().setSelectedTrip(0);
        emitNavigation(NavigateAction.TELEMATICS_TRIP_DETAILS);
        trackAction(ANALYTICS_DRIVE_EASY_TRIP_DETAIL_SELECTED, DRIVE_EASY_TRIP_DETAIL_SELECTED);
    }

    public void onMyDrivingFactorsClick() {
        getScoreDetailsModel().setNonDrivingFactorsScoreDetailsSelected(false);
        trackAction(ANALYTICS_DRIVE_EASY_SCORE_PANE_TOGGLE_SELECTED, DRIVE_EASY_PANE_TOGGLE_SELECTED);
    }

    public void onNonDrivingFactorsClick() {
        getScoreDetailsModel().setNonDrivingFactorsScoreDetailsSelected(true);
        trackAction(ANALYTICS_DRIVE_EASY_SCORE_PANE_TOGGLE_SELECTED, DRIVE_EASY_PANE_TOGGLE_SELECTED);
    }

    public void onOperatorModeSelect() {
        trackAction(ANALYTICS_DRIVE_EASY_TRIP_EDIT_COMPLETED, DRIVE_EASY_TRIP_EDIT_COMPLETED);
        getRepository().submitTripCorrection();
    }

    public void onOperatorMoreDropdownClick() {
        trackAction(ANALYTICS_DRIVE_EASY_TRIP_EDIT_SELECTED, DRIVE_EASY_TRIP_EDIT_SELECTED);
    }

    public void onScoreDetailsClick() {
        emitNavigation(NavigateAction.TELEMATICS_SCORE_DETAILS);
        trackAction(ANALYTICS_DRIVE_EASY_LEARN_MORE, DRIVE_EASY_LEARN_MORE);
        logEvent(TELEMATICS_SCORE_BUTTON_SELECTED);
        logEvent(TELEMATICS_SCORE_DETAILS_PAGE_DISPLAYED);
        logEvent(new AceTelematicsButtonSelectedSplunkLoggingContext(TELEMATICS_SCORE_BUTTON_SELECTED, "ScoreDetails"));
    }

    public void onTripClick(int selectedTripIndex) {
        trackAction(ANALYTICS_DRIVE_EASY_TRIP_DETAIL_SELECTED, DRIVE_EASY_TRIP_DETAIL_SELECTED);
        getRepository().considerGettingTripDetails(selectedTripIndex);
        logEvent(new AceTelematicsTripSelectedEvent(getSelectedTrip().getTripId()));
    }

    public void onTripDetailsCreated(MapView mapView, OnMapReadyCallback callback, Bundle savedInstanceState) {
        getTripDetailsModel().setSelectedDingType(TelematicsDingType.UNSET);
        initializeMapView(mapView, callback, savedInstanceState);
    }

    public void onTripDetailsMapReady(@NonNull GoogleMap map, @NonNull TelematicsTripDetailsFragmentBinding viewDataBinding) {
        adjustMapViewPadding(map, viewDataBinding);
        disableMapToolbar(map);
        getRepository().initializeMap(map);
    }

    public void onWhyDidMyScoreChangeClick() {
        openFullSite(MitWebLinkNames.TELEMATICS_FAQ);
        logEvent(TELEMATICS_SCORE_CHANGE_LINK_SELECTED);
        trackAction(ANALYTICS_DRIVE_EASY_SCORE_CHANGE_SELECTED, DRIVE_EASY_SCORE_CHANGE_SELECTED);
    }

    private boolean selectedTripHasDingsOfType(@NonNull @TelematicsDingType String eventType) {
        return getSelectedTrip().getDingDetails() != null && getSelectedTrip().getDingDetails().getCount(eventType) > 0;
    }

    void setLastTripCardMarkers(GoogleMap map, LatLng startPosition) {
        map.addMarker(new MarkerOptions().position(startPosition));
        map.moveCamera(CameraUpdateFactory.newLatLng(startPosition));
    }

    private boolean shouldMarkEventTypeSelected(@TelematicsDingType String currentSelection, @TelematicsDingType String eventType) {
        return selectedTripHasDingsOfType(eventType) && !eventType.equals(currentSelection);
    }

}