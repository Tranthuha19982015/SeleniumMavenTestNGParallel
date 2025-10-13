package com.hatester.Bai29_DataProvider;

import com.hatester.Bai26_ParallelExecution_POM.pages.CustomersPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.DashboardPage;
import com.hatester.Bai26_ParallelExecution_POM.pages.LoginPage;
import com.hatester.common.BaseTest;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class DemoDataProviderFromExcel extends BaseTest {

    @Test(dataProvider = "DataLoginFromExcel", dataProviderClass = DataProviderFactory.class)
    public void testLoginCRMFromExcel(String email, String password) {
        System.out.println("Email: " + email + " | Password: " + password);
        LoginPage loginPage = new LoginPage();
        loginPage.loginCRM(email, String.valueOf(password));
        loginPage.verifyLoginSuccess();
    }

    @Test(dataProvider = "DataLoginFromExcelHashtable", dataProviderClass = DataProviderFactory.class)
    public void testLoginCRMFromExcelHashtable(Hashtable<String,String> data) {
        System.out.println("Email: " + data.get("EMAIL") + " | Password: " + data.get("PASSWORD"));
        LoginPage loginPage = new LoginPage();
        loginPage.loginCRM(data.get("EMAIL"), String.valueOf(data.get("PASSWORD")));
        loginPage.verifyLoginSuccess();
    }
}
