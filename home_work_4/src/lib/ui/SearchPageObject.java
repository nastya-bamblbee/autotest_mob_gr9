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

    private static final String SEARCH_INIT_ELEMENT = "xpath://*[contains(@text,'Search Wikipedia')]";
    private static final String SEARCH_INPUT = "xpath://*[contains(@text,'Search…')]";
    private static final String SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='{SUBSTRING}']";
    private static final String SEARCH_CANCEL_BUTTON = "id:org.wikipedia:id/search_close_btn";
    private static final String SEARCH_RESULT = "xpath://*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
    private static final String SEARCH_EMPTY_RESULT_ELEMENT = "xpath://*[@text = 'No results found']";
    private static final String SEARCH_RESULT_LIST = "id:org.wikipedia:id/search_results_list";
    private static final String SEARCH_EMPTY_PAGE = "id:org.wikipedia:id/search_empty_message";
    private static final String SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[@text = '{TITLESUBSTRING}']/following-sibling::text[text() = '{DESCSUBSTRING}'] ";


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

    private static String getSearchResultTitleDescription(String titilesubstring, String descsubstring) {

        String str =   SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL.replace("{TITLESUBSTRING}", titilesubstring);
        return  str.replace("{DESCSUBSTRING}", descsubstring);
    }

    public void initSearchInput () {

        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "cannot find element with text 'Search Wikipedia'", 5);
        this.waitForElementPresent(SEARCH_INPUT, "cannot find search input");
    }

    public void typeSearchLine (String searchLine) {

        this.waitForElementAndSendKeys(SEARCH_INPUT, searchLine, "cannot find search input field", 5);
    }

    public void assertTextSearchLine(String expValue, String attribute ) {

        this.waitForElementAndAttributePresence(SEARCH_INPUT, expValue, attribute, "cannot find element with text 'Search…'",
                10);
    }

    public void waitForSearchResult (String substring) {

        String searchResultXpath = getSearchResult(substring);
        this.waitForElementPresent(searchResultXpath, "cannot find element " + substring);
    }

    public void waitForElementByTitleAndDescription (String titilesubstring, String descsubstring) {

        String searchResultXpath = getSearchResultTitleDescription(titilesubstring, descsubstring);
        System.out.println(searchResultXpath);
        this.waitForElementPresent(searchResultXpath, "cannot find element", 10);
    }

    public void waitForSearchResultList () {

        this.waitForElementPresent(SEARCH_RESULT_LIST,"cannot find element with search results", 15);
    }

    public void clickByArticleWithSubstring (String substring) {

        String searchResultXpath = getSearchResult(substring);
        this.waitForElementAndClick(searchResultXpath, "cannot find and click element " + substring, 5);
    }

    public void waitForCancelButtonToAppear () {

        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "cannot find X cancel button",5);
    }

    public void waitForCancelButtonToDissappear () {

        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "search cancel button is still presence", 5);
    }

    public void clickCancelSearch () {

        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON,"cannot find X cancel button",5);
    }

    public int getAmountOfFoundArticles () {

        this.waitForElementPresent(
                SEARCH_RESULT,
                "cannot find anything",
                10
                );
        return this.getAmountOfElements(SEARCH_RESULT);
    }

    public void assertContainSearchResult (String searchVal,  String attribute) {

        String searchResultId = getSearchResultContainer("");
        WebElement element = this.waitForElementPresent(SEARCH_RESULT_LIST, "cannot find element with search results", 15);

        By by = getLocatorByString(SEARCH_RESULT);
        List<WebElement> searchResult = element.findElements(by);
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

        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "cannot find empty result element", 10);

    }

    public void assertThereIsNotResultOfSearch() {

        this.assertElementNotPresent(SEARCH_RESULT, "we supposed not to find any results");
    }

    public void assertEmptySearchPage () {

        this.assertElementPresent(SEARCH_EMPTY_PAGE, "cannot find empty message");
    }
}
