package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Common {

    /**
     * Создает и возвращает AndroidDriver
     * можно передать параметры при запуске теста
     *  platformVersion (-Dplatform=...), deviceName (-Ddevice=...)
     * @return AndroidDriver
     * @throws Exception
     */
    public static AndroidDriver getDriver() throws Exception {
        String AppiumUrl = " http://0.0.0.0:4723/wd/hub";
        String projectPath = System.getProperty("user.dir");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", System.getProperty("platform"));
        capabilities.setCapability("deviceName", System.getProperty("device"));
        capabilities.setCapability("AutomationName", "Appium");
        capabilities.setCapability("appPackage", "com.downdogapp");
        capabilities.setCapability("appActivity", ".AppActivity");
        capabilities.setCapability("app", projectPath + "\\apk\\com.downdogapp_224_apps.evozi.com.apk");
//        capabilities.setCapability("app", projectPath + "\\apk_new\\com.downdogapp_226_apps.evozi.com.apk");

         return new AndroidDriver(new URL(AppiumUrl), capabilities);
    }

    /**
     * Ожидает видимости элемента и возвращает его
     * @param driver
     * @param by
     * @param error_message
     * @param timeOutInSecond
     * @return WebElement
     */
    public static WebElement waitForElement(AppiumDriver driver, By by, String error_message, long timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");

        return wait.until(
//                ExpectedConditions.presenceOfElementLocated(by)
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    /**
     * Ожидает видимости элемента
     * @param driver
     * @param by
     * @param error_message
     * @param timeOutInSecond
     */
    public static void waitForElementPresent(AppiumDriver driver, By by, String error_message, long timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    /**
     * Выполняет один свайп вверх
     * @param driver
     */
    public static void swipeUp(AppiumDriver driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width/2;
        int start_y = (int) (size.height*0.8);
        int end_y = (int) (size.height*0.4);

        action
                .press(PointOption.point(x, start_y))
                .waitAction()
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    /**
     * Выполняет один свайп вниз
     * @param driver
     */
    public static void swipeDown(AppiumDriver driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width/2;
        int start_y = (int) (size.height*0.4);
        int end_y = (int) (size.height*0.8);

        action
                .press(PointOption.point(x, start_y))
                .waitAction()
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    /**
     * Прокручивает страницу до нужного элемента и кликает по нему
     * @param driver
     * @param by
     * @param error_message
     * @param max_swipes
     */
    public static void scrollToElementAndClick(AppiumDriver driver, By by, String error_message, int max_swipes) {
        int count = 1;

        if (driver.findElements(by).size() == 0) {
            swipeDown(driver);
            swipeDown(driver);
        }

        while (driver.findElements(by).size() == 0) {
            if (count > max_swipes) {
                waitForElement(driver, by, "Закончились свайпы. " + error_message, 5);
            }
            swipeUp(driver);
            ++count;
        }
        waitForElement(driver, by, "Элемент не найден. " + error_message, 5).click();
    }

    /**
     * Создает скриншот и сохраняет его в указанную папку
     * @param driver
     */
    public static void getScreenshot(AppiumDriver driver) {
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        LocalDateTime now = LocalDateTime.now();
        String path = System.getProperty("user.dir") + "\\screenshot\\" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".jpg";
        System.out.println(path);
        try {
            FileUtils.copyFile(scrFile, new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
