package com.bensonl.samples.jsonview.entities;

import com.bensonl.samples.jsonview.Gender;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by bensonliu on 4/6/17.
 */
@Data
@Entity
@Table(name = "person")
public class PersonEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Version
	private Integer version;

	private String first;
	private String last;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@ManyToOne
	@JoinColumn(name = "father_id")
	private PersonEntity father;

	@ManyToOne
	@JoinColumn(name = "mother_id")
	private PersonEntity mother;

	@ManyToMany
	@JoinTable(name = "siblings",
	           joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
	           inverseJoinColumns = @JoinColumn(name = "sibling_id", referencedColumnName = "id"))
	private Set<PersonEntity> siblings;


	@Override
	public boolean equals(Object o) {
		
		if (o == null) return false;
		if (!(o instanceof PersonEntity)) return false;
		
		PersonEntity that = (PersonEntity)o;
		
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);

	}

	
	// ~ Public Methods
	public void addSibling(PersonEntity sibling) {
		Validate.notNull(sibling);

		if (siblings == null) {
			this.siblings = new HashSet<>();
		}
		this.siblings.add(sibling);
	}
}
