package com.navyug.atm.api.utility;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.navyug.atm.api.entity.ATMStatesEntity;
import com.navyug.atm.api.entity.AccountDetailsEntity;
import com.navyug.atm.api.entity.AccountTypeEntity;
import com.navyug.atm.api.entity.DenominationEntity;
import com.navyug.atm.api.entity.DepositDetailsEntity;
import com.navyug.atm.api.entity.WithdrawalDetailsEntity;
import com.navyug.atm.api.repository.ATMStatesRepository;
import com.navyug.atm.api.repository.AccountDetailsRepository;
import com.navyug.atm.api.repository.AccountTypeRepository;
import com.navyug.atm.api.repository.DenominationRepository;
import com.navyug.atm.api.request.ATMRequest;
import com.navyug.atm.api.request.utility.ATMUtilityRequest;
import com.navyug.atm.api.response.CommonResponse;

public class ATMUtility {
	
	public static CommonResponse generateBalanceEnquiryResponse(ATMUtilityRequest atmUtilityRequest)throws Exception {
		CommonResponse commonResponse=new CommonResponse();
		commonResponse.setCurrentTime(atmUtilityRequest.getLocalDateTime());
		commonResponse.setAvailableBalance("\u20B9 "+atmUtilityRequest.getAccountDetailsEntity().getAmount());
		return commonResponse;
	}

	
	public static AccountDetailsEntity generateCreateAccountRequest(ATMUtilityRequest atmUtilityRequest)throws Exception {
		AccountDetailsEntity accountDetailsEntity=new AccountDetailsEntity();
		ATMRequest atmRequest=atmUtilityRequest.getAtmRequest();
		if(StringUtils.isNoneBlank(atmRequest.getCustomerName())) {
			accountDetailsEntity.setCustomerName(atmRequest.getCustomerName());
		}if(StringUtils.isNoneBlank(atmRequest.getEmail())) {
			accountDetailsEntity.setEmail(atmRequest.getEmail());
		}if(Optional.ofNullable(atmRequest.getAmount()).orElse(0D)!=0D) {
			accountDetailsEntity.setAmount(atmRequest.getAmount());
		}if(StringUtils.isNoneBlank(atmRequest.getAccountType())) {
			Map<String,AccountTypeEntity> accountTypesMap=getAccountTypeEntitiesMap(atmUtilityRequest.getAccountTypeRepository());
			if(accountTypesMap.containsKey(atmRequest.getAccountType())) {
				accountDetailsEntity.setAccountTypeById(accountTypesMap.get(atmRequest.getAccountType()));
			}
		}if(StringUtils.isNoneBlank(atmRequest.getPin())) {
			accountDetailsEntity.setCustomerPin(atmRequest.getPin());
		}
		accountDetailsEntity.setIsActive(Boolean.TRUE);
		accountDetailsEntity.setCreatedDate(LocalDateTime.now());
		return accountDetailsEntity;
	}
	
	private static Map<String,AccountTypeEntity> getAccountTypeEntitiesMap(AccountTypeRepository accountTypeRepository) throws Exception{
		Map<String,AccountTypeEntity> accountTypesMap=new HashMap<String, AccountTypeEntity>();
		List<AccountTypeEntity>accountTypeEntities=(List<AccountTypeEntity>) accountTypeRepository.findAll();
		if(CollectionUtils.isNotEmpty(accountTypeEntities)) {
			accountTypesMap=accountTypeEntities.stream().collect(Collectors.toMap(AccountTypeEntity::getAccountType, accountType -> accountType));
		}
		return accountTypesMap;
	}
	
	public static DepositDetailsEntity generateDepositRequest(ATMUtilityRequest atmUtilityRequest)throws Exception {
		DepositDetailsEntity depositDetailsEntity=new DepositDetailsEntity();
		ATMRequest atmRequest=atmUtilityRequest.getAtmRequest();
		depositDetailsEntity.setAccountDetailsById(atmUtilityRequest.getAccountDetailsEntity());
		if(Optional.ofNullable(atmRequest.getAmount()).orElse(0D)!=0D) {
			depositDetailsEntity.setAmount(atmRequest.getAmount());
		}
		depositDetailsEntity.setDepositDate(LocalDateTime.now());
		return depositDetailsEntity;
	}
	
