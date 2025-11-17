package com.hatester.listeners;

import com.aventstack.extentreports.Status;
import com.hatester.helpers.*;
import com.hatester.reports.AllureManager;
import com.hatester.reports.ExtentReportManager;
import com.hatester.reports.ExtentTestManager;
import com.hatester.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.List;

public class TestListener implements ITestListener {

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("Setup môi trường onStart: " + result.getStartDate());  //thời gian bắt đầu
        //Initialize report
        //Connect to database
        //Call API get Token
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("Kết thúc bộ test: " + result.getEndDate()); //thời gian kết thúc
        //Generate report
        //Kết thúc và thực thi Extents Report
        ExtentReportManager.getExtentReports().flush();
        //Send email
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Test Started: " + result.getName());
        //Write log to file

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            CaptureHelper.startRecord(result.getName());
        }
        //Bắt đầu ghi 1 TCs mới vào Extent Report
        ExtentTestManager.saveToReport(getTestName(result), getTestDescription(result));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case " + result.getName() + " is passed.");
//        LogUtils.info("==> Status: " + result.getStatus());
        //Write log to file
        //Write status to report

        //Extent Report
        if (PropertiesHelper.getValue("SCREENSHOT_SUCCESS").equals("true")) {
            ExtentTestManager.logMessage(Status.PASS, result.getName() + " is passed.");
        }

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("Test case " + result.getName() + " is failed.");
//        LogUtils.info("==> Status: " + result.getStatus());

        LogUtils.error("==> Reason: " + result.getThrowable()); //Lấy lý do lỗi
        CaptureHelper.takeScreenshot(result.getName() + "_" + SystemHelper.getDateTimeNowFormat()); //Lấy tên TCs làm tên hình ảnh

        //Create ticket on Jira
        if (PropertiesHelper.getValue("CREATE_TICKET_JIRA").equals("true")) {

            // Lấy các step
            List<String> steps = TestStepHelper.getSteps();
            Throwable cause = result.getThrowable();
            String message = (cause != null) ? cause.getMessage() : "Unknown failure";
            // Lưu Actual Result
            TestStepHelper.setActualResult(message);

            JiraNodoHelper.createJiraTicket(result.getName(), steps.toArray(new String[0]));
            System.out.println("Jira ticket created for failed test: " + result.getName());
            // Xoá steps sau khi dùng
            TestStepHelper.clear();
        }
        //Write log to file
        //Write status to report

        //Extent Report
        if (PropertiesHelper.getValue("SCREENSHOT_FAILURE").equals("true")) {
            ExtentTestManager.addScreenshot(result.getName());
            ExtentTestManager.logMessage(Status.FAIL, result.getThrowable().toString());
            ExtentTestManager.logMessage(Status.FAIL, result.getName() + " is failed.");
        }
        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test case " + result.getName() + " is skipped.");
        //Write log to file
        //Write status to report

        //Extent Report
        ExtentTestManager.logMessage(Status.SKIP, result.getThrowable().toString());

        if (PropertiesHelper.getValue("VIDEO_RECORD").equals("true")) {
            CaptureHelper.stopRecord();
        }
    }
}
