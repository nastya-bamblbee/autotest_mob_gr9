package lib.ui.mobile_web;

import lib.ui.SearchPageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWSearchPageObject extends SearchPageObject {

    static {
        SEARCH_INIT_ELEMENT = "css:button#searchIcon";
        SEARCH_INPUT = "css:form>input[type='search']";
        SEARCH_RESULT_BY_SUBSTRING_TPL = "xpath://li[contains(@class,'page-summary')][contains(@title, '{SUBSTRING}')]";
        SEARCH_CANCEL_BUTTON = "css:button.clear";
        SEARCH_RESULT = "xpath://div[contains(@class,'results-list-container')]/descendant::li[contains(@class,'page-summary')]";
        SEARCH_EMPTY_RESULT_ELEMENT = "css:p.without-results";
        SEARCH_RESULT_LIST = "xpath://div[contains(@class, 'results-list-container')]";
        //Ex18*: Рефакторинг
        SEARCH_RESULT_BY_TITLE_AND_DESCRIPTION_TPL = "xpath://*[text() ='{DESCSUBSTRING}']/../../a[@data-title='{TITLESUBSTRING}']";
    }

    public MWSearchPageObject (RemoteWebDriver driver) {

        super(driver);
    }
}