	public static List<ATMStatesEntity> generateATMStatesEntityRequest(ATMUtilityRequest atmUtilityRequest)throws Exception{
		List<ATMStatesEntity> atmStatesEntities=null;
		ATMRequest atmRequest=atmUtilityRequest.getAtmRequest();
		atmStatesEntities=atmUtilityRequest.getAtmStatesRepository().findAll();
		if(CollectionUtils.isNotEmpty(atmStatesEntities)) {
			atmStatesEntities.forEach(atmStates->{
				atmRequest.getDenomination().forEach((denomination,count)->{
					if(Optional.ofNullable(atmStates.getDenominationById()).isPresent()) {
						if(atmStates.getDenominationById().getValue()==denomination.intValue()) {
							atmStates.setDenominationCount(Math.addExact(atmStates.getDenominationCount(), count));
						}
					}
				});
			});
		}else {
			Map<Integer,DenominationEntity> denominationMap=getDenominationMap(atmUtilityRequest.getDenominationRepository());
			atmStatesEntities=new ArrayList<>();
			for(Map.Entry<Integer, Integer>denoMap:atmRequest.getDenomination().entrySet()) {
				ATMStatesEntity atmStatesEntity=new ATMStatesEntity();
				if(denominationMap.containsKey(denoMap.getKey())) {
					atmStatesEntity.setDenominationById(denominationMap.get(denoMap.getKey()));
					atmStatesEntity.setDenominationCount(denoMap.getValue());
					atmStatesEntities.add(atmStatesEntity);
				}
			}
		}
		return atmStatesEntities;
	}
	public static AccountDetailsEntity updateAccountDetailAfterDeposit(AccountDetailsEntity accountDetailsEntity,Double depositAmount){
		accountDetailsEntity.setAmount(addUsingBigDecimalOperation(accountDetailsEntity.getAmount(), depositAmount));
		return accountDetailsEntity;
	}
	
	private static Map<Integer,DenominationEntity> getDenominationMap(DenominationRepository denominationRepository){
		Map<Integer,DenominationEntity> denominationMap=null;
		List<DenominationEntity>denominationEntities=(List<DenominationEntity>) denominationRepository.findAll();
		if(CollectionUtils.isNotEmpty(denominationEntities)) {
			denominationMap=denominationEntities.stream().collect(Collectors.toMap(DenominationEntity::getValue, denominationEntity -> denominationEntity));
		}
		return denominationMap;
	}
	
	public static WithdrawalDetailsEntity generateWithdrawalsRequest(ATMUtilityRequest atmUtilityRequest)throws Exception {
		WithdrawalDetailsEntity withdrawalDetailsEntity = new WithdrawalDetailsEntity();
		ATMRequest atmRequest = atmUtilityRequest.getAtmRequest();
		withdrawalDetailsEntity.setAccountDetailsById(atmUtilityRequest.getAccountDetailsEntity());
		if (Optional.ofNullable(atmRequest.getAmount()).orElse(0D) != 0D) {
			withdrawalDetailsEntity.setAmount(atmRequest.getAmount());
		}
		withdrawalDetailsEntity.setWithdrawalDate(LocalDateTime.now());
		withdrawalDetailsEntity.setAtmStateById(atmUtilityRequest.getAtmStatesEntity());
		return withdrawalDetailsEntity;
	}
	
	
	public static Boolean isValidAmount(Double amount)throws Exception {
		Boolean isValid=Boolean.FALSE;
		if(amount%100==0) {
			isValid=Boolean.TRUE;
		}
		return isValid;
	}
	
	public static Boolean isSufficientAmountAvailableInAtm(ATMStatesRepository atmStatesRepository,Double amount)throws Exception {
		Boolean isValid=Boolean.FALSE;
		List<ATMStatesEntity> atmStatesEntities=(List<ATMStatesEntity>) atmStatesRepository.findAll();
		if(CollectionUtils.isNotEmpty(atmStatesEntities)) {
			Long totalAmount=0L;
			for(ATMStatesEntity atmStates: atmStatesEntities) {
				totalAmount+=atmStates.getDenominationById().getValue()*atmStates.getDenominationCount();
			}if(amount.longValue()<=totalAmount) {
				isValid=Boolean.TRUE;
			}
		}
		return isValid;
	}
	
