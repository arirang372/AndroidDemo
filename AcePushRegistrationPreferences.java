package com.geico.mobile.android.ace.geicoappbusiness.pushnotification;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.persist.AceBaseSharedPreferences;

import static com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceMessagingConstants.SERVICE_ENROLLMENT_NO;

/**
 * SharedPreferences to cache the push enrollment preferences to be sent to the
 * {@link com.geico.mobile.android.ace.mitsupport.mitmodel.MitMessagingEnrollmentRequest}.
 *
 * @author Mahmudul Hasan, GEICO.
 */
public class AcePushRegistrationPreferences extends AceBaseSharedPreferences {

    private static final String PUSH_POLICY_REGISTRATION_KEY = "PUSH_POLICY_REGISTRATION_KEY";
    private static final String PUSH_REGISTRATION_PREFERENCES = "PUSH_REGISTRATION_PREFERENCES";
    private static final String PUSH_TELEMATICS_REGISTRATION_KEY = "PUSH_TELEMATICS_REGISTRATION_KEY";
    private static final String PUSH_WEATHER_REGISTRATION_KEY = "PUSH_WEATHER_REGISTRATION_KEY";
    private static final String IS_ENROLLED_DRIVEEASY_PUSH_NOTIFICATION = "IS_ENROLLED_DRIVEEASY_PUSH_NOTIFICATION";

    public AcePushRegistrationPreferences(@NonNull AceRegistry registry) {
        super(registry, PUSH_REGISTRATION_PREFERENCES);
    }

    private String getPolicyPreferenceKey(@NonNull String policy) {
        return policy + PUSH_POLICY_REGISTRATION_KEY;
    }

    private String getTelematicsPreferenceKey(@NonNull String policy) {
        return policy + PUSH_TELEMATICS_REGISTRATION_KEY;
    }

    private String getWeatherPreferenceKey(@NonNull String policy) {
        return policy + PUSH_WEATHER_REGISTRATION_KEY;
    }

    public void storeIsEnrolledDriveEasyNotification(@NonNull String policyKey, boolean value) {
        write(policyKey + IS_ENROLLED_DRIVEEASY_PUSH_NOTIFICATION, String.valueOf(value));
    }

    public boolean isEnrolledDriveEasyNotification(@NonNull String policyKey) {
        String isEnrolledDriveEasyNotification = read(policyKey + IS_ENROLLED_DRIVEEASY_PUSH_NOTIFICATION, "false");
        if(isEnrolledDriveEasyNotification.isEmpty()) return false;
        return Boolean.parseBoolean(read(policyKey + IS_ENROLLED_DRIVEEASY_PUSH_NOTIFICATION, "false"));
    }

    @NonNull
    public String retrievePolicyPushPreferenceForPolicy(@NonNull String policy) {
        return read(getPolicyPreferenceKey(policy), SERVICE_ENROLLMENT_NO);
    }

    @NonNull
    public String retrieveTelematicsPushPreferenceForPolicy(@NonNull String policy) {
        return read(getTelematicsPreferenceKey(policy), SERVICE_ENROLLMENT_NO);
    }

    @NonNull
    public String retrieveWeatherPushPreferenceForPolicy(@NonNull String policy) {
        return read(getWeatherPreferenceKey(policy), SERVICE_ENROLLMENT_NO);
    }

    public void storePolicyPushPreferenceForPolicy(@NonNull String policy, @NonNull String value) {
        write(getPolicyPreferenceKey(policy), value);
    }

    public void storeTelematicsPushPreferenceForPolicy(@NonNull String policy, @NonNull String value) {
        write(getTelematicsPreferenceKey(policy), value);
    }

    public void storeWeatherPushPreferenceForPolicy(@NonNull String policy, @NonNull String value) {
        write(getWeatherPreferenceKey(policy), value);
    }

}
