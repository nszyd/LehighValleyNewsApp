package test;

import model.Article;
import model.Author;
import model.Post;

public class TestHarness {
    public static void main(String[] args) {
        // Instantiate a TestHarness object, which will invoke its constructor
        // and run the tests in the constructor
        new TestHarness();
    }

    public TestHarness() {
        // Manual Tests
        int numFails = 0;

        Author author = new Author("John", "Doe", "john.doe@example.com", "Example Corp");
        Article article = new Article("Test Title", author, "2023-10-12", "http://example.com", "Example Corp", "Test Content");
        Post post = new Post(article, "http://example.com/image.jpg", author, "Example Corp", "2023-10-12", "http://example.com");

        // Test getAuthorFullName()
        if (!post.getAuthorFullName().equals("John Doe")) {
            System.out.println("getAuthorFullName() failed the test");
            numFails++;
        }

        // Test getArticleTitle()
        if (!post.getArticleTitle().equals("Test Title")) {
            System.out.println("getArticleTitle() failed the test");
            numFails++;
        }

        // Test isFromCompany(String)
        if (!post.isFromCompany("Example Corp")) {
            System.out.println("isFromCompany(String) failed the test");
            numFails++;
        }

        // End of Manual Test
        if (numFails == 0) {
            System.out.println("All tests pass");
        } else {
            System.out.println(numFails + " tests failed");
        }
    }
}
