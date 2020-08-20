package com.geico.mobile.android.ace.geicoapppresentation.notificationsettings.mvvm.model;

import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents annotated push notification types for notification preferences in Settings.
 *
 * @author John Sung, Geico
 */
@StringDef(value = {AcePushNotificationType.DRIVEEASY_PUSH_NOTIFICATION, AcePushNotificationType.NONE, AcePushNotificationType.WEATHER_PUSH_NOTIFICATION})
@Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
@Retention(RetentionPolicy.SOURCE)
public @interface AcePushNotificationType {
    String DRIVEEASY_PUSH_NOTIFICATION = "DRIVEEASY_PUSH_NOTIFICATION";
    String NONE = "";
    String WEATHER_PUSH_NOTIFICATION = "WEATHER_PUSH_NOTIFICATION";
}
