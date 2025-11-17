package com.hatester.Bai34_AllureReport.testcases;

import com.hatester.Bai34_AllureReport.pages.LoginPage;
import com.hatester.common.BaseTest;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.Test;

@Epic("CRM version 2.0.0")
@Feature("Login user feature")
@Story("Login with valid and invalid credentials")
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @Description("Verify user can login with valid credentials")
    @Link(name="Login ticket", url = "https://jira.anhtester.com/login/CRM-01")
    @Test(priority = 1, testName = "Login Success", description = "TC_LOGIN_CRM_01")
    public void testLoginSuccess() {
        loginPage = new LoginPage(); //Khởi tạo đối tượng LoginPage với driver lấy từ BaseTest
        loginPage.loginCRM("admin@example.com", "1234567");
        loginPage.verifyLoginSuccess();
    }

    @Description("Verify user cannot login with invalid email")
    @Link(name="Login ticket", url = "https://jira.anhtester.com/login/CRM-02")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2, testName = "Login Fail With Email Invalid", description = "TC_LOGIN_CRM_02")
    public void testLoginFailureWithEmailInvalid() {
        loginPage = new LoginPage();
        loginPage.loginCRM("12admin@example.com", "1234567");
        loginPage.verifyLoginFailureWithInvalidEmailOrPassword();
    }

    @Test(priority = 3)
    public void testLoginFailureWithPasswordInvalid() {
        loginPage = new LoginPage();
        loginPage.loginCRM("admin@example.com", "12345678");
        loginPage.verifyLoginFailureWithInvalidEmailOrPassword();
    }

    @Test(priority = 4)
    public void testLoginFailureWithEmailNull() {
        loginPage = new LoginPage();
        loginPage.loginCRM("", "123456");
        loginPage.verifyLoginFailureWithEmailNull();
    }

    @Test(priority = 5)
    public void testLoginFailureWithPasswordNull() {
//        throw new SkipException("Skipping The Test Method");
        loginPage = new LoginPage();
        loginPage.loginCRM("admin@example.com", "");
        loginPage.verifyLoginFailureWithPasswordNull();
    }
}
