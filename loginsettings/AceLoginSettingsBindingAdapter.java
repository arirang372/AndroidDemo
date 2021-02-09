package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.view;

import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;

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

    @BindingAdapter({"loginSettingsSectionDetailItems", "callback"})
    public static void setLoginSettingsSectionDetailItems(@NonNull RecyclerView recyclerView,
                                                          @NonNull List<AceLoginSettingsSectionDetailItem> sectionDetailItems,
                                                          @NonNull AceLoginSettingsCallback callback) {
        recyclerView.setAdapter(new AceLoginSettingsSectionDetailItemAdapter(sectionDetailItems, callback));
    }

    @BindingAdapter({"loginSettingsSectionDetailListItems", "callback"})
    public static void setLoginSettingsSectionDetailListItems(@NonNull RecyclerView recyclerView,
                                                              @NonNull List<AceLoginSettingsSectionDetailListItem> sectionDetailListItems, @NonNull AceLoginSettingsCallback callback) {
        recyclerView.setAdapter(new AceLoginSettingsSectionDetailListItemAdapter(sectionDetailListItems, callback));
    }

    @BindingAdapter({"loginSettingsSections", "callback"})
    public static void setLoginSettingsSectionItems(@NonNull RecyclerView recyclerView,
                                                    @NonNull List<AceLoginSettingsSectionItem> loginSettingsSectionItems,
                                                    @NonNull AceLoginSettingsCallback callback) {
        recyclerView.setAdapter(new AceLoginSettingsSectionItemAdapter(loginSettingsSectionItems, callback));
    }

    @BindingAdapter({"securityQuestions", "selectedSecurityQuestionPosition"})
    public static void setSecurityQuestions(@NonNull Spinner spinner,
                                            @NonNull List<MitCodeDescriptionPair> securityQuestions,
                                            @NonNull int selectedSecurityQuestionPosition) {
        spinner.setAdapter(new AceLoginSettingsSecurityQuestionsSpinnerAdapter(securityQuestions));
        spinner.setSelection(selectedSecurityQuestionPosition);
    }

    @BindingAdapter({"passwordSection", "callback"})
    public static void setFocusChangedListener(@NonNull AppCompatEditText editText, @NonNull AceLoginSettingsSectionDetailItem passwordSection, @NonNull AceLoginSettingsCallback callback) {
        if ("Password".equals(passwordSection.getLabel().get())) {
            editText.setOnFocusChangeListener((v, hasFocus) -> callback.onPasswordFieldFocusChanged(hasFocus));
        }
    }
}
