package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserLoginSettingsFlow;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;

import java.util.Arrays;
import java.util.List;

import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.LoginSettingsSectionDetailViewType;

public class AceLoginSettingsPasswordSectionPopulator implements AcePopulator<AceUserLoginSettingsFlow,
        AceLoginSettingsSectionItem> {

    private final AceConverter<Integer, String> resourceConverter;

    public AceLoginSettingsPasswordSectionPopulator(@NonNull AceConverter<Integer, String> resourceConverter) {
        this.resourceConverter = resourceConverter;
    }

    private AceLoginSettingsSectionDetailListItem createLoginSettingsSectionListDetailItemWith(String title) {
        AceLoginSettingsSectionDetailListItem sectionDetailListItem = new AceLoginSettingsSectionDetailListItem();
        sectionDetailListItem.setTitle(title);
        return sectionDetailListItem;
    }

    private List<AceLoginSettingsSectionDetailItem> buildFirstPasswordSectionDetailItem(AceUserLoginSettingsFlow source) {
        return Arrays.asList(createLoginSettingsSectionDetailItemWith(LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD,
                resourceConverter.convert(R.string.currentPassword), source.getPassword()),
                createLoginSettingsSectionDetailItemWith(LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD,
                        resourceConverter.convert(R.string.passwordText), ""),
                createLoginSettingsSectionDetailItemWith(LoginSettingsSectionDetailViewType.FOOTER,
                        "", resourceConverter.convert(R.string.passwordStrengthHint)));
    }

    private List<AceLoginSettingsSectionDetailItem> buildSecondPasswordSectionDetailItem(AceUserLoginSettingsFlow source) {
        return Arrays.asList(createLoginSettingsSectionDetailItemWith(LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD,
                resourceConverter.convert(R.string.reenterPassword), ""),
                createLoginSettingsSectionDetailItemWith(LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD,
                        resourceConverter.convert(R.string.passwordHint), source.getPasswordHint())
        );
    }

    private AceLoginSettingsSectionDetailItem createLoginSettingsSectionDetailItemWith(@LoginSettingsSectionDetailViewType int viewType,
                                                                                       String label, String text) {
        AceLoginSettingsSectionDetailItem sectionDetailItem = new AceLoginSettingsSectionDetailItem();
        sectionDetailItem.setViewType(viewType);
        sectionDetailItem.setLabel(label);
        sectionDetailItem.setText(text);
        return sectionDetailItem;
    }

    @Override
    public void populate(@NonNull AceUserLoginSettingsFlow source, @NonNull AceLoginSettingsSectionItem target) {
        List<AceLoginSettingsSectionDetailListItem> passwordSectionDetailListItems = Arrays.asList(
                createLoginSettingsSectionListDetailItemWith(resourceConverter.convert(R.string.createANewPassword))
                        .withSectionDetailItems(buildFirstPasswordSectionDetailItem(source)),
                createLoginSettingsSectionListDetailItemWith("")
                        .withSectionDetailItems(buildSecondPasswordSectionDetailItem(source)));
        target.withSectionDetailListItems(passwordSectionDetailListItems);
    }
}
