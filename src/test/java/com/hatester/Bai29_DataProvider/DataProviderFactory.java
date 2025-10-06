package com.hatester.Bai29_DataProvider;

import org.testng.annotations.DataProvider;

public class DataProviderFactory {
    @DataProvider(name = "loginData1")
    public Object[][] getDataLogin1() {
        return new Object[][]{
                {"admin@example.com", 123456},
                {"admin1@example.com", 12345}
        };
    }

    @DataProvider(name = "loginData2", parallel = true)
    public Object[][] getDataLogin2() {
        return new Object[][]{
                {"admin@example.com", 123456, "Admin"},
                {"user@example.com", 56897, "User"},
                {"customer@example.com", 12563, "Customer"}
        };
    }

    @DataProvider(name = "customerData", parallel = true)
    public Object[][] getCustomerData() {
        return new Object[][]{
                {"Test Customer 0610A1","VIP","USD","Vietnamese","Vietnam"},
                {"Test Customer 0610A2","Gold","EUR","English","Canada"}
        };
    }
}
