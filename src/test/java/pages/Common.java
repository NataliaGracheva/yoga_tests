package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Common {

    // универсальный метод поиска элемента для работы с разными локаторами
    public static WebElement waitForElement(AppiumDriver driver, By by, String error_message, long timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");

        return wait.until(
//                ExpectedConditions.presenceOfElementLocated(by)
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    // метод, который проверяет что элемент есть на странице (экране)
    public static void waitForElementPresent(AppiumDriver driver, By by, String error_message, long timeOutInSecond) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSecond);
        wait.withMessage(error_message + "\n");

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(by)
        );
    }

    // метод для свайпа вверх
    public static void swipeUp(AppiumDriver driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width/2;
        int start_y = (int) (size.height*0.8);
//        int end_y = (int) (size.height*0.2);
        int end_y = (int) (size.height*0.4);

        action
                .press(PointOption.point(x, start_y))
                .waitAction()
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }

    // метод для свайпа вниз
    public static void swipeDown(AppiumDriver driver) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();

        int x = size.width/2;
//        int start_y = (int) (size.height*0.2);
        int start_y = (int) (size.height*0.4);
        int end_y = (int) (size.height*0.8);

        action
                .press(PointOption.point(x, start_y))
                .waitAction()
                .moveTo(PointOption.point(x, end_y))
                .release()
                .perform();
    }


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
        Common.getScreenshot(driver);
        waitForElement(driver, by, "Элемент не найден. " + error_message, 5).click();
    }

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
