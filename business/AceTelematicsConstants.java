package com.geico.mobile.android.ace.geicoappbusiness.telematics;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

/**
 * Constant values for Telematics
 *
 * @author Nick Emerson
 */
public final class AceTelematicsConstants {

    public static final String DEVICE_MANUFACTURER_SAMSUNG = "SAMSUNG";
    public static final String EXPIRED_REFRESH_TOKEN = "EXPIRED_REFRESH_TOKEN";
    public static final String FORCE_TELEMATICS_REFRESH_ALERT_FAILURE_PREFERENCE_KEY = "FORCE_TELEMATICS_REFRESH_ALERT_FAILURE_PREFERENCE_KEY";
    public static final String IGNORE_BATTERY_OPTIMIZATION = "IGNORE_BATTERY_OPTIMIZATION";
    public static final String LOCATION_PERMISSION = "LOCATION_PERMISSION";
    public static final String MISSING_CACHE_ENTRY = "MISSING_CACHE_ENTRY";
    public static final String OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY = "OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY";
    public static final String OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY = "OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY";
    public static final String PHONE_NUMBER_ALREADY_ENROLLED_ALERT = "PHONE_NUMBER_ALREADY_ENROLLED";
    public static final String PHONE_PERMISSION = "PHONE_PERMISSION";
    public static final String PHYSICAL_ACTIVITY_PERMISSION = "PHYSICAL_ACTIVITY_PERMISSION";
    public static final String REFRESH_TOKEN_MISMATCH = "REFRESH_TOKEN_MISMATCH";
    public static final String REFRESH_TOKEN_WAS_INVALIDATED = "REFRESH_TOKEN_WAS_INVALIDATED";
    public static final String SCORE_SHARING = "score_sharing";
    public static final String SCORE_SHARING_NO = "NO";
    public static final String SCORE_SHARING_YES = "YES";
    public static final String TAMPERED_CREDENTIALS = "TAMPERED_CREDENTIALS";
    public static final String TELEMATICS_BASE_URL = "TELEMATICS_BASE_URL";
    public static final String TELEMATICS_CREDENTIALS = "TELEMATICS_CREDENTIALS";
    public static final String TELEMATICS_DRIVER_TYPE = "TELEMATICS_DRIVER_TYPE";
    public static final String TELEMATICS_DRIVER_ID = "TELEMATICS_GEICO_DRIVER_ID";
    public static final String TELEMATICS_DRIVER_ID_UPDATED_WITH_SAME_VALUE = "TELEMATICS_DRIVER_ID_UPDATED_WITH_SAME_VALUE";
    public static final String TELEMATICS_ENROLLED = "TELEMATICS_ENROLLED";
    public static final String TELEMATICS_FEATURE_SET_ID_UPDATE_COMPLETE = "TELEMATICS_FEATURE_SET_ID_UPDATE_COMPLETE";
    public static final String TELEMATICS_INVITE_DRIVERS_INITIATED_TIMESTAMP = "TELEMATICS_INVITE_DRIVERS_INITIATED_TIMESTAMP";
    public static final String TELEMATICS_ONBOARDING_INCOMPLETE_AIRSHIP_EVENT_TRIGGERED = "TELEMATICS_ONBOARDING_INCOMPLETE_AIRSHIP_EVENT_TRIGGERED";
    public static final String TELEMATICS_PERSISTENT_PREFERENCES = "TELEMATICS_PERSISTENT_PREFERENCES";
    public static final String TELEMATICS_PREFERENCES = "TELEMATICS";
    public static final String TELEMATICS_PUSH_ENROLLMENT_COMPLETE = "TELEMATICS_PUSH_ENROLLMENT_COMPLETE";
    public static final String TELEMATICS_RECENTLY_DISABLED = "TELEMATICS_RECENTLY_DISABLED";
    public static final String TELEMATICS_REFRESH_TOKEN = "TELEMATICS_REFRESH_TOKEN";
    public static final String TELEMATICS_REGISTRATION_INCOMPLETE_AIRSHIP_EVENT_TRIGGERED = "TELEMATICS_REGISTRATION_INCOMPLETE_AIRSHIP_EVENT_TRIGGERED";
    public static final String TELEMATICS_UNREGISTERED_SECONDARY_DRIVERS_EVENT_TRIGGERED = "TELEMATICS_UNREGISTERED_SECONDARY_DRIVERS_EVENT_TRIGGERED";
    public static final String TELEMATICS_VENDOR_DRIVER_ID = "TELEMATICS_VENDOR_DRIVER_ID";
    public static final String UNABLE_TO_REFRESH = "UNABLE_TO_REFRESH";
    public static final List<String> TELEMATICS_CREDENTIAL_UNABLE_TO_REFRESH_ALERTS =
            Arrays.asList(EXPIRED_REFRESH_TOKEN,
                    MISSING_CACHE_ENTRY,
                    TAMPERED_CREDENTIALS,
                    REFRESH_TOKEN_MISMATCH,
                    REFRESH_TOKEN_WAS_INVALIDATED,
                    UNABLE_TO_REFRESH);

    private AceTelematicsConstants() {
    }

    @StringDef({TelematicsDriverType.PRIMARY, TelematicsDriverType.SECONDARY, TelematicsDriverType.UNKNOWN})
    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TelematicsDriverType {

        String PRIMARY = "PRIMARY";
        String SECONDARY = "SECONDARY";
        String UNKNOWN = "UNKNOWN";
    }
}
