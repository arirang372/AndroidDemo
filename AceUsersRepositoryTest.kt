package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository

import android.app.Activity
import android.content.Context
import com.geico.mobile.R
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState
import com.geico.mobile.android.ace.coreframework.environment.AceEnvironment
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter
import com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry
import com.geico.mobile.android.ace.geicoappbusiness.emittedstate.AceStateEmitter
import com.geico.mobile.android.ace.geicoappbusiness.findgas.AceFindGasFacade
import com.geico.mobile.android.ace.geicoappbusiness.findgas.rules.AceFindGasFilterSettingsHolder
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith
import com.geico.mobile.android.ace.geicoappbusiness.permission.AcePermissionCategoryManager
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController
import com.geico.mobile.android.ace.geicoappbusiness.session.AceUserSession
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.*
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SWITCH
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_SWITCH
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsFacade
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLog
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceActivityStarter
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceLogEvent
import com.geico.mobile.android.ace.geicoappbusiness.userprivileges.AceUserPrivilegeAuthority
import com.geico.mobile.android.ace.geicoappbusiness.users.AcePersonProfileEditMonitor
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileDetailEvent
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSynchronizer
import com.geico.mobile.android.ace.geicoappmodel.*
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceChangeableEmittedState
import com.geico.mobile.android.ace.geicoappmodel.emittedstate.AceConstantState
import com.geico.mobile.android.ace.geicoappmodel.enums.AceContactGeicoType
import com.geico.mobile.android.ace.geicoappmodel.enums.AceHasOptionState
import com.geico.mobile.android.ace.geicoappmodel.enums.policy.AcePolicyLocationType
import com.geico.mobile.android.ace.geicoappmodel.enums.roadside.AceOutOfGasTypeEnum
import com.geico.mobile.android.ace.geicoappmodel.enums.users.AceUserRole
import com.geico.mobile.android.ace.geicoappmodel.findgas.AceFindGasProduct
import com.geico.mobile.android.ace.geicoappmodel.userprofile.*
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.email.mvvm.view.AceSendAnEmailActivity
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceDashboardActivity
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.AceApplicationDependentTest
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceRecommendationsDTO
import com.geico.mobile.android.ace.geicoapppresentation.permission.AceLocationPermissionForWebLinkActivity
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers.AceUserProfileListItemClickRuleFactory
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPhoneValidationRuleFactory
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPopulator
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPrimaryVehiclePopulator
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 *  Unit tests to cover [AceUsersRepository]
 *
 *  @author John Sung, Geico
 */
@RunWith(MockitoJUnitRunner.Silent::class)
class AceUsersRepositoryTest : AceApplicationDependentTest() {
    private val contactInformation = AceContactInformation()
    private val dashfolioFlow = AceDashfolioFlow()
    private var destinationActivity: Class<out Activity>? = null
    private val drivers = mutableListOf<AceDriver>()
    private var findGasFilterSettingsHolder = AceFindGasFilterSettingsHolder(AceFindGasFlow())
    private var knownIsPermissionGranted: Boolean = false
    private var knownPersonalPolicyProfile = AcePersonalPolicyProfile("9036139123")
    private var knownIsSpecialtyVehicle: Boolean = false
    private val knownVehicleDetails = AceLookupVehicleDetails().apply {
        typesOfGas = listOf(
                AceCodeDescriptionPair("Diesel", "Diesel"), AceCodeDescriptionPair("Electric", "Electric"),
                AceCodeDescriptionPair("Premium", "Premium"), AceCodeDescriptionPair("Regular", "Regular")
        )
        vehicleColors = listOf(
                AceVehicleColor("black", "Black", "#000000", 4, AceHasOptionState.YES),
                AceVehicleColor("blue", "Blue", "#0000FF", 9, AceHasOptionState.YES),
                AceVehicleColor("brown", "Brown", "#964B00", 6, AceHasOptionState.YES),
                AceVehicleColor("gold", "Gold", "#FFD700", 13, AceHasOptionState.YES)
        )
    }
    private lateinit var knownUserProfileMetricsData: String
    private val loginFlow = AceLoginFlow()
    private var mobilePhoneSectionItem = AceUserSettingsSectionItem()

    @Mock
    private lateinit var mockActivityStarter: AceActivityStarter

    @Mock
    private lateinit var mockAnalyticsFacade: AceAnalyticsFacade

