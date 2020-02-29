package com.hectorlopezfernandez.blog.backup;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hectorlopezfernandez.blog.metadata.MetadataService;

@Service
public class BackupService {

	@Inject
	private MetadataService metadataService;

	public void initialize() {
		if (metadataService.isAlreadyInitialized()) return;
		
		metadataService.initialize();
	}

	public void setMetadataService(MetadataService metadataService) {
		this.metadataService = metadataService;
	}

}