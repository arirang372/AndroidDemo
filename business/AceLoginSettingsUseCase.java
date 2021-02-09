package com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings;

/**
 * API used to invoke any login-settings-related service calls
 *
 * @author John Sung, Geico
 */
public interface AceLoginSettingsUseCase extends AceChangePasswordUseCase, AcePrepareToUpdateUserLoginInformationUseCase,
        AceUpdateSecurityQuestionAnswerUseCase, AceUpdateUserNameUseCase {
}
