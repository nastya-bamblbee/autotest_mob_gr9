package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle () {

        String title;

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        String searchValue = "Java";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(titleSearchArticle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {

            title =  ArticlePageObject.getArticleTitle();
        } else {

            title = ArticlePageObject.getArticleTitleForIOS(titleSearchArticle);
        }

        assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                title
        );

    }

    @Test
    public void testSwipeArticle () {


        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String search = "Appium";
        SearchPageObject.typeSearchLine(search);
        String searchArticleTitle = "Appium";
        SearchPageObject.clickByArticleWithSubstring(searchArticleTitle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()){

            ArticlePageObject.waitForTitleElement();
        } else {

            ArticlePageObject.getArticleTitleForIOS(searchArticleTitle);
        }

        ArticlePageObject.swipeToFooter();

    }

    @Test //Ex6: Тест: assert title
    public void testTitleArticlePresence () {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String searchValueSecondArticle = "Appium";
        String titleSearchSecondArticle = "Appium";
        SearchPageObject.typeSearchLine(searchValueSecondArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchSecondArticle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {

            ArticlePageObject.assertTitlePresence();

        }else {

            ArticlePageObject.assertTitlePresenceForIOS(titleSearchSecondArticle);
        }

    }



}
