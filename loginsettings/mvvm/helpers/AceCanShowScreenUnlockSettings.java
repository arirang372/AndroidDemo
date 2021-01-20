package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import com.geico.mobile.android.ace.coreframework.patterns.AceDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceFingerprintDetector;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoappbusiness.permission.AcePermissionCategoryManager;

/**
 * An object that determines whether to show screen unlock settings options to user
 *
 * @author John Sung, Geico
 */
public class AceCanShowScreenUnlockSettings implements AceDerivation<AceRegistry, Boolean> {
    @Override
    public Boolean deriveValueFrom(AceRegistry subject) {
        AceLoginSettingsDao loginSettingsDao = new AceLoginSharedPreferencesDao(subject);
        return isAlternateLoginMethodAvailable(subject, loginSettingsDao) && isEligibleForFingerprint(loginSettingsDao);
    }

    private boolean isAlternateLoginMethodAvailable(AceRegistry subject, AceLoginSettingsDao loginSettingsDao) {
        return isTouchIdAvailable(subject.getFingerprintDetector(), loginSettingsDao, subject.getPermissionCategoryManager()) || new AceCanLoginWithDeviceUnlock().deriveValueFrom(subject);
    }

    private boolean isTouchIdAvailable(AceFingerprintDetector fingerprintDetector, AceLoginSettingsDao loginSettingsDao, AcePermissionCategoryManager permissionManager) {
        return fingerprintDetector.isFingerprintSetup(loginSettingsDao, permissionManager);
    }

    private boolean isEligibleForFingerprint(AceLoginSettingsDao loginSettingsDao) {
        String currentFingerprintUserId = loginSettingsDao.retrieveFingerprintUserId();
        return currentFingerprintUserId.isEmpty() || currentFingerprintUserId.equalsIgnoreCase(loginSettingsDao.retrieveUserId());
    }
}
