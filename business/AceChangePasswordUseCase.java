package com.geico.mobile.android.ace.geicoappbusiness.usecase.loginsettings;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceWaitable;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceNotAuthorized;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitChangePasswordResponse;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitResponse;

/**
 * API used to invoke the change password service call
 *
 * @author John Sung, Geico
 */
public interface AceChangePasswordUseCase extends AceWaitable, AceNotAuthorized {

    void onChangePasswordAnyFailure(@NonNull MitResponse response);

    void onChangePasswordPartialSuccess(@NonNull MitChangePasswordResponse response);

    void onChangePasswordSuccess(@NonNull MitChangePasswordResponse response);
}
