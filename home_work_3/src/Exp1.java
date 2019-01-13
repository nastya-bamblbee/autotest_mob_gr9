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

import java.net.URL;
import java.util.List;

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
                10
        );

        List searchResult = element.findElements(By.id("org.wikipedia:id/page_list_item_container"));

        Assert.assertTrue("found less than 2 elements", searchResult.size() > 1);

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "cannot find X to cancel search ",
                5
        );

        boolean result = waitForElementAndAttributePresence(
                By.id("org.wikipedia:id/search_empty_message"),
                "Search and read the free encyclopedia in your language",
                "text",
                "cannot find element",
                5
        );
        Assert.assertTrue("search not empty", result);

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

        WebElement title_element =  waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find title",
                15
        );

        String article_text = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title",
                "Java (programming language)",
                article_text
        );

    }

    @Test //Ex2: Создание метода.
    public void testPresenceText () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        boolean result = waitForElementAndAttributePresence(By.id("org.wikipedia:id/search_src_text"),
                "Search…",
                "text",
                "cannot find element with text 'Search…'",
                10);

        Assert.assertTrue("element with text 'Search…' not found ", result);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "java",
                "cannot find element with text 'Search…'",
                5
        );

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
    private boolean waitForElementAndAttributePresence(By by, String exp_value, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

        return  element.getAttribute(attribute).contains(exp_value);

    }
}
