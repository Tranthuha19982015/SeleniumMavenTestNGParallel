package com.hatester.Bai30_TakeScreenshot_RecordVideo;

import com.hatester.Bai26_ParallelExecution_POM.pages.DashboardPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.LeadsPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.LoginPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.TasksPage;
import com.hatester.common.BaseTest;
import com.hatester.drivers.DriverManager;
import com.hatester.helpers.CaptureHelper;
import com.hatester.helpers.PropertiesHelper;
import com.hatester.helpers.SystemHelper;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DemoRecordVideo extends BaseTest {

    @Test
    public void testLoginRecordVideo(Method method) {
        CaptureHelper.startRecord(method.getName());
        LoginPage loginPage = new LoginPage(); //Khởi tạo đối tượng LoginPage với driver lấy từ BaseTest

        loginPage.loginCRM("admin@example.com", "123456");
        loginPage.verifyLoginSuccess();
    }

    @Test
    public void testAddNewLead(Method method) {
        CaptureHelper.startRecord(method.getName());

        LoginPage loginPage;
        DashboardPage dashboardPage;
        LeadsPage leadsPage;
        TasksPage tasksPage;

        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        leadsPage = dashboardPage.clickMenuLeads();

        String leadsName = "Leads Htest " + SystemHelper.getCurrentDatetime();
        leadsPage.clickIconLeadsSummary();
        leadsPage.verifyNavigateToLeadPage();
        leadsPage.clickButtonNewLead();
        leadsPage.verifyOpenWindowAddNewLead();
        leadsPage.fillDataAddNewLead(leadsName);
        leadsPage.clickSaveButton();
        leadsPage.clickButtonCloseAfterAdd();
        leadsPage.searchAndCheckLeadInTable(leadsName);
    }
}
