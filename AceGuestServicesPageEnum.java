package com.geico.mobile.android.ace.geicoappbusiness.firststart;

import com.geico.mobile.android.ace.coreframework.patterns.AceVisitor;

import androidx.annotation.NonNull;

/**
 * This enum indicates the pages for the Guest Services feature.
 *
 * @author Mahmudul Hasan - GEICO
 */
public enum AceGuestServicesPageEnum {

	WELCOME_PAGE(0) {
		@Override
		public <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input) {
			return visitor.visitWelcomePage(input);
		}
	},
	POLICY_HOLDER_PAGE(1) {
		@Override
		public <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input) {
			return visitor.visitPolicyHolderPage(input);
		}
	},
	ACTIVATE_ACCOUNT_PAGE(2) {
		@Override
		public <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input) {
			return visitor.visitActivateAccountPage(input);
		}
	},
	TELEMATICS_ENROLLMENT_PAGE(3) {
		@Override
		public <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input) {
			return visitor.visitTelematicsEnrollmentPage(input);
		}
	},
	LETS_GET_YOU_LOGGED_IN_PAGE(4) {
		@Override
		public <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input) {
			return visitor.visitLetsGetYouLoggedInPage(input);
		}
	},
	INTENT_PAGE(9999) {
		@Override
		public <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input) {
			return visitor.visitIntentPage(input);
		}
	};

	public interface AceGuestServicesPageVisitor<I, O> extends AceVisitor {

		O visitActivateAccountPage(I input);

		O visitIntentPage(I input);

		O visitLetsGetYouLoggedInPage(I input);

		O visitPolicyHolderPage(I input);

		O visitTelematicsEnrollmentPage(I input);

		O visitWelcomePage(I input);
	}

	@NonNull
	public static AceGuestServicesPageEnum valueOf(int index) {
		try {
			return values()[index];
		} catch (IndexOutOfBoundsException e) {
			return INTENT_PAGE;
		}
	}

	private int index;

	AceGuestServicesPageEnum(int index) {
		this.index = index;
	}

	public abstract <I, O> O acceptVisitor(AceGuestServicesPageVisitor<I, O> visitor, I input);

	public <O> O acceptVisitor(AceGuestServicesPageVisitor<Void, O> visitor) {
		return acceptVisitor(visitor, AceVisitor.NOTHING);
	}

	public int getIndex() {
		return index;
	}
}
