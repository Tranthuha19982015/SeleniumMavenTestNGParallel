package com.hatester.Bai33_ExtendReport.pages;

import com.hatester.drivers.DriverManager;
import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

public class TasksPage extends BasePage {
    private CustomersPage customersPage;
    private ProjectsPage projectsPage;
    private LeadsPage leadsPage;

    //Locators of elements on Tasks page
    private By buttonNewTask = By.xpath("//a[normalize-space()='New Task']");
    private By headerTasksPage = By.xpath("//span[normalize-space()='Tasks Summary']");
    private By labelNotStarted = By.xpath("//span[normalize-space()='Not Started']/preceding-sibling::span");
    private By labelInProgress = By.xpath("//span[normalize-space()='In Progress']/preceding-sibling::span");
    private By labelTesting = By.xpath("//span[normalize-space()='Testing']/preceding-sibling::span");
    private By labelAwaitingFeedback = By.xpath("//span[normalize-space()='Awaiting Feedback']/preceding-sibling::span");
    private By labelComplete = By.xpath("//span[normalize-space()='Complete']/preceding-sibling::span");
    private By taskTableRows = By.xpath("//table[@id='tasks']/descendant::tbody/tr");
    private By inputSearchTasks = By.xpath("//div[@id='tasks_filter']/descendant::input[@type='search']");

    private By firstRowItemTasksName(String taskName) {
        String text = "//table[@id='tasks']//a[normalize-space()='" + taskName + "']";
        return By.xpath(text);
    }

    //Locators of elements on Add New Task page
    private By headerAddNewTask = By.xpath("//h4[@id='myModalLabel']");
    private By checkboxPublic = By.xpath("//label[normalize-space()='Public']");
    private By checkboxBillable = By.xpath("//label[@for='task_is_billable']");
    //    private By textLinkAttachFiles = By.xpath("//a[normalize-space()='Attach Files']");
//    private By inputChooseFileAttachment = By.xpath("//div[@id='new-task-attachments']/descendant::input[@type='file']");
//    private By iconAddMoreFileAttachment = By.xpath("//div[@id='new-task-attachments']/descendant::button[contains(@class,'add_more_attachments')]");
//    private By iconDeleteFileAttachment = By.xpath("//div[@id='new-task-attachments']/descendant::button[contains(@class,'remove_attachment')]");
    private By inputSubject = By.xpath("//input[@id='name']");
    private By inputHourlyRate = By.xpath("//input[@id='hourly_rate']");
    private By datepickerStartDate = By.xpath("//input[@id='startdate']");
    private By datepickerDueDate = By.xpath("//input[@id='duedate']");
    private By dropdownPriority = By.xpath("//button[@data-id='priority']");

    private By selectPriority(String priority) {
        String xpathPriority = "//button[@data-id='priority']/following-sibling::div/descendant::span[normalize-space()='" + priority + "']";
        return By.xpath(xpathPriority);
    }

    private By dropdownRepeatEvery = By.xpath("//button[@data-id='repeat_every']");

    private By selectRepeatEvery(String repeatEvery) {
        String xpathRepeatEvery = "//button[@data-id='repeat_every']/following-sibling::div/descendant::span[normalize-space()='" + repeatEvery + "']";
        return By.xpath(xpathRepeatEvery);
    }

    private By listRepeatEvery = By.xpath("//button[@data-id='repeat_every']/following-sibling::div/descendant::ul/li");
    private By checkboxInfinity = By.xpath("//label[normalize-space()='Infinity']");
    private By inputTotalCycles = By.xpath("//input[@id='cycles']");
    private By inputNumberCustomRepeatEvery = By.xpath("//input[@id='repeat_every_custom']");
    private By dropdownTypeCustomRepeatEvery = By.xpath("//button[@data-id='repeat_type_custom']");

    private By selectTypeCustomRepeatEvery(String type) {
        String xpathType = "//button[@data-id='repeat_type_custom']/following-sibling::div/descendant::span[contains(normalize-space(),'" + type + "')]";
        return By.xpath(xpathType);
    }

    private By dropdownRelatedTo = By.xpath("//button[@data-id='rel_type']");

