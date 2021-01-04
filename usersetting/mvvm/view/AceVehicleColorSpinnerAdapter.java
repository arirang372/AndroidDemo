package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseListAdapter;

import java.util.List;

/**
 * A spinner adapter class that populates primary vehicle colors
 *
 * @author John Sung, Geico
 */
public class AceVehicleColorSpinnerAdapter extends AceBaseListAdapter<AceVehicleColor> {
    private final AceUserSettingsCallback callback;

    public AceVehicleColorSpinnerAdapter(@NonNull List<AceVehicleColor> items, @NonNull AceUserSettingsCallback callback) {
        super(items);
        this.callback = callback;
    }

    @Override
    protected void bindItem(ViewDataBinding binding, int position) {
        binding.setVariable(BR.model, getItemAt(position));
        binding.setVariable(BR.callback, callback);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.user_profile_vehicle_color_spinner_item;
    }
}
