package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.navigation;

import java.util.HashMap;
import java.util.Map;

import com.geico.mobile.TelematicsNavigationGraphDirections;
import com.geico.mobile.TelematicsRegistrationNavigationGraphDirections;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoapppresentation.activateaccount.mvvm.view.AceActivateAccountConfirmFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.billing.mvvm.view.AceBillingOverviewFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.billing.mvvm.view.AceBillingPaymentHistoryDetailsFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.billing.mvvm.view.AceBillingPaymentHistoryFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view.AcePolicyDashboardFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.registration.mvvm.view.AceTelematicsInviteDriversFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.view.AceWeatherAlertDetailsServiceErrorFragmentDirection;

import androidx.navigation.NavDirections;

/**
 * A mapping that keeps track of the navigation action ids represented
 * by the NavigationAction annotated type of emitted state change.
 *
 * @author Kal Tadesse & Nick Emerson
 */
class AceNavigationStateToAction {

	static final Map<@NavigateAction String, NavDirections> MAPPING = createMapping();

	private static NavDirections createActivateAccountCreateAction() {
		return AceActivateAccountConfirmFragmentDirections.activateAccountConfirmFragmentToActivateAccountCreateFragment();
	}

	private static NavDirections createCancelPaymentThankYouAction() {
		return AceBillingPaymentHistoryDetailsFragmentDirections.actionAceBillingPaymentHistoryDetailsFragmentToAceBillingCancelPaymentThankYouFragment();
	}

	private static NavDirections createCoverageAction() {
		return AcePolicyDashboardFragmentDirections.policyDashboardFragmentToCoverageFragment();
	}

	private static NavDirections createDiscountAction() {
		return AcePolicyDashboardFragmentDirections.policyDashboardFragmentToDiscountFragment();
	}

	private static NavDirections createDriverDetailAction() {
		return AcePolicyDashboardFragmentDirections.policyDashboardFragmentToDriverDetailFragment();
	}

	private static NavDirections createEditAccountInfoAction() {
		return AcePolicyDashboardFragmentDirections.policyDashboardFragmentToEditAccountInfoFragment();
	}

	private static Map<@NavigateAction String, NavDirections> createMapping() {
		Map<@NavigateAction String, NavDirections> mapping = new HashMap<>();
		mapping.put(NavigateAction.ACTIVATE_ACCOUNT_CREATE_FRAGMENT, createActivateAccountCreateAction());
		mapping.put(NavigateAction.BILLING_PAYMENT_PLANS_FRAGMENT, createPaymentPlansAction());
		mapping.put(NavigateAction.BILLING_PAYMENT_HISTORY_DETAILS_FRAGMENT, createPaymentHistoryDetailsAction());
		mapping.put(NavigateAction.BILLING_CANCEL_PAYMENT_THANK_YOU_FRAGMENT, createCancelPaymentThankYouAction());
		mapping.put(NavigateAction.COVERAGE_FRAGMENT, createCoverageAction());
		mapping.put(NavigateAction.DISCOUNT_FRAGMENT, createDiscountAction());
		mapping.put(NavigateAction.DRIVER_DETAIL_FRAGMENT, createDriverDetailAction());
		mapping.put(NavigateAction.EDIT_ACCOUNT_INFO_FRAGMENT, createEditAccountInfoAction());
		mapping.put(NavigateAction.TELEMATICS_AUTHORIZATION, createTelematicsAuthorizationAction());
		mapping.put(NavigateAction.TELEMATICS_CONFIRMATION, createTelematicsConfirmationAction());
		mapping.put(NavigateAction.TELEMATICS_DASHBOARD, createTelematicsDashboardAction());
		mapping.put(NavigateAction.TELEMATICS_DASHBOARD_DISABLED, createTelematicsDashboardDisabledAction());
		mapping.put(NavigateAction.TELEMATICS_FAMILY_REPORT_CARD, createTelematicsFamilyReportCardAction());
		mapping.put(NavigateAction.TELEMATICS_INVITE_DRIVERS, createTelematicsInviteDriversAction());
		mapping.put(NavigateAction.TELEMATICS_INVITE_DRIVERS_PHONE_ENTRY, createTelematicsInviteDriversPhoneEntryAction());
		mapping.put(NavigateAction.TELEMATICS_OUTAGE, createTelematicsOutageAction());
		mapping.put(NavigateAction.TELEMATICS_PERMISSIONS, createTelematicsPermissionsAction());
		mapping.put(NavigateAction.TELEMATICS_PUSH_ENROLLMENT, createTelematicsPushEnrollmentAction());
		mapping.put(NavigateAction.TELEMATICS_SCORE_DETAILS, createTelematicsScoreDetailsAction());
		mapping.put(NavigateAction.TELEMATICS_TRIP_DETAILS, createTelematicsTripDetailsAction());
		mapping.put(NavigateAction.TELEMATICS_TRIP_HISTORY, createTelematicsTripHistoryAction());
		mapping.put(NavigateAction.VEHICLE_DETAIL_FRAGMENT, createVehicleDetailAction());
		mapping.put(NavigateAction.WEATHER_ALERT_DETAILS_SERVICE_ERROR_FRAGMENT, createWeatherAlertDetailsServiceErrorAction());
		return mapping;
	}

