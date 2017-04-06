package com.bensonl.samples.jsonview.repositories;

import com.bensonl.samples.jsonview.entities.PersonEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by bensonliu on 4/6/17.
 */
public interface PersonEntityRepository extends CrudRepository<PersonEntity, Long> {
}
