package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.viewmodel

import android.app.Application
import com.geico.mobile.android.ace.geicoappmodel.AceVehicleColor
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfileVehicle
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.AceRegistryDependentTest
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository.AceUsersRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.powermock.modules.junit4.PowerMockRunner


@RunWith(PowerMockRunner::class)
class AceUsersViewModelTest : AceRegistryDependentTest() {

    private lateinit var testObject: AceUsersViewModel

    @Mock
    private lateinit var mockLifecycleObserver: AceUsersLifecycleObserver

    @Mock
    private lateinit var mockRepository: AceUsersRepository

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        testObject = object : AceUsersViewModel(application) {
            override fun createRepository(application: Application) = mockRepository
            override fun createLifecycleObserver() = mockLifecycleObserver
        }
    }

    @Test
    fun testInitializeModel() {
        testObject.initializeModel()
        verify(mockRepository).initializeModel()
    }

    @Test
    fun testOnBackPressed() {
        testObject.onBackPressed()
        verify(mockRepository).handleBackPressedAction()
    }

    @Test
    fun testOnContinueButtonClicked() {
        testObject.onContinueButtonClicked()
        verify(mockRepository).handleContinueButtonAction()
        verify(mockRepository).handleBackPressedAction()
    }

    @Test
    fun testOnEditButtonClicked() {
        testObject.onEditButtonClicked()
        verify(mockRepository).handleEditButtonClickAction()
    }

    @Test
    fun testOnEditUserSettingsButtonClicked() {
        testObject.onEditUserSettingsButtonClicked()
        verify(mockRepository).handleEditUserSettingsButtonAction()
    }

    @Test
    fun testOnFuelTypeSpinnerItemSelected() {
        val fuelType = AceOutOfGasTypeEnum.PREMIUM_UNLEADED
        testObject.onFuelTypeSpinnerItemSelected(fuelType)
        verify(mockRepository).handleSelectedFuelTypeSpinnerItem(fuelType)
    }

    @Test
    fun testOnSkipButtonClicked() {
        testObject.onSkipButtonClicked()
        verify(mockRepository).handleSkipButtonAction()
    }


    @Test
    fun testUsersListItemRowClicked() {
        val userProfile = AceUserProfilePerson()
        testObject.onUsersListItemRowClicked(userProfile)
        verify(mockRepository).handleListItemRowClickAction(userProfile)
    }


    @Test
    fun testOnVehicleColorSpinnerItemSelected() {
        val vehicleColor = AceVehicleColor.UNKNOWN_COLOR
        testObject.onVehicleColorSpinnerItemSelected(vehicleColor)
        verify(mockRepository).handleSelectedVehicleColorAction(vehicleColor)
    }

    @Test
    fun testOnVehicleSpinnerItemSelected() {
        val vehicle = AceUserProfileVehicle()
        val selectedVehiclePosition = 1
        testObject.onVehicleSpinnerItemSelected(vehicle, selectedVehiclePosition)
        verify(mockRepository).handleSelectedVehicleAction(vehicle, selectedVehiclePosition)
    }

    @Test
    fun testRecordMetricsForCompletingEditUserSettings() {
        testObject.recordMetricsForCompletingEditUserSettings()
        verify(mockRepository).recordMetricsForCompletingEditUserSettings()
    }

    @Test
    fun testRegisterListeners() {
        testObject.registerListeners()
        verify(mockRepository).registerListeners()
    }

    @Test
    fun testUnregisterListeners() {
        testObject.unregisterListeners()
        verify(mockRepository).unregisterListeners()
    }
}