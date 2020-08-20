package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.view;

import android.view.View;
import android.view.View.OnFocusChangeListener;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceDialogMonitor;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model.AceNotificationSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.viewmodel.AceNotificationSettingsViewModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;
import com.geico.mobile.databinding.NotificationSettingsFragmentBinding;

/**
 * Page where the user can manage contact preferences.
 *
 * @author Mahmudul Hasan & Austin Morgan, GEICO.
 */
public class AceNotificationSettingsFragment extends AceBaseFragment<NotificationSettingsFragmentBinding, AceNotificationSettingsViewModel, AceNotificationSettingsModel>
        implements OnFocusChangeListener, AceNotificationSettingsCallback {

    private final OnBackPressedCallback onBackPressedCallback = createOnBackPressedCallback();

    @Override
    protected AceDialogMonitor createDialogMonitorFrom(@NonNull FragmentManager fragmentManager) {
        return new AceNotificationSettingsDialogMonitor(fragmentManager);
    }

    private OnBackPressedCallback createOnBackPressedCallback() {
        return new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                onCloseClick();
            }
        };
    }

    @Override
    public void customizeActionBar(@ActionBarCustomization int customizationType) {
        onBackPressedCallback.setEnabled(customizationType == ActionBarCustomization.FORM);
        super.customizeActionBar(customizationType);
    }

    @Override
    protected @ActionBarCustomization int getActionBarCustomizationType() {
        return ActionBarCustomization.STANDARD;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.notificationSettings;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.notification_settings_fragment;
    }

    private void onCloseClick() {
        ((AceNotificationSettingsActivity) requireActivity()).clearFocus();
        getViewModel().discardChanges();
        customizeActionBar(ActionBarCustomization.STANDARD);
    }

    @Override
    public void onEmailProductsCheckBoxClick() {
        customizeActionBar(ActionBarCustomization.FORM);
        getViewModel().toggleEmailProductsNotificationsEnrollment();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        customizeActionBar(ActionBarCustomization.FORM);
    }

    @Override
    public void onNewsletterCheckBoxClick() {
        customizeActionBar(ActionBarCustomization.FORM);
        getViewModel().toggleNewsletterEnrollment();
    }

    @Override
    public void onPolicyServicesCheckBoxClick() {
        customizeActionBar(ActionBarCustomization.FORM);
        getViewModel().togglePolicyServicesNotificationsEnrollment();
    }

    @Override
    public void onPushNotificationEnrollCheckboxClick() {
        getViewModel().considerEnrollingInPush();
    }

    @Override
    public void onSpecialOffersCheckBoxClick() {
        customizeActionBar(ActionBarCustomization.FORM);
        getViewModel().toggleSpecialOffersNotificationsEnrollment();
    }

    @Override
    public void onTermsAndConditionsClick() {
        openFullSite(MitWebLinkNames.TERMS_AND_CONDITIONS);
    }

    @Override
    public void onWeatherNotificationEnrollCheckboxClicked() {
        getViewModel().onWeatherNotificationEnrollCheckboxClicked();
    }

    @Override
    public void onDriveEasyPushNotificationEnrollCheckboxClicked() {
        getViewModel().onDriveEasyPushNotificationEnrollCheckboxClicked();
    }

    @Override
    protected void setCustomVariables(NotificationSettingsFragmentBinding viewDataBinding) {
        viewDataBinding.setVariable(com.geico.mobile.BR.focusChangeCallback, this);
    }

    @Override
    protected void setOnBackPressedCallback() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), onBackPressedCallback);
    }

}