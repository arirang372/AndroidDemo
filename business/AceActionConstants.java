package com.geico.mobile.android.ace.geicoappbusiness.application;

/**
 * Defines the Intent action constants used throughout the GEICO Android
 * application.
 * <p>
 * Important: These action strings should be prefixed with "ACE" to avoid
 * conflicts with other action strings installed on the customers device.
 *
 * @author Chris Delia, Lucid Frameworks Corporation
 * @see android.app.Application
 */
public interface AceActionConstants {

	/**
	 * Open a view that allows a user to view Policy Account Information.
	 */
	String ACTION_ACCOUNT_INFORMATION = "ACE_ACTION_ACCOUNT_INFORMATION";
	String ACTION_ACCOUNT_LEVEL_BILLING_ERROR = "ACE_ACTION_ACCOUNT_LEVEL_BILLING_ERROR";
	String ACTION_ACCOUNT_LEVEL_MAKE_PAYMENT_THANKYOU = "ACTION_ACCOUNT_LEVEL_MAKE_PAYMENT_THANKYOU";
	/**
	 * Opens a view where the user can activate an account for an existing
	 * policy using existing user ID.
	 */
	String ACTION_ACCOUNT_LINKING = "ACE_ACCOUNT_LINKING";
	/**
	 * Opens a view where the user can confirm the information in preparation
	 * for activating an account for an existing policy
	 */
	String ACTION_ACTIVATE_ACCOUNT = "ACE_ACTIVATE_ACCOUNT";

	/**
	 * Opens a view where the user can confirm the information in preparation
	 * for activating an account for an existing policy
	 */
	String ACTION_ACTIVATE_ACCOUNT_CONFIRM_FROM_DEEP_LINKING = "geicoappdeeplink://sections/confirmFragment";
	/**
	 * Opens a view where the user can complete activating an account for an
	 * existing policy
	 */
	String ACTION_ACTIVATE_ACCOUNT_CREATE = "ACE_ACTIVATE_ACCOUNT_CREATE";

	/**
	 * Opens all products page
	 */
	String ACTION_ALL_PRODUCTS = "ACTION_ALL_PRODUCTS";
	String ACTION_APPLICATION_RATER = "ACTION_APPLICATION_RATER";
	/**
	 * Open a hybrid web view for third party claimants.
	 */
	String ACTION_AUTHENTICATED_THIRD_PARTY_WEB_VIEW = "ACTION_AUTHENTICATED_THIRD_PARTY_WEB_VIEW";
	/**
	 * Open a view that authenticates a third party user that wants view a claim.
	 */
	String ACTION_AUTHENTICATE_THIRD_PARTY_CLAIMANT = "ACE_AUTHENTICATE_THIRD_PARTY_CLAIMANT";
	/**
	 * Open a view that allows the user to view Billing Options
	 */
	String ACTION_BILLING = "ACE_ACTION_BILLING";
	/**
	 * Open a view that allows the user to view Billing Documents and Statement activity
	 */
	String ACTION_BILLING_DOCUMENTS_AND_STATEMENTS = "ACTION_BILLING_DOCUMENTS_AND_STATEMENTS";
	/**
	 * Open a view that allows the user to view the Billing Make Payment Activity
	 */
	String ACTION_BILLING_MAKE_PAYMENT = "ACE_ACTION_BILLING_MAKE_PAYMENT";
	/**
	 * Open a view that allows the user to view Billing Payment Method activity
	 */
	String ACTION_BILLING_PAYMENT_METHOD = "ACTION_BILLING_PAYMENT_METHOD";
	/**
	 * Open a view that allows the user to view Billing Payment Plan activity
	 */
	String ACTION_BILLING_PAYMENT_PLAN = "ACTION_BILLING_PAYMENT_PLAN";
	/**
	 * Open a view that allows the user to view Billing Tab activity
	 */
	String ACTION_BILLING_TAB = "ACE_ACTION_BILLING_TAB";
	/**
	 * Open a view that shows the back of ID Card image
	 */
	String ACTION_BORDERED_ID_CARDS_BACK_IMAGE = "ACE_ACTION_BORDERED_ID_CARDS_BACK_IMAGE";
	/**
	 * Open a view that request camera permission
	 */
	String ACTION_CAMERA_PERMISSION = "ACE_ACTION_CAMERA_PERMISSION";
	String ACTION_CAMERA_PERMISSION_FOR_WEB_LINK = "ACE_ACTION_CAMERA_PERMISSION_FOR_WEB_LINK";

