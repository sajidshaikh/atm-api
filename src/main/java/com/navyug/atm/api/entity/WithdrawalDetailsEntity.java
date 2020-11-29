package com.navyug.atm.api.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "withdrawal_details")
public class WithdrawalDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_details_id", referencedColumnName = "customer_id")
	private AccountDetailsEntity accountDetailsById;
	
	
	@Column(name = "withdrawal_date")
	private LocalDateTime withdrawalDate;
	
	@Column(name = "amount")
	private Double amount;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "atm_state_id", referencedColumnName = "id")
	private ATMStatesEntity atmStateById;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountDetailsEntity getAccountDetailsById() {
		return accountDetailsById;
	}

	public void setAccountDetailsById(AccountDetailsEntity accountDetailsById) {
		this.accountDetailsById = accountDetailsById;
	}



	public LocalDateTime getWithdrawalDate() {
		return withdrawalDate;
	}

	public void setWithdrawalDate(LocalDateTime withdrawalDate) {
		this.withdrawalDate = withdrawalDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public ATMStatesEntity getAtmStateById() {
		return atmStateById;
	}

	public void setAtmStateById(ATMStatesEntity atmStateById) {
		this.atmStateById = atmStateById;
	}
	

}