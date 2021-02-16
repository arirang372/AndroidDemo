package com.geico.mobile.android.ace.geicoapppresentation.emittedstate.dashboard;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceBasicStateEmitter;
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceEmittedStateObserver;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceBasicOnDemandEmittedState;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState.Visibility;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceDialogVisibilityState;

/**
 * This object is to be used as the state value emitter on the Dashboard pages
 *
 * @author Richard Peirson, GEICO
 */
public class AceDashboardStateEmitter extends AceBasicStateEmitter implements AceStateEmitterForDashboard {

	private final AceBasicOnDemandEmittedState makePaymentState = new AceBasicOnDemandEmittedState();

	@Override
	public void addObserver(@NonNull AceEmittedStateObserver observer, @NonNull LifecycleOwner viewLifecycleOwner) {
		super.addObserver(observer, viewLifecycleOwner);
		makePaymentState.observe(viewLifecycleOwner, observer::onEmittedStateChange);
	}

	@Override
	public void emitMakePaymentDialogVisibility(@Visibility int visibility) {
		makePaymentState.startObservingWithValue(new AceDialogVisibilityState(DialogTag.MAKE_PAYMENT, visibility));
	}

	@Override
	public void removeObservers(@NonNull LifecycleOwner viewLifecycleOwner) {
		super.removeObservers(viewLifecycleOwner);
		makePaymentState.removeObservers(viewLifecycleOwner);
	}
}