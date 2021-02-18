package com.geico.mobile.android.ace.geicoappbusiness.telematics;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.persist.AceBasicSingleBooleanSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.persist.AceSingleBooleanSharedPreferences;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsConstants.TelematicsDriverType;
import com.geico.mobile.android.ace.geicoappmodel.enums.telematics.AceTelematicsFeatureMode;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsCredentials;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsRefreshResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsValidateConfirmationCodeResponse;

import static com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsConstants.FORCE_TELEMATICS_REFRESH_ALERT_FAILURE_PREFERENCE_KEY;
import static com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsConstants.OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY;
import static com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsConstants.OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY;

/**
 * Common implementation of shared preferences APIs related to Telematics
 *
 * @author Asim Qureshi, John Sung & Nick Emerson, GEICO
 */
public class AceTelematicsSharedPreferencesDao implements AceTelematicsSharedPreferences {
    private final AceSingleBooleanSharedPreferences overrideTelematicsNextGenOfferSignUpVisibilitySettingPreferences;
    private final AceSingleBooleanSharedPreferences forceTelematicsRefreshAlertFailurePreferences;
    private final AceSingleBooleanSharedPreferences overrideTelematicsVisibilitySettingPreferences;
    private final AceTelematicsPreferences telematicsPreferences;

    public AceTelematicsSharedPreferencesDao(@NonNull AceRegistry registry) {
        this.forceTelematicsRefreshAlertFailurePreferences = createForceRefreshAlertFailureSharedPreferences(registry);
        this.overrideTelematicsVisibilitySettingPreferences = createOverrideTelematicsVisibilitySharedPreferences(registry);
        this.overrideTelematicsNextGenOfferSignUpVisibilitySettingPreferences =
                createOverrideTelematicsNextGenOfferSignUpVisibilitySharedPreferences(registry);
        this.telematicsPreferences = createTelematicsSharedPreferences(registry);
    }

    @Override
    public void clear() {
        telematicsPreferences.clear();
    }

    @VisibleForTesting
    protected AceSingleBooleanSharedPreferences createForceRefreshAlertFailureSharedPreferences(AceRegistry registry) {
        return new AceBasicSingleBooleanSharedPreferencesDao(registry, FORCE_TELEMATICS_REFRESH_ALERT_FAILURE_PREFERENCE_KEY);
    }

    @VisibleForTesting
    protected AceSingleBooleanSharedPreferences createOverrideTelematicsVisibilitySharedPreferences(AceRegistry registry) {
        return new AceBasicSingleBooleanSharedPreferencesDao(registry, OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY);
    }

    @VisibleForTesting
    protected AceSingleBooleanSharedPreferences createOverrideTelematicsNextGenOfferSignUpVisibilitySharedPreferences(AceRegistry registry) {
        return new AceBasicSingleBooleanSharedPreferencesDao(registry, OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY);
    }

    private AceTelematicsPreferences createTelematicsSharedPreferences(AceRegistry registry) {
        return new AceBasicTelematicsPreferences(registry);
    }

    @NonNull
    @Override
    public String getBaseUrl() {
        return telematicsPreferences.getBaseUrl();
    }

    @NonNull
    @Override
    @TelematicsDriverType
    public String getDriverType() {
        return telematicsPreferences.getDriverType();
    }

    @Override
    public void setDriverType(@NonNull @TelematicsDriverType String driverType) {
        telematicsPreferences.setDriverType(driverType);
    }

    @NonNull
    @Override
    public String getDriverId() {
        return telematicsPreferences.getDriverId();
    }

    @Override
    public long getInviteDriversInitiatedTimestamp() {
        return telematicsPreferences.getInviteDriversInitiatedTimestamp();
    }

    @Override
    public void setInviteDriversInitiatedTimestamp(long inviteDriversTimestamp) {
        telematicsPreferences.setInviteDriversInitiatedTimestamp(inviteDriversTimestamp);
    }

    @NonNull
    @Override
    public String getPolicyNumber() {
        return telematicsPreferences.getPolicyNumber();
    }

    @NonNull
    @Override
    public String getRefreshToken() {
        return telematicsPreferences.getRefreshToken();
    }

    @NonNull
    @Override
    public AceTelematicsCredentials getTelematicsCredentials() {
        return telematicsPreferences.getTelematicsCredentials();
    }

