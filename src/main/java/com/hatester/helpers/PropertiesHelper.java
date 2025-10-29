package com.hatester.helpers;

import com.hatester.utils.LogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

public class PropertiesHelper {

    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;
    private static FileOutputStream out;
    private static String relPropertiesFilePathDefault = "src/test/resources/configs/config.properties";

    //3 hàm đọc file (chọn 1 trong 3)
    //Hàm đọc multi file
    public static Properties loadAllFiles() {
        //Khai báo danh sách file .properties
        //LinkedList: danh sách đường dẫn
        LinkedList<String> files = new LinkedList<>();
        // Add tất cả file Properties vào đây theo mẫu
        files.add("src/test/resources/configs/config.properties");
//        files.add("src/test/resources/configs/local.properties");
//        files.add("src/test/resources/configs/production.properties");

        try {
            properties = new Properties();
            for (String f : files) {
                Properties tempProp = new Properties();
                linkFile = SystemHelper.getCurrentDir() + f;
                file = new FileInputStream(linkFile); //Mở file (đọc nội dung của file được chỉ định)
                tempProp.load(file); //hàm load nội dung để duyệt file
                properties.putAll(tempProp); //đẩy vào đối tượng properties
            }
            return properties;
        } catch (IOException ioe) {
            return new Properties();
        }
    }

    //Hàm phụ (đọc 1 file được chỉ định)
    public static void setFile(String relativePropertiesFilePath) {
        properties = new Properties();
        try {
            linkFile = SystemHelper.getCurrentDir() + relativePropertiesFilePath; //đường dẫn cục bộ tính từ đường dẫn từ project
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close(); //không đọc nữa để tiết kiệm bộ nhớ
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Hàm phụ (đọc 1 file default)
    public static void setDefaultFile() {
        properties = new Properties();
        try {
            linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
            file = new FileInputStream(linkFile);
            properties.load(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Lấy giá trị trong file properties theo key
    public static String getValue(String key) {
        String value = null;
        try {
            if (file == null) {
                setDefaultFile();
            }
            // Lấy giá trị từ file đã Set
            value = properties.getProperty(key); //Hàm getProperty của Java, mặc định trả về kiểu String
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
        return value;
    }

    //Set Lấy giá trị trong file properties theo key = value
    public static void setValue(String key, String value) {
        try {
            if (file == null) {
                setDefaultFile();
                //khai báo 1 đường dẫn để trích xuất data đó ra (đang đọc ghi trên cùng 1 file)
                out = new FileOutputStream(SystemHelper.getCurrentDir() + relPropertiesFilePathDefault);
            }
            //Ghi vào cùng file Prop với file lấy ra
            out = new FileOutputStream(linkFile);
            LogUtils.info(linkFile);
            properties.setProperty(key, value);
            properties.store(out, null);
            out.close();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }

    public static void setValue(String key, String keyValue, String filePath) {
        try {
            if (file == null) {
                setDefaultFile();
                //khai báo 1 đường dẫn để trích xuất data đó ra (đang đọc ghi trên cùng 1 file)
                out = new FileOutputStream(SystemHelper.getCurrentDir() + filePath);
            }
            //Ghi vào cùng file Prop với file lấy ra
            out = new FileOutputStream(SystemHelper.getCurrentDir() + filePath);
            LogUtils.info(SystemHelper.getCurrentDir() + filePath);
            properties.setProperty(key, keyValue);
            properties.store(out, null);
            out.close();
        } catch (Exception e) {
            LogUtils.error(e.getMessage());
        }
    }
}
