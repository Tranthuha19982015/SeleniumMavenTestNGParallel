package com.hatester.Bai34_AllureReport.pages;

import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;

public class BasePage {

    //Element chung cho cac trang
    public By menuDashboard = By.xpath("//span[normalize-space()='Dashboard' and @class='menu-text']");
    public By menuCustomers = By.xpath("//span[normalize-space()='Customers' and @class='menu-text']");
    public By menuProjects = By.xpath("//span[normalize-space()='Projects' and @class='menu-text']");
    public By menuLeads = By.xpath("//span[normalize-space()='Leads' and @class='menu-text']");
    public By menuTasks = By.xpath("//span[normalize-space()='Tasks' and @class='menu-text']");
    public By iconProfile = By.xpath("//li[@class='icon header-user-profile']");
    public By optionLogout = By.xpath("//a[text()='Logout']");
    public By alertMessage = By.xpath("//div[@id='alert_float_1']/span[@class='alert-title']");

    public void logoutSystem() {
        WebUI.clickElement(iconProfile);
        WebUI.clickElement(optionLogout);

        LoginPage loginPage = new LoginPage();
        loginPage.verifyLoginPageDisplay();
    }

    //Cac phuong thuc chung cho cac trang
    public void clickMenuDashboard() {
        WebUI.clickElement(menuDashboard);
    }

    public CustomersPage clickMenuCustomers() {
        WebUI.clickElement(menuCustomers);
        return new CustomersPage();
    }

    public ProjectsPage clickMenuProjects() {
        WebUI.clickElement(menuProjects);
        return new ProjectsPage(); // Trả về đối tượng ProjectsPage sau khi click vào menu Projects
    }

    public LeadsPage clickMenuLeads() {
        WebUI.waitForPageLoaded();
        WebUI.clickElement(menuLeads);
        return new LeadsPage();
    }

    public TasksPage clickMenuTasks() {
        WebUI.waitForPageLoaded();
        WebUI.clickElement(menuTasks);
        return new TasksPage();
    }
}
