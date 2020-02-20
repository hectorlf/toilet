package com.hectorlopezfernandez.blog.post;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.hectorlopezfernandez.blog.BaseTest;

public class ArchiveEntryRepositoryTests extends BaseTest {
	
	@Autowired
	private ArchiveEntryRepository entryRepository;

	@Test
	public void testArchiveEntries() {
		assertThat(entryRepository.findAll(), hasSize(2));
	}
	
	@Test
	public void testSetMonth_NegativeMonthPassed_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ArchiveEntry ae = new ArchiveEntry();
			ae.setMonth(-1);
		});
	}
	
	@Test
	public void testSetMonth_InvalidMonthPassed_ExceptionThrown() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			ArchiveEntry ae = new ArchiveEntry();
			ae.setMonth(12);
		});
	}

	@Test
	public void testSave_IndexBreakingObjectSaved_ExceptionThrown() {
		Assertions.assertThrows(DuplicateKeyException.class, () -> {
			ArchiveEntry ae = new ArchiveEntry();
			ae.setMonth(0);
			ae.setPostCount(0);
			ae.setYear(2000);
			entryRepository.save(ae);
		});
	}

	@BeforeEach
	public void setup() {
		ArchiveEntry ae = new ArchiveEntry();
		ae.setMonth(0);
		ae.setPostCount(0);
		ae.setYear(2000);
		entryRepository.save(ae);
		ae = new ArchiveEntry();
		ae.setMonth(2);
		ae.setPostCount(0);
		ae.setYear(2002);
		entryRepository.save(ae);
	}

	@AfterEach
	public void teardown() {
		entryRepository.deleteAll();
	}

}