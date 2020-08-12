package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.model;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * An action bar settings model that will be used to drive settings view behavior.
 *
 * @author Kal Tadesse, Austin Morgan
 */
public class AceSettingsModel extends AceViewBackingModel {

    public final ObservableBoolean logoutVisibility = new ObservableBoolean(false);
    private final ObservableBoolean showLogInButton = new ObservableBoolean(false);
    // TODO : Refactor to use ObservableList - this will require modifying the Recycler View adapter code to accept
    // 		ObservableList instead of a List
    public ObservableField<List<AceSettingsCategory>> settingsCategories = new ObservableField<>(new ArrayList<>());

    public void setShouldShowLoginButton(boolean showLoginButton) {
        this.showLogInButton.set(showLoginButton);
    }

    public ObservableBoolean shouldShowLoginButton() {
        return showLogInButton;
    }
}