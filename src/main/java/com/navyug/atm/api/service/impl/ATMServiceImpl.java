package com.navyug.atm.api.service.impl;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.LockModeType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navyug.atm.api.contants.HttpResponse;
import com.navyug.atm.api.entity.ATMStatesEntity;
import com.navyug.atm.api.entity.AccountDetailsEntity;
import com.navyug.atm.api.entity.DepositDetailsEntity;
import com.navyug.atm.api.entity.WithdrawalDetailsEntity;
import com.navyug.atm.api.repository.ATMStatesRepository;
import com.navyug.atm.api.repository.AccountDetailsRepository;
import com.navyug.atm.api.repository.AccountTypeRepository;
import com.navyug.atm.api.repository.DenominationRepository;
import com.navyug.atm.api.repository.DepositDetailsRepository;
import com.navyug.atm.api.repository.WithdrawalDetailsRepository;
import com.navyug.atm.api.request.ATMRequest;
import com.navyug.atm.api.request.utility.ATMUtilityRequest;
import com.navyug.atm.api.response.ATMResponse;
import com.navyug.atm.api.response.CommonResponse;
import com.navyug.atm.api.service.IATMService;
import com.navyug.atm.api.utility.ATMUtility;
import com.navyug.atm.api.utility.ResponseUtility;

@Service
public class ATMServiceImpl implements IATMService{
	
	@Autowired
	AccountTypeRepository accountTypeRepository;
	
	@Autowired
	AccountDetailsRepository accountDetailsRepository;
	
	@Autowired
	DepositDetailsRepository depositDetailsRepository;
	
	@Autowired
	ATMStatesRepository atmStatesRepository;
	
	@Autowired
	DenominationRepository denominationRepository;
	
	@Autowired
	WithdrawalDetailsRepository withdrawalDetailsRepository;

	
	@Override
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public ATMResponse deposit(ATMRequest atmRequest) throws Exception {
		ATMResponse response=null;
		if(Optional.ofNullable(atmRequest).isPresent() && 
				Optional.ofNullable(atmRequest.getCustomerId()).orElse(0L)!=0L
				&& Optional.ofNullable(atmRequest.getAmount()).orElse(0D)!=0D) {
			Optional<AccountDetailsEntity>accountDetailsOptional=accountDetailsRepository.findById(atmRequest.getCustomerId());
			if(accountDetailsOptional.isPresent()) {
				ATMUtilityRequest atmUtilityRequest=new ATMUtilityRequest();
				atmUtilityRequest.setAtmRequest(atmRequest);
				atmUtilityRequest.setAccountDetailsEntity(accountDetailsOptional.get());
				DepositDetailsEntity depositDetailsEntity=ATMUtility.generateDepositRequest(atmUtilityRequest);
				depositDetailsRepository.save(depositDetailsEntity);
				atmUtilityRequest.setAtmStatesRepository(atmStatesRepository);
				atmUtilityRequest.setDenominationRepository(denominationRepository);
				List<ATMStatesEntity> atmStatesEntities=ATMUtility.generateATMStatesEntityRequest(atmUtilityRequest);
				atmStatesRepository.saveAll(atmStatesEntities);
				accountDetailsRepository.save(ATMUtility.updateAccountDetailAfterDeposit(accountDetailsOptional.get(),atmRequest.getAmount()));
				response = ResponseUtility.setResponse(null, "\u20B9 "+depositDetailsEntity.getAmount()+" Successfully deposited.!", HttpResponse.SUCCESS_STATUS,HttpResponse.SC_CREATED);
			}else {
				response = ResponseUtility.setResponse(null, "Customer ID not Found.!", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_FOUND);
			}
		}else {
				response = ResponseUtility.setResponse(null, "Failed", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_ACCEPTABLE);
		}
		return response;
	}

