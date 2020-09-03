package com.geico.mobile.android.ace.mitsupport.mitmodel.telematics;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.mitsupport.mitmodel.MitMessagingEnrollmentRequest;

/**
 * Enroll or Unenroll Drive Easy Alert Push Notification setting when user is complete on 2FA but is not
 * logged into Geico mobile app
 *
 * @author John Sung, Geico
 */
public class MitTelematicsMessagingEnrollmentRequest extends MitBaseTelematicsRequest {

    private MitMessagingEnrollmentRequest messagingEnrollment = new MitMessagingEnrollmentRequest();

    @NonNull
    public MitMessagingEnrollmentRequest getMessagingEnrollment() {
        return messagingEnrollment;
    }

    public void setMessagingEnrollment(@NonNull MitMessagingEnrollmentRequest messagingEnrollment) {
        this.messagingEnrollment = messagingEnrollment;
    }
}

