package com.hatester.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.hatester.drivers.DriverManager;
import com.hatester.helpers.TestStepHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.util.HashMap;
import java.util.Map;


public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static ExtentReports extent = ExtentReportManager.getExtentReports();

    public static ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest saveToReport(String testName, String desc) {
        ExtentTest test = extent.createTest(testName, desc);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    //đính kèm hình ảnh vào report
    public static void addScreenshot(String message) {
        String base64Image = "data:image/png;base64,"
                + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);

        getTest().log(Status.INFO, message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    public static void addScreenshot(Status status, String message) {
        String base64Image = "data:image/png;base64,"
                + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);

        getTest().log(status, message,
                MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
    }

    //hàm để ghi log của report để ghi mô tả/Mô tả trong từng TCs
    public static void logMessage(String message) {
        getTest().log(Status.INFO, message);
        TestStepHelper.addStep(message);
    }

    public static void logMessage(Status status, String message) {
        getTest().log(status, message);
        TestStepHelper.addStep(message);
    }

}
