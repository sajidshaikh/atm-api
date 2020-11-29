package com.navyug.atm.api.service;

import com.navyug.atm.api.request.ATMRequest;
import com.navyug.atm.api.response.ATMResponse;

public interface IATMService{
	
	ATMResponse deposit(ATMRequest atmRequest) throws Exception;
	ATMResponse withdrawals(ATMRequest atmRequest) throws Exception;
	ATMResponse balanceEnquiry(ATMRequest atmRequest) throws Exception;
	ATMResponse createAccount(ATMRequest atmRequest) throws Exception;

}
