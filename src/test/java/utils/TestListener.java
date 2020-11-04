package utils;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.lang.reflect.Field;
import java.util.Optional;

public class TestListener implements TestWatcher {

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        try {
        getDriverFromContext(context).quit();
        System.out.println("Test was disabled");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        try {
            getDriverFromContext(context).quit();
            System.out.println("Test was successful");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        try {
            getDriverFromContext(context).quit();
            System.out.println("Test was aborted");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable throwable) {
        try {
            AppiumDriver driver = getDriverFromContext(context);

            byte[] srcFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            saveScreenshot(srcFile);
            driver.quit();
            System.out.println("Test was failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public byte[] saveScreenshot(byte[] screenShot) {
        return screenShot;
    }

    private AppiumDriver getDriverFromContext(ExtensionContext context) throws NoSuchFieldException, IllegalAccessException {
        Field field = context.getRequiredTestInstance().getClass().getDeclaredField("driver");
        field.setAccessible(true);
        AppiumDriver driver = (AppiumDriver) field.get(context.getRequiredTestInstance());
        return driver;
    }
}
