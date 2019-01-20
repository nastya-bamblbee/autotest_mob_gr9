package lib.ui;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPageObject {

    protected AppiumDriver driver;

    public  MainPageObject(AppiumDriver driver) {

        this.driver = driver;
    }

    public WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return  wait.until(
                ExpectedConditions.presenceOfElementLocated(by)
        );
    }

    public WebElement waitForElementPresent(By by, String error_message) {

        return waitForElementPresent(by,error_message,5);
    }

    public WebElement waitForElementAndClick(By by , String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }
    public WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    public boolean waitForElementNotPresent( By by , String error_message, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }
    public WebElement waitForElementAndClear(By by , String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message,timeoutInSeconds);
        element.clear();
        return element;
    }

    public String waitForElementAndGetAttribute (By by, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

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

    public void swipeUpToFindElement(By by, String error_message, int maxSwipes) {

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

    public void swipeElementToLeft(By by, String error_messages) {

        WebElement element = waitForElementPresent(by, error_messages, 10);

        int leftX = element.getLocation().getX();
        int rightX = leftX + element.getSize().getWidth();
        int upperY = element.getLocation().getY();
        int lowerY = upperY + element.getSize().getHeight();
        int middleY = (upperY + lowerY) / 2;

        TouchAction action = new TouchAction(driver);
        action.press(rightX, middleY).waitAction(300).moveTo(leftX, middleY).release().perform();

    }

    public int getAmountOfElements(By by) {

        List elements = driver.findElements(by);
        return elements.size();
    }

    public void assertElementNotPresent(By by, String error_message) {

        int amountOfElements = getAmountOfElements(by);
        if (amountOfElements > 0) {
            String defMessage = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }

    public void assertElementPresent(By by, String error_message) {

        String titleOfArticle = driver.findElement(by).getTagName();
        if (titleOfArticle.isEmpty()) {

            String defMessage = "there is no element '" + by.toString() + "' the page";
            throw new AssertionError(defMessage + " " + error_message);
        }
    }
    public void waitForElementAndAttributePresence(By by, String exp_value, String attribute, String error_message, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);

        String attributeValue =   element.getAttribute(attribute);

        if (!(attributeValue.equals(exp_value)))
        {
            String defMessage = "Value '" + attributeValue + "' not equals  '" + exp_value + "' value. \n"  ;
            throw new AssertionError(defMessage + " " + error_message);
        }

    }

}
