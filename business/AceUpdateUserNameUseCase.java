package com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceWaitable;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceNotAuthorized;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameResponse;

/**
 * API used to invoke the prepare to update username service call
 *
 * @author John Sung, Geico
 */
public interface AceUpdateUserNameUseCase extends AceWaitable, AceNotAuthorized {

    void onUpdateUserNameSuccess(@NonNull MitUpdateUsernameResponse response);

    void onUpdateUserNamePartialSuccess(@NonNull MitUpdateUsernameResponse response);
}