    @Mock
    private lateinit var mockAnalyticsTrackable: AceAnalyticsTrackable

    @Mock
    private lateinit var mockApplicationSession: AceApplicationSession

    @Mock
    private lateinit var mockEventLogger: AceLogEvent

    @Mock
    private lateinit var mockEnvironment: AceEnvironment

    @Mock
    private lateinit var mockFindGasFacade: AceFindGasFacade

    @Mock
    private lateinit var mockOpenFullSiteExecutableWithActivity: AceExecutableWith<Activity>

    @Mock
    private lateinit var mockPermissionCategoryManager: AcePermissionCategoryManager

    @Mock
    private lateinit var mockPersonProfileEditMonitor: AcePersonProfileEditMonitor

    @Mock
    private lateinit var mockPolicy: AceVehiclePolicy

    @Mock
    private lateinit var mockPolicyLocationType: AcePolicyLocationType

    @Mock
    private lateinit var mockPolicySession: AcePolicySession

    @Mock
    private lateinit var mockPostLoginDestinationDeterminer: AceExecutableWith<AceSessionController>

    @Mock
    private lateinit var mockRecommendationsDTO: AceRecommendationsDTO

    @Mock
    private lateinit var mockRegistry: AceRegistry

    @Mock
    private lateinit var mockResourceConverter: AceConverter<Int, String>

    @Mock
    private lateinit var mockSessionController: AceSessionController

    @Mock
    private lateinit var mockStateEmitter: AceStateEmitter

    @Mock
    private lateinit var mockUserProfileSynchronizer: AceUserProfileSynchronizer

    @Mock
    private lateinit var mockUserSession: AceUserSession
    private val userFlowForTest = AceUserFlow()
    private val userProfilePerson = AceUserProfilePerson()
    private val targetUsersModel = AceUsersModel()
    private val telematicsFlow = AceTelematicsFlow()
    private lateinit var testObject: AceUsersRepository

    private fun createTestObject() {
        testObject = object : AceUsersRepository(application) {
            override fun startActivity(context: Context, destinationActivity: Class<out Activity>) {
                this@AceUsersRepositoryTest.destinationActivity = destinationActivity
                mockActivityStarter.startActivity(context, destinationActivity)
            }

            override fun createUserProfileListItemClickRuleFactory() =
                    object : AceUserProfileListItemClickRuleFactory(registry, testObject) {
                        override fun createUserProfileSynchronizer(registry: AceRegistry) =
                                mockUserProfileSynchronizer

                        override fun getPolicyFrom(registry: AceRegistry) = mockPolicy
                        override fun getUserFlowFrom(registry: AceRegistry) = userFlowForTest
                    }

            override fun logEvent(eventLog: AceEventLog) {
                mockEventLogger.logEvent(eventLog)
            }

            override fun updateUserProfileFrom(personProfile: AceUserProfilePerson?) {
                mockUserProfileSynchronizer.updateUserProfileFrom(personProfile)
                trackAction(
                        AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SET,
                        userFlow.userProfileMetricsData
                )
            }

            override fun trackAction(action: String, contextValue: String) {
                analyticsFacade.trackAction(trackable, action, contextValue)
            }

            override fun getAnalyticsFacade() = mockAnalyticsFacade

            override fun getTrackable() = mockAnalyticsTrackable

            override fun recordMetricsForFinishSwitchUser() {
                trackAction(ANALYTICS_USER_PROFILE_SWITCH, USER_PROFILE_SWITCH)
                logEvent(AceUserProfileDetailEvent(FINISH_SWITCH_USER_PROFILE))
            }

            override fun getRegistry() = mockRegistry
            override fun getBuildEnvironment() = mockEnvironment
            override fun getPolicy() = mockPolicy
            override fun getApplicationSession() = mockApplicationSession
            override fun getUserFlow() = userFlowForTest
            override fun getSessionController() = mockSessionController
            override fun createUserSettingsPhoneValidationRuleFactory() =
                    object : AceUserSettingsPhoneValidationRuleFactory(targetUsersModel, mockResourceConverter) {
                        override fun getUnformattedPhoneNumber(phoneNumber: String?): String {
                            return if (phoneNumber != null) stripSeparators(phoneNumber) else ""
                        }

                        override fun obtainMobilePhoneSectionItem(): AceUserSettingsSectionItem {
                            return mobilePhoneSectionItem!!
                        }
                    }

            override fun createUserSettingsPopulator() = object : AceUserSettingsPopulator(mockResourceConverter) {
                override fun isSpecialtyVehicle(policy: AceVehiclePolicy?): Boolean {
                    return false
                }

                override fun shouldShowEditAddressButton(
                        applicationSession: AceApplicationSession?,
                        policySession: AcePolicySession?,
                        userPrivilegeAuthority: AceUserPrivilegeAuthority?
                ) = true

                override fun createPhoneValidationRuleFactory(target: AceUsersModel?): AceUserSettingsPhoneValidationRuleFactory {
                    return object :
                            AceUserSettingsPhoneValidationRuleFactory(targetUsersModel, resourceConverter) {
                        override fun getUnformattedPhoneNumber(phoneNumber: String?) =
                                if (phoneNumber != null) stripSeparators(phoneNumber) else ""
                    }
                }

                override fun createPrimaryVehiclePopulator(): AceUserSettingsPrimaryVehiclePopulator {
                    return object : AceUserSettingsPrimaryVehiclePopulator(resourceConverter) {
                        override fun getLookupVehicleDetails(applicationSession: AceApplicationSession?) =
                                knownVehicleDetails

                        override fun getPrimaryVehicle(
                                userFlow: AceUserFlow?,
                                policyNumber: String?
                        ) = userFlowForTest.person.primaryVehicle
                    }
                }
            }

            override fun getModel() = targetUsersModel
            override fun createPostLoginDestinationDeterminer(): AceExecutableWith<AceSessionController> {
                return mockPostLoginDestinationDeterminer
            }

            override fun createStateEmitter(): AceStateEmitter {
                return mockStateEmitter
            }

            override fun createPersonProfileEditMonitor() = mockPersonProfileEditMonitor
            override fun createRecommentationsDTO() = mockRecommendationsDTO
            override fun isSpecialtyVehicle() = knownIsSpecialtyVehicle
            override fun isPermissionGranted(permission: String) = knownIsPermissionGranted
            override fun createOpenFullSiteExecutable(webLinkName: String?) = mockOpenFullSiteExecutableWithActivity
            override fun getPrimaryVehicle() = userFlowForTest.person.primaryVehicle
            override fun getPersonalPolicyProfileFrom(policyNumber: String?) = knownPersonalPolicyProfile
        }
    }

