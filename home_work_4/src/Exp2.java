import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.net.URL;
import java.sql.Struct;
import java.util.List;


public class Exp2 {

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
        capabilities.setCapability("orientation", "PORTRAIT"); //Ex7*: Поворот экрана. каждый новый тест будет начинаться с портретной ориентации.
        capabilities.setCapability("app","/Users/anastasiya/Documents/autotest_les/home_work_3/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {

        driver.quit();
    }


    @Test
    public void testSwipeArticle () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "cannot find element with text 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "cannot find element with text 'Appium'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find title",
                15
        );

        swipeUpToFindElement(
                By.xpath("//*[@text='View page in browser']"),
                "Cannot find end of the article",
                20
        );

    }

    @Test
    public void testSaveFirstArticleToMyList () {

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

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find article title",
                20
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to option",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "cannot find GOT IT",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "cannot find input to set name of article folder",
                5
        );
        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                "My first reading list",
                "cannot put text",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "cannot find button OK",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "cannot find button X",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "cannot find navigation button to My List",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='My first reading list']"),
                "cannot find create folder",
                5
        );
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "cannot delete saved article",
                5
        );
    }

    @Test
    public void testAmountOfNotEmptySearchResult () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        String searchValue = "Linkin Park Discography";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValue,
                "cannot find element with text 'Search…'",
                10
        );
        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        waitForElementPresent(
                By.xpath(searchResultLocator),
                "cannot find anything" + searchValue,
                10
        );
        int amountOfResultSearch = getAmountOfElement(By.xpath(searchResultLocator));
        Assert.assertTrue("We found too few result", amountOfResultSearch > 0);

    }

    @Test
    public void testAmountOfEmptySearch () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        String searchValue = "fgtyhykmbg";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValue,
                "cannot find element with text 'Search…'",
                10
        );

        String searchResultLocator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String emptyResultLabel = "//*[@text = 'No results found']";

        waitForElementPresent(
                By.xpath(emptyResultLabel),
                "cannot find empty result label by the request " + searchValue,
                15
        );

        assertElementNotPresent(
                By.xpath(searchResultLocator),
                "we found some request " + searchValue
        );

    }

    @Test
    public void testChangeScreenOrientationOnSearchResult () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        String searchValue = "java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValue,
                "cannot find element with text 'Search…'",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find element with text 'Object-oriented programming language'",
                15
        );

        String titleBeforeRotation  = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article",
                15
        );

        driver.rotate(ScreenOrientation.LANDSCAPE);
//        driver.rotate(ScreenOrientation.PORTRAIT);

        String titleAfterRotation  = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article",
                15
        );

        Assert.assertEquals("title not equals",titleBeforeRotation, titleAfterRotation);
    }

    @Test
    public void testCheckSearchArticleInBackground () {

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        String searchValue = "java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValue,
                "cannot find element with text 'Search…'",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find element with text 'Object-oriented programming language'"
        );
        driver.rotate(ScreenOrientation.PORTRAIT);

        driver.runAppInBackground(5);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find article after returning from background"
        );
    }

    @Test //Ex5: Тест: сохранение двух статей
    public void testSaveTwoArticle () {
        //поиск первой статьи
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );
        String searchValFirstArticle = "Java";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValFirstArticle,
                "cannot find element with text 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "cannot find element with text 'Object-oriented programming language'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find article title",
                20
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to option",
                5
        );
        //добавление статьи в список
        String addReadingList = "//*[@text='Add to reading list']";
        waitForElementPresent(
                By.xpath(addReadingList),
                "cannot find option to add article to reading list",
                10
        );
        waitForElementAndClick(
                By.xpath(addReadingList),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "cannot find GOT IT",
                5
        );

        String textInputField = "org.wikipedia:id/text_input";
        waitForElementAndClear(
                By.id(textInputField),
                "cannot find input to set name of article folder",
                5
        );

        String nameFolder = "My first reading list";
        waitForElementAndSendKeys(
                By.id(textInputField),
                nameFolder,
                "cannot put text",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "cannot find button OK",
                5
        );
        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "cannot find button X",
                5
        );
        //поиск второй статьи в список
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );
        String searchValSecondArticle = "Appium";
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchValSecondArticle,
                "cannot find element with text 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "cannot find element with text 'Appium'",
                5
        );

        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "cannot find title article",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to option",
                5
        );
        //добавление второй статьи в список
        waitForElementPresent(
                By.xpath(addReadingList),
                "cannot find option to add article to reading list",
                10
        );
        waitForElementAndClick(
                By.xpath(addReadingList),
                "Cannot find option to add article to reading list",
                5
        );
        String savedFolder = "//*[@resource-id='org.wikipedia:id/item_title'][@text='"+ nameFolder +"']";
        waitForElementAndClick(
                By.xpath(savedFolder),
                "cannot find folder " + nameFolder ,
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "cannot find button X",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "cannot find navigation button to My List",
                5
        );

        //переход в сохраненный список статей
        waitForElementPresent(
                By.xpath(savedFolder),
                "cannot find create folder " + nameFolder,
                10
        );
        waitForElementAndClick(
                By.xpath(savedFolder),
                "cannot find create folder " + nameFolder,
                5
        );

        //удаление одной из статей
        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article"
        );
        //проверка что статьи нет
        waitForElementNotPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "cannot delete saved article",
                5
        );

        String titleSavedArticle = waitForElementAndGetAttribute(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "text",
                "cannot find saved article in folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Appium']"),
                "cannot find saved article in folder with text = Appium",
                10
        );

        //String titleArticle = "notAppium";
        String titleArticle = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "cannot find title of article",
                5
        );
        //проверка что заголовок в списке и в статье совпадает
        Assert.assertEquals("title article in folder and title article not equals", titleSavedArticle, titleArticle);
    }

    @Test //Ex6: Тест: assert title
    public void testTitleArticlePresence () {


        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "cannot find element with text 'Search Wikipedia'",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "cannot find element with text 'Search…'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
                "cannot find element with text 'Appium'",
                5
        );

        assertElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/view_page_title_text'][@text='Java (programming language)']"),
                "cannot find element"
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

    private String waitForElementAndGetAttribute (By by, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

        return  element.getAttribute(attribute);

    }

    protected void swipeUp(int timeOfSwipe) {

        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int X = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action.press(X, startY).waitAction(timeOfSwipe).moveTo(X, endY).release().perform();
    }

    protected void swipeUpQuick() {

        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int maxSwipes) {

        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0)
        {
            if (alreadySwiped > maxSwipes) {

                waitForElementPresent(by, "Cannot find element by swipe up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_messages) {

        WebElement element = waitForElementPresent(by, error_messages, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();

    }

    int getAmountOfElement(By by) {

        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message) {

        int amountOfElements = getAmountOfElement(by);
        if (amountOfElements > 0) {
            String defMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }

//    private String assertElementPresent(By by, String error_message){
//
//        String element = driver.findElement(by).getTagName();
//
//        return element;
//    }
    private void assertElementPresent(By by, String error_message) {

        String titleOfArticle = driver.findElement(by).getTagName();
        if (titleOfArticle.isEmpty()) {

            String defMessage = "there is no element '" + by.toString() + "' the page";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }

}

