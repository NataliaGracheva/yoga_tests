import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.StartPage;

import java.net.URL;

public class UiTest {

    public AppiumDriver driver;
    public String AppiumUrl = " http://0.0.0.0:4723/wd/hub";

    private StartPage startPage;

    @BeforeEach
    public void setUp() throws Exception {
        String projectPath = System.getProperty("user.dir");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "7.1.1");
        capabilities.setCapability("deviceName", "ZC554KL");
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("appPackage", "com.downdogapp");
        capabilities.setCapability("appActivity", ".AppActivity");
        capabilities.setCapability("app", projectPath + "\\apk\\com.downdogapp_224_apps.evozi.com.apk");

        driver = new AndroidDriver(new URL(AppiumUrl), capabilities);
        startPage = new StartPage(driver);
    }

    @Test
    public void testClosingAndLaunching() {
        startPage.clickStart();
        driver.closeApp();
        driver.launchApp();
        startPage = new StartPage(driver);
        startPage.checkHeader();
    }

    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
