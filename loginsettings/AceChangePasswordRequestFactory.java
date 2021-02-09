package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierRequestFactory;
import com.geico.mobile.android.ace.geicoappmodel.AceModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitChangePasswordRequest;

/**
 * A factory class that defines change password request
 *
 * @author John Sung, Geico
 */
public class AceChangePasswordRequestFactory extends
        AceTierRequestFactory<MitChangePasswordRequest, AceModel> {

    @Override
    protected AcePopulator<AceModel, MitChangePasswordRequest> createPopulator() {
        return new AceLoginSettingsRequestPopulator();
    }

    @NonNull
    @Override
    protected MitChangePasswordRequest createRequest() {
        return new MitChangePasswordRequest();
    }
}