	/**
	 * open a view that allows to user to change payment plans available for a
	 * policy.
	 */
	String ACTION_CHANGE_PAYMENT_PLAN = "ACE_CHANGE_PAYMENT_PLAN";
	String ACTION_CHANGE_PAYMENT_PLAN_ERROR = "ACE_ACTION_CHANGE_PAYMENT_PLAN_ERROR";
	String ACTION_CHANGE_PAYMENT_PLAN_THANKYOU = "ACE_ACTION_CHANGE_PAYMENT_PLAN_THANKYOU";
	String ACTION_CLAIMS_DASHBOARD = "ACTION_CLAIMS_DASHBOARD";
	/**
	 * Open a view that allows the user to select a claim or start a new one.
	 */
	String ACTION_CLAIMS_LIST = "ACE_ACTION_CLAIMS_LIST";
	/**
	 * Open a view that allows the user to view claims via claims web. Back button will return user to last native page
	 */
	String ACTION_CLAIMS_WEB_VIEW = "ACTION_CLAIMS_WEB_VIEW";
	/**
	 * Open a view that shows claim detail
	 */
	String ACTION_CLAIM_DETAIL = "ACTION_CLAIM_DETAIL";
	/**
	 * Open a view that shows the contact Geico view.
	 */
	String ACTION_CONTACT_GEICO = "ACE_ACTION_CONTACT_GEICO";
	/**
	 * Open a view that shows agent locations.
	 */
	String ACTION_CONTACT_GEICO_BY_AGENT_LOCATION = "ACTION_CONTACT_GEICO_BY_AGENT_LOCATION";
	/**
	 * Open a view that shows the contact by email confirmation view.
	 */
	String ACTION_CONTACT_GEICO_BY_EMAIL_THANKYOU = "ACE_ACTION_CONTACT_GEICO_BY_GEICO_THANKYOU";
	/**
	 * Open a view that shows list of phone numbers to contact geico.
	 */
	String ACTION_CONTACT_GEICO_BY_PHONE = "CONTACT_GEICO_BY_PHONE";
	/**
	 * Open a view that shows list of options to contact geico
	 * by phone for Billing issues.
	 */
	String ACTION_CONTACT_GEICO_BY_PHONE_BILLING = "CONTACT_GEICO_BY_PHONE_BILLING_PAGE";
	/**
	 * Open a view that shows list of options to contact geico
	 * by phone for Claims issues.
	 */
	String ACTION_CONTACT_GEICO_BY_PHONE_CLAIMS = "CONTACT_GEICO_BY_PHONE_CLAIMS_PAGE";
	/**
	 * Open a view that shows list of options to contact geico
	 * by phone for Policy issues.
	 */
	String ACTION_CONTACT_GEICO_BY_PHONE_POLICY = "CONTACT_GEICO_BY_PHONE_POLICY_PAGE";
	/**
	 * Open a view that shows list of options to contact geico
	 * by phone for Quotes issues.
	 */
	String ACTION_CONTACT_GEICO_BY_PHONE_QUOTES = "CONTACT_GEICO_BY_PHONE_QUOTES_PAGE";
	/**
	 * Open a view that shows list of options to contact geico
	 * by phone for Roadside issues.
	 */
	String ACTION_CONTACT_GEICO_BY_PHONE_ROADSIDE = "CONTACT_GEICO_BY_PHONE_ROADSIDE_PAGE";
	/**
	 * Open a view that shows coverage description
	 */
	String ACTION_COVERAGE_DESCRIPTION = "ACTION_COVERAGE_DESCRIPTION";
	/**
	 * Opens a view which allows the user to scan credit card through the device camera.
	 */
	String ACTION_CREDIT_CARD_SCAN = "ACTION_CREDIT_CARD_SCAN";
	/**
	 * Open an error view if the customer keys their current password incorrectly > 5 times on the login settings view
	 */
	String ACTION_CURRENT_PASSWORD_ENTRY_ERROR = "ACTION_CURRENT_PASSWORD_ENTRY_ERROR";
	/**
	 * Open a view that shows the dashboard page to the user
	 */
	String ACTION_DASHBOARD = "ACE_ACTION_DASHBOARD";
	/**
	 * Opens the Demo view
	 */
	String ACTION_DEMO = "ACE_ACTION_DEMO";
	/**
	 * Opens the Destinations view and displays nearby attractions.
	 */
	String ACTION_DESTINATIONS = "ACE_ACTION_DESTINATIONS";
	/**
	 * Open a view that shows the policy discount description view to the user
	 */
	String ACTION_DISCOUNT_DESCRIPTION = "ACE_ACTION_DISCOUNT_DESCRIPTION";
	/**
	 * Open a view that shows the documents view to the user
	 */
	String ACTION_DOCUMENTS = "ACE_ACTION_DOCUMENTS";
	/**
	 *
	 */
	String ACTION_DRIVER_DETAIL = "ACE_ACTION_DRIVER_DETAIL";
	String ACTION_DUCK_CREEK_EDIT_PAYMENT_METHOD = "ACE_ACTION_DUCK_CREEK_EDIT_PAYMENT_METHOD";
	String ACTION_DUCK_CREEK_EDIT_STORED_ALTERNATE_PAYER = "ACE_ACTION_DUCK_CREEK_EDIT_STORED_ALTERNATE_PAYER";
	/**
	 * View that opens Duck Creek New Payment information.
	 */
	String ACTION_DUCK_CREEK_NEW_PAYMENT_ACCOUNT = "ACTION_DUCK_CREEK_NEW_PAYMENT_ACCOUNT";
	String ACTION_DUCK_CREEK_NEW_PAYMENT_METHOD = "ACE_ACTION_DUCK_CREEK_NEW_PAYMENT_METHOD";
	String ACTION_DUCK_CREEK_PAYMENT_METHODS_THANK_YOU = "ACE_ACTION_DUCK_CREEK_PAYMENT_METHODS_THANK_YOU";
	/**
	 * Open a view that allows the user to postpone payment date for a Duck Creek policy
	 */
	String ACTION_DUCK_CREEK_POSTPONE_PAYMENT_THANK_YOU = "ACE_DUCK_CREEK_ACTION_POSTPONE_PAYMENT_THANK_YOU";
	/**
	 * Open views that prepares the user for Easy Photo Estimate flow
	 */
	String ACTION_EASY_PHOTO_ESTIMATE_AUTHENTICATION = "ACTION_EASY_PHOTO_ESTIMATE_AUTHENTICATION";
	String ACTION_EASY_PHOTO_ESTIMATE_INSTRUCTIONS_PAGE = "ACTION_EASY_PHOTO_ESTIMATE_INSTRUCTIONS_PAGE";
	String ACTION_EASY_PHOTO_ESTIMATE_LANDING = "ACTION_EASY_PHOTO_ESTIMATE_LANDING";
	String ACTION_EASY_PHOTO_ESTIMATE_PERMISSIONS = "ACTION_EASY_PHOTO_ESTIMATE_PERMISSIONS";
	/**
	 * Open a view that guides the user through photo capture for Easy Photo Estimate
	 */
	String ACTION_EASY_PHOTO_ESTIMATE_PHOTO_CAPTURE = "ACTION_EASY_PHOTO_ESTIMATE_PHOTO_CAPTURE";
	String ACTION_EASY_PHOTO_ESTIMATE_RETAKE_LANDING = "ACTION_EASY_PHOTO_ESTIMATE_RETAKE_LANDING";
	String ACTION_EASY_PHOTO_ESTIMATE_RETAKE_SUMMARY_PAGE = "ACTION_EASY_PHOTO_ESTIMATE_RETAKE_SUMMARY_PAGE";
	String ACTION_EASY_PHOTO_ESTIMATE_START_RETAKE = "ACTION_EASY_PHOTO_ESTIMATE_START_RETAKE";
	/**
	 * Open a view displaying a summary of the photos taken during Easy Photo Estimate
	 */
	String ACTION_EASY_PHOTO_ESTIMATE_SUMMARY = "ACTION_EASY_PHOTO_ESTIMATE_SUMMARY";
	/**
	 * Open a view that informs the user about the end result of the photo upload for Easy Photo Estimate
	 */
	String ACTION_EASY_PHOTO_ESTIMATE_UPLOAD_CONFIRMATION = "ACTION_EASY_PHOTO_ESTIMATE_UPLOAD_CONFIRMATION";
	/**
	 * Opens a view that helps the user scan or enter a VIN for Easy Photo Estimate
	 */
	String ACTION_EASY_PHOTO_ESTIMATE_VIN_SCAN = "ACTION_EASY_PHOTO_ESTIMATE_VIN_SCAN";
	String ACTION_ECAMS_WEB_VIEW = "ACTION_ECAMS_WEB_VIEW";
	/**
	 * Open a view that lets the user to edit coverage.
	 */
	String ACTION_EDIT_COVERAGE = "ACTION_EDIT_COVERAGE";
	/**
	 * Open a view that shows the dialog to edit photos.
	 */
	String ACTION_EDIT_DRIVER_PHOTOS = "ACTION_EDIT_DRIVER_PHOTOS";
	String ACTION_EDIT_PHOTOS = "ACTION_EDIT_PHOTOS";
	String ACTION_EDIT_VEHICLE_PHOTOS = "ACTION_EDIT_VEHICLE_PHOTOS";
	/**
	 * Open a view that shows the email id cards view to the user
	 */
	String ACTION_EMAIL_ID_CARDS = "ACE_ACTION_EMAIL_ID_CARDS";
	String ACTION_EMAIL_ID_CARDS_STAND_ALONE = "ACE_ACTION_EMAIL_ID_CARDS_STAND_ALONE";
	String ACTION_ENROLL_AUTOMATIC_PAYMENT = "ACE_ACTION_ENROLL_AUTOMATIC_PAYMENT";
	String ACTION_ENROLL_IN_PAPERLESS_OPTIONS_THANK_YOU = "ACE_ACTION_ENROLL_IN_PAPERLESS_OPTIONS_THANK_YOU";
	String ACTION_ENROLL_IN_PAPERLESS_POLICY = "ACE_ACTION_ENROLL_IN_PAPERLESS_POLICY";
	String ACTION_ENROLL_IN_PAPERLESS_POLICY_THANK_YOU = "ACTION_ENROLL_IN_PAPERLESS_POLICY_THANK_YOU";
	String ACTION_ENROLL_IN_RPM_FAILURE = "ACE_ACTION_ENROLL_IN_RPM_FAILURE";
	String ACTION_ENROLL_IN_RPM_THANK_YOU = "ACE_ACTION_ENROLL_IN_RPM_THANK_YOU";
	/**
	 * Open a view that allows user to see their ERS courtesy card.
	 */
	String ACTION_ERS_COURTESY_CARD_VIEW = "ACE_ACTION_ERS_COURTESY_CARD_VIEW";
	/**
	 * Open a view that shows the dealership search page for the emergency
	 * roadside service view
	 */
	String ACTION_ERS_DEALERSHIP_SEARCH = "ACTION_ERS_DEALERSHIP_SEARCH";
	/**
	 * Open a view that shows the LOCATION tab of the emergency roadside service
	 * view to the user
	 */
	String ACTION_ERS_LOCATION = "ACTION_ERS_LOCATION";
	/**
	 * Open a view that shows the Request Confirmation of the emergency roadside
	 * service view to the user
	 */
	String ACTION_ERS_REQUEST_CONFIRMATION = "ACTION_ERS_REQUEST_CONFIRMATION";
	/**
	 * Open a view that shows the REVIEW/SUBMIT tab of the emergency roadside
	 * service view to the user
	 */
	String ACTION_ERS_REVIEW_SUBMIT = "ACTION_ERS_REVIEW_AND_SUBMIT";
	/**
	 * Open a view that shows the WHAT IS WRONG tab of the emergency roadside
	 * service view to the user
	 */
	String ACTION_ERS_WHAT_IS_WRONG = "ACE_ACTION_ERS_WHAT_IS_WRONG";
	/**
	 * Open a view that shows the YOUR INFO tab of the emergency roadside
	 * service view to the user
	 */
	String ACTION_ERS_YOUR_INFO = "ACTION_ERS_YOUR_INFO";
	/**
	 * Opens the GEICO Explore activity.
	 */
	String ACTION_EXPLORE = "ACTION_EXPLORE";
	/**
	 * Opens the GEICO Explore game activity.
	 */
	String ACTION_EXPLORE_GAME = "ACTION_EXPLORE_GAME";
	/**
	 * Opens the GEICO Explore game onboarding activity.
	 */
	String ACTION_EXPLORE_GAME_ONBOARDING = "ACTION_EXPLORE_GAME_ONBOARDING";
	/**
	 * Opens the GEICO Explore Onboarding activity.
	 */
	String ACTION_EXPLORE_ONBOARDING = "ACTION_EXPLORE_ONBOARDING";
	/**
	 * Open a view that shows the Extras view to the user.
	 */
	String ACTION_EXTRAS = "ACE_ACTION_EXTRAS";
	/**
	 * A view that allows a user to view FAQs page
	 */
	String ACTION_FAQS = "ACE_ACTION_FAQS";
	/**
	 * Open a view that shows the fax id cards view to the user
	 */
	String ACTION_FAX_ID_CARDS = "ACE_ACTION_FAX_ID_CARDS";
	/**
	 * Opens find me a ride view to the user.
	 */
	String ACTION_FIND_A_RIDE = "ACE_ACTION_FIND_A_RIDE";
	/**
	 * Opens find me a ride map view to the user.
	 */
	String ACTION_FIND_A_RIDE_MAP = "ACE_ACTION_FIND_A_RIDE_MAP";
	/**
	 * Opens the gas buddy view to the user.
	 */
	String ACTION_FIND_GAS = "ACE_ACTION_FIND_GAS";
	/**
	 * Opens find me a ride taxi view to the user.
	 */
	String ACTION_FIND_RIDE_TAXI = "ACE_ACTION_FIND_RIDE_TAXI";
	/**
	 * Open a view that shows the back of ID Card image
	 */
	String ACTION_FIXED_ID_CARDS_BACK_IMAGE = "ACE_ACTION_FIXED_ID_CARDS_BACK_IMAGE";
	/**
	 * Opens a Forgot Your Password view.
	 */
	String ACTION_FORGOT_PASSWORD = "ACE_ACTION_FORGOT_PASSWORD";
	/**
	 * Opens a Forgot Your Password Login view.
	 */
	String ACTION_FORGOT_PASSWORD_LOGIN = "ACTION_FORGOT_PASSWORD_LOGIN";
	/**
	 * Open a web view
	 */
	String ACTION_FULL_SITE_OPENER = "ACTION_FULL_SITE_OPENER";
	/**
	 * Open a view kicks off link logins.
	 */
	String ACTION_FULL_SITE_TRANSFER = "ACTION_FULL_SITE_TRANSFER";
	/**
	 * Opens the AceGeniusLinkLandingActivity
	 */
	String ACTION_GENIUS_LINK_LANDING = "ACE_ACTION_GENIUS_LINK_LANDING";
	/**
	 * Opens get a quote view to the user.
	 */
	String ACTION_GET_A_QUOTE = "ACTION_GET_A_QUOTE";
	/**
	 * Opens a webView that allows for users to get a quote
	 */
	String ACTION_GET_QUOTE_WEB_VIEW = "ACTION_GET_QUOTE_WEB_VIEW";
	/**
	 * Opens the Intent Page for Guest Services. This value is stored in and read from shared
	 * preferences so its string value should remain as-is for backward compatibility.
	 */
	String ACTION_GUEST_SERVICES = "ACE_ACTION_GUEST_SERVICES_FIRST_START";
	/**
	 * Open a view that shows the id cards view to the user
	 */
	String ACTION_ID_CARDS = "ACE_ACTION_ID_CARDS";
	/**
	 * Open a view that prompts user for additional questions before proceeding to ID Cards.
	 */
	String ACTION_ID_CARDS_ADDITIONAL_QUESTIONS = "ACE_ACTION_ID_CARDS_ADDITIONAL_QUESTIONS";
	/**
	 * Open a view that shows the id cards barcode view to the user
	 */
	String ACTION_ID_CARDS_BARCODE = "ACE_ACTION_ID_CARDS_BARCODE";
	/**
	 * Open an ID Cards error view
	 */
	String ACTION_ID_CARDS_ERROR = "ACTION_ID_CARDS_ERROR";
	/**
	 * Open a view that shows the id cards save to device advisory view to the
	 * user
	 */
	String ACTION_ID_CARDS_SAVE_TO_DEVICE = "ACTION_ID_CARDS_SAVE_TO_DEVICE";
	/**
	 * Open a view that shows the id cards thank you page view to the user
	 */
	String ACTION_ID_CARDS_THANK_YOU = "ACE_ACTION_ID_CARDS_THANK_YOU";
	/**
	 * A view that allows a user to allow or deny location and/or gps features.
	 */
	String ACTION_INITIALIZE_LOCATION_REQUEST = "ACTION_INITIALIZE_LOCATION_REQUEST";
	/**
	 * Opens Inventory Calculator activity
	 */
	String ACTION_INVENTORY_CALCULATOR = "ACTION_INVENTORY_CALCULATOR";
	/**
	 * Opens Inventory Calculator Onboarding activity
	 */
	String ACTION_INVENTORY_CALCULATOR_ONBOARDING = "ACTION_INVENTORY_CALCULATOR_ONBOARDING";
	/**
	 * Open a view that allows the user to view a learn more tutorial.
	 */
	String ACTION_LEARN_MORE_TUTORIAL = "ACE_ACTION_LEARN_MORE_TUTORIAL";
	/**
	 * A view that allows a user to view legals page
	 */
	String ACTION_LEGALS = "ACE_ACTION_LEGALS";
	/**
	 * Open a view that allows the user to login to policies.
	 */
	String ACTION_LOGIN = "ACE_ACTION_LOGIN";
	/**
	 * Open a view that allows a user to edit login settings
	 */
	String ACTION_LOGIN_SETTINGS = "ACTION_LOGIN_SETTINGS";
	/**
	 * A view that allows a user to view login settings Thank you page
	 */
	String ACTION_LOGIN_SETTINGS_THANKYOU = "ACE_ACTION_LOGIN_SETTINGS_THANKYOU";
	/**
	 * Open a view that allows the user to logout of policies.
	 */
	String ACTION_LOGOUT = "ACE_ACTION_LOGOUT";
	/**
	 * Open a view that shows the mail id cards view to the user
	 */
	String ACTION_MAIL_ID_CARDS = "ACE_ACTION_MAIL_ID_CARDS";
	String ACTION_MAKE_PAYMENT = "ACE_ACTION_MAKE_PAYMENT";
	/**
	 * This should be the first activity we show after starting a policy. - cmd
	 */
	String ACTION_MAKE_PAYMENT_COMBINED = "ACE_ACTION_MAKE_PAYMENT_COMBINED";
	/**
	 * This should be the last activity we show after making payment
	 */
	String ACTION_MAKE_PAYMENT_THANK_YOU = "ACE_ACTION_MAKE_PAYMENT_THANKYOU";
	String ACTION_MESSAGE_CENTER = "ACE_ACTION_MESSAGE_CENTER";
	/**
	 * Open a view that allows the user to view non auto policy with more
	 * information.
	 */
	String ACTION_NEW_PAYMENT_ACCOUNT = "ACE_ACTION_NEW_PAYMENT_ACCOUNT";
	String ACTION_NEW_PAYMENT_METHOD = "ACE_ACTION_NEW_PAYMENT_METHOD";
	String ACTION_NON_AUTO_POLICY = "ACE_ACTION_NON_AUTO_POLICY";
	String ACTION_NON_RPM_EDIT_STORED_ACCOUNT = "ACE_ACTION_NON_RPM_EDIT_STORED_ACCOUNT";
	/**
	 * Activity to show notification settings
	 */
	String ACTION_NOTIFICATION_SETTINGS = "ACE_ACTION_NOTIFICATION_SETTINGS";
	String ACTION_ONBOARDING_APP_SETTINGS = "ACE_ACTION_ONBOARDING_APP_SETTINGS";
	String ACTION_OUTAGE = "ACE_ACTION_OUTAGE";
	String ACTION_PAPERLESS_OPTIONS = "ACE_ACTION_PAPERLESS_OPTIONS";
	String ACTION_PAPERLESS_POLICY_OPTIONS = "ACE_ACTION_PAPERLESS_POLICY_OPTIONS";
	String ACTION_PARKING = "ACE_ACTION_PARKING";
	String ACTION_PARKING_PROVIDER_DETAIL = "ACE_ACTION_PARKING_PROVIDER_DETAIL";
	String ACTION_PAYMENT_HISTORY = "ACE_ACTION_PAYMENT_HISTORY";
	String ACTION_PAYMENT_METHODS_UPDATE_ERROR = "ACE_ACTION_PAYMENT_METHODS_UPDATE_ERROR";
	String ACTION_PAYMENT_METHODS_UPDATE_THANK_YOU = "ACE_ACTION_PAYMENT_METHODS_UPDATE_THANK_YOU";
	/**
	 * A view that allows a user to allow or deny location features.
	 */
	String ACTION_PERMISSION_LOCATION_REQUEST = "ACTION_PERMISSION_LOCATION_REQUEST";
	/**
	 * A view that allows a user to allow or deny location permission before entering ENVOY weblink.
	 */
	String ACTION_PERMISSION_LOCATION_REQUEST_FOR_ENVOY = "ACTION_PERMISSION_LOCATION_REQUEST_FOR_ENVOY";
	/**
	 * A view that allows a user to allow or deny location permission before entering a hybrid weblink.
	 */
	String ACTION_PERMISSION_WEBLINK_LOCATION_REQUEST = "ACTION_PERMISSION_WEBLINK_LOCATION_REQUEST";
	/**
	 * A view that allows a user to view thank you page after user updates the
	 * information.
	 */
	String ACTION_POLICY_ACCOUNT_INFORMATION_THANKYOU = "ACE_ACTION_POLICY_ACCOUNT_INFORMATION_THANKYOU";
	/**
	 * Open a view that allows the user to see policy information
	 */
	String ACTION_POLICY_DASHBOARD = "ACE_ACTION_POLICY_DASHBOARD";
	/**
	 * Open a view that allows the user to see M5 coverage page
	 */
	String ACTION_POLICY_DASHBOARD_TO_COVERAGE = "ACTION_POLICY_DASHBOARD_TO_COVERAGE";
	/**
	 * Open a view that allows the user to see M5 coverage page
	 */
	String ACTION_POLICY_DASHBOARD_TO_DISCOUNTS = "ACTION_POLICY_DASHBOARD_TO_DISCOUNTS";
	/**
	 * Open a view that allows the user to see M5 coverage page
	 */
	String ACTION_POLICY_DASHBOARD_TO_DRIVERS = "ACTION_POLICY_DASHBOARD_TO_DRIVERS";
	/**
	 * Open a view that allows the user to see M5 coverage page
	 */
	String ACTION_POLICY_DASHBOARD_TO_EDIT_ACCOUNT_INFO = "ACTION_POLICY_DASHBOARD_TO_EDIT_ACCOUNT_INFO";
	/**
	 * Open a view that allows the user to see M5 coverage page
	 */
	String ACTION_POLICY_DASHBOARD_TO_VEHICLES = "ACTION_POLICY_DASHBOARD_TO_VEHICLE";
	/**
	 * Open a view that allows a user to edit policy account information.
	 */
	String ACTION_POLICY_INFORMATION_EDITOR = "ACTION_POLICY_INFORMATION_EDITOR";
	/**
	 * Open a view that allows a user to edit policy account email information.
	 */
	String ACTION_POLICY_INFORMATION_EMAIL_EDITOR = "ACTION_POLICY_INFORMATION_EMAIL_EDITOR";
	/**
	 * Open a legacy view for policy configuration.
	 */
	String ACTION_POLICY_LEGACY_TAB = "ACTION_POLICY_LEGACY_TAB";
	/**
	 * Open a view that allows a user to view available related policies in Policy Linking Page.
	 */
	String ACTION_POLICY_LINKING = "ACE_ACTION_POLICY_LINKING";
	/**
	 * Open a view that allows a user to view available related policies in Policy selector or Portfolio Page.
	 */
	String ACTION_POLICY_SELECTOR = "ACE_ACTION_POLICY_SELECTOR";
	/**
	 * Open a view that allows the user to postpone payment date.
	 */
	String ACTION_POSTPONE_PAYMENT = "ACE_ACTION_POSTPONE_PAYMENT";
	/**
	 * Open a view that allows the user to postpone payment date.
	 */
	String ACTION_POSTPONE_PAYMENT_THANK_YOU = "ACE_ACTION_POSTPONE_PAYMENT_THANK_YOU";
	/**
	 * Open a view that prepares to authenticate a third party claimant.
	 */
	String ACTION_PREPARE_TO_AUTHENTICATE_THIRD_PARTY_CLAIMANT = "ACE_ACTION_PREPARE_TO_AUTHENTICATE_THIRD_PARTY_CLAIMANT";
	/**
	 * Open a view that allows user to see their claims.
	 */
	String ACTION_PRE_CLAIM_VIEW = "ACE_ACTION_PRE_CLAIM_VIEW";
	String ACTION_PRE_ENROLL_AUTOMATIC_PAYMENT = "ACE_ACTION_PRE_ENROLL_AUTOMATIC_PAYMENT";
	/**
	 * Open a view that allows user to see their id cards.
	 */
	String ACTION_PRE_ID_CARDS_VIEW = "ACE_ACTION_PRE_ID_CARDS_VIEW";
	String ACTION_PRE_MAKE_PAYMENT_VIEW = "ACE_ACTION_PRE_MAKE_PAYMENT_VIEW";
	String ACTION_PROOF_OF_INSURANCE = "ACE_ACTION_PROOF_OF_INSURANCE";
	/**
	 * Open a pro active login account recovery view.
	 */
	String ACTION_PRO_ACTIVE_LOGIN = "ACE_ACTION_PRO_ACTIVE_LOGIN";
	String ACTION_QUALTRICS_FEEDBACK_WEB_VIEW = "ACTION_QUALTRICS_FEEDBACK_WEB_VIEW";
	/**
	 * Opens a quote recall list view.
	 */
	String ACTION_QUOTE_RECALL = "ACE_ACTION_QUOTE_RECALL";
	/**
	 * Opens a view that request read contacts permission.
	 */
	String ACTION_READ_CONTACTS_PERMISSION = "ACTION_READ_CONTACTS_PERMISSION";
	/**
	 * Opens a reimbursement information view.
	 */
	String ACTION_REIMBURSEMENT = "ACTION_REIMBURSEMENT";
	/**
	 * Opens a view that allows the user to report a claim.
	 */
	String ACTION_REISSUE_PAGE = "ACTION_REISSUE_PAGE";
	/**
	 * Opens a view that allows the user to report a claim.
	 */
	String ACTION_REPORT_CLAIM = "ACTION_REPORT_CLAIM";
	/**
	 * Opens a reset password view.
	 */
	String ACTION_RESET_PASSWORD = "ACE_ACTION_RESET_PASSWORD";
	/**
	 * Opens a reset password instruction view.
	 */
	String ACTION_RESET_PASSWORD_INSTRUCTION = "ACE_ACTION_RESET_PASSWORD_INSTRUCTION";
	/**
	 * Opens a recovery account view.
	 */
	String ACTION_RESET_PASSWORD_RECOVERY_ACCOUNT = "ACE_ACTION_RESET_PASSWORD_RECOVERY_ACCOUNT";
	/**
	 * Opens a recovery method view.
	 */
	String ACTION_RESET_PASSWORD_RECOVERY_METHOD = "ACE_ACTION_RESET_PASSWORD_RECOVERY_METHOD";
	/**
	 * Opens a recovery pin view.
	 */
	String ACTION_RESET_PASSWORD_RECOVERY_PIN = "ACE_ACTION_RESET_PASSWORD_RECOVERY_PIN";
	/**
	 * Opens the Ride Share view to the user.
	 */
	String ACTION_RIDE_SHARE = "ACE_ACTION_RIDE_SHARE";
	/**
	 * Opens find view for the user to select a ride type
	 */
	String ACTION_RIDE_SHARE_TYPES = "ACE_ACTION_RIDE_SHARE_DISPLAY_RIDE_TYPES";
	/**
	 * Open a view that shows the roadside assistance view to the user
	 */
	String ACTION_ROADSIDE_ASSISTANCE_MAIN = "ACE_ACTION_ROADSIDE_ASSISTANCE_MAIN";
	String ACTION_ROADSIDE_REIMBURSEMENT_EMAIL = "ACTION_ROADSIDE_REIMBURSEMENT_EMAIL";
	String ACTION_ROADSIDE_REIMBURSEMENT_THANK_YOU = "ACTION_ROADSIDE_REIMBURSEMENT_THANK_YOU";
	String ACTION_SALES_QUOTE_WEB_VIEW = "ACE_SALES_QUOTE_WEB_VIEW";
	String ACTION_SAVED_ID_CARDS = "ACTION_SAVED_ID_CARDS";
	String ACTION_SEND_AN_EMAIL = "ACTION_SEND_AN_EMAIL";
	String ACTION_SPLASH = "ACTION_SPLASH";
	String ACTION_STORAGE = "ACTION_STORAGE";
	/**
	 * Open a view that displays telematics data and any needed registration steps.
	 */
	String ACTION_TELEMATICS = "ACTION_TELEMATICS";
	String ACTION_TELEMATICS_NEXTGEN_OFFER = "ACTION_TELEMATICS_NEXTGEN_OFFER";
	/**
	 * Open a view that displays third party claims dashboard.
	 */
	String ACTION_THIRD_PARTY_CLAIMANT_DASHBOARD = "ACTION_THIRD_PARTY_CLAIMANT_DASHBOARD";
	/**
	 * Open a view that displays the Towing and Labor courtesy card
	 */
	String ACTION_TOWING_AND_LABOR_COURTESY_CARD_VIEW = "ACE_ACTION_TOWING_AND_LABOR_COURTESY_CARD_VIEW";
	/**
	 * Opens a view that allow user to start password recovery process.
	 */
	String ACTION_TROUBLE_LOGGING_IN = "ACE_ACTION_TROUBLE_LOGGING_IN";
	String ACTION_UMBRELLA_WEB_VIEW = "ACTION_UMBRELLA_WEB_VIEW";
	String ACTION_UNENROLL_AUTOMATIC_PAYMENT = "ACE_ACTION_UNENROLL_AUTOMATIC_PAYMENT";
	String ACTION_UNENROLL_EBILL = "ACE_ACTION_UNENROLL_EBILL";
	String ACTION_UNENROLL_EBILL_THANK_YOU = "ACE_ACTION_UNENROLL_EBILL_THANK_YOU";
	String ACTION_UNENROLL_IN_RPM_THANK_YOU = "ACE_ACTION_UNENROLL_IN_RPM_THANK_YOU";
	/**
	 * Open a web view and prefill the value of the zip code.
	 */
	String ACTION_UNLINKED_APPEND_ZIP_CODE_WEB_VIEW = "ACTION_UNLINKED_APPEND_ZIP_CODE_WEB_VIEW";
	String ACTION_UNLINKED_WEB_VIEW = "ACTION_UNLINKED_WEB_VIEW";
	/**
	 * Open a view that allows a user to confirm changes they made to the policy contact information.
	 */
	String ACTION_UPDATE_CONTACT_CONFIRMATION = "ACE_ACTION_UPDATE_CONTACT_CONFIRMATION";
	String ACTION_UPGRADE_ON_STARTUP_DIALOG = "ACTION_UPGRADE_ON_STARTUP_DIALOG";
	/**
	 * Open a view that allows the user to access user profiles.
	 */
	String ACTION_USERS = "ACE_ACTION_USERS";
	String ACTION_USER_LEVEL_ACCOUNT_PAYMENT = "ACTION_USER_LEVEL_ACCOUNT_PAYMENT";
	/**
	 * Open a view that allows the user to edit the currently selected user
	 * profile.
	 */
	String ACTION_USER_SET_UP = "ACE_ACTION_USER_SET_UP";
	/**
	 * Open a view that allows the user to quickly enter address data
	 */
	String ACTION_USER_WORK_ADDRESS_SEARCH = "ACTION_USER_WORK_ADDRESS_SEARCH";
	/**
	 *  Open the prerequisites view before opeing carfax.
	 */
	String ACTION_VEHICLE_CARE = "ACTION_VEHICLE_CARE";
	String ACTION_VEHICLE_CARE_CARFAX_INFO = "ACTION_VEHICLE_CARE_CARFAX_INFO";
	String ACTION_VEHICLE_CARE_LEARN_MORE = "ACTION_VEHICLE_CARE_LEARN_MORE";
	/**
	 *  Open a WebView to display Carfax's features
	 */

