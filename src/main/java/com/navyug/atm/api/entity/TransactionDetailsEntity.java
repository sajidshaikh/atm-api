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
@Table(name = "transaction_details")
public class TransactionDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_details_id", referencedColumnName = "customer_id")
	private AccountDetailsEntity accountDetailsById;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trasaction_type_id", referencedColumnName = "id")
	private TransactionTypeEntity transactionTypeEntityById;
	
	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;


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





	public TransactionTypeEntity getTransactionTypeEntityById() {
		return transactionTypeEntityById;
	}


	public void setTransactionTypeEntityById(TransactionTypeEntity transactionTypeEntityById) {
		this.transactionTypeEntityById = transactionTypeEntityById;
	}


	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}


	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	
}