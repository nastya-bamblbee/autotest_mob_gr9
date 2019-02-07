package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;

abstract public class MyListPageObject extends MainPageObject {

    protected static String FOLDER_NAME_TPL;
    protected static String ARTICLE_TITLE_TPL;

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

        if (Platform.getInstance().isIOS()) {

            this.clickToElementToTheRightUpperCorner(articleTitleXpath, "cannot find  end click to saved article");
        }
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

        if (Platform.getInstance().isAndroid()) {

            return this.waitForElementPresent(articleTitleXpath, "cannot find article " + articleTitle, 10).getText();
        } else {


            //дописать. разбить лейбл на две части, взять первую, сравнить с нейм в статье
            String fullLabel = waitForElementPresent(articleTitleXpath, "cannot find article " + articleTitle, 10).getText();
            String label = fullLabel.split("\n")[0];
            return label;
        }

    }




}
