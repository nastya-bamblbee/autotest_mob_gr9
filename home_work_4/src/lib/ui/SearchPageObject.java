package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

abstract public class SearchPageObject extends MainPageObject {

    protected static  String SEARCH_INIT_ELEMENT ;
    protected static  String SEARCH_INPUT ;
    protected static  String SEARCH_RESULT_BY_SUBSTRING_TPL;
    protected static  String SEARCH_CANCEL_BUTTON ;
    protected static  String SEARCH_RESULT ;
    protected static  String SEARCH_EMPTY_RESULT_ELEMENT ;
    protected static  String SEARCH_RESULT_LIST ;
    protected static  String SEARCH_EMPTY_PAGE;
    protected static  String SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL;


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
        //System.out.println(searchResultXpath);
        this.waitForElementPresent(searchResultXpath, "cannot find article with title " + titilesubstring + " and description " + descsubstring , 10);

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

      if (Platform.getInstance().isAndroid()) {

            String searchResultId = getSearchResultContainer("");
            By by_search = getLocatorByString(searchResultId);
            System.out.println(by_search);
            WebElement element = this.waitForElementPresent(SEARCH_RESULT_LIST, "cannot find element with search results", 15);

            By by = getLocatorByString(SEARCH_RESULT);
            List<WebElement> searchResult = element.findElements(by);
            System.out.println(searchResult.size());

            Iterator<WebElement> it = searchResult.iterator();
            for (;it.hasNext();  ) {
                WebElement elem = it.next();
                String title = elem.findElement(by_search).getAttribute(attribute).toLowerCase();
                String[] titleWords = title.split("[–| ]").clone();

                Boolean res = Arrays.stream(titleWords).anyMatch(o -> Objects.equals(o, searchVal));
                Assert.assertTrue("none element found at :" + title, res);
            }

        } else {

          By by = getLocatorByString(SEARCH_RESULT);

          List<WebElement> searchResult =  driver.findElements(by);
          System.out.println(searchResult.size());

            for (WebElement elem :searchResult ) {

                String title = elem.findElement(by).getAttribute(attribute).toLowerCase();
                String head = title.split("[\n]")[0];
                String[] titleWords = head.split("[–| ]").clone();

                Boolean res = Arrays.stream(titleWords).anyMatch(o -> Objects.equals(o, searchVal));
                Assert.assertTrue("none element found at :" + title, res);
            }

        }

    }

    public void waitForEmptyResultLabel () {

        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT, "cannot find empty result element", 10);

    }

    public void assertThereIsNotResultOfSearch() {

        this.assertElementNotPresent(SEARCH_RESULT, "we supposed not to find any results");
    }

    public void assertEmptySearchPage () {

        if (Platform.getInstance().isAndroid()) {

            this.waitForElementPresent(SEARCH_EMPTY_PAGE, "cannot find empty message");
        } else {

            this.assertElementNotPresent(SEARCH_RESULT, "any articles located on page");
        }

    }


}
