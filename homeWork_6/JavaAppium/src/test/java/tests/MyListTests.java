package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.*;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class MyListTests extends CoreTestCase {

    private static String nameOfFolder = "My first reading list";

    @Test
    public void testSaveFirstArticleToMyList () {



        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Java";
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.clickByArticleWithSubstring(titleSearchArticle);
        

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        String articleTitle = ArticlePageObject.getArticleTitle();

        if (Platform.getInstance().isAndroid()) {

            ArticlePageObject.addArticleToMyList(nameOfFolder);
        } else {

            ArticlePageObject.addArticlesToMySaved();
        }

        if (Platform.getInstance().isMW()) {

            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData("test_of_wiki", "qaz1wsx2");
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("we are not on the same page after login",articleTitle, ArticlePageObject.getArticleTitle());

            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        driver.manage().timeouts().pageLoadTimeout(10000, TimeUnit.MILLISECONDS);

        MyListPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {

            MyListPageObject.openFolderByName(nameOfFolder);
        }

        MyListPageObject.swipeByArticleForDelete(articleTitle);

    }

    @Test //Ex5: Тест: сохранение двух статей
    public void testSaveTwoArticle () {

        String nameOfFolder = "My first reading list";
        String titleDeletedArticle;
        String titleSavedArticle;
        String titleOfArticle;
        //поиск и добавление в список первой статьи
        SearchPageObject  SearchPageObject = SearchPageObjectFactory.get(driver);
        SearchPageObject.initSearchInput();
        String searchValueFirstArticle = "Java";
        String titleSearchFirstArticle = "Java (programming language)";
        SearchPageObject.typeSearchLine(searchValueFirstArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchFirstArticle);
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isIOS()) {

            ArticlePageObject.waitForTitleElementForIOS(titleSearchFirstArticle);
            titleDeletedArticle = ArticlePageObject.getArticleTitleForIOS(titleSearchFirstArticle);
            ArticlePageObject.addArticlesToMySaved();

        } else if (Platform.getInstance().isAndroid()){

            ArticlePageObject.waitForTitleElement();
            titleDeletedArticle = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticleToMyList(nameOfFolder);
        } else {

            ArticlePageObject.waitForTitleElement();
            titleDeletedArticle = ArticlePageObject.getArticleTitle();
            driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
            ArticlePageObject.addArticlesToMySaved();

            AuthorizationPageObject Auth = new AuthorizationPageObject(driver);
            Auth.clickAuthButton();
            Auth.enterLoginData("test_of_wiki", "qaz1wsx2");
            Auth.submitForm();

            ArticlePageObject.waitForTitleElement();

            assertEquals("we are not on the same page after login",titleDeletedArticle, ArticlePageObject.getArticleTitle());
            driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
            ArticlePageObject.addArticlesToMySaved();
        }

        ArticlePageObject.closeArticle();

        //поиск и добавление второй статьи в список
        SearchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS()) {

            SearchPageObject.clickCancelSearch();
        }
//
        String searchValueSecondArticle = "Appium";
        String titleSearchSecondArticle = "Appium";
        SearchPageObject.typeSearchLine(searchValueSecondArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchSecondArticle);
        driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);

        if (Platform.getInstance().isIOS()) {

            ArticlePageObject.waitForTitleElementForIOS(titleSearchSecondArticle);
            titleSavedArticle = ArticlePageObject.getArticleTitleForIOS(titleSearchSecondArticle);
            ArticlePageObject.addArticlesToMySaved();
        } else if (Platform.getInstance().isAndroid()){

            ArticlePageObject.waitForTitleElement();
            titleSavedArticle = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addNewArticleToMyList(nameOfFolder);
        }else {

            driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
            ArticlePageObject.waitForTitleElement();
            titleSavedArticle = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticlesToMySaved();

        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.openNavigation();
        NavigationUI.clickMyLists();
        driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);


        MyListPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {

            MyListPageObject.openFolderByName(nameOfFolder);
        }

        //удаление одной из статей
        MyListPageObject.swipeByArticleForDelete(titleDeletedArticle);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {

        MyListPageObject.waitForArticleToAppear(titleSavedArticle);
        String titleArticleInFolder = MyListPageObject.getTitleArticleInFolder(titleSavedArticle);//тайтл из папки
        System.out.println("titleArticleInFolder is " + titleArticleInFolder);

        MyListPageObject.clickArticle(titleArticleInFolder);// открываем статью

        if (Platform.getInstance().isIOS()) {

            titleOfArticle = ArticlePageObject.getArticleTitleForIOS(titleSearchSecondArticle);//получем тайтл статьи
            System.out.println("titleOfArticle is " + titleOfArticle);
        } else  {

            titleOfArticle = ArticlePageObject.getArticleTitle();
        }
        //проверка что заголовок в списке и в статье совпадает
        assertEquals("titleOfArticle article in folder and title article not equals", titleArticleInFolder, titleOfArticle);
        }
    }
}
