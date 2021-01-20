package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.service;

import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierEcamsRequestPopulator;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCredentials;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitEcamsRequest;

/**
 * An object that provides credentials for login settings related service request.
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsRequestPopulator<M, I extends MitEcamsRequest> extends AceTierEcamsRequestPopulator<M, I> {

    @Override
    public MitCredentials getCredentials() {
        return getPolicySession().getCredentials();
    }
}
