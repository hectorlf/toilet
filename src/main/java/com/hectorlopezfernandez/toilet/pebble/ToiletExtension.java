package com.hectorlopezfernandez.toilet.pebble;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.PropertyResolver;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.extension.Function;

/**
 * Pebble extensions specific to Toilet
 * 
 * @author hector
 */
public class ToiletExtension extends AbstractExtension {

	private final PropertyResolver propertyResolver;

	public ToiletExtension(PropertyResolver propertyResolver) {
		this.propertyResolver = propertyResolver;
	}

	@Override
	public Map<String, Function> getFunctions() {
		Map<String, Function> functions = new HashMap<>(3);
		functions.put("property", new PropertyFunction(propertyResolver));
		functions.put("dateTime", new DateTimeFunction());
		return functions;
	}

	@Override
	public Map<String, Filter> getFilters() {
		Map<String, Filter> filters = new HashMap<>(2);
		filters.put("dateTime", new DateTimeFilter());
		return filters;
	}

}
