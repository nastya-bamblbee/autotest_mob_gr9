package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ChangeAppConditionsTests extends CoreTestCase {

    @Test
    public void testChangeScreenOrientationOnSearchResult () {

        if (Platform.getInstance().isMW()) {
            return;
        }
        String titleBeforeRotation;
        String titleAfterRotation;
        String titleAfterSecondRotation;

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        String searchValue = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(titleSearchArticle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {

            titleBeforeRotation  = ArticlePageObject.getArticleTitle();

        } else {

            titleBeforeRotation = ArticlePageObject.getArticleTitleForIOS(titleSearchArticle);
        }

        this.rotateScreenLandscape();

        if (Platform.getInstance().isAndroid()) {

            titleAfterRotation  = ArticlePageObject.getArticleTitle();

        } else {

            titleAfterRotation = ArticlePageObject.getArticleTitleForIOS(titleSearchArticle);
        }

        assertEquals("title not equals",titleBeforeRotation, titleAfterRotation);

        this.rotateScreenPortrait();


        if (Platform.getInstance().isAndroid()) {

            titleAfterSecondRotation  = ArticlePageObject.getArticleTitle();
        } else {

            titleAfterSecondRotation = ArticlePageObject.getArticleTitleForIOS(titleSearchArticle);
        }

        assertEquals("title not equals",titleBeforeRotation, titleAfterSecondRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground () {

        if (Platform.getInstance().isMW()) {
            return;
        }
        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);

        String searchValue = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.waitForSearchResult(titleSearchArticle);

        this.backgroundApp(5);

        SearchPageObject.waitForSearchResult(titleSearchArticle);
    }

}
