package com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceWaitable;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceNotAuthorized;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerResponse;

public interface AceUpdateSecurityQuestionAnswerUseCase extends AceWaitable, AceNotAuthorized {
    void onUpdateSecurityQuestionAnswerSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response);

    void onUpdateSecurityQuestionAnswerPartialSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response);
}