    private By selectRelatedTo(String relatedTo) {
        String xpathRelatedTo = "//button[@data-id='rel_type']/following-sibling::div/descendant::span[normalize-space()='" + relatedTo + "']";
        return By.xpath(xpathRelatedTo);
    }

    private By dropdownTypeRelatedTo = By.xpath("//button[@data-id='rel_id']");
    private By inputSearchTypeRelatedTo = By.xpath("//button[@data-id='rel_id']/following-sibling::div/descendant::input[@type='search']");

    private By selectValueSearchTypeRelatedTo(String value) {
        String valueSearch = "(//button[@data-id='rel_id']/following-sibling::div)/descendant::span[contains(normalize-space(),'" + value + "')]";
        return By.xpath(valueSearch);
    }

    private By dropdownAssignees = By.xpath("//button[@data-id='assignees']");
    private By inputSearchAssignees = By.xpath("//button[@data-id='assignees']/following-sibling::div/descendant::input[@type='search']");
    private By dropdownFollowers = By.xpath("//button[@data-id='followers[]']");
    private By inputSearchFollowers = By.xpath("//button[@data-id='followers[]']/following-sibling::div/descendant::input[@type='search']");
    private By labelTags = By.xpath("//label[normalize-space()='Tags']");
    private By inputTags = By.xpath("//div[@id='inputTagsWrapper']/descendant::li[@class='tagit-new']/input");
    private By inputTaskDescription = By.xpath("//textarea[@id='description']");
    private By iframeTaskDescription = By.xpath("//iframe[@id='description_ifr']");
    private By inputOnFrame = By.xpath("//body[@id='tinymce']/descendant::p");
    private By buttonClose = By.xpath("//button[normalize-space()='Close']");
    private By buttonSave = By.xpath("//button[normalize-space()='Save']");

    private By valueLeadInTask = By.xpath("//button[@data-id='rel_id']/following-sibling::div/descendant::a//span[@class='text']");

    //locators pop-up task detail
    private By iconCloseTaskDetail = By.xpath("//div[@class='modal-header task-single-header']//button[@aria-label='Close']");
    private By popupTaskDetail = By.xpath("//div[@id='task-modal']//div[@class='modal-content data']");

    public void verifyTasksPageDisplay() {
        boolean isTasksPageDisplayed = WebUI.checkElementExist(headerTasksPage);
        Assert.assertTrue(isTasksPageDisplayed, "The tasks header page not display.");
    }

    public void clickButtonNewTask() {
        WebUI.clickElement(buttonNewTask);
    }

    public void verifyAddNewTaskPageDisplay() {
        boolean isAddNewTaskPageDisplayed = WebUI.checkElementExist(headerAddNewTask);
        Assert.assertTrue(isAddNewTaskPageDisplayed, "The Add new task header page not display.");
        Assert.assertEquals(WebUI.getElementText(headerAddNewTask), "Add new task", "The Add new task header not match.");
    }

    public void chooseOptionRepeatEvery(String typeRepeat) {
        if (!typeRepeat.equals("") && !typeRepeat.equals("Custom")) {
            WebUI.clickElement(checkboxInfinity);
            WebUI.clearElementText(inputTotalCycles);
            WebUI.setText(inputTotalCycles, "8");
        } else if (typeRepeat.equals("Custom")) {
            WebUI.clearElementText(inputNumberCustomRepeatEvery);
            WebUI.setText(inputNumberCustomRepeatEvery, "3");
            WebUI.clickElement(dropdownTypeCustomRepeatEvery);
            WebUI.clickElement(selectTypeCustomRepeatEvery("Week"));
        }
    }

