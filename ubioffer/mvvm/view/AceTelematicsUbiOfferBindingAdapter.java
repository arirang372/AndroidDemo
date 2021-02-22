package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferListItem;

import java.util.List;

/**
 * A binding adapter that is used for binding Telematics Ubi Offer components for recycler view
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferBindingAdapter {

    private AceTelematicsUbiOfferBindingAdapter() {

    }

    @BindingAdapter({"ubiOfferListItems", "callback"})
    public static void setUbiOfferListItems(@NonNull RecyclerView recyclerView,
                                            @NonNull List<AceTelematicsUbiOfferListItem> ubiOfferListItems,
                                            @NonNull AceTelematicsUbiOfferCallback callback) {
        recyclerView.setAdapter(new AceTelematicsUbiOfferListItemAdapter(ubiOfferListItems, callback));
    }

    @BindingAdapter({"ubiOfferDetailItems", "callback"})
    public static void setUbiOfferDetailItems(@NonNull RecyclerView recyclerView,
                                              @NonNull List<AceTelematicsUbiOfferDetailItem> ubiOfferDetailItems,
                                              @NonNull AceTelematicsUbiOfferCallback callback) {
        recyclerView.setAdapter(new AceTelematicsUbiOfferDetailItemAdapter(ubiOfferDetailItems, callback));
    }
}
