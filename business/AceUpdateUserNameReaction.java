package com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTierServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings.AceUpdateUserNameUseCase;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameResponse;

/**
 * Prepare to update username service reaction for TIER call.
 *
 * @author John Sung, Geico
 */
public class AceUpdateUserNameReaction extends AceTierServiceReaction<
        MitUpdateUsernameResponse, AceUpdateUserNameUseCase> {

    public AceUpdateUserNameReaction(@NonNull AceUpdateUserNameUseCase useCase) {
        super(useCase);
    }

    @NonNull
    @Override
    public Class<? extends MitRequest> getRequestType() {
        return MitUpdateUsernameRequest.class;
    }

    @Override
    protected void onPartialSuccess(@NonNull MitUpdateUsernameResponse response) {
        getUseCase().onUpdateUserNamePartialSuccess(response);
    }

    @Override
    protected void onSuccess(@NonNull MitUpdateUsernameResponse response) {
        super.onSuccess(response);
        getUseCase().onUpdateUserNameSuccess(response);
    }
}
