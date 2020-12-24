package com.hectorlopezfernandez.blog.metadata;

import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/api/i18n")
public class TranslationAdminController {

	private static final Logger logger = LoggerFactory.getLogger(TranslationAdminController.class);

	private final MessageSource messageSource;

	@Inject
	public TranslationAdminController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping
	public ResponseEntity<Translation> listTranslations() {
		logger.debug("Going into .listTranslations()");
		// currently there's no easy way to list all the available messages
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(path = "/{key}")
	public ResponseEntity<Translation> translate(@PathVariable("key") String key, Locale locale) {
		logger.debug("Going into .translate()");
		try {
			String translationResult = messageSource.getMessage(key, null, locale);
			// this result cannot be cached because the actual locale is not considered by the browser's cache
			return ResponseEntity.ok().cacheControl(CacheControl.noStore())
					.body(new Translation(key, translationResult, locale.getLanguage()));
		} catch (NoSuchMessageException nsme) {
			return ResponseEntity.notFound().build();
		}
	}

}
