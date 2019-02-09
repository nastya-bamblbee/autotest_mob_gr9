package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.ArticlePageObject;

public class AndroidArticlePageObject extends ArticlePageObject {

    static {

        TITLE = "id:org.wikipedia:id/view_page_title_text";
        FOOTER_ELEMENT = "xpath://*[@text='View page in browser']";
        OPTIONS_BUTTON = "xpath://android.widget.ImageView[@content-desc='More options']";
        OPTIONS_ADD_TO_LIST_BUTTON = "xpath://*[@text='Add to reading list']";
        ADD_TO_LIST_OVERLAY = "id:org.wikipedia:id/onboarding_button";
        LIST_NAME_INPUT = "id:org.wikipedia:id/text_input";
        LIST_OK_BUTTON = "xpath://*[@text='OK']";
        CLOSE_ARTICLE_BUTTON = "xpath://android.widget.ImageButton[@content-desc='Navigate up']";
        LIST_OF_LISTS = "id:org.wikipedia:id/list_of_lists";
        FOLDER_NAME_IN_LIST_TPL = "xpath://*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']";

    }

    public AndroidArticlePageObject (AppiumDriver driver) {

        super(driver);
    }
}
