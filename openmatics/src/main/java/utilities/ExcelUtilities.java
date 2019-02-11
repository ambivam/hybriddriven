package utilities;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dataproviders.ConfigFileReader;

public class ExcelUtilities {
	
	private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    
    private static XSSFRow row;
    
    public static void setExcelSheet(String excelFileName,String sheetName) throws Exception{
    	FileInputStream ExcelFile = new FileInputStream(System.getProperty("user.dir")+ConfigFileReader.getConfigFileReader().getExcelData()+excelFileName);
    	ExcelWBook = new XSSFWorkbook(ExcelFile);
    	ExcelWSheet = ExcelWBook.getSheet(sheetName);   	
    	
    }
    
    public static String getCellData(String tcName,String colName) throws Exception{
		
    	int firstRow = ExcelWSheet.getFirstRowNum();
    	int lastRow = ExcelWSheet.getLastRowNum();
    	int firstCell = ExcelWSheet.getRow(firstRow).getFirstCellNum();
    	int lastCell = ExcelWSheet.getRow(firstRow).getLastCellNum();
    	String value = null;   
    	   	 
    	
    	for(int i = firstRow; i<= lastRow; i++ ) {
    		
    		if(ExcelWSheet.getRow(i).getCell(ExcelWSheet.getRow(i).getFirstCellNum()).getStringCellValue().equalsIgnoreCase(tcName)) {
    			
    			for(int j = firstCell;j<=lastCell;j++) {
    				String temp = ExcelWSheet.getRow(firstRow).getCell(j).getStringCellValue();
    				if(ExcelWSheet.getRow(firstRow).getCell(j).getStringCellValue().equalsIgnoreCase(colName)) {
    					value = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
    					break;
    				}
    			}
    		}    		
    	}
    	
    	return value;    	
   }
    
    public static void main(String a[]) throws Exception {
    	
    	ExcelUtilities.setExcelSheet("ExcelData.xlsx", "ZFTeam");
    	System.out.println(ExcelUtilities.getCellData("TC02","TEAM"));
    	System.out.println(ExcelUtilities.getCellData("TC02","NAME"));
    }
    
    

}
