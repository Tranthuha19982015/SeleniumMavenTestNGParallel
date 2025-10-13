package com.hatester.Bai29_DataProvider;

import com.hatester.Bai26_ParallelExecution_POM.pages.CustomersPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.DashboardPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.LoginPage;
import com.hatester.common.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoDataProvider extends BaseTest {

    @Test(dataProvider = "loginData1", dataProviderClass = DataProviderFactory.class)
    public void testLogin1(String email, int password) {
        System.out.println("Email: " + email + " | Password: " + password);
    }


    @Test(dataProvider = "loginData2", dataProviderClass = DataProviderFactory.class)
    public void testLogin2(String email, int password, String role) {
        System.out.println("Email: " + email + " | Password: " + password + " | Role: " + role);

    }

    @Test(dataProvider = "loginData2", dataProviderClass = DataProviderFactory.class)
    public void testLogin3(String email, int password, String role) {
        System.out.println("Email: " + email + " | Password: " + password + " | Role: " + role);
        LoginPage loginPage = new LoginPage();
        loginPage.loginCRM(email, String.valueOf(password));
        loginPage.verifyLoginSuccess();
    }

    @Test(dataProvider = "AddNewCustomerData", dataProviderClass = DataProviderFactory.class)
    public void testAddNewCustomer(String customerName, String group, String currency, String language, String country) {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = loginPage.loginCRM();
        CustomersPage customersPage = dashboardPage.clickMenuCustomers();

        customersPage.verifyNavigateToCustomerPage();
        customersPage.clickButtonNewCustomer();
        customersPage.fillDataForAddNewCustomer(customerName, group, currency, language, country);
        customersPage.clickSaveButton();
        customersPage.verifyNavigateToCustomerDetailPage();
        customersPage.verifyAddNewCustomerSuccess(customerName, group, currency, language, country);
        customersPage.clickMenuCustomers();
        customersPage.searchAndCheckCustomerInTable(customerName);
    }
}