	String ACTION_VEHICLE_CARE_WEBVIEW = "ACTION_VEHICLE_CARE_WEBVIEW";
	String ACTION_VEHICLE_DETAIL_DISPLAY = "ACE_ACTION_VEHICLE_DETAIL_GEN4";
	/**
	 * Open a view that allows the user to view official id cards
	 */
	String ACTION_VIEW_OFFICIAL_ID_CARDS = "ACE_ACTION_VIEW_OFFICIAL_ID_CARDS";
	/**
	 * Open a view that tells the user there was a problem with their embedded
	 * web session
	 */
	String ACTION_WEATHER_ALERT_LIST = "ACTION_WEATHER_ALERT_LIST";
	String ACTION_WEB_VIEW_ERROR_PAGE = "ACTION_WEB_VIEW_ERROR_PAGE";
	String ACTION_WEB_VIEW_PHOTO = "ACTION_WEB_VIEW_PHOTO";
	/**
	 * Opens the welcome question flow. This value is stored in and read from shared preferences so
	 * its string value should remain as-is for backward compatibility.
	 */
	String ACTION_WELCOME = "ACE_ACTION_GUEST_SERVICES_LANDING";
	String ACTION_YOUTHFUL_DRIVERS_SPINOFF = "ACTION_YOUTHFUL_DRIVERS_SPINOFF";
}