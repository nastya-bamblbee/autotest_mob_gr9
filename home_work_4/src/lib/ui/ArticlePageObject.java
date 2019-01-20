package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String TITLE = "org.wikipedia:id/view_page_title_text";
    private static final String FOOTER_ELEMENT = "//*[@text='View page in browser']";
    private static final String OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']";
    private static final String OPTIONS_ADD_TO_LIST_BUTTON = "//*[@text='Add to reading list']";
    private static final String ADD_TO_LIST_OVERLAY = "org.wikipedia:id/onboarding_button";
    private static final String LIST_NAME_INPUT = "org.wikipedia:id/text_input";
    private static final String LIST_OK_BUTTON = "//*[@text='OK']";
    private static final String CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";
    private static final String LIST_OF_LISTS = "org.wikipedia:id/list_of_lists";
    private static final String FOLDER_NAME_IN_LIST_TPL = "//*[@resource-id='org.wikipedia:id/item_title'][@text='{FOLDER_NAME}']";

    public ArticlePageObject (AppiumDriver driver) {

        super(driver);
    }
    private static String getFolderName(String nameOfFolder) {

        return FOLDER_NAME_IN_LIST_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    public WebElement waitForTitleElement() {

        return this.waitForElementPresent(By.id(TITLE), "cannot title of article", 15);
    }

    public String getArticleTitle() {

        WebElement titleElement = waitForTitleElement();
        return titleElement.getAttribute("text");
    }

    public void swipeToFooter() {

        this.swipeUpToFindElement(By.xpath(FOOTER_ELEMENT), "cannot find footer", 15);
    }

    public void addArticleToMyList(String nameOfFolder) {

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to option",
                5
        );

        this.waitForElementPresent(By.xpath(OPTIONS_ADD_TO_LIST_BUTTON),"Cannot find option to add article to reading list");
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_LIST_OVERLAY),
                "cannot find GOT IT",
                5
        );
        this.waitForElementAndClear(
                By.id(LIST_NAME_INPUT),
                "cannot find input to set name of article folder",
                5
        );
        this.waitForElementAndSendKeys(
                By.id(LIST_NAME_INPUT),
                nameOfFolder,
                "cannot put text",
                5
        );
        this.waitForElementAndClick(
                By.xpath(LIST_OK_BUTTON),
                "cannot find button OK",
                5
        );
    }

    public void addNewArticleToMyList (String nameOfFolder) {

        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to option",
                5
        );
        this.waitForElementPresent(By.xpath(OPTIONS_ADD_TO_LIST_BUTTON),"Cannot find option to add article to reading list");
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                5
        );

        this.waitForElementPresent(By.id(LIST_OF_LISTS), "cannot find list of lists",10);

        String folderNameXpath = getFolderName(nameOfFolder);
        this.waitForElementAndClick(By.xpath(folderNameXpath), "cannot found folder " + nameOfFolder, 10);

    }

    public void closeArticle() {

        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "cannot find button X (close)",
                5
        );
    }

    public void assertTitlePresence() {

        this.assertElementPresent(By.id(TITLE), "cannot find element 'title' on page");
    }
}
