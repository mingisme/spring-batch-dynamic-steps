package com.example.springbatchdynamicsteps.controller;

import com.example.springbatchdynamicsteps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/reset")
    public String resetAccount(){
        accountService.resetAccounts();
        return "OK";
    }

    @GetMapping("/thresh-hold")
    public String threshHold(){
        accountService.threshHold();
        return "OK";
    }
}
