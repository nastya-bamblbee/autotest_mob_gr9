package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListPageObject extends MainPageObject {

    public static final String FOLDER_NAME_TPL = "//*[@text= '{FOLDER_NAME}']";
    public static final String ARTICLE_TITLE_TPL = "//*[@text='{TITLE}']";

    public MyListPageObject(AppiumDriver driver) {

        super(driver);
    }

    private static String getFolderName(String nameOfFolder) {

        return FOLDER_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getArticleTitle(String articleTitle) {

        return ARTICLE_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    public void openFolderByName(String nameOfFolder) {


        String folderNameXpath = getFolderName(nameOfFolder);
        this.waitForElementPresent(By.xpath(folderNameXpath),"cannot find  folder by name " + nameOfFolder);
        this.waitForElementAndClick(
                By.xpath(folderNameXpath),
                "cannot find  folder by name " + nameOfFolder,
                5
        );
    }

    public void swipeByArticleForDelete(String articleTitle) {

        this.waitForArticleToAppear(articleTitle);
        String articleTitleXpath = getArticleTitle(articleTitle);
        this.swipeElementToLeft(
                By.xpath(articleTitleXpath),
                "Cannot find saved article"
        );
        this.waitForArticleToDisappear(articleTitle);
    }

    public void waitForArticleToDisappear(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementNotPresent(
                By.xpath(articleTitleXpath),
                "cannot delete saved article",
                15
        );
    }


    public void waitForArticleToAppear(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementPresent(
                By.xpath(articleTitleXpath),
                "cannot find saved article",
                15
        );
    }

    public void clickArticle(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementAndClick(By.xpath(articleTitleXpath), "cannot find article " + articleTitle, 5);

    }

    public String getTitleArticleInFolder(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        return this.waitForElementPresent(By.xpath(articleTitleXpath), "cannot find article " + articleTitle, 10).getText();
    }
}
