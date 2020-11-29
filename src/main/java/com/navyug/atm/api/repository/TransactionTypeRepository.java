package com.navyug.atm.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.navyug.atm.api.entity.TransactionTypeEntity;



@Repository
public interface TransactionTypeRepository extends CrudRepository<TransactionTypeEntity, Integer> {

}
