package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository

import android.app.Activity
import android.content.Context
import com.geico.mobile.R
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState
import com.geico.mobile.android.ace.coreframework.enums.lifecycle.AceRunState
import com.geico.mobile.android.ace.coreframework.environment.AceEnvironment
import com.geico.mobile.android.ace.coreframework.eventhandling.AceEvent
import com.geico.mobile.android.ace.coreframework.eventhandling.AceListener
import com.geico.mobile.android.ace.coreframework.eventhandling.monitoring.AceEventMonitor
import com.geico.mobile.android.ace.coreframework.features.AceBasicFeatureMode
import com.geico.mobile.android.ace.coreframework.patterns.AceConverter
import com.geico.mobile.android.ace.coreframework.patterns.AceExecutable
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
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceFindGasFlow
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceLoginFlow
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceTelematicsFlow
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SWITCH
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_SWITCH
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLog
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLogConstants
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
import com.geico.mobile.android.ace.geicoappmodel.tag.ViewTag
import com.geico.mobile.android.ace.geicoappmodel.userprofile.*
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.email.mvvm.view.AceSendAnEmailActivity
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceDashboardActivity
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.AceRegistryDependentTest
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceRecommendationsDTO
import com.geico.mobile.android.ace.geicoapppresentation.permission.AceLocationPermissionForWebLinkActivity
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers.AceUserProfileListItemClickRuleFactory
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPhoneValidationRuleFactory
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPopulator
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers.AceUserSettingsPrimaryVehiclePopulator
import com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.model.AceUserSettingsSectionItem
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitWebLinkNames
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.powermock.modules.junit4.PowerMockRunner

/**
 *  Unit tests to cover [AceUsersRepository]
 *
 *  @author John Sung, Geico
 */
@RunWith(PowerMockRunner::class)
class AceUsersRepositoryTest : AceRegistryDependentTest() {

    private val contactInformation = AceContactInformation()
    private var destinationActivity: Class<out Activity>? = null
    private val drivers = mutableListOf<AceDriver>()
    private var findGasFilterSettingsHolder = AceFindGasFilterSettingsHolder(AceFindGasFlow())
    private var isPermissionGranted: Boolean = false
    private var isSpecialtyVehicle: Boolean = false
    private var personalPolicyProfile = AcePersonalPolicyProfile("9036139123")
    private val vehicleDetails = AceLookupVehicleDetails().apply {
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
    private lateinit var knownUserProfileMetricsData: String
    private val loginFlow = AceLoginFlow()
    private var mobilePhoneSectionItem = AceUserSettingsSectionItem()

    @Mock
    private lateinit var mockActivityStarter: AceActivityStarter

    @Mock
    private lateinit var mockAnalyticsTrackable: AceAnalyticsTrackable

    @Mock
    private lateinit var mockEventArgumentsMap: AceEvent<String, String>

    @Mock
    private lateinit var mockEventLogger: AceLogEvent

    @Mock
    private lateinit var mockEventMonitor: AceEventMonitor

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
    private lateinit var mockPostLoginDestinationDeterminer: AceExecutableWith<AceSessionController>

    @Mock
    private lateinit var mockRapidSessionFinisher: AceExecutable

    @Mock
    private lateinit var mockRecommendationsDTO: AceRecommendationsDTO

    @Mock
    private lateinit var mockResourceConverter: AceConverter<Int, String>

    @Mock
    private lateinit var mockListenerForReturnToLoginPage: AceListener<AceEvent<String, String>>

    @Mock
    private lateinit var mockStateEmitter: AceStateEmitter

    @Mock
    private lateinit var mockUserProfileSynchronizer: AceUserProfileSynchronizer

    private val userFlowForTest = AceUserFlow()
    private val userProfilePerson = AceUserProfilePerson()
    private val targetUsersModel = AceUsersModel()
    private val telematicsFlow = AceTelematicsFlow()
    private lateinit var testObject: AceUsersRepository

    private fun buildUsersModel() {
        testObject.initializeModel()
    }

    private fun createKnownUserProfileListItemClickRuleFactory(registry: AceRegistry) =
            object : AceUserProfileListItemClickRuleFactory(registry, testObject) {
                override fun createUserProfileSynchronizer(registry: AceRegistry) =
                        mockUserProfileSynchronizer

                override fun getPolicyFrom(registry: AceRegistry) = mockPolicy
                override fun getUserFlowFrom(registry: AceRegistry) = userFlowForTest
            }

    private fun createKnownUserSettingsPhoneValidationRuleFactory() =
            object :
                    AceUserSettingsPhoneValidationRuleFactory(targetUsersModel, mockResourceConverter) {
                override fun getUnformattedPhoneNumber(phoneNumber: String?) =
                        if (phoneNumber != null) stripSeparators(phoneNumber) else ""

                override fun obtainMobilePhoneSectionItem() = mobilePhoneSectionItem
            }

    private fun createKnownUserSettingsPopulator() =
            object : AceUserSettingsPopulator(mockResourceConverter) {
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
                                vehicleDetails

                        override fun getPrimaryVehicle(
                                userFlow: AceUserFlow?,
                                policyNumber: String?
                        ) = userFlowForTest.person.primaryVehicle
                    }
                }

