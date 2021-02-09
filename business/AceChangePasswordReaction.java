package com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTierServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings.AceChangePasswordUseCase;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitChangePasswordRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitChangePasswordResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;

/**
 * Change password service reaction for TIER call.
 *
 * @author John Sung, Geico
 */
public class AceChangePasswordReaction extends AceTierServiceReaction<
        MitChangePasswordResponse, AceChangePasswordUseCase> {

    public AceChangePasswordReaction(@NonNull AceChangePasswordUseCase useCase) {
        super(useCase);
    }

    @NonNull
    @Override
    public Class<? extends MitRequest> getRequestType() {
        return MitChangePasswordRequest.class;
    }

    @Override
    protected void onAnyFailure(@NonNull MitResponse response) {
        getUseCase().onChangePasswordAnyFailure(response);
    }

    @Override
    protected void onPartialSuccess(@NonNull MitChangePasswordResponse response) {
        getUseCase().onChangePasswordPartialSuccess(response);
    }

    @Override
    protected void onSuccess(@NonNull MitChangePasswordResponse response) {
        super.onSuccess(response);
        getUseCase().onChangePasswordSuccess(response);
    }
}
