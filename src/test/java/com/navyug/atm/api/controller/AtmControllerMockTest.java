package com.navyug.atm.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import com.navyug.atm.api.request.ATMRequest;
import com.navyug.atm.api.response.ATMResponse;
import com.navyug.atm.api.service.IATMService;


@SpringBootTest
@AutoConfigureMockMvc
public class AtmControllerMockTest {
	
	@Mock
    private ATMResponse response;
	
	@SpyBean
	private IATMService atmService;
	
	
	@Test
	public void createAccountMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setAccountType("Savings Account");
		atmRequest.setAmount(50000d);
		atmRequest.setCustomerName("Mock Test");
		atmRequest.setEmail("sajid.shaikh@mock.com");
		atmRequest.setPin("123456");
		when(atmService.createAccount(atmRequest)).thenCallRealMethod();
		response=atmService.createAccount(atmRequest);
		assertEquals(201, response.getStatusCode());
	}

	@Test
	public void depositMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		Map<Integer,Integer>denomination=new HashMap<>();
		denomination.put(100, 50);
		denomination.put(500, 80);
		denomination.put(2000, 25);
		atmRequest.setAmount(50000d);
		atmRequest.setDenomination(denomination);
		atmRequest.setCustomerId(1L);
		when(atmService.deposit(atmRequest)).thenCallRealMethod();
		response=atmService.deposit(atmRequest);
		assertEquals(201, response.getStatusCode());
	}

	@Test
	public void withdrawalsMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(5000d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(200, response.getStatusCode());
	}
	
	@Test
	public void balanceEnquiryMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		when(atmService.balanceEnquiry(atmRequest)).thenCallRealMethod();
		response=atmService.balanceEnquiry(atmRequest);
		assertEquals(200, response.getStatusCode());
	}


}
