package com.hatester.Bai31_ITestListener.pages;

import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.testng.Assert;

public class DashboardPage extends BasePage {

    private By buttonDashboardOptions = By.xpath("//div[@class='screen-options-btn']");
    private By labelTotalProjectsInprogress = By.xpath("(//span[normalize-space()='Projects In Progress']/parent::div)/following-sibling::span");
    private By labelTotalConvertedLeads = By.xpath("((//span[normalize-space()='Converted Leads'])/parent::div)/following-sibling::span");

    public void verifyDashboardPageDisplay() {
        boolean isDashboardDisplayed = WebUI.checkElementExist(buttonDashboardOptions);
        Assert.assertTrue(isDashboardDisplayed, "Dashboard page is not displayed");
    }

    //Cách 1
    public String getTotalProjectsInProgress() {
        return WebUI.getElementText(labelTotalProjectsInprogress);
    }

    public String getTotalConvertedLeads() {
        return WebUI.getElementText(labelTotalConvertedLeads);
    }

    //Cách 2
//    public void verifyTotalProjectsInprogress() {
//        String totalProjectsInProgressOnDashboard = driver.findElement(labelTotalProjectsInprogress).getText();
//        System.out.println("Total Projects In Progress: " + totalProjectsInProgressOnDashboard);
//
//        clickMenuProjects();
//
//        ProjectsPage projectsPage = new ProjectsPage(driver);
//
//        Assert.assertEquals(totalProjectsInProgressOnDashboard, projectsPage.getTotalInProgress() + " / " + projectsPage.getTotalProjects(),
//                "Total Projects In Progress on Dashboard does not match with Projects Page");
//    }

}
