package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.pushnotification;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.application.AceBasicContextRegistryLocator;
import com.geico.mobile.android.ace.coreframework.device.AceDeviceInformationDao;
import com.geico.mobile.android.ace.coreframework.patterns.AceCustomFactory;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushDao;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentType;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingFacade;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceUserSession;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitMessagingEnrollmentRequest;

/**
 * Factory for creating Airship Messaging requests for registering a device and policy with TIER via
 * its ChannelID, and for enrolling or un-enrolling in PUSH notifications.
 *
 * @author Bogdan Hampu, 3Pillar Global
 */
public class AcePushRegistrationMessagingRequestFactory implements AceCustomFactory<MitMessagingEnrollmentRequest, AcePushMessagingEnrollmentType> {

    private final AceRegistry registry = AceBasicContextRegistryLocator.DEFAULT.locateRegistry();
    private final String policyNumber;

    public AcePushRegistrationMessagingRequestFactory() {
        this("");
    }

    public AcePushRegistrationMessagingRequestFactory(@NonNull String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @Override
    public MitMessagingEnrollmentRequest create(@NonNull AcePushMessagingEnrollmentType enrollmentType) {
        MitMessagingEnrollmentRequest request = getUserSession().createAuthenticatedRequest(MitMessagingEnrollmentRequest.class);
        createRegistrationMessagingRequestPopulator().populate(enrollmentType, request);
        createEnrollmentRequestPopulator().populate(enrollmentType, request);
        return request;
    }

    private AcePushEnrollmentRequestPopulator createEnrollmentRequestPopulator() {
        return TextUtils.isEmpty(policyNumber) ? new AcePushEnrollmentRequestPopulator(getPushDao(), getUserSession()) :
                new AcePushEnrollmentRequestPopulator(getPushDao(), getUserSession(), policyNumber);
    }

    private AcePushRegistrationMessagingRequestPopulator createRegistrationMessagingRequestPopulator() {
        AcePushMessagingFacade pushMessagingFacade = getPushMessagingFacade();
        AceDeviceInformationDao deviceInformationDao = getDeviceInformationDao();
        return new AcePushRegistrationMessagingRequestPopulator(pushMessagingFacade.getChannelId(), deviceInformationDao.getOperatingSystem(),
                deviceInformationDao.getOperatingSystemVersion(), pushMessagingFacade.getPushToken());
    }

    private AceDeviceInformationDao getDeviceInformationDao() {
        return registry.getDeviceInformationDao();
    }

    private AcePushDao getPushDao() {
        return registry.getPushDao();
    }

    private AcePushMessagingFacade getPushMessagingFacade() {
        return registry.getPushMessagingFacade();
    }

    private AceUserSession getUserSession() {
        return registry.getSessionController().getUserSession();
    }
}