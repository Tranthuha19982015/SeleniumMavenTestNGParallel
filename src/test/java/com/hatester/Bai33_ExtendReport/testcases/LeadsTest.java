package com.hatester.Bai33_ExtendReport.testcases;


import com.hatester.Bai33_ExtendReport.pages.DashboardPage;
import com.hatester.Bai33_ExtendReport.pages.LeadsPage;
import com.hatester.Bai33_ExtendReport.pages.LoginPage;
import com.hatester.Bai33_ExtendReport.pages.TasksPage;
import com.hatester.common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LeadsTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private LeadsPage leadsPage;
    private TasksPage tasksPage;

    @Test
    public void testAddNewLead() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        leadsPage = dashboardPage.clickMenuLeads();

        String leadsName = "Leads Htest " + System.currentTimeMillis();
        leadsPage.clickIconLeadsSummary();
        leadsPage.verifyNavigateToLeadPage();
        leadsPage.clickButtonNewLead();
        leadsPage.verifyOpenWindowAddNewLead();
        leadsPage.fillDataAddNewLead(leadsName);
        leadsPage.clickSaveButton();
        leadsPage.clickButtonCloseAfterAdd();
        leadsPage.searchAndCheckLeadInTable(leadsName);
    }

    @Test
    public void testStatusOnTableWithLeadsSummaryAfterAddSuccess() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        leadsPage = dashboardPage.clickMenuLeads();

        String leadsName = "Leads Htest " + System.currentTimeMillis();

        leadsPage.clickIconLeadsSummary();
        leadsPage.verifyNavigateToLeadPage();

        //get total lead status in leads summary
        String beforeTotalActive = leadsPage.getTotalStatusActiveLeads();
        String beforeTotalCustomer = leadsPage.getTotalStatusCustomerLeads();

        //Add new lead
        leadsPage.clickButtonNewLead();
        leadsPage.verifyOpenWindowAddNewLead();
        //fill data
        leadsPage.fillDataAddNewLead(leadsName);
        leadsPage.clickSaveButton();

        //dong pop-up
        leadsPage.clickButtonCloseAfterAdd();
        //verify add lead success
        leadsPage.searchAndCheckLeadInTable(leadsName);
        String statusOfFisrtRowItem = leadsPage.getFirstRowItemLeadStatus();

        leadsPage.clickMenuLeads();
        leadsPage.clickIconLeadsSummary();

        //get total lead status in leads summary
        String afterTotalActive = leadsPage.getTotalStatusActiveLeads();
        String afterTotalCustomer = leadsPage.getTotalStatusCustomerLeads();

        leadsPage.verifyAfterAddingNewLead(Integer.parseInt(beforeTotalActive), Integer.parseInt(beforeTotalCustomer),
                Integer.parseInt(afterTotalActive), Integer.parseInt(afterTotalCustomer), statusOfFisrtRowItem);
    }

    @Test
    public void testTotalStatusOnTableWithLeadsSummary() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        leadsPage = dashboardPage.clickMenuLeads();

        leadsPage.clickIconLeadsSummary();
        leadsPage.verifyNavigateToLeadPage();

        //get total active status in leads summary
        int totalActiveOnLeadsSummary = Integer.parseInt(leadsPage.getTotalStatusActiveLeads());
        int totalActiveOnTable = leadsPage.countActiveStatusOnTable();
        Assert.assertEquals(totalActiveOnTable, totalActiveOnLeadsSummary,
                "Total active status leads on summary does not match with total active status leads on table.");

        //get total customer status in leads summary
        int totalCustomerOnLeadsSummary = Integer.parseInt(leadsPage.getTotalStatusCustomerLeads());
        int totalCustomerOnTable = leadsPage.countCustomerStatusOnTable();
        Assert.assertEquals(totalCustomerOnTable, totalCustomerOnLeadsSummary,
                "Total customer status leads on summary does not match with total customer status leads on table.");
    }

    @Test
    public void testLeadValueInTaskAfterAddingSuccessfully() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        leadsPage = dashboardPage.clickMenuLeads();

        String leadsName = "Leads Htest " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        leadsPage.clickIconLeadsSummary();
        leadsPage.verifyNavigateToLeadPage();
        //Add new lead
        leadsPage.clickButtonNewLead();
        leadsPage.verifyOpenWindowAddNewLead();
        //fill data
        leadsPage.fillDataAddNewLead(leadsName);
        leadsPage.clickSaveButton();
        //dong pop-up
        leadsPage.clickButtonCloseAfterAdd();
        //verify add lead success
        leadsPage.searchAndCheckLeadInTable(leadsName);
        //get Lead Name, Lead Email
        String email = leadsPage.getFirstRowItemLeadEmail();

        //Navigate to Tasks page
        tasksPage = leadsPage.clickMenuTasks();
        //Verify that the tasks page is displayed
        tasksPage.verifyTasksPageDisplay();
        //Verify that the add new task page is displayed
        tasksPage.clickButtonNewTask();
        tasksPage.verifyAddNewTaskPageDisplay();
        //Verify lead after successful addition is displayed on the new task screen
        tasksPage.chooseLeadFollowingRelatedTo(leadsName);
        String valueLeadDisplayOnTask = tasksPage.getValueLeadInTask();
        System.out.println("Lead value is displayed on the Add New Task form: " + valueLeadDisplayOnTask);

        //Assert that the lead name and email are displayed correctly in the task creation form
        Assert.assertEquals(valueLeadDisplayOnTask, leadsName + " - " + email, "Lead value on the New Task does not match.");
    }
}
