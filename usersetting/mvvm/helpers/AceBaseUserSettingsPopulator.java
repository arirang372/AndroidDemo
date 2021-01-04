package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsListItem;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsSectionItem;

import java.util.List;

/**
 * A parent class that has common methods used for sub-classes for edit user implementation
 *
 * @author John Sung, Geico
 */
public abstract class AceBaseUserSettingsPopulator implements AcePopulator<AceRegistry, AceUsersModel> {
    protected final AceConverter<Integer, String> resourceConverter;

    public AceBaseUserSettingsPopulator(@NonNull AceConverter<Integer, String> resourceConverter) {
        this.resourceConverter = resourceConverter;
    }

    @Override
    public abstract void populate(@NonNull AceRegistry source, @NonNull AceUsersModel target);

    protected AceUserSettingsListItem createUserSettingsListItemWith(String title, String subtitle, List<AceUserSettingsSectionItem> userSettingsSectionItems, boolean shouldShowSubtitle, boolean shouldShowSkipButton) {
        AceUserSettingsListItem listItem = new AceUserSettingsListItem();
        listItem.setTitle(title);
        listItem.setSubtitle(subtitle);
        listItem.setUserSettingsSectionItems(userSettingsSectionItems);
        listItem.setShouldShowSubtitle(shouldShowSubtitle);
        listItem.setShouldShowSkipButton(shouldShowSkipButton);
        return listItem;
    }

    protected AceUserSettingsSectionItem createUserSettingsSectionItemWith(String label, String text, boolean shouldShowSettingsText, boolean shouldShowSettingsEditText, boolean shouldEditUserSettingsButton, int editUserSettingsButtonResourceId) {
        AceUserSettingsSectionItem sectionItem = new AceUserSettingsSectionItem();
        sectionItem.setLabel(label);
        sectionItem.setText(text);
        sectionItem.setShouldShowUserSettingsText(shouldShowSettingsText);
        sectionItem.setShouldShowUserSettingsEditText(shouldShowSettingsEditText);
        sectionItem.setShouldShowVehicleSpinner(false);
        sectionItem.setShouldShowFuelTypeSpinner(false);
        sectionItem.setShouldShowVehicleColorSpinner(false);
        sectionItem.setShouldShowEditUserSettingsButton(shouldEditUserSettingsButton);
        sectionItem.setEditUserSettingButtonResource(editUserSettingsButtonResourceId);
        return sectionItem;
    }

    protected AceUserSettingsSectionItem createUserSettingsSectionItemWith(String label, boolean shouldShowVehicleSpinner, boolean shouldShowFuelTypeSpinner, boolean shouldShowVehicleColorSpinner) {
        AceUserSettingsSectionItem sectionItem = new AceUserSettingsSectionItem();
        sectionItem.setLabel(label);
        sectionItem.setShouldShowUserSettingsText(false);
        sectionItem.setShouldShowUserSettingsEditText(false);
        sectionItem.setShouldShowVehicleSpinner(shouldShowVehicleSpinner);
        sectionItem.setShouldShowFuelTypeSpinner(shouldShowFuelTypeSpinner);
        sectionItem.setShouldShowVehicleColorSpinner(shouldShowVehicleColorSpinner);
        sectionItem.setShouldShowEditUserSettingsButton(false);
        sectionItem.setEditUserSettingButtonResource(0);
        return sectionItem;
    }
}
