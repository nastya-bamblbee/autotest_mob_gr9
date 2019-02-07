package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.IOSMyListsPageObject;

public class MyListsPageObjectFactory {

    public static MyListPageObject get(AppiumDriver  driver) {

        if (Platform.getInstance().isAndroid()) {

            return new AndroidMyListsPageObject(driver);
        } else {

            return new IOSMyListsPageObject(driver);
        }
    }
}
