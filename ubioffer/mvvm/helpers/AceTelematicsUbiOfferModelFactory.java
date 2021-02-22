package com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.helpers;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceCustomFactory;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferDetailItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferListItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.model.AceTelematicsUbiOfferModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.ubioffer.mvvm.repository.AceTelematicsUbiOfferRepository;

import java.util.Arrays;
import java.util.List;

/**
 * A class that creates Telematics ubi offer list item and detail item into Telematics ubi model to be used for data binding
 *
 * @author John Sung, Geico
 */
public class AceTelematicsUbiOfferModelFactory implements AceCustomFactory<AceTelematicsUbiOfferModel, AceTelematicsUbiOfferRepository> {
    @Override
    public AceTelematicsUbiOfferModel create(@NonNull AceTelematicsUbiOfferRepository options) {
        AceTelematicsUbiOfferModel ubiOfferModel = new AceTelematicsUbiOfferModel();
        ubiOfferModel.getUbiOfferListItems().add(createUbiOfferListItem(options.getString(R.string.getRewardForSafeDriverTitle), options.getString(R.string.getRewardForSafeDriverSubtitle), true));
        AceTelematicsUbiOfferListItem bottomListItem = createUbiOfferListItem(options.getString(R.string.hereIsHowItWorks), "", false);
        bottomListItem.setUbiOfferDetailItems(buildUbiOfferDetailItems(options));
        bottomListItem.setShouldShowEnrollInDriveEasyButton(true);
        ubiOfferModel.getUbiOfferListItems().add(bottomListItem);
        return ubiOfferModel;
    }

    private AceTelematicsUbiOfferListItem createUbiOfferListItem(String title, String subtitle, boolean showTopDriveEasyLogo) {
        AceTelematicsUbiOfferListItem listItem = new AceTelematicsUbiOfferListItem();
        listItem.setTitle(title);
        listItem.setSubtitle(subtitle);
        listItem.setShouldShowTopDriveEasyLogo(showTopDriveEasyLogo);
        return listItem;
    }

    private AceTelematicsUbiOfferDetailItem createUbiOfferDetailItem(String title, String subtitle, @DrawableRes int SectionImageResourceId) {
        AceTelematicsUbiOfferDetailItem offerDetailItem = new AceTelematicsUbiOfferDetailItem();
        offerDetailItem.setTitle(title);
        offerDetailItem.setSubtitle(subtitle);
        offerDetailItem.setSectionImageResourceId(SectionImageResourceId);
        return offerDetailItem;
    }

    private List<AceTelematicsUbiOfferDetailItem> buildUbiOfferDetailItems(AceTelematicsUbiOfferRepository options) {
        return Arrays.asList(createUbiOfferDetailItem(options.getString(R.string.setUpDriveEasyTitle),
                options.getString(R.string.setUpDriveEasySubtitle), R.drawable.ic_checkmark_in_blue_circle),
                createUbiOfferDetailItem(options.getString(R.string.driveAndScoreTitle),
                        options.getString(R.string.driveAndScoreSubtitle), R.drawable.ic_smartphone_in_blue_circle),
                createUbiOfferDetailItem(options.getString(R.string.getYourPersonalizedPremiumTitle),
                        options.getString(R.string.getYourPersonalizedPremiumSubtitle), R.drawable.ic_auto_in_blue_circle));
    }
}
