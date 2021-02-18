package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model;

import android.annotation.SuppressLint;
import android.text.SpannableStringBuilder;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceDoNothing;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard.DashboardCardType;
import com.geico.mobile.android.ace.geicoappmodel.imageicons.AceImageIcon;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;

/**
 * Abstract class that represents components that is needed to render dashboard card.
 *
 * @author Gopal Palanisamy
 */
public class AceBaseDashboardCard implements AceDashboardCard {

    private final AceExecutableWith<AceBaseActivity> action;
    private final String alertMessage;
    @DashboardCardType
    private final String cardType;
    private final int drawableResourceId;
    private final String subtitle;
    private final String title;
    @StringRes
    private int firstButtonText = R.string.moreOffers;
    private boolean policyCancelled;

    public AceBaseDashboardCard(@DashboardCardType String cardType, @NonNull String title, @NonNull String subtitle) {
        this(cardType, title, subtitle, AceImageIcon.NO_DRAWABLE_RESOURCE);
    }

    public AceBaseDashboardCard(@DashboardCardType String cardType, @NonNull String title, @NonNull String subtitle, @DrawableRes int drawableResourceId) {
        //noinspection unchecked
        this(cardType, title, subtitle, drawableResourceId, AceDoNothing.EXECUTABLE_WITH);
    }

    public AceBaseDashboardCard(@DashboardCardType String cardType, @NonNull String title, @NonNull String subtitle, @DrawableRes int drawableResourceId, @NonNull AceExecutableWith<AceBaseActivity> action) {
        this(cardType, title, subtitle, drawableResourceId, action, "");
    }

    public AceBaseDashboardCard(@DashboardCardType String cardType, @NonNull String title, @NonNull String subtitle, @DrawableRes int drawableResourceId, @NonNull AceExecutableWith<AceBaseActivity> action, @StringRes int firstButtonText) {
        this(cardType, title, subtitle, drawableResourceId, action, "");
        this.firstButtonText = firstButtonText;
    }

    public AceBaseDashboardCard(@DashboardCardType String cardType, @NonNull String title, @NonNull String subtitle, @DrawableRes int drawableResourceId, @NonNull AceExecutableWith<AceBaseActivity> action, @NonNull String alertMessage) {
        this.action = action;
        this.cardType = cardType;
        this.drawableResourceId = drawableResourceId;
        this.subtitle = subtitle;
        this.title = title;
        this.alertMessage = alertMessage;
    }

    @NonNull
    @Override
    public AceExecutableWith<AceBaseActivity> getAction() {
        return action;
    }

    @NonNull
    @Override
    public String getAlertMessage() {
        return alertMessage;
    }

    @DashboardCardType
    @Override
    public String getCardType() {
        return cardType;
    }

    @SuppressLint("SwitchIntDef")
    @NonNull
    @Override
    public String getContentDescription() {
        switch (getCardType()) {
            case DashboardCardType.WHAT_IS_NEXT_SECTION_HEADER:
                return "What's Next";
            case DashboardCardType.ESIGNATURE:
                return "eForms";
            case DashboardCardType.PAYMENT_OVER_DUE:
            case DashboardCardType.PAYMENT_PENDING_CANCELLATION:
                return "Payment Overdue";
            case DashboardCardType.CLAIMS_ACTIVE_ROADSIDE:
            case DashboardCardType.CLAIMS_ADDITIONAL_ESTIMATE_RECEIVED:
            case DashboardCardType.CLAIMS_AWAITING_PHOTOS:
            case DashboardCardType.CLAIMS_ESTIMATE_RECEIVED:
            case DashboardCardType.CLAIMS_FORMS_AVAILABLE:
            case DashboardCardType.CLAIMS_INSPECTION_REMINDER:
            case DashboardCardType.CLAIMS_INSPECT_DAMAGE:
            case DashboardCardType.CLAIMS_NEED_ADDITIONAL_PHOTOS:
            case DashboardCardType.CLAIMS_REPAIRS_COMPLETE:
            case DashboardCardType.CLAIMS_REPAIRS_STARTED:
            case DashboardCardType.CLAIMS_REPORT_DAMAGE:
            case DashboardCardType.CLAIMS_SCHEDULE_INSPECTION:
                return "Claims Alert";
            case DashboardCardType.MISSING_DRIVERS_LICENSE:
                return "Your Attention Required";
            case DashboardCardType.PAYMENT_DUE:
                return "Payment Due";
            case DashboardCardType.SAVED_QUOTES:
                return "Saved Quotes";
            case DashboardCardType.CHANGE_OF_ADDRESS:
                return "Change of Address";
            case DashboardCardType.RENEWAL_UPDATE:
                return "Renewal Update";
            case DashboardCardType.POLICY_LINKING:
                return "Policy Linking";
            case DashboardCardType.ENROLL_IN_AUTOMATIC_PAYMENT:
                return "Enroll in Automatic Payments";
            case DashboardCardType.PAPERLESS_BILLING:
                return "Enroll in Paperless Billing";
            case DashboardCardType.PAPERLESS_OPTIONS:
            case DashboardCardType.PAPERLESS_POLICY:
                return "Enroll in Paperless Policy";
            case DashboardCardType.VEHICLE_CARE:
                return "Vehicle Care";
            default:
                return AceDashboardCard.super.getContentDescription();
        }
    }

