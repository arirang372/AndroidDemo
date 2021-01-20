package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates login settings section(each line) items
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsSectionItemAdapter extends AceSingleTypeDataBoundAdapter<AceLoginSettingsSectionItem> {
    private final AceLoginSettingsCallback callback;

    public AceLoginSettingsSectionItemAdapter(@NonNull List<AceLoginSettingsSectionItem> items,
                                              AceLoginSettingsCallback callback) {
        super(items);
        this.callback = callback;
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.new_login_settings_section_item;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }
}
