package com.hatester.helpers;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelHelper {
    private FileInputStream fileIn;
    private FileOutputStream fileOut;
    private Workbook wb;
    private Sheet sh;
    private Row row;
    private Cell cell;
    private CellStyle cellStyle;
    private Color color;
    private String excelFilePath;
    private Map<String, Integer> columns = new HashMap<>();

    public void setExcelFile(String excelPath, String sheetName) {
        try {
            File f = new File(excelPath);

            if (!f.exists()) {
                System.out.println("File doesn't exist.");
            }

            fileIn = new FileInputStream(excelPath);
            wb = WorkbookFactory.create(fileIn);
            sh = wb.getSheet(sheetName);

            if (sh == null) {
                throw new Exception("Sheet name doesn't exist.");
            }

            this.excelFilePath = excelPath;

            //adding all the column header names to the map 'columns'
            sh.getRow(0).forEach(cell -> {
                columns.put(cell.getStringCellValue(), cell.getColumnIndex());
            });

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getCellData(int columnIndex, int rowIndex) {
        try {
            cell = sh.getRow(rowIndex).getCell(columnIndex);

            String cellData = null;

            switch (cell.getCellType()) {  //Kiểm tra kiểu dữ liệu trong ô Excel (vì Excel có thể chứa String, Number, Date, Boolean, Blank…)
                case STRING:  //Nếu ô là text → đọc trực tiếp giá trị chuỗi.
                    cellData = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellData = String.valueOf(cell.getDateCellValue());  //Nếu là ngày tháng → lấy giá trị Date
                    } else {
                        cellData = String.valueOf((long) cell.getNumericCellValue());  //Nếu là số → ép về long rồi convert sang chuỗi
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
        return getCellData(columns.get(columnName), rowIndex);  //columns.get(columnName) sẽ trả về columnIndex
    }

    //set by column index
//    public void setCellData(String text, int columnIndex, int rowIndex) {
//        try {
//            row = sh.getRow(rowIndex);
//            if (row == null) {
//                row = sh.createRow(rowIndex);
//            }
//            cell = row.getCell(columnIndex);
//
//            if (cell == null) {
//                cell = row.createCell(columnIndex);
//            }
//            cell.setCellValue(text);
//
//            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
//            style.setFillPattern(FillPatternType.NO_FILL);
//            style.setAlignment(HorizontalAlignment.CENTER);
//            style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//            cell.setCellStyle(style);
//
//            fileOut = new FileOutputStream(excelFilePath);
//            wb.write(fileOut);
//            fileOut.flush();
//            fileOut.close();
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }

    //set by column name
//    public void setCellData(String text, String columnName, int rowIndex) {
//        try {
//            row = sh.getRow(rowIndex);
//            if (row == null) {
//                row = sh.createRow(rowIndex);
//            }
//            cell = row.getCell(columns.get(columnName));
//
//            if (cell == null) {
//                cell = row.createCell(columns.get(columnName));
//            }
//            cell.setCellValue(text);
//
//            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
//            style.setFillPattern(FillPatternType.NO_FILL);
//            style.setAlignment(HorizontalAlignment.CENTER);
//            style.setVerticalAlignment(VerticalAlignment.CENTER);
//
//            cell.setCellStyle(style);
//
//            fileOut = new FileOutputStream(excelFilePath);
//            wb.write(fileOut);
//            fileOut.flush();
//            fileOut.close();
//        } catch (Exception e) {
//            e.getMessage();
//        }
//    }
}
