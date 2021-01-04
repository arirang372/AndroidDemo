package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceSingleTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsListItem;

import java.util.List;

/**
 * A recyclerview adapter class that populates user settings list items
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsListItemAdapter extends AceSingleTypeDataBoundAdapter<AceUserSettingsListItem> {
    private final AceUserSettingsCallback callback;

    public AceUserSettingsListItemAdapter(@NonNull List<AceUserSettingsListItem> items,
                                          @NonNull AceUserSettingsCallback callback) {
        super(items);
        this.callback = callback;
    }

    @Override
    public int getLayoutId(int position) {
        return R.layout.user_settings_list_item;
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        holder.binding.setVariable(BR.model, getItemAt(position));
        holder.binding.setVariable(BR.callback, callback);
    }
}
