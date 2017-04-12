package com.bensonl.samples.jsonview.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.validator.constraints.NotEmpty;
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
@Slf4j
@Getter
@Setter
@Component
@PropertySource(name = "jsonViewMappingProperties", value = "classpath:json-view-mappings.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties("jsonViewMappings")
public class JsonViewMappingsConfigurationProperty {

	private String requestParameterName;
	private List<JsonViewMapping> mappings = new ArrayList<>();

	public void setRequestParameterName(String paramName) {
		Validate.isTrue(StringUtils.isNotBlank(paramName));
		this.requestParameterName = paramName;
	}

	@Getter
	public static class JsonViewMapping {

		private String name;
		private String className;

		public void setName(String name) {
			Validate.isTrue(StringUtils.isNotBlank(name));
			this.name = name;
		}

		public void setClassName(String className) {
			Validate.isTrue(StringUtils.isNotBlank(className));
			this.className = className;
		}

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
