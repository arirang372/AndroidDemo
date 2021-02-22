package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferDetailItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates Telematics ubi offer detail items
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferDetailItemAdapter extends AceSingleTypeDataBoundAdapter<AceTelematicsUbiOfferDetailItem> {
    private final AceTelematicsUbiOfferCallback callback;

    public AceTelematicsUbiOfferDetailItemAdapter(@NonNull List<AceTelematicsUbiOfferDetailItem> ubiOfferDetailItems, @NonNull AceTelematicsUbiOfferCallback callback) {
        super(ubiOfferDetailItems);
        this.callback = callback;
    }


    @Override
    public int getLayoutId(int position) {
        return R.layout.telematics_ubi_offer_detail_item;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }
}
