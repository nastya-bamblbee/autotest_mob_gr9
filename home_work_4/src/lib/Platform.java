package lib;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class Platform {

    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;
    private Platform() {}

    public static Platform getInstance() {

        if (instance==null) {

            instance = new Platform();
        }
        return instance;
    }

    public AppiumDriver getDriver () throws Exception {

        URL URL = new URL(APPIUM_URL);

        if (this.isAndroid()) {

            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());

        } else if (this.isIOS()) {

            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        }else {

            throw new Exception("cannot detected type of the Driver. Platform value " + this.getPlatformVar());
        }
    }

    public boolean isAndroid () {

        return isPlatform(PLATFORM_ANDROID);
    }

    public boolean isIOS () {

        return isPlatform(PLATFORM_IOS);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities () {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","7.1");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackege","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("orientation", "PORTRAIT"); //Ex7*: Поворот экрана. каждый новый тест будет начинаться с портретной ориентации.
        capabilities.setCapability("app","/Users/anastasiya/Documents/autotest_les/home_work_4/apks/org.wikipedia.apk");

        return capabilities;
    }

    private DesiredCapabilities getIOSDesiredCapabilities () {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("deviceName","iPhone 6");
        capabilities.setCapability("platformVersion","11.4");
        capabilities.setCapability("orientation", "PORTRAIT"); //Ex7*: Поворот экрана. каждый новый тест будет начинаться с портретной ориентации.
        capabilities.setCapability("app","/Users/anastasiya/Documents/autotest_les/home_work_4/apks/Wikipedia.app");

        return capabilities;
    }

    private boolean isPlatform (String myPlatform) {

        String  platform = this.getPlatformVar();
        return myPlatform.equals(platform);

    }

    private String getPlatformVar () {

        return System.getenv("PLATFORM");
    }
}