	@Override
	@Transactional
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public ATMResponse withdrawals(ATMRequest atmRequest) throws Exception {
		ATMResponse response=null;
		if(Optional.ofNullable(atmRequest).isPresent() && 
				Optional.ofNullable(atmRequest.getCustomerId()).orElse(0L)!=0L
				&& Optional.ofNullable(atmRequest.getAmount()).orElse(0D)!=0D
				&& StringUtils.isNoneBlank(atmRequest.getPin())) {
			if(ATMUtility.isValidAmount(atmRequest.getAmount())
					&& ATMUtility.isSufficientAmountAvailableInAtm(atmStatesRepository, atmRequest.getAmount())) {
				 if(atmRequest.getAmount().intValue()>15000){
					 response = ResponseUtility.setResponse(null, "Cash limit exceeds", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_ACCEPTABLE);
				 }else {
					 Optional<AccountDetailsEntity>accountDetailsOptional=accountDetailsRepository.findById(atmRequest.getCustomerId());
						if(accountDetailsOptional.isPresent()) {
							if(StringUtils.equals(accountDetailsOptional.get().getCustomerPin(), atmRequest.getPin())) {
								if(ATMUtility.isSufficientAmountAvailableInUserAccount(accountDetailsOptional.get(), atmRequest.getAmount())) {
									ATMUtilityRequest atmUtilityRequest=new ATMUtilityRequest();
									atmUtilityRequest.setAtmRequest(atmRequest);
									atmUtilityRequest.setAccountDetailsEntity(accountDetailsOptional.get());
									atmUtilityRequest.setAtmStatesRepository(atmStatesRepository);
									atmUtilityRequest.setAccountDetailsRepository(accountDetailsRepository);
									atmUtilityRequest.setDenominationRepository(denominationRepository);
									WithdrawalDetailsEntity withdrawalDetailsEntity=ATMUtility.generateWithdrawalsRequest(atmUtilityRequest);
									withdrawalDetailsRepository.save(withdrawalDetailsEntity);
									ATMUtility.updateAccountDetailsAmount(accountDetailsRepository, atmRequest.getCustomerId(), atmRequest.getAmount());
									Map<String,Integer> countMap=ATMUtility.updateAtmStates(atmUtilityRequest);
									CommonResponse commonResponse=ATMUtility.generateWithdrawalsResponse(accountDetailsRepository, atmRequest.getCustomerId());
									commonResponse.setDenominationMap(countMap);
									response = ResponseUtility.setResponse(commonResponse,"\u20B9 "+ withdrawalDetailsEntity.getAmount().intValue()+" Successfully withdrawal.!", HttpResponse.SUCCESS_STATUS,HttpResponse.SC_OK);
								}
							}else {
								response = ResponseUtility.setResponse(null, "Please Enter Correct Pin.!", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_ACCEPTABLE);
							}
						}else {
							response = ResponseUtility.setResponse(null, "Customer ID not Found.!", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_FOUND);
						}
				 }
			}
		}else {
				response = ResponseUtility.setResponse(null, "Failed", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_ACCEPTABLE);
		}
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public ATMResponse balanceEnquiry(ATMRequest atmRequest) throws Exception {
		ATMResponse response=null;
		if(Optional.ofNullable(atmRequest.getCustomerId()).orElse(0L) !=0L) {
			ATMUtilityRequest atmUtilityRequest=new ATMUtilityRequest();
			atmUtilityRequest.setLocalDateTime(LocalDateTime.now());
			Optional<AccountDetailsEntity>accountDetailsOptional=accountDetailsRepository.findById(atmRequest.getCustomerId());
			if(accountDetailsOptional.isPresent()) {
				atmUtilityRequest.setAccountDetailsEntity(accountDetailsOptional.get());
				response = ResponseUtility.setResponse(ATMUtility.generateBalanceEnquiryResponse(atmUtilityRequest), "Success", HttpResponse.SUCCESS_STATUS,HttpResponse.SC_OK);
			}else {
				response = ResponseUtility.setResponse(null, "Success", HttpResponse.SUCCESS_STATUS,HttpResponse.SC_NOT_FOUND);
			}
		}else {
				response = ResponseUtility.setResponse(null, "Failed", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_ACCEPTABLE);
		}
		return response;
	}

	@Override
	@Transactional
	public ATMResponse createAccount(ATMRequest atmRequest) throws Exception {
		ATMResponse response=null;
		if(Optional.ofNullable(atmRequest).isPresent() && StringUtils.isNoneBlank(atmRequest.getCustomerName())
				&& StringUtils.isNoneBlank(atmRequest.getEmail())) {
				ATMUtilityRequest atmUtilityRequest=new ATMUtilityRequest();
				atmUtilityRequest.setAtmRequest(atmRequest);
				atmUtilityRequest.setAccountTypeRepository(accountTypeRepository);
				AccountDetailsEntity accountDetailsEntity=ATMUtility.generateCreateAccountRequest(atmUtilityRequest);
				accountDetailsRepository.save(accountDetailsEntity);
				response = ResponseUtility.setResponse(accountDetailsEntity.getCustomerId(), "Success", HttpResponse.SUCCESS_STATUS,HttpResponse.SC_CREATED);
		}else {
				response = ResponseUtility.setResponse(null, "Failed", HttpResponse.FAILURE_STATUS,HttpResponse.SC_NOT_ACCEPTABLE);
		}
		return response;
	}

}
