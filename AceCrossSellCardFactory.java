package com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.enumerating.AceBasicEnumerator;
import com.geico.mobile.android.ace.coreframework.patterns.AceCustomFactory;
import com.geico.mobile.android.ace.geicoappbusiness.patterns.AceExecutableWith;
import com.geico.mobile.android.ace.geicoappbusiness.repository.AceBaseRepository;
import com.geico.mobile.android.ace.geicoappbusiness.session.AcePolicySession;
import com.geico.mobile.android.ace.geicoappbusiness.session.flows.model.AceWeatherFlow;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard.DashboardCardType;
import com.geico.mobile.android.ace.geicoapppresentation.action.AceAction;
import com.geico.mobile.android.ace.geicoapppresentation.action.AceTelematicsSignUpAction;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.cards.AceTelematicsSignUpCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceBaseDashboardCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceDashboardCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceCrossSellSectionHeader;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceDiscoverOffersCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating cross sell cards that is displayed in the dashboard view.
 *
 * @author Gopal Palanisamy
 */
public class AceCrossSellCardFactory implements AceCustomFactory<List<AceDashboardCard>, AceBaseRepository> {

    private void considerRemovingDiscoverOffersCard(List<AceDashboardCard> dashboardCards) {
        if (shouldRemoveDiscoverOffersCard(dashboardCards)) {
            dashboardCards.removeAll(AceBasicEnumerator.DEFAULT.select(dashboardCards,
                    dashboardCard -> dashboardCard.getCardType() == DashboardCardType.DISCOVER_OFFERS));
        }
    }

    private void considerRemovingWeatherAlertsCard(List<AceDashboardCard> dashboardCards, @NonNull AceBaseRepository repository) {
        if (getWeatherFlow(repository).shouldShowPickedForYouWeatherCard()) return;
        dashboardCards.removeAll(AceBasicEnumerator.DEFAULT.select(dashboardCards,
                dashboardCard -> DashboardCardType.WEATHER_ALERTS_FOR_YOU.equals(dashboardCard.getCardType())));
    }

    @NonNull
    @Override
    public List<AceDashboardCard> create(@NonNull AceBaseRepository repository) {
        List<AceDashboardCard> cards = new ArrayList<>();
        cards.add(createCrossSellSectionHeaderCard(repository));
        considerAddingTelematicsSignUpCard(cards, repository);
        cards.add(createWeatherAlertsCard(repository));
        cards.add(createDiscoverOffersCard(repository));
        cards.addAll(createCrossSellPromoCards(repository));
        considerRemovingDiscoverOffersCard(cards);
        considerRemovingWeatherAlertsCard(cards, repository);
        return cards;
    }

    private List<AceDashboardCard> createCrossSellPromoCards(@NonNull AceBaseRepository repository) {
        return new AceCrossSellCardDerivation().deriveValueFrom(repository);
    }

    private AceDashboardCard createCrossSellSectionHeaderCard(AceBaseRepository repository) {
        return new AceCrossSellSectionHeader(repository.getCrossSellSectionHeaderText());
    }

    private AceDashboardCard createDiscoverOffersCard(AceBaseRepository repository) {
        String title = repository.getString(R.string.discoverOffersCardTitleText);
        String subtitle = repository.getString(R.string.discoverOffersCardSubtitleText);
        AceExecutableWith<AceBaseActivity> action = new AceDiscoverOffersCardAction(repository.getApplication());
        return new AceDiscoverOffersCard(title, subtitle, action);
    }

    private void considerAddingTelematicsSignUpCard(List<AceDashboardCard> cards, AceBaseRepository repository) {
        if (repository.createTelematicsSharedPreferences().isTelematicsNextGenOfferSignUpVisibilityOverridden()) {
            cards.add(createTelematicsSignUpCard(repository));
        }
    }

    private AceDashboardCard createTelematicsSignUpCard(@NonNull AceBaseRepository repository) {
        String title = repository.getString(R.string.telematicsSignUpCardTitle);
        String subtitle = repository.getString(R.string.telematicsSignUpCardSubtitle);
        return new AceTelematicsSignUpCard(title, subtitle, new AceTelematicsSignUpAction(repository.getApplication()));
    }

    private AceDashboardCard createWeatherAlertsCard(@NonNull AceBaseRepository repository) {
        String title = repository.getString(R.string.weatherAlertsPickedForYouCardTitle);
        String subtitle = repository.getString(R.string.weatherAlertsPickedForYouCardSubTitle);
        AceExecutableWith<AceBaseActivity> action = new AceTelematicsSignUpAction(repository.getApplication());
        return new AceWeatherAlertsCard(title, subtitle, action);
    }

    private AcePolicySession getPolicySession(@NonNull AceBaseRepository repository) {
        return repository.getPolicySession();
    }

    private AceWeatherFlow getWeatherFlow(@NonNull AceBaseRepository repository) {
        return getPolicySession(repository).getWeatherFlow();
    }

    private boolean isWeatherCardSelected(@NonNull AceBaseRepository repository) {
        return getWeatherFlow(repository).isWeatherCardSelected(getPolicySession(repository).getPolicyNumber(), repository.getApplication());
    }

    private boolean shouldRemoveDiscoverOffersCard(List<AceDashboardCard> dashboardCards) {
        boolean weatherCardExists = AceBasicEnumerator.DEFAULT.anySatisfy(dashboardCards,
                dashboardCard -> DashboardCardType.WEATHER_ALERTS_FOR_YOU.equals(dashboardCard.getCardType()));
        return dashboardCards.size() > (weatherCardExists ? 3 : 2);
    }

    public class AceWeatherAlertsCard extends AceBaseDashboardCard {

        AceWeatherAlertsCard(@NonNull String title, @NonNull String subtitle, @NonNull AceExecutableWith<AceBaseActivity> action) {
            super(DashboardCardType.WEATHER_ALERTS_FOR_YOU, title, subtitle, R.drawable.ic_weather, action);
        }
    }

    class AceWeatherAlertsCardAction extends AceAction {

        AceWeatherAlertsCardAction(@NonNull Context context) {
            super(context, R.string.emptyText);
        }

        @Override
        public void executeWith(AceBaseActivity helper) {
            //todo - log events and do action - will be addressed in MOBT-94579
        }
    }

}