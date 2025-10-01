package Bai29_DataProvider;

import com.hatester.Bai26_ParallelExecution_POM.pages.LoginPage;
import com.hatester.common.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DemoDataProvider extends BaseTest {
    @DataProvider(name = "loginData1")
    public Object[][] getDataLogin1() {
        return new Object[][]{
                {"admin@example.com", 123456},
                {"admin1@example.com", 12345}
        };
    }

    @Test(dataProvider = "loginData1")
    public void testLogin1(String email, int password) {
        System.out.println("Email: " + email + " | Password: " + password);
    }

    @DataProvider(name = "loginData2")
    public Object[][] getDataLogin2() {
        return new Object[][]{
                {"admin@example.com", 123456, "Admin"},
                {"admin1@example.com", 12345, "User"},
                {"customer@example.com", 12345, "Customer"}
        };
    }

    @Test(dataProvider = "loginData2")
    public void testLogin2(String email, int password, String role) {
        System.out.println("Email: " + email + " | Password: " + password + " | Role: " + role);
        LoginPage loginPage = new LoginPage();
        loginPage.loginCRM(email,String.valueOf(password));
    }
}
