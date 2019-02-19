package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

abstract public class NavigationUI extends MainPageObject {

    protected static String  MY_LISTS_LINK ;
    protected static String OPEN_NAVIGATION;

    public NavigationUI (RemoteWebDriver driver) {

        super(driver);
    }

    public void openNavigation() {

        if (Platform.getInstance().isMW()) {

            this.waitForElementAndClick(OPEN_NAVIGATION, "cannot find and click Navigation button", 5);
            System.out.println("я открыл блок навигации");
        } else {

            System.out.println("Method openNavigation() does nothing from platform " + Platform.getInstance().getPlatformVar());
        }

    }

    public void clickMyLists() {

        if (Platform.getInstance().isMW()) {

            this.tryClickElementWithFewAttempt(MY_LISTS_LINK, "cannot find and click My list", 5);
            System.out.println("я открыл вотч лист");
        } else {
            this.waitForElementAndClick(
                    MY_LISTS_LINK,
                    "cannot find navigation button to My List",
                    5
            );
        }
    }
}
