package com.geico.mobile.android.ace.geicoappmodel.tag;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A tag that is used to uniquely identify a fragment or
 * activity in the app.
 */
@StringDef(value = {
        ViewTag.ACTIVATE_ACCOUNT_STEP_1,
        ViewTag.ACTIVATE_ACCOUNT_STEP_1_SSN,
        ViewTag.ACTIVATE_ACCOUNT_STEP_2,
        ViewTag.ACTIVATE_ACCOUNT_LANDING,
        ViewTag.BILLING,
        ViewTag.BILLING_MAKE_PAYMENT,
        ViewTag.BILLING_OVERVIEW,
        ViewTag.BILLING_PAYMENT_HISTORY,
        ViewTag.CLAIMS_DASHBOARD,
        ViewTag.CLAIMS_LIST,
        ViewTag.CLAIMS_REPORT_A_CLAIM,
        ViewTag.CLAIMS_REQUEST_ROADSIDE_SERVICE,
        ViewTag.COVERAGE_FRAGMENT_TAG,
        ViewTag.DASHBOARD,
        ViewTag.DISCOUNT_FRAGMENT_TAG,
        ViewTag.DRIVER_DETAIL_FRAGMENT_TAG,
        ViewTag.ERS_REQUEST_CONFIRMATION,
        ViewTag.ID_CARD_ERROR,
        ViewTag.LEGAL,
        ViewTag.LOGIN_SETTINGS_FRAGMENT,
        ViewTag.LOGIN_SETTINGS_USER_ID_FRAGMENT,
        ViewTag.NON_AUTO_POLICY_PAGE,
        ViewTag.NONE_NEEDED,
        ViewTag.POLICY_DASHBOARD,
        ViewTag.POLICY_SELECTOR,
        ViewTag.PROOF_OF_INSURANCE_BINDER,
        ViewTag.PROOF_OF_INSURANCE_CONFIRMATION,
        ViewTag.PROOF_OF_INSURANCE_DASHBOARD,
        ViewTag.PROOF_OF_INSURANCE_DOCUMENT_DATE_OF_REQUEST,
        ViewTag.PROOF_OF_INSURANCE_DOCUMENT_START,
        ViewTag.PROOF_OF_INSURANCE_DOCUMENT_SELECT_DRIVERS,
        ViewTag.PROOF_OF_INSURANCE_DOCUMENT_SELECT_VEHICLES,
        ViewTag.PROOF_OF_INSURANCE_DOCUMENT_SHARE,
        ViewTag.PROOF_OF_INSURANCE_DOCUMENT_TYPE_OF_LETTER,
        ViewTag.PROOF_OF_INSURANCE_ERROR,
        ViewTag.PROOF_OF_INSURANCE_PRINT_ID_CARDS,
        ViewTag.RECALL_QUOTE_LIST,
        ViewTag.ROADSIDE_REIMBURSEMENT,
        ViewTag.ROADSIDE_SERVICE,
        ViewTag.REISSUE_PAGE,
        ViewTag.SAVE_ID_CARDS_TO_DEVICE,
        ViewTag.SHARE_ID_CARDS_EMAIL,
        ViewTag.SHARE_ID_CARDS_FAX,
        ViewTag.TELEMATICS,
        ViewTag.TELEMATICS_AUTHORIZATION,
        ViewTag.TELEMATICS_BATTERY_OPTIMIZATION,
        ViewTag.TELEMATICS_CONFIRMATION,
        ViewTag.TELEMATICS_DASHBOARD,
        ViewTag.TELEMATICS_DRIVER_LIST,
        ViewTag.TELEMATICS_INVITE_DRIVERS,
        ViewTag.TELEMATICS_INVITE_DRIVERS_PHONE_ENTRY,
        ViewTag.TELEMATICS_PERMISSIONS_LOCATION,
        ViewTag.TELEMATICS_PERMISSIONS_LOCATION_DENIED,
        ViewTag.TELEMATICS_PERMISSIONS_PHONE_USAGE,
        ViewTag.TELEMATICS_PERMISSIONS_PHONE_USAGE_DENIED,
        ViewTag.TELEMATICS_PERMISSIONS_PHYSICAL_ACTIVITY,
        ViewTag.TELEMATICS_PERMISSIONS_PHYSICAL_ACTIVITY_DENIED,
        ViewTag.TELEMATICS_REGISTRATION_CONFIRMATION,
        ViewTag.TELEMATICS_REPORT_CARD,
        ViewTag.TELEMATICS_SCORE_DETAILS,
        ViewTag.TELEMATICS_TRIP_DETAILS,
        ViewTag.TELEMATICS_TRIP_HISTORY,
        ViewTag.VEHICLE_DETAIL_FRAGMENT_TAG,
        ViewTag.USER_SETTINGS_FRAGMENT,
        ViewTag.USERS_FRAGMENT
})
@Retention(RetentionPolicy.SOURCE)
public @interface ViewTag {