    private fun stripSeparators(phoneNumber: String): String {
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

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        `when`(mockRegistry.sessionController).thenReturn(mockSessionController)
        `when`(mockRegistry.analyticsFacade).thenReturn(mockAnalyticsFacade)
        `when`(mockRegistry.findGasFacade).thenReturn(mockFindGasFacade)
        `when`(mockRegistry.findGasFacade.settings).thenReturn(findGasFilterSettingsHolder)
        `when`(mockRegistry.permissionCategoryManager).thenReturn(mockPermissionCategoryManager)
        `when`(mockSessionController.applicationSession).thenReturn(mockApplicationSession)
        `when`(mockSessionController.policySession).thenReturn(mockPolicySession)
        `when`(mockSessionController.userSession).thenReturn(mockUserSession)
        `when`(mockUserSession.dashfolioFlow).thenReturn(dashfolioFlow)
        `when`(mockPolicySession.policy).thenReturn(mockPolicy)
        `when`(mockPolicy.drivers).thenReturn(drivers)
        `when`(mockPolicy.contact).thenReturn(contactInformation)
        `when`(mockApplicationSession.telematicsFlow).thenReturn(telematicsFlow)
        `when`(mockApplicationSession.userFlow).thenReturn(userFlowForTest)
        `when`(mockApplicationSession.loginFlow).thenReturn(loginFlow)
        setUpBasicUserProfileDetails()
        setUpStringResources()
        createTestObject()
        buildUsersModel()
        telematicsFlow.driverInformationState = AceInformationState.UNAVAILABLE
    }

    private fun buildUsersModel() {
        testObject.initializeModel()
    }

    private fun setUpBasicUserProfileDetails() {
        with(userFlowForTest.person) {
            fullName = "Tom Brake"
            mobilePhoneNumber = "(578)-233-4232"
        }
        with(mockPolicy.contact.mailingAddress) {
            streetLines.add("5260 Western Ave")
            city = "Chevy Chase"
            state = "MD"
            zipCode = "20815"
        }
    }

