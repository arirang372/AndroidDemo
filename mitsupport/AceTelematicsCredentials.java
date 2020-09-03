package com.geico.mobile.android.ace.mitsupport.mitmodel.telematics;

import androidx.annotation.NonNull;

import com.geico.mobile.android.ace.mitsupport.mitmodel.MitBaseModel;
import com.geico.mobile.android.ace.mitsupport.mitmodel.MitKeyValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents data used for authenticating Telematics driver data requests.
 *
 * @author Nick Emerson
 */
@SuppressWarnings("unused")
public class AceTelematicsCredentials extends MitBaseModel {

    private String breadcrumbId = "";
    private List<MitKeyValuePair> details = new ArrayList<>();
    private String digest = "";
    private String geicoDriverId = "";
    private long issued;
    private String policyNumber = "";
    private String salt = "";
    private String version = "";
    private long expiration;

    /**
     * The timestamp when the accessToken expires.
     */
    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @NonNull
    public String getBreadcrumbId() {
        return breadcrumbId;
    }

    public void setBreadcrumbId(@NonNull String breadcrumbId) {
        this.breadcrumbId = breadcrumbId;
    }

    @NonNull
    public List<MitKeyValuePair> getDetails() {
        return details;
    }

    @NonNull
    public String getDigest() {
        return digest;
    }

    public void setDigest(@NonNull String digest) {
        this.digest = digest;
    }

    public String getGeicoDriverId() {
        return geicoDriverId;
    }

    public void setGeicoDriverId(@NonNull String geicoDriverId) {
        this.geicoDriverId = geicoDriverId;
    }

    public long getIssued() {
        return issued;
    }

    public void setIssued(long issued) {
        this.issued = issued;
    }

    @NonNull
    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(@NonNull String policyNumber) {
        this.policyNumber = policyNumber;
    }

    @NonNull
    public String getSalt() {
        return salt;
    }

    public void setSalt(@NonNull String salt) {
        this.salt = salt;
    }

    @NonNull
    public String getVersion() {
        return version;
    }

    public void setVersion(@NonNull String version) {
        this.version = version;
    }
}

