package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierRequestFactory;
import com.geico.mobile.android.ace.geicoappmodel.AceModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitPrepareToUpdateUserLoginInformationRequest;

/**
 * A factory class that defines update user login information request
 *
 * @author John Sung, Geico
 */
public class AcePrepareToUpdateUserLoginInformationRequestFactory extends
        AceTierRequestFactory<MitPrepareToUpdateUserLoginInformationRequest, AceModel> {

    protected AcePopulator<AceModel, MitPrepareToUpdateUserLoginInformationRequest> createPopulator() {
        return new AceLoginSettingsRequestPopulator();
    }

    @NonNull
    @Override
    protected MitPrepareToUpdateUserLoginInformationRequest createRequest() {
        return new MitPrepareToUpdateUserLoginInformationRequest();
    }
}
