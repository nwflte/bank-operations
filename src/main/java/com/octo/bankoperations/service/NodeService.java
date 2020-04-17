package com.octo.bankoperations.service;


import org.springframework.stereotype.Service;

@Service
public class NodeService {

    /*public ImmutableMap<String, CordaX500Name> whoiam(){
        return ImmutableMap.of("me", myLegalName);
    }

    public Map<String, List<CordaX500Name>> getPeers() {
        List<NodeInfo> nodeInfo = proxy.networkMapSnapshot();
        return ImmutableMap.of("peers", nodeInfo.stream().map(n -> n.getLegalIdentities().get(0).getName()).filter(
                name -> !myLegalName.getOrganisation().contains(name.getOrganisation()) && !SERVICE_NAMES.contains(name.getOrganisation())
        ).collect(Collectors.toList()));
    }

    public Party getPartyFromRIB(String rib){
        return proxy.wellKnownPartyFromX500Name(CordaX500Name.parse("O=BankB,L=New York,C=US"));
    }*/

}
