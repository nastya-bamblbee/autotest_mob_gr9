package lib.ui;

import io.appium.java_client.AppiumDriver;
import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

abstract public class MyListPageObject extends MainPageObject {

    protected static String FOLDER_NAME_TPL;
    protected static String ARTICLE_TITLE_TPL;
    protected static String REMOVE_FROM_SAVED_BUTTON;
    protected static String ARTICLE_IN_WATCHLIST_LIST;

    public MyListPageObject(RemoteWebDriver driver) {

        super(driver);
    }

    private static String getFolderName(String nameOfFolder) {

        return FOLDER_NAME_TPL.replace("{FOLDER_NAME}", nameOfFolder);
    }

    private static String getArticleTitle(String articleTitle) {

        return ARTICLE_TITLE_TPL.replace("{TITLE}", articleTitle);
    }

    private static String getRemoveButtonByTitle(String articleTitle) {

        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", articleTitle);
    }

    private static String getArticleInWatchlist( String title) {

        return ARTICLE_IN_WATCHLIST_LIST.replace("{TITLE}", title);
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

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {

            this.swipeElementToLeft(
                    articleTitleXpath,
                    "Cannot find saved article"
            );
        } else {

            String removeLocator = getRemoveButtonByTitle(articleTitle);
            driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);
            this.waitForElementAndClick(removeLocator, "cannot find and click button to remove article from saved", 10);
            System.out.println("я удалил статью из вотчлист");

        }

        if (Platform.getInstance().isIOS()) {

            this.clickToElementToTheRightUpperCorner(articleTitleXpath, "cannot find  end click to saved article");
        }

        if (Platform.getInstance().isMW()) {

            driver.navigate().refresh();
            driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);
            driver.navigate().refresh();
            System.out.println("я обновил страницу");
        }

        this.waitForArticleToDisappear(articleTitle);


    }

    public void waitForArticleToDisappear(String articleTitle) {

        if (Platform.getInstance().isAndroid() || Platform.getInstance().isIOS()) {
        String articleTitleXpath = getArticleTitle(articleTitle);
        this.waitForElementNotPresent(
                articleTitleXpath,
                "cannot delete saved article",
                15
        ); } else {
            //Ex17: Рефакторинг
            this.assertRemoveArticleFromWatchlist(articleTitle);
        }
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


            String fullLabel = waitForElementPresent(articleTitleXpath, "cannot find article " + articleTitle, 10).getText();
            String label = fullLabel.split("\n")[0];
            return label;
        }

    }

    //Ex17: Рефакторинг
    public void assertRemoveArticleFromWatchlist(String title) {

        String titleXpath = getArticleInWatchlist(title);

        this.waitForElementNotPresent(titleXpath, "deleted article found in watchlist", 5);
        System.out.println("я проверил. статьи с заголовком " + title + "нет в вотчлисте");

    }
}