    private fun setUpStringResources() {
        `when`(mockResourceConverter.convert(R.string.yourSettings)).thenReturn("Your Settings")
        `when`(mockResourceConverter.convert(R.string.userSetUpReason))
                .thenReturn("Enter basic details now to save time if you ever need roadside assistance.")
        `when`(mockResourceConverter.convert(R.string.name)).thenReturn("Name")
        `when`(mockResourceConverter.convert(R.string.mailingAddress)).thenReturn("Mailing Address")
        `when`(mockResourceConverter.convert(R.string.mobilePhone)).thenReturn("Mobile Phone")
        `when`(mockResourceConverter.convert(R.string.invalidPhoneNumber))
                .thenReturn("The phone number entered is invalid. Please verify.")
        `when`(mockResourceConverter.convert(R.string.chooseYourVehicle))
                .thenReturn("Choose Your Vehicle")
        `when`(mockResourceConverter.convert(R.string.chooseYourVehicleColor))
                .thenReturn("Choose Your Vehicle Color")
        `when`(mockResourceConverter.convert(R.string.yourPrimaryVehicle))
                .thenReturn("Your Primary Vehicle")
        `when`(mockResourceConverter.convert(R.string.fuelType)).thenReturn("Fuel Type")
        `when`(mockResourceConverter.convert(R.string.vehicleColor)).thenReturn("Vehicle Color")
        `when`(mockResourceConverter.convert(R.string.primaryVehicle)).thenReturn("Primary Vehicle")
    }

    private fun setUpForTestHandleListItemRowClickAction(
            fullName: String, isDuckCreek: Boolean,
            knownUserProfileMetricsData: String, userRule: AceUserRole
    ) {
        `when`(mockUserProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(
                false
        )
        drivers.add(AceDriver())
        drivers.add(AceDriver())
        userFlowForTest.person = AceUserProfilePerson().apply {
            this.role = userRule
            this.fullName = fullName
        }
        `when`(mockPolicy.policyLocation).thenReturn(mockPolicyLocationType)
        `when`(mockPolicyLocationType.isDuckCreek).thenReturn(isDuckCreek)
        this.knownUserProfileMetricsData = knownUserProfileMetricsData
    }

    private fun setUpForTestNavigateUserByDestinationAndRunTest(
            contactGeicoType: AceContactGeicoType,
            knownDestinationActivity: Class<out Activity>
    ) {
        this.destinationActivity = null
        userFlowForTest.destination = contactGeicoType
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(knownDestinationActivity, destinationActivity)
    }

    @Test
    fun testHandleBackPressedAction() {
        testObject.handleBackPressedAction()
        assertEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        assertEquals(AceActionConstants.ACTION_USERS, dashfolioFlow.nextPageAction)
    }

    private fun setUpForTestHandleContinueButtonAction(phoneNumber: String) {
        userFlowForTest.resetViewState()
        userFlowForTest.temporaryWorkAddress = AceUserProfileAddress().apply {
            this.city = "Chevy Chase"
            this.state = "MD"
            this.zipCode = "20815"
            this.addressType = AceUserProfileAddressType.WORK_ADDRESS
        }
        var phoneSection = targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1]
        phoneSection.setText(phoneNumber)
    }

