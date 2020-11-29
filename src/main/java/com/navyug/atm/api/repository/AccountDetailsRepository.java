package com.navyug.atm.api.repository;

import java.util.Optional;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.navyug.atm.api.entity.AccountDetailsEntity;



@Repository
public interface AccountDetailsRepository extends CrudRepository<AccountDetailsEntity, Long> {
	
	Optional<AccountDetailsEntity>findByEmailIgnoreCaseAndIsActive(String email,Boolean isActive);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<AccountDetailsEntity>findByCustomerIdAndIsActive(Long customerId,Boolean isActive);
}
