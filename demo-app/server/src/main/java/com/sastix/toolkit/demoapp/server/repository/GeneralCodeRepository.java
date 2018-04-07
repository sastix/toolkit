package com.sastix.toolkit.demoapp.server.repository;

import com.sastix.toolkit.demoapp.server.model.GeneralCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralCodeRepository extends CrudRepository<GeneralCode, Long> {

}
