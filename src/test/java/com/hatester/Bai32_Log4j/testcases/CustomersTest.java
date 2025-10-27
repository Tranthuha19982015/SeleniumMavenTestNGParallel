package com.hatester.Bai32_Log4j.testcases;


import com.hatester.Bai32_Log4j.pages.CustomersPage;
import com.hatester.Bai32_Log4j.pages.DashboardPage;
import com.hatester.Bai32_Log4j.pages.LoginPage;
import com.hatester.common.BaseTest;
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
        customersPage.fillDataForAddNewCustomer(customerName,"hatran","USD","Vietnamese","Vietnam");
        customersPage.clickSaveButton();
        customersPage.verifyNavigateToCustomerDetailPage();
        customersPage.verifyAddNewCustomerSuccess(customerName,"hatran","USD","Vietnamese","Vietnam");
        customersPage.clickMenuCustomers();
        customersPage.searchAndCheckCustomerInTable(customerName);
    }
}
