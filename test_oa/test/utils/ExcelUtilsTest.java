package utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExcelUtilsTest {

    @Test
    void testReadExcelCell() throws IOException {
        String path = "D:\\AOM\\ZT_ITD_AOM_BLG_0001 问题反馈 v1.0.0 20190704.xlsx";
        new ExcelUtils().readExcelCell(path,205,14);
    }

    @Test
    void XSSFReadCell() {
        String path ="";
//        ExcelUtils.XSSFReadCell(1,1,new File(path));
    }
}