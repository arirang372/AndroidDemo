package com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.geico.mobile.BR;
import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappbusiness.listeners.AceOnCloseClickListener;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.initializers.AceModelInitializer;
import com.geico.mobile.android.ace.geicoapppresentation.settings.mvvm.viewmodel.AceSettingsViewModel;
import com.geico.mobile.databinding.SettingsViewBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory;
import androidx.lifecycle.ViewModelProviders;

/**
 * A settings view that will be used to show categorized actions
 * up on the click of settings icon on the action bar. This is a reusable piece
 * that can get inserted to any layout if there is a design change in the future.
 *
 * @author Kal Tadesse
 */
public class AceSettingsView extends RelativeLayout implements AceModelInitializer, AceSettingsCallback {

	private SettingsViewBinding binding;
	private AceOnCloseClickListener onCloseClickListener;
	private AceSettingsViewModel viewModel;

	public AceSettingsView(Context context) {
		super(context);
	}

	public AceSettingsView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initializeViewModel();
		initializeView(context);
	}

	public AceSettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initializeViewModel();
		initializeView(context);
	}

	public AceSettingsViewModel getViewModel() {
		return viewModel;
	}

	@Override
	public void initializeModel() {
		viewModel.initializeModel();
	}

	private void initializeView(Context context) {
		binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.settings_view, this, true);
		binding.setVariable(BR.model, viewModel.getModel());
		binding.setVariable(BR.callback, this);
	}

	private void initializeViewModel() {
		viewModel = ViewModelProviders.of((AceBaseActivity) getContext(), new AndroidViewModelFactory(
				((AceBaseActivity) getContext()).getApplication())).get(AceSettingsViewModel.class);
	}

	@Override
	public void onCloseSettingsDialogClick() {
		onCloseClickListener.onCloseClick();
	}

	@Override
	public void onConfigurationChanged() {
		viewModel.getModelLiveData().resetValue();
	}

	@Override
	public void onLogoutClick() {
		viewModel.onLogoutClick();
	}

	@Override
	public void onLoginClick() {
		onCloseClickListener.onCloseClick();
	}

	public void setOnCloseClickListener(@NonNull AceOnCloseClickListener onCloseClickListener) {
		this.onCloseClickListener = onCloseClickListener;
	}
}