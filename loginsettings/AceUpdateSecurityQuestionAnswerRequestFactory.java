package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceTierRequestFactory;
import com.geico.mobile.android.ace.geicoappmodel.AceModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitUpdateSecurityQuestionAnswerRequest;

/**
 * A factory class that defines update security question answer request
 *
 * @author John Sung, Geico
 */
public class AceUpdateSecurityQuestionAnswerRequestFactory extends
        AceTierRequestFactory<MitUpdateSecurityQuestionAnswerRequest, AceModel> {

    protected AcePopulator<AceModel, MitUpdateSecurityQuestionAnswerRequest> createPopulator() {
        return new AceLoginSettingsRequestPopulator();
    }

    @NonNull
    @Override
    protected MitUpdateSecurityQuestionAnswerRequest createRequest() {
        return new MitUpdateSecurityQuestionAnswerRequest();
    }
}
