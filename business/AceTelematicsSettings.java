package com.geico.mobile.android.ace.geicoappbusiness.telematics;

/**
 * API that provides Telematics user settings
 *
 * @author Asim Qureshi and John Sung, GEICO
 */
public interface AceTelematicsSettings {

    /**
     * determines whether telematics functionality is to be overridden by the user through debug setting (non-prod only)
     *
     * @return boolean representing whether telematics functionality is to be overridden by the user
     */
    boolean isTelematicsVisibilityOverridden();

    /**
     * Determines whether telematics credentials refresh is overridden to fail with an invalidation alert through debug setting (non-prod only)
     *
     * @return boolean representing whether the MCIT refresh service response should always be treated as an alert failure
     */
    boolean shouldForceRefreshAlertFailure();


    /**
     * determines whether telematics nextgen offer sign up option is to be overridden by the user through debug setting (non-prod only)
     *
     * @return boolean representing whether telematics nextgen offer entry point is to be overridden by the user
     */
    boolean isTelematicsNextGenOfferSignUpVisibilityOverridden();
}
