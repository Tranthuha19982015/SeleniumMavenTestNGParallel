package com.hatester.helpers;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class ExcelHelper {
    private FileInputStream fileIn;  // Đọc dữ liệu từ file Excel
    private FileOutputStream fileOut;  // Ghi dữ liệu ra file Excel
    private Workbook workbook;  // Đại diện cho toàn bộ workbook (tệp Excel)
    private Sheet sheet;  // Đại diện cho một sheet trong workbook
    private Row row;  // Đại diện cho một dòng trong Excel
    private Cell cell;  // Đại diện cho một ô trong Excel
    private CellStyle cellStyle;  // Dùng để định dạng ô (ví dụ: font, màu nền, border)
    private Color color;  // Đại diện cho màu (dùng trong định dạng)
    private String excelFilePath;  // Đường dẫn tới file Excel
    private Map<String, Integer> columns = new HashMap<>();  // Lưu map tên cột -> chỉ số cột (header mapping)

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            File f = new File(excelPath);

            //Kiểm tra file có tồn tại hay không
            if (!f.exists()) {
                System.out.println("File doesn't exist.");
            }

            //đọc đường dẫn của file excel mà mình chỉ định rồi lưu vào đối tượng fileIn
            fileIn = new FileInputStream(excelPath);
            workbook = WorkbookFactory.create(fileIn); //dùng Workbook chuyên nghiệp của Apache POI để tạo phiên làm việc đối với file excel
            sheet = workbook.getSheet(sheetName); //lấy data theo từng sheet

            //kiểm tra sheet đó tồn tại hay không
            if (sheet == null) {
                throw new Exception("Sheet name doesn't exist.");
            }

            //
            this.excelFilePath = excelPath;

            //adding all the column header names to the map 'columns'
            sheet.getRow(0).forEach(cell -> {
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //Đọc/ghi từng ô
    //lấy data theo từng ô trong file Excel
    public String getCellData(int columnIndex, int rowIndex) {
        try {
            cell = sheet.getRow(rowIndex).getCell(columnIndex);

            String cellData = null;

            switch (cell.getCellType()) {  //Kiểm tra kiểu dữ liệu trong ô Excel (vì Excel có thể chứa String, Number, Date, Boolean, Blank…)
                case STRING:  //Nếu ô là text → đọc trực tiếp giá trị chuỗi.
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellData = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                    } else {
                        // Nếu là số nguyên thì ép kiểu về long, nếu là số thực thì để nguyên
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {  //Math.floor(x) = làm tròn xuống giá trị nguyên gần nhất ≤ x
                            cellData = String.valueOf((long) numericValue); // Số nguyên
                        } else {
                            cellData = String.valueOf(numericValue); // Số thực
                        }
                    }
                    break;
                case BOOLEAN:
                    cellData = Boolean.toString(cell.getBooleanCellValue());  //Nếu ô chứa giá trị true/false → convert sang String
                    break;
                case BLANK:
                    cellData = "";  //Nếu ô rỗng → trả về chuỗi rỗng ""
                    break;
            }
            return cellData;
        } catch (Exception e) {
            return "";
        }
    }

    //Gọi ra hàm này nè
    public String getCellData(String columnName, int rowIndex) {
        if (columns.get(columnName) == null) {
            try {
                throw new Exception("Column name doesn't exist.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return getCellData(columns.get(columnName), rowIndex);  //columns.get(columnName) sẽ trả về columnIndex
    }

    //set by column index
    public void setCellData(String text, int columnIndex, int rowIndex) {
        try {
            row = sheet.getRow(rowIndex);
            if (row == null) {
                row = sheet.createRow(rowIndex);
            }
            cell = row.getCell(columnIndex);

            if (cell == null) {
                cell = row.createCell(columnIndex);
            }
            cell.setCellValue(text); //set giá trị vào ô đó

            //chỉnh format của ô đó
            XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
            style.setFillPattern(FillPatternType.NO_FILL); //màu nền của ô
            style.setAlignment(HorizontalAlignment.CENTER); //căn theo chiều ngang
            style.setVerticalAlignment(VerticalAlignment.CENTER); //căn theo chiều dọc

            cell.setCellStyle(style); //set format vào ô đó

            fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    //set by column name
    public void setCellData(String text, String columnName, int rowIndex) {
        Integer columnIndex = columns.get(columnName);
        if (columnIndex == null) {
            throw new IllegalArgumentException("Column '" + columnName + "' not found in sheet.");
        }
        setCellData(text, columnIndex, rowIndex);
    }


    //Đọc toàn bộ sheet làm data cho test  =>  Đọc toàn bộ file (bỏ qua header) để dùng cho DataProvider
    public Object[][] getExcelData(String filePath, String sheetName) {
        Object[][] data = null;
        Workbook workbook = null;
        try {
            // load the file
            FileInputStream fis = new FileInputStream(filePath);


            // load the workbook
            workbook = new XSSFWorkbook(fis);

            // load the sheet
            Sheet sh = workbook.getSheet(sheetName);

            // load the row
            Row row = sh.getRow(0);

            //
            int noOfRows = sh.getPhysicalNumberOfRows(); //số lượng dòng có DL thực tế (tính cả header), có bỏ qua dòng trống - dòng không có DL ở ô nào)
            int noOfCols = row.getLastCellNum(); //từ dòng lấy ra ô cuối cùng của dòng đó chính là cột cuối cùng
                                                 // trả về chỉ số cột cuối cùng + 1 (vì nó đếm theo kiểu “số lượng cột” chứ không phải index).

            System.out.println(noOfRows + " - " + noOfCols);

            Cell cell;
            data = new Object[noOfRows - 1][noOfCols];

            //
            for (int i = 1; i < noOfRows; i++) {
                for (int j = 0; j < noOfCols; j++) {
                    row = sh.getRow(i);
                    cell = row.getCell(j);

                    switch (cell.getCellType()) {
                        case STRING:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data[i - 1][j] = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                            } else {
                                // Nếu là số nguyên thì ép kiểu về long, nếu là số thực thì để nguyên
                                double numericValue = cell.getNumericCellValue();
                                if (numericValue == Math.floor(numericValue)) {  //Math.floor(x) = làm tròn xuống giá trị nguyên gần nhất ≤ x
                                    data[i - 1][j] = String.valueOf((long) numericValue); // Số nguyên
                                } else {
                                    data[i - 1][j] = String.valueOf(numericValue); // Số thực
                                }
                            }
                            break;
                        case BOOLEAN:
                            data[i - 1][j] = Boolean.toString(cell.getBooleanCellValue()); //HOẶC dùng String.valueOf(cell.getBooleanCellValue());
                            break;
                        case BLANK:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                        default:
                            data[i - 1][j] = cell.getStringCellValue();
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("The exception is:" + e.getMessage());
            throw new RuntimeException(e);
        }
        return data;
    }

    //Hàm này dùng cho trường hợp nhiều Field trong File Excel
    public int getColumns() {
        try {
            row = sheet.getRow(0);
            return row.getLastCellNum();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw (e);
        }
    }

    //Get last row number (lấy vị trí dòng cuối cùng tính từ 0)
    public int getLastRowNum() {
        return sheet.getLastRowNum();
    }

    //Lấy số dòng có data đang sử dụng
    public int getPhysicalNumberOfRows() {
        return sheet.getPhysicalNumberOfRows();
    }

    //đọc Excel data với số dòng tùy ý
    public Object[][] getDataHashTable(String excelPath, String sheetName, int startRow, int endRow) {
        System.out.println("Excel Path: " + excelPath);
        Object[][] data = null;

        try {
            File f = new File(excelPath);
            if (!f.exists()) {
                try {
                    System.out.println("File Excel path not found.");
                    throw new IOException("File Excel path not found.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            fileIn = new FileInputStream(excelPath);

            workbook = new XSSFWorkbook(fileIn);

            sheet = workbook.getSheet(sheetName);

            int rows = getLastRowNum();
            int columns = getColumns();

            System.out.println("Row: " + rows + " - Column: " + columns);
            System.out.println("StartRow: " + startRow + " - EndRow: " + endRow);

            data = new Object[(endRow - startRow) + 1][1];
            Hashtable<String, String> table = null;
            for (int rowNums = startRow; rowNums <= endRow; rowNums++) {
                table = new Hashtable<>();
                for (int colNum = 0; colNum < columns; colNum++) {
                    table.put(getCellData(colNum, 0), getCellData(colNum, rowNums));
                }
                data[rowNums - startRow][0] = table;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
