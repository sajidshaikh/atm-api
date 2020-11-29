package com.navyug.atm.api.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.navyug.atm.api.entity.ATMStatesEntity;



@Repository
public interface ATMStatesRepository extends CrudRepository<ATMStatesEntity, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<ATMStatesEntity>findAll();

}
