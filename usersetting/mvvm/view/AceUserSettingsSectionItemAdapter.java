package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsSectionItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates user settings section(each line) items
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsSectionItemAdapter extends AceSingleTypeDataBoundAdapter<AceUserSettingsSectionItem> {
    private final AceUserSettingsCallback callback;

    public AceUserSettingsSectionItemAdapter(@NonNull List<AceUserSettingsSectionItem> items,
                                             @NonNull AceUserSettingsCallback callback) {
        super(items);
        this.callback = callback;
    }


    @Override
    public int getLayoutId(int position) {
        return R.layout.user_settings_section_item;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }
}
