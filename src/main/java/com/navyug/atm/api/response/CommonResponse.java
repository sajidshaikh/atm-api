package com.navyug.atm.api.response;

import java.time.LocalDateTime;
import java.util.Map;

public class CommonResponse {
	private String availableBalance;
	private LocalDateTime currentTime;
	private Map<String,Integer> denominationMap;
	
	
	

	public Map<String, Integer> getDenominationMap() {
		return denominationMap;
	}
	public void setDenominationMap(Map<String, Integer> denominationMap) {
		this.denominationMap = denominationMap;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}
	public LocalDateTime getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(LocalDateTime currentTime) {
		this.currentTime = currentTime;
	}
	
	
	
	

}
