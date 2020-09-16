package com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableList;

import com.geico.mobile.android.ace.geicoappmodel.AceViewBackingModel;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.helpers.AceTelematicsOutageModelFactory;
import com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.model.outage.AceTelematicsOutageModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsDriver;
import com.geico.mobile.android.ace.mitsupport.mitmodel.telematics.AceTelematicsRelatedDriver;

import java.util.List;

/**
 * Models data needed to display on {@link com.geico.mobile.android.ace.geicoapppresentation.telematics.mvvm.view.AceTelematicsActivity}
 * and to use as part of communication with MCIT and vendor SDKs.
 *
 * @author Austin Morgan
 */
public class AceTelematicsModel extends AceViewBackingModel {

    private final AceTelematicsDashboardModel dashboardModel = new AceTelematicsDashboardModel();
    private final ObservableList<AceTelematicsRelatedDriver> relatedDrivers = new ObservableArrayList<>();
    private final AceTelematicsScoreDetailsModel scoreDetailsModel = new AceTelematicsScoreDetailsModel();
    private final ObservableBoolean showLogInForMoreOptions = new ObservableBoolean(false);
    private final AceTelematicsTripDetailsModel tripDetailsModel = new AceTelematicsTripDetailsModel();
    private final AceTelematicsOutageModel outageModel = new AceTelematicsOutageModelFactory().create();
    private AceTelematicsDriver driver = new AceTelematicsDriver();

    @NonNull
    public AceTelematicsDashboardModel getDashboardModel() {
        return dashboardModel;
    }

    @NonNull
    public AceTelematicsOutageModel getOutageModel() {
        return outageModel;
    }

    @NonNull
    public AceTelematicsDriver getDriver() {
        return driver;
    }

    public void setDriver(@NonNull AceTelematicsDriver driver) {
        this.driver = driver;
    }

    @NonNull
    public ObservableList<AceTelematicsRelatedDriver> getRelatedDrivers() {
        return relatedDrivers;
    }

    public void setRelatedDrivers(@NonNull List<AceTelematicsRelatedDriver> relatedDrivers) {
        this.relatedDrivers.clear();
        this.relatedDrivers.addAll(relatedDrivers);
    }

    @NonNull
    public AceTelematicsScoreDetailsModel getScoreDetailsModel() {
        return scoreDetailsModel;
    }

    @NonNull
    public AceTelematicsTripDetailsModel getTripDetailsModel() {
        return tripDetailsModel;
    }

    public void setShouldShowLogInForMoreOptions(boolean showLogInForMoreOptions) {
        this.showLogInForMoreOptions.set(showLogInForMoreOptions);
    }

    @NonNull
    public ObservableBoolean shouldShowLogInForMoreOptions() {
        return showLogInForMoreOptions;
    }
}
