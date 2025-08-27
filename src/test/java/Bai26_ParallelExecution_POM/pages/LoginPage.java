package Bai26_ParallelExecution_POM.pages;

import driver.DriverManager;
import keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginPage extends BasePage {

    //Khai báo URL của trang Login
    private String urlLoginAdmin = "https://crm.anhtester.com/admin/authentication";

    //Khai báo các đối tượng element thuộc về trang Login (khai báo những element cần thiết cho TCs sau này)
    private By headerLoginPage = By.xpath("//h1[normalize-space()='Login']");
    private By inputEmail = By.xpath("//input[@id='email']");
    private By inputPassword = By.xpath("//input[@id='password']");
    private By buttonLogin = By.xpath("//button[normalize-space()='Login']");
    private By checkboxRememberMe = By.xpath("//input[@id='remember']");
    private By linkForgotPassword = By.xpath("//a[normalize-space()='Forgot Password?']");
    private By errorMessageInvalid = By.xpath("//div[@id='alerts']/div[contains(text(),'Invalid email or password')]");
    private By errorMessageRequiredEmail = By.xpath("//div[normalize-space()='The Email Address field is required.']");
    private By errorMessageRequiredPassword = By.xpath("//div[normalize-space()='The Password field is required.']");

    //Khai báo các hàm xử lý nội bộ trong nội bộ trang Login

    public void verifyLoginPageDisplay() {
        boolean check = WebUI.checkElementExist(headerLoginPage);
        Assert.assertTrue(check, "Login page is not displayed.");
    }

    public void navigateToLoginAdminCRM() {
        WebUI.openURL(urlLoginAdmin);
        WebUI.waitForPageLoaded();
    }

    private void enterEmail(String email) {
        WebUI.setText(inputEmail, email);
    }

    private void enterPassword(String password) {
        WebUI.setText(inputPassword, password);
    }

    private void clickLoginButton() {
        WebUI.clickElement(buttonLogin);
    }

    public void loginCRM(String email, String password) { //Chỉ dùng nội bộ trang Login
        navigateToLoginAdminCRM();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        WebUI.waitForPageLoaded();
    }

    public DashboardPage loginCRM() { //Dùng để liên kết trang
        navigateToLoginAdminCRM();
        enterEmail("admin@example.com");
        enterPassword("123456");
        clickLoginButton();
        WebUI.waitForPageLoaded();
        verifyLoginSuccess();

        return new DashboardPage(); //Trả về đối tượng DashboardPage sau khi đăng nhập thành công
    }

    public void verifyLoginSuccess() {
        boolean check = WebUI.checkElementExist(menuDashboard);
        Assert.assertTrue(check, "Login failed or Dashboard not displayed.");
    }

    public void verifyLoginFailureWithInvalidEmailOrPassword() {
        boolean check = WebUI.checkElementExist(errorMessageInvalid);
        Assert.assertTrue(check, "Error message for invalid email not displayed.");
    }

    public void verifyLoginFailureWithEmailNull() {
        boolean check = WebUI.checkElementExist(errorMessageRequiredEmail);
        Assert.assertTrue(check, "Error message for required email not displayed.");
    }

    public void verifyLoginFailureWithPasswordNull() {
        boolean check = WebUI.checkElementExist(errorMessageRequiredPassword);
        Assert.assertTrue(check, "Error message for required password not displayed.");
    }
}
