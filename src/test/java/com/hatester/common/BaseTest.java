package com.hatester.common;

import com.hatester.drivers.DriverManager;
import com.hatester.helpers.CaptureHelper;
import com.hatester.helpers.PropertiesHelper;
import com.hatester.helpers.SystemHelper;
import com.hatester.listeners.TestListener;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static com.hatester.constants.FrameworkConstant.*;

@Listeners(TestListener.class)
public class BaseTest {
    public SoftAssert softAssert;

    @BeforeSuite
    public void setupBeforeSuite() {
        PropertiesHelper.loadAllFiles();
    }

    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chrome") String browserName) {
        WebDriver driver;

        //Nếu properties có truyền browser thì ưu tiên lấy browser từ đây, k có thì lấy từ file xml, nếu xml không truyền thì lấy từ Optional
        if (BROWSER == null || BROWSER.isEmpty()) {
            browserName = browserName;
        } else {
            browserName = BROWSER;
        }

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                LogUtils.info("Launching Chrome browser...");

                ChromeOptions options = new ChromeOptions();
                if (HEADLESS.equalsIgnoreCase("true")) {
                    options.addArguments("--headless=new");  //chạy headless
                    options.addArguments("--window-size=" + WINDOW_SIZE); // set kích thước
                }
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                LogUtils.info("Launching Firefox browser...");
                driver = new FirefoxDriver();
                break;
            case "edge":
                LogUtils.info("Launching Edge browser...");
                driver = new EdgeDriver();
                break;
            default:
                LogUtils.info("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
        }

        DriverManager.setDriver(driver);
        if (HEADLESS.equalsIgnoreCase("false")) {
            LogUtils.info("Headless: " + HEADLESS);
            DriverManager.getDriver().manage().window().maximize();
        }
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void closeDriver(ITestResult result) {
        //quit driver
        if (DriverManager.getDriver() != null) {
            DriverManager.quit();
        }
        softAssert.assertAll(); // Bắt buộc phải gọi assertAll() để kiểm tra tất cả các assert đã được thực hiện
    }
}
