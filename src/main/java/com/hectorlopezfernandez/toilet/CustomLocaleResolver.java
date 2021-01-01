package com.hectorlopezfernandez.toilet;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.SimpleLocaleContext;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.LocaleContextResolver;

import com.hectorlopezfernandez.toilet.metadata.Language;
import com.hectorlopezfernandez.toilet.metadata.MetadataService;
import com.hectorlopezfernandez.toilet.security.User;

public class CustomLocaleResolver implements LocaleContextResolver {

	private static final Logger logger = LoggerFactory.getLogger(CustomLocaleResolver.class);

	private static final String STORED_LOCALE_KEY = "CustomLocaleResolver.STORED_LOCALE";

	private MetadataService metadataService;

	@Inject
	public CustomLocaleResolver(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		logger.debug("resolveLocale()");
		// spring will call this method whenever it feels, so we need to cache the computed locale
		Locale cachedLocale = (Locale)request.getAttribute(STORED_LOCALE_KEY);
		if (cachedLocale != null) {
			logger.debug("Found request-cached locale '{}'", cachedLocale);
			return cachedLocale;
		}
		// not cached! first, resolve authenticated user, if any
		SecurityContext sc = SecurityContextHolder.getContext();
		if (sc != null) {
			Authentication auth = sc.getAuthentication();
			if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.getPrincipal() != null) {
				User p = (User) auth.getPrincipal();
				Locale l = Locale.forLanguageTag(p.getLanguage());
				logger.debug("Found authenticated user with locale '{}'", l);
				storeLocale(request, l);
				return l;			
			}
		}
		// no authenticated user, resort to Accept-Language header
		Set<String> supportedLanguages = metadataService.findSupportedLanguageTags();
		Enumeration<Locale> browserLocales = request.getLocales();
		while (browserLocales.hasMoreElements()) {
			Locale browserLocale = browserLocales.nextElement();
			if (supportedLanguages.contains(browserLocale.toLanguageTag())) {
				logger.debug("Found app language matching Accept-Language header '{}'", browserLocale);
				storeLocale(request, browserLocale);
				return browserLocale;
			} else {
				logger.debug("Passed on Accept-Language header '{}' beacuse no app language matched", browserLocale);
			}
		}
		// no match found, return the default
		logger.debug("No language match, resorting to app default");
		Language defaultLanguage = metadataService.getDefaultLanguage();
		// it's possible that the DB is empty, handle the edge case
		Locale defaultLocale = defaultLanguage == null ? Locale.getDefault() : defaultLanguage.toLocale();
		storeLocale(request, defaultLocale);
		return defaultLocale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale l) {
		logger.debug("setLocale()");
		storeLocale(request, l);
	}

	@Override
	public LocaleContext resolveLocaleContext(HttpServletRequest request) {
		// currently, timezone is not persisted
		Locale locale = resolveLocale(request);
		return new SimpleLocaleContext(locale);
	}

	@Override
	public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext lc) {
		logger.debug("setLocaleContext()");
		// currently, timezone is not persisted
		storeLocale(request, lc.getLocale());
	}

	/*
	 * Utility methods
	 */

	private void storeLocale(HttpServletRequest request, Locale l) {
		assert request != null && l != null;
		request.setAttribute(STORED_LOCALE_KEY, l);
	}

}
