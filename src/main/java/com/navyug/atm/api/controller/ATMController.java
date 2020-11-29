package com.navyug.atm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.navyug.atm.api.request.ATMRequest;
import com.navyug.atm.api.response.ATMResponse;
import com.navyug.atm.api.service.IATMService;


@RestController
@RequestMapping("/atm/api")
public class ATMController {
	
	@Autowired
	private IATMService atmService;
	
	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public ATMResponse createAccount(@RequestBody ATMRequest atmRequest)throws Exception {
        return atmService.createAccount(atmRequest);
    }
	
	@RequestMapping(value = "/deposit", method = RequestMethod.POST)
    public ATMResponse deposit(@RequestBody ATMRequest atmRequest)throws Exception {
        return atmService.deposit(atmRequest);
    }
	
	@RequestMapping(value = "/withdrawals", method = RequestMethod.POST)
    public ATMResponse withdrawals(@RequestBody ATMRequest atmRequest)throws Exception {
        return atmService.withdrawals(atmRequest);
    }
	
	@RequestMapping(value = "/balanceEnquiry", method = RequestMethod.GET)
    public ATMResponse balanceEnquiry(ATMRequest atmRequest)throws Exception {
        return atmService.balanceEnquiry(atmRequest);
    }

}
