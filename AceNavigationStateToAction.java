package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.navigation;

import androidx.navigation.NavDirections;

import com.geico.mobile.TelematicsNavigationGraphDirections;
import com.geico.mobile.TelematicsRegistrationNavigationGraphDirections;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.NavigateAction;
import com.geico.mobile.android.ace.geicoapppresentation.activateaccount.mvvm.view.AceActivateAccountConfirmFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.billing.mvvm.view.AceBillingOverviewFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.billing.mvvm.view.AceBillingPaymentHistoryDetailsFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.billing.mvvm.view.AceBillingPaymentHistoryFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.claims.mvvm.view.AceClaimsDashboardFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.byphone.pages.billing.AceContactGeicoByPhoneBillingPageFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.byphone.pages.claims.AceContactGeicoByPhoneClaimsPageFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.byphone.pages.policy.AceContactGeicoByPhonePolicyPageFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.byphone.pages.quotes.AceContactGeicoByPhoneQuotesPageFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.byphone.pages.roadside.AceContactGeicoByPhoneRoadsidePageFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view.AceLoginSettingsFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.policy.mvvm.view.AcePolicyDashboardFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceConfirmationFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceDashboardFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceDocumentLetterDateOfRequestFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceDocumentSelectDriversFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceDocumentSelectVehiclesFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceDocumentShareFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceDocumentStartFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceErrorFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsurancePrintIdCardsFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.proofofinsurance.mvvm.view.AceProofOfInsuranceTypeOfLetterFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.registration.mvvm.view.AceTelematicsInviteDriversFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.AceUsersFragmentDirections;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.view.AceWeatherAlertDetailsServiceErrorFragmentDirection;

import java.util.HashMap;
import java.util.Map;

/**
 * A mapping that keeps track of the navigation action ids represented
 * by the NavigationAction annotated type of emitted state change.
 *
 * @author Kal Tadesse & Nick Emerson
 */
class AceNavigationStateToAction {

    static final Map<@NavigateAction String, NavDirections> MAPPING = createMapping();

    private AceNavigationStateToAction() {
        // do nothing default constructor
    }

    private static NavDirections createActionLoginSettingsPasswordFragment() {
        return AceLoginSettingsFragmentDirections.actionLoginSettingsFragmentToLoginSettingsPasswordFragment();
    }

    private static NavDirections createActionLoginSettingsSecurityQuestionsFragment() {
        return AceLoginSettingsFragmentDirections.actionLoginSettingsFragmentToLoginSettingsSecurityQuestionsFragment();
    }

    private static NavDirections createActionLoginSettingsUserIdAction() {
        return AceLoginSettingsFragmentDirections.actionLoginSettingsFragmentToLoginSettingsUserIdFragment();
    }

    private static NavDirections createActivateAccountCreateAction() {
        return AceActivateAccountConfirmFragmentDirections.activateAccountConfirmFragmentToActivateAccountCreateFragment();
    }

    private static NavDirections createCancelPaymentThankYouAction() {
        return AceBillingPaymentHistoryDetailsFragmentDirections.actionAceBillingPaymentHistoryDetailsFragmentToAceBillingCancelPaymentThankYouFragment();
    }

    private static NavDirections createClaimsDashboardAction() {
        return AceClaimsDashboardFragmentDirections.claimsDashboardNavigation();
    }

    private static NavDirections createClaimsListAction() {
        return AceClaimsDashboardFragmentDirections.claimsListNavigation();
    }

