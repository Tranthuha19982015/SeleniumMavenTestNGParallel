package com.hatester.helpers;

import java.io.File;

public class SystemHelper {

    //Hàm lấy ra đường dẫn hiện tại từ ổ đĩa trỏ tới project của mình
    public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }
}
