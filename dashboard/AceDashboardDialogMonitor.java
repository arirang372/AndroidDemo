package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view;

import android.app.Activity;

import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.viewmodel.AceDashboardViewModelAfterDownload;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceBasicDialogMonitor;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.AceChangeDeviceNotificationSettingsDialog;
import com.google.android.gms.common.GoogleApiAvailability;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.CHANGE_DEVICE_NOTIFICATION_SETTINGS;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.ENROLL_WITH_DEVICE_UNLOCK;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.GOOGLE_API_AVAILABILITY_ERROR;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.KEEP_ME_LOGGED_IN;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.MAKE_PAYMENT;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.PAYMENT_FAILURE;

/**
 * Dialog monitor that in addition to handling all the commonly used dialogs, also allows
 * for payment specific dialogs to be shown to the user.
 *
 * @author Gopal Palanisamy
 */
class AceDashboardDialogMonitor extends AceBasicDialogMonitor {

	AceDashboardDialogMonitor(@NonNull FragmentManager fragmentManager) {
		super(fragmentManager);
	}

	private Activity getActivity() {
		return getFragmentManager().getFragments().get(0).getActivity();
	}

	@Override
	protected void showSpecializedDialog(@DialogTag @NonNull String tag, @NonNull String displayText) {
		switch (tag) {
			case CHANGE_DEVICE_NOTIFICATION_SETTINGS:
				new AceChangeDeviceNotificationSettingsDialog<AceDashboardViewModelAfterDownload>().show(getFragmentManager(), tag);
				break;
			case GOOGLE_API_AVAILABILITY_ERROR:
				int status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());
				GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), status, 1).show();
				break;
			case KEEP_ME_LOGGED_IN:
				new AceKeepMeLoggedInDialog().show(getFragmentManager(), tag);
				break;
			case MAKE_PAYMENT:
				new AceMakePaymentDialog().show(getFragmentManager(), tag);
				break;
			case PAYMENT_FAILURE:
				new AcePaymentFailureDialog().show(getFragmentManager(), tag);
				break;
			default:
				break;
		}
	}
}