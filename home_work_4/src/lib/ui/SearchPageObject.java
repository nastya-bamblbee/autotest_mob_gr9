package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SearchPageObject extends MainPageObject {

    private static final String SEARCH_INIT_ELEMENT = "//*[contains(@text,'Search Wikipedia')]";
    private static final String SEARCH_INPUT = "//*[contains(@text,'Search…')]";
    private static final String SEARCH_RESULT_BY_SUBSTRING_TPL = "//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
    private static final String SEARCH_CANCEL_BUTTON = "org.wikipedia:id/search_close_btn";
    private static final String SEARCH_RESULT = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    private static final String SEARCH_EMPTY_RESULT_ELEMENT = "//*[@text = 'No results found']";
    private static final String SEARCH_RESULT_LIST = "org.wikipedia:id/search_results_list";
    private static final String SEARCH_EMPTY_PAGE = "org.wikipedia:id/search_empty_message";

    public SearchPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    private static String getSearchResult (String substring) {

     return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getSearchResultContainer (String substring) {

        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("[@text='{SUBSTRING}']", substring);
    }

    public void initSearchInput () {

        this.waitForElementAndClick(By.xpath(SEARCH_INIT_ELEMENT), "cannot find element with text 'Search Wikipedia'", 5);
        this.waitForElementPresent(By.xpath(SEARCH_INPUT), "cannot find search input");
    }

    public void typeSearchLine (String searchLine) {

        this.waitForElementAndSendKeys(By.xpath(SEARCH_INPUT), searchLine, "cannot find search input field", 5);
    }

    public void assertTextSearchLine(String expValue, String attribute ) {

        this.waitForElementAndAttributePresence(By.xpath(SEARCH_INPUT), expValue, attribute, "cannot find element with text 'Search…'",
                10);
    }

    public void waitForSearchResult (String substring) {

        String searchResultXpath = getSearchResult(substring);
        this.waitForElementPresent(By.xpath(searchResultXpath), "cannot find element " + substring);
    }

    public void waitForSearchResultList () {

        this.waitForElementPresent(By.id(SEARCH_RESULT_LIST),"cannot find element with search results", 15);
    }

    public void clickByArticleWithSubstring (String substring) {

        String searchResultXpath = getSearchResult(substring);
        this.waitForElementAndClick(By.xpath(searchResultXpath), "cannot find and click element " + substring, 5);
    }

    public void waitForCancelButtonToAppear () {

        this.waitForElementPresent(By.id(SEARCH_CANCEL_BUTTON), "cannot find X cancel button",5);
    }

    public void waitForCancelButtonToDissappear () {

        this.waitForElementNotPresent(By.id(SEARCH_CANCEL_BUTTON), "search cancel button is still presence", 5);
    }

    public void clickCancelSearch () {

        this.waitForElementAndClick(By.id(SEARCH_CANCEL_BUTTON),"cannot find X cancel button",5);
    }

    public int getAmountOfFoundArticles () {

        this.waitForElementPresent(
                By.xpath(SEARCH_RESULT),
                "cannot find anything",
                10
                );
        return this.getAmountOfElements(By.xpath(SEARCH_RESULT));
    }

    public void assertContainSearchResult (String searchVal,  String attribute) {

        String searchResultId = getSearchResultContainer("");
        WebElement element = this.waitForElementPresent(By.id(SEARCH_RESULT_LIST), "cannot find element with search results", 15);

        List<WebElement> searchResult = element.findElements(By.xpath(SEARCH_RESULT));
        System.out.println(searchResult.size());

        Iterator<WebElement> it = searchResult.iterator();
        for (;it.hasNext();  ) {
            WebElement elem = it.next();
            String title = elem.findElement(By.xpath(searchResultId)).getAttribute(attribute).toLowerCase();
            String[] titleWords = title.split("[–| ]").clone();

            Boolean res = Arrays.stream(titleWords).anyMatch(o -> Objects.equals(o, searchVal));
            Assert.assertTrue("none element found at :" + title, res);
        }
    }

    public void waitForEmptyResultLabel () {

        this.waitForElementPresent(By.xpath(SEARCH_EMPTY_RESULT_ELEMENT), "cannot find empty result element", 10);

    }

    public void assertThereIsNotResultOfSearch() {

        this.assertElementNotPresent(By.xpath(SEARCH_RESULT), "we supposed not to find any results");
    }

    public void assertEmptySearchPage () {

        this.assertElementPresent(By.id(SEARCH_EMPTY_PAGE), "cannot find empty message");
    }
}
