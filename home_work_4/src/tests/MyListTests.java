package tests;

import lib.CoreTestCase;
import lib.ui.ArticlePageObject;
import lib.ui.MyListPageObject;
import lib.ui.NavigationUI;
import lib.ui.SearchPageObject;
import org.junit.Test;

public class MyListTests extends CoreTestCase {

    @Test
    public void testSaveFirstArticleToMyList () {

        SearchPageObject SearchPageObject = new  SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String searchValue = "Java";
        String titleSearchArticle = "Java (programming language)";
        SearchPageObject.typeSearchLine(searchValue);
        SearchPageObject.clickByArticleWithSubstring(titleSearchArticle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String articleTitle = ArticlePageObject.getArticleTitle();

        String nameOfFolder = "My first reading list";
        ArticlePageObject.addArticleToMyList(nameOfFolder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();

        MyListPageObject MyListPageObject = new MyListPageObject(driver);
        MyListPageObject.openFolderByName(nameOfFolder);
        MyListPageObject.swipeByArticleForDelete(articleTitle);

    }

    @Test //Ex5: Тест: сохранение двух статей
    public void testSaveTwoArticle () {
        //поиск и добавление в список первой статьи
        SearchPageObject  SearchPageObject = new  SearchPageObject(driver);
        SearchPageObject.initSearchInput();
        String searchValueFirstArticle = "Java";
        String titleSearchFirstArticle = "Java (programming language)";
        SearchPageObject.typeSearchLine(searchValueFirstArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchFirstArticle);

        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        ArticlePageObject.waitForTitleElement();
        String titleDeletedArticle = ArticlePageObject.getArticleTitle();

        String nameOfFolder = "My first reading list";
        ArticlePageObject.addArticleToMyList(nameOfFolder);
        ArticlePageObject.closeArticle();

        //поиск и добавление второй статьи в список
        SearchPageObject.initSearchInput();
        String searchValueSecondArticle = "Appium";
        String titleSearchSecondArticle = "Appium";
        SearchPageObject.typeSearchLine(searchValueSecondArticle);
        SearchPageObject.clickByArticleWithSubstring(titleSearchSecondArticle);

        ArticlePageObject.waitForTitleElement();
        String titleSavedArticle = ArticlePageObject.getArticleTitle();
        ArticlePageObject.addNewArticleToMyList(nameOfFolder);
        ArticlePageObject.closeArticle();

        NavigationUI NavigationUI = new NavigationUI(driver);
        NavigationUI.clickMyLists();


        MyListPageObject MyListPageObject = new MyListPageObject(driver);
        MyListPageObject.openFolderByName(nameOfFolder);

        //удаление одной из статей
        MyListPageObject.swipeByArticleForDelete(titleDeletedArticle);
        //проверка что статьи нет
        MyListPageObject.waitForArticleToDisappear(titleDeletedArticle);


        MyListPageObject.waitForArticleToAppear(titleSavedArticle);
        String titleArticleInFolder = MyListPageObject.getTitleArticleInFolder(titleSavedArticle);//тайтл из папки

        MyListPageObject.clickArticle(titleArticleInFolder);// открываем статью

        String titleOfArticle = ArticlePageObject.getArticleTitle();//получем тайтл статьи

        //проверка что заголовок в списке и в статье совпадает
        assertEquals("title article in folder and title article not equals", titleArticleInFolder, titleOfArticle);
    }
}
