package com.hectorlopezfernandez.blog;

import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

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

import com.hectorlopezfernandez.blog.auth.Principal;
import com.hectorlopezfernandez.blog.metadata.Language;
import com.hectorlopezfernandez.blog.metadata.MetadataService;

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
				Principal p = (Principal) auth.getPrincipal();
				Locale l = p.getLanguage().toLocale();
				logger.debug("Found authenticated user with locale '{}'", l);
				setLocale(request, l);
				return l;			
			}
		}
		// we'll need this
		List<Language> supportedLanguages = metadataService.findAllLanguages();
		// no authenticated user, resort to Accept-Language header
		Enumeration<Locale> browserLocales = request.getLocales();
		while (browserLocales.hasMoreElements()) {
			Locale browserLocale = browserLocales.nextElement();
			if (isLocaleSupported(supportedLanguages, browserLocale)) {
				logger.debug("Found app language matching Accept-Language header '{}'", browserLocale);
				setLocale(request, browserLocale);
				return browserLocale;
			} else {
				logger.debug("Passed on Accept-Language header '{}' beacuse no app language matched", browserLocale);
			}
		}
		// no match found, return the default
		logger.debug("No language match, resorting to app default");
		Locale defaultLocale = metadataService.getDefaultLanguage().toLocale();
		setLocale(request, defaultLocale);
		return defaultLocale;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale l) {
		logger.debug("setLocale()");
		setLocale(request, l);
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
		setLocale(request, lc.getLocale());
	}

	/*
	 * Utility methods
	 */

	private void setLocale(HttpServletRequest request, Locale l) {
		assert request != null && l != null;
		request.setAttribute(STORED_LOCALE_KEY, l);
	}

	private boolean isLocaleSupported(List<Language> supportedLanguages, Locale locale) {
		if (supportedLanguages == null || supportedLanguages.size() == 0) return false;
		for (Language lang : supportedLanguages) {
			if (lang.toLocale().equals(locale)) return true;
		}
		return false;
	}

}
