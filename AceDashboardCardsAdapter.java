package com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.view;

import android.content.Context;

import androidx.annotation.NonNull;

import com.geico.mobile.BR;
import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceDefaultingMap;
import com.geico.mobile.android.ace.geicoappbusiness.ui.logging.events.AceCrossSellCardEvent;
import com.geico.mobile.android.ace.geicoappmodel.dashboard.AceDashboard.DashboardCardType;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.cards.AceTelematicsNextGenOfferCard;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.model.cards.AceWhatIsNextSectionHeader;
import com.geico.mobile.android.ace.geicoapppresentation.dashboard.mvvm.viewmodel.AceDashboardViewModel;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.AceDashboardCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceCrossSellSectionHeader;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceDiscoverOffersCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceHomeOwnersPromotionalCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceRentersPromotionalCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.model.crosssell.AceUmbrellaPromotionalCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.repository.crosssell.AceCrossSellCardFactory.AceWeatherAlertsCard;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceBaseActivity;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceDataBoundViewHolder;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.view.AceMultiTypeDataBoundAdapter;
import com.geico.mobile.android.ace.geicoapppresentation.framework.mvvm.viewmodel.AceCrossSellCardsCallback;
import com.geico.mobile.android.ace.geicoapppresentation.getaquote.AceGetAQuoteActivity;
import com.geico.mobile.android.ace.geicoapppresentation.weather.mvvm.view.AceWeatherLearnMoreActivity;

import java.util.List;
import java.util.Map;

/**
 * Concrete implementation of the multiple data bound adapter that builds dashboard cards.
 *
 * @author Gopal Palanisamy
 */
public class AceDashboardCardsAdapter extends AceMultiTypeDataBoundAdapter<AceDashboardCard> implements AceDashboardCardsCallback {

    public AceDashboardCardsAdapter(@NonNull List<AceDashboardCard> cards) {
        super(cards);
    }

    @Override
    protected void bindItem(AceDataBoundViewHolder holder, int position) {
        super.bindItem(holder, position);
        holder.binding.setVariable(BR.callback, this);
    }

    @Override
    protected Map<Class<? extends AceDashboardCard>, Integer> createModelToLayoutIdMap() {
        Map<Class<? extends AceDashboardCard>, Integer> map = AceDefaultingMap.withDefault(R.layout.dashboard_what_is_next_card);
        map.put(AceCrossSellSectionHeader.class, R.layout.dashboard_header_card);
        map.put(AceTelematicsNextGenOfferCard.class, R.layout.dashboard_cross_sell_card);
        map.put(AceWeatherAlertsCard.class, R.layout.dashboard_cross_sell_card);
        map.put(AceDiscoverOffersCard.class, R.layout.dashboard_cross_sell_card);
        map.put(AceHomeOwnersPromotionalCard.class, R.layout.dashboard_cross_sell_card);
        map.put(AceRentersPromotionalCard.class, R.layout.dashboard_cross_sell_card);
        map.put(AceUmbrellaPromotionalCard.class, R.layout.dashboard_cross_sell_card);
        map.put(AceWhatIsNextSectionHeader.class, R.layout.dashboard_header_card);
        return map;
    }

    @Override
    protected int getBindingVariableId() {
        return BR.model;
    }

    private AceCrossSellCardsCallback getCrossSellCallback(AceBaseActivity activity) {
        return (AceCrossSellCardsCallback) activity.getViewModel();
    }

    private AceDashboardViewModel getViewModel(AceBaseActivity activity) {
        return (AceDashboardViewModel) activity.getViewModel();
    }

    private void handleSeeOfferSelectionAction(AceDashboardCard card, AceBaseActivity activity) {
        switch (card.getCardType()) {
            case DashboardCardType.WEATHER_ALERTS_FOR_YOU:
                getCrossSellCallback(activity).updateWeatherFlowForWeatherCardSelected(true);
                getCrossSellCallback(activity).startActivity(activity, AceWeatherLearnMoreActivity.class);
                break;
            case DashboardCardType.TELEMATICS_NEXT_GEN_OFFER:
                getCrossSellCallback(activity).navigateToTelematicsNextGenOffer();
                break;
            default:
                getCrossSellCallback(activity).navigateToCrossSell(activity, card);
                break;
        }
    }

    @Override
    public void onMoreOffersSelected(@NonNull AceDashboardCard card, @NonNull Context context) {
        AceBaseActivity activity = (AceBaseActivity) context;
        getCrossSellCallback(activity).considerUpdatingPolicyChangesTransactionDate();
        getCrossSellCallback(activity).logCrossSellOfferSelected(card.getCardType(), AceCrossSellCardEvent.MORE_OFFERS);
        getCrossSellCallback(activity).startActivity(context, AceGetAQuoteActivity.class);
    }

    @Override
    public void onSeeOfferSelected(@NonNull AceDashboardCard card, @NonNull Context context) {
        AceBaseActivity activity = (AceBaseActivity) context;
        getCrossSellCallback(activity).considerUpdatingPolicyChangesTransactionDate();
        updateSelectedCrossSellCardToFlow(activity, card);
        getCrossSellCallback(activity).logCrossSellOfferSelected(card.getCardType(), AceCrossSellCardEvent.SEE_OFFER);
        if (DashboardCardType.WEATHER_ALERTS_FOR_YOU.equals(card.getCardType())) {
            getCrossSellCallback(activity).updateWeatherFlowForWeatherCardSelected(true);
            getCrossSellCallback(activity).startActivity(context, AceWeatherLearnMoreActivity.class);
            return;
        }
        getCrossSellCallback(activity).navigateToCrossSell(activity, card);
        handleSeeOfferSelectionAction(card, activity);
    }

    @Override
    public void onWhatIsNextCardClick(@NonNull AceDashboardCard card, @NonNull Context context) {
        AceBaseActivity activity = (AceBaseActivity) context;
        updateWhatIsNextCardDetails(activity, card);
        getViewModel(activity).logWhatIsNextCardSelected(card.getCardType());
        getViewModel(activity).trackWhatIsNextCardAction(card.getCardType());
        card.getAction().executeWith(activity);
    }

    private void updateSelectedCrossSellCardToFlow(AceBaseActivity activity, AceDashboardCard card) {
        getCrossSellCallback(activity).updateSelectedCrossSellCardToFlow(card);
    }

    private void updateWhatIsNextCardDetails(AceBaseActivity activity, AceDashboardCard card) {
        getViewModel(activity).updateSelectedWhatIsNextCardDetails(card);
    }
}