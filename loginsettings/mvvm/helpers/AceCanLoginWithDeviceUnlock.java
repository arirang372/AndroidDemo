package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.patterns.AceDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.derivations.AceDeviceSecuredDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController;

/**
 * An object that determines if user may use login using device unlock.
 *
 * @author John Sung, GEICO
 */
public class AceCanLoginWithDeviceUnlock implements AceDerivation<AceRegistry, Boolean> {

    @Override
    public Boolean deriveValueFrom(@NonNull AceRegistry subject) {
        return subject.getFeatureConfiguration().getModeForLoginThroughDeviceUnlock().isEnabled()
                && new AceDeviceSecuredDerivation().deriveValueFrom(subject.getApplicationContext())
                && isNotReturningFromMobileLinkedLogin(subject.getSessionController());
    }

    private boolean isNotReturningFromMobileLinkedLogin(@NonNull AceSessionController sessionController) {
        return !sessionController.getFullSiteStrategy().getLinkedLoginState().isReturningToMobile();
    }
}