	public static Boolean isSufficientAmountAvailableInUserAccount(AccountDetailsEntity accountDetailsEntity,Double amount)throws Exception {
		Boolean isValid=Boolean.FALSE;
		if(amount<=accountDetailsEntity.getAmount()) {
			isValid=Boolean.TRUE;
		}
		return isValid;
	}
	public static void updateAccountDetailsAmount(AccountDetailsRepository accountDetailsRepository,Long customerId,Double withdrawalAmount)throws Exception {
		Optional<AccountDetailsEntity>accountDetailEntityOptional=accountDetailsRepository.findById(customerId);
		if(accountDetailEntityOptional.isPresent()) {
			accountDetailEntityOptional.get().setAmount(subtractUsingBigDecimalOperation(accountDetailEntityOptional.get().getAmount(), withdrawalAmount));
			accountDetailsRepository.save(accountDetailEntityOptional.get());
		}
	}
	
	public static CommonResponse generateWithdrawalsResponse(AccountDetailsRepository accountDetailsRepository,Long customerId)throws Exception {
		CommonResponse commonResponse=null;
		Optional<AccountDetailsEntity>accountDetailEntityOptional=accountDetailsRepository.findById(customerId);
		if(accountDetailEntityOptional.isPresent()) {
			commonResponse=new CommonResponse();
			commonResponse.setAvailableBalance("\u20B9 "+accountDetailEntityOptional.get().getAmount());
			commonResponse.setCurrentTime(LocalDateTime.now());
		}
		return commonResponse;
	}
	private static Double subtractUsingBigDecimalOperation(Double amount, Double withdrawalAmount) {
		  BigDecimal result = BigDecimal.valueOf(amount).subtract(BigDecimal.valueOf(withdrawalAmount));
		  return result.doubleValue();
	}
	
	private static Double addUsingBigDecimalOperation(Double amount, Double depositAmount) {
		  BigDecimal result = BigDecimal.valueOf(amount).subtract(BigDecimal.valueOf(depositAmount));
		  return result.doubleValue();
	}
	
	public static Map<String,Integer> updateAtmStates(ATMUtilityRequest atmUtilityRequest)throws Exception {
		List<ATMStatesEntity> atmStatesEntities=null;
		Map<String,Integer> countMap=null;
		ATMRequest atmRequest=atmUtilityRequest.getAtmRequest();
		atmStatesEntities=atmUtilityRequest.getAtmStatesRepository().findAll();
		Optional<AccountDetailsEntity>accountDetailEntityOptional=atmUtilityRequest.getAccountDetailsRepository().findById(atmRequest.getCustomerId());
		if(CollectionUtils.isNotEmpty(atmStatesEntities) && accountDetailEntityOptional.isPresent()) {
			countMap=updateDenominationCount(atmStatesEntities, atmRequest.getAmount(), atmUtilityRequest.getAtmStatesRepository());
		}
		return countMap;
	}
	
