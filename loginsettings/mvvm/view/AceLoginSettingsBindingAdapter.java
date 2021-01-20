package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;

import java.util.List;

/**
 * A binding adapter that is used for binding Login Settings for recycler view
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsBindingAdapter {

    private AceLoginSettingsBindingAdapter() {

    }

    @BindingAdapter({"loginSettingsListItems", "callback"})
    public static void setLoginSettingsListItems(@NonNull RecyclerView recyclerView,
                                                 @NonNull List<AceLoginSettingsListItem> loginSettingsListItems,
                                                 @NonNull AceLoginSettingsCallback callback) {
        recyclerView.setAdapter(new AceLoginSettingsListItemAdapter(loginSettingsListItems, callback));
    }

    @BindingAdapter({"loginSettingsSections", "callback"})
    public static void setLoginSettingsSectionItems(@NonNull RecyclerView recyclerView,
                                                    @NonNull List<AceLoginSettingsSectionItem> loginSettingsSectionItems,
                                                    @NonNull AceLoginSettingsCallback callback) {
        recyclerView.setAdapter(new AceLoginSettingsSectionItemAdapter(loginSettingsSectionItems, callback));
    }
}