    public void fillDataAddNewTask(String subject, String valueRelatedTo) {
        Actions actions = new Actions(DriverManager.getDriver());
        WebUI.clickElement(checkboxPublic);
        WebUI.setText(inputSubject, subject);
        WebUI.clearElementText(inputHourlyRate);
        WebUI.setText(inputHourlyRate, "3");

        //select Start Date
        WebUI.clearElementText(datepickerStartDate);
        WebUI.setText(datepickerStartDate, "11-08-2025");
        WebUI.clickElement(datepickerStartDate);

        //select Due Date
        WebUI.clearElementText(datepickerDueDate);
        WebUI.setText(datepickerDueDate, "12-08-2025");
        WebUI.clickElement(datepickerDueDate);

        //select Priority
        WebUI.clickElement(dropdownPriority);
        WebUI.clickElement(selectPriority("High"));

        //select Repeat Every
        WebUI.clickElement(dropdownRepeatEvery);
        String typeRepeatEvery = "Custom";
        WebUI.clickElement(selectRepeatEvery(typeRepeatEvery));
        chooseOptionRepeatEvery(typeRepeatEvery);

        //select Related To
        WebUI.clickElement(dropdownRelatedTo);
        String optionRelatedTo = "Lead";
        WebUI.clickElement(selectRelatedTo(optionRelatedTo));
        WebUI.clickElement(dropdownTypeRelatedTo);
        WebUI.setText(inputSearchTypeRelatedTo, valueRelatedTo);
        WebUI.sleep(1);
        actions.moveToElement(DriverManager.getDriver().findElement(inputSearchTypeRelatedTo)).click().keyDown(Keys.CONTROL).sendKeys(Keys.END).keyUp(Keys.CONTROL).sendKeys(" ").build().perform();
        WebUI.waitForElementVisible(selectValueSearchTypeRelatedTo(valueRelatedTo));
        WebUI.clickElement(selectValueSearchTypeRelatedTo(valueRelatedTo));

        //select Assignees
        WebUI.clickElement(dropdownAssignees);
        WebUI.setTextAndKey(inputSearchAssignees, "Anh Tester", Keys.ENTER);
        WebUI.clickElement(dropdownAssignees);

        //select Followers
        WebUI.clickElement(dropdownFollowers);
        WebUI.setTextAndKey(inputSearchFollowers, "Anh Tester", Keys.ENTER);
        WebUI.clickElement(dropdownFollowers);

        //fill Tags
        WebUI.setTextAndKey(inputTags, "htest", Keys.ENTER);
        WebUI.clickElement(labelTags);

        //fill iFrame Description
        WebUI.clickElement(inputTaskDescription);
        WebUI.waitForSwitchToFrameWhenAvailable(iframeTaskDescription);
        WebUI.setText(inputOnFrame, "htest iframe");
        WebUI.switchToDefaultContent();
    }

    public void clickButtonSave() {
        WebUI.clickElement(buttonSave);
        WebUI.waitForPageLoaded();
    }

    public void verifyAlertMessageSuccessDisplayed() {
        Assert.assertTrue(WebUI.checkElementExist(alertMessage), "The alert message not display.");
        String actualMessage = WebUI.getElementText(alertMessage);
        Assert.assertEquals(actualMessage, "Task added successfully.", "The alert message add new tasks not match.");
    }

    public void clickIconCloseTaskDetail() {
        WebUI.clickElement(iconCloseTaskDetail);
        WebUI.waitForPageLoaded();
    }

    public void verifyTaskListVisibleAfterClosingTaskDetailPopup() {
        WebUI.waitForElementNotVisible(popupTaskDetail);
    }

    public void searchAndCheckTaskInTable(String subject) {
        WebUI.clickElement(inputSearchTasks);
        WebUI.clearElementText(inputSearchTasks);
        WebUI.setText(inputSearchTasks, subject);
        WebUI.setKey(inputSearchTasks, Keys.ENTER);
        WebUI.sleep(1);
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(firstRowItemTasksName(subject)), "FAILED. Incorrect Task added");
    }

    public String getValueLeadInTask() {
        return WebUI.getElementText(valueLeadInTask);
    }

    public void chooseLeadFollowingRelatedTo(String leadName) {
        Actions actions = new Actions(DriverManager.getDriver());
        WebUI.clickElement(dropdownRelatedTo);
        WebUI.clickElement(selectRelatedTo("Lead"));
        WebUI.clickElement(dropdownTypeRelatedTo);
        WebUI.setText(inputSearchTypeRelatedTo, leadName);
        WebUI.sleep(1);
        actions.moveToElement(DriverManager.getDriver().findElement(inputSearchTypeRelatedTo)).click().keyDown(Keys.CONTROL).sendKeys(Keys.END).keyUp(Keys.CONTROL).sendKeys(" ").build().perform();
        WebUI.waitForElementVisible(valueLeadInTask);
    }
}
