package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.helpers

import com.geico.mobile.android.ace.coreframework.patterns.AceFactory
import com.geico.mobile.android.ace.coreframework.rules.AceRule
import com.geico.mobile.android.ace.coreframework.rules.AceSimpleRuleEngine
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession
import com.geico.mobile.android.ace.geicoappbusiness.session.AceSessionController
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import com.geico.mobile.android.ace.geicoappbusiness.users.AceUserProfileSynchronizer
import com.geico.mobile.android.ace.geicoappmodel.AceDriver
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository.AceUsersRepository
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 *  Unit tests to cover [AceUserProfileListItemClickRuleFactory]
 *
 *  @author John Sung, Geico
 */
@RunWith(MockitoJUnitRunner.Silent::class)
class AceUserProfileListItemClickRuleFactoryTest {

    @Mock
    private lateinit var registry: AceRegistry

    @Mock
    private lateinit var sessionController: AceSessionController

    @Mock
    private lateinit var applicationSession: AceApplicationSession

    @Mock
    private lateinit var usersRepository: AceUsersRepository

    @Mock
    private lateinit var userProfileSynchronizer: AceUserProfileSynchronizer

    @Mock
    private lateinit var policySession: AcePolicySession

    private val userFlow = AceUserFlow()

    private lateinit var testObject: AceFactory<List<AceRule<AceUserProfilePerson>>>

    @Mock
    private lateinit var policy: AceVehiclePolicy

    private val userProfilePerson = AceUserProfilePerson()

    private val drivers = mutableListOf<AceDriver>()

    @Before
    fun setUp() {
        `when`(registry.sessionController).thenReturn(sessionController)
        `when`(sessionController.applicationSession).thenReturn(applicationSession)
        `when`(sessionController.policySession).thenReturn(policySession)
        `when`(policySession.policy).thenReturn(policy)
        `when`(policy.drivers).thenReturn(drivers)
        `when`(applicationSession.userFlow).thenReturn(userFlow)
    }

    private fun createTestObjectAndRun() {
        testObject = object : AceUserProfileListItemClickRuleFactory(registry, usersRepository) {
            override fun createUserProfileSynchronizer(registry: AceRegistry) = userProfileSynchronizer
            override fun getPolicyFrom(registry: AceRegistry) = policy
        }
        AceSimpleRuleEngine.DEFAULT.applyFirst(testObject.create(), userProfilePerson)
    }

    @Test
    fun testCreateSelectedUserHasNeverBeenSetUpRuleWithConditionOne() {
        setUpConditionForCreateSelectedUserHasNeverBeenSetUpRule(false, 2)
        createTestObjectAndRun()
        verify(usersRepository).handleSelectedUserHasNeverBeenSetUpRule(userProfilePerson)
    }


    @Test
    fun testCreateSelectedUserHasNeverBeenSetUpRuleWithConditionTwo() {
        setUpConditionForCreateSelectedUserHasNeverBeenSetUpRule(false, 0)
        createTestObjectAndRun()
        verify(usersRepository, never()).handleSelectedUserHasNeverBeenSetUpRule(userProfilePerson)
        verify(usersRepository).handleSelectedUserOtherwiseRule(userProfilePerson)
    }


    @Test
    fun testCreateSelectedUserHasNeverBeenSetUpRuleWithConditionThree() {
        setUpConditionForCreateSelectedUserHasNeverBeenSetUpRule(true, 2)
        createTestObjectAndRun()
        verify(usersRepository, never()).handleSelectedUserHasNeverBeenSetUpRule(userProfilePerson)
        verify(usersRepository).handleSelectedUserOtherwiseRule(userProfilePerson)
    }

    @Test
    fun testCreateSelectedUserHasNeverBeenSetUpRuleWithConditionFour() {
        setUpConditionForCreateSelectedUserHasNeverBeenSetUpRule(true, 0)
        createTestObjectAndRun()
        verify(usersRepository, never()).handleSelectedUserHasNeverBeenSetUpRule(userProfilePerson)
        verify(usersRepository).handleSelectedUserOtherwiseRule(userProfilePerson)
    }

    private fun setUpConditionForCreateSelectedUserHasNeverBeenSetUpRule(
            wasSetUpDoneOnThisDeviceFor: Boolean,
            driverSize: Int
    ) {
        `when`(userProfileSynchronizer.wasSetUpDoneOnThisDeviceFor(userProfilePerson)).thenReturn(wasSetUpDoneOnThisDeviceFor)
        for (i in 1..driverSize) {
            drivers.add(AceDriver())
        }
    }

    @Test
    fun testCreateSelectedUserSameAsFlowUserRuleWithConditionOne() {
        userFlow.person = userProfilePerson
        createTestObjectAndRun()
        verify(usersRepository).navigateUserByDestination()
    }

    @Test
    fun testCreateSelectedUserSameAsFlowUserRuleWithConditionTwo() {
        userFlow.person = AceUserProfilePerson()
        createTestObjectAndRun()
        verify(usersRepository, never()).navigateUserByDestination()
        verify(usersRepository).handleSelectedUserOtherwiseRule(userProfilePerson)
    }
}