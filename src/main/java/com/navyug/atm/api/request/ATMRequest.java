package com.navyug.atm.api.request;

import java.util.Map;

public class ATMRequest {
	private Long customerId;
	private String customerName;
	private String email;
	private String pin;
	private Double amount;
	private String accountType;
	private Map<Integer,Integer>denomination;
	private Integer atmId;
	
	

	public Integer getAtmId() {
		return atmId;
	}
	public void setAtmId(Integer atmId) {
		this.atmId = atmId;
	}
	public Map<Integer, Integer> getDenomination() {
		return denomination;
	}
	public void setDenomination(Map<Integer, Integer> denomination) {
		this.denomination = denomination;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
