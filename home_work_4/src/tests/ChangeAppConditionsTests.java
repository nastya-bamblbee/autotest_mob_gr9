package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class ChangeAppConditionsTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchResult () {

        SearchPageObject SearchPageObject = new  SearchPageObject(driver);

        String searchValue = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(titleSearchArticle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        String titleBeforeRotation  = ArticlePageObject.getArticleTitle();

        this.rotateScreenLandscape();

        String titleAfterRotation  = ArticlePageObject.getArticleTitle();

        assertEquals("title not equals",titleBeforeRotation, titleAfterRotation);

        this.rotateScreenPortrait();

        String titleAfterSecondRotation  = ArticlePageObject.getArticleTitle();

        assertEquals("title not equals",titleBeforeRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground () {

        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);

        String searchValue = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.waitForSearchResult(titleSearchArticle);

        this.backgroundApp(5);

        SearchPageObject.waitForSearchResult(titleSearchArticle);
    }

}
