package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.LoginSettingsSectionDetailViewType;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;

import java.util.List;

/**
 * A model for the login settings security questions detail item or create new password detail item
 *
 * @author John Sung, Geico
 */
public class AceLoginSettingsSectionDetailItem {

    private final ObservableField<String> errorMessage = new ObservableField<>("");
    private final ObservableField<String> label = new ObservableField<>("");
    private final ObservableField<String> mediumPasswordColor = new ObservableField<>("#F2F0EC");
    private final ObservableField<String> mediumPasswordText = new ObservableField<>("");
    private final ObservableInt securityQuestionPosition = new ObservableInt(0);
    private final ObservableList<MitCodeDescriptionPair> securityQuestions = new ObservableArrayList<>();
    private final ObservableInt selectedSecurityQuestionPosition = new ObservableInt(0);
    private final ObservableBoolean showPasswordStrengthMeter = new ObservableBoolean(false);
    private final ObservableBoolean showSecurityQuestionSpinner = new ObservableBoolean(false);
    private final ObservableField<String> strongPasswordColor = new ObservableField<>("#F2F0EC");
    private final ObservableField<String> strongPasswordText = new ObservableField<>("");
    private final ObservableField<String> text = new ObservableField<>("");
    private final ObservableBoolean showPasswordRequirementLayout = new ObservableBoolean(false);
    private final ObservableField<String> weakPasswordColor = new ObservableField<>("#F2F0EC");
    private final ObservableField<String> weakPasswordText = new ObservableField<>("");
    private final ObservableField<String> checkMarkResourceOne = new ObservableField<>("");
    private final ObservableField<String> checkMarkResourceTwo = new ObservableField<>("");
    private String temporaryQuestionOneCode = "";
    private String temporaryQuestionTwoCode = "";
    private int viewType = 0;

    public void setShouldShowPasswordRequirementLayout(boolean showPasswordRequirementLayout) {
        this.showPasswordRequirementLayout.set(showPasswordRequirementLayout);
    }

    @NonNull
    public ObservableBoolean shouldShowPasswordRequirementLayout() {
        return showPasswordRequirementLayout;
    }

    @NonNull
    public ObservableField<String> getCheckMarkResourceOne() {
        return checkMarkResourceOne;
    }

    public void setCheckMarkResourceOne(@NonNull String checkMarkResourceOne) {
        this.checkMarkResourceOne.set(checkMarkResourceOne);
    }

    @NonNull
    public ObservableField<String> getCheckMarkResourceTwo() {
        return checkMarkResourceTwo;
    }

    public void setCheckMarkResourceTwo(@NonNull String checkMarkResourceTwo) {
        this.checkMarkResourceTwo.set(checkMarkResourceTwo);
    }

    @NonNull
    public ObservableField<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@NonNull String errorMessage) {
        this.errorMessage.set(errorMessage);
    }

    @NonNull
    public ObservableField<String> getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label.set(label);
    }

    @NonNull
    public ObservableField<String> getMediumPasswordColor() {
        return mediumPasswordColor;
    }

    public void setMediumPasswordColor(@NonNull String mediumPasswordColor) {
        this.mediumPasswordColor.set(mediumPasswordColor);
    }

    @NonNull
    public ObservableField<String> getMediumPasswordText() {
        return mediumPasswordText;
    }

    public void setMediumPasswordText(@NonNull String mediumPasswordText) {
        this.mediumPasswordText.set(mediumPasswordText);
    }

    @NonNull
    public ObservableInt getSecurityQuestionPosition() {
        return securityQuestionPosition;
    }

    public void setSecurityQuestionPosition(int position) {
        securityQuestionPosition.set(position);
    }

    @NonNull
    public ObservableList<MitCodeDescriptionPair> getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(@NonNull List<MitCodeDescriptionPair> securityQuestions) {
        this.securityQuestions.clear();
        this.securityQuestions.addAll(securityQuestions);
    }

    @NonNull
    public ObservableInt getSelectedSecurityQuestionPosition() {
        return selectedSecurityQuestionPosition;
    }

    public void setSelectedSecurityQuestionPosition(int position) {
        selectedSecurityQuestionPosition.set(position);
    }

    @NonNull
    public ObservableField<String> getStrongPasswordColor() {
        return strongPasswordColor;
    }

    public void setStrongPasswordColor(@NonNull String strongPasswordColor) {
        this.strongPasswordColor.set(strongPasswordColor);
    }

    @NonNull
    public ObservableField<String> getStrongPasswordText() {
        return strongPasswordText;
    }

    public void setStrongPasswordText(@NonNull String strongPasswordText) {
        this.strongPasswordText.set(strongPasswordText);
    }

    @NonNull
    public String getTemporaryQuestionOneCode() {
        return temporaryQuestionOneCode;
    }

    public void setTemporaryQuestionOneCode(@NonNull String temporaryQuestionOneCode) {
        this.temporaryQuestionOneCode = temporaryQuestionOneCode;
    }

    @NonNull
    public String getTemporaryQuestionTwoCode() {
        return temporaryQuestionTwoCode;
    }

    public void setTemporaryQuestionTwoCode(@NonNull String temporaryQuestionTwoCode) {
        this.temporaryQuestionTwoCode = temporaryQuestionTwoCode;
    }

    @NonNull
    public ObservableField<String> getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text.set(text);
    }

    @LoginSettingsSectionDetailViewType
    public int getViewType() {
        return viewType;
    }

    public void setViewType(@LoginSettingsSectionDetailViewType int viewType) {
        this.viewType = viewType;
    }

    @NonNull
    public ObservableField<String> getWeakPasswordColor() {
        return weakPasswordColor;
    }

    public void setWeakPasswordColor(@NonNull String weakPasswordColor) {
        this.weakPasswordColor.set(weakPasswordColor);
    }

    @NonNull
    public ObservableField<String> getWeakPasswordText() {
        return weakPasswordText;
    }

    public void setWeakPasswordText(@NonNull String weakPasswordText) {
        this.weakPasswordText.set(weakPasswordText);
    }

    public void setShouldShowPasswordStringthMeter(boolean showPasswordStringthMeter) {
        this.showPasswordStrengthMeter.set(showPasswordStringthMeter);
    }

    public void setShouldShowSecurityQuestionSpinner(boolean showSecurityQuestionSpinner) {
        this.showSecurityQuestionSpinner.set(showSecurityQuestionSpinner);
    }

    @NonNull
    public ObservableBoolean shouldShowPasswordStrengthMeter() {
        return showPasswordStrengthMeter;
    }

    @NonNull
    public ObservableBoolean shouldShowSecurityQuestionSpinner() {
        return showSecurityQuestionSpinner;
    }
}
