package com.hatester.Bai29_DataProvider;

import com.hatester.helpers.ExcelHelper;
import com.hatester.helpers.SystemHelper;
import org.testng.annotations.DataProvider;

public class DataProviderFactory {
    @DataProvider(name = "loginData1")
    public Object[][] getDataLogin1() {
        return new Object[][]{
                {"admin@example.com", 123456},
                {"admin1@example.com", 12345}
        };
    }

    @DataProvider(name = "loginData2", parallel = false)
    public Object[][] getDataLogin2() {
        return new Object[][]{
                {"admin@example.com", 123456, "Admin"},
                {"user@example.com", 56897, "User"},
                {"customer@example.com", 12563, "Customer"}
        };
    }

    @DataProvider(name = "AddNewCustomerData", parallel = false)
    public Object[][] getCustomerData() {
        return new Object[][]{
                {"New Customer 1010B" + SystemHelper.getCurrentDatetime(), "GroupVIP", "USD", "Vietnamese", "Vietnam"},
                {"New Customer 1010B" + SystemHelper.getCurrentDatetime(), "Gold", "EUR", "English", "Canada"},
                {"New Customer 1010B" + SystemHelper.getCurrentDatetime(), "Silver", "EUR", "German", "Germany"}
        };
    }

    @DataProvider(name = "DataLoginFromExcel", parallel = true)
    public Object[][] dataLoginFromExcel() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getExcelData(SystemHelper.getCurrentDir()
                + "src/test/resources/testdata/dataCRM.xlsx", "Login_DataProvider");
        System.out.println("Login Data from Excel: " + data);
        return data;
    }

    @DataProvider(name = "DataLoginFromExcelHashtable", parallel = false)
    public Object[][] dataLoginFromExcelHashtable() {
        ExcelHelper excelHelper = new ExcelHelper();
        Object[][] data = excelHelper.getDataHashTable(SystemHelper.getCurrentDir()
                + "src/test/resources/testdata/dataCRM.xlsx", "Login_DataProvider", 2, 3);
        System.out.println("Login Data from Excel (Hashtable): " + data);
        return data;
    }
}
