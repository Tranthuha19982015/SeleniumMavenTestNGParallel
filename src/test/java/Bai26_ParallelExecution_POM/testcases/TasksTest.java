package Bai26_ParallelExecution_POM.testcases;


import Bai26_ParallelExecution_POM.pages.DashboardPage;
import Bai26_ParallelExecution_POM.pages.LeadsPage;
import Bai26_ParallelExecution_POM.pages.LoginPage;
import Bai26_ParallelExecution_POM.pages.TasksPage;
import common.BaseTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksTest extends BaseTest {
    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private LeadsPage leadsPage;
    private TasksPage tasksPage;

    @Test
    public void testAddNewTask() {
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
        leadsPage.verifyAleartMessageSuccessDisplayed();
        leadsPage.clickButtonCloseAfterAdd();
        leadsPage.searchAndCheckLeadInTable(leadsName);

        tasksPage = leadsPage.clickMenuTasks();

        String taskName = "Tasks Htest " + System.currentTimeMillis();
        tasksPage.verifyTasksPageDisplay();
        tasksPage.clickButtonNewTask();
        tasksPage.verifyAddNewTaskPageDisplay();
        tasksPage.fillDataAddNewTask(taskName, leadsName);
        tasksPage.clickButtonSave();
        tasksPage.verifyAlertMessageSuccessDisplayed();
        tasksPage.clickIconCloseTaskDetail();
        tasksPage.verifyTaskListVisibleAfterClosingTaskDetailPopup();
        tasksPage.searchAndCheckTaskInTable(taskName);
    }

    @Test
    public void testTaskDisplayedOnLeadAfterAddSuccess() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        leadsPage = dashboardPage.clickMenuLeads();

        String leadsName = "Leads Htest " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        leadsPage.clickIconLeadsSummary();
        leadsPage.verifyNavigateToLeadPage();
        leadsPage.clickButtonNewLead();
        leadsPage.verifyOpenWindowAddNewLead();
        leadsPage.fillDataAddNewLead(leadsName);
        leadsPage.clickSaveButton();
        leadsPage.verifyAleartMessageSuccessDisplayed();
        leadsPage.clickButtonCloseAfterAdd();
        leadsPage.verifyLeadListVisibleAfterClosingLeadDetailPopup();
        leadsPage.searchAndCheckLeadInTable(leadsName);

        tasksPage = leadsPage.clickMenuTasks();

        List<String> taskNames = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            String taskName = String.format("Tasks Htest " + i + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
            tasksPage.verifyTasksPageDisplay();
            tasksPage.clickButtonNewTask();
            tasksPage.verifyAddNewTaskPageDisplay();
            tasksPage.fillDataAddNewTask(taskName, leadsName);
            tasksPage.clickButtonSave();
            tasksPage.verifyAlertMessageSuccessDisplayed();
            tasksPage.clickIconCloseTaskDetail();
            tasksPage.verifyTaskListVisibleAfterClosingTaskDetailPopup();
            tasksPage.searchAndCheckTaskInTable(taskName);
            taskNames.add(taskName);
        }

        leadsPage = tasksPage.clickMenuLeads();
        leadsPage.searchAndCheckLeadInTable(leadsName);
        leadsPage.clickFirstIteamName();
        leadsPage.clickTaskTabOnLeadDetailPage();
        for (String name : taskNames) {
            leadsPage.searchNewTaskInTaskTabOnLeadDetailPage(name);
        }
    }
}
