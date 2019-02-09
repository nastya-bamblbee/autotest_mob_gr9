package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.MyListsPageObjectFactory;
import lib.ui.factories.NavigationUIFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

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

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();

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

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);

        if (Platform.getInstance().isIOS()) {

            ArticlePageObject.waitForTitleElementForIOS(titleSearchFirstArticle);
            titleDeletedArticle = ArticlePageObject.getArticleTitleForIOS(titleSearchFirstArticle);
            ArticlePageObject.addArticlesToMySaved();

        } else {

            ArticlePageObject.waitForTitleElement();
            titleDeletedArticle = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addArticleToMyList(nameOfFolder);
        }

        ArticlePageObject.closeArticle();

        //поиск и добавление второй статьи в список
        SearchPageObject.initSearchInput();

        if (Platform.getInstance().isIOS()) {

            SearchPageObject.clickCancelSearch();
        }

        String searchValueSecondArticle = "Appium";
        String titleSearchSecondArticle = "Appium";
        SearchPageObject.typeSearchLine(searchValueSecondArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchSecondArticle);

        if (Platform.getInstance().isIOS()) {

            ArticlePageObject.waitForTitleElementForIOS(titleSearchSecondArticle);
            titleSavedArticle = ArticlePageObject.getArticleTitleForIOS(titleSearchSecondArticle);
            ArticlePageObject.addArticlesToMySaved();
        } else {

            ArticlePageObject.waitForTitleElement();
            titleSavedArticle = ArticlePageObject.getArticleTitle();
            ArticlePageObject.addNewArticleToMyList(nameOfFolder);
        }

        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = NavigationUIFactory.get(driver);
        NavigationUI.clickMyLists();


        MyListPageObject MyListPageObject = MyListsPageObjectFactory.get(driver);

        if (Platform.getInstance().isAndroid()) {

            MyListPageObject.openFolderByName(nameOfFolder);
        }

        //удаление одной из статей
        MyListPageObject.swipeByArticleForDelete(titleDeletedArticle);


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
        assertEquals("title article in folder and title article not equals", titleArticleInFolder, titleOfArticle);
    }
}
