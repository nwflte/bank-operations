package com.octo.bankoperations.web;


import org.springframework.web.bind.annotation.RestController;

@RestController("api/node")
public class NodeController {
/*
    @Autowired
    private NodeService nodeService;

    @GetMapping(value = "/me")
    public ImmutableMap<String, CordaX500Name> whoami() {
        return nodeService.whoiam();
    }

    *//**
     * Returns all parties registered with the network map service. These names can be used to look up identities using
     * the identity service.
     *//*
    @GetMapping(value = "peers", produces = APPLICATION_JSON_VALUE)
    public Map<String, List<CordaX500Name>> getPeers() {
        return nodeService.getPeers();
    }*/
}
