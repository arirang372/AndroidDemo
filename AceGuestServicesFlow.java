package com.geico.mobile.android.ace.geicoappbusiness.firststart;

import com.geico.mobile.android.ace.geicoappbusiness.firststart.AceGuestServicesPageEnum.AceGuestServicesPageVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.AceFlowType;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceBaseFlowModel;

import androidx.annotation.NonNull;

/**
 * This is a model object to communicate between AceGuestServicesLandingActivity and AceGuestServicesLandingFragment
 *
 * @author Mahmudul Hasan - GEICO
 */
public class AceGuestServicesFlow extends AceBaseFlowModel {

	public static final String GUEST_SERVICES_EVENT_ACTIVATE_ACCOUNT = "GUEST_SERVICES_EVENT_ACTIVATE_ACCOUNT";
	public static final String GUEST_SERVICES_EVENT_LETS_GET_YOU_LOGGED_IN = "GUEST_SERVICES_EVENT_LETS_GET_YOU_LOGGED_IN";
	public static final String GUEST_SERVICES_EVENT_POLICY_HOLDER_QUESTIONS = "GUEST_SERVICES_EVENT_POLICY_HOLDER_QUESTIONS";
	public static final String GUEST_SERVICES_EVENT_WELCOME = "GUEST_SERVICES_EVENT_WELCOME";
	public static final String GUEST_SERVICES_EVENT_TELEMATICS_ENROLLMENT_QUESTIONS = "GUEST_SERVICES_EVENT_TELEMATICS_ENROLLMENT_QUESTIONS";
	private AceGuestServicesPageEnum currentPage = AceGuestServicesPageEnum.WELCOME_PAGE;

	public void acceptVisitor(@NonNull AceGuestServicesPageVisitor visitor) {
		currentPage.acceptVisitor(visitor);
	}

	@Override
	public AceFlowType getFlowType() {
		return AceFlowType.GUEST_SERVICES;
	}

	public void setCurrentPage(@NonNull AceGuestServicesPageEnum currentPage) {
		this.currentPage = currentPage;
	}
}