	private static Map<String,Integer> updateDenominationCount(List<ATMStatesEntity> atmStatesEntities,Double withdrawalsAmount,ATMStatesRepository atmStatesRepository) {
		Map<Integer,ATMStatesEntity> atmStateMap=getATMStatesMap(atmStatesEntities);
		Map<String,Integer> countMap=new HashMap<>();
		Double count=0d;
		if(withdrawalsAmount.intValue()<500) {
			if(atmStateMap.containsKey(100)) {
				count=withdrawalsAmount/100;
				atmStateMap.get(100).setDenominationCount(Math.subtractExact(atmStateMap.get(100).getDenominationCount(),count.intValue()));
				countMap.put("\u20B9 100", count.intValue());
			}
		}else if(withdrawalsAmount.intValue()>=500 && withdrawalsAmount.intValue()<=2000) {
			int hundred=5;
	        int fiveHundred=(withdrawalsAmount.intValue()-500)/500;
	        hundred += ((withdrawalsAmount.intValue()-500)%500)/100;
	        if(hundred>5){
	        	fiveHundred=fiveHundred+1;
	        	hundred=hundred-5;
	        }
	        if(atmStateMap.containsKey(100) && atmStateMap.containsKey(500)) {
				atmStateMap.get(100).setDenominationCount(Math.subtractExact(atmStateMap.get(100).getDenominationCount(),hundred));
				atmStateMap.get(500).setDenominationCount(Math.subtractExact(atmStateMap.get(500).getDenominationCount(),fiveHundred));
				 countMap.put("\u20B9 100", hundred);
			     countMap.put("\u20B9 500", fiveHundred);
			}else if(atmStateMap.containsKey(500) && !atmStateMap.containsKey(100)) {
				count=withdrawalsAmount/500;
				atmStateMap.get(500).setDenominationCount(Math.subtractExact(atmStateMap.get(500).getDenominationCount(),count.intValue()));
			}else if(atmStateMap.containsKey(100) && !atmStateMap.containsKey(500)) {
				count=withdrawalsAmount/100;
				atmStateMap.get(100).setDenominationCount(Math.subtractExact(atmStateMap.get(100).getDenominationCount(),count.intValue()));
			}
		}else if(withdrawalsAmount.intValue()>2000){
			if(withdrawalsAmount.intValue()%2000==0 &&
					atmStateMap.containsKey(2000) ) {
				count=withdrawalsAmount/2000;
				atmStateMap.get(2000).setDenominationCount(Math.subtractExact(atmStateMap.get(2000).getDenominationCount(),count.intValue()));
				 countMap.put("\u20B9 2000", count.intValue());
			}else {
		        int twothousand=(withdrawalsAmount.intValue()-2000)/2000;
		        int fiveHundred=(withdrawalsAmount.intValue()-(2000*twothousand))/500;
		        int hundred = ((withdrawalsAmount.intValue()-((500*fiveHundred)+(2000*twothousand))))/100;
		        if(fiveHundred>4) {
	        		twothousand=twothousand+1;
		        	fiveHundred=fiveHundred-4;
	        	}if(hundred>5){
		        	fiveHundred=fiveHundred+1;
		        	hundred=hundred-5;
		        }if(atmStateMap.containsKey(100) && atmStateMap.containsKey(500)
		        		&& atmStateMap.containsKey(2000)) {
					atmStateMap.get(100).setDenominationCount(Math.subtractExact(atmStateMap.get(100).getDenominationCount(),hundred));
					atmStateMap.get(500).setDenominationCount(Math.subtractExact(atmStateMap.get(500).getDenominationCount(),fiveHundred));
					atmStateMap.get(2000).setDenominationCount(Math.subtractExact(atmStateMap.get(2000).getDenominationCount(),twothousand));
					 countMap.put("\u20B9 100", hundred);
				     countMap.put("\u20B9 500", fiveHundred);
				     countMap.put("\u20B9 2000", twothousand);
				}else if(atmStateMap.containsKey(500) && !atmStateMap.containsKey(100)) {
					count=withdrawalsAmount/500;
					atmStateMap.get(500).setDenominationCount(Math.subtractExact(atmStateMap.get(500).getDenominationCount(),count.intValue()));
				}else if(atmStateMap.containsKey(100) && !atmStateMap.containsKey(500)) {
					count=withdrawalsAmount/100;
					atmStateMap.get(100).setDenominationCount(Math.subtractExact(atmStateMap.get(100).getDenominationCount(),count.intValue()));
				}
			}
			
		}
		if(null != atmStateMap && atmStateMap.size()>0) {
			atmStatesRepository.saveAll(generateMapToList(atmStateMap));
		}
		return countMap;
	}
	
	private static Map<Integer,ATMStatesEntity> getATMStatesMap(List<ATMStatesEntity> atmStatesEntities){
		Map<Integer,ATMStatesEntity> atmStateMap=new HashMap<>();
		for(ATMStatesEntity atmStatesEntity:atmStatesEntities) {
			atmStateMap.put(atmStatesEntity.getDenominationById().getValue(), atmStatesEntity);
		}
		return atmStateMap;
	}
	
	private static List<ATMStatesEntity> generateMapToList(Map<Integer,ATMStatesEntity> atmStateMap){
		List<ATMStatesEntity> atmStatesEntities = atmStateMap.values().stream().collect(Collectors.toList());
		return atmStatesEntities;
	}
	
	
}
