package cn.com.agree.openldap;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * jxl��excel
 * 
 * @author jianggujin
 * 
 */
public class ExcelRead {
	private Workbook workbook = null;
	private static boolean HAS_HEADER_FLAG = false;

	public static boolean getHAS_HEADER_FLAG() {
		return HAS_HEADER_FLAG;
	}

	public static void setHAS_HEADER_FLAG(boolean hAS_HEADER_FLAG) {
		HAS_HEADER_FLAG = hAS_HEADER_FLAG;
	}

	public  void init(String fileName) {
		File xlsFile = new File(fileName);
		// ��ù���������
		try {
			workbook = Workbook.getWorkbook(xlsFile);
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static HashMap<String,String> readDEMapFromExcel(String fileName) {
		File xlsFile = new File(fileName);
		HashMap<String, String> de = new HashMap<>();
		
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(xlsFile);
			// ������й�����
			Sheet[] sheets = workbook.getSheets();
			// ����������
			if (sheets != null) {
				for (Sheet sheet : sheets) {
					// �������
					int rows = sheet.getRows();
					// �������
					int cols = sheet.getColumns();
					// ��ȡ����
//					System.out.println("������" + rows + " ������" + cols);
					int initRow = 0;
					if( HAS_HEADER_FLAG ) initRow++;
					
					if( cols == 2 ) {//����ӳ���
						for (int row = initRow; row < rows; row++) {
							for (int col = 0; col < cols; col++) {
								System.out.printf("%10s", sheet.getCell(col, row).getContents());
							}
							String key = sheet.getCell(0, row).getContents();
							String val = sheet.getCell(1, row).getContents();
							de.put(key, val);
							System.out.println();
						}
					}else {
						
					}
				}
			      //ʹ��Map.Entry����
//		        for(Entry<String, String> en : de.entrySet()){
//		            System.out.println(en.getKey()+ "=" + en.getValue());
//		        }
	
			}
			workbook.close();
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return de;
	}

	public static ArrayList<UsersData> readUsersDataFromExcel(String fileName) {
		File xlsFile = new File(fileName);
		ArrayList<UsersData> list = new ArrayList<UsersData>();		
		// ������й�����
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(xlsFile);
			Sheet[] sheets = workbook.getSheets();
			// ����������
			if (sheets != null) {
				for (Sheet sheet : sheets) {
					// �������
					int rows = sheet.getRows();
					// �������
					int cols = sheet.getColumns();
					// ��ȡ����
//					System.out.println("������" + rows + " ������" + cols);
					int initRow = 0;
					if( HAS_HEADER_FLAG ) initRow++;
					
					for (int row = initRow; row < rows; row++) {
							for (int col = 0; col < cols; col++) {
								System.out.printf("%10s", sheet.getCell(col, row).getContents());
							}
						UsersData ud = new UsersData();
						ud.setUserName(sheet.getCell(0, row).getContents());//�û�������
						ud.setUserId(sheet.getCell(1, row).getContents());//����
						ud.setUserMD5PWD(sheet.getCell(2, row).getContents());//MD5����
						ud.setPath(sheet.getCell(3, row).getContents());//��֯�ṹ·��
						ud.setRemark(sheet.getCell(4, row).getContents());//��֯�ṹ·������
						list.add(ud);
						System.out.println();
					}
				}
			}
		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		ExcelRead.setHAS_HEADER_FLAG(true);
		ExcelRead.readDEMapFromExcel("e:\\eclipse\\doc\\bumen_0912.xls");
		//List<UsersData> list = ExcelRead.readUsersDataFromExcel("e:\\eclipse\\doc\\all_users_0912.xls");
//		for(UsersData ud : list) {
//			 System.out.println(ud.getUserName()+" "+ud.getUserId()+" "+ud.getPath());
//		}
	}
}