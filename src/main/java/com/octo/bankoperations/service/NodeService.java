package com.octo.bankoperations.service;

import com.octo.bankoperations.dto.NodeInfo;

import java.time.Instant;
import java.util.List;

public interface NodeService {
    NodeInfo whoami();

    List<String> getPeers();

    List<String> myLegalIdentities();

    List<String> myAddresses();

    int platformVersion();

    Instant currentNodeTime();

    List<String> notaryIdentities();

    List<NodeInfo> networkMapSnapshot();

    List<String> registeredFlows();
}
