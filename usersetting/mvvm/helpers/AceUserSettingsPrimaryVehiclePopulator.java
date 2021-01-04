package com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.helpers;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter;
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry;
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow;
import com.geico.mobile.android.ace.geicoappmodel.AceCodeDescriptionPair;
import com.geico.mobile.android.ace.geicoappmodel.AceLookupVehicleDetails;
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor;
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy;
import com.geico.mobile.android.ace.geicoappmodel.enums.AceHasOptionState;
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum;
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle;
import com.geico.mobile.android.ace.geicoapppresentation.users.AceUserProfileVehicleHint;
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel;
import com.geico.mobile.android.ace.geicoapppresentation.users.usersetting.mvvm.model.AceUserSettingsSectionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that populates user profile section primary vehicle details(vehicle, fuel type, and vehicle colors) into users model to be used for data binding
 *
 * @author John Sung, Geico
 */
public class AceUserSettingsPrimaryVehiclePopulator extends AceBaseUserSettingsPopulator {

    public AceUserSettingsPrimaryVehiclePopulator(@NonNull AceConverter<Integer, String> resourceConverter) {
        super(resourceConverter);
    }

    @Override
    public void populate(@NonNull AceRegistry source, @NonNull AceUsersModel target) {
        target.getUserSettingsListItems().add(createUserSettingsListItemWith(resourceConverter.convert(R.string.yourPrimaryVehicle), "", buildUserSettingsSectionItems(source), false, true));
    }

    private List<AceUserSettingsSectionItem> buildUserSettingsSectionItems(AceRegistry source) {
        AceApplicationSession applicationSession = source.getSessionController().getApplicationSession();
        AceVehiclePolicy policy = source.getSessionController().getPolicySession().getPolicy();
        List<AceUserSettingsSectionItem> sectionItems = new ArrayList<>();
        populateVehicleSection(sectionItems, applicationSession.getUserFlow(), policy);
        populateFuelTypeSection(sectionItems, applicationSession, policy);
        populateVehicleColorSection(sectionItems, applicationSession, policy);
        return sectionItems;
    }

    private void populateVehicleSection(List<AceUserSettingsSectionItem> sectionItems, AceUserFlow userFlow, AceVehiclePolicy policy) {
        AceUserSettingsSectionItem vehicleSectionItem = createUserSettingsSectionItemWith(resourceConverter.convert(R.string.primaryVehicle), true, false, false);
        vehicleSectionItem.setVehicles(buildUserProfileVehicles(userFlow));
        vehicleSectionItem.setPrimaryVehicle(getPrimaryVehicle(userFlow, policy.getNumber()));
        sectionItems.add(vehicleSectionItem);
    }

    private void populateFuelTypeSection(List<AceUserSettingsSectionItem> sectionItems, AceApplicationSession applicationSession, AceVehiclePolicy policy) {
        AceUserSettingsSectionItem fuelTypeSectionItem = createUserSettingsSectionItemWith(resourceConverter.convert(R.string.fuelType), false, true, false);
        fuelTypeSectionItem.setFuelTypes(buildFuelTypes());
        fuelTypeSectionItem.setPrimaryVehicleFuelCode(getPrimaryVehicleFuelCode(applicationSession.getUserFlow(), policy.getNumber()));
        if (!getFuelTypesFrom(applicationSession).isEmpty())
            sectionItems.add(fuelTypeSectionItem);
    }

    private void populateVehicleColorSection(List<AceUserSettingsSectionItem> sectionItems, AceApplicationSession applicationSession, AceVehiclePolicy policy) {
        AceUserSettingsSectionItem vehicleColorSectionItem = createUserSettingsSectionItemWith(resourceConverter.convert(R.string.vehicleColor), false, false, true);
        List<AceVehicleColor> vehicleColors = buildVehicleColors(applicationSession);
        vehicleColorSectionItem.setVehicleColors(vehicleColors);
        vehicleColorSectionItem.setPrimaryVehicleColor(getPrimaryVehicleColor(applicationSession.getUserFlow(), policy.getNumber()));
        if (!vehicleColors.isEmpty())
            sectionItems.add(vehicleColorSectionItem);
    }

    private AceUserProfileVehicle getPrimaryVehicle(AceUserFlow userFlow, String policyNumber) {
        return userFlow.getPerson().getPrimaryVehicle(policyNumber);
    }

    private AceVehicleColor getPrimaryVehicleColor(AceUserFlow userFlow, String policyNumber) {
        return getPrimaryVehicle(userFlow, policyNumber).getColor();
    }

    private String getPrimaryVehicleFuelCode(AceUserFlow userFlow, String policyNumber) {
        return getPrimaryVehicle(userFlow, policyNumber).getPreferredFuelType().getCode();
    }

    private List<AceCodeDescriptionPair> getFuelTypesFrom(AceApplicationSession applicationSession) {
        return AceBasicEnumerator.DEFAULT.coalesce(getLookupVehicleDetails(applicationSession).getTypesOfGas(), new ArrayList<>());
    }

    private List<AceUserProfileVehicle> buildUserProfileVehicles(AceUserFlow userFlow) {
        List<AceUserProfileVehicle> userProfileVehicles = new ArrayList<>();
        userProfileVehicles.add(new AceUserProfileVehicleHint(resourceConverter.convert(R.string.chooseYourVehicle)));
        userProfileVehicles.addAll(userFlow.getCurrentPolicyVehicleProfiles());
        return userProfileVehicles;
    }

    private List<AceOutOfGasTypeEnum> buildFuelTypes() {
        List<AceOutOfGasTypeEnum> fuelTypes = new ArrayList<>();
        fuelTypes.add(AceOutOfGasTypeEnum.REGULAR_UNLEADED);
        fuelTypes.add(AceOutOfGasTypeEnum.PREMIUM_UNLEADED);
        fuelTypes.add(AceOutOfGasTypeEnum.DIESEL);
        fuelTypes.add(AceOutOfGasTypeEnum.ELECTRIC);
        return fuelTypes;
    }

    private List<AceVehicleColor> buildVehicleColors(AceApplicationSession applicationSession) {
        List<AceVehicleColor> vehicleColors = new ArrayList<>();
        vehicleColors.add(new AceVehicleColor("", resourceConverter.convert(R.string.chooseYourVehicleColor), "", 0, AceHasOptionState.NO));
        vehicleColors.addAll(getVehicleColorsWith(applicationSession));
        return vehicleColors;
    }

    private List<AceVehicleColor> getVehicleColorsWith(AceApplicationSession applicationSession) {
        return AceBasicEnumerator.DEFAULT.coalesce(getLookupVehicleDetails(applicationSession).getVehicleColors(), new ArrayList<>());
    }

    private AceLookupVehicleDetails getLookupVehicleDetails(AceApplicationSession applicationSession) {
        return AceBasicEnumerator.DEFAULT.coalesce(applicationSession.getLookupVehicleDetails(), new AceLookupVehicleDetails());
    }
}
