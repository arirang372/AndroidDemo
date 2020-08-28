package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback;

/**
 * API defining user interactions with the Telematics dashboard.
 *
 * @author Austin Morgan
 */
public interface AceTelematicsDashboardCallback extends AceDriveEasyCallback, AceTelematicsEventCountDisplayCallback {

	void onAllTripsClick();

	void onFamilyReportCardClick();

	void onGetIdCardsClick();

	void onGetRoadsideHelpClick();

	void onLastTripDetailsClick();

	void onScoreDetailsClick();

	void onStreakLearnMoreClick();

}