    private static NavDirections createContactGeicoByPhoneATVAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneATVFragment();
    }

    private static NavDirections createContactGeicoByPhoneAutoAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneAutoFragment();
    }

    private static NavDirections createContactGeicoByPhoneBoatAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneBoatFragment();
    }

    private static NavDirections createContactGeicoByPhoneCancelPolicyAction() {
        return AceContactGeicoByPhonePolicyPageFragmentDirections.actionContactGeicoByPhonePolicyPageToContactGeicoByPhoneCancelPolicyFragment();
    }

    private static NavDirections createContactGeicoByPhoneCancelRequestAction() {
        return AceContactGeicoByPhoneRoadsidePageFragmentDirections.actionContactGeicoByPhoneRoadsideAssistancePageToContactGeicoByPhoneCancelRequestFragment();
    }

    private static NavDirections createContactGeicoByPhoneChangeAddressAction() {
        return AceContactGeicoByPhonePolicyPageFragmentDirections.actionContactGeicoByPhonePolicyPageToContactGeicoByPhoneChangeAddressFragment();
    }

    private static NavDirections createContactGeicoByPhoneCheckBillAction() {
        return AceContactGeicoByPhoneBillingPageFragmentDirections.actionContactGeicoByPhoneBillingPageToContactGeicoByPhoneCheckBillFragment();
    }

    private static NavDirections createContactGeicoByPhoneCheckPaymentHistoryAction() {
        return AceContactGeicoByPhoneBillingPageFragmentDirections.actionContactGeicoByPhoneBillingPageToContactGeicoByPhoneCheckPaymentHistoryFragment();
    }

    private static NavDirections createContactGeicoByPhoneCheckRequestStatusAction() {
        return AceContactGeicoByPhoneRoadsidePageFragmentDirections.actionContactGeicoByPhoneRoadsideAssistancePageToContactGeicoByPhoneCheckRequestStatustFragment();
    }

    private static NavDirections createContactGeicoByPhoneCondoAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneCondoFragment();
    }

    private static NavDirections createContactGeicoByPhoneHomeownersAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneHomeownersFragment();
    }

    private static NavDirections createContactGeicoByPhoneMakePaymentAction() {
        return AceContactGeicoByPhonePolicyPageFragmentDirections.actionContactGeicoByPhonePolicyPageToContactGeicoByPhoneMakePaymentFragment();
    }

    private static NavDirections createContactGeicoByPhoneManageClaimAction() {
        return AceContactGeicoByPhoneClaimsPageFragmentDirections.actionContactGeicoByPhoneClaimsPageToContactGeicoByPhoneManageClaimFragment();
    }

    private static NavDirections createContactGeicoByPhoneManageGlassClaimAction() {
        return AceContactGeicoByPhoneClaimsPageFragmentDirections.actionContactGeicoByPhoneClaimsPageToContactGeicoByPhoneManageGlassClaimFragment();
    }

    private static NavDirections createContactGeicoByPhoneMotorcycleAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneMotorcycleFragment();
    }

    private static NavDirections createContactGeicoByPhonePWCAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhonePWCFragment();
    }

    private static NavDirections createContactGeicoByPhonePayBillAction() {
        return AceContactGeicoByPhoneBillingPageFragmentDirections.actionContactGeicoByPhoneBillingPageToContactGeicoByPhonePayBillFragment();
    }

    private static NavDirections createContactGeicoByPhonePolicyChangesAction() {
        return AceContactGeicoByPhonePolicyPageFragmentDirections.actionContactGeicoByPhonePolicyPageToContactGeicoByPhoneMakePolicyChangesFragment();
    }

    private static NavDirections createContactGeicoByPhoneProofOfInsuranceAction() {
        return AceContactGeicoByPhonePolicyPageFragmentDirections.actionContactGeicoByPhonePolicyPageToContactGeicoByPhoneProofOfInsuranceFragment();
    }

    private static NavDirections createContactGeicoByPhoneRVAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneRVFragment();
    }

    private static NavDirections createContactGeicoByPhoneRentersAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneRentersFragment();
    }

    private static NavDirections createContactGeicoByPhoneReportClaimAction() {
        return AceContactGeicoByPhoneClaimsPageFragmentDirections.actionContactGeicoByPhoneClaimsPageToContactGeicoByPhoneReportClaimFragment();
    }

    private static NavDirections createContactGeicoByPhoneReportGlassDamageAction() {
        return AceContactGeicoByPhoneClaimsPageFragmentDirections.actionContactGeicoByPhoneClaimsPageToContactGeicoByPhoneReportGlassClaimFragment();
    }

    private static NavDirections createContactGeicoByPhoneRequestRoadsideAssistanceAction() {
        return AceContactGeicoByPhoneRoadsidePageFragmentDirections.actionContactGeicoByPhoneRoadsideAssistancePageToContactGeicoByPhoneRequestRoadsideAssistanceFragment();
    }

    private static NavDirections createContactGeicoByPhoneRequestTowAssisntaceAction() {
        return AceContactGeicoByPhoneRoadsidePageFragmentDirections.actionContactGeicoByPhoneRoadsideAssistancePageToContactGeicoByPhoneRequestTowAssistanceFragment();
    }

    private static NavDirections createContactGeicoByPhoneUmbrellaAction() {
        return AceContactGeicoByPhoneQuotesPageFragmentDirections.actionContactGeicoByPhoneQuotesPageToContactGeicoByPhoneUmbrellaFragment();
    }

    private static NavDirections createContactGeicoByPhoneUpdatePaymentMethodsAction() {
        return AceContactGeicoByPhoneBillingPageFragmentDirections.actionContactGeicoByPhoneBillingPageToContactGeicoByPhoneUpdatePaymentMethodFragment();
    }

    private static NavDirections createContactGeicoByPhoneViewPaymentPlansAction() {
        return AceContactGeicoByPhoneBillingPageFragmentDirections.actionContactGeicoByPhoneBillingPageToContactGeicoByPhoneViewPaymentPlansFragment();
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

    private static NavDirections createLaunchDriveEasyAction() {
        return TelematicsNavigationGraphDirections.driveEasyFragmentNavigation();
    }

    private static Map<@NavigateAction String, NavDirections> createMapping() {
        Map<@NavigateAction String, NavDirections> mapping = new HashMap<>();
        mapping.put(NavigateAction.ACTIVATE_ACCOUNT_CREATE_FRAGMENT, createActivateAccountCreateAction());
        mapping.put(NavigateAction.BILLING_PAYMENT_PLANS_FRAGMENT, createPaymentPlansAction());
        mapping.put(NavigateAction.BILLING_PAYMENT_HISTORY_DETAILS_FRAGMENT, createPaymentHistoryDetailsAction());
        mapping.put(NavigateAction.BILLING_CANCEL_PAYMENT_THANK_YOU_FRAGMENT, createCancelPaymentThankYouAction());
        mapping.put(NavigateAction.CLAIMS_DASHBOARD, createClaimsDashboardAction());
        mapping.put(NavigateAction.CLAIMS_LIST, createClaimsListAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_ATV_FRAGMENT, createContactGeicoByPhoneATVAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_AUTO_FRAGMENT, createContactGeicoByPhoneAutoAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_BOAT_FRAGMENT, createContactGeicoByPhoneBoatAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CANCEL_POLICY_FRAGMENT, createContactGeicoByPhoneCancelPolicyAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CANCEL_REQUEST_FRAGMENT, createContactGeicoByPhoneCancelRequestAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CHANGE_ADDRESS_FRAGMENT, createContactGeicoByPhoneChangeAddressAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CHECK_BILL_FRAGMENT, createContactGeicoByPhoneCheckBillAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CHECK_PAYMENT_HISTORY_FRAGMENT, createContactGeicoByPhoneCheckPaymentHistoryAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CHECK_REQUEST_STATUS_FRAGMENT, createContactGeicoByPhoneCheckRequestStatusAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_CONDO_FRAGMENT, createContactGeicoByPhoneCondoAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_HOMEOWNERS_FRAGMENT, createContactGeicoByPhoneHomeownersAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_MAKE_PAYMENT_FRAGMENT, createContactGeicoByPhoneMakePaymentAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_MANAGE_CLAIM_FRAGMENT, createContactGeicoByPhoneManageClaimAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_MANAGE_GLASS_CLAIM_FRAGMENT, createContactGeicoByPhoneManageGlassClaimAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_MOTORCYCLE_FRAGMENT, createContactGeicoByPhoneMotorcycleAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_PAY_BILL_FRAGMENT, createContactGeicoByPhonePayBillAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_POLICY_CHANGES_FRAGMENT, createContactGeicoByPhonePolicyChangesAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_PROOF_OF_INSURANCE_FRAGMENT, createContactGeicoByPhoneProofOfInsuranceAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_PWC_FRAGMENT, createContactGeicoByPhonePWCAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_RENTERS_FRAGMENT, createContactGeicoByPhoneRentersAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_REQUEST_ROADSIDE_ASSISTANCE_FRAGMENT, createContactGeicoByPhoneRequestRoadsideAssistanceAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_REQUEST_TOW_ASSISTANCE_FRAGMENT, createContactGeicoByPhoneRequestTowAssisntaceAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_REPORT_CLAIM_FRAGMENT, createContactGeicoByPhoneReportClaimAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_REPORT_GLASS_DAMAGE_FRAGMENT, createContactGeicoByPhoneReportGlassDamageAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_RV_FRAGMENT, createContactGeicoByPhoneRVAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_UMBRELLA_FRAGMENT, createContactGeicoByPhoneUmbrellaAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_UPDATE_PAYMENT_METHODS_FRAGMENT, createContactGeicoByPhoneUpdatePaymentMethodsAction());
        mapping.put(NavigateAction.CONTACT_GEICO_BY_PHONE_VIEW_PAYMENT_PLAN_FRAGMENT, createContactGeicoByPhoneViewPaymentPlansAction());
        mapping.put(NavigateAction.COVERAGE_FRAGMENT, createCoverageAction());
        mapping.put(NavigateAction.DISCOUNT_FRAGMENT, createDiscountAction());
        mapping.put(NavigateAction.DRIVER_DETAIL_FRAGMENT, createDriverDetailAction());
        mapping.put(NavigateAction.EDIT_ACCOUNT_INFO_FRAGMENT, createEditAccountInfoAction());
        mapping.put(NavigateAction.LOGIN_SETTINGS_PASSWORD_FRAGMENT, createActionLoginSettingsPasswordFragment());
        mapping.put(NavigateAction.LOGIN_SETTINGS_SECURITY_QUESTIONS_FRAGMENT, createActionLoginSettingsSecurityQuestionsFragment());
        mapping.put(NavigateAction.LOGIN_SETTINGS_USER_ID_FRAGMENT, createActionLoginSettingsUserIdAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_BINDER_SELECT_DRIVERS_TO_SHARE_FRAGMENT, createProofOfInsuranceDocumentSelectDriversToDocumentShareFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_BINDER_SELECT_VEHICLES_FRAGMENT, createProofOfInsuranceDocumentStartToDocumentSelectVehiclesFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_BINDER_SELECT_VEHICLES_TO_SHARE_FRAGMENT, createProofOfInsuranceDocumentSelectVehiclesToDocumentShareFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_BINDER_SHARE_TO_CONFIRMATION_FRAGMENT, createProofOfInsuranceDocumentShareToConfirmationFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_BINDER_SHARE_TO_ERROR_FRAGMENT, createProofOfInsuranceDocumentShareToErrorFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_CONFIRMATION_TO_DASHBOARD_FRAGMENT, createProofOfInsuranceConfirmationToDashboardFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_DOCUMENT_START_FRAGMENT, createProofOfInsuranceDashboardToDocumentStartFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_DOCUMENT_START_TO_ERROR_FRAGMENT, createProofOfInsuranceDocumentStartToErrorFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_DOCUMENT_START_TO_TYPE_OF_LETTER_FRAGMENT, createProofOfInsuranceDocumentStartToTypeOfLetterFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_ERROR_TO_DASHBOARD_FRAGMENT, createProofOfInsuranceErrorToDashboardFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_LETTER_DATE_OF_REQUEST_TO_SHARE_FRAGMENT, createProofOfInsuranceDateOfRequestToDocumentShareFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_PRINT_ID_CARDS_FRAGMENT, createProofOfInsuranceDashboardToPrintIdCardsFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_PRINT_ID_CARDS_TO_ERROR_FRAGMENT, createProofOfInsurancePrintIdCardsToErrorFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_SELECT_VEHICLES_TO_LETTER_DATE_OF_REQUEST_FRAGMENT, createProofOfInsuranceDocumentSelectVehiclesToDateOfRequestFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_TYPE_OF_LETTER_TO_SELECT_DRIVERS_FRAGMENT, createProofOfInsuranceTypeOfLetterToSelectDriversFragmentAction());
        mapping.put(NavigateAction.PROOF_OF_INSURANCE_TYPE_OF_LETTER_TO_SELECT_VEHICLES_FRAGMENT, createProofOfInsuranceTypeOfLetterToSelectVehiclesFragmentAction());
        mapping.put(NavigateAction.TELEMATICS_AUTHORIZATION, createTelematicsAuthorizationAction());
        mapping.put(NavigateAction.TELEMATICS_BATTERY_OPTIMIZATION, createTelematicsBatteryOptimizationAction());
        mapping.put(NavigateAction.TELEMATICS_CONFIRMATION, createTelematicsConfirmationAction());
        mapping.put(NavigateAction.TELEMATICS_CONTINUE_TO_STANDALONE_APP, createLaunchDriveEasyAction());
        mapping.put(NavigateAction.TELEMATICS_DASHBOARD, createTelematicsDashboardAction());
        mapping.put(NavigateAction.TELEMATICS_DRIVER_LIST, createTelematicsDriverListAction());
        mapping.put(NavigateAction.TELEMATICS_FAMILY_REPORT_CARD, createTelematicsFamilyReportCardAction());
        mapping.put(NavigateAction.TELEMATICS_INVITE_DRIVERS, createTelematicsInviteDriversAction());
        mapping.put(NavigateAction.TELEMATICS_INVITE_DRIVERS_PHONE_ENTRY, createTelematicsInviteDriversPhoneEntryAction());
        mapping.put(NavigateAction.TELEMATICS_OUTAGE, createTelematicsOutageAction());
        mapping.put(NavigateAction.TELEMATICS_PERMISSIONS_LOCATION, createTelematicsPermissionsLocationAction());
        mapping.put(NavigateAction.TELEMATICS_PERMISSIONS_LOCATION_DENIED, createTelematicsPermissionsLocationDeniedAction());
        mapping.put(NavigateAction.TELEMATICS_PERMISSIONS_PHONE_USAGE, createTelematicsPermissionsPhoneUsageAction());
        mapping.put(NavigateAction.TELEMATICS_PERMISSIONS_PHONE_USAGE_DENIED, createTelematicsPermissionsPhoneUsageDeniedAction());
        mapping.put(NavigateAction.TELEMATICS_PERMISSIONS_PHYSICAL_ACTIVITY, createTelematicsPermissionsPhysicalActivityAction());
        mapping.put(NavigateAction.TELEMATICS_PERMISSIONS_PHYSICAL_ACTIVITY_DENIED, createTelematicsPermissionsPhysicalActivityDeniedAction());
        mapping.put(NavigateAction.TELEMATICS_PUSH_ENROLLMENT, createTelematicsPushEnrollmentAction());
        mapping.put(NavigateAction.TELEMATICS_REGISTRATION_CONFIRMATION, createTelematicsRegistrationConfirmationAction());
        mapping.put(NavigateAction.TELEMATICS_SCORE_DETAILS, createTelematicsScoreDetailsAction());
        mapping.put(NavigateAction.TELEMATICS_TRIP_DETAILS, createTelematicsTripDetailsAction());
        mapping.put(NavigateAction.TELEMATICS_TRIP_HISTORY, createTelematicsTripHistoryAction());
        mapping.put(NavigateAction.USER_SETTINGS_FRAGMENT, createUserSettingsFragmentAction());
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

    private static NavDirections createProofOfInsuranceConfirmationToDashboardFragmentAction() {
        return AceProofOfInsuranceConfirmationFragmentDirections.actionAceProofOfInsuranceConfirmationFragmentToAceProofOfInsuranceDashboardFragment();
    }

    private static NavDirections createProofOfInsuranceDashboardToDocumentStartFragmentAction() {
        return AceProofOfInsuranceDashboardFragmentDirections.actionAceProofOfInsuranceDashboardFragmentToAceProofOfInsuranceDocumentStartFragment();
    }

    private static NavDirections createProofOfInsuranceDashboardToPrintIdCardsFragmentAction() {
        return AceProofOfInsuranceDashboardFragmentDirections.actionAceProofOfInsuranceDashboardFragmentToAceProofOfInsurancePrintIdCardsFragment();
    }

    private static NavDirections createProofOfInsuranceDateOfRequestToDocumentShareFragmentAction() {
        return AceProofOfInsuranceDocumentLetterDateOfRequestFragmentDirections.actionAceProofOfInsuranceDocumentLetterDateOfRequestFragmentToAceProofOfInsuranceDocumentShareFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentSelectDriversToDocumentShareFragmentAction() {
        return AceProofOfInsuranceDocumentSelectDriversFragmentDirections.actionAceProofOfInsuranceDocumentSelectDriversFragmentToAceProofOfInsuranceDocumentShareFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentSelectVehiclesToDateOfRequestFragmentAction() {
        return AceProofOfInsuranceDocumentSelectVehiclesFragmentDirections.actionAceProofOfInsuranceDocumentSelectVehiclesFragmentToAceProofOfInsuranceDocumentLetterDateOfRequestFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentSelectVehiclesToDocumentShareFragmentAction() {
        return AceProofOfInsuranceDocumentSelectVehiclesFragmentDirections.actionAceProofOfInsuranceDocumentSelectVehiclesFragmentToAceProofOfInsuranceDocumentShareFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentShareToConfirmationFragmentAction() {
        return AceProofOfInsuranceDocumentShareFragmentDirections.actionAceProofOfInsuranceDocumentShareFragmentToAceProofOfInsuranceConfirmationFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentShareToErrorFragmentAction() {
        return AceProofOfInsuranceDocumentShareFragmentDirections.actionAceProofOfInsuranceDocumentShareFragmentToAceProofOfInsuranceErrorFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentStartToDocumentSelectVehiclesFragmentAction() {
        return AceProofOfInsuranceDocumentStartFragmentDirections.actionAceProofOfInsuranceDocumentStartFragmentToAceProofOfInsuranceDocumentSelectVehiclesFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentStartToErrorFragmentAction() {
        return AceProofOfInsuranceDocumentStartFragmentDirections.actionAceProofOfInsuranceDocumentStartFragmentToAceProofOfInsuranceErrorFragment();
    }

    private static NavDirections createProofOfInsuranceDocumentStartToTypeOfLetterFragmentAction() {
        return AceProofOfInsuranceDocumentStartFragmentDirections.actionAceProofOfInsuranceDocumentStartFragmentToAceProofOfInsuranceTypeOfLetterFragment();
    }

    private static NavDirections createProofOfInsuranceErrorToDashboardFragmentAction() {
        return AceProofOfInsuranceErrorFragmentDirections.actionAceProofOfInsuranceErrorFragmentToAceProofOfInsuranceDashboardFragment();
    }

    private static NavDirections createProofOfInsurancePrintIdCardsToErrorFragmentAction() {
        return AceProofOfInsurancePrintIdCardsFragmentDirections.actionAceProofOfInsurancePrintIdCardsFragmentToAceProofOfInsuranceErrorFragment();
    }

    private static NavDirections createProofOfInsuranceTypeOfLetterToSelectDriversFragmentAction() {
        return AceProofOfInsuranceTypeOfLetterFragmentDirections.actionAceProofOfInsuranceTypeOfLetterFragmentToAceProofOfInsuranceDocumentSelectDriversFragment();
    }

    private static NavDirections createProofOfInsuranceTypeOfLetterToSelectVehiclesFragmentAction() {
        return AceProofOfInsuranceTypeOfLetterFragmentDirections.actionAceProofOfInsuranceTypeOfLetterFragmentToAceProofOfInsuranceDocumentSelectVehiclesFragment();
    }

    private static NavDirections createTelematicsAuthorizationAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsAuthorizationFragmentNavigation();
    }

    private static NavDirections createTelematicsBatteryOptimizationAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsBatteryOptimizationFragmentNavigation();
    }

    private static NavDirections createTelematicsConfirmationAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsConfirmationFragmentNavigation();
    }

    private static NavDirections createTelematicsDashboardAction() {
        return TelematicsNavigationGraphDirections.telematicsDashboardFragmentNavigation();
    }

    private static NavDirections createTelematicsDriverListAction() {
        return TelematicsNavigationGraphDirections.telematicsDriverListFragmentNavigation();
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

    private static NavDirections createTelematicsOutageAction() {
        return TelematicsNavigationGraphDirections.telematicsOutageFragmentNavigation();
    }

    private static NavDirections createTelematicsPermissionsLocationAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsLocationFragmentNavigation();
    }

    private static NavDirections createTelematicsPermissionsLocationDeniedAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsLocationDeniedFragmentNavigation();
    }

    private static NavDirections createTelematicsPermissionsPhoneUsageAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsPhoneUsageFragmentNavigation();
    }

    private static NavDirections createTelematicsPermissionsPhoneUsageDeniedAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsPhoneUsageDeniedFragmentNavigation();
    }

    private static NavDirections createTelematicsPermissionsPhysicalActivityAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsPhysicalActivityFragmentNavigation();
    }

    private static NavDirections createTelematicsPermissionsPhysicalActivityDeniedAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPermissionsPhysicalActivityDeniedFragmentNavigation();
    }

    private static NavDirections createTelematicsPushEnrollmentAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsPushEnrollmentFragmentNavigation();
    }

    private static NavDirections createTelematicsRegistrationConfirmationAction() {
        return TelematicsRegistrationNavigationGraphDirections.telematicsRegistrationConfirmationFragmentNavigation();
    }

    private static NavDirections createTelematicsScoreDetailsAction() {
        return TelematicsNavigationGraphDirections.telematicsScoreDetailsNavigation();
    }

    private static NavDirections createTelematicsTripDetailsAction() {
        return TelematicsNavigationGraphDirections.telematicsTripDetailsNavigation();
    }

    private static NavDirections createTelematicsTripHistoryAction() {
        return TelematicsNavigationGraphDirections.telematicsTripHistoryNavigation();
    }

    private static NavDirections createUserSettingsFragmentAction() {
        return AceUsersFragmentDirections.actionUsersFragmentToUserSettingsFragment();
    }

    private static NavDirections createVehicleDetailAction() {
        return AcePolicyDashboardFragmentDirections.policyDashboardFragmentToVehicleDetailFragment();
    }

    private static NavDirections createWeatherAlertDetailsServiceErrorAction() {
        return AceWeatherAlertDetailsServiceErrorFragmentDirection.weatherAlertDetailsServiceErrorNavigation();
    }
}