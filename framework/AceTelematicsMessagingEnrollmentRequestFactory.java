package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.telematics;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.patterns.AceCustomFactory;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentType;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.pushnotification.AcePushRegistrationMessagingRequestFactory;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsMessagingEnrollmentRequest;

/**
 * Creates and populates a complete {@link MitTelematicsMessagingEnrollmentRequest}
 *
 * @author John Sung, Geico
 */
public class AceTelematicsMessagingEnrollmentRequestFactory implements AceCustomFactory<MitTelematicsMessagingEnrollmentRequest, AcePushMessagingEnrollmentType> {

    private final AceRegistry registry;

    public AceTelematicsMessagingEnrollmentRequestFactory(@NonNull AceRegistry registry) {
        this.registry = registry;
    }

    @Override
    public MitTelematicsMessagingEnrollmentRequest create(AcePushMessagingEnrollmentType options) {
        MitTelematicsMessagingEnrollmentRequest request = new AceTelematicsMessagingEnrollmentRequestHelperFactory(registry.getSessionController()).create();
        request.setMessagingEnrollment(new AcePushRegistrationMessagingRequestFactory(request.getTelematicsCredentials().getPolicyNumber()).create(options));
        return request;
    }
}
