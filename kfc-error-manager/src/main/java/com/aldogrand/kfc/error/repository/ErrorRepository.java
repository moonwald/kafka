//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.error.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.aldogrand.kfc.error.model.MessagingError;

/**
 * Repository of errors.
 */
@Repository
public interface ErrorRepository extends MongoRepository<MessagingError, Long> {
   
}
