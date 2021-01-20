package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsListItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates login settings list items
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsListItemAdapter extends AceSingleTypeDataBoundAdapter<AceLoginSettingsListItem> {
    private final AceLoginSettingsCallback callback;

    public AceLoginSettingsListItemAdapter(@NonNull List<AceLoginSettingsListItem> items,
                                           AceLoginSettingsCallback callback) {
        super(items);
        this.callback = callback;
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.new_login_settings_list_item;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }
}
