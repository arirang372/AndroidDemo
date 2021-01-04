package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceBasicDialogMonitor;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.AceUserSettingsCallToMakeChangesDialog;

import static com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag.CALL_TO_MAKE_CHANGES;

/**
 * A Dialog monitor that in addition to handling all the commonly used dialogs, also allows
 * for specific dialogs on the user settings page to be shown to the user.
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsDialogMonitor extends AceBasicDialogMonitor {

    public AceUserSettingsDialogMonitor(@NonNull FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    protected void showSpecializedDialog(@DialogTag @NonNull String tag, @NonNull String displayText) {
        switch (tag) {
            case CALL_TO_MAKE_CHANGES:
                new AceUserSettingsCallToMakeChangesDialog().show(getFragmentManager(), tag);
                break;
            default:
                break;
        }
    }
}
