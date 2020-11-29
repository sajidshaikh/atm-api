package com.navyug.atm.api.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "denomination")
public class DenominationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "value")
	private Integer value;
	
	@OneToMany(mappedBy = "denominationById")
	private List<ATMStatesEntity> atmStatesEntities;
	

	public List<ATMStatesEntity> getAtmStatesEntities() {
		return atmStatesEntities;
	}

	public void setAtmStatesEntities(List<ATMStatesEntity> atmStatesEntities) {
		this.atmStatesEntities = atmStatesEntities;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	

	
}