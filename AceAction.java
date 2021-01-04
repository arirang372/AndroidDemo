package com.geico.mobile.android.ace.geicoapppresentation.action;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLog;
import com.geico.mobile.android.ace.geicoappmodel.AceClaimAlertNotification;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.DialogTag;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceBaseViewModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

/**
 * Abstract class that represent a user-initiated action, usually triggered
 * by interacting with the UI (button clicks, menu item selections, etc).
 *
 * @author Richard Peirson, GEICO
 */
public abstract class AceAction implements AceAnalyticsActionConstants, AceExecutableWith<AceBaseActivity>, MitWebLinkNames {

	public final String name;

	public AceAction(@NonNull Context context, @StringRes int nameId) {
		this.name = context.getString(nameId);
	}

	public AceAction(@NonNull Context context, @StringRes int nameFormatId, String nameParameter) {
		this.name = context.getString(nameFormatId, nameParameter);
	}

	protected void logEvent(AceBaseActivity activity, @NonNull String eventName) {
		activity.getViewModel().logEvent(eventName);
	}

	protected void logEvent(AceBaseActivity activity, @NonNull AceEventLog event) {
		activity.getViewModel().logEvent(event);
	}

	protected void navigateTo(@ViewTag String viewTag, AceBaseActivity activity, Object helper) {
		activity.getViewModel().navigateTo(viewTag, activity, helper);
	}

	protected void openDialog(@DialogTag String dialogTag, AceBaseActivity activity) {
		activity.getViewModel().openDialog(dialogTag);
	}

	protected void openFullSite(AceBaseActivity activity, AceClaimAlertNotification alert, String webLinkName) {
		activity.getViewModel().openFullSite(activity, alert, webLinkName);
	}

	protected void openFullSite(AceBaseActivity activity, String webLinkName) {
		activity.getViewModel().openFullSite(activity, webLinkName);
	}

	protected void startActivity(AceBaseActivity activity, Class<? extends Activity> destination, String action) {
		activity.getViewModel().startActivity(activity, destination, action);
	}

	protected void startActivity(AceBaseActivity activity, Class<? extends Activity> activityBeingStartedClass) {
		activity.getViewModel().startActivity(activity, activityBeingStartedClass);
	}

	protected void startActivity(AceBaseActivity activity, Uri uri) {
		activity.getViewModel().startActivity(activity, uri);
	}

	protected void trackAction(AceBaseActivity activity, @NonNull String action, @NonNull String contextValue) {
		activity.getViewModel().trackAction(action, contextValue);
	}

	protected void trackAction(AceBaseViewModel viewModel, @NonNull String action, @NonNull String contextValue) {
		viewModel.trackAction(action, contextValue);
	}

	protected void trackAction(AceBaseActivity activity, @NonNull String action) {
		activity.getViewModel().trackAction(action);
	}
}