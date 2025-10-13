package com.hatester.Bai26_ParallelExecution_POM.testcases;


import com.hatester.Bai26_ParallelExecution_POM.pages.CustomersPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.DashboardPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.LoginPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.ProjectsPage;
import com.hatester.common.BaseTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProjectsTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private CustomersPage customersPage;
    private ProjectsPage projectsPage;

    @Test
    public void testAddNewProject() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();

        customersPage = dashboardPage.clickMenuCustomers();
        customersPage.verifyNavigateToCustomerPage();
        String customerName = "Company HTest " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        customersPage.clickButtonNewCustomer();
        customersPage.fillDataForAddNewCustomer(customerName,"hatran","USD","Vietnamese","Vietnam");
        customersPage.clickSaveButton();
        customersPage.verifyAlertMessageSuccessDisplayed();

        projectsPage = dashboardPage.clickMenuProjects();
        String projectName = "Project Htest " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        projectsPage.verifyProjectsPageDisplay();
        projectsPage.clickAddNewProjectButton();
        projectsPage.fillDataNewProject(projectName,customerName,"Fixed Rate");
        projectsPage.clickSaveButton();
//        projectsPage.verifyAlertMessageSuccessDisplayed();
        projectsPage.clickMenuProjects();
        projectsPage.verifyAddNewProjectSuccess(projectName);
    }
}
