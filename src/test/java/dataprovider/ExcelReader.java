package dataprovider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public static FileInputStream fis=null;
	public static Object[][] objectArray;
	private static XSSFWorkbook workbook=null;
	private static XSSFSheet sheet=null;

	public static Object[][] readingDataFromExcel(String file,String sheetName) throws IOException {
		fis=new FileInputStream(file);
		workbook=new XSSFWorkbook(fis);
		sheet=workbook.getSheet(sheetName);
		int rows=sheet.getLastRowNum()+1;
		int cols=sheet.getRow(0).getLastCellNum();
		objectArray=new Object[rows-1][cols];
		
		for (int i = 1; i < rows; i++) {
			for (int j = 0; j < cols; j++) {

				if(sheet.getRow(i).getCell(j).getCellType()!=null) {
					if(sheet.getRow(i).getCell(j).getCellType().name().equals("STRING")) {
						String value=sheet.getRow(i).getCell(j).getStringCellValue();
						objectArray[i-1][j]=value;  //here written value tabArray[i-1] to avoid Null value,Since we are fetching the data from First row but Object array stores the values from 0.  
						
					}
					else if(sheet.getRow(i).getCell(j).getCellType().name().equals("NUMERIC")) {
						double value=sheet.getRow(i).getCell(j).getNumericCellValue();
						objectArray[i-1][j]=value+"";
					}
				}
			
			}
		}
		
	return objectArray;
	}
}
