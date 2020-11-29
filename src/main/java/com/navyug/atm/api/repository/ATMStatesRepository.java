package com.navyug.atm.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.navyug.atm.api.entity.ATMStatesEntity;



@Repository
public interface ATMStatesRepository extends CrudRepository<ATMStatesEntity, Long> {

}
