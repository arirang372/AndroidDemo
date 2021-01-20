package com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceTierServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings.AcePrepareToUpdateUserLoginInformationUseCase;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitRequest;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;

/**
 * Prepare to update user login information service reaction for TIER call.
 *
 * @author John Sung, Geico
 */
public class AcePrepareToUpdateUserLoginInformationReaction extends AceTierServiceReaction<
        MitPrepareToUpdateUserLoginInformationResponse, AcePrepareToUpdateUserLoginInformationUseCase> {
    public AcePrepareToUpdateUserLoginInformationReaction(@NonNull AcePrepareToUpdateUserLoginInformationUseCase useCase) {
        super(useCase);
    }

    @NonNull
    @Override
    public Class<? extends MitRequest> getRequestType() {
        return MitPrepareToUpdateUserLoginInformationRequest.class;
    }

    @Override
    protected void onSuccess(@NonNull MitPrepareToUpdateUserLoginInformationResponse response) {
        super.onSuccess(response);
        getUseCase().onPrepareToUpdateUserLoginInformationSuccess(response);
    }

    @Override
    protected void onAnyFailure(@NonNull MitResponse response) {
        getUseCase().onPrepareToUpdateUserLoginInformationAnyFailure(response);
    }
}
