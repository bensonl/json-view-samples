package com.bensonl.samples.jsonview.mappers;

import com.bensonl.samples.jsonview.Person;
import com.bensonl.samples.jsonview.entities.PersonEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by bensonliu on 4/6/17.
 */
@Mapper
public interface PersonMapper {

	@Mappings({
			@Mapping(target = "father", expression = "java( mapWithoutRelationship(entity.getFather()) )"),
			@Mapping(target = "mother", expression = "java( mapWithoutRelationship(entity.getMother()) )")
	})
	Person entityToDto(PersonEntity entity);

	default Person mapWithoutRelationship(PersonEntity entity) {
		if (entity == null) {
			return null;
		}

		Person person = new Person();

		person.setId(entity.getId());
		person.setFirst(entity.getFirst());
		person.setLast(entity.getLast());
		person.setGender(entity.getGender());

		return person;
	}

	default Set<Person> mapSiblings(Set<PersonEntity> siblings) {

		if (! CollectionUtils.isEmpty(siblings)) {
			return siblings.stream()
			               .map(this::mapWithoutRelationship)
			               .collect(Collectors.toSet());
		}

		return null;
	}

}
