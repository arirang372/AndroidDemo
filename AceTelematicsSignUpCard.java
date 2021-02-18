package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.cards;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceBaseDashboardCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;

/**
 * Dashboard card used to display telematics sign up card to the user
 *
 * @author John Sung, Geico
 */
public class AceTelematicsSignUpCard extends AceBaseDashboardCard {
    public AceTelematicsSignUpCard(@NonNull String title, @NonNull String subtitle, @NonNull AceExecutableWith<AceBaseActivity> action) {
        super(AceDashboard.DashboardCardType.TELEMATICS_NEXT_GEN_SIGNUP, title, subtitle, R.drawable.ic_drive_easy_signup_card, action);
    }
}
