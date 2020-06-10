package com.octo.bankoperations.web;

import com.octo.bankoperations.dto.NodeInfo;
import com.octo.bankoperations.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/node")
public class NodeController {

    @Autowired
    private NodeService nodeService;

    @GetMapping("/me")
    public NodeInfo whoami() {
        return nodeService.whoami();
    }
    
    @GetMapping("/peers")
    public List<String> getPeers() {
        return nodeService.getPeers();
    }

    @GetMapping("/legalIds")
    public List<String> myLegalIdentities() {
        return nodeService.myLegalIdentities();
    }

    @GetMapping("/addresses")
    public List<String> myAddresses() {
        return nodeService.myAddresses();
    }

    @GetMapping("/platform-version")
    public int platformVersion() {
        return nodeService.platformVersion();
    }

    @GetMapping("node-time")
    public Instant currentNodeTime() {
        return nodeService.currentNodeTime();
    }

    @GetMapping("notaries")
    public List<String> notaryIdentities() {
        return nodeService.notaryIdentities();
    }

    @GetMapping("network-map")
    public List<NodeInfo> networkMapSnapshot() {
        return nodeService.networkMapSnapshot();
    }

    @GetMapping("registered-flows")
    public List<String> registeredFlows() {
        return nodeService.registeredFlows();
    }
}