package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Author;

class AuthorTest {

	@Test
	void testFullName() {
		Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
		assertEquals("John Doe", author.fullName());

		Author authorWithMiddleName = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
		authorWithMiddleName.setMiddleName("Michael");
		assertEquals("John Michael Doe", authorWithMiddleName.fullName());
	}

	@Test
	void testIsFromCompany() {
		Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
		assertTrue(author.isFromCompany("Example Corp"));
		assertFalse(author.isFromCompany("Other Corp"));
	}

	@Test
	void testGetInitials() {
		Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
		assertEquals("JD", author.getInitials());

		Author authorWithMiddleName = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
		authorWithMiddleName.setMiddleName("Michael");
		assertEquals("JMD", authorWithMiddleName.getInitials());
	}
}
