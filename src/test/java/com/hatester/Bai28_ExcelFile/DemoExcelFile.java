package com.hatester.Bai28_ExcelFile;

import com.hatester.Bai26_ParallelExecution_POM.pages.LoginPage;
import com.hatester.common.BaseTest;
import com.hatester.helpers.ExcelHelper;
import org.testng.annotations.Test;

public class DemoExcelFile extends BaseTest {
    @Test
    public void testReadExcelFile() {
        System.out.println("Demo Read Excel File");
        ExcelHelper excel = new ExcelHelper();
        excel.setExcelFile("src/test/resources/testdata/dataCRM.xlsx", "Login");
        System.out.println(excel.getCellData("EMAIL", 1));
        System.out.println(excel.getCellData("PASSWORD", 1));

        LoginPage loginPage = new LoginPage();
        loginPage.loginCRM(excel.getCellData("EMAIL", 1), excel.getCellData("PASSWORD", 1));
    }

    @Test
    public void testReadExcelFileCustomer() {
        System.out.println("Demo Read Excel File");
        ExcelHelper excel = new ExcelHelper();
        excel.setExcelFile("src/test/resources/testdata/dataCRM.xlsx", "Customer");
        System.out.println(excel.getCellData("CUSTOMER_NAME", 1));
        System.out.println(excel.getCellData("EMAIL", 1));
        System.out.println(excel.getCellData("PHONE", 1));
        System.out.println(excel.getCellData("WEBSITE", 1));

//        LoginPage loginPage = new LoginPage();
//        loginPage.loginCRM(excel.getCellData("EMAIL", 1), excel.getCellData("PASSWORD", 1));
    }

    @Test
    public void testWriteExcelFile() {
        System.out.println("Demo Write Excel File");
        ExcelHelper excel = new ExcelHelper();
        excel.setExcelFile("src/test/resources/testdata/dataCRM.xlsx", "Login");

//        excel.setCellData("namtest@gmail.com","EMAIL",3);
        excel.setCellData("PASS", "TEST_RESULT", 1);
        excel.setCellData("FAIL", "TEST_RESULT", 2);
    }
}
