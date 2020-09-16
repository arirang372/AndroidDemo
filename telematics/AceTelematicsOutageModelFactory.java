package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers;

import com.geico.mobile.R;
import com.geico.mobile.android.ace.coreframework.patterns.AceFactory;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageHeaderItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageListItem;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSection;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageSectionItem;

import java.util.Arrays;
import java.util.List;

import static com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageDefinitions.AceTelematicsSectionItemNames.FIND_GAS;
import static com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageDefinitions.AceTelematicsSectionItemNames.GEICO_EXPLORE;
import static com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageDefinitions.AceTelematicsSectionItemNames.GET_A_QUOTE;
import static com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageDefinitions.AceTelematicsSectionItemNames.ROADSIDE_ASSISTANCE;
import static com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageDefinitions.AceTelematicsSectionItemNames.SAVED_ID_CARDS;

/**
 * A class that creates and builds telematics outage model needed for view
 *
 * @author John Sung, Geico
 */
public class AceTelematicsOutageModelFactory implements AceFactory<AceTelematicsOutageModel> {

    private AceTelematicsOutageSectionItem createTelematicsOutageSectionItemWith(String sectionName, int sectionItemImageResourceId) {
        return new AceTelematicsOutageSectionItem(sectionName, sectionItemImageResourceId);
    }

    private AceTelematicsOutageSection createTelematicsOutageSectionWith(String title, List<AceTelematicsOutageSectionItem> sectionItems) {
        AceTelematicsOutageSection section = new AceTelematicsOutageSection(title);
        section.getSectionItems().addAll(sectionItems);
        return section;
    }

    @Override
    public AceTelematicsOutageModel create() {
        AceTelematicsOutageModel model = new AceTelematicsOutageModel();
        model.getOutageListItems().add(new AceTelematicsOutageHeaderItem(R.drawable.outage_page_header));
        model.getOutageListItems().add(new AceTelematicsOutageListItem
                (createTelematicsOutageSectionWith("Features",
                        Arrays.asList(createTelematicsOutageSectionItemWith(SAVED_ID_CARDS, R.drawable.quick_actions_id_card_icon),
                                createTelematicsOutageSectionItemWith(ROADSIDE_ASSISTANCE, R.drawable.ic_roadside_service),
                                createTelematicsOutageSectionItemWith(GET_A_QUOTE, R.drawable.ic_outage_get_a_quote)
                        ))));
        model.getOutageListItems().add(new AceTelematicsOutageListItem(
                createTelematicsOutageSectionWith("Extras",
                        Arrays.asList(createTelematicsOutageSectionItemWith(FIND_GAS, R.drawable.ic_discover_find_gas),
                                createTelematicsOutageSectionItemWith(GEICO_EXPLORE, R.drawable.ic_discover_explore_ar)))));
        return model;
    }
}
