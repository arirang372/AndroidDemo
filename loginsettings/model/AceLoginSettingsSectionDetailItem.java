package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.mitsupport.mitmodel.MitCodeDescriptionPair;

import java.util.List;

public class AceLoginSettingsSectionDetailItem {
    private final ObservableField<String> label = new ObservableField<>("");
    private final ObservableField<String> text = new ObservableField<>("");
    private final ObservableField<String> errorMessage = new ObservableField<>("");
    private final ObservableList<MitCodeDescriptionPair> securityQuestions = new ObservableArrayList<>();
    private final ObservableInt selectedSecurityQuestionPosition = new ObservableInt(0);
    private final ObservableBoolean showSecurityQuestionSpinner = new ObservableBoolean(false);
    private final ObservableInt securityQuestionPosition = new ObservableInt(0);
    private String temporaryQuestionOneCode = "";
    private String temporaryQuestionTwoCode = "";

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
    public ObservableField<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(@NonNull String errorMessage) {
        this.errorMessage.set(errorMessage);
    }

    @NonNull
    public ObservableInt getSecurityQuestionPosition() {
        return securityQuestionPosition;
    }

    public void setSecurityQuestionPosition(int position) {
        this.securityQuestionPosition.set(position);
    }

    public void setShouldShowSecurityQuestionSpinner(boolean showSecurityQuestionSpinner) {
        this.showSecurityQuestionSpinner.set(showSecurityQuestionSpinner);
    }

    @NonNull
    public ObservableBoolean shouldShowSecurityQuestionSpinner() {
        return showSecurityQuestionSpinner;
    }

    @NonNull
    public ObservableInt getSelectedSecurityQuestionPosition() {
        return selectedSecurityQuestionPosition;
    }

    public void setSelectedSecurityQuestionPosition(int position) {
        selectedSecurityQuestionPosition.set(position);
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
    public ObservableField<String> getLabel() {
        return this.label;
    }

    public void setLabel(@NonNull String label) {
        this.label.set(label);
    }

    @NonNull
    public ObservableField<String> getText() {
        return this.text;
    }

    public void setText(@NonNull String text) {
        this.text.set(text);
    }
}
