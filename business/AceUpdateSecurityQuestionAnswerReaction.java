package com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTierServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings.AceUpdateSecurityQuestionAnswerUseCase;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerResponse;

public class AceUpdateSecurityQuestionAnswerReaction extends AceTierServiceReaction<
        MitUpdateSecurityQuestionAnswerResponse, AceUpdateSecurityQuestionAnswerUseCase> {
    public AceUpdateSecurityQuestionAnswerReaction(@NonNull AceUpdateSecurityQuestionAnswerUseCase useCase) {
        super(useCase);
    }

    @NonNull
    @Override
    public Class<? extends MitRequest> getRequestType() {
        return MitUpdateSecurityQuestionAnswerRequest.class;
    }

    @Override
    protected void onPartialSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response) {
        getUseCase().onUpdateSecurityQuestionAnswerPartialSuccess(response);
    }

    @Override
    protected void onSuccess(@NonNull MitUpdateSecurityQuestionAnswerResponse response) {
        super.onSuccess(response);
        getUseCase().onUpdateSecurityQuestionAnswerSuccess(response);
    }
}
