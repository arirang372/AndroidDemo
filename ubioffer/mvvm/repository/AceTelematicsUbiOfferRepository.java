package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.repository.AceBaseRepository;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.helpers.AceTelematicsUbiOfferModelFactory;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferModel;

/**
 * Repository responsible for handling business logic for the Telematics ubi offer component.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferRepository extends AceBaseRepository<AceTelematicsUbiOfferModel> {
    public AceTelematicsUbiOfferRepository(@NonNull Application application) {
        super(application);
    }

    @NonNull
    @Override
    protected AceModelLiveData<AceTelematicsUbiOfferModel> createModelLiveData() {
        return new AceModelLiveData<>(new AceTelematicsUbiOfferModelFactory().create(this));
    }
}
