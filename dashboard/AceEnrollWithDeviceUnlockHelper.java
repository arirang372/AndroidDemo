package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.repository;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.rules.AceDoNothingRule;
import com.geico.mobile.android.ace.coreframework.rules.AceOtherwiseRule;
import com.geico.mobile.android.ace.coreframework.rules.AceRule;
import com.geico.mobile.android.ace.geicoappbusiness.derivations.AceDeviceSecuredDerivation;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginConstants;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.login.AceLoginSettingsToIsEnrolledForDeviceUnlock;
import com.geico.mobile.android.ace.geicoappbusiness.transforming.login.AceLoginSettingsToIsEnrolledForFingerprint;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;

import java.util.Arrays;
import java.util.List;

/**
 * Object that evaluates rules to determine if the Enroll
 * With Device Unlock dialog will be shown to the user.
 *
 * @author Richard Peirson and John Sung, GEICO
 */
public class AceEnrollWithDeviceUnlockHelper {

    public void considerDisplayingDialogWith(@NonNull AceTierRepository repository) {
        repository.getRuleEngine().applyFirst(createRules(), repository);
    }

    private List<AceRule<AceTierRepository>> createRules() {
        return Arrays.asList(
                new AceDoNothingIfReturningFromMobileLinkedLogin(),
                new AceDoNothingIfDeviceNotSecured(),
                new AceDoNothingIfLoginThroughDeviceUnlockDisabled(),
                new AceDoNothingIfAlreadyEnrolledWithDeviceUnlock(),
                new AceDoNothingIfEnrollWithDeviceUnlockOfferSeen(),
                new AceDoNothingIfEnrolledWithKeepMeLoggedIn(),
                new AceDoNothingIfEnrolledWithFingerprint(),
                new AceOtherwiseShowEnrollWithDeviceUnlockDialog()
        );
    }

    static class AceDoNothingIfAlreadyEnrolledWithDeviceUnlock extends AceDoNothingRule<AceTierRepository> {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            AceLoginSettingsDao loginSettingsDao = context.getLoginSettingsDao();
            return new AceLoginSettingsToIsEnrolledForDeviceUnlock().transform(loginSettingsDao)
                    && loginSettingsDao.retrieveUserId().equalsIgnoreCase(loginSettingsDao.retrieveFingerprintUserId());
        }
    }

    static class AceDoNothingIfDeviceNotSecured extends AceDoNothingRule<AceTierRepository> {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            return !new AceDeviceSecuredDerivation().deriveValueFrom(context.getApplication());
        }
    }

    static class AceDoNothingIfEnrollWithDeviceUnlockOfferSeen extends AceDoNothingRule<AceTierRepository> {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            return context.getLoginSettingsDao().offerSeenForEnrollWithDeviceUnlock();
        }
    }

    static class AceDoNothingIfEnrolledWithFingerprint extends AceDoNothingRule<AceTierRepository> {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            return new AceLoginSettingsToIsEnrolledForFingerprint().transform(context.getLoginSettingsDao());
        }
    }

    static class AceDoNothingIfEnrolledWithKeepMeLoggedIn extends AceDoNothingRule<AceTierRepository> implements AceLoginConstants {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            return isUserConnectedByKeepMeLoggedIn(context)
                    && isTokenRetainedForAutomaticLogin(context);
        }

        private boolean isTokenRetainedForAutomaticLogin(AceTierRepository repository) {
            return AUTOMATIC_LOGIN.equals(repository.getLoginSettingsDao().retrieveReasonForRetainingRefreshToken());
        }

        private boolean isUserConnectedByKeepMeLoggedIn(AceTierRepository repository) {
            return repository.getApplicationSession().getLoginFlow().getUserConnectionTechnique().isKeepMeLoggedIn();
        }
    }

    static class AceDoNothingIfLoginThroughDeviceUnlockDisabled extends AceDoNothingRule<AceTierRepository> {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            return context.getFeatureConfiguration().getModeForLoginThroughDeviceUnlock().isDisabled();
        }
    }

    static class AceDoNothingIfReturningFromMobileLinkedLogin extends AceDoNothingRule<AceTierRepository> {

        @Override
        public boolean isApplicable(@NonNull AceTierRepository context) {
            return context.getSessionController().getFullSiteStrategy().getLinkedLoginState().isReturningToMobile();
        }
    }

    static class AceOtherwiseShowEnrollWithDeviceUnlockDialog extends AceOtherwiseRule<AceTierRepository> {

        @Override
        public void applyTo(@NonNull AceTierRepository context) {
            context.getApplicationSession().setKeepMeLoggedInDialogHasBeenDisplayed(true);
            context.openDialog(DialogTag.ENROLL_WITH_DEVICE_UNLOCK);
            context.recordScreenUnlockMetrics();
        }
    }
}