package com.geico.mobile.android.ace.geicoappbusiness.application;

public interface AceLaunchKeepMeLoggedInDialog {
    default void onKeepMeLoggedInDiscarded() {
        //do nothing by default
    }

    default void onKeepMeLoggedInSelected() {
        //do nothing by default
    }
}
