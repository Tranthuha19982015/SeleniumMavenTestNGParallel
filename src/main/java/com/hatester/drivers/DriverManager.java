package com.hatester.drivers;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    //Khởi tạo đối tượng WebDriver theo dạng ThreadLocal (để driver đó có thể lưu được nhiều luồng khác nhau)
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        //Hàm get() thuộc ThreadLocal
        //Hỗ trợ lấy ra từng session driver theo từng luồng khác nhau
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        //set() thuộc ThreadLocal
        //Tự động set đúng driver cục bộ theo từng luồng trong ThreadLocal
        DriverManager.driver.set(driver);
    }

    public static void quit() {
        //quit() thuộc Selenium
        //quit() để reset session của driver về null
        DriverManager.driver.get().quit();

        //remove() thuộc ThreadLocal
        //xóa thread đó (xóa luôn vùng nhớ của nó để nó không chiếm vị trí trong bộ nhớ)
        driver.remove();
    }
}
