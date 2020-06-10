package com.octo.bankoperations.web;

import com.octo.bankoperations.service.DDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ddrs")
public class DDRController {

    @Autowired
    public DDRService ddrService;

    @GetMapping("balance")
    public Long balance(){
        return ddrService.balance();
    }

    @GetMapping("count")
    public Long count(){
        return ddrService.count();
    }

    @GetMapping("average")
    public Double average(){
        return ddrService.average();
    }
}
