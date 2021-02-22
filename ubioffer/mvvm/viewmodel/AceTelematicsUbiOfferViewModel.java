package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.repository.AceTelematicsUbiOfferRepository;

/**
 * ViewModel for handling UI tasks for the Telematics Ubi offer component
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferViewModel extends AceBaseViewModel<AceTelematicsUbiOfferModel, AceTelematicsUbiOfferRepository> {
    public AceTelematicsUbiOfferViewModel(@NonNull Application application) {
        super(application);
    }


    @NonNull
    @Override
    protected AceTelematicsUbiOfferRepository createRepository(@NonNull Application application) {
        return new AceTelematicsUbiOfferRepository(application);
    }
}
