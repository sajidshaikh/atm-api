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
@Table(name = "deposit_details")
public class DepositDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_details_id", referencedColumnName = "customer_id")
	private AccountDetailsEntity accountDetailsById;
	
	
	@Column(name = "deposit_date")
	private LocalDateTime depositDate;
	
	@Column(name = "amount")
	private Double amount;

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

	public LocalDateTime getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(LocalDateTime depositDate) {
		this.depositDate = depositDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	

}