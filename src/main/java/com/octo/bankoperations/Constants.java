package com.octo.bankoperations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "corda")
public class Constants {

    private String nodeUrl;
    private Currency currency;
    private String centralBankNodeName;
    private List<String> services;
    private String bankCode;

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCentralBankNodeName() {
        return centralBankNodeName;
    }

    public void setCentralBankNodeName(String centralBankNodeName) {
        this.centralBankNodeName = centralBankNodeName;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "CONSTANTS{" +
                "nodeUrl='" + nodeUrl + '\'' +
                ", currency=" + currency +
                ", centralBankNodeName='" + centralBankNodeName + '\'' +
                ", services=" + services +
                ", bankCode='" + bankCode + '\'' +
                '}';
    }
}
