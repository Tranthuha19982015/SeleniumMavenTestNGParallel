package com.hatester.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemHelper {

    //Hàm lấy ra đường dẫn hiện tại từ ổ đĩa trỏ tới project của mình
    public static String getCurrentDirectory() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }

    public static String getCurrentDatetime(){
        return new SimpleDateFormat("_ddMMyyyy_HHmmss").format(new Date());
    }
}
