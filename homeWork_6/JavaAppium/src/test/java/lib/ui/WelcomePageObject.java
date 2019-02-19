package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WelcomePageObject extends  MainPageObject {

    private static final String STEP_LEARN_MORE_LINK = "id:Learn more about Wikipedia";
    private static final String CLICK_NEXT_BUTTON = "id:Next";
    private static final String STEP_NEW_WAYS_TO_EXPLORE = "id:New ways to explore";
    private static final String STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES = "id:Add or edit preferred languages";
    private static final String STEP_LEARN_MORE_ABOUT_DATA_COLLECTION = "id:Learn more about data collected";
    private static final String CLICK_GET_STARTED_BUTTON = "id:Get started";
    private static final String SKIP = "id:Skip";

    public WelcomePageObject(RemoteWebDriver driver) {

        super(driver);
    }

    public void waitForLearnMoreLink() {

        this.waitForElementPresent(STEP_LEARN_MORE_LINK, "cannot find 'Learn more about Wikipedia' link", 10);
    }

    public void clickNextButton() {

        this.waitForElementAndClick(CLICK_NEXT_BUTTON, "cannot find end click button 'Next'", 10);
    }

    public void waitForNewWaysToExplore() {

        this.waitForElementPresent(STEP_NEW_WAYS_TO_EXPLORE, "cannot find 'New ways to explore' on page", 10);
    }

    public void waitForAddOrEditPreferredLanguages() {

        this.waitForElementPresent(STEP_ADD_OR_EDIT_PREFERRED_LANGUAGES, "cannot find 'Add or edit preferred languages' on page",10);
    }

    public void waitForLearnMoreAboutDataCollected() {

        this.waitForElementPresent(STEP_LEARN_MORE_ABOUT_DATA_COLLECTION, "cannot find 'Learn more about data collected' on page", 10);
    }

    public void clickGetStartedButton() {

        this.waitForElementAndClick(CLICK_GET_STARTED_BUTTON, "cannot find and click 'Get started' button", 10);
    }

    public void clickSkip () {

        this.waitForElementAndClick(SKIP,"cannot find element",5);
    }
}
