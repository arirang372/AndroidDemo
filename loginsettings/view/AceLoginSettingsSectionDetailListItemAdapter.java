package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailListItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates login settings section detail list items
 * used in either Update Security Questions page or Create a new password page
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsSectionDetailListItemAdapter extends AceSingleTypeDataBoundAdapter<AceLoginSettingsSectionDetailListItem> {
    private final AceLoginSettingsCallback callback;
    public AceLoginSettingsSectionDetailListItemAdapter(@NonNull List<AceLoginSettingsSectionDetailListItem> items, @NonNull AceLoginSettingsCallback callback) {
        super(items);
        this.callback = callback;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }

    @LayoutRes
    @Override
    public int getLayoutId(int position) {
        return R.layout.new_login_settings_section_detail_list_item;
    }
}
