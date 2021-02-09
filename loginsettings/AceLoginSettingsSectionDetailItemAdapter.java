package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.BR;
import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.LoginSettingsSectionDetailViewType;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;
import com.geico.mobile.databinding.NewLoginSettingsPasswordSectionDetailItemBinding;
import com.geico.mobile.databinding.NewLoginSettingsPasswordSectionFooterItemBinding;
import com.geico.mobile.databinding.NewLoginSettingsSectionDetailItemBinding;

import java.util.List;

/**
 * A recyclerview adapter class that populates login settings section detail items
 * used in either Update Security Questions page or Create a new password page
 *
 * @author John Sung, Geico
 */
@SuppressWarnings({"unchecked", "rawtypes", "DuplicateBranchesInSwitch"})
public class AceLoginSettingsSectionDetailItemAdapter extends RecyclerView.Adapter {

    private final AceLoginSettingsCallback callback;
    private final List<AceLoginSettingsSectionDetailItem> items;

    public AceLoginSettingsSectionDetailItemAdapter(@NonNull List<AceLoginSettingsSectionDetailItem> items,
                                                    @NonNull AceLoginSettingsCallback callback) {
        this.items = items;
        this.callback = callback;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AceDataBoundViewHolder.create(parent, getLayoutResourceId(viewType));
    }

    private AceLoginSettingsSectionDetailItem getItem(int position) {
        return items.get(position);
    }

    @LayoutRes
    private int getLayoutResourceId(int viewType) {
        switch (viewType) {
            case LoginSettingsSectionDetailViewType.FOOTER:
                return R.layout.new_login_settings_password_section_footer_item;
            case LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD:
                return R.layout.new_login_settings_password_section_detail_item;
            case LoginSettingsSectionDetailViewType.UPDATE_TEXT_FIELD:
                return R.layout.new_login_settings_section_detail_item;
        }
        throw new IllegalArgumentException("Wrong view type is entered");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewDataBinding binding = getViewDataBindingWith(holder);
        binding.setVariable(BR.model, getItem(position));
        binding.setVariable(BR.callback, callback);
    }

    private ViewDataBinding getViewDataBindingWith(RecyclerView.ViewHolder holder) {
        switch (holder.getItemViewType()) {
            case LoginSettingsSectionDetailViewType.FOOTER:
                return ((AceDataBoundViewHolder<NewLoginSettingsPasswordSectionFooterItemBinding>) holder).binding;
            case LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD:
                return ((AceDataBoundViewHolder<NewLoginSettingsPasswordSectionDetailItemBinding>) holder).binding;
            case LoginSettingsSectionDetailViewType.UPDATE_TEXT_FIELD:
                return ((AceDataBoundViewHolder<NewLoginSettingsSectionDetailItemBinding>) holder).binding;
        }
        throw new IllegalArgumentException("Wrong view type is entered");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
