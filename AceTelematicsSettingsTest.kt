package com.geico.mobile.android.ace.geicoappbusiness.telematics

import com.geico.mobile.android.ace.geicoappbusiness.AceRegistryDependentTest
import com.geico.mobile.android.ace.geicoappbusiness.application.AceRegistry
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Unit tests to cover the settings APIs of [AceTelematicsSharedPreferencesDao].
 *
 * @author John Sung & Nick Emerson, Geico
 */
@RunWith(MockitoJUnitRunner.Silent::class)
class AceTelematicsSettingsTest : AceRegistryDependentTest() {

    private lateinit var testObject: AceTelematicsSettings

    @Before
    @Throws(Exception::class)
    override fun setUp() {
        super.setUp()
        testObject = object : AceTelematicsSharedPreferencesDao(registry) {
            override fun createForceRefreshAlertFailureSharedPreferences(registry: AceRegistry) =
                    createSingleBooleanSharedPreferences(sharedPreferences, KNOWN_FORCE_REFRESH_ALERT_PREFERENCE_KEY)

            override fun createOverrideTelematicsVisibilitySharedPreferences(registry: AceRegistry) =
                    createSingleBooleanSharedPreferences(sharedPreferences, KNOWN_OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY)

            override fun createOverrideTelematicsNextGenOfferSignUpVisibilitySharedPreferences(registry: AceRegistry?) =
                    createSingleBooleanSharedPreferences(sharedPreferences, KNOWN_OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY)
        }
    }

    @Test
    fun testForceRefreshAlertFailure() {
        assertFalse(testObject.shouldForceRefreshAlertFailure())
        verify(sharedPreferences).getBoolean(KNOWN_FORCE_REFRESH_ALERT_PREFERENCE_KEY, false)
    }

    @Test
    fun testIsTelematicsVisibilityOverriden() {
        assertFalse(testObject.isTelematicsVisibilityOverridden)
        verify(sharedPreferences)
                .getBoolean(KNOWN_OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY, false)
    }

    @Test
    fun testIsTelematicsNextGenOfferSignUpVisibilityOverriden() {
        assertFalse(testObject.isTelematicsNextGenOfferSignUpVisibilityOverridden)
        verify(sharedPreferences).getBoolean(KNOWN_OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY, false)
    }

    companion object {

        private const val KNOWN_FORCE_REFRESH_ALERT_PREFERENCE_KEY =
                "FORCE_TELEMATICS_REFRESH_ALERT_FAILURE_PREFERENCE_KEY"
        private const val KNOWN_OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY =
                "OVERRIDE_TELEMATICS_VISIBILITY_PREFERENCE_KEY"
        private const val KNOWN_OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY =
                "KNOWN_OVERRIDE_TELEMATICS_NEXT_GEN_OFFER_SIGN_UP_VISIBILITY_PREFERENCE_KEY"
    }
}
