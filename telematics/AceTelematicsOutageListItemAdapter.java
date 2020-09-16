package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.BR;
import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageListItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.callback.AceTelematicsOutageCallback;
import com.geico.mobile.databinding.TelematicsOutageHeaderBinding;
import com.geico.mobile.databinding.TelematicsOutageSectionBinding;

import java.util.List;

import static com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageDefinitions.AceTelematicsOutageListItemRow.OUTAGE_HEADER;

/**
 * Concrete implementation of recycler view adapter that builds the whole outage page.
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageListItemAdapter extends RecyclerView.Adapter {
    private List<AceTelematicsOutageListItem> outageListItems;
    private AceTelematicsOutageCallback outageCallback;

    public AceTelematicsOutageListItemAdapter(List<AceTelematicsOutageListItem> outageSections,
                                              AceTelematicsOutageCallback outageCallback) {
        this.outageListItems = outageSections;
        this.outageCallback = outageCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AceDataBoundViewHolder.create(parent, viewType == OUTAGE_HEADER ?
                R.layout.telematics_outage_header : R.layout.telematics_outage_section);
    }

    private AceTelematicsOutageListItem getItem(int position) {
        return outageListItems.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding binding = holder.getItemViewType() == OUTAGE_HEADER ?
                ((AceDataBoundViewHolder<TelematicsOutageHeaderBinding>) holder).binding :
                ((AceDataBoundViewHolder<TelematicsOutageSectionBinding>) holder).binding;
        binding.setVariable(BR.model, getItem(position));
        binding.setVariable(BR.callback, outageCallback);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return outageListItems.size();
    }
}
