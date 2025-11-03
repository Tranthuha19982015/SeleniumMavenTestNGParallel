package com.hatester.Bai34_AllureReport.pages;

import com.hatester.keywords.WebUI;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;

public class CustomersPage extends BasePage {

    //Locator for Customer List
    private By buttonNewCustomer = By.xpath("//a[normalize-space()='New Customer']");
    private By headerCustomerPage = By.xpath("//span[normalize-space()='Customers Summary']");
    private By inputSearchCustomer = By.xpath("//div[@id='clients_filter']//input[@type='search']");

    private By firstRowItemCustomer(String customerName) {
        String xpathCustomerName = "//table[@id='clients']/descendant::a[normalize-space()='" + customerName + "']";
        return By.xpath(xpathCustomerName);
    }

    private By labelTotalCustomers = By.xpath("//span[normalize-space()='Total Customers']/preceding-sibling::span");

    //Locator for Add New Customer
    private By inputCompany = By.xpath("//input[@id='company']");
    private By inputVatNumber = By.xpath("//input[@id='vat']");
    private By inputPhone = By.xpath("//input[@id='phonenumber']");
    private By inputWebsite = By.xpath("//input[@id='website']");
    private By inputAddress = By.xpath("//textarea[@id='address']");
    private By inputCity = By.xpath("//input[@id='city']");
    private By inputState = By.xpath("//input[@id='state']");
    private By inputZipCode = By.xpath("//input[@id='zip']");
    private By dropdownGroups = By.xpath("//button[contains(@data-id,'groups_in')]");
    private By inputSearchGroups = By.xpath("//button[contains(@data-id,'groups_in')]/following-sibling::div//input[@type='search']");
    private By dropdownCurrency = By.xpath("//button[contains(@data-id,'default_currency')]");
    private By inputSearchCurrency = By.xpath("//button[contains(@data-id,'default_currency')]/following-sibling::div//input[@type='search']");
    private By dropdownDefaultLanguage = By.xpath("//button[contains(@data-id,'default_language')]");

    public By optionValueDefaultLanguage(String language) {
        String xpathLanguage = "//span[normalize-space()='" + language + "']";
        System.out.println("Select language: " + language);
        return By.xpath(xpathLanguage);
    }

    //    private By optionLanguageVietnamese = By.xpath("//span[normalize-space()='Vietnamese']");
//    private String optionLanguageDynamic = "(//span[normalize-space()='%s'])[%d]";
    private By dropdownCountry = By.xpath("//button[contains(@data-id,'country')]");
    private By inputSearchCountry = By.xpath("//button[contains(@data-id,'country')]/following-sibling::div//input[@type='search']");
    private By buttonSave = By.xpath("//div[@id='profile-save-section']//button[normalize-space()='Save']");

    //Locator for Customer Detail Page
    private By headerCustomerDetailPage(String tabName) {
        String xpathTab = "//h4[normalize-space()='" + tabName + "']";
        return By.xpath(xpathTab);
    }

    private By labelNotstarted = By.xpath("//dt[normalize-space()='Not Started']/following-sibling::dd/div");
    private By labelInProgress = By.xpath("//dt[normalize-space()='In Progress']/following-sibling::dd/div");
    private By labelOnHold = By.xpath("//dt[normalize-space()='On Hold']/following-sibling::dd/div");
    private By labelCancelled = By.xpath("//dt[normalize-space()='Cancelled']/following-sibling::dd/div");
    private By labelFinished = By.xpath("//dt[normalize-space()='Finished']/following-sibling::dd/div");

    private By customerTabProjects = By.xpath("//a[@data-group='projects' and normalize-space()='Projects']");
    private By inputSearchInCustomerTabProjects = By.xpath("//div[@id='projects_filter']/descendant::input[@type='search']");

    private By firstRowItemProject(String projectName) {
        String xpathProject = "//table[@id='projects']/descendant::a[normalize-space()='" + projectName + "']";
        return By.xpath(projectName);
    }