    @Test
    fun testHandleContinueButtonActionWithValidPhoneNumber() {
        setUpForTestHandleContinueButtonAction("(678)-123-1103")
        testObject.handleContinueButtonAction()
        with(userFlowForTest.person.workAddress) {
            assertEquals(userFlowForTest.temporaryWorkAddress.city, city)
            assertEquals(userFlowForTest.temporaryWorkAddress.state, state)
            assertEquals(userFlowForTest.temporaryWorkAddress.zipCode, zipCode)
        }
        assertEquals(targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1].text.get(), userFlowForTest.person.mobilePhoneNumber)
        assertEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        verify(mockSessionController).startAction(application, mockPolicySession.postLoginAction)
    }

    @Test
    fun testHandleContinueButtonActionWithInvalidPhoneNumber() {
        setUpForTestHandleContinueButtonAction("(67)-123-1103")
        testObject.handleContinueButtonAction()
        with(userFlowForTest.person.workAddress) {
            assertNotEquals(userFlowForTest.temporaryWorkAddress.city, city)
            assertNotEquals(userFlowForTest.temporaryWorkAddress.state, state)
            assertNotEquals(userFlowForTest.temporaryWorkAddress.zipCode, zipCode)
        }
        assertNotEquals(targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1].text.get(), userFlowForTest.person.mobilePhoneNumber)
        assertNotEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        verify(mockSessionController, never()).startAction(application, mockPolicySession.postLoginAction)
    }

    @Test
    fun testHandleEditButtonClickAction() {
        testObject.handleEditButtonClickAction()
        verify(mockPersonProfileEditMonitor).recordValues()
        verify(mockStateEmitter).emitNavigation(AceConstantState.NavigateAction.USER_SETTINGS_FRAGMENT)
    }

    @Test
    fun testHandleEditUserSettingsButtonActionWithSpecialtyVehicle() {
        knownIsSpecialtyVehicle = true
        testObject.handleEditUserSettingsButtonAction()
        verify(mockRecommendationsDTO).rememberAddressChangeInProgress()
        verify(mockStateEmitter).emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        verify(mockPermissionCategoryManager, never()).action = MitWebLinkNames.UPDATE_ADDRESS
        assertNotEquals(destinationActivity, AceLocationPermissionForWebLinkActivity::class.java)
        verify(mockStateEmitter, never()).emitExecuteWithActivityState { mockOpenFullSiteExecutableWithActivity }
    }

    @Test
    fun testHandleEditUserSettingsButtonActionWithNonSpecialtyVehicle() {
        knownIsPermissionGranted = true
        knownIsSpecialtyVehicle = false
        testObject.handleEditUserSettingsButtonAction()
        verify(mockRecommendationsDTO).rememberAddressChangeInProgress()
        verify(mockStateEmitter, never()).emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        verify(mockPermissionCategoryManager, never()).action = MitWebLinkNames.UPDATE_ADDRESS
        assertNotEquals(destinationActivity, AceLocationPermissionForWebLinkActivity::class.java)
        knownIsPermissionGranted = false
        knownIsSpecialtyVehicle = false
        testObject.handleEditUserSettingsButtonAction()
        verify(mockPermissionCategoryManager).action = MitWebLinkNames.UPDATE_ADDRESS
        assertEquals(destinationActivity, AceLocationPermissionForWebLinkActivity::class.java)
    }

    @Test
    fun testHandleSelectedFuelTypeSpinnerItem() {
        testObject.handleSelectedFuelTypeSpinnerItem(AceOutOfGasTypeEnum.REGULAR_UNLEADED)
        assertEquals(AceOutOfGasTypeEnum.REGULAR_UNLEADED, userFlowForTest.person.primaryVehicle.preferredFuelType)
        assertEquals(AceFindGasProduct.REGULAR, mockRegistry.findGasFacade.settings.fuelCriteria)
        testObject.handleSelectedFuelTypeSpinnerItem(AceOutOfGasTypeEnum.DIESEL)
        assertEquals(AceOutOfGasTypeEnum.DIESEL, userFlowForTest.person.primaryVehicle.preferredFuelType)
        assertEquals(AceFindGasProduct.DIESEL, mockRegistry.findGasFacade.settings.fuelCriteria)
        testObject.handleSelectedFuelTypeSpinnerItem(AceOutOfGasTypeEnum.ELECTRIC)
        assertEquals(AceOutOfGasTypeEnum.ELECTRIC, userFlowForTest.person.primaryVehicle.preferredFuelType)
        assertEquals(AceFindGasProduct.ELECTRIC, mockRegistry.findGasFacade.settings.fuelCriteria)
        testObject.handleSelectedFuelTypeSpinnerItem(AceOutOfGasTypeEnum.PREMIUM_UNLEADED)
        assertEquals(AceOutOfGasTypeEnum.PREMIUM_UNLEADED, userFlowForTest.person.primaryVehicle.preferredFuelType)
        assertEquals(AceFindGasProduct.PREMIUM, mockRegistry.findGasFacade.settings.fuelCriteria)
        verify(mockRegistry.findGasFacade, times(8)).saveSettings(mockRegistry.findGasFacade.settings)
    }

    @Test
    fun testHandleSelectedVehicleColorAction() {
        var knownVehicleColor = AceVehicleColor.UNKNOWN_COLOR
        testObject.handleSelectedVehicleColorAction(knownVehicleColor)
        assertEquals(knownVehicleColor, userFlowForTest.person.primaryVehicle.color)
    }

    private fun getUserSettingsSectionItemBy(listItemIndex: Int, sectionItemIndex: Int) =
            targetUsersModel.userSettingsListItems[listItemIndex].userSettingsSectionItems[sectionItemIndex]

    @Test
    fun testHandleSelectedVehicleActionWithZeroPosition() {
        val vehicle = AceUserProfileVehicle()
        getUserSettingsSectionItemBy(1, 1).setSelectedFuelTypePosition(3)
        getUserSettingsSectionItemBy(1, 2).setSelectedVehicleColorPosition(4)
        testObject.handleSelectedVehicleAction(vehicle, 0)
        assertEquals(vehicle, knownPersonalPolicyProfile.primaryVehicle)
        assertEquals(0, getUserSettingsSectionItemBy(1, 1).selectedFuelTypePosition.get())
        assertEquals(0, getUserSettingsSectionItemBy(1, 2).selectedVehicleColorPosition.get())
    }

    @Test
    fun testHandleSelectedVehicleActionWithNonZeroPosition() {
        val vehicle = AceUserProfileVehicle()
        getUserSettingsSectionItemBy(1, 1).setSelectedFuelTypePosition(3)
        getUserSettingsSectionItemBy(1, 2).setSelectedVehicleColorPosition(4)
        testObject.handleSelectedVehicleAction(vehicle, 1)
        assertEquals(vehicle, knownPersonalPolicyProfile.primaryVehicle)
        assertEquals(3, getUserSettingsSectionItemBy(1, 1).selectedFuelTypePosition.get())
        assertEquals(4, getUserSettingsSectionItemBy(1, 2).selectedVehicleColorPosition.get())
    }

    @Test
    fun testHandleSkipButtonAction() {
        setUpForTestHandleContinueButtonAction("(678)-123-1103")
        testObject.handleSkipButtonAction()
        with(userFlowForTest.person.workAddress) {
            assertEquals(userFlowForTest.temporaryWorkAddress.city, city)
            assertEquals(userFlowForTest.temporaryWorkAddress.state, state)
            assertEquals(userFlowForTest.temporaryWorkAddress.zipCode, zipCode)
        }
        assertEquals(targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1].text.get(), userFlowForTest.person.mobilePhoneNumber)
        assertEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        verify(mockSessionController).startAction(application, mockPolicySession.postLoginAction)
    }

    @Test
    fun testHandleListItemRowClickAction() {
        setUpForTestHandleListItemRowClickAction("", true, "Driver:Driver", AceUserRole.DRIVER)
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(knownUserProfileMetricsData, userFlowForTest.userProfileMetricsData)
        assertEquals(AceInformationState.UNREQUESTED, telematicsFlow.driverInformationState)
    }

    @Test
    fun testHandleListItemRowClickActionWithVariation() {
        setUpForTestHandleListItemRowClickAction(
                "knownFullName", false,
                "Policyholder:Unknown", AceUserRole.PROSPECT
        )
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(knownUserProfileMetricsData, userFlowForTest.userProfileMetricsData)
        assertEquals(AceInformationState.UNAVAILABLE, telematicsFlow.driverInformationState)
    }

    @Test
    fun testHandleSelectedUserOtherwiseRule() {
        `when`(mockUserProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(
                true
        )
        userFlowForTest.person = AceUserProfilePerson()
        `when`(mockPolicy.policyLocation).thenReturn(mockPolicyLocationType)
        `when`(mockPolicyLocationType.isDuckCreek).thenReturn(true)
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(AceInformationState.UNREQUESTED, telematicsFlow.driverInformationState)
        assertEquals(AceDashboardActivity::class.java, destinationActivity)
    }

    @Test
    fun testNavigateUserByDestination() {
        `when`(mockUserProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(
                true
        )
        userFlowForTest.person = userProfilePerson
        setUpForTestNavigateUserByDestinationAndRunTest(
                AceContactGeicoType.CHAT,
                AceContactGeicoActivity::class.java
        )
        setUpForTestNavigateUserByDestinationAndRunTest(
                AceContactGeicoType.EMAIL,
                AceSendAnEmailActivity::class.java
        )
        setUpForTestNavigateUserByDestinationAndRunTest(
                AceContactGeicoType.UNKNOWN,
                AceDashboardActivity::class.java
        )
    }

    @Test
    fun testRecordMetricsForCompletingEditUserSettings() {
        val preEditValues = userFlowForTest.preEditValues
        testObject.recordMetricsForCompletingEditUserSettings()
        assertNotEquals(userFlowForTest.preEditValues, preEditValues)
    }
}