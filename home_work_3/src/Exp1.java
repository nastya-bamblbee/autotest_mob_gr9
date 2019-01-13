import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Exp1 {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","7.1");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackege","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/anastasiya/Documents/autotest_les/home_work_3/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {

        driver.quit();
    }

    @Test
    public void testSearch() {


        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "cannot find element with text 'Search…'",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find element with text 'Object-oriented programming language' ",
                15
        );

    }

    @Test //Ex3: Тест: отмена поиска
    public void testCancelSearch () {

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "cannot find Search Wikipedia input",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "cannot find element with text 'Search…'",
                30
        );

       WebElement element =  waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "cannot find element",
                5
        );

        List searchResult = element.findElements(By.id("org.wikipedia:id/page_list_item_container"));

        Assert.assertTrue("found less than 2 elements", searchResult.size() > 1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "cannot find X to cancel search ",
                5
        );

        waitForElementAndAttributePresence(
                By.id("org.wikipedia:id/search_empty_message"),
                "Search and read the free encyclopedia in your language",
                "text",
                "cannot find element",
                5
        );

    }

    @Test
    public void testCompareArticleTitle () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "cannot find element with text 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find element with text 'Object-oriented programming language'",
                5
        );

        WebElement titleElement =  waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find title",
                15
        );

        String articleText = titleElement.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                articleText
        );

    }

    @Test //Ex2: Создание метода.
    public void testPresenceText () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        waitForElementAndAttributePresence(By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "text",
                "cannot find element with text 'Search…'",
                10);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "cannot find element with text 'Search…'",
                5
        );

    }

    @Test //Ex4*: Тест: проверка слов в поиске
    public void testPresenceWordsInSearch () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );
        String searchVal = "barbi";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchVal,
                "cannot find element with text 'Search…'",
                5
        );

        WebElement element =  waitForElementPresent(
                By.id("org.wikipedia:id/search_results_list"),
                "cannot find element",
                10
        );

        List<WebElement> searchResult = element.findElements(By.id("org.wikipedia:id/page_list_item_container"));

        Iterator<WebElement> it = searchResult.iterator();
        for (;it.hasNext();  ) {
            WebElement elem = it.next();
            String title = elem.findElement(By.id("org.wikipedia:id/page_list_item_title")).getAttribute("text").toLowerCase();
            String[] titleWords = title.split("[–| ]").clone();

            Boolean res = Arrays.stream(titleWords).anyMatch(o -> Objects.equals(o, searchVal));
            Assert.assertTrue("none element found at :" + title, res);
        }
    }

    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    private WebElement waitForElementPresent(By by, String error_message) {

        return waitForElementPresent(by,error_message,5);
    }

    private WebElement waitForElementAndClick(By by , String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent( By by , String error_message, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }
    private WebElement waitForElementAndClear(By by , String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    //Ex2: Создание метода
    private void waitForElementAndAttributePresence(By by, String exp_value, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

        String attributeValue =   element.getAttribute(attribute);

        if (!(attributeValue.equals(exp_value)))
        {
            String defMessage = "Value '" + attributeValue + "' not equals  '" + exp_value + "' value. \n"  ;
            throw new AssertionError(defMessage + " " + error_message);
        }

    }
}
