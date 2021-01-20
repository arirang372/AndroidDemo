package com.geico.mobile.android.ace.geicoapppresentation.fullsite.backnavigation;

import androidx.fragment.app.FragmentManager;

import com.geico.mobile.android.ace.geicoappbusiness.ui.AceHybridBackButtonOverrideContext;
import com.geico.mobile.android.ace.geicoappbusiness.ui.AceHybridBackButtonStrategy;
import com.geico.mobile.android.ace.geicoappmodel.enums.policy.AcePolicyLocationType;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.AceRegistryDependentTest;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.dialog.AceOneButtonDialog;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceDialogDoNothingViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static com.geico.mobile.android.ace.geicoappbusiness.application.AceGeicoAppEventConstants.WEB_VIEW_HANDLE_BACK_BUTTON_PRESS;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

/**
 * Unit tests to exercise a behavior of AceQualtricsFeedbackBackButtonDeterminer.java in order to provide confidence that the behavior works fine
 *
 * @author John Sung, Geico
 */
@RunWith(PowerMockRunner.class)
public class AceQualtricsFeedbackBackNavigationDeterminerTest extends AceRegistryDependentTest {

	private static final String KNOWN_POLICY_NUMBER = "778343234";
	private static final String KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL = "https://zn5ydlsbwpgrsccsp-qps.siteintercept.qualtrics.com/WRSiteInterceptEngine/?Q_SIID=SI_4IRI7E7Didzz5Nr&Q_LOC=https%3A%2F%2Fgeico.az1.qualtrics.com%2Fjfe%2Fform%2FSV_9KQU69MSBztvxnn&t=1586978267474";
	private static final String KNOWN_QUALTRICS_FEEDBACK_URL = "https://geico.az1.qualtrics.com/jfe/form/SV_9KQU69MSBztvxnn";
	@Mock
	private AceOneButtonDialog<AceDialogDoNothingViewModel> dialog;
	@Mock
	private FragmentManager fragmentManager;
	private final AceHybridBackButtonOverrideContext hybridBackButtonOverrideContext = new AceHybridBackButtonOverrideContext();

	private void createTestObjectAndSetUpData(AceHybridBackButtonStrategy hybridBackButtonStrategy, String url, String policyNumber, AcePolicyLocationType locationType, boolean isStartUrlQualtricsFeedbackUrl) {
		getVehiclePolicy().setNumber(policyNumber);
		getVehiclePolicy().setPolicyLocation(locationType);
		hybridBackButtonOverrideContext.setStrategy(hybridBackButtonStrategy);
		AceQualtricsFeedbackBackNavigationDeterminer testObject = new AceQualtricsFeedbackBackNavigationDeterminer(fragmentManager, hybridBackButtonOverrideContext, getPublisher(), getVehiclePolicy(), isStartUrlQualtricsFeedbackUrl) {

			@Override
			protected AceOneButtonDialog<AceDialogDoNothingViewModel> createAndShowQualtricsFeedbackConfirmationDialog() {
				return dialog;
			}

			@Override
			protected void dismissDialog(AceOneButtonDialog<AceDialogDoNothingViewModel> dialog) {
				//do nothing
			}
		};
		testObject.executeWith(url);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		doNothing().when(getPublisher()).publish(WEB_VIEW_HANDLE_BACK_BUTTON_PRESS, WEB_VIEW_HANDLE_BACK_BUTTON_PRESS);
	}

	@Test
	public void testThatSetsHybridBackButtonOverrideContextStrategyToComplete() {
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, "", AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, "", AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_URL, "", AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.COMPLETE, KNOWN_QUALTRICS_FEEDBACK_URL, "", AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
	}

	@Test
	public void testThatSetsHybridBackButtonOverrideContextStrategyToUnknown() {
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.UNKNOWN, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, "", AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.COMPLETE, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_SITE_INTERCEPT_URL, "", AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.UNKNOWN, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.WARN, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_URL, KNOWN_POLICY_NUMBER, AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.WARN, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_URL, "", AcePolicyLocationType.DUCK_CREEK, true);
		assertEquals(AceHybridBackButtonStrategy.WARN, hybridBackButtonOverrideContext.getStrategy());
		createTestObjectAndSetUpData(AceHybridBackButtonStrategy.UNKNOWN, KNOWN_QUALTRICS_FEEDBACK_URL, "", AcePolicyLocationType.OASIS, true);
		assertEquals(AceHybridBackButtonStrategy.WARN, hybridBackButtonOverrideContext.getStrategy());
	}
}
