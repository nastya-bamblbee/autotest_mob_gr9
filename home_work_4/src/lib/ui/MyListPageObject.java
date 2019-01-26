package lib.ui;

import io.appium.java_client.AppiumDriver;

public class MyListPageObject extends MainPageObject {

    public static final String FOLDER_NAME_TPL = "xpath://*[@text= '{FOLDER_NAME}']";
    public static final String ARTICLE_TITLE_TPL = "xpath://*[@text='{TITLE}']";

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
        this.waitForElementPresent(folderNameXpath,"cannot find  folder by name " + nameOfFolder);
        this.waitForElementAndClick(
                folderNameXpath,
                "cannot find  folder by name " + nameOfFolder,
                5
        );
    }

    public void swipeByArticleForDelete(String articleTitle) {

        this.waitForArticleToAppear(articleTitle);
        String articleTitleXpath = getArticleTitle(articleTitle);
        this.swipeElementToLeft(
                articleTitleXpath,
                "Cannot find saved article"
        );
        this.waitForArticleToDisappear(articleTitle);
    }

    public void waitForArticleToDisappear(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementNotPresent(
                articleTitleXpath,
                "cannot delete saved article",
                15
        );
    }


    public void waitForArticleToAppear(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementPresent(
                articleTitleXpath,
                "cannot find saved article",
                15
        );
    }

    public void clickArticle(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementAndClick(articleTitleXpath, "cannot find article " + articleTitle, 5);

    }

    public String getTitleArticleInFolder(String articleTitle) {

        String articleTitleXpath = getArticleTitle(articleTitle);
        return this.waitForElementPresent(articleTitleXpath, "cannot find article " + articleTitle, 10).getText();
    }
}
