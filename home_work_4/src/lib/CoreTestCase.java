package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";

    @Override
    protected void setUp() throws Exception
    {
        super.setUp();

        DesiredCapabilities capabilities = this.getCapabilitiesByPlatformEnv();
        driver = getDriverByPlatformEnv(capabilities); // драйвер в зависимости от платформы
    }


    @Override
    protected void tearDown() throws Exception
    {

        driver.quit();

        super.tearDown();
    }

    protected  void rotateScreenPortrait () {

        driver.rotate(ScreenOrientation.PORTRAIT);
    }
    protected  void rotateScreenLandscape () {

        driver.rotate(ScreenOrientation.LANDSCAPE);
    }

    protected void backgroundApp (int seconds) {

        driver.runAppInBackground(seconds);
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv () throws Exception {

        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {

            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            capabilities.setCapability("platformVersion","7.1");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackege","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("orientation", "PORTRAIT"); //Ex7*: Поворот экрана. каждый новый тест будет начинаться с портретной ориентации.
            capabilities.setCapability("app","/Users/anastasiya/Documents/autotest_les/home_work_4/apks/org.wikipedia.apk");

        } else if (platform.equals(PLATFORM_IOS)) {

            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone 6");
            capabilities.setCapability("platformVersion","11.4");
            capabilities.setCapability("orientation", "PORTRAIT"); //Ex7*: Поворот экрана. каждый новый тест будет начинаться с портретной ориентации.
            capabilities.setCapability("app","/Users/anastasiya/Documents/autotest_les/home_work_4/apks/Wikipedia.app");

        } else {

            throw  new Exception("cannot get run platform from env variable. Platform value:" + platform);
        }

        return capabilities;
    }

    private AppiumDriver getDriverByPlatformEnv(DesiredCapabilities capabilities) throws Exception{

        String platform = System.getenv("PLATFORM");

        if (platform.equals(PLATFORM_ANDROID)) {

            driver = new AndroidDriver(new URL(AppiumURL), capabilities);

        } else if (platform.equals(PLATFORM_IOS)) {

            driver = new IOSDriver(new URL(AppiumURL), capabilities);

        } else {

            throw  new Exception("cannot get run platform from env variable. Platform value:" + platform);
        }
        return driver;
    }

}
