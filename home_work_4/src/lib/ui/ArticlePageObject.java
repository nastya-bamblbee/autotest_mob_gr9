package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.WebElement;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String TITLE;
    protected static  String FOOTER_ELEMENT;
    protected static  String OPTIONS_BUTTON;
    protected static  String OPTIONS_ADD_TO_LIST_BUTTON;
    protected static  String ADD_TO_LIST_OVERLAY;
    protected static  String LIST_NAME_INPUT;
    protected static  String LIST_OK_BUTTON;
    protected static  String CLOSE_ARTICLE_BUTTON;
    protected static  String LIST_OF_LISTS ;
    protected static  String FOLDER_NAME_IN_LIST_TPL;

    public ArticlePageObject (AppiumDriver driver) {

        super(driver);
    }
    private static String getFolderName(String nameOfFolder) {

        return FOLDER_NAME_IN_LIST_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getTitle (String title) {

        return TITLE.replace("{TITLE}", title);
    }

    public WebElement waitForTitleElement() {

        return this.waitForElementPresent(TITLE, "cannot title of article", 15);
    }

    public WebElement waitForTitleElementForIOS (String title) {

        String titleID = getTitle(title);
        return this.waitForElementPresent(titleID,"cannot find title of article", 15);
    }

    public String getArticleTitleForIOS (String title) {

        WebElement titleElement = waitForTitleElementForIOS(title);
        return titleElement.getAttribute("name");
    }

    public String getArticleTitle() {

        WebElement titleElement = waitForTitleElement();

        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {

        if (Platform.getInstance().isAndroid()) {

            this.swipeUpToFindElement(FOOTER_ELEMENT, "cannot find footer", 40);

        } else {

            this.swipeUpTitleElementAppear(FOOTER_ELEMENT,"cannot find footer",40);
        }

    }

    public void addArticleToMyList(String nameOfFolder) {

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to option",
                5
        );

        this.waitForElementPresent(OPTIONS_ADD_TO_LIST_BUTTON,"Cannot find option to add article to reading list");
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                ADD_TO_LIST_OVERLAY,
                "cannot find GOT IT",
                5
        );
        this.waitForElementAndClear(
               LIST_NAME_INPUT,
                "cannot find input to set name of article folder",
                5
        );
        this.waitForElementAndSendKeys(
                LIST_NAME_INPUT,
                nameOfFolder,
                "cannot put text",
                5
        );
        this.waitForElementAndClick(
                LIST_OK_BUTTON,
                "cannot find button OK",
                5
        );
    }

    public void addNewArticleToMyList (String nameOfFolder) {

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to option",
                5
        );
        this.waitForElementPresent(OPTIONS_ADD_TO_LIST_BUTTON,"Cannot find option to add article to reading list");
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementPresent(LIST_OF_LISTS, "cannot find list of lists",10);

        String folderNameXpath = getFolderName(nameOfFolder);
        this.waitForElementAndClick(folderNameXpath, "cannot found folder " + nameOfFolder, 10);

    }

    public void closeArticle() {

        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "cannot find button X (close)",
                5
        );
    }

    public void assertTitlePresence() {

        this.assertElementPresent(TITLE, "cannot find element 'title' on page");
    }

    public void assertTitlePresenceForIOS (String  title) {

        String titleID = getTitle(title);
        System.out.println(titleID);
        this.assertElementPresent(titleID, "cannot find element 'title' on page");
    }

    public void addArticlesToMySaved() {

        this.waitForElementAndClick(OPTIONS_ADD_TO_LIST_BUTTON, "cannot find and click to Sve to later button", 5);
    }
}
