package com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceWaitable;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceNotAuthorized;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;

/**
 * API used to invoke the prepare to update user login information service call
 *
 * @author John Sung, Geico
 */
public interface AcePrepareToUpdateUserLoginInformationUseCase extends AceWaitable, AceNotAuthorized {

    void onPrepareToUpdateUserLoginInformationAnyFailure(@NonNull MitResponse response);

    void onPrepareToUpdateUserLoginInformationSuccess(@NonNull MitPrepareToUpdateUserLoginInformationResponse response);

}
