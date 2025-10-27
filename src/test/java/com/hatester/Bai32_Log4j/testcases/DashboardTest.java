package com.hatester.Bai32_Log4j.testcases;


import com.hatester.Bai32_Log4j.pages.DashboardPage;
import com.hatester.Bai32_Log4j.pages.LeadsPage;
import com.hatester.Bai32_Log4j.pages.LoginPage;
import com.hatester.Bai32_Log4j.pages.ProjectsPage;
import com.hatester.common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private ProjectsPage projectsPage;
    private LeadsPage leadsPage;

    @Test
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
