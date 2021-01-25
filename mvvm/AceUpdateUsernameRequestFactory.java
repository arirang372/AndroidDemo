package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierRequestFactory;
import com.geico.mobile.android.ace.geicoappmodel.AceModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateUsernameRequest;

/**
 * A factory class that defines update username request
 *
 * @author John Sung, Geico
 */
public class AceUpdateUsernameRequestFactory extends
        AceTierRequestFactory<MitUpdateUsernameRequest, AceModel> {

    protected AcePopulator<AceModel, MitUpdateUsernameRequest> createPopulator() {
        return new AceLoginSettingsRequestPopulator();
    }

    @NonNull
    @Override
    protected MitUpdateUsernameRequest createRequest() {
        return new MitUpdateUsernameRequest();
    }
}
