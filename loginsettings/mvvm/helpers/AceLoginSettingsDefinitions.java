package com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.geico.mobile.android.ace.geicoapppresentation.loginsettings.mvvm.helpers.AceLoginSettingsDefinitions.EditLoginSettingsType.NOT_APPLICABLE;

/**
 * Represents annotated login setting type definitions for login settings implementation.
 *
 * @author John Sung, Geico
 */
public interface AceLoginSettingsDefinitions {

    @IntDef(value = {EditLoginSettingsType.EDIT_USER_ID, EditLoginSettingsType.EDIT_PASSWORD, EditLoginSettingsType.EDIT_SECURITY_QUESTION,
            NOT_APPLICABLE})
    @Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface EditLoginSettingsType {

        int EDIT_SECURITY_QUESTION = 2;
        int EDIT_PASSWORD = 1;
        int EDIT_USER_ID = 0;
        int NOT_APPLICABLE = -1;
    }
}
