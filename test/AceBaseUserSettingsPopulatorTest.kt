package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.CallSuper
import com.geico.mobile.R
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import com.geico.mobile.android.ace.geicoappmodel.*
import com.geico.mobile.android.ace.geicoappmodel.enums.AceHasOptionState
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem
import org.junit.Assert
import org.junit.Before
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito

/**
 *  A test class that shares common implementation for user setting tests
 *
 *  @author John Sung, Geico
 */
open class AceBaseUserSettingsPopulatorTest {

    protected val knownErrorMessage = "The phone number entered is invalid. Please verify."
    protected val knownVehicleDetails = AceLookupVehicleDetails().apply {
        typesOfGas = listOf(
            AceCodeDescriptionPair("Diesel", "Diesel"),
            AceCodeDescriptionPair("Electric", "Electric"),
            AceCodeDescriptionPair("Premium", "Premium"),
            AceCodeDescriptionPair("Regular", "Regular")
        )
        vehicleColors = listOf(
            AceVehicleColor("black", "Black", "#000000", 4, AceHasOptionState.YES),
            AceVehicleColor("blue", "Blue", "#0000FF", 9, AceHasOptionState.YES),
            AceVehicleColor("brown", "Brown", "#964B00", 6, AceHasOptionState.YES),
            AceVehicleColor("gold", "Gold", "#FFD700", 13, AceHasOptionState.YES)
        )
    }
    protected val knownUsersModel = AceUsersModel()

    @Mock
    protected lateinit var mockApplicationContext: Context

    @Mock
    protected lateinit var mockApplicationSession: AceApplicationSession

    @Mock
    protected lateinit var mockDrawable: Drawable

    @Mock
    protected lateinit var mockPolicySession: AcePolicySession

    @Mock
    protected lateinit var mockRegistry: AceRegistry

    @Mock
    protected lateinit var mockSessionController: AceSessionController
    protected val policy = AceVehiclePolicy()

    @Mock
    protected lateinit var resourceConverter: AceConverter<Int, String>
    protected val targetUsersModel = AceUsersModel()
    protected val userFlowForTest = AceUserFlow()

    open fun assertUserSettingsSectionItems(
        knownUserSettingsSectionItems: List<AceUserSettingsSectionItem>,
        targetUserSettingsSectionItems: List<AceUserSettingsSectionItem>
    ) {
        for (i in knownUserSettingsSectionItems.indices) {
            with(knownUserSettingsSectionItems[i]) {
                Assert.assertEquals(
                    editUserSettingButtonResource.get(),
                    targetUserSettingsSectionItems[i].editUserSettingButtonResource.get()
                )
                Assert.assertEquals(
                    fuelTypes.size,
                    targetUserSettingsSectionItems[i].fuelTypes.size
                )
                Assert.assertEquals(
                    label.get(),
                    targetUserSettingsSectionItems[i].label.get()
                )
                Assert.assertEquals(
                    labelContentDescription.get(),
                    targetUserSettingsSectionItems[i].labelContentDescription.get()
                )
                Assert.assertEquals(
                    matchingVehicleColors.size,
                    targetUserSettingsSectionItems[i].matchingVehicleColors.size
                )
                Assert.assertEquals(
                    primaryVehicle.get()?.let { it.color.code },
                    targetUserSettingsSectionItems[i].primaryVehicle.get()?.let { it.color.code }
                )
                Assert.assertEquals(
                    primaryVehicleColor.get()?.let { it.code },
                    targetUserSettingsSectionItems[i].primaryVehicleColor.get()?.let { it.code }
                )
                Assert.assertEquals(
                    primaryVehicleFuelCode.get(),
                    targetUserSettingsSectionItems[i].primaryVehicleFuelCode.get()
                )
                Assert.assertEquals(
                    selectedFuelTypePosition.get(),
                    targetUserSettingsSectionItems[i].selectedFuelTypePosition.get()
                )
                Assert.assertEquals(
                    selectedVehicleColorPosition.get(),
                    targetUserSettingsSectionItems[i].selectedVehicleColorPosition.get()
                )

                Assert.assertEquals(
                    selectedVehiclePosition.get(),
                    targetUserSettingsSectionItems[i].selectedVehiclePosition.get()
                )

                Assert.assertEquals(
                    shouldShowEditUserSettingsButton().get(),
                    targetUserSettingsSectionItems[i].shouldShowEditUserSettingsButton().get()
                )
                Assert.assertEquals(
                    shouldShowFuelTypeSpinner().get(),
                    targetUserSettingsSectionItems[i].shouldShowFuelTypeSpinner().get()
                )

                Assert.assertEquals(
                    shouldShowUserSettingsText().get(),
                    targetUserSettingsSectionItems[i].shouldShowUserSettingsText().get()
                )

                Assert.assertEquals(
                    shouldShowUserSettingsEditText().get(),
                    targetUserSettingsSectionItems[i].shouldShowUserSettingsEditText().get()
                )

                Assert.assertEquals(
                    shouldShowVehicleColorSpinner().get(),
                    targetUserSettingsSectionItems[i].shouldShowVehicleColorSpinner().get()
                )

                Assert.assertEquals(
                    shouldShowVehicleSpinner().get(),
                    targetUserSettingsSectionItems[i].shouldShowVehicleSpinner().get()
                )

                Assert.assertEquals(
                    text.get(),
                    targetUserSettingsSectionItems[i].text.get()
                )

                Assert.assertEquals(
                    isValidPhoneNumber.get(),
                    targetUserSettingsSectionItems[i].isValidPhoneNumber.get()
                )
                Assert.assertEquals(
                    vehicleColors.size,
                    targetUserSettingsSectionItems[i].vehicleColors.size
                )
                Assert.assertEquals(
                    vehicles.size,
                    targetUserSettingsSectionItems[i].vehicles.size
                )
            }
        }
    }

