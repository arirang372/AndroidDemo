package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.viewmodel;

import android.app.Application;
import android.content.ActivityNotFoundException;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.initializers.AceModelInitializer;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model.AceSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.repository.AceSettingsRepository;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.view.AceSettingsCallback;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.view.AceSettingsCategoryAdapter;

/**
 * When we are dealing with settings view we have two lists that
 * are going to be part of two adapters as coded in the {@link AceSettingsBindingAdapters}
 * so the list of settings categories that is defined in here is going to be the input for the {@link AceSettingsCategoryAdapter}.
 *
 * @author Kal Tadesse
 */
public class AceSettingsViewModel extends AceBaseViewModel<AceSettingsModel, AceSettingsRepository> implements AceModelInitializer, AceSettingsCallback {

    public AceSettingsViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Create a repository instance that will provide this viewModel with access to the data layer.
     *
     * @param application GEICO mobile
     * @return repository instance
     */
    @NonNull
    @Override
    protected AceSettingsRepository createRepository(@NonNull Application application) {
        return new AceSettingsRepository(application);
    }

    @Override
    public void initializeModel() {
        getRepository().initializeModel();
    }

    @Override
    public void onFeedbackClick() {
        logEvent("M5_SETTINGS_FEEDBACK_SELECTED");
        trackAction(ANALYTICS_M5_SETTINGS_MENU_FEEDBACK, "M5SettingsMenu:Feedback");
        getRepository().displayFeedback();
    }

    @Override
    public void onLogoutClick() {
        getRepository().handleLogout();
    }

    public void openGooglePlayStore() {
        try {
            startActivity(getApplication(), Uri.parse(getString(R.string.googlePlayStoreUri)));
        } catch (ActivityNotFoundException exception) {
            //do nothing
        }
    }

    public void openHelpCenter() {
        startActivity(getApplication().getApplicationContext(), AceContactGeicoActivity.class);
    }
}