	private static NavDirections createPaymentHistoryDetailsAction() {
		return AceBillingPaymentHistoryFragmentDirections.actionAceBillingPaymentHistoryFragmentToAceBillingPaymentHistoryDetailsFragment();
	}

	private static NavDirections createPaymentPlansAction() {
		return AceBillingOverviewFragmentDirections.actionAceBillingOverviewFragmentToAceBillingPaymentPlansFragment();
	}

	private static NavDirections createTelematicsAuthorizationAction() {
		return TelematicsRegistrationNavigationGraphDirections.telematicsAuthorizationFragmentNavigation();
	}

	private static NavDirections createTelematicsConfirmationAction() {
		return TelematicsRegistrationNavigationGraphDirections.telematicsConfirmationFragmentNavigation();
	}

	private static NavDirections createTelematicsDashboardAction() {
		return TelematicsNavigationGraphDirections.telematicsDashboardFragmentNavigation();
	}

	private static NavDirections createTelematicsDashboardDisabledAction() {
		return TelematicsNavigationGraphDirections.telematicsDisabledFragmentNavigation();
	}

	private static NavDirections createTelematicsFamilyReportCardAction() {
		return TelematicsNavigationGraphDirections.telematicsFamilyReportCardNavigation();
	}

	private static NavDirections createTelematicsInviteDriversAction() {
		return TelematicsRegistrationNavigationGraphDirections.telematicsInviteDriversFragmentNavigation();
	}

	private static NavDirections createTelematicsInviteDriversPhoneEntryAction() {
		return AceTelematicsInviteDriversFragmentDirections.telematicsInviteDriversPhoneEntryFragmentNavigation();
	}

	private static NavDirections createTelematicsPermissionsAction() {
		return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsFragmentNavigation();
	}

	private static NavDirections createTelematicsPushEnrollmentAction() {
		return TelematicsRegistrationNavigationGraphDirections.telematicsPushEnrollmentFragmentNavigation();
	}

	private static NavDirections createTelematicsScoreDetailsAction() {
		return TelematicsNavigationGraphDirections.telematicsScoreDetailsNavigation();
	}

	private static NavDirections createTelematicsTripDetailsAction() {
		return TelematicsNavigationGraphDirections.telematicsTripDetailsNavigation();
	}

	private static NavDirections createTelematicsOutageAction() {
		return TelematicsNavigationGraphDirections.telematicsOutageFragmentNavigation();
	}

	private static NavDirections createTelematicsTripHistoryAction() {
		return TelematicsNavigationGraphDirections.telematicsTripHistoryNavigation();
	}

	private static NavDirections createVehicleDetailAction() {
		return AcePolicyDashboardFragmentDirections.policyDashboardFragmentToVehicleDetailFragment();
	}

	private static NavDirections createWeatherAlertDetailsServiceErrorAction() {
		return AceWeatherAlertDetailsServiceErrorFragmentDirection.weatherAlertDetailsServiceErrorNavigation();
	}

	private AceNavigationStateToAction() {
		// do nothing default constructor
	}
}