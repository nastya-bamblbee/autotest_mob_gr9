package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListPageObject;

public class AndroidMyListsPageObject extends MyListPageObject {

    static {

        FOLDER_NAME_TPL = "xpath://*[@text= '{FOLDER_NAME}']";
        ARTICLE_TITLE_TPL = "xpath://*[@text='{TITLE}']";

    }

    public AndroidMyListsPageObject(AppiumDriver driver) {

        super(driver);
    }
}
