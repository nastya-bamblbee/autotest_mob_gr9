package lib.ui.mobile_web;

import lib.ui.ArticlePageObject;
import org.openqa.selenium.remote.RemoteWebDriver;

public class MWArticlePageObject extends ArticlePageObject {

    static {

        TITLE = "css:#content h1";
        FOOTER_ELEMENT = "css:footer";
        OPTIONS_ADD_TO_LIST_BUTTON = "css:#page-actions li[title='Watch this page']";
        CLOSE_ARTICLE_BUTTON = "css:button#searchIcon";
        LIST_OF_LISTS = "id:org.wikipedia:id/list_of_lists";
        OPTIONS_REMOVE_FROM_MY_LIST_BUTTON = "css:#page-actions li[title='Stop watching']";

    }

    public MWArticlePageObject (RemoteWebDriver driver) {

        super(driver);
    }
}