    open fun buildKnownUsersModel() {
        // do nothing by default
    }

    open fun createTestObject() {
        // do nothing by default
    }

    @CallSuper
    @Before
    open fun setUp() {
        Mockito.`when`(mockRegistry.applicationContext).thenReturn(mockApplicationContext)
        Mockito.`when`(mockApplicationContext.getDrawable(anyInt())).thenReturn(mockDrawable)
        Mockito.`when`(mockRegistry.sessionController).thenReturn(mockSessionController)
        Mockito.`when`(mockSessionController.applicationSession).thenReturn(mockApplicationSession)
        Mockito.`when`(mockSessionController.policySession).thenReturn(mockPolicySession)
        Mockito.`when`(mockPolicySession.policy).thenReturn(policy)
        Mockito.`when`(mockApplicationSession.userFlow).thenReturn(userFlowForTest)
        setUpPrimaryVehicle()
        setUpStringResources()
    }

    private fun setUpPrimaryVehicle() {
        userFlowForTest.person.primaryVehicle.make = AceVehicleMake("Honda", "Honda")
        userFlowForTest.person.primaryVehicle.model = AceVehicleModel("Accord", "Accord")
        userFlowForTest.person.primaryVehicle.color =
            AceVehicleColor("blue", "Blue", "#0000FF", 9, AceHasOptionState.YES)
        userFlowForTest.person.primaryVehicle.preferredFuelType =
            AceOutOfGasTypeEnum.REGULAR_UNLEADED
    }

    private fun setUpStringResources() {
        Mockito.`when`(resourceConverter.convert(R.string.yourSettings)).thenReturn("Your Settings")
        Mockito.`when`(resourceConverter.convert(R.string.userSetUpReason))
            .thenReturn("Enter basic details now to save time if you ever need roadside assistance.")
        Mockito.`when`(resourceConverter.convert(R.string.name)).thenReturn("Name")
        Mockito.`when`(resourceConverter.convert(R.string.mailingAddress))
            .thenReturn("Mailing Address")
        Mockito.`when`(resourceConverter.convert(R.string.mobilePhone)).thenReturn("Mobile Phone")
        Mockito.`when`(resourceConverter.convert(R.string.invalidPhoneNumber))
            .thenReturn(knownErrorMessage)
        Mockito.`when`(resourceConverter.convert(R.string.chooseYourVehicle))
            .thenReturn("Choose Your Vehicle")
        Mockito.`when`(resourceConverter.convert(R.string.chooseYourVehicleColor))
            .thenReturn("Choose Your Vehicle Color")
        Mockito.`when`(resourceConverter.convert(R.string.yourPrimaryVehicle))
            .thenReturn("Your Primary Vehicle")
        Mockito.`when`(resourceConverter.convert(R.string.fuelType)).thenReturn("Fuel Type")
        Mockito.`when`(resourceConverter.convert(R.string.vehicleColor)).thenReturn("Vehicle Color")
        Mockito.`when`(resourceConverter.convert(R.string.primaryVehicle))
            .thenReturn("Primary Vehicle")
    }

    open fun stripSeparators(phoneNumber: String): String {
        if (phoneNumber.isEmpty()) return ""
        val len = phoneNumber.length - 1
        val builder = StringBuilder()
        for (i in 0..len) {
            var char: Char = phoneNumber[i]
            if (char.isDigit()) {
                builder.append(char)
            }
        }
        return builder.toString()
    }
}
