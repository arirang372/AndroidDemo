package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceEnrollWithDeviceUnlockDialog;

import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.CALL_TO_MAKE_CHANGES;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.CLAIMS_UNDER_CONSTRUCTION_ERROR;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.ENROLL_WITH_DEVICE_UNLOCK;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.GENERIC_SERVICE_ERROR;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.NETWORK_UNAVAILABLE;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.SERVICE_ERROR_WITH_ALERTS;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.SESSION_EXPIRED;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.WAIT;
import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.WAIT_STALLER;

/**
 * An object that monitors the {@link android.opengl.Visibility} of a
 * {@link DialogFragment} </br>
 *
 * @author Kal Tadesse
 */
public class AceBasicDialogMonitor implements AceDialogMonitor {

    private final FragmentManager fragmentManager;

    public AceBasicDialogMonitor(@NonNull FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void dismissDialog(@DialogTag @NonNull String tag) {
        //The purpose of this block is:
        //1 - Dismiss the existing dialog in the transaction.
        //2 - Block multiple instances of the dialog from being shown
        //    in case of orientation change or any scenario that will have
        //    recreated the dialog in multiplicity.
        DialogFragment dialog = (DialogFragment) getFragmentManager().findFragmentByTag(tag);
        if (dialog == null) return;
        dialog.dismiss();
    }

    protected FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    @Override
    public void showDialog(@DialogTag @NonNull String tag, @NonNull String displayText) {
        dismissDialog(tag);
        switch (tag) {
            case CALL_TO_MAKE_CHANGES:
                new AceCallToMakeChangesDialog().show(getFragmentManager(), tag);
                break;
            case CLAIMS_UNDER_CONSTRUCTION_ERROR:
                new AceClaimsUnderConstructionErrorDialog(displayText).show(getFragmentManager(), tag);
                break;
            case ENROLL_WITH_DEVICE_UNLOCK:
                new AceEnrollWithDeviceUnlockDialog().show(getFragmentManager(), tag);
                break;
            case GENERIC_SERVICE_ERROR:
                new AceGenericServiceErrorDialog().show(getFragmentManager(), tag);
                break;
            case NETWORK_UNAVAILABLE:
                new AceNetworkUnavailableDialog().show(getFragmentManager(), tag);
                break;
            case SESSION_EXPIRED:
                new AceSessionExpiredDialog(displayText).show(getFragmentManager(), tag);
                break;
            case SERVICE_ERROR_WITH_ALERTS:
                new AceServiceErrorWithAlertsDialog(displayText).show(getFragmentManager(), tag);
                break;
            case WAIT:
                new AceWaitDialog().show(getFragmentManager(), tag);
                break;
            case WAIT_STALLER:
                new AceWaitStaller().show(getFragmentManager(), tag);
                break;
            default:
                showSpecializedDialog(tag, displayText);
                break;
        }
    }

    protected void showSpecializedDialog(@DialogTag @NonNull String tag, @NonNull String displayText) {
        // do nothing but subclasses that need to add uncommon dialogs should override this method
    }
}