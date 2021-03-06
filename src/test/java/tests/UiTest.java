package tests;

import io.appium.java_client.AppiumDriver;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.*;
import utils.Common;
import utils.TestListener;

@ExtendWith(TestListener.class)
public class UiTest {

    public AppiumDriver driver;
    private StartPage startPage;

    @BeforeEach
    public void setUp() throws Exception {
        driver = Common.getDriver();
        startPage = new StartPage(driver);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/userPreferences1.csv", numLinesToSkip = 1)
    @DisplayName("Должен установить все параметры пользователя")
    public void setNewUserPreferences(String experience, String instructions, String music, String yogaType,
                                          String pace, String focusOn, String shavasanaTime, String practiceTime) {
        System.out.println(experience + ", " + instructions + ", " + music + ", " + yogaType + ", " +
                pace + ", " + focusOn + ", " + shavasanaTime + ", " + practiceTime);
        UserPreferences userPreferences = startPage.clickStart();
        AuthPage authPage = userPreferences.setPreferences(experience, instructions, music, yogaType,
                pace, focusOn, shavasanaTime, practiceTime);
        authPage.checkHeader();
    }

    @Test
    @DisplayName("Должен пропустить установку всех параметров")
    public void skipAllOptionsWithTakingScreenshots() {
        UserPreferences userPreferences = startPage.clickStart();
        AuthPage authPage = userPreferences.skipAllOptionsWithTakingScreenshots(8);
        authPage.checkHeader();
    }

    @Test
    @DisplayName("Должен вновь открыть стартовую страницу после повторного запуска")
    public void closingAndLaunching() {
        startPage.clickStart();
        driver.closeApp();
        driver.launchApp();
        startPage = new StartPage(driver);
        startPage.checkHeader();
    }

    @Test
    @DisplayName("Должен перейти к предыдущей странице при нажатии на кнопку Назад")
    public void goBack() {
        UserPreferences userPreferences = startPage.clickStart();
        userPreferences.goBack("Какой у Вас опыт в йоге?", "Добро пожаловать!");
    }

    @Test
    @DisplayName("Должен быть проигнорирован")
    @Disabled
    public void shouldBeSkipped() {
        System.out.println("Должен быть проигнорирован");
    }

//    @AfterEach
//    public void tearDown(){
//        driver.quit();
//    }
}
