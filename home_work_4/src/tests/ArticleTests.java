package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle () {

        SearchPageObject SearchPageObject = new  SearchPageObject(driver);
        String searchValue = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(titleSearchArticle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String title =  ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                title
        );

    }

    @Test
    public void testSwipeArticle () {


        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Appium");
        SearchPageObject.clickByArticleWithSubstring("Appium");

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();

    }

    @Test //Ex6: Тест: assert title
    public void testTitleArticlePresence () {

        SearchPageObject SearchPageObject = new SearchPageObject(driver);

        SearchPageObject.initSearchInput();
        String searchValueSecondArticle = "Java";
        String titleSearchSecondArticle = "Java (programming language)";
        SearchPageObject.typeSearchLine(searchValueSecondArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchSecondArticle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.assertTitlePresence();

    }



}
