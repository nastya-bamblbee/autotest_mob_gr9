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

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {

            title =  ArticlePageObject.getArticleTitle();
            System.out.println("title of article " + title);
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
        String search = "Java";
        SearchPageObject.typeSearchLine(search);
        String searchArticleTitle = "Java (programming language)";
        SearchPageObject.clickByArticleWithSubstring(searchArticleTitle);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()){

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

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isMW()) {

            ArticlePageObject.assertTitlePresence();

        } else if (Platform.getInstance().isIOS()) {

            ArticlePageObject.assertTitlePresenceForIOS(titleSearchSecondArticle);
        }
    }



}