                override fun isSpecialtyVehicle(policy: AceVehiclePolicy?) = false
                override fun shouldShowEditAddressButton(
                        applicationSession: AceApplicationSession?,
                        policySession: AcePolicySession?,
                        userPrivilegeAuthority: AceUserPrivilegeAuthority?
                ) = true
            }

    private fun createTestObject() {
        testObject = object : AceUsersRepository(application) {
            override fun startActivity(context: Context, destinationActivity: Class<out Activity>) {
                this@AceUsersRepositoryTest.destinationActivity = destinationActivity
                mockActivityStarter.startActivity(context, destinationActivity)
            }

            override fun createEventMonitor() = mockEventMonitor
            override fun createListenerForReturnToLoginPage() = mockListenerForReturnToLoginPage
            override fun createOpenFullSiteExecutable(webLinkName: String?) =
                    mockOpenFullSiteExecutableWithActivity

            override fun createPersonProfileEditMonitor() = mockPersonProfileEditMonitor
            override fun createPostLoginDestinationDeterminer() = mockPostLoginDestinationDeterminer
            override fun createRapidSessionFinisher() = mockRapidSessionFinisher
            override fun createRecommentationsDTO() = mockRecommendationsDTO
            override fun createStateEmitter() = mockStateEmitter
            override fun createUserProfileListItemClickRuleFactory() =
                    createKnownUserProfileListItemClickRuleFactory(registry)

            override fun createUserSettingsPhoneValidationRuleFactory() =
                    createKnownUserSettingsPhoneValidationRuleFactory()

            override fun createUserSettingsPopulator() = createKnownUserSettingsPopulator()
            override fun getAnalyticsFacade() = mockAnalyticsFacade
            override fun getApplicationSession() = mockApplicationSession
            override fun getBuildEnvironment() = mockEnvironment
            override fun getModel() = targetUsersModel
            override fun getPersonalPolicyProfileFrom(policyNumber: String?) =
                    this@AceUsersRepositoryTest.personalPolicyProfile

            override fun getPolicy() = mockPolicy
            override fun getPrimaryVehicle() = userFlowForTest.person.primaryVehicle
            override fun getRegistry() = mockRegistry
            override fun getSessionController() = mockSessionController
            override fun getTrackable() = mockAnalyticsTrackable
            override fun getUserFlow() = userFlowForTest
            override fun isSpecialtyVehicle() = this@AceUsersRepositoryTest.isSpecialtyVehicle
            override fun isPermissionGranted(permission: String) =
                    this@AceUsersRepositoryTest.isPermissionGranted

            override fun logEvent(eventLog: AceEventLog) {
                mockEventLogger.logEvent(eventLog)
            }

            override fun recordMetricsForFinishSwitchUser() {
                trackAction(ANALYTICS_USER_PROFILE_SWITCH, USER_PROFILE_SWITCH)
                logEvent(AceUserProfileDetailEvent(FINISH_SWITCH_USER_PROFILE))
            }

            override fun trackAction(action: String, contextValue: String) {
                analyticsFacade.trackAction(trackable, action, contextValue)
            }

            override fun updateUserProfileFrom(personProfile: AceUserProfilePerson?) {
                mockUserProfileSynchronizer.updateUserProfileFrom(personProfile)
                trackAction(
                        AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SET,
                        userFlow.userProfileMetricsData
                )
            }
        }
    }

    private fun getUserSettingsSectionItemBy(listItemIndex: Int, sectionItemIndex: Int) =
            targetUsersModel.userSettingsListItems[listItemIndex].userSettingsSectionItems[sectionItemIndex]

    private fun runTestHandleSelectedFuelTypeSpinnerItemWith(
            knownFuelType: AceOutOfGasTypeEnum,
            knownFindGasProduct: AceFindGasProduct
    ) {
        testObject.handleSelectedFuelTypeSpinnerItem(knownFuelType)
        assertEquals(
                knownFuelType,
                userFlowForTest.person.primaryVehicle.preferredFuelType
        )
        assertEquals(knownFindGasProduct, mockRegistry.findGasFacade.settings.fuelCriteria)
    }

    private fun stripSeparators(phoneNumber: String): String {
        if (phoneNumber.isEmpty()) return ""
        val len = phoneNumber.length - 1
        val builder = StringBuilder()
        for (i in 0..len) {
            val char: Char = phoneNumber[i]
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
        `when`(mockRegistry.findGasFacade).thenReturn(mockFindGasFacade)
        `when`(mockRegistry.findGasFacade.settings).thenReturn(findGasFilterSettingsHolder)
        `when`(mockRegistry.permissionCategoryManager).thenReturn(mockPermissionCategoryManager)
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
            fullName: String,
            isDuckCreek: Boolean,
            knownUserProfileMetricsData: String,
            userRule: AceUserRole
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

    private fun setUpForTestHandleContinueButtonAction(phoneNumber: String) {
        userFlowForTest.resetViewState()
        userFlowForTest.temporaryWorkAddress = AceUserProfileAddress().apply {
            this.city = "Fredericksburg"
            this.state = "VA"
            this.zipCode = "22401"
            this.addressType = AceUserProfileAddressType.WORK_ADDRESS
        }
        val phoneSection = targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1]
        phoneSection.setText(phoneNumber)
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

    @Test
    fun testHandleContinueButtonActionWithInvalidPhoneNumber() {
        setUpForTestHandleContinueButtonAction("(67)-123-1103")
        testObject.handleContinueButtonAction()
        with(userFlowForTest.person.workAddress) {
            assertNotEquals(userFlowForTest.temporaryWorkAddress.city, city)
            assertNotEquals(userFlowForTest.temporaryWorkAddress.state, state)
            assertNotEquals(userFlowForTest.temporaryWorkAddress.zipCode, zipCode)
        }
        assertNotEquals(
                targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1].text.get(),
                userFlowForTest.person.mobilePhoneNumber
        )
        assertNotEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        verify(mockSessionController, never()).startAction(
                application,
                mockPolicySession.postLoginAction
        )
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
        assertEquals(
                targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1].text.get(),
                userFlowForTest.person.mobilePhoneNumber
        )
        assertEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        verify(mockSessionController).startAction(application, mockPolicySession.postLoginAction)
    }

    @Test
    fun testHandleEditButtonClickAction() {
        testObject.handleEditButtonClickAction()
        verify(mockPersonProfileEditMonitor).recordValues()
        verify(mockStateEmitter).emitNavigation(AceConstantState.NavigateAction.USER_SETTINGS_FRAGMENT)
    }

    @Test
    fun testHandleEditUserSettingsButtonActionWithSpecialtyVehicle() {
        isSpecialtyVehicle = true
        testObject.handleEditUserSettingsButtonAction()
        verify(mockRecommendationsDTO).rememberAddressChangeInProgress()
        verify(mockStateEmitter).emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        verify(mockPermissionCategoryManager, never()).action = MitWebLinkNames.UPDATE_ADDRESS
        assertNotEquals(destinationActivity, AceLocationPermissionForWebLinkActivity::class.java)
        verify(
                mockStateEmitter,
                never()
        ).emitExecuteWithActivityState { mockOpenFullSiteExecutableWithActivity }
    }

    @Test
    fun testHandleEditUserSettingsButtonActionWithNonSpecialtyVehicle() {
        isPermissionGranted = true
        isSpecialtyVehicle = false
        testObject.handleEditUserSettingsButtonAction()
        verify(mockRecommendationsDTO).rememberAddressChangeInProgress()
        verify(mockStateEmitter, never()).emitCallToMakeChangesDialogVisibility(
                AceChangeableEmittedState.Visibility.VISIBLE
        )
        verify(mockPermissionCategoryManager, never()).action = MitWebLinkNames.UPDATE_ADDRESS
        assertNotEquals(destinationActivity, AceLocationPermissionForWebLinkActivity::class.java)
    }

    @Test
    fun testHandleEditUserSettingsButtonActionWithNonSpecialtyVehicleAndNoGrantedPermissions() {
        isPermissionGranted = false
        isSpecialtyVehicle = false
        testObject.handleEditUserSettingsButtonAction()
        verify(mockPermissionCategoryManager).action = MitWebLinkNames.UPDATE_ADDRESS
        assertEquals(destinationActivity, AceLocationPermissionForWebLinkActivity::class.java)
    }

    @Test
    fun testHandleSelectedFuelTypeSpinnerItem() {
        runTestHandleSelectedFuelTypeSpinnerItemWith(
                AceOutOfGasTypeEnum.REGULAR_UNLEADED,
                AceFindGasProduct.REGULAR
        )
        runTestHandleSelectedFuelTypeSpinnerItemWith(
                AceOutOfGasTypeEnum.DIESEL,
                AceFindGasProduct.DIESEL
        )
        runTestHandleSelectedFuelTypeSpinnerItemWith(
                AceOutOfGasTypeEnum.ELECTRIC,
                AceFindGasProduct.ELECTRIC
        )
        runTestHandleSelectedFuelTypeSpinnerItemWith(
                AceOutOfGasTypeEnum.PREMIUM_UNLEADED,
                AceFindGasProduct.PREMIUM
        )
        verify(
                mockRegistry.findGasFacade,
                times(8)
        ).saveSettings(mockRegistry.findGasFacade.settings)
    }

    @Test
    fun testHandleSelectedVehicleColorAction() {
        val knownVehicleColor = AceVehicleColor.UNKNOWN_COLOR
        testObject.handleSelectedVehicleColorAction(knownVehicleColor)
        assertEquals(knownVehicleColor, userFlowForTest.person.primaryVehicle.color)
    }

    @Test
    fun testHandleSelectedVehicleActionWithZeroPosition() {
        val vehicle = AceUserProfileVehicle()
        val fuelTypeSectionItem =
                getUserSettingsSectionItemBy(1, 1).apply { setSelectedFuelTypePosition(3) }
        val vehicleColorSectionItem =
                getUserSettingsSectionItemBy(1, 2).apply { setSelectedVehicleColorPosition(4) }
        testObject.handleSelectedVehicleAction(vehicle, 0)
        assertEquals(vehicle, personalPolicyProfile.primaryVehicle)
        assertEquals(0, fuelTypeSectionItem.selectedFuelTypePosition.get())
        assertEquals(0, vehicleColorSectionItem.selectedVehicleColorPosition.get())
    }

    @Test
    fun testHandleSelectedVehicleActionWithNonZeroPosition() {
        val vehicle = AceUserProfileVehicle()
        val fuelTypeSectionItem =
                getUserSettingsSectionItemBy(1, 1).apply { setSelectedFuelTypePosition(3) }
        val vehicleColorSectionItem =
                getUserSettingsSectionItemBy(1, 2).apply { setSelectedVehicleColorPosition(4) }
        testObject.handleSelectedVehicleAction(vehicle, 1)
        assertEquals(vehicle, personalPolicyProfile.primaryVehicle)
        assertEquals(3, fuelTypeSectionItem.selectedFuelTypePosition.get())
        assertEquals(4, vehicleColorSectionItem.selectedVehicleColorPosition.get())
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
        assertEquals(
                targetUsersModel.userSettingsListItems[0].userSettingsSectionItems[1].text.get(),
                userFlowForTest.person.mobilePhoneNumber
        )
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
        `when`(mockUserProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson))
                .thenReturn(
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
        `when`(mockUserProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson))
                .thenReturn(
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
    fun testNavigateUserToLoginPage() {
        featureConfigurationModel.modeForCancelledPolicyPage = AceBasicFeatureMode.INITIAL
        `when`(mockEventArgumentsMap.id).thenReturn("INITIAL")
        `when`(mockEventArgumentsMap.subject).thenReturn("KNOWN_SUBJECT")
        testObject.navigateUserToLoginPage(mockEventArgumentsMap)
        verify(mockApplicationSession).isFinishSessionFromCancelledPolicyAlert = false
        verify(mockApplicationSession).policyDetailPageNavigationAction = ""
        verify(mockApplicationSession).selectedDriverClientId = ""
        verify(mockApplicationSession).selectedVehicleId = ""
        assertEquals(AceInformationState.UNREQUESTED, telematicsFlow.driverInformationState)
        verify(mockRapidSessionFinisher).execute()
        verify(mockSessionController).beLoggedOut()
        verify(mockApplicationSession).setRunState(AceRunState.RUNNING)
        assertEquals(mockEventArgumentsMap.subject, loginFlow.postLoginError)
    }

    @Test
    fun testOpenDialog() {
        testObject.openDialog(AceConstantState.DialogTag.CALL_TO_MAKE_CHANGES)
        verify(mockStateEmitter).emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        testObject.openDialog(AceConstantState.DialogTag.ENROLL_WITH_DEVICE_UNLOCK)
        verify(mockStateEmitter).emitEnrollWithDeviceUnlockDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        testObject.openDialog(AceConstantState.DialogTag.KEEP_ME_LOGGED_IN)
        verify(mockStateEmitter).emitKeepMeLoggedInDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
    }

    @Test
    fun testOpenDialogWithUnrelatedDialogTag() {
        testObject.openDialog(AceConstantState.DialogTag.ROADSIDE_CALL_GEICO_DIALOG)
        verify(mockStateEmitter, never()).emitCallToMakeChangesDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        verify(mockStateEmitter, never()).emitEnrollWithDeviceUnlockDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
        verify(mockStateEmitter, never()).emitKeepMeLoggedInDialogVisibility(AceChangeableEmittedState.Visibility.VISIBLE)
    }

    @Test
    fun testRecordMetricsForFinishSwitchUser() {
        testObject.recordMetricsForFinishSwitchUser()
        verify(testObject).trackAction(ANALYTICS_USER_PROFILE_SWITCH, USER_PROFILE_SWITCH)
        verify(testObject).logEvent(AceUserProfileDetailEvent(AceEventLogConstants.FINISH_SWITCH_USER_PROFILE))
    }

    @Test
    fun testRecordMetricsForCompletingEditUserSettings() {
        val preEditValues = userFlowForTest.preEditValues
        testObject.recordMetricsForCompletingEditUserSettings()
        assertNotEquals(userFlowForTest.preEditValues, preEditValues)
    }

    @Test
    fun testRegisterListeners() {
        testObject.model.viewTag = ViewTag.USER_SETTINGS_FRAGMENT
        testObject.registerListeners()
        verify(mockEventMonitor).registerListener(mockListenerForReturnToLoginPage)
    }

    @Test
    fun testRegisterListenersWithUnrelatedViewTag() {
        testObject.model.viewTag = ViewTag.USERS_FRAGMENT
        testObject.registerListeners()
        verify(mockEventMonitor, never()).registerListener(mockListenerForReturnToLoginPage)
    }
}
