package com.hectorlopezfernandez.toilet.pebble;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.PropertyResolver;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

/**
 * Pebble function that will retrieve a property from Spring's Environment, used to
 * access Toilet-specific application configuration (e.g. from application.properties).
 * 
 * Property names passed to this function are automatically prefixed with "toilet.", 
 * so any custom entry needs to follow this convention.
 * 
 * @author hector
 */
public class PropertyFunction implements Function {

	private static final String prefix = "toilet.";

	private final List<String> argumentNames = Arrays.asList("name", "defaultValue");
	private final PropertyResolver propertyResolver;

	public PropertyFunction(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}

	@Override
	public List<String> getArgumentNames() {
		return argumentNames;
	}

	@Override
	public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
		Object value = args.get("name");
		if (value == null) {
			throw new IllegalArgumentException("Name parameter cannot be null");
		}
		String propertyName = value.toString();
		if (propertyName.isBlank()) {
			throw new IllegalArgumentException("Name parameter cannot be blank");
		}
		String defaultValue = args.get("defaultValue") == null ? null : args.get("defaultValue").toString();
		return propertyResolver.getProperty(prefix + propertyName, defaultValue);
	}

}
