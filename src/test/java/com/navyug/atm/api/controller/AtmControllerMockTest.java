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
		atmRequest.setAmount(500000000d);
		atmRequest.setCustomerName("Mock Test");
		atmRequest.setEmail("sajid.shaikh@mock.com");
		atmRequest.setPin("123456");
		when(atmService.createAccount(atmRequest)).thenCallRealMethod();
		response=atmService.createAccount(atmRequest);
		assertEquals(201, response.getStatusCode());
	}
	
	@Test
	public void createAccountWithoutEmailMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setAccountType("Savings Account");
		atmRequest.setAmount(50000d);
		atmRequest.setCustomerName("Mock Test");
//		atmRequest.setEmail("sajid.shaikh@mock.com");
		atmRequest.setPin("123456");
		when(atmService.createAccount(atmRequest)).thenCallRealMethod();
		response=atmService.createAccount(atmRequest);
		assertEquals(406, response.getStatusCode());
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
	public void depositMoneyWithMissingParameterMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		Map<Integer,Integer>denomination=new HashMap<>();
		denomination.put(100, 50);
		denomination.put(500, 80);
		denomination.put(2000, 25);
		atmRequest.setAmount(50000d);
		atmRequest.setDenomination(denomination);
//		atmRequest.setCustomerId(1L);
		when(atmService.deposit(atmRequest)).thenCallRealMethod();
		response=atmService.deposit(atmRequest);
		assertEquals(406, response.getStatusCode());
	}
	
	@Test
	public void depositMoneyWithWrongCustomerIdMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		Map<Integer,Integer>denomination=new HashMap<>();
		denomination.put(100, 50);
		denomination.put(500, 80);
		denomination.put(2000, 25);
		atmRequest.setAmount(50000d);
		atmRequest.setDenomination(denomination);
		atmRequest.setCustomerId(465252L);
		when(atmService.deposit(atmRequest)).thenCallRealMethod();
		response=atmService.deposit(atmRequest);
		assertEquals(404, response.getStatusCode());
	}

	@Test
	public void withdrawalsGreaterThanTenThousandMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(12300d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(406, response.getStatusCode());
	}
	
	@Test
	public void withdrawalsLessThanTenThousandMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(5000d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(200, response.getStatusCode());
	}
	@Test
	public void withdrawalsLessThanTwoThousandMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(1900d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(200, response.getStatusCode());
	}
	
	@Test
	public void withdrawalsAmountWithInsufficientBalanceMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(1915100d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(406, response.getStatusCode());
	}
	
	@Test
	public void withdrawalsAmountWithWrongCustomerIdMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(465L);
		atmRequest.setAmount(1900d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void withdrawalsAmountWithWrongPinMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(1900d);
		atmRequest.setPin("1234sdf");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(406, response.getStatusCode());
	}
	
	@Test
	public void withdrawalsAmountWithMissingParametesMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(1900d);
//		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(406, response.getStatusCode());
	}
	
	@Test
	public void withdrawalsLessThanThousandButNotTheMultiplicationOfHundredMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		atmRequest.setAmount(950d);
		atmRequest.setPin("123456");
		when(atmService.withdrawals(atmRequest)).thenCallRealMethod();
		response=atmService.withdrawals(atmRequest);
		assertEquals(406, response.getStatusCode());
	}
	
	@Test
	public void balanceEnquiryMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(1L);
		when(atmService.balanceEnquiry(atmRequest)).thenCallRealMethod();
		response=atmService.balanceEnquiry(atmRequest);
		assertEquals(200, response.getStatusCode());
	}
	@Test
	public void balanceEnquiryWithWrongCusmoterIdMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		atmRequest.setCustomerId(5L);
		when(atmService.balanceEnquiry(atmRequest)).thenCallRealMethod();
		response=atmService.balanceEnquiry(atmRequest);
		assertEquals(404, response.getStatusCode());
	}
	
	@Test
	public void balanceEnquiryWithoutCusmoterIdMockTest() throws Exception {
		ATMRequest atmRequest=new ATMRequest();
		when(atmService.balanceEnquiry(atmRequest)).thenCallRealMethod();
		response=atmService.balanceEnquiry(atmRequest);
		assertEquals(406, response.getStatusCode());
	}


}
