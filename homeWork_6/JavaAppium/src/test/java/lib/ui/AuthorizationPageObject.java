package lib.ui;

import org.openqa.selenium.remote.RemoteWebDriver;

public class AuthorizationPageObject extends  MainPageObject {

    private static final String LOGIN_BUTTON = "xpath://body/div/a[text() = 'Log in']";
    private static final String LOGIN_INPUT = "css:input[name='wpName']";
    private static final String PASSWORD_INPUT = "css:input[name='wpPassword']";
    private static final String SUBMIT_BUTTON = "css:button#wpLoginAttempt";

    public AuthorizationPageObject (RemoteWebDriver driver) {

        super(driver);
    }

    public void clickAuthButton () {

        this.waitForElementPresent(LOGIN_BUTTON, "cannot find auth button", 5);
        this.waitForElementAndClick(LOGIN_BUTTON, "cannot find and click auth button", 5);

    }

    public void enterLoginData (String login, String password) {

        this.waitForElementAndSendKeys(LOGIN_INPUT, login, "cannot find and put login", 5);
        this.waitForElementAndSendKeys(PASSWORD_INPUT, password, "cannot find and put password", 5 );

    }

    public void submitForm () {

        this.waitForElementAndClick(SUBMIT_BUTTON, "cannot find and click submit button",5);
    }
}
