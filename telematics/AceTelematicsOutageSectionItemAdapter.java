package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSectionItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback.AceTelematicsOutageCallback;
import com.geico.mobile.databinding.TelematicsOutageSectionItemBinding;

import java.util.List;

/**
 * Concrete implementation of recycler view adapter that builds each outage section item.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageSectionItemAdapter extends RecyclerView.Adapter {
    private final AceTelematicsOutageCallback callback;
    private final List<AceTelematicsOutageSectionItem> telematicsOutageSectionItems;

    public AceTelematicsOutageSectionItemAdapter(@NonNull List<AceTelematicsOutageSectionItem> telematicsOutageSectionItems,
                                                 @NonNull AceTelematicsOutageCallback callback) {
        this.telematicsOutageSectionItems = telematicsOutageSectionItems;
        this.callback = callback;
    }

    @Override
    public int getItemCount() {
        return telematicsOutageSectionItems.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TelematicsOutageSectionItemBinding binding = ((AceDataBoundViewHolder<TelematicsOutageSectionItemBinding>) holder).binding;
        binding.setModel(telematicsOutageSectionItems.get(position));
        binding.setCallback(callback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AceDataBoundViewHolder.create(parent, R.layout.telematics_outage_section_item);
    }
}
