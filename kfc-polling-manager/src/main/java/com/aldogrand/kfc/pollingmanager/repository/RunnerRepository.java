package com.aldogrand.kfc.pollingmanager.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.aldogrand.kfc.pollingmanager.model.SourceRunner;

public interface RunnerRepository extends CrudRepository<SourceRunner, Long>, JpaSpecificationExecutor<SourceRunner> {

}
