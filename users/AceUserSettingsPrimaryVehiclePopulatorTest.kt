package com.geico.mobile.android.ace.geicoapppresentation.users.usersettings.mvvm.helpers

import com.geico.mobile.R
import com.geico.mobile.android.ace.geicoappbusiness.session.AceApplicationSession
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 *  Unit tests to cover [AceUserSettingsPrimaryVehiclePopulator]
 *
 *  @author John Sung, Geico
 */
@RunWith(MockitoJUnitRunner.Silent::class)
class AceUserSettingsPrimaryVehiclePopulatorTest : AceBaseUserSettingsPopulatorTest() {

	private lateinit var testObject: AceUserSettingsPrimaryVehiclePopulator

	override fun buildKnownUsersModel() {
		knownUsersModel.userSettingsListItems.add(
			testObject.createUserSettingsListItemWith(
				resourceConverter.convert(R.string.yourPrimaryVehicle),
					resourceConverter.convert(R.string.userSetUpReason),
				testObject.buildUserSettingsSectionItems(mockRegistry), true, true
			)
		)
	}

	override fun createTestObject() {
		testObject = object : AceUserSettingsPrimaryVehiclePopulator(resourceConverter) {
			override fun getLookupVehicleDetails(applicationSession: AceApplicationSession?) =
				knownVehicleDetails

			override fun getPrimaryVehicle(
				userFlow: AceUserFlow?,
				policyNumber: String?
			) = userFlowForTest.person.primaryVehicle
		}
	}

	@Test
	fun testUserSettingsPrimaryVehiclePopulator() {
		createTestObject()
		buildKnownUsersModel()
		testObject.populate(mockRegistry, targetUsersModel)
		assertEquals(
			knownUsersModel.userSettingsListItems.size,
			targetUsersModel.userSettingsListItems.size
		)
		with(knownUsersModel.userSettingsListItems[0]) {
			assertEquals(title.get(), targetUsersModel.userSettingsListItems[0].title.get())
			assertEquals(subtitle.get(), targetUsersModel.userSettingsListItems[0].subtitle.get())
			assertUserSettingsSectionItems(
				userSettingsSectionItems,
				targetUsersModel.userSettingsListItems[0].userSettingsSectionItems
			)
			assertEquals(
				shouldShowSubtitle().get(),
				targetUsersModel.userSettingsListItems[0].shouldShowSubtitle().get()
			)
			assertEquals(
				shouldShowSkipButton().get(),
				targetUsersModel.userSettingsListItems[0].shouldShowSkipButton().get()
			)
		}
	}
}