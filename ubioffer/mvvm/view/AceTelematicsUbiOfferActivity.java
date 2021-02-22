package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.view;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.viewmodel.AceTelematicsUbiOfferViewModel;
import com.geico.mobile.databinding.TelematicsUbiOfferActivityBinding;

/**
 * Activity for the Telematics ubi offer component.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferActivity extends AceBaseActivity<TelematicsUbiOfferActivityBinding,
        AceTelematicsUbiOfferViewModel> {
    @Override
    protected int getLayoutResourceId() {
        return R.layout.telematics_ubi_offer_activity;
    }

    @Override
    protected Class<AceTelematicsUbiOfferViewModel> getViewModelClass() {
        return AceTelematicsUbiOfferViewModel.class;
    }
}
