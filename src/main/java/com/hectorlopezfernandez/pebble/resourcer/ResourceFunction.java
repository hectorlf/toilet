package com.hectorlopezfernandez.pebble.resourcer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.env.Environment;

import com.mitchellbosecke.pebble.extension.Function;
import com.mitchellbosecke.pebble.spring.PebbleView;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class ResourceFunction implements Function {

	private static final Pattern absoluteUrl = Pattern.compile("^https?://");

    private final List<String> argumentNames = new ArrayList<>();
    private final Environment environment;

    public ResourceFunction(Environment environment) {
    	this.environment = environment;
        argumentNames.add("url");
        argumentNames.add("prependContext");
    }

    @Override
    public List<String> getArgumentNames() {
        return argumentNames;
    }

	@Override
	public Object execute(Map<String, Object> args, PebbleTemplate self, EvaluationContext context, int lineNumber) {
    	// process resource url
    	Object value = args.get("url");
		if (value == null) {
			throw new IllegalArgumentException("Url parameter cannot be null");
		}
		String url = value.toString();
		if (absoluteUrl.matcher(url).matches()) {
			return url;
		}

		// process resources base location 
        String base = environment.getProperty("resourcer.location", "");
        if (!base.isEmpty()) {
        	return base + url;
        }
        
        // process prependContext
        boolean prepend = true;
    	Object prependContext = args.get("prependContext");
    	if (prependContext instanceof Boolean) prepend = ((Boolean) prependContext).booleanValue();
    	else if (prependContext instanceof String) prepend = Boolean.parseBoolean(prependContext.toString());
    	if (!prepend) {
    		return url;
    	}

		// context path
		HttpServletRequest request = (HttpServletRequest)context.getVariable(PebbleView.REQUEST_VARIABLE_NAME);
		if (request == null) {
			throw new IllegalStateException("Configuration error. No visible ServletRequest instance could be found"
					+ " in the evaluation context. Check if pebble-spring4 is well configured.");
		}
		String contextPath = request.getContextPath();
		if (url.startsWith(contextPath)) {
			return url;
		}

		// build url
		StringBuilder sb = new StringBuilder(contextPath.length() + url.length() + 1);
		sb.append(contextPath);
		if (!url.startsWith("/")) sb.append('/');
		sb.append(url);
    	return sb.toString();
    }

}