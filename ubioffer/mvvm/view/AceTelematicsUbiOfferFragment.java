package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.view;

import androidx.annotation.StringRes;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.viewmodel.AceTelematicsUbiOfferViewModel;
import com.geico.mobile.databinding.TelematicsUbiOfferFragmentBinding;

/**
 * Fragment for Telematics Ubi Offer entry point where the user can navigate to hybrid view
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferFragment extends AceBaseFragment<TelematicsUbiOfferFragmentBinding, AceTelematicsUbiOfferViewModel,
        AceTelematicsUbiOfferModel> implements AceTelematicsUbiOfferCallback {

    @AceConstantState.ActionBarCustomization
    @Override
    protected int getActionBarCustomizationType() {
        return AceConstantState.ActionBarCustomization.STANDARD;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.telematics_ubi_offer_fragment;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.driveEasy;
    }

    @Override
    public void onEnrollInDriveEasyButtonClick() {
        //TODO:will be implemented
    }
}
