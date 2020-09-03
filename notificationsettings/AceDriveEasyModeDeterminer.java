package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.helpers;

import com.geico.mobile.android.ace.coreframework.transforming.AceBaseTransformer;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceBaseSessionStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.telematics.AceTelematicsSharedPreferencesDao;

/**
 * A transformer that determines whether user is in driver easy mode
 *
 * @author John Sung, Geico
 */
public class AceDriveEasyModeDeterminer extends AceBaseTransformer<AceRegistry, Boolean> {

    @Override
    protected Boolean convert(AceRegistry original) {
        return !new AceTelematicsSharedPreferencesDao(original).isMissingInitializationValues()
                && !isUserLoggedIn(original);
    }

    private boolean isUserLoggedIn(AceRegistry registry) {
        return registry.getSessionController().acceptVisitor(new AceBaseSessionStateVisitor<Void, Boolean>() {
            @Override
            protected Boolean visitAnyState(Void input) {
                return false;
            }

            @Override
            public Boolean visitInPolicySession(Void input) {
                return true;
            }
        });
    }

    @Override
    protected Boolean defaultTransformation() {
        return false;
    }
}
