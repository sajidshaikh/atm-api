package com.navyug.atm.api.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "account_details")
public class AccountDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_id")
	private Long customerId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_pin")
	private String customerPin;

	@Column(name = "email")
	private String email;

	@Column(name = "amount")
	private Double amount=0.0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_type", referencedColumnName = "id")
	private AccountTypeEntity accountTypeById;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "account_created_date")
	private LocalDateTime createdDate;

	@OneToMany(mappedBy = "accountDetailsById")
	private List<DepositDetailsEntity> depositDetailsEntities;
	
	@OneToMany(mappedBy = "accountDetailsById")
	private List<TransactionDetailsEntity> transactionDetailsEntities;
	
	@OneToMany(mappedBy = "accountDetailsById")
	private List<WithdrawalDetailsEntity> withdrawalDetailsEntities;

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



	public String getCustomerPin() {
		return customerPin;
	}

	public void setCustomerPin(String customerPin) {
		this.customerPin = customerPin;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public AccountTypeEntity getAccountTypeById() {
		return accountTypeById;
	}

	public void setAccountTypeById(AccountTypeEntity accountTypeById) {
		this.accountTypeById = accountTypeById;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public List<DepositDetailsEntity> getDepositDetailsEntities() {
		return depositDetailsEntities;
	}

	public void setDepositDetailsEntities(List<DepositDetailsEntity> depositDetailsEntities) {
		this.depositDetailsEntities = depositDetailsEntities;
	}

	public List<TransactionDetailsEntity> getTransactionDetailsEntities() {
		return transactionDetailsEntities;
	}

	public void setTransactionDetailsEntities(List<TransactionDetailsEntity> transactionDetailsEntities) {
		this.transactionDetailsEntities = transactionDetailsEntities;
	}

	public List<WithdrawalDetailsEntity> getWithdrawalDetailsEntities() {
		return withdrawalDetailsEntities;
	}

	public void setWithdrawalDetailsEntities(List<WithdrawalDetailsEntity> withdrawalDetailsEntities) {
		this.withdrawalDetailsEntities = withdrawalDetailsEntities;
	}

	@Override
	public String toString() {
		return "AccountDetailsEntity [customerId=" + customerId + ", customerName=" + customerName + ", customerPin="
				+ customerPin + ", email=" + email + ", amount=" + amount + ", accountTypeById=" + accountTypeById
				+ ", isActive=" + isActive + ", createdDate=" + createdDate + ", depositDetailsEntities="
				+ depositDetailsEntities + ", transactionDetailsEntities=" + transactionDetailsEntities
				+ ", withdrawalDetailsEntities=" + withdrawalDetailsEntities + "]";
	}
	
	
}