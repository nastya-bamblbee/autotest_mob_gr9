package lib.ui.mobile_web;

import lib.ui.NavigationUI;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWNavigationUI extends NavigationUI {


    static {

        MY_LISTS_LINK = "css:a[data-event-name='watchlist']";
        OPEN_NAVIGATION = "css:form[class = 'header'] a[title = 'Open main menu']";
    }

    public MWNavigationUI(RemoteWebDriver driver) {

        super(driver);
    }
}
