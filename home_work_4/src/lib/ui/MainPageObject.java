package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.regex.Pattern;

public class MainPageObject {

    protected AppiumDriver driver;

    public  MainPageObject(AppiumDriver driver) {

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

    public void swipeUp(int timeOfSwipe) {

        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int X = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * 0.2);

        action.press(X, startY).waitAction(timeOfSwipe).moveTo(X, endY).release().perform();
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

    public void swipeElementToLeft(String locator, String error_messages) {

        WebElement element = waitForElementPresent(locator, error_messages, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();

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
        } else {

            throw new IllegalArgumentException("cannot get type of locator. Locator:" + locatorWithType);
        }
    }

}
