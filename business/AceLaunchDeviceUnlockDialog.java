package com.geico.mobile.android.ace.geicoappbusiness.application;

import androidx.annotation.NonNull;

/**
 * This is an api for launching a device unlock dialog that is prompted in a dashboard page or an User selection page
 *
 * @author John Sung, Geico
 */

public interface AceLaunchDeviceUnlockDialog {
    default boolean userWasEnrolledForDeviceUnlock() {
        return false;
    }

    @NonNull
    default String getMessageForDeviceUnlockDialog() {
        return "";
    }

    default void onActivateDeviceUnlockSelected() {
        //do nothing by default
    }

    default void onActivateDeviceUnlockDiscarded() {
        //do nothing by default
    }
}
