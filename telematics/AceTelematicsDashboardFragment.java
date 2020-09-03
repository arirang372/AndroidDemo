package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceBasicDialogMonitor;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback.AceTelematicsDashboardCallback;
import com.geico.mobile.databinding.TelematicsDashboardFragmentBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * UI component which displays an overview of a customer's Telematics policy data with links to subpages.
 *
 * @author Austin Morgan
 */
public class AceTelematicsDashboardFragment extends AceBaseTelematicsFragment<TelematicsDashboardFragmentBinding>
        implements AceTelematicsDashboardCallback, OnMapReadyCallback {

    @Override
    protected AceDialogMonitor createDialogMonitorFrom(@NonNull FragmentManager fragmentManager) {
        return new AceBasicDialogMonitor(fragmentManager);
    }

    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.driveEasy;
    }

    @LayoutRes
    @Override
    protected int getLayoutResourceId() {
        return R.layout.telematics_dashboard_fragment;
    }

    @Override
    protected String getViewTag() {
        return ViewTag.TELEMATICS_DASHBOARD;
    }

    @Override
    public void onAllTripsClick() {
        getViewModel().onAllTripsClick();
    }

    @Override
    public void onContinueToAppClick() {
        getViewModel().onContinueToAppClick();
    }

    @Override
    public void onFamilyReportCardClick() {
        getViewModel().onFamilyReportCardClick();
    }

//    @Override
//    public void onGetIdCardsClick() {
//        getViewModel().onGetIdCardsClick();
//    }
//
//    @Override
//    public void onGetRoadsideHelpClick() {
//        getViewModel().onGetRoadsideHelpClick();
//    }

    @Override
    public void onLastTripDetailsClick() {
        getViewModel().onLastTripDetailsClick();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        getViewModel().onDashboardMapReady(map, getViewLifecycleOwner());
    }

    @Override
    public void onScoreDetailsClick() {
        getViewModel().onScoreDetailsClick();
    }

    @Override
    public void onStreakLearnMoreClick() {
        getViewModel().onStreakLearnMoreClick();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getViewModel().onDashboardCreated(getViewDataBinding().lastTripCard.mapView, this, savedInstanceState);
    }

}