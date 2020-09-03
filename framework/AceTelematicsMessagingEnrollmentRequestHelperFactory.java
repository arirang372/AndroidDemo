package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.telematics;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierAuthenticatedRequestFactory;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.MitTelematicsMessagingEnrollmentRequest;

/**
 * Creates and populates a {@link MitTelematicsMessagingEnrollmentRequest} with all required fields except for messaging enrollment fields
 *
 * @author John Sung, Geico
 */
public class AceTelematicsMessagingEnrollmentRequestHelperFactory extends AceTierAuthenticatedRequestFactory<MitTelematicsMessagingEnrollmentRequest, Void> {

    public AceTelematicsMessagingEnrollmentRequestHelperFactory(@NonNull AceSessionController sessionController) {
        super(sessionController);
    }

    @NonNull
    @Override
    protected AcePopulator<Void, MitTelematicsMessagingEnrollmentRequest> createPopulator() {
        return new AceBasicTelematicsRequestPopulator<>();
    }

    @NonNull
    @Override
    protected Class<MitTelematicsMessagingEnrollmentRequest> getRequestType() {
        return MitTelematicsMessagingEnrollmentRequest.class;
    }
}
