package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository

import android.app.Activity
import android.content.Context
import com.geico.mobile.android.ace.coreframework.enums.informationstate.AceInformationState
import com.geico.mobile.android.ace.geicoappbusiness.application.AceActionConstants
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController
import com.geico.mobile.android.ace.geicoappbusiness.session.AceUserSession
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceDashfolioFlow
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceTelematicsFlow
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceViewExhibitor
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SWITCH
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsContextConstants.USER_PROFILE_SWITCH
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsFacade
import com.geico.mobile.android.ace.geicoappbusiness.ui.analytics.AceAnalyticsTrackable
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.AceEventLog
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceActivityStarter
import com.geico.mobile.android.ace.geicoappbusiness.usecase.AceLogEvent
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileDetailEvent
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSynchronizer
import com.geico.mobile.android.ace.geicoappmodel.AceDriver
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy
import com.geico.mobile.android.ace.geicoappmodel.enums.AceContactGeicoType
import com.geico.mobile.android.ace.geicoappmodel.enums.policy.AcePolicyLocationType
import com.geico.mobile.android.ace.geicoappmodel.enums.users.AceUserRole
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.email.mvvm.view.AceSendAnEmailActivity
import com.geico.mobile.android.ace.geicoapppresentation.contactgeico.mvvm.view.AceContactGeicoActivity
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view.AceDashboardActivity
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.AceApplicationDependentTest
import com.geico.mobile.android.ace.geicoapppresentation.users.AceUserSetUpActivity
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers.AceUserProfileListItemClickRuleFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 *  Unit tests to cover [AceUsersRepository]
 *
 *  @author John Sung, Geico
 */
@RunWith(MockitoJUnitRunner.Silent::class)
class AceUsersRepositoryTest : AceApplicationDependentTest() {
    @Mock
    private lateinit var registry: AceRegistry

    @Mock
    private lateinit var mockSessionController: AceSessionController

    private lateinit var testObject: AceUsersRepository

    @Mock
    private lateinit var userSession: AceUserSession

    @Mock
    private lateinit var mockApplicationSession: AceApplicationSession

    @Mock
    private lateinit var policySession: AcePolicySession

    @Mock
    private lateinit var activityStarter: AceActivityStarter

    @Mock
    private lateinit var userProfileSynchronizer: AceUserProfileSynchronizer

    private val userFlowForTest = AceUserFlow()

    @Mock
    private lateinit var mockPolicy: AceVehiclePolicy
    private val userProfilePerson = AceUserProfilePerson()

    private val drivers = mutableListOf<AceDriver>()

    @Mock
    private lateinit var eventLogger: AceLogEvent

    @Mock
    private lateinit var mockAnalyticsFacade: AceAnalyticsFacade

    @Mock
    private lateinit var mockAnalyticsTrackable: AceAnalyticsTrackable

    private val telematicsFlow = AceTelematicsFlow()
    private val dashfolioFlow = AceDashfolioFlow()
    private lateinit var knownUserProfileMetricsData: String

    @Mock
    private lateinit var policyLocationType: AcePolicyLocationType

    private var destinationActivity: Class<out Activity>? = null

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        `when`(registry.sessionController).thenReturn(mockSessionController)
        `when`(registry.analyticsFacade).thenReturn(mockAnalyticsFacade)
        `when`(mockSessionController.applicationSession).thenReturn(mockApplicationSession)
        `when`(mockSessionController.policySession).thenReturn(policySession)
        `when`(mockSessionController.userSession).thenReturn(userSession)
        `when`(userSession.dashfolioFlow).thenReturn(dashfolioFlow)
        `when`(policySession.policy).thenReturn(mockPolicy)
        `when`(mockPolicy.drivers).thenReturn(drivers)
        `when`(mockApplicationSession.telematicsFlow).thenReturn(telematicsFlow)
        `when`(mockApplicationSession.userFlow).thenReturn(userFlowForTest)

