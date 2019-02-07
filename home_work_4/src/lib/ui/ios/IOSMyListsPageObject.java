package lib.ui.ios;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class IOSMyListsPageObject extends MyListPageObject {

    static {

        ARTICLE_TITLE_TPL = "xpath://XCUIElementTypeLink[contains(@name,'{TITLE}')]";
    }

    public IOSMyListsPageObject(AppiumDriver driver) {

        super(driver);
    }
}
