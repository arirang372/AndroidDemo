package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback.AceTelematicsOutageCallback;
import com.geico.mobile.databinding.TelematicsOutageFragmentBinding;

/**
 * Fragment that shows users telematics outage page when telematics data is unavailable.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageFragment extends AceBaseTelematicsFragment<TelematicsOutageFragmentBinding> implements AceTelematicsOutageCallback {

    @Override
    @StringRes
    protected int getFragmentTitleResourceId() {
        return R.string.driveEasy;
    }

    @LayoutRes
    @Override
    protected int getLayoutResourceId() {
        return R.layout.telematics_outage_fragment;
    }

    @Override
    public void onOutageSectionItemClick(@NonNull AceTelematicsOutageSectionItem sectionItem) {
        getViewModel().onOutageSectionItemClick(sectionItem);
    }
}