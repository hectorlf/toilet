package com.hectorlopezfernandez.pebble.resourcer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;

import com.mitchellbosecke.pebble.extension.AbstractExtension;
import com.mitchellbosecke.pebble.extension.Function;

public class ResourcerExtension extends AbstractExtension {

	private final Environment environment;

	public ResourcerExtension(Environment environment) {
		this.environment = environment;
	}

    @Override
    public Map<String, Function> getFunctions() {
    	Map<String, Function> functions = new HashMap<>(2);
    	functions.put("resource", new ResourceFunction(environment));
        return functions;
    }

}