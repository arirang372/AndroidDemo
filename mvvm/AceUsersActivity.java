package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view.navigation.AceUserDestinations;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel.AceUsersViewModel;
import com.geico.mobile.databinding.NewUsersActivityBinding;

/**
 * Activity for the user selection component.
 * Keep this class minimal.
 *
 * @author John Sung, Geico
 */
public class AceUsersActivity extends AceBaseActivity<NewUsersActivityBinding, AceUsersViewModel> {

    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_users_activity;
    }

    @Override
    protected int getNavigationHostFragmentId() {
        return R.id.usersNavigationHostFragment;
    }

    @Override
    protected Class<AceUsersViewModel> getViewModelClass() {
        return AceUsersViewModel.class;
    }

    @Override
    protected void onCreateFirstTime() {
        super.onCreateFirstTime();
        navigateToDestinationOnNewIntent(AceUserDestinations.DESTINATION_MAP.get(getIntent().getAction()));
    }

    @Override
    public void onBackPressed() {
        getViewModel().onBackPressed();
        super.onBackPressed();
    }

    @Override
    protected boolean shouldSuppressEmittedStateChanges() {
        return true;
    }
}
