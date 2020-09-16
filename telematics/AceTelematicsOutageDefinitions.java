package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers;

import androidx.annotation.IntDef;
import androidx.annotation.StringDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Definitions needed for Telematics outage page
 *
 * @author John Sung, Geico
 */
public interface AceTelematicsOutageDefinitions {

    @IntDef(value = {AceTelematicsOutageListItemRow.OUTAGE_EXTRA_SECTION, AceTelematicsOutageListItemRow.OUTAGE_FEATURE_SECTION,
            AceTelematicsOutageListItemRow.OUTAGE_HEADER})
    @Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface AceTelematicsOutageListItemRow {
        int OUTAGE_EXTRA_SECTION = 2;
        int OUTAGE_FEATURE_SECTION = 1;
        int OUTAGE_HEADER = 0;
    }

    @StringDef(value = {AceTelematicsSectionItemNames.FIND_GAS, AceTelematicsSectionItemNames.GEICO_EXPLORE,
            AceTelematicsSectionItemNames.GET_A_QUOTE, AceTelematicsSectionItemNames.ROADSIDE_ASSISTANCE,
            AceTelematicsSectionItemNames.SAVED_ID_CARDS})
    @Target(value = {ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    @interface AceTelematicsSectionItemNames {
        String FIND_GAS = "Find Gas";
        String GEICO_EXPLORE = "GEICO Explore";
        String GET_A_QUOTE = "Get a Quote";
        String ROADSIDE_ASSISTANCE = "Roadside Assistance";
        String SAVED_ID_CARDS = "Saved ID Cards";
    }
}
