package com.aldogrand.kfc.pollingmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.aldogrand.kfc.pollingmanager.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    List<Account> findByConnectorName(String name);
}
