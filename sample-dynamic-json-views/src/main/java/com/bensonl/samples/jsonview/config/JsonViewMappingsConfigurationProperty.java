package com.bensonl.samples.jsonview.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by bensonliu on 4/12/17.
 */
@Data
@Slf4j
@Component
@PropertySource(value = "classpath:json-view-mappings.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("jsonViewMappings")
public class JsonViewMappingsConfigurationProperty {
	
	private String requestParameterName;
	private List<JsonViewMapping> mappings = new ArrayList<>();

	@Data
	public static class JsonViewMapping {

		private String name;
		private String className;

		public Class<?> getSerializationViewClass() {

			try {
				return Class.forName(className);
			} catch (Exception e) {
				// TODO: throw exception here
				log.error("Failed to resolve JsonView class for viewName={}, className={}", name, className);
			}

			return null;

		}

	}

	public JsonViewMapping findMapping(String viewName) {

		return Optional.ofNullable(mappings).orElseGet(Collections::emptyList)
		               .stream()
		               .filter(m -> m.getName().equals(viewName))
		               .findFirst()
		               .orElse(null );

	}

}
