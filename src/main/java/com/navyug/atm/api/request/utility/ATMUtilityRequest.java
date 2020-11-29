package com.navyug.atm.api.request.utility;

import java.time.LocalDateTime;
import java.util.Map;

import com.navyug.atm.api.entity.ATMStatesEntity;
import com.navyug.atm.api.entity.AccountDetailsEntity;
import com.navyug.atm.api.repository.ATMStatesRepository;
import com.navyug.atm.api.repository.AccountDetailsRepository;
import com.navyug.atm.api.repository.AccountTypeRepository;
import com.navyug.atm.api.repository.DenominationRepository;
import com.navyug.atm.api.request.ATMRequest;

public class ATMUtilityRequest {

	private AccountDetailsEntity accountDetailsEntity;
	private LocalDateTime localDateTime;
	private ATMRequest atmRequest;
	private AccountTypeRepository accountTypeRepository;
	private ATMStatesRepository atmStatesRepository;
	private DenominationRepository denominationRepository;
	private ATMStatesEntity atmStatesEntity;
	private AccountDetailsRepository accountDetailsRepository;
	private Map<String,Integer> denominationMap;
	/*
	 * private Double withdrawalsAmount;
	 * 
	 * public Double getWithdrawalsAmount() { return withdrawalsAmount; }
	 * 
	 * public void setWithdrawalsAmount(Double withdrawalsAmount) {
	 * this.withdrawalsAmount = withdrawalsAmount; }
	 */
	
	

	public AccountDetailsRepository getAccountDetailsRepository() {
		return accountDetailsRepository;
	}

	public Map<String, Integer> getDenominationMap() {
		return denominationMap;
	}

	public void setDenominationMap(Map<String, Integer> denominationMap) {
		this.denominationMap = denominationMap;
	}

	public void setAccountDetailsRepository(AccountDetailsRepository accountDetailsRepository) {
		this.accountDetailsRepository = accountDetailsRepository;
	}

	public ATMStatesEntity getAtmStatesEntity() {
		return atmStatesEntity;
	}

	public void setAtmStatesEntity(ATMStatesEntity atmStatesEntity) {
		this.atmStatesEntity = atmStatesEntity;
	}

	public DenominationRepository getDenominationRepository() {
		return denominationRepository;
	}

	public void setDenominationRepository(DenominationRepository denominationRepository) {
		this.denominationRepository = denominationRepository;
	}

	public ATMStatesRepository getAtmStatesRepository() {
		return atmStatesRepository;
	}

	public void setAtmStatesRepository(ATMStatesRepository atmStatesRepository) {
		this.atmStatesRepository = atmStatesRepository;
	}

	public AccountTypeRepository getAccountTypeRepository() {
		return accountTypeRepository;
	}

	public void setAccountTypeRepository(AccountTypeRepository accountTypeRepository) {
		this.accountTypeRepository = accountTypeRepository;
	}

	public ATMRequest getAtmRequest() {
		return atmRequest;
	}

	public void setAtmRequest(ATMRequest atmRequest) {
		this.atmRequest = atmRequest;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public AccountDetailsEntity getAccountDetailsEntity() {
		return accountDetailsEntity;
	}

	public void setAccountDetailsEntity(AccountDetailsEntity accountDetailsEntity) {
		this.accountDetailsEntity = accountDetailsEntity;
	}
	
	
	
}