    //Hàm xử lý cho trang Customer
    public void verifyNavigateToCustomerPage() {
        boolean check = WebUI.checkElementExist(headerCustomerPage);
        Assert.assertTrue(check, "The customer header page not display.");
    }

    public void clickButtonNewCustomer() {
        WebUI.clickElement(buttonNewCustomer);
    }

    public void fillDataForAddNewCustomer(String customerName, String group, String currency, String language, String country) {
        WebUI.setText(inputCompany, customerName);
        WebUI.setText(inputVatNumber, "10");
        WebUI.setText(inputPhone, "0965898635");
        WebUI.setText(inputWebsite, "htest.com.vn");

        // select Groups
        WebUI.clickElement(dropdownGroups);
        WebUI.setTextAndKey(inputSearchGroups, group, Keys.ENTER);
        WebUI.clickElement(dropdownGroups);

        //select Currency
        WebUI.clickElement(dropdownCurrency);
        WebUI.setTextAndKey(inputSearchCurrency, currency, Keys.ENTER);

        //select Default Language
        WebUI.clickElement(dropdownDefaultLanguage);
        WebUI.clickElement(optionValueDefaultLanguage(language));

        WebUI.setText(inputAddress, "Minh Khai, Bắc Từ Liêm");
        WebUI.setText(inputCity, "Hà Nội");
        WebUI.setText(inputState, "123545");
        WebUI.setText(inputZipCode, "0001212");

        //select Country
        WebUI.clickElement(dropdownCountry);
        WebUI.setTextAndKey(inputSearchCountry, country, Keys.ENTER);
    }

    public void clickSaveButton() {
        WebUI.clickElement(buttonSave);
    }

    public void verifyAlertMessageSuccessDisplayed() {
        boolean isAlertDisplay = WebUI.checkElementExist(alertMessage);
        Assert.assertTrue(isAlertDisplay, "The alert message not display after add new customer.");
        String alertText = WebUI.getElementText(alertMessage);
        Assert.assertEquals(alertText, "Customer added successfully.", "Alert message does not match.");
    }

    public void verifyNavigateToCustomerDetailPage() {
        boolean check = WebUI.checkElementExist(headerCustomerDetailPage("Profile"));
        Assert.assertTrue(check, "The customer detail header page not display.");
    }

    public void verifyAddNewCustomerSuccess(String customerName, String group, String currency, String language, String country) {
        //navigation to customer detail
//        verifyNavigateToCustomerDetailPage();

        //Verify data in customer detail
        Assert.assertEquals(WebUI.getElementAttribute(inputCompany, "value"), customerName, "The Company name not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputVatNumber, "value"), "10", "The VAT value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputPhone, "value"), "0965898635", "The Phone number value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputWebsite, "value"), "htest.com.vn", "The Website value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(dropdownGroups, "title"), group, "The Groups value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(dropdownCurrency, "title"), currency, "The Currency value not match.");
        WebUI.scrollToElementAtBottom(buttonSave);
        Assert.assertEquals(WebUI.getElementAttribute(dropdownDefaultLanguage, "title"), language, "The Default Language value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputAddress, "value"), "Minh Khai, Bắc Từ Liêm", "The Address value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputCity, "value"), "Hà Nội", "The City value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputState, "value"), "123545", "The State value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(inputZipCode, "value"), "0001212", "The Zip Code value not match.");
        Assert.assertEquals(WebUI.getElementAttribute(dropdownCountry, "title"), country, "The Country value not match.");
    }

    public void searchAndCheckCustomerInTable(String customerName) {
        WebUI.waitForPageLoaded();
        WebUI.clearElementText(inputSearchCustomer);
        WebUI.clickElement(inputSearchCustomer);
        WebUI.setTextAndKey(inputSearchCustomer, customerName, Keys.ENTER);
        WebUI.waitForPageLoaded();
        Assert.assertTrue(WebUI.checkElementExist(firstRowItemCustomer(customerName)), "FAILED! Incorrect Customer added.");
    }

    public void verifyCustomerDetail() {

    }
}

