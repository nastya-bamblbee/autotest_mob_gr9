package lib.ui.mobile_web;

import lib.ui.MyListPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWMyListPageObject extends MyListPageObject {

    static {

        ARTICLE_TITLE_TPL = "xpath://*[@id='mw-content-text']/ul[contains(@class, 'watchlist')]//h3[contains(text(), '{TITLE}')]";
        REMOVE_FROM_SAVED_BUTTON = "xpath://*[@id='mw-content-text']/ul[contains(@class, 'watchlist')]//h3[contains(text(), '{TITLE}')]/../../div[contains(@title,'Stop watching')]";
        ARTICLE_IN_WATCHLIST_LIST = "xpath://ul[contains(@class, 'watchlist')]/li[contains(@title, '{TITLE}')]";
    }

    public MWMyListPageObject(RemoteWebDriver driver) {
        super(driver);
    }
}
