package com.bensonl.samples.jsonview.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

/**
 * Created by bensonliu on 4/12/17.
 */
@Slf4j
public class YamlPropertySourceFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
		try {
			YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
			return loader.load(name, resource.getResource(), null);
		} catch (IOException ioe) {
			log.error("Failed to load property source for resource ", ioe);
			throw new RuntimeException(ioe);
		}
	}
}
