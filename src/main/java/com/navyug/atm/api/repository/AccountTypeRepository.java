package com.navyug.atm.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.navyug.atm.api.entity.AccountTypeEntity;



@Repository
public interface AccountTypeRepository extends CrudRepository<AccountTypeEntity, Integer> {

}
