package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.pushnotification;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.transforming.AceBaseTransformer;
import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceMessagingConstants;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushDao;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentFromPolicyNumberFactory;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentFromSubjectEnrollmentFactory;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AcePushMessagingEnrollmentType;
import com.geico.mobile.android.ace.geicoappbusiness.pushnotification.AceSubjectEnrollment;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceUserSession;
import com.geico.mobile.android.ace.geicoappmodel.AceInsurancePolicy;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitEnrollment;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitMessagingEnrollmentRequest;

import java.util.Collections;
import java.util.List;

/**
 * Populates the {@link MitMessagingEnrollmentRequest} with a list of enrollments, stored locally or
 * retrieved from user session in case of first enrollment. Based on this list, the overall value of
 * the enrollment (Y or N) is determined and added to the request.
 *
 * @author Bogdan Hampu, 3Pillar Global
 */
public class AcePushEnrollmentRequestPopulator implements AcePopulator<AcePushMessagingEnrollmentType, MitMessagingEnrollmentRequest>, AceMessagingConstants {

    private final AcePushDao pushDao;
    private final AceUserSession userSession;
    private final String policyNumber;

    public AcePushEnrollmentRequestPopulator(@NonNull AcePushDao pushDao, @NonNull AceUserSession userSession) {
        this(pushDao, userSession, "");
    }

    public AcePushEnrollmentRequestPopulator(@NonNull AcePushDao pushDao, @NonNull AceUserSession userSession, @NonNull String policyNumber) {
        this.pushDao = pushDao;
        this.userSession = userSession;
        this.policyNumber = policyNumber;
    }

    private String determineOverallEnrollment(List<MitEnrollment> enrollments) {
        return isAnyEnrollmentYes(enrollments) ? SERVICE_ENROLLMENT_YES : SERVICE_ENROLLMENT_NO;
    }

    private List<AceInsurancePolicy> getAuthorizedActivePolicies() {
        return AceBasicEnumerator.DEFAULT.select(userSession.getAuthorizedPolicies(), policy -> !policy.getPolicyStatus().isCancelled());
    }

    private List<MitEnrollment> getEnrollmentList(@NonNull AcePushMessagingEnrollmentType enrollmentType, @NonNull String originatingPolicyNumber) {
        return isFirstEnrollmentOrUserNotLoggedIn() ? toMitEnrollmentsFromAuthorizedPolicies(enrollmentType, originatingPolicyNumber) : toMitEnrollmentsFromStoredEnrollments(enrollmentType, originatingPolicyNumber);
    }

    private List<AceSubjectEnrollment> getStoredEnrollmentsForUserId() {
        return pushDao.retrieveEnrollmentsForUserId(userSession.getPolicySession().getUserId());
    }

    private boolean isAnyEnrollmentYes(List<MitEnrollment> enrollments) {
        return AceBasicEnumerator.DEFAULT.anySatisfy(enrollments, element -> SERVICE_ENROLLMENT_YES.equals(element.getEnroll()));
    }

    private boolean isFirstEnrollmentOrUserNotLoggedIn() {
        return getStoredEnrollmentsForUserId().isEmpty();
    }

    @Override
    public void populate(AcePushMessagingEnrollmentType source, MitMessagingEnrollmentRequest target) {
        List<MitEnrollment> enrollments = getEnrollmentList(source, !TextUtils.isEmpty(policyNumber) ? policyNumber : target.getCredentials().getPolicyNumber());
        target.setEnablePushNotifications(determineOverallEnrollment(enrollments));
        target.setEnrollments(enrollments);
    }

    private List<MitEnrollment> toMitEnrollmentsFromAuthorizedPolicies(AcePushMessagingEnrollmentType enrollmentType, String originatingPolicyNumber) {
        if (getAuthorizedActivePolicies().isEmpty() && !TextUtils.isEmpty(policyNumber)) {
            return Collections.singletonList(new AcePushMessagingEnrollmentFromPolicyNumberFactory(enrollmentType, true, originatingPolicyNumber).create(policyNumber));
        }
        return new AceBaseTransformer<AceInsurancePolicy, MitEnrollment>() {
            @Override
            protected MitEnrollment convert(AceInsurancePolicy policy) {
                return new AcePushMessagingEnrollmentFromPolicyNumberFactory(enrollmentType, true, originatingPolicyNumber).create(policy.getNumber());
            }
        }.transformAll(getAuthorizedActivePolicies());
    }

    private List<MitEnrollment> toMitEnrollmentsFromStoredEnrollments(AcePushMessagingEnrollmentType enrollmentType, String originatingPolicyNumber) {
        return new AceBaseTransformer<AceSubjectEnrollment, MitEnrollment>() {
            @Override
            protected MitEnrollment convert(AceSubjectEnrollment storedEnrollment) {
                return new AcePushMessagingEnrollmentFromSubjectEnrollmentFactory(enrollmentType, false, originatingPolicyNumber).create(storedEnrollment);
            }
        }.transformAll(getStoredEnrollmentsForUserId());
    }
}