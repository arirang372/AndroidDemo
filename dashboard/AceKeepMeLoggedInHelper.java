package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.enums.userconnectiontechnique.AcePickyUserConnectionTechniqueVisitor;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;

/**
 * Object used to show the Keep Me Logged In dialog to the user when it is appropriate to do so.
 * Scope is package-private since it is only used by AceDashboardRepository
 *
 * @author Richard Peirson, GEICO
 */
public class AceKeepMeLoggedInHelper {

    public void considerDisplayingDialogWith(@NonNull AceTierRepository repository) {
        AceApplicationSession applicationSession = repository.getApplicationSession();
        applicationSession.getLoginFlow().acceptVisitor(new AcePickyUserConnectionTechniqueVisitor<Void>() {
            @Override
            public Void visitLoginEachTime(Void input) {
                if (!(applicationSession.hasKeepMeLoggedInDialogHasBeenDisplayed())) {
                    applicationSession.setKeepMeLoggedInDialogHasBeenDisplayed(true);
                    repository.openDialog(DialogTag.KEEP_ME_LOGGED_IN);
                }
                return NOTHING;
            }
        });
    }
}