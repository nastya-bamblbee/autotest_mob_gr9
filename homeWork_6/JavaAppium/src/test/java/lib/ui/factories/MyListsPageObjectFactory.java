package lib.ui.factories;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import lib.ui.MyListPageObject;
import lib.ui.android.AndroidMyListsPageObject;
import lib.ui.ios.IOSMyListsPageObject;
import lib.ui.mobile_web.MWMyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MyListsPageObjectFactory {

    public static MyListPageObject get(RemoteWebDriver driver) {

        if (Platform.getInstance().isAndroid()) {

            return new AndroidMyListsPageObject(driver);
        } else if (Platform.getInstance().isIOS()){

            return new IOSMyListsPageObject(driver);
        }else {

            return new MWMyListPageObject(driver);
        }
    }
}