    @NonNull
    @Override
    public String getVendorDriverId() {
        return telematicsPreferences.getVendorDriverId();
    }

    @Override
    public void setVendorDriverId(@NonNull String vendorDriverId) {
        telematicsPreferences.setVendorDriverId(vendorDriverId);
    }

    @Override
    public boolean hasDriverPreviouslyRegistered() {
        return telematicsPreferences.hasDriverPreviouslyRegistered();
    }

    @Override
    public boolean isFeatureSetIdUpdateComplete() {
        return telematicsPreferences.isFeatureSetIdUpdateComplete();
    }

    @Override
    public void setFeatureSetIdUpdateComplete(boolean isComplete) {
        telematicsPreferences.setFeatureSetIdUpdateComplete(isComplete);
    }

    @Override
    public boolean isMissingBaseUrl() {
        return telematicsPreferences.isMissingBaseUrl();
    }

    @Override
    public boolean isMissingStartupCredentials() {
        return telematicsPreferences.isMissingStartupCredentials();
    }

    @Override
    public boolean isOnboardingIncompleteAirshipEventTriggered() {
        return telematicsPreferences.isOnboardingIncompleteAirshipEventTriggered();
    }

    @Override
    public void setOnboardingIncompleteAirshipEventTriggered(boolean onboardingIncompleteAirshipEventTriggered) {
        telematicsPreferences.setOnboardingIncompleteAirshipEventTriggered(onboardingIncompleteAirshipEventTriggered);
    }

    @Override
    public boolean isPermissionAcknowledged(@NonNull String permissionKey) {
        return telematicsPreferences.isPermissionAcknowledged(permissionKey);
    }

    @Override
    public boolean isPushEnrollmentComplete() {
        return telematicsPreferences.isPushEnrollmentComplete();
    }

    @Override
    public void setPushEnrollmentComplete(boolean isPushEnrollmentComplete) {
        telematicsPreferences.setPushEnrollmentComplete(isPushEnrollmentComplete);
    }

    @Override
    public boolean isRegistrationIncompleteAirshipEventTriggered() {
        return telematicsPreferences.isRegistrationIncompleteAirshipEventTriggered();
    }

    @Override
    public void setRegistrationIncompleteAirshipEventTriggered(boolean registrationIncompleteEventTriggered) {
        telematicsPreferences.setRegistrationIncompleteAirshipEventTriggered(registrationIncompleteEventTriggered);
    }

    @Override
    public boolean isTelematicsRecentlyDisabled() {
        return telematicsPreferences.isTelematicsRecentlyDisabled();
    }

    @Override
    public void setTelematicsRecentlyDisabled(@NonNull AceTelematicsFeatureMode mode) {
        telematicsPreferences.setTelematicsRecentlyDisabled(mode);
    }

    @Override
    public boolean isTelematicsVisibilityOverridden() {
        return overrideTelematicsVisibilitySettingPreferences.isPreferenceEnabled();
    }

    @Override
    public boolean isUnregisteredSecondaryDriversAirshipEventTriggered() {
        return telematicsPreferences.isUnregisteredSecondaryDriversAirshipEventTriggered();
    }

    @Override
    public void setUnregisteredSecondaryDriversAirshipEventTriggered(boolean unregisteredSecondaryDriversEventTriggered) {
        telematicsPreferences.setUnregisteredSecondaryDriversAirshipEventTriggered(unregisteredSecondaryDriversEventTriggered);
    }

    @Override
    public void setPermissionAcknowledged(@NonNull String permissionKey) {
        telematicsPreferences.setPermissionAcknowledged(permissionKey);
    }

    @Override
    public void setTelematicsCredentialDataFrom(@NonNull MitTelematicsRefreshResponse response) {
        telematicsPreferences.setTelematicsCredentialDataFrom(response);
    }

    @Override
    public void setTelematicsCredentialDataFrom(@NonNull MitTelematicsValidateConfirmationCodeResponse response) {
        telematicsPreferences.setTelematicsCredentialDataFrom(response);
    }

    @Override
    public boolean shouldForceRefreshAlertFailure() {
        return forceTelematicsRefreshAlertFailurePreferences.isPreferenceEnabled();
    }

    @Override
    public boolean isTelematicsNextGenOfferSignUpVisibilityOverridden() {
        return overrideTelematicsNextGenOfferSignUpVisibilitySettingPreferences.isPreferenceEnabled();
    }
}
