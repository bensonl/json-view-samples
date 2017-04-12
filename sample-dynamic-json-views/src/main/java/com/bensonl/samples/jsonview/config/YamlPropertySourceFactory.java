package com.bensonl.samples.jsonview.config;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * Created by bensonliu on 4/12/17.
 */
public class YamlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
		try {
			YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
			PropertySource<?> applicationYamlPropertySource =
					loader.load("json-view-mappings.yml", resource.getResource(), null);
			return applicationYamlPropertySource;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}
}
