package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

class ExcelPosition {
    private String rowNumber;
    private int colNumber;

    ExcelPosition(String rowNumber, int colNumber) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
    }

    ExcelPosition(String position) {
        this.rowNumber = ExcelUtils.analysisPosition(position).getRowNumber();
        this.colNumber = ExcelUtils.analysisPosition(position).getColNumber();
    }

    public String getRowNumber() {
        return this.rowNumber;
    }

    public int getColNumber() {
        return this.colNumber;
    }
}

class CellStringException extends RuntimeException {
    public CellStringException() {
        super();
    }

    public CellStringException(String s) {
        super(s);
    }
}

public class ExcelUtils {
    public static void main(String[] args) {
//        readExcelCell("D:/AOM/ZT_ITD_AOM_BLG_0001 问题反馈 v1.0.0 20190704.xlsx", 3, 3);
//        String filepath = "D:/AOM/ZT_ITD_AOM_BLG_0001 问题反馈 v1.0.0 20190704.xlsx";
//        XSSFReadCell(4, 4, 0, new File(filepath));
        analysisPosition("B133");
        String position = "EaA11";
        analysisPosition(position);
        String b = "jsd-023";

    }

    public static ExcelPosition analysisPosition(String position) {
        String row;
        int col;
        if (ValidatorUtils.isCellString(position)) {
            row = ValidatorUtils.letterString(position).get(0).toUpperCase();
            col = ValidatorUtils.numberString(position).get(0).intValue();
            System.out.println("row:" + row + "\tcol:" + col);
        } else {
            throw new CellStringException("not a cell");
        }
        return new ExcelPosition(row, col);
    }

    //TODO 将rowNumber ColNumber改为 B12这样的一个参数
    public static void readExcelCell(String path, int rowNumber, int colNumber) throws IOException {
        int sheetNumber = 0;
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("file not found in path " + path);
        }
        String suffix = path.substring(path.lastIndexOf("."));
        if (suffix.equals("xlsx")) {
            XSSFReadCell(rowNumber, colNumber, sheetNumber, file);
        } else if (suffix.equals("xls")) {
            throw new RuntimeException("TODO: read xls file");
        } else {
            throw new RuntimeException("path is not SUPPORTED EXCEL file");
        }
        if (rowNumber < 0 || colNumber < 0) {
            throw new IndexOutOfBoundsException("rowNumber/colNumber < 0");
        }
    }
//    todo
    public static Sheet getSheet(){
        return null;
    }

    public static Cell XSSFReadCell(int rowNumber, int colNumber, int sheetNumber, File file) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        XSSFWorkbook wb = new XSSFWorkbook(bis);
        XSSFSheet sheet = wb.getSheetAt(sheetNumber);
        XSSFCell cell;
        XSSFRow row;
        if (rowNumber > sheet.getLastRowNum()) {
            throw new IndexOutOfBoundsException("sheet " + sheet.getSheetName() + " rowNumber should <= " + sheet.getLastRowNum());
        }
        row = sheet.getRow(rowNumber);
        if (colNumber > row.getLastCellNum()) {
            throw new IndexOutOfBoundsException("sheet " + sheet.getSheetName() + " colNumber should <= " + row.getLastCellNum() + " in row " + rowNumber);
        }
        cell = row.getCell(colNumber);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            System.out.println("(" + rowNumber + "," + colNumber + ")value is: " + cell.getStringCellValue());
        } else if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            System.out.println("(" + rowNumber + "," + colNumber + ")value is: " + cell.getNumericCellValue());
        } else {
            System.out.println("cell content is not a String");
        }
        return cell;
    }

    public static void getPosition(int rowNumber, int colNumber, int sheetNumber) {
        return;
    }

    //TODO 检查是否越界
    public static boolean checkBoundary(String position, XSSFSheet sheet) {
        position = "G200";
        return true;
    }
}

