package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.trackable.AceSettingsTrackable;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.AceTierRepository;
import com.geico.mobile.android.ace.geicoapppresentation.initializers.AceModelInitializer;
import com.geico.mobile.android.ace.geicoapppresentation.logout.AceLogoutActivity;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceSettingsLiveData;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceSettingsModel;

import static com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_M5_SETTINGS_MENU_LOGOUT;

/**
 * An action bar settings repository for logic that involves {@link com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry}
 * This repository will take care of a data layer logic for us in order to carry out a proper action on the model live data -
 * which in turn will update the UI.
 *
 * @author Kal Tadesse
 */
public class AceSettingsRepository extends AceTierRepository<AceSettingsModel> implements AceModelInitializer {

    public AceSettingsRepository(@NonNull Application application) {
        super(application);
        setTrackable(new AceSettingsTrackable(getAnalyticsFacade()));
    }

    @NonNull
    @Override
    protected AceSettingsLiveData createModelLiveData() {
        return new AceSettingsLiveData(new AceSettingsModel());
    }

    public void displayFeedback() {
        startActivity(getApplication(), com.geico.mobile.android.ace.geicoapppresentation.feedback.mvvm.view.AceFeedbackActivity.class);
    }

    public void handleLogout() {
        logEvent("M5_SETTINGS_LOGOUT_SELECTED");
        trackAction(ANALYTICS_M5_SETTINGS_MENU_LOGOUT, "M5SettingsMenu:Logout");
        resetPolicyDetailPageNavigation();
        startActivity(getApplication(), AceLogoutActivity.class);
    }

    @Override
    public void initializeModel() {
        new AceSettingsModelInitializer(this).initializeModel();
    }

    private void resetPolicyDetailPageNavigation() {
        getApplicationSession().setPolicyDetailPageNavigationAction("");
        getApplicationSession().setSelectedDriverClientId("");
    }
}

