package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.adapters;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageListItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback.AceTelematicsOutageCallback;

import java.util.List;

/**
 * Binding adapters used by the Telematics outage page.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageBindingAdapters {
    @BindingAdapter({"outageListItems", "callback"})
    public static void setOutageListItems(@NonNull RecyclerView recyclerView, @NonNull List<AceTelematicsOutageListItem> outageListItems,
                                          @NonNull AceTelematicsOutageCallback callback) {
        recyclerView.setAdapter(new AceTelematicsOutageListItemAdapter(outageListItems, callback));
    }

    @BindingAdapter({"outageSectionItems", "callback"})
    public static void setOutageSectionItems(@NonNull RecyclerView recyclerView, @NonNull List<AceTelematicsOutageSectionItem> outageSectionItems,
                                             @NonNull AceTelematicsOutageCallback callback) {
        recyclerView.setAdapter(new AceTelematicsOutageSectionItemAdapter(outageSectionItems, callback));
    }
}
