package com.hatester.listeners;

import com.hatester.helpers.CaptureHelper;
import com.hatester.helpers.SystemHelper;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    static int count_total = 0;
    static int count_passed = 0;
    static int count_failed = 0;
    static int count_skipped = 0;

    @Override
    public void onStart(ITestContext result) {
        System.out.println("Setup môi trường onStart: " + result.getStartDate());
        //Initialize report
        //Connect to database
        //Call API get Token
    }

    @Override
    public void onFinish(ITestContext result) {
        System.out.println("Kết thúc bộ test: " + result.getEndDate());
        System.out.println("Total testcase: " + count_total);
        System.out.println("Total testcase pass: " + count_passed);
        System.out.println("Total testcase fail: " + count_failed);
        System.out.println("Total testcase skip: " + count_skipped);
        //Generate report
        //Send email
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("Bắt đầu chạy test case: " + result.getName());
        count_total++;
        //Write log to file
        CaptureHelper.startRecord(result.getName()+ SystemHelper.getCurrentDatetime());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("Test case " + result.getName() + " is passed.");
        System.out.println("==> Status: " + result.getStatus());
        count_passed++;
        //Write log to file
        //Write status to report
        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("Test case " + result.getName() + " is failed.");
        System.out.println("==> Status: " + result.getStatus());
        count_failed++;
        System.out.println("==> Reason: " + result.getThrowable()); //Lấy lý do lỗi
        CaptureHelper.takeScreenshot(result.getName() +SystemHelper.getCurrentDatetime()); //Lấy tên TCs làm tên hình ảnh
        //Create ticket on Jira
        //Write log to file
        //Write status to report
        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("Test case " + result.getName() + " is skipped.");
        System.out.println("==> Status: " + result.getStatus());
        count_skipped++;
        //Write log to file
        //Write status to report
        CaptureHelper.stopRecord();
    }
}
