package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseSpinnerAdapter;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;

import java.util.List;

public class AceLoginSettingsSecurityQuestionsSpinnerAdapter extends AceBaseSpinnerAdapter<MitCodeDescriptionPair> {
    public AceLoginSettingsSecurityQuestionsSpinnerAdapter(@NonNull List<MitCodeDescriptionPair> items) {
        super(items);
    }

    @Override
    protected void bindItem(ViewDataBinding binding, int position) {
        binding.setVariable(BR.model, getItemAt(position));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.login_settings_security_questions_spinner_item;
    }
}
