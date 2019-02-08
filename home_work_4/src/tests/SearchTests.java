package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
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

        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertEmptySearchPage();
    }

    @Test //Ex2: Создание метода.
    public void testPresenceText () {

        String expValue;

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();

        if (Platform.getInstance().isAndroid()){

            expValue = "Search…";

        }else {

            expValue = "Search Wikipedia";
        }
        String attribute = "name";
        SearchPageObject.assertTextSearchLine(expValue, attribute);
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
    }

    @Test //Ex4*: Тест: проверка слов в поиске
    public void testPresenceWordsInSearch () {

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        String searchValue = "1942";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.waitForSearchResultList();
        String attribute = "name";
        SearchPageObject.assertContainSearchResult(searchValue, attribute);
    }

    @Test
    public void testAmountOfNotEmptySearchResult () {

        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);

        String searchValue = "Linkin Park Discography";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
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


        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Epoch";
        SearchPageObject.typeSearchLine(searchValue);

        String arrayTitle[] = {"Epoch","Epoch (reference date)", "Epoch (astronomy)" };
        String arrayDesc[] = {"Wikimedia disambiguation page", "Reference point from which time is measured", "Moment in time used as a reference point for some time-varying astronomical quantity"};


        for ( int i = 0; i < 3; i++ ) {

            SearchPageObject.waitForElementByTitleAndDescription(arrayTitle[i],arrayDesc[i]);

        }

    }


}
