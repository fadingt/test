package cn.com.agree.openldap;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * jxlдExcel
 * 
 * @author jianggujin
 * 
 */
public class JxlWriteDemo
{
   public static void main(String[] args) throws IOException, WriteException
   {
      File xlsFile = new File("jxl.xls");
      // ����һ��������
      WritableWorkbook workbook = Workbook.createWorkbook(xlsFile);
      // ����һ��������
      WritableSheet sheet = workbook.createSheet("sheet1", 0);
      for (int row = 0; row < 10; row++)
      {
         for (int col = 0; col < 10; col++)
         {
            // ���������������
            sheet.addCell(new Label(col, row, "data" + row + col));
         }
      }
      workbook.write();
      workbook.close();
   }
}