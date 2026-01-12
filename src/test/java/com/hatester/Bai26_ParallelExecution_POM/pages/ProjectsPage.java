package com.hatester.Bai26_ParallelExecution_POM.pages;

import com.hatester.drivers.DriverManager;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class ProjectsPage extends BasePage {

    //Locator list projects page
    private By buttonNewProject = By.xpath("//div[@id='vueApp']/descendant::a[normalize-space()='New Project']");
    private By headerProjectsPage = By.xpath("//span[normalize-space()='Projects Summary']");
    private By labelTotalNotStarted = By.xpath("//div[@class='_filters _hidden_inputs']/descendant::span[normalize-space()='Not Started']/preceding-sibling::span");
    private By labelTotalInProgress = By.xpath("//div[@class='_filters _hidden_inputs']/descendant::span[normalize-space()='In Progress']/preceding-sibling::span");
    private By labelTotalOnHold = By.xpath("//div[@class='_filters _hidden_inputs']/descendant::span[normalize-space()='On Hold']/preceding-sibling::span");
    private By labelTotalCancelled = By.xpath("//div[@class='_filters _hidden_inputs']/descendant::span[normalize-space()='Cancelled']/preceding-sibling::span");
    private By labelTotalFinished = By.xpath("//div[@class='_filters _hidden_inputs']/descendant::span[normalize-space()='Finished']/preceding-sibling::span");
    private By inputSearchProject = By.xpath("//div[@id='projects_filter']/descendant::input[@type='search']");

    private By firstRowItemProjectName(String projectName) {
        String xpathProject = "//table[@id='projects']/descendant::a[normalize-space()='" + projectName + "']";
        return By.xpath(xpathProject);
    }

    //Locator add new project page
    private By inputProjectName = By.xpath("//input[@id='name']");
    private By dropdownCustomer = By.xpath("//button[@data-id='clientid']");
    private By inputSearchCustomer = By.xpath("//button[@data-id='clientid']/following-sibling::div/descendant::input[@type='search']");

    private By valueCustomer(String value) {
        String xpathCustomer = "//button[@data-id='clientid']/following-sibling::div/descendant::span[normalize-space()='" + value + "']";
        return By.xpath(xpathCustomer);
    }

    private By checkboxCalculateProgressThroughTasks = By.xpath("//label[normalize-space()='Calculate progress through tasks']");
    private By dropdownBillingType = By.xpath("//button[@data-id='billing_type']");

    private By valueBillingType(String value) {
        String xpathBillingType = "//button[@data-id='billing_type']/following-sibling::div/descendant::span[normalize-space()='" + value + "']";
        return By.xpath(xpathBillingType);
    }

    private By dropdownStatus = By.xpath(" //button[@data-id='status']");

    private By valueStatus(String value) {
        String xpathStatus = "//button[@data-id='status']/following-sibling::div/descendant::span[normalize-space()='" + value + "']";
        return By.xpath(xpathStatus);
    }

    private By inputTotalRate = By.xpath("//input[@id='project_cost']");
    private By inputRatePerHour = By.xpath("//input[@id='project_rate_per_hour']");
    private By inputEstimatedHours = By.xpath("//input[@id='estimated_hours']");
    private By dropdownMembers = By.xpath("//button[@data-id='project_members[]']");
    private By inputSearchMembers = By.xpath("//button[@data-id='project_members[]']/following-sibling::div/descendant::input[@type='search']");
    private By inputStartDate = By.xpath("//input[@id='start_date']");
    private By inputDeadline = By.xpath("//input[@id='deadline']");
    private By inputTags = By.xpath("//label[normalize-space()='Tags']/following-sibling::ul/descendant::input");
    private By iFrameDescription = By.xpath("//iframe[@id='description_ifr']");
    private By inputDescription = By.xpath("//body[@id='tinymce']//p");
    private By checkboxSendProjectCreatedEmail = By.xpath("//label[normalize-space()='Send project created email']");
    private By buttonSave = By.xpath("//div[contains(@class,'panel-footer')]//button[normalize-space()='Save']");

    //Khai bao cac ham su dung noi bo trong trang Projects
    public String getTotalNotStarted() {
        return WebUI.getElementText(labelTotalNotStarted);
    }

    public String getTotalInProgress() {
        return WebUI.getElementText(labelTotalInProgress);
    }

    public String getTotalOnHold() {
        return WebUI.getElementText(labelTotalOnHold);
    }

    public String getTotalCancelled() {
        return WebUI.getElementText(labelTotalCancelled);
    }

    public String getTotalFinished() {
        return WebUI.getElementText(labelTotalFinished);
    }

    //Ham tinh toan tong so du lieu trong trang Projects
    public int getTotalProjects() {
        int totalProjects = Integer.parseInt(getTotalNotStarted()) +
                Integer.parseInt(getTotalInProgress()) +
                Integer.parseInt(getTotalOnHold()) +
                Integer.parseInt(getTotalCancelled()) +
                Integer.parseInt(getTotalFinished());
        return totalProjects;
    }

    public void verifyProjectsPageDisplay() {
        boolean isDisplayed = WebUI.checkElementExist(headerProjectsPage);
        Assert.assertTrue(isDisplayed, "Projects Page is not displayed!");
    }

    public void clickAddNewProjectButton() {
        WebUI.clickElement(buttonNewProject);
    }

    public void fillInputBasedOnBillingType(String billingType) {
        if (billingType.equals("Fixed Rate")) {
            WebUI.setText(inputTotalRate, "1000");
        } else if (billingType.equals("Project Hours")) {
            WebUI.setText(inputRatePerHour, "100");
        } else if (billingType.equals("Task Hours")) {
            System.out.println(billingType + " Based on task hourly rate");
        } else {
            System.out.println("Invalid billing type: " + billingType);
        }
    }

    public void fillDataNewProject(String projectName, String customerName, String billingType) {
        Actions actions = new Actions(DriverManager.getDriver());
        WebUI.setText(inputProjectName, projectName);

        // Select customer from dropdown
        WebUI.clickElement(dropdownCustomer);
        WebUI.setText(inputSearchCustomer, customerName);
        WebUI.sleep(1);
        actions.moveToElement(DriverManager.getDriver().findElement(valueCustomer(customerName))).click().keyDown(Keys.CONTROL).sendKeys(Keys.END).keyUp(Keys.CONTROL).sendKeys(" ").build().perform();
        WebUI.waitForElementVisible(valueCustomer(customerName));
        WebUI.clickElement(valueCustomer(customerName));
        WebUI.clickElement(checkboxCalculateProgressThroughTasks);

        // Select billing type
        WebUI.clickElement(dropdownBillingType);
        WebUI.clickElement(valueBillingType(billingType));
        fillInputBasedOnBillingType(billingType);

        // Select status
        WebUI.clickElement(dropdownStatus);
        WebUI.clickElement(valueStatus("On Hold"));

        //fill Estimated Hours
        WebUI.setText(inputEstimatedHours, "10");

        // Select members from dropdown
        WebUI.clickElement(dropdownMembers);
        WebUI.setTextAndKey(inputSearchMembers, "Anh Tester", Keys.ENTER);
        WebUI.clickElement(dropdownMembers);

        // Fill start date, deadline, tags
        WebUI.clearElementText(inputStartDate);
        WebUI.setText(inputStartDate, "10/08/2025");
        WebUI.setText(inputDeadline, "01/12/2025");
        WebUI.setText(inputTags, "htest13825");

        // Switch to iframe for description input
        WebUI.switchToFrame(iFrameDescription);
        WebUI.setText(inputDescription, "Here is the description of the iframe test project.");
        WebUI.switchToDefaultContent();
        WebUI.clickElement(checkboxSendProjectCreatedEmail);
    }

    public void clickSaveButton() {
        WebUI.clickElement(buttonSave);
    }

    public void verifyAlertMessageSuccessDisplayed() {
        boolean isDisplayed = WebUI.checkElementExist(alertMessage);
        Assert.assertTrue(isDisplayed, "Alert message success is not displayed!");

        String actualMessage = WebUI.getElementText(alertMessage);
        Assert.assertEquals(actualMessage, "Project added successfully.", "Alert message does not match expected message!");
    }

    public void verifyProjectDetail() {

    }

    public void verifyAddNewProjectSuccess(String projectName) {
        WebUI.waitForPageLoaded();
        WebUI.clearElementText(inputSearchProject);
        WebUI.clickElement(inputSearchProject);
        WebUI.setTextAndKey(inputSearchProject, projectName, Keys.ENTER);
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(firstRowItemProjectName(projectName)), "FAILED! Incorrect Project added.");
    }
}
