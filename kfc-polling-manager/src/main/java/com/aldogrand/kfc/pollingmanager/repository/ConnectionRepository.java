package com.aldogrand.kfc.pollingmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.aldogrand.kfc.pollingmanager.model.Connection;

public interface ConnectionRepository extends CrudRepository<Connection, Long>, JpaSpecificationExecutor<Connection> {
    List<Connection> findByConnectorName(String name);
}
