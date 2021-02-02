package com.geico.mobile.android.ace.geicoappbusiness.application;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.geico.mobile.android.ace.coreframework.connectivity.AceConnectionState.AceConnectionStateVisitor;
import com.geico.mobile.android.ace.coreframework.environment.AceEnvironmentVisitor;
import com.geico.mobile.android.ace.coreframework.patterns.AceVisitor;
import com.geico.mobile.android.ace.coreframework.rules.AceRuleEngine;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.coreframework.webservices.task.livedatatask.model.AceResponse;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceStateEmitter;
import com.geico.mobile.android.ace.geicoappbusiness.fullsite.AceSiteOpener;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceConnectable;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceServiceErrorDisplayable;
import com.geico.mobile.android.ace.geicoappbusiness.repository.servicereaction.AceServiceReaction;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionStateEnum.AceSessionStateVisitor;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceActivityStarter;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceDialogOpener;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceLogEvent;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceRequestPermission;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceShowBottomNavigation;
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceTrackAction;
import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.livedata.AceModelLiveData;

import java.util.Map;

/**
 * This is a public api that will expose application scoped objects
 * on demand. Instances of this object will be available on the android
 * {@link android.app.Application}
 * *
 *
 * @author Kal Tadesse and Richard Peirson, GEICO
 */
public interface AceRepository<M extends AceViewBackingModel> extends AceActivityStarter,
        AceConnectable, AceDialogOpener, AceRequestPermission, AceServiceErrorDisplayable,
        AceShowBottomNavigation, AceSiteOpener, AceTrackAction, AceLogEvent, AceLaunchDialer, AceLaunchDeviceUnlockDialog {

    <I, O> O acceptVisitor(@NonNull AceConnectionStateVisitor<I, O> visitor, I input);

    default <O> O acceptVisitor(@NonNull AceConnectionStateVisitor<Void, O> visitor) {
        return acceptVisitor(visitor, AceVisitor.NOTHING);
    }

    <I, O> O acceptVisitor(@NonNull AceEnvironmentVisitor<I, O> visitor, I input);

    default <O> O acceptVisitor(@NonNull AceEnvironmentVisitor<Void, O> visitor) {
        return acceptVisitor(visitor, AceVisitor.NOTHING);
    }

    <I, O> O acceptVisitor(@NonNull AceSessionStateVisitor<I, O> visitor, I input);

    default <O> O acceptVisitor(@NonNull AceSessionStateVisitor<Void, O> visitor) {
        return acceptVisitor(visitor, AceVisitor.NOTHING);
    }

    void configureSwitchPolicyBanner();

    @NonNull
    Application getApplication();

    @NonNull
    default M getModel() {
        return getModelLiveData().getValue();
    }

    @NonNull
    <L extends AceModelLiveData<M>> L getModelLiveData();

    @NonNull
    default AceRuleEngine getRuleEngine() {
        return AceSimpleRuleEngine.DEFAULT;
    }

    @NonNull
    Map<Class, AceServiceReaction> getServiceReactionsByRequestType();

    @NonNull
    <E extends AceStateEmitter> E getStateEmitter();

    @NonNull
    LiveData<AceResponse> getTaskResponseLiveData();

    void hideBottomNavigationWhenNotInPolicySession();

    void hideBottomNavigationWhileKeyboardIsShown(@NonNull View bottomNavigation, int windowHeight);

    @Override
    default void markAsConnected() {
        getStateEmitter().emitNetworkDialogVisibility(Visibility.HIDDEN);
    }

    @Override
    default void markAsNotConnected() {
        getStateEmitter().emitNetworkDialogVisibility(Visibility.VISIBLE);
    }

    @Override
    default void markAsNotWaiting() {
        getStateEmitter().emitWaitDialogVisibility(Visibility.HIDDEN);
    }

    @Override
    default void markAsWaiting() {
        getStateEmitter().emitWaitDialogVisibility(Visibility.VISIBLE);
    }

    /**
     * A contract that will keep {@link com.geico.mobile.android.ace.coreframework.webservices.task.AceThreadPoolScheduler}
     * in sync with {@link androidx.lifecycle.ViewModel}
     */
    void onCleared();

    default void reactToTaskResponse(@NonNull AceResponse<?> response) {
        //Do nothing by default
    }
}