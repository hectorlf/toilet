package com.hectorlopezfernandez.blog.post;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetMonth_NegativeMonthPassed_ExceptionThrown() {
		ArchiveEntry ae = new ArchiveEntry();
		ae.setMonth(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSetMonth_InvalidMonthPassed_ExceptionThrown() {
		ArchiveEntry ae = new ArchiveEntry();
		ae.setMonth(12);
	}

	@Test(expected = DuplicateKeyException.class)
	public void testSave_IndexBreakingObjectSaved_ExceptionThrown() {
		ArchiveEntry ae = new ArchiveEntry();
		ae.setMonth(0);
		ae.setPostCount(0);
		ae.setYear(2000);
		entryRepository.save(ae);
	}

	@Before
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

	@After
	public void teardown() {
		entryRepository.deleteAll();
	}

}