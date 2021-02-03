package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers

import com.geico.mobile.R
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import com.geico.mobile.android.ace.geicoappbusiness.userprivileges.AceUserPrivilegeAuthority
import com.geico.mobile.android.ace.geicoappmodel.AceVehiclePolicy
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 *  Unit tests to cover [AceUserSettingsPopulator]
 *
 *  @author John Sung, Geico
 */
@RunWith(MockitoJUnitRunner.Silent::class)
class AceUserSettingsPopulatorTest : AceBaseUserSettingsPopulatorTest() {

	private lateinit var testObject: AceUserSettingsPopulator

	private fun assertUsersModelValue() {
		assertEquals(
			knownUsersModel.userSettingsListItems.size,
			targetUsersModel.userSettingsListItems.size
		)

		for (i in knownUsersModel.userSettingsListItems.indices) {
			with(knownUsersModel.userSettingsListItems[i]) {
				assertEquals(title.get(), targetUsersModel.userSettingsListItems[i].title.get())
				assertEquals(
					subtitle.get(),
					targetUsersModel.userSettingsListItems[i].subtitle.get()
				)
				assertUserSettingsSectionItems(
					userSettingsSectionItems,
					targetUsersModel.userSettingsListItems[i].userSettingsSectionItems
				)
				assertEquals(
					shouldShowSubtitle().get(),
					targetUsersModel.userSettingsListItems[i].shouldShowSubtitle().get()
				)
				assertEquals(
					shouldShowSkipButton().get(),
					targetUsersModel.userSettingsListItems[i].shouldShowSkipButton().get()
				)
			}
		}
	}

	override fun buildKnownUsersModel() {
		knownUsersModel.userSettingsListItems.clear()
		knownUsersModel.userSettingsListItems.add(
			testObject.createUserSettingsListItemWith(
				resourceConverter.convert(R.string.yourSettings),
				"",
				testObject.buildUserSettingsSectionItems(mockRegistry, knownUsersModel),
				false,
				false
			)
		)
		createUserSettingsPrimaryVehiclePopulator().populate(mockRegistry, knownUsersModel)
	}

	private fun createTestObject(isSpecialtyVehicle: Boolean, shouldShowEditAddressButton: Boolean) {
		testObject = object : AceUserSettingsPopulator(resourceConverter) {
			override fun isSpecialtyVehicle(policy: AceVehiclePolicy?): Boolean {
				return isSpecialtyVehicle
			}

			override fun shouldShowEditAddressButton(
				applicationSession: AceApplicationSession?,
				policySession: AcePolicySession?,
				userPrivilegeAuthority: AceUserPrivilegeAuthority?
			) = shouldShowEditAddressButton

			override fun createPhoneValidationRuleFactory(target: AceUsersModel?): AceUserSettingsPhoneValidationRuleFactory {
				return object :
					AceUserSettingsPhoneValidationRuleFactory(targetUsersModel, resourceConverter) {
					override fun getUnformattedPhoneNumber(phoneNumber: String?) =
						if (phoneNumber != null) stripSeparators(phoneNumber) else ""
				}
			}

			override fun createPrimaryVehiclePopulator(): AceUserSettingsPrimaryVehiclePopulator {
				return createUserSettingsPrimaryVehiclePopulator()
			}
		}
	}

	private fun createUserSettingsPrimaryVehiclePopulator() =
		object : AceUserSettingsPrimaryVehiclePopulator(resourceConverter) {
			override fun getLookupVehicleDetails(applicationSession: AceApplicationSession?) =
				knownVehicleDetails

			override fun getPrimaryVehicle(
				userFlow: AceUserFlow?,
				policyNumber: String?
			) = userFlowForTest.person.primaryVehicle
		}

	@Before
	override fun setUp() {
		super.setUp()
		with(userFlowForTest.person) {
			fullName = "Tom Brake"
			mobilePhoneNumber = "(578)-233-4232"
		}
		with(policy.contact.mailingAddress) {
			streetLines.add("5260 Western Ave")
			city = "Chevy Chase"
			state = "MD"
			zipCode = "20815"
		}
	}

	@Test
	fun testUserSettingsPopulator() {
		testUserSettingsPopulatorWith(isSpecialtyVehicle = true, shouldShowEditAddressButton = true)
		testUserSettingsPopulatorWith(isSpecialtyVehicle = true, shouldShowEditAddressButton = false)
		testUserSettingsPopulatorWith(isSpecialtyVehicle = false, shouldShowEditAddressButton = true)
		testUserSettingsPopulatorWith(isSpecialtyVehicle = false, shouldShowEditAddressButton = false)
	}

	private fun testUserSettingsPopulatorWith(
		isSpecialtyVehicle: Boolean,
		shouldShowEditAddressButton: Boolean
	) {
		createTestObject(isSpecialtyVehicle, shouldShowEditAddressButton)
		buildKnownUsersModel()
		testObject.populate(mockRegistry, targetUsersModel)
		assertUsersModelValue()
	}
}