    @DrawableRes
    @Override
    public int getDrawableResourceId() {
        return drawableResourceId;
    }

    @StringRes
    @Override
    public int getFirstButtonText() {
        return firstButtonText;
    }

    @Override
    public Integer getPriority() {
        switch (cardType) {
            case DashboardCardType.WHAT_IS_NEXT_SECTION_HEADER:
                return 1;
            case DashboardCardType.ESIGNATURE:
                return 2;
            case DashboardCardType.GIVE_BACK:
                return 3;
            case DashboardCardType.PAYMENT_OVER_DUE:
            case DashboardCardType.PAYMENT_PENDING_CANCELLATION:
                return 4;
            case DashboardCardType.CLAIMS_ACTIVE_ROADSIDE:
            case DashboardCardType.CLAIMS_ADDITIONAL_ESTIMATE_RECEIVED:
            case DashboardCardType.CLAIMS_AWAITING_PHOTOS:
            case DashboardCardType.CLAIMS_ESTIMATE_RECEIVED:
            case DashboardCardType.CLAIMS_FORMS_AVAILABLE:
            case DashboardCardType.CLAIMS_INSPECTION_REMINDER:
            case DashboardCardType.CLAIMS_INSPECT_DAMAGE:
            case DashboardCardType.CLAIMS_NEED_ADDITIONAL_PHOTOS:
            case DashboardCardType.CLAIMS_REPAIRS_COMPLETE:
            case DashboardCardType.CLAIMS_REPAIRS_STARTED:
            case DashboardCardType.CLAIMS_REPORT_DAMAGE:
            case DashboardCardType.CLAIMS_SCHEDULE_INSPECTION:
                return 5;
            case DashboardCardType.MISSING_DRIVERS_LICENSE:
                return 6;
            case DashboardCardType.VEHICLE_CARE_RECALL:
                return 7;
            case DashboardCardType.TELEMATICS_REGISTRATION:
                return 8;
            case DashboardCardType.PAYMENT_DUE:
                return 9;
            case DashboardCardType.TELEMATICS:
                return 10;
            case DashboardCardType.GET_A_QUOTE:
                return 11;
            case DashboardCardType.SAVED_QUOTES:
                return 12;
            case DashboardCardType.CHANGE_OF_ADDRESS:
                return 13;
            case DashboardCardType.RENEWAL_UPDATE:
                return 14;
            case DashboardCardType.POLICY_LINKING:
                return 15;
            case DashboardCardType.ENROLL_IN_AUTOMATIC_PAYMENT:
            case DashboardCardType.PAPERLESS_BILLING:
            case DashboardCardType.PAPERLESS_OPTIONS:
            case DashboardCardType.PAPERLESS_POLICY:
                return 16;
            case DashboardCardType.VEHICLE_CARE:
                return 17;
            case DashboardCardType.PUSH_ENROLLMENT:
            case DashboardCardType.TEXT_ALERTS:
                return 18;
            case DashboardCardType.ENROLL_IN_AUTOMATIC_PAYMENT_COMPLETED:
            case DashboardCardType.ESIGNATURE_COMPLETED:
            case DashboardCardType.MISSING_DRIVERS_LICENSE_COMPLETED:
            case DashboardCardType.PAPERLESS_BILLING_COMPLETED:
            case DashboardCardType.PAPERLESS_OPTIONS_COMPLETED:
            case DashboardCardType.PAPERLESS_POLICY_COMPLETED:
            case DashboardCardType.PAYMENT_COMPLETED:
                return 19;
            case DashboardCardType.CROSS_SELL_SECTION_HEADER:
                return 20;
            case DashboardCardType.TELEMATICS_NEXT_GEN_SIGNUP:
            case DashboardCardType.WEATHER_ALERTS_FOR_YOU:
            case DashboardCardType.DISCOVER_OFFERS:
                return 21;
            case DashboardCardType.HOME_OWNERS_PROMOTION:
            case DashboardCardType.RENTERS_PROMOTION:
            case DashboardCardType.UMBRELLA_PROMOTION:
                return 22;
            default:
                throw new IllegalStateException();
        }
    }

    @NonNull
    @Override
    public SpannableStringBuilder getSubtitle() {
        return new SpannableStringBuilder(subtitle);
    }

    @NonNull
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isPolicyCancelled() {
        return policyCancelled;
    }

    @Override
    public void setPolicyCancelled(boolean policyCancelled) {
        this.policyCancelled = policyCancelled;
    }
}