package com.geico.mobile.android.ace.geicoapppresentation.fullsite.backnavigation;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.fragment.app.FragmentManager;

import com.geico.mobile.android.ace.coreframework.eventhandling.AcePublisher;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceHybridBackButtonOverrideContext;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceHybridBackButtonStrategy;
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceOneButtonDialog;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceDialogDoNothingViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.fullsite.AceQualtricsFeedbackConfirmationDialog;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceGeicoAppEventConstants.WEB_VIEW_HANDLE_BACK_BUTTON_PRESS;

/**
 * A class that determines whether to set up a back navigation behavior through back button on Qualtrics feedback hybrid page after submitting Qualtrics feedback
 *
 * @author John Sung, Geico
 */
public class AceQualtricsFeedbackBackNavigationDeterminer implements AceExecutableWith<String> {

    private final FragmentManager fragmentManager;
    private final AceHybridBackButtonOverrideContext hybridBackButtonOverrideContext;
    private final boolean isQualtricsFeedbackStartUrl;
    private final AceVehiclePolicy policy;
    private final AcePublisher<String, Object> publisher;

    public AceQualtricsFeedbackBackNavigationDeterminer(@NonNull FragmentManager fragmentManager, @NonNull AceHybridBackButtonOverrideContext hybridBackButtonOverrideContext,
                                                        @NonNull AcePublisher<String, Object> publisher, @NonNull AceVehiclePolicy policy, boolean isQualtricsFeedbackStartUrl) {
        this.fragmentManager = fragmentManager;
        this.hybridBackButtonOverrideContext = hybridBackButtonOverrideContext;
        this.publisher = publisher;
        this.policy = policy;
        this.isQualtricsFeedbackStartUrl = isQualtricsFeedbackStartUrl;
    }

    private void considerSendingUserBackToMobileApp(boolean shouldRedirectUser) {
        if (shouldRedirectUser) {
            AceOneButtonDialog<AceDialogDoNothingViewModel> dialog = createAndShowQualtricsFeedbackConfirmationDialog();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                setHybridBackButtonOverrideContext();
                dismissDialog(dialog);
                publisher.publish(WEB_VIEW_HANDLE_BACK_BUTTON_PRESS, WEB_VIEW_HANDLE_BACK_BUTTON_PRESS);
            }, 4000);
        }
    }

    private void setHybridBackButtonOverrideContext() {
        hybridBackButtonOverrideContext.setStrategy(policy.getPolicyLocation().isDuckCreek() ?
                AceHybridBackButtonStrategy.COMPLETE : AceHybridBackButtonStrategy.UNKNOWN);
    }

    private void considerSettingHybridBackButtonOverrideContext(boolean isQualtricsAboutToRedirect) {
        if (isQualtricsFeedbackStartUrl) {
            hybridBackButtonOverrideContext.setStrategy(isQualtricsAboutToRedirect && shouldSetHybridBackButtonStrategyToComplete() ?
                    AceHybridBackButtonStrategy.COMPLETE : AceHybridBackButtonStrategy.WARN);
        }
    }

    @VisibleForTesting
    protected AceOneButtonDialog<AceDialogDoNothingViewModel> createAndShowQualtricsFeedbackConfirmationDialog() {
        AceOneButtonDialog<AceDialogDoNothingViewModel> dialog = new AceQualtricsFeedbackConfirmationDialog();
        dialog.show(fragmentManager, "QUALTRICS_FEEDBACK_CONFIRMATION_DIALOG_TAG");
        return dialog;
    }

    @VisibleForTesting
    protected void dismissDialog(AceOneButtonDialog<AceDialogDoNothingViewModel> dialog) {
        dialog.dismiss();
    }

    @Override
    public void executeWith(@NonNull String url) {
        if (hybridBackButtonOverrideContext.getStrategy().isComplete()) return;
        boolean isQualtricsAboutToRedirect = url.contains("siteintercept.qualtrics.com");
        considerSettingHybridBackButtonOverrideContext(isQualtricsAboutToRedirect);
        considerSendingUserBackToMobileApp(isQualtricsAboutToRedirect && isQualtricsFeedbackStartUrl);
    }

    private boolean shouldSetHybridBackButtonStrategyToComplete() {
        return !TextUtils.isEmpty(policy.getNumber()) && policy.getPolicyLocation().isDuckCreek();
    }
}
