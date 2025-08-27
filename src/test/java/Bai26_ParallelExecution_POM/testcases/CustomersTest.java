package Bai26_ParallelExecution_POM.testcases;


import Bai26_ParallelExecution_POM.pages.CustomersPage;
import Bai26_ParallelExecution_POM.pages.DashboardPage;
import Bai26_ParallelExecution_POM.pages.LoginPage;
import common.BaseTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomersTest extends BaseTest {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    CustomersPage customersPage;

    @Test
    public void testAddNewCustomer() {
        loginPage = new LoginPage();
        dashboardPage = loginPage.loginCRM();
        customersPage = dashboardPage.clickMenuCustomers();

        String customerName = "Company HTest " + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        customersPage.verifyNavigateToCustomerPage();
        customersPage.clickButtonNewCustomer();
        customersPage.fillDataForAddNewCustomer(customerName);
        customersPage.clickSaveButton();
        customersPage.verifyNavigateToCustomerDetailPage();
        customersPage.verifyAddNewCustomerSuccess(customerName);
        customersPage.clickMenuCustomers();
        customersPage.searchAndCheckCustomerInTable(customerName);
    }
}
