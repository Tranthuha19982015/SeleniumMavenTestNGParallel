package com.hatester.Bai30_TakeScreenshot_RecordVideo;

import com.hatester.common.BaseTest;
import com.hatester.drivers.DriverManager;
import com.hatester.helpers.CaptureHelper;
import com.hatester.helpers.SystemHelper;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DemoTakeScreenshot extends BaseTest {

    @Test
    public void testScreenshot() {
        WebUI.openURL("https://anhtester.com");
        Assert.assertEquals(DriverManager.getDriver().getTitle(), "Anh Tester Automation Testing");
        CaptureHelper.takeScreenshot("IMG_HomePage");
        WebUI.clickElement(By.xpath("//a[@id='btn-login']"));
        WebUI.waitForPageLoaded();

        CaptureHelper.takeScreenshot("IMG_LoginPage");
        System.out.println("Screenshot success !!");
    }

    @Test
    public void testScreenshot2(Method method) {
        WebUI.openURL("https://anhtester.com");
        Assert.assertEquals(DriverManager.getDriver().getTitle(), "Anh Tester Automation Testing");
        CaptureHelper.takeScreenshot("IMG_HomePage");
        WebUI.clickElement(By.xpath("//a[@id='btn-login']"));
        WebUI.waitForPageLoaded();

        CaptureHelper.takeScreenshot(method.getName() + SystemHelper.getCurrentDatetime());
        System.out.println("Screenshot success !!");
    }
}
