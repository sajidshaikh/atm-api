package com.navyug.atm.api.entity;

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
@Table(name = "atm_state")
public class ATMStatesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "denomination_id", referencedColumnName = "id")
	private DenominationEntity denominationById;
	
	@Column(name = "denomination_count")
	private Integer denominationCount;
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DenominationEntity getDenominationById() {
		return denominationById;
	}

	public void setDenominationById(DenominationEntity denominationById) {
		this.denominationById = denominationById;
	}

	public Integer getDenominationCount() {
		return denominationCount;
	}

	public void setDenominationCount(Integer denominationCount) {
		this.denominationCount = denominationCount;
	}



	
}