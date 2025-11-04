package com.hatester.Bai34_AllureReport.testcases;


import com.hatester.Bai34_AllureReport.pages.DashboardPage;
import com.hatester.Bai34_AllureReport.pages.LeadsPage;
import com.hatester.Bai34_AllureReport.pages.LoginPage;
import com.hatester.Bai34_AllureReport.pages.ProjectsPage;
import com.hatester.common.BaseTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Link;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("CRM version 2.0.0")
@Feature("Dashboard feature")
@Story("Verify labels on Dashboard")
public class DashboardTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ProjectsPage projectsPage;
    private LeadsPage leadsPage;

    @Link(name="Dashboard ticket", url = "https://jira.anhtester.com/dashboard/CRM-06")
    @Test(priority = 1, description = "TC_DASHBOARD_CRM_01")
    public void testLabelProjectsInProgressOnDashboard() {
        loginPage = new LoginPage();
//        loginPage.loginCRM("admin@example.com", "123456");
//        loginPage.verifyLoginSuccess();
//
//        dashboardPage = new DashboardPage(driver);

        dashboardPage = loginPage.loginCRM(); // login và trả về đối tượng DashboardPage: new DashboardPage(driver);

        dashboardPage.verifyDashboardPageDisplay();

        String totalProjectsInProgressOnDashboard = dashboardPage.getTotalProjectsInProgress();
        System.out.println("Total Projects In Progress: " + totalProjectsInProgressOnDashboard);

//        dashboardPage.clickMenuProjects(); // Chuyển sang trang Projects

        projectsPage = dashboardPage.clickMenuProjects();

        Assert.assertEquals(totalProjectsInProgressOnDashboard, projectsPage.getTotalInProgress() + " / " + projectsPage.getTotalProjects(),
                "Total Projects In Progress on Dashboard does not match with Projects Page");
//        dashboardPage.verifyTotalProjectsInprogress(); //cách 2
    }

    @Test
    public void testLabelConvertedLeadsOnDashboard() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        dashboardPage.verifyDashboardPageDisplay();

        String totalConvertedLeadsOnDashboard = dashboardPage.getTotalConvertedLeads();
        System.out.println("Total Converted Leads: " + totalConvertedLeadsOnDashboard);

        leadsPage = dashboardPage.clickMenuLeads();
        leadsPage.clickIconLeadsSummary();

        Assert.assertEquals(totalConvertedLeadsOnDashboard, leadsPage.getTotalStatusCustomerLeads() + " / " + leadsPage.getTotalLeads(), "Total Converted Leads on Dashboard does not match with Leads Page");
    }
}
