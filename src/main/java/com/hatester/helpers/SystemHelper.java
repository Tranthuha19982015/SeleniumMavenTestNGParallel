package com.hatester.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SystemHelper {

    //Hàm lấy ra đường dẫn hiện tại từ ổ đĩa trỏ tới project của mình
    public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }

    public static String getCurrentDatetime() {
        return new SimpleDateFormat("_ddMMyyyy_HHmmss").format(new Date());
    }

    public static String getDateTimeNowFormat() {
        //Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        //Định dạng ngày giờ theo pattern
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


        //Chuyển sang chuỗi và thay thế ký tự
        String formatted = now.format(formatter).replace("-", "_").replace(":", "_").replace(" ", "_");
        return formatted;
    }
}
