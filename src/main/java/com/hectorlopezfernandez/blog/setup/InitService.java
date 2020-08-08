package com.hectorlopezfernandez.blog.setup;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.blog.metadata.MetadataService;
import com.hectorlopezfernandez.blog.security.SecurityService;

@Service
public class InitService {

	private final SecurityService securityService;
	private final MetadataService metadataService;

	@Inject
	public InitService(SecurityService securityService, MetadataService metadataService) {
		this.securityService = securityService;
		this.metadataService = metadataService;
	}

	public void initialize() {
		if (metadataService.isAlreadyInitialized()) return;

		metadataService.initialize();
		securityService.initialize();		
	}

}