package com.hectorlopezfernandez.toilet.pebble;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mitchellbosecke.pebble.error.PebbleException;
import com.mitchellbosecke.pebble.extension.Filter;
import com.mitchellbosecke.pebble.template.EvaluationContext;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

public class DateTimeFilter implements Filter {

    private final List<String> argumentNames = new ArrayList<>(4);

    public DateTimeFilter() {
        argumentNames.add("pattern");
        argumentNames.add("locale");
        argumentNames.add("timezone");
        argumentNames.add("style");
    }

    public List<String> getArgumentNames() {
        return argumentNames;
    }

	@Override
	public Object apply(Object input, Map<String, Object> args, PebbleTemplate self, EvaluationContext context,
			int lineNumber) throws PebbleException {
//        if (input == null || !(input instanceof LocalDateTime || inputObject instanceof ReadablePartial)) {
//            return input;
//        }
//        
//        // create formatter
//        DateTimeFormatter formatter;
//        String patternParam = (String) args.get("pattern");
//        String styleParam = (String) args.get("style");
//    	// if pattern is specified, it is preferred to style; but if not specified, style is preferred to default joda pattern
//    	if (patternParam == null && styleParam != null) {
//    		formatter = DateTimeFormat.forStyle(styleParam.toString());
//    	} else {
//    		String pattern = ResolveUtils.resolvePattern(patternParam, values);
//    		if (pattern != null) {
//                formatter = DateTimeFormat.forPattern(pattern);
//            } else {
//                // use a medium date (no time) style by default; same as jstl
//                formatter = DateTimeFormat.mediumDate();
//            }
//    	}
//
//        // locale param
//    	formatter = formatter.withLocale(ResolveUtils.resolveLocale(args.get("locale"), context));
//        
//        // shortcircuit if no timezone can be involved
//        if (inputObject instanceof ReadablePartial) {
//        	return formatter.print((ReadablePartial) inputObject);
//        }
//
//        // timezone param
//        formatter = formatter.withZone(ResolveUtils.resolveTimezone(args.get("timezone"), values));
//
//        return formatter.print((ReadableInstant) inputObject);
		return null;
    }

}
