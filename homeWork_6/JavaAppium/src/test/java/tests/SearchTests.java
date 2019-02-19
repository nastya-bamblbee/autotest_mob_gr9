package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;
import org.openqa.selenium.support.ui.Sleeper;

import java.util.concurrent.TimeUnit;


public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        //String titleSearchArticle = "object-oriented programming language";
        SearchPageObject.waitForSearchResult(titleSearchArticle);

    }

    @Test //Ex3: Тест: отмена поиска
    public void testCancelSearch () {

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.waitForSearchResultList();

        int searchResult = SearchPageObject.getAmountOfFoundArticles();

        assertTrue("found less than 2 elements", searchResult > 1);

        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        SearchPageObject.clickCancelSearch();
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
        SearchPageObject.assertEmptySearchPage();
    }

    @Test //Ex2: Создание метода.
    public void testPresenceText () {

        String expValue;
        String attribute;

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();

        if (Platform.getInstance().isAndroid()){

            expValue = "Search…";
            attribute = "text";

        }else if (Platform.getInstance().isIOS()){

            expValue = "Search Wikipedia";
            attribute = "name";
        } else {

            expValue = "Search Wikipedia";
            attribute = "placeholder";
        }

        SearchPageObject.assertTextSearchLine(expValue, attribute);
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
    }

    @Test //Ex4*: Тест: проверка слов в поиске
    public void testPresenceWordsInSearch () {

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        String searchValue = "barbe";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
        SearchPageObject.waitForSearchResultList();
        String attribute = "title";
        SearchPageObject.assertContainSearchResult(searchValue, attribute);
    }

    @Test
    public void testAmountOfNotEmptySearchResult () {

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);

        String searchValue = "Linkin Park Discography";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        driver.manage().timeouts().implicitlyWait(60000, TimeUnit.MILLISECONDS);
        int amountOfResultSearch = SearchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few result", amountOfResultSearch > 0);

    }

    @Test
    public void testAmountOfEmptySearch () {

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);

        String searchValue = "fgtyhykmbg";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNotResultOfSearch();

    }

    @Test// Ex9*
    public void testSearchWithTitleAndDescription () {
        //доделать для mw

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Epoch";
        SearchPageObject.typeSearchLine(searchValue);

        String arrayTitle[] = {"Epoch","Epoch (reference date)", "Epoch (astronomy)111" };
        String arrayDesc[] = {"Wikimedia disambiguation page", "111Reference point from which time is measured", "Moment in time used as a reference point for some time-varying astronomical quantity"};
//        String arrayTitle[] = {"Epoch", "Epoch (astronomy)"};
//        String arrayDesc[] = {"Disambiguation page providing links to topics that could be referred to by the same search term" , "moment in time used as a reference point for some time-varying astronomical quantity"};
//
//        SearchPageObject.waitTest("Epoch");

        for ( int i = 0; i < arrayDesc.length; i++ ) {

            SearchPageObject.waitForElementByTitleAndDescription(arrayTitle[i],arrayDesc[i]);

        }

    }


}
