package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.enumerating.AceMatcher;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.transforming.AcePopulator;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserLoginSettingsFlow;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionDetailListItem;
import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model.AceLoginSettingsSectionItem;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitSecurityQuestionAnswer;

import java.util.Arrays;
import java.util.List;

public class AceLoginSettingsSecurityQuestionSectionPopulator implements AcePopulator<AceUserLoginSettingsFlow, AceLoginSettingsSectionItem> {
    private final AceConverter<Integer, String> resourceConverter;

    public AceLoginSettingsSecurityQuestionSectionPopulator(@NonNull AceConverter<Integer, String> resourceConverter) {
        this.resourceConverter = resourceConverter;
    }

    @Override
    public void populate(AceUserLoginSettingsFlow source, AceLoginSettingsSectionItem target) {
        List<AceLoginSettingsSectionDetailListItem> securityQuestionSectionDetailListItems = Arrays.asList(
                createLoginSettingsSectionListDetailItemWith(resourceConverter.convert(R.string.loginSettingsUpdateSecurityQuestions))
                        .withSectionDetailItems(buildSecurityQuestionSectionDetailItems(source, 0, source.getQuestionOne().getOptions())),
                createLoginSettingsSectionListDetailItemWith("")
                        .withSectionDetailItems(buildSecurityQuestionSectionDetailItems(source, 1, source.getQuestionTwo().getOptions())));
        target.withSectionDetailListItems(securityQuestionSectionDetailListItems);
    }

    private int getSelectedSecurityQuestionPosition(AceUserLoginSettingsFlow options, int questionIndex) {
        return isPasswordResetByAdmin(options) ? questionIndex
                : getSelectedQuestionPositionWith(getQuestionList(options, questionIndex), getSelectedQuestionCode(options, questionIndex));
    }

    private String getSelectedSecurityQuestionAnswer(AceUserLoginSettingsFlow options, int securityQuestionPosition) {
        List<MitSecurityQuestionAnswer> list = options.getSecurityQuestionAnswers();
        return isPasswordResetByAdmin(options) || list.isEmpty() ? "" : list.get(securityQuestionPosition).getAnswer();
    }

    private String getSelectedQuestionCode(AceUserLoginSettingsFlow options, int index) {
        return index == 0 ? options.getTemporaryQuestionOneCode() : options.getTemporaryQuestionTwoCode();
    }

    private List<MitCodeDescriptionPair> getQuestionList(AceUserLoginSettingsFlow options, int index) {
        return options.getSecurityQuestions().get(index).getOptions();
    }

    private MitCodeDescriptionPair getSelectedMitCodeDescriptionPair(List<MitCodeDescriptionPair> list, final String code) {
        AceMatcher<MitCodeDescriptionPair> matcher = question -> question.getCode().equals(code);
        return AceBasicEnumerator.DEFAULT.firstMatch(list, matcher, new MitCodeDescriptionPair());
    }

    private int getSelectedQuestionPositionWith(List<MitCodeDescriptionPair> list, String code) {
        MitCodeDescriptionPair pair = getSelectedMitCodeDescriptionPair(list, code);
        int position = list.indexOf(pair);
        return position == -1 ? 0 : position;
    }

    private boolean isPasswordResetByAdmin(AceUserLoginSettingsFlow options) {
        return "Password Reset by Admin".equals(options.getPasswordHint());
    }

    private AceLoginSettingsSectionDetailListItem createLoginSettingsSectionListDetailItemWith(String title) {
        AceLoginSettingsSectionDetailListItem sectionDetailListItem = new AceLoginSettingsSectionDetailListItem();
        sectionDetailListItem.setTitle(title);
        return sectionDetailListItem;
    }

    private List<AceLoginSettingsSectionDetailItem> buildSecurityQuestionSectionDetailItems(AceUserLoginSettingsFlow options, int securityQuestionPosition, List<MitCodeDescriptionPair> securityQuestions) {
        return Arrays.asList(createLoginSettingsSectionDetailItemWith(securityQuestionPosition, securityQuestions, true, getSelectedSecurityQuestionPosition(options, securityQuestionPosition)),
                createLoginSettingsSectionDetailItemWith(securityQuestionPosition, resourceConverter.convert(R.string.answer), getSelectedSecurityQuestionAnswer(options, securityQuestionPosition), false));
    }

    private AceLoginSettingsSectionDetailItem createLoginSettingsSectionDetailItemWith(int securityQuestionPosition, String label, String securityQuestionAnswer, boolean showSecurityQuestionSpinner) {
        AceLoginSettingsSectionDetailItem sectionDetailItem = new AceLoginSettingsSectionDetailItem();
        sectionDetailItem.setSecurityQuestionPosition(securityQuestionPosition);
        sectionDetailItem.setLabel(label);
        sectionDetailItem.setShouldShowSecurityQuestionSpinner(showSecurityQuestionSpinner);
        sectionDetailItem.setText(securityQuestionAnswer);
        return sectionDetailItem;
    }

    private AceLoginSettingsSectionDetailItem createLoginSettingsSectionDetailItemWith(int securityQuestionPosition, List<MitCodeDescriptionPair> securityQuestions, boolean showSecurityQuestionSpinner,
                                                                                       int selectedSecurityQuestionPosition) {
        AceLoginSettingsSectionDetailItem sectionDetailItem = new AceLoginSettingsSectionDetailItem();
        sectionDetailItem.setSecurityQuestionPosition(securityQuestionPosition);
        sectionDetailItem.setSecurityQuestions(securityQuestions);
        sectionDetailItem.setShouldShowSecurityQuestionSpinner(showSecurityQuestionSpinner);
        sectionDetailItem.setSelectedSecurityQuestionPosition(selectedSecurityQuestionPosition);
        return sectionDetailItem;
    }
}
