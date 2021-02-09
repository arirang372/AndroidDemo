package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents annotated login setting type definitions for login settings implementation.
 *
 * @author John Sung, Geico
 */
public interface AceLoginSettingsDefinitions {

    String PASSWORD_RESET_BY_ADMIN = "Password Reset by Admin";

    @IntDef(value = {EditLoginSettingsType.EDIT_PASSWORD, EditLoginSettingsType.EDIT_SECURITY_QUESTION, EditLoginSettingsType.EDIT_USER_ID,
            EditLoginSettingsType.NOT_APPLICABLE})
    @Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface EditLoginSettingsType {

        int EDIT_PASSWORD = 1;
        int EDIT_SECURITY_QUESTION = 2;
        int EDIT_USER_ID = 0;
        int NOT_APPLICABLE = -1;
    }

    @IntDef(value = {LoginSettingsSectionDetailViewType.FOOTER, LoginSettingsSectionDetailViewType.UPDATE_PASSWORD_FIELD,
            LoginSettingsSectionDetailViewType.UPDATE_TEXT_FIELD})
    @Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoginSettingsSectionDetailViewType {
        int FOOTER = 2;
        int UPDATE_PASSWORD_FIELD = 1;
        int UPDATE_TEXT_FIELD = 0;
    }

    @IntDef(value = {LoginSettingsPasswordStrength.MEDIUM_PASSWORD, LoginSettingsPasswordStrength.STRONG_PASSWORD, LoginSettingsPasswordStrength.WEAK_PASSWORD})
    @Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface LoginSettingsPasswordStrength {
        int MEDIUM_PASSWORD = 2;
        int STRONG_PASSWORD = 3;
        int WEAK_PASSWORD = 1;
    }
}
