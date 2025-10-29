package com.hatester.listeners;

import com.hatester.helpers.CaptureHelper;
import com.hatester.helpers.SystemHelper;
import com.hatester.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static int count_total = 0;
    private static int count_passed = 0;
    private static int count_failed = 0;
    private static int count_skipped = 0;

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
        LogUtils.info("Total testcase: " + count_total);
        LogUtils.info("Total testcase pass: " + count_passed);
        LogUtils.info("Total testcase fail: " + count_failed);
        LogUtils.info("Total testcase skip: " + count_skipped);
        //Generate report
        //Send email
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Test Started: " + result.getName());
        count_total++;
        //Write log to file
        CaptureHelper.startRecord(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case " + result.getName() + " is passed.");
//        LogUtils.info("==> Status: " + result.getStatus());
        count_passed++;
        //Write log to file
        //Write status to report
        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("Test case " + result.getName() + " is failed.");
//        LogUtils.info("==> Status: " + result.getStatus());
        count_failed++;
        LogUtils.error("==> Reason: " + result.getThrowable()); //Lấy lý do lỗi
        CaptureHelper.takeScreenshot(result.getName() + "_" + SystemHelper.getDateTimeNowFormat()); //Lấy tên TCs làm tên hình ảnh
        //Create ticket on Jira
        //Write log to file
        //Write status to report
        CaptureHelper.stopRecord();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test case " + result.getName() + " is skipped.");
//        LogUtils.info("==> Status: " + result.getStatus());
        count_skipped++;
        //Write log to file
        //Write status to report
        CaptureHelper.stopRecord();
    }
}
