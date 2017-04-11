package com.bensonl.samples.jsonview.configs;

import com.bensonl.samples.jsonview.services.PersonService;
import com.bensonl.samples.jsonview.services.PersonServiceImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by bensonliu on 4/6/17.
 */
@Configuration
@ComponentScan(basePackages = {"com.bensonl.samples.jsonview.mappers"})
@EntityScan(value = "com.bensonl.samples.jsonview.entities")
@EnableJpaRepositories("com.bensonl.samples.jsonview")
@EnableTransactionManagement
public class PersistenceConfig {

	@Bean
	public PersonService personService() {
		return new PersonServiceImpl();
	}

}
