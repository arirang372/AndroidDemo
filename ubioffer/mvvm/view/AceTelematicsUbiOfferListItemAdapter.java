package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferListItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates Telematics ubi offer list items
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferListItemAdapter extends AceSingleTypeDataBoundAdapter<AceTelematicsUbiOfferListItem> {
    private final AceTelematicsUbiOfferCallback callback;

    public AceTelematicsUbiOfferListItemAdapter(@NonNull List<AceTelematicsUbiOfferListItem> ubiOfferListItems, @NonNull AceTelematicsUbiOfferCallback callback) {
        super(ubiOfferListItems);
        this.callback = callback;
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.telematics_ubi_offer_list_item;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }
}