    String ACTIVATE_ACCOUNT_LANDING = "Mobile:App:ActivateAccount:LandingPage";
    String ACTIVATE_ACCOUNT_STEP_1 = "Mobile:App:ActivateAccount:Step1";
    String ACTIVATE_ACCOUNT_STEP_1_SSN = "Mobile:App:ActivateAccount:Step1wSSN";
    String ACTIVATE_ACCOUNT_STEP_2 = "Mobile:App:ActivateAccount:Step2";
    String BILLING = "Mobile:App:M5Dashboard:Billing";
    String BILLING_MAKE_PAYMENT = "Mobile:App:Billing:MakePayment";
    String BILLING_OVERVIEW = "Mobile:App:Billing:Landing";
    String BILLING_PAYMENT_HISTORY = "Mobile:App:Billing:PaymentHistory";
    String CLAIMS_DASHBOARD = "Mobile:App:M5Dashboard:Claims";
    String CLAIMS_LIST = "Mobile:App:Claims:Landing";
    String CLAIMS_REPORT_A_CLAIM = "CLAIMS_REPORT_A_CLAIM";
    String CLAIMS_REQUEST_ROADSIDE_SERVICE = "CLAIMS_REQUEST_ROADSIDE_SERVICE";
    String COVERAGE_FRAGMENT_TAG = "COVERAGE_FRAGMENT_TAG";
    String DASHBOARD = "Mobile:App:M5Dashboard";
    String DISCOUNT_FRAGMENT_TAG = "DISCOUNT_FRAGMENT_TAG";
    String DRIVER_DETAIL_FRAGMENT_TAG = "DRIVER_DETAIL_FRAGMENT_TAG";
    String ERS_REQUEST_CONFIRMATION = "Mobile:App:RoadAssist:Confirmation";
    String ID_CARD_ERROR = "Mobile:App:M5:M5IdCardError";
    String LEGAL = "Mobile:App:Legal";
    String LOGIN_SETTINGS_FRAGMENT = "LOGIN_SETTINGS_FRAGMENT";
    String LOGIN_SETTINGS_USER_ID_FRAGMENT = "LOGIN_SETTINGS_USER_ID_FRAGMENT";
    String NONE_NEEDED = "";
    String NON_AUTO_POLICY_PAGE = "NON_AUTO_POLICY_PAGE";
    String POLICY_DASHBOARD = "Mobile:App:M5Dashboard:Policy";
    String POLICY_SELECTOR = "Mobile:App:M5PolicySelector";
    String PROOF_OF_INSURANCE_BINDER = "Mobile:App:POI:Binder";
    String PROOF_OF_INSURANCE_CONFIRMATION = "Mobile:App:ProofOfInsurance:Confirmation";
    String PROOF_OF_INSURANCE_DASHBOARD = "Mobile:App:ProofOfInsurance:Dashboard";
    String PROOF_OF_INSURANCE_DOCUMENT_DATE_OF_REQUEST = "Mobile:App:ProofOfInsurance:Document:DateOfRequest";
    String PROOF_OF_INSURANCE_DOCUMENT_SELECT_DRIVERS = "Mobile:App:ProofOfInsurance:Document:SelectDrivers";
    String PROOF_OF_INSURANCE_DOCUMENT_SELECT_VEHICLES = "Mobile:App:ProofOfInsurance:Document:SelectVehicles";
    String PROOF_OF_INSURANCE_DOCUMENT_SHARE = "Mobile:App:ProofOfInsurance:Document:Share";
    String PROOF_OF_INSURANCE_DOCUMENT_START = "Mobile:App:ProofOfInsurance:Document";
    String PROOF_OF_INSURANCE_DOCUMENT_TYPE_OF_LETTER = "Mobile:App:ProofOfInsurance:Document:TypeOfLetter";
    String PROOF_OF_INSURANCE_ERROR = "Mobile:App:ProofOfInsurance:Error";
    String PROOF_OF_INSURANCE_LETTER = "Mobile:App:POI:Letter";
    String PROOF_OF_INSURANCE_PRINT_ID_CARDS = "Mobile:App:ProofOfInsurance:PrintIDCards";
    String RECALL_QUOTE_LIST = "Mobile:App:Quotes:List";
    String REISSUE_PAGE = "Mobile:App:ReissueLanding";
    String ROADSIDE_REIMBURSEMENT = "MOBILE_ERS_REIMBURSEMENT_INFORMATION_PAGE_DISPLAYED";
    String ROADSIDE_SERVICE = "MOBILE_ERS_SELF_SERVICE_PAGE_DISPLAYED";
    String SAVE_ID_CARDS_TO_DEVICE = "Mobile:App:M5SaveIdCardsToDevice";
    String SHARE_ID_CARDS_EMAIL = "Mobile:App:M5ShareIdCardsEmail";
    String SHARE_ID_CARDS_FAX = "Mobile:App:M5ShareIdCardsFax";
    String TELEMATICS = "Mobile:App:DriveEasy:Telematics";
    String TELEMATICS_AUTHORIZATION = "TELEMATICS_AUTHORIZATION";
    String TELEMATICS_BATTERY_OPTIMIZATION = "Mobile:App:UserProfile:BatterySetup";
    String TELEMATICS_CONFIRMATION = "TELEMATICS_CONFIRMATION";
    String TELEMATICS_DASHBOARD = "Mobile:App:DriveEasy:TelematicsDashboard";
    String TELEMATICS_DRIVER_LIST = "Mobile:App:DriveEasy:TelematicsDriverInfo";
    String TELEMATICS_DRIVER_SETUP = "Mobile:App:DriveEasy:TelematicsDriverSetup";
    String TELEMATICS_EDIT_MOBILE_NUMBER = "Mobile:App:DriveEasy:EditMobileNumber";
    String TELEMATICS_ENROLLED_QUESTION = "Mobile:App:TelematicsEnrolledQuestion";
    String TELEMATICS_INVITE_DRIVERS = "TELEMATICS_INVITE_DRIVERS";
    String TELEMATICS_INVITE_DRIVERS_PHONE_ENTRY = "TELEMATICS_INVITE_DRIVERS_PHONE_ENTRY";
    String TELEMATICS_PERMISSIONS_LOCATION = "Mobile:App:UserProfile:LocationSetup";
    String TELEMATICS_PERMISSIONS_LOCATION_DENIED = "Mobile:App:UserProfile:LocationSetupDenied";
    String TELEMATICS_PERMISSIONS_PHONE_USAGE = "Mobile:App:UserProfile:PhoneUsageSetup";
    String TELEMATICS_PERMISSIONS_PHONE_USAGE_DENIED = "Mobile:App:UserProfile:PhoneUsageSetupDenied";
    String TELEMATICS_PERMISSIONS_PHYSICAL_ACTIVITY = "Mobile:App:UserProfile:MotionSetup";
    String TELEMATICS_PERMISSIONS_PHYSICAL_ACTIVITY_DENIED = "Mobile:App:UserProfile:MotionSetupDenied";
    String TELEMATICS_REGISTRATION_CONFIRMATION = "Mobile:App:TelematicsSetUpConfirmation";
    String TELEMATICS_REPORT_CARD = "Mobile:App:DriveEasy:TelematicsReportCard";
    String TELEMATICS_SCORE_DETAILS = "Mobile:App:DriveEasy:TelematicsScoreDetails";
    String TELEMATICS_TRIP_DETAILS = "Mobile:App:DriveEasy:TelematicsTripDetails";
    String TELEMATICS_TRIP_HISTORY = "Mobile:App:DriveEasy:TelematicsTripsHistory";
    String VEHICLE_DETAIL_FRAGMENT_TAG = "VEHICLE_DETAIL_FRAGMENT_TAG";
    String USER_SETTINGS_FRAGMENT = "USER_SETTINGS_FRAGMENT";
    String USERS_FRAGMENT = "USERS_FRAGMENT";
}