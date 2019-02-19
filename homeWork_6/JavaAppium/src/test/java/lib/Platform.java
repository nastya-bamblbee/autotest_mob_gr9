package lib;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Platform {

    private static final String PLATFORM_ANDROID = "android";
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_MW = "mobile_web";
    private static final String APPIUM_URL = "http://127.0.0.1:4723/wd/hub";

    private static Platform instance;
    private Platform() {}

    public static Platform getInstance() {

        if (instance==null) {

            instance = new Platform();
        }
        return instance;
    }

    public RemoteWebDriver getDriver () throws Exception {

        URL URL = new URL(APPIUM_URL);

        if (this.isAndroid()) {

            return new AndroidDriver(URL, this.getAndroidDesiredCapabilities());

        } else if (this.isIOS()) {

            return new IOSDriver(URL, this.getIOSDesiredCapabilities());
        }else if (this.isMW()) {

            return new ChromeDriver(this.getMWChromeOptions());

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

    public boolean isMW() {

        return isPlatform(PLATFORM_MW);
    }

    private DesiredCapabilities getAndroidDesiredCapabilities () {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","andr90");
        capabilities.setCapability("platformVersion","9");
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

    private ChromeOptions getMWChromeOptions () {

        Map<String, Object> deviceMetrics = new HashMap<String, Object>();
        deviceMetrics.put("width", 360);
        deviceMetrics.put("height", 640);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<String, Object>();
        mobileEmulation.put("deviceMetrics",deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.109 Mobile Safari/537.36");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=340,640");

        return chromeOptions;
    }

    private boolean isPlatform (String myPlatform) {

        String  platform = this.getPlatformVar();
        return myPlatform.equals(platform);

    }

    public String getPlatformVar () {

        return System.getenv("PLATFORM");
    }
}
