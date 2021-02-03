package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.derivations.AceSpecialtyVehiclesFromPolicy;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession;
import com.geico.mobile.android.ace.geicoappbusiness.userprivileges.AceUserPrivilegeAuthority;
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVisibilityState;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that populates all user profile section details(name, phone number, vehicle, fuel type, and vehicle colors) into users model to be used for data binding
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsPopulator extends AceBaseUserSettingsPopulator {

    public AceUserSettingsPopulator(@NonNull AceConverter<Integer, String> resourceConverter) {
        super(resourceConverter);
    }

    @VisibleForTesting
    protected List<AceUserSettingsSectionItem> buildUserSettingsSectionItems(AceRegistry source, AceUsersModel target) {
        AceVehiclePolicy policy = source.getSessionController().getPolicySession().getPolicy();
        AceUserProfilePerson profilePerson = source.getSessionController().getApplicationSession().getUserFlow().getPerson();
        boolean shouldShowEditAddressButton = shouldShowEditAddressButton(source.getSessionController().getApplicationSession(), source.getSessionController().getPolicySession(), source.getUserPrivilegeAuthority());
        boolean isSpecialtyVehicle = isSpecialtyVehicle(policy);
        List<AceUserSettingsSectionItem> sectionItems = new ArrayList<>();
        sectionItems.add(createUserSettingsSectionItemWith(resourceConverter.convert(R.string.name), profilePerson.getFullName(), true, false, false, 0));
        populatePhoneNumberSection(sectionItems, profilePerson, target);
        sectionItems.add(createUserSettingsSectionItemWith(resourceConverter.convert(R.string.mailingAddress), policy.getContact().getMailingAddress().getDisplayableMailingAddress(),
                true, false, shouldShowEditAddressButton || isSpecialtyVehicle,
                shouldShowEditAddressButton ? R.drawable.ic_edit_pencil : isSpecialtyVehicle ? R.drawable.ic_phone : 0));
        return sectionItems;
    }

    @VisibleForTesting
    protected AceUserSettingsPhoneValidationRuleFactory createPhoneValidationRuleFactory(AceUsersModel target) {
        return new AceUserSettingsPhoneValidationRuleFactory(target, resourceConverter);
    }

    @VisibleForTesting
    protected AceUserSettingsPrimaryVehiclePopulator createPrimaryVehiclePopulator() {
        return new AceUserSettingsPrimaryVehiclePopulator(resourceConverter);
    }

    @VisibleForTesting
    protected boolean isSpecialtyVehicle(AceVehiclePolicy policy) {
        return !(new AceSpecialtyVehiclesFromPolicy().deriveValueFrom(policy).isEmpty() || policy.isCyclePolicy());
    }

    @Override
    public void populate(@NonNull AceRegistry source, @NonNull AceUsersModel target) {
        target.getUserSettingsListItems().clear();
        target.getUserSettingsListItems().add(createUserSettingsListItemWith(resourceConverter.convert(R.string.yourSettings), "", buildUserSettingsSectionItems(source, target), false, false));
        createPrimaryVehiclePopulator().populate(source, target);
    }

    private void populatePhoneNumberSection(List<AceUserSettingsSectionItem> sectionItems, AceUserProfilePerson profilePerson, AceUsersModel target) {
        AceUserSettingsSectionItem phoneNumberSection = createUserSettingsSectionItemWith(resourceConverter.convert(R.string.mobilePhone), profilePerson.getMobilePhoneNumber(), false, true, false, 0);
        AceSimpleRuleEngine.DEFAULT.applyFirst(createPhoneValidationRuleFactory(target).create(), profilePerson.getMobilePhoneNumber().trim());
        sectionItems.add(phoneNumberSection);
    }

    @VisibleForTesting
    protected boolean shouldShowEditAddressButton(AceApplicationSession applicationSession, AcePolicySession policySession, AceUserPrivilegeAuthority userPrivilegeAuthority) {
        applicationSession.setMailingAddressVisibilityState(AceUserProfileVisibilityState.SHOWN);
        boolean mailingAddressShownForUserSetUp = applicationSession.isMailingAddressShowingForFreshInstall();
        boolean isPolicyHolderWithMailingAddressShown = applicationSession.getUserFlow().getUserRole().isPolicyHolder() && !mailingAddressShownForUserSetUp;
        boolean isWebLinkAllowedAndPolicyHolder = userPrivilegeAuthority.isDestinationAllowed(MitWebLinkNames.UPDATE_ADDRESS) && isPolicyHolderWithMailingAddressShown;
        return isWebLinkAllowedAndPolicyHolder && !policySession.getPolicy().isCallToMakeChangesToAccountInfo();
    }
}