        testObject = object : AceUsersRepository(application) {
            override fun startActivity(context: Context, destinationActivity: Class<out Activity>) {
                this@AceUsersRepositoryTest.destinationActivity = destinationActivity
                activityStarter.startActivity(context, destinationActivity)
            }

            override fun createUserProfileListItemClickRuleFactory() = object : AceUserProfileListItemClickRuleFactory(registry, testObject) {
                override fun createUserProfileSynchronizer(registry: AceRegistry) = userProfileSynchronizer
                override fun getPolicyFrom(registry: AceRegistry) = mockPolicy
                override fun getUserFlowFrom(registry: AceRegistry) = userFlowForTest
            }

            override fun logEvent(eventLog: AceEventLog) {
                eventLogger.logEvent(eventLog)
            }

            override fun updateUserProfileFrom(personProfile: AceUserProfilePerson?) {
                userProfileSynchronizer.updateUserProfileFrom(personProfile)
                trackAction(AceAnalyticsActionConstants.ANALYTICS_USER_PROFILE_SET, userFlow.userProfileMetricsData)
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

            override fun getPolicy() = mockPolicy
            override fun getApplicationSession() = mockApplicationSession
            override fun getUserFlow() = userFlowForTest
            override fun getSessionController() = mockSessionController
        }

        telematicsFlow.driverInformationState = AceInformationState.UNAVAILABLE
    }

    @Test
    fun testHandleListItemRowClickAction() {
        setUpForTestHandleListItemRowClickAction("", true, "Driver:Driver", AceUserRole.DRIVER)
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(knownUserProfileMetricsData, userFlowForTest.userProfileMetricsData)
        assertEquals(AceInformationState.UNREQUESTED, telematicsFlow.driverInformationState)
        assertEquals(AceUserSetUpActivity::class.java, destinationActivity)
    }

    private fun setUpForTestHandleListItemRowClickAction(fullName: String, isDuckCreek: Boolean,
                                                         knownUserProfileMetricsData: String, userRule: AceUserRole) {
        `when`(userProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(false)
        drivers.add(AceDriver())
        drivers.add(AceDriver())
        userFlowForTest.person = AceUserProfilePerson().apply {
            this.role = userRule
            this.fullName = fullName
        }
        `when`(mockPolicy.policyLocation).thenReturn(policyLocationType)
        `when`(policyLocationType.isDuckCreek).thenReturn(isDuckCreek)
        this.knownUserProfileMetricsData = knownUserProfileMetricsData
    }

    @Test
    fun testHandleListItemRowClickActionWithVariation() {
        setUpForTestHandleListItemRowClickAction("knownFullName", false,
                "Policyholder:Unknown", AceUserRole.PROSPECT)
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(knownUserProfileMetricsData, userFlowForTest.userProfileMetricsData)
        assertEquals(AceInformationState.UNAVAILABLE, telematicsFlow.driverInformationState)
        assertEquals(AceUserSetUpActivity::class.java, destinationActivity)
    }

    @Test
    fun testNavigateUserByDestination() {
        `when`(userProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(true)
        userFlowForTest.person = userProfilePerson
        setUpForNavigateUserByDestinationAndRunTest(AceContactGeicoType.CHAT, AceContactGeicoActivity::class.java)
        setUpForNavigateUserByDestinationAndRunTest(AceContactGeicoType.EMAIL, AceSendAnEmailActivity::class.java)
        setUpForNavigateUserByDestinationAndRunTest(AceContactGeicoType.UNKNOWN, AceDashboardActivity::class.java)
    }

    private fun setUpForNavigateUserByDestinationAndRunTest(contactGeicoType: AceContactGeicoType, knownDestinationActivity: Class<out Activity>) {
        this.destinationActivity = null
        userFlowForTest.destination = contactGeicoType
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(knownDestinationActivity, destinationActivity)
    }

    @Test
    fun testHandleSelectedUserOtherwiseRule() {
        `when`(userProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(true)
        userFlowForTest.person = AceUserProfilePerson()
        `when`(mockPolicy.policyLocation).thenReturn(policyLocationType)
        `when`(policyLocationType.isDuckCreek).thenReturn(true)
        testObject.handleListItemRowClickAction(userProfilePerson)
        assertEquals(AceInformationState.UNREQUESTED, telematicsFlow.driverInformationState)
        assertEquals(AceDashboardActivity::class.java, destinationActivity)
    }

    @Test
    fun testHandleBackPressedAction() {
        testObject.handleBackPressedAction()
        assertEquals(AceViewExhibitor.VIEW_SHOWED_TO_USER, userFlowForTest.viewState)
        assertEquals(AceActionConstants.ACTION_USERS, dashfolioFlow.nextPageAction)
    }
}