package com.hatester.Bai32_Log4j.testcases;

import com.hatester.Bai32_Log4j.pages.LoginPage;
import com.hatester.common.BaseTest;
import com.hatester.utils.LogUtils;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @Test(priority = 1)
    public void testLoginSuccess() {
        loginPage = new LoginPage(); //Khởi tạo đối tượng LoginPage với driver lấy từ BaseTest
        loginPage.loginCRM("admin@example.com", "1234567");
        loginPage.verifyLoginSuccess();
    }

    @Test(priority = 2)
    public void testLoginFailureWithEmailInvalid() {
        loginPage = new LoginPage();
        loginPage.loginCRM("12admin@example.com", "123456");
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
        throw new SkipException("Skipping The Test Method");
//        loginPage = new LoginPage();
//        loginPage.loginCRM("admin@example.com", "");
//        loginPage.verifyLoginFailureWithPasswordNull();
    }
}
