package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import lib.Platform;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected RemoteWebDriver driver;

    public  MainPageObject(RemoteWebDriver driver) {

        this.driver = driver;
    }

    public WebElement waitForElementPresent(String locator, String error_message, long timeoutInSeconds) {

        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(String locator, String error_message) {

        return waitForElementPresent(locator,error_message,5);
    }

    public WebElement waitForElementAndClick(String locator , String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    public WebElement waitForElementAndSendKeys(String locator, String value, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent(String locator, String error_message, long timeoutInSeconds) {

        By by = getLocatorByString(locator);
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }
    public WebElement waitForElementAndClear(String locator , String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    public String waitForElementAndGetAttribute (String locator, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);

        return  element.getAttribute(attribute);

    }

    public void scrollWebPageUp () {

        if (Platform.getInstance().isMW()) {

            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            JSExecutor.executeScript("window.scrollBy(0, 250)");
        } else {

            System.out.println("Method scrollWebPageUp() does nothing from platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void scrollWebPageTillElementNotVisible (String locator, String error_message, int max_swipe) {

        int alreadySwiped = 0;

        WebElement element = this.waitForElementPresent(locator, error_message);

        while (!this.isElementLocatedOnTheScreen(locator)) {

            scrollWebPageUp();
            ++alreadySwiped;

            if (alreadySwiped > max_swipe) {
                Assert.assertTrue(error_message, element.isDisplayed());
            }
        }
        System.out.println("я скролил " + alreadySwiped + "  раз.");
    }

    public void swipeUp(int timeOfSwipe) {

        if (driver instanceof AppiumDriver)
        {

            TouchAction action = new TouchAction((AppiumDriver) driver);
            Dimension size = driver.manage().window().getSize();

            int X = size.width / 2;
            int startY = (int) (size.height * 0.8);
            int endY = (int) (size.height * 0.3);

            action.press(X, startY).waitAction(timeOfSwipe).moveTo(X, endY).release().perform();
        } else {

            System.out.println("Method swipeUp() does nothing from platform " + Platform.getInstance().getPlatformVar());
        }

    }

    public void swipeUpQuick() {

        swipeUp(200);
    }

    public void swipeUpToFindElement(String locator, String error_message, int maxSwipes) {

        By by = getLocatorByString(locator);
        int alreadySwiped = 0;
        while (driver.findElements(by).size() == 0)
        {
            if (alreadySwiped > maxSwipes) {

                waitForElementPresent(locator, "Cannot find element by swipe up. \n" + error_message, 0);
                return;
            }

            swipeUpQuick();
            ++alreadySwiped;
        }
    }

    public void swipeUpTitleElementAppear (String locator, String error_message, int max_swipe) {

        int alreadySwiped = 0;

        while (!this.isElementLocatedOnTheScreen(locator)) {

            if (alreadySwiped > max_swipe) {

                Assert.assertTrue(error_message, this.isElementLocatedOnTheScreen(locator));
            }

            swipeUpQuick();
            ++alreadySwiped;
        }

    }

    public boolean isElementLocatedOnTheScreen (String locator) {

        int element_location_by_y = this.waitForElementPresent(locator, "cannot find element", 1).getLocation().getY();
        if (Platform.getInstance().isMW()) {

            JavascriptExecutor JSExecutor = (JavascriptExecutor) driver;
            Object js_result = JSExecutor.executeScript("return window.pageYOffset");
            element_location_by_y -= Integer.parseInt(js_result.toString());
        }
        int screen_size_by_y = driver.manage().window().getSize().getHeight();
        return element_location_by_y < screen_size_by_y;
    }

    public void clickToElementToTheRightUpperCorner (String locator, String error_message) {

        if (driver instanceof AppiumDriver)
        {
        String locatorAdd = locator + "/..";
        System.out.println(locatorAdd);

        WebElement element = this.waitForElementPresent(locatorAdd,error_message);
        int rightX = element.getLocation().getX();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;
        int width = element.getSize().getWidth();

        int pointToClickX = (rightX + width) - 5 ;
        int pointToClickY = middleY;

        TouchAction action = new TouchAction((AppiumDriver) driver);
        action.tap(pointToClickX, pointToClickY).perform();
        } else {

            System.out.println("Method clickToElementToTheRightUpperCorner() does nothing from platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void swipeElementToLeft(String locator, String error_messages) {

        if (driver instanceof AppiumDriver) {
        WebElement element = waitForElementPresent(locator, error_messages, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction((AppiumDriver) driver);

        action.press(rightX, middleY);
        action.waitAction(300);

        if (Platform.getInstance().isAndroid()) {

            action.moveTo(leftX, middleY);
        } else {

            int offsetX = (-1 * element.getSize().getWidth());
            action.moveTo(offsetX,0);
        }
        action.release();
        action.perform();
        } else {

            System.out.println("Method clickToElementToTheRightUpperCorner() does nothing from platform " + Platform.getInstance().getPlatformVar());
        }

    }

    public int getAmountOfElements(String locator) {

        By by = getLocatorByString(locator);
        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(String locator, String error_message) {

        int amountOfElements = getAmountOfElements(locator);
        if (amountOfElements > 0) {
            String defMessage = "An element '" + locator + "' supposed to be not present";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }

    public void assertElementPresent(String locator, String error_message) {

        By by = getLocatorByString(locator);
        String titleOfArticle = driver.findElement(by).getTagName();
        if (titleOfArticle.isEmpty()) {

            String defMessage = "there is no element '" + locator + "' the page";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }

    public void assertElementPresentForIOS(String locator, String error_message) {

        By by = getLocatorByString(locator);
        String titleOfArticle = driver.findElement(by).getTagName();
        if (titleOfArticle.isEmpty()) {

            String defMessage = "there is no element '" + locator + "' the page";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }
    public void waitForElementAndAttributePresence(String locator, String exp_value, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(locator, error_message, timeoutInSeconds);

        String attributeValue =   element.getAttribute(attribute);

        if (!(attributeValue.equals(exp_value)))
        {
            String defMessage = "Value '" + attributeValue + "' not equals  '" + exp_value + "' value. \n"  ;
            throw new AssertionError(defMessage + " " + error_message);
        }

    }

    public By getLocatorByString(String locatorWithType) {

        String[] explodedLocator = locatorWithType.split(Pattern.quote(":"), 2);
        String byType = explodedLocator[0];
        String locator = explodedLocator[1];

        if (byType.equals("xpath")) {

            return By.xpath(locator);
        } else if (byType.equals("id")) {

            return By.id(locator);
        } else if (byType.equals("css")) {

            return By.cssSelector(locator);
        }else {

            throw new IllegalArgumentException("cannot get type of locator. Locator:" + locatorWithType);
        }
    }

    public void tryClickElementWithFewAttempt(String locator, String error_message, int ammounOfAttempt) {

        int currentAttempt = 0;
        boolean needMoreAttempt = true;

        while (needMoreAttempt) {

            try {
                this.waitForElementAndClick(locator, error_message, 1);
                needMoreAttempt = false;
            } catch (Exception e) {

                if (currentAttempt > ammounOfAttempt) {

                    this.waitForElementAndClick(locator, error_message,1);

                }
            }

            System.out.println("я пытался кликнуть " + currentAttempt + "раз...");

            ++currentAttempt;
        }

    }

}
