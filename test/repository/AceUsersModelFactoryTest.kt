package com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.repository

import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceUserFlow
import com.geico.mobile.android.ace.geicoappmodel.userprofile.AceUserProfilePerson
import com.geico.mobile.android.ace.geicoapppresentation.users.mvvm.model.AceUsersModel
import org.junit.Test

/**
 *  Unit tests to cover [AceUsersModelFactory]
 *
 *  @author John Sung, Geico
 */
class AceUsersModelFactoryTest {
    private val fullNames = mutableListOf("Tom Brook", "Johnny Walker", "Kevin Bason")
    private val mobilePhoneNumbers = mutableListOf("943-834-0093", "", "264-998-4834")
    private val vehicleDescriptions = mutableListOf("2012 Honda Civic", "2008 Mercedes-Benz CL 550", "")
    private val knownPolicyNumber = "knownPolicyNumber"
    private lateinit var testObject: AceUsersModelFactory
    private val userFlow = AceUserFlow()
    private val knownUsersModel = AceUsersModel()

    @Test
    fun testUsersModelFactoryCreatesUsersModel() {
        for (i in fullNames.indices) {
            setUpConditionsForTest(fullNames[i], mobilePhoneNumbers[i], vehicleDescriptions[i], false)
            buildKnownUsersModel()
            val targetUsersModel = testObject.create(userFlow)
            for ((i, targetUsersProfile) in targetUsersModel.profiles.withIndex()) {
                assert(knownUsersModel.profiles[i].shouldShowVehicleDescription() ==
                        targetUsersProfile.shouldShowVehicleDescription())
                assert(knownUsersModel.profiles[i].shouldShowUserPhone() == targetUsersProfile.shouldShowUserPhone())
                assert(knownUsersModel.profiles[i].shouldShowEditButton() == targetUsersProfile.shouldShowEditButton())
                assert(knownUsersModel.profiles[i].vehicleDescription == targetUsersProfile.vehicleDescription)
            }
        }
    }

    @Test
    fun testUsersModelFactoryCreatesUsersModelWithEditButtonVariation() {
        for (i in fullNames.indices) {
            setUpConditionsForTest(fullNames[i], mobilePhoneNumbers[i], vehicleDescriptions[i], true)
            buildKnownUsersModel()
            val targetUsersModel = testObject.create(userFlow)
            for ((i, targetUsersProfile) in targetUsersModel.profiles.withIndex()) {
                assert(knownUsersModel.profiles[i].shouldShowVehicleDescription() ==
                        targetUsersProfile.shouldShowVehicleDescription())
                assert(knownUsersModel.profiles[i].shouldShowUserPhone() == targetUsersProfile.shouldShowUserPhone())
                assert(knownUsersModel.profiles[i].shouldShowEditButton() == targetUsersProfile.shouldShowEditButton())
                assert(knownUsersModel.profiles[i].vehicleDescription == targetUsersProfile.vehicleDescription)
            }
        }
    }

    private fun setUpConditionsForTest(fullName: String, mobilePhoneNumber: String, vehicleDescription: String,
                                       showEditButtonVariation: Boolean) {
        userFlow.person = createUserProfilePerson(fullName, mobilePhoneNumber, vehicleDescription)
        testObject = object : AceUsersModelFactory(knownPolicyNumber) {
            override fun getVehicleDescription(userProfilePerson: AceUserProfilePerson): String {
                return userProfilePerson.vehicleDescription
            }
        }
        userFlow.currentPolicyPersonProfiles.clear()
        for (i in fullNames.indices) {
            if (showEditButtonVariation && i == 0) {
                userFlow.currentPolicyPersonProfiles.add(userFlow.person)
                continue
            }
            userFlow.currentPolicyPersonProfiles.add(createUserProfilePerson(fullNames[i],
                    mobilePhoneNumbers[i], vehicleDescriptions[i]))
        }
    }

    private fun buildKnownUsersModel() {
        for (userProfile in userFlow.currentPolicyPersonProfiles) {
            userProfile.setShouldShowVehicleDescription(testObject.getVehicleDescription(userFlow.person)
                    .isNotEmpty())
            userProfile.setShouldShowUserPhone(userFlow.person.mobilePhoneNumber.isNotEmpty())
            userProfile.setShouldShowEditButton(userProfile === userFlow.person)
            userProfile.vehicleDescription = testObject.getVehicleDescription(userProfile)
        }
        knownUsersModel.profiles.clear()
        knownUsersModel.profiles.addAll(userFlow.currentPolicyPersonProfiles)
    }

    private fun createUserProfilePerson(fullName: String, mobilePhoneNumber: String, vehicleDescription: String) = AceUserProfilePerson().apply {
        this.fullName = fullName
        this.mobilePhoneNumber = mobilePhoneNumber
        this.vehicleDescription = vehicleDescription
    }
}