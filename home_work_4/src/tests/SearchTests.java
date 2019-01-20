package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch() {

        SearchPageObject SearchPageObject = new  SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.waitForSearchResult(titleSearchArticle);

    }

    @Test //Ex3: Тест: отмена поиска
    public void testCancelSearch () {

        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);
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

        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String expValue = "Search…";
        String attribute = "text";
        SearchPageObject.assertTextSearchLine(expValue, attribute);
        String searchValue = "Java";
        SearchPageObject.typeSearchLine(searchValue);
    }

    @Test //Ex4*: Тест: проверка слов в поиске
    public void testPresenceWordsInSearch () {

        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);
        String searchValue = "1942";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.waitForSearchResultList();
        String attribute = "text";
        SearchPageObject.assertContainSearchResult(searchValue, attribute);
    }

    @Test
    public void testAmountOfNotEmptySearchResult () {

        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);

        String searchValue = "Linkin Park Discography";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        int amountOfResultSearch = SearchPageObject.getAmountOfFoundArticles();

        assertTrue("We found too few result", amountOfResultSearch > 0);

    }

    @Test
    public void testAmountOfEmptySearch () {

        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);

        String searchValue = "fgtyhykmbg";
        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.waitForEmptyResultLabel();
        SearchPageObject.assertThereIsNotResultOfSearch();

    }


}
