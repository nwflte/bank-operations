package com.octo.bankoperations.dto;

import java.util.List;

public class NodeInfo {
    private List<String> addresses;
    private List<String> legalIdentitiesAndCerts;
    private int platformVersion;
    private String serial;

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<String> getLegalIdentitiesAndCerts() {
        return legalIdentitiesAndCerts;
    }

    public void setLegalIdentitiesAndCerts(List<String> legalIdentitiesAndCerts) {
        this.legalIdentitiesAndCerts = legalIdentitiesAndCerts;
    }

    public int getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(int platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
