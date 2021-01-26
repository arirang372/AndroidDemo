package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSettingsDao;
import com.geico.mobile.android.ace.geicoappbusiness.login.AceLoginSharedPreferencesDao;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsModel;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;

import java.util.Arrays;
import java.util.List;

import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_PASSWORD;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_SECURITY_QUESTION;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.EDIT_USER_ID;
import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.NOT_APPLICABLE;

/**
 * A class that populates all login settings section details(user id, password, and security questions) into login settings model to be used for data binding
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsPopulator implements AcePopulator<AceRegistry, AceLoginSettingsModel> {

    private final AceConverter<Integer, String> resourceConverter;

    public AceLoginSettingsPopulator(@NonNull AceConverter<Integer, String> resourceConverter) {
        this.resourceConverter = resourceConverter;
    }

    private List<AceLoginSettingsSectionItem> buildLoginSettingsSectionItems(String userId) {
        return Arrays.asList(createLoginSettingsSectionItemWith(resourceConverter.convert(R.string.userIdText), userId, EDIT_USER_ID),
                createLoginSettingsSectionItemWith(resourceConverter.convert(R.string.passwordText), EDIT_PASSWORD),
                createLoginSettingsSectionItemWith(resourceConverter.convert(R.string.securityQuestions), EDIT_SECURITY_QUESTION));
    }

    private List<AceLoginSettingsSectionItem> buildScreenUnlockSettingsSectionItems(boolean isScreenUnlockEnabled) {
        return Arrays.asList(createLoginSettingsSectionItemWith(resourceConverter.convert(R.string.enableScreenUnlock), NOT_APPLICABLE)
                .withScreenUnlockEnabled(isScreenUnlockEnabled)
                .withLabel(resourceConverter.convert(isScreenUnlockEnabled ? R.string.disableScreenUnlock : R.string.enableScreenUnlock)));
    }

    private void considerPopulatingScreenUnlockRow(AceRegistry source, AceLoginSettingsModel target) {
        if (shouldShowScreenUnlockSettings(source)) {
            target.getLoginSettingsListItems().add(createLoginSettingsListItemWith(resourceConverter.convert(R.string.screenUnlockSettings),
                    buildScreenUnlockSettingsSectionItems(isScreenUnlockEnabled(source))).withSubtitle(getScreenUnlockSettingsSubtitle(source)));
        }
    }

    private AceLoginSettingsListItem createLoginSettingsListItemWith(String title,
                                                                     List<AceLoginSettingsSectionItem> loginSettingsSectionItems) {
        AceLoginSettingsListItem loginSettingsListItem = new AceLoginSettingsListItem();
        loginSettingsListItem.setTitle(title);
        loginSettingsListItem.setLoginSettingsSectionItems(loginSettingsSectionItems);
        return loginSettingsListItem;
    }

    private AceLoginSettingsSectionItem createLoginSettingsSectionItemWith(String label, String text, int position) {
        AceLoginSettingsSectionItem sectionItem = new AceLoginSettingsSectionItem();
        sectionItem.setLabel(label);
        sectionItem.setText(text);
        sectionItem.setPosition(position);
        return sectionItem;
    }

    private AceLoginSettingsSectionItem createLoginSettingsSectionItemWith(String label, int position) {
        return createLoginSettingsSectionItemWith(label, "", position);
    }

    private String getScreenUnlockSettingsSubtitle(AceRegistry source) {
        return !source.getSessionController().getPolicySession().getLoginSettingsFlow().isEdited() ? "" : resourceConverter.convert(R.string.loginSettingsScreenUnlockNeedsReEnabling);
    }

    private boolean isScreenUnlockEnabled(AceRegistry source) {
        AceLoginSettingsDao loginSettingsDao = new AceLoginSharedPreferencesDao(source);
        return determineTouchId(loginSettingsDao) || loginSettingsDao.isEnrolledForDeviceUnlock();
    }

    private boolean determineTouchId(AceLoginSettingsDao loginSettingsDao) {
        return isEligibleForFingerprint(loginSettingsDao) && loginSettingsDao.isTouchIdEnabled();
    }

    private boolean isEligibleForFingerprint(AceLoginSettingsDao loginSettingsDao) {
        String currentFingerprintUserId = loginSettingsDao.retrieveFingerprintUserId();
        return currentFingerprintUserId.isEmpty() || currentFingerprintUserId.equalsIgnoreCase(loginSettingsDao.retrieveUserId());
    }

    @Override
    public void populate(@NonNull AceRegistry source, @NonNull AceLoginSettingsModel target) {
        target.getLoginSettingsListItems().clear();
        considerPopulatingScreenUnlockRow(source, target);
        target.getLoginSettingsListItems().add(createLoginSettingsListItemWith(resourceConverter.convert(R.string.loginSettings),
                buildLoginSettingsSectionItems(source.getSessionController().getPolicySession().getLoginSettingsFlow().getUserId())));
    }

    private boolean shouldShowScreenUnlockSettings(AceRegistry registry) {
        return new AceCanShowScreenUnlockSettings().deriveValueFrom(registry);
    }
}
