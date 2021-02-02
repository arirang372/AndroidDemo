package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.view;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleObserver;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState.ActionBarCustomization;
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseFragment;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel.AceUsersViewModel;
import com.geico.mobile.databinding.NewUsersActivityBinding;

/**
 * Fragment for the User selection page
 *
 * @author John Sung, Geico
 */
public class AceUsersFragment extends AceBaseFragment<NewUsersActivityBinding, AceUsersViewModel, AceUsersModel> implements AceUsersCallback {

    @ActionBarCustomization
    @Override
    protected int getActionBarCustomizationType() {
        return ActionBarCustomization.NO_ACTION_BUTTONS;
    }

    @StringRes
    @Override
    protected int getFragmentTitleResourceId() {
        return R.string.selectUser;
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.new_users_fragment;
    }

    @Override
    public void onEditButtonClicked() {
        getViewModel().onEditButtonClicked();
    }

    @Override
    public void onUsersListItemRowClicked(@NonNull AceUserProfilePerson profilePerson) {
        getViewModel().onUsersListItemRowClicked(profilePerson);
    }

    @Override
    protected LifecycleObserver getLifecycleObserver() {
        return getViewModel().createLifecycleObserver();
    }

    @NonNull
    @Override
    protected String getViewTag() {
        return ViewTag.USERS_FRAGMENT;
    }
}
