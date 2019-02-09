package Utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelUtils {

    public static void CreatingTestSummaryReport() throws Exception {
        FileOutputStream fos = new FileOutputStream(new File(CONSTANTS.TestSummaryFile));
        HSSFWorkbook workbook =  new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Result");
        sheet.createRow(0);
        sheet.getRow(0).createCell(0).setCellValue("Test Script Name");
        sheet.getRow(0).createCell(1).setCellValue("Result");
        workbook.write(fos);
        workbook.close();
        fos.close();
    }

    public static void addingTestResultstoTestSummary(String ScriptName, String Status) throws Exception {
        FileInputStream fis= new FileInputStream(new File(CONSTANTS.TestSummaryFile));
        HSSFWorkbook workbook= new HSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet("Result");
        int lastrow= sheet.getLastRowNum()+1;
        sheet.createRow(lastrow);
        sheet.getRow(lastrow).createCell(0).setCellValue(ScriptName);
        sheet.getRow(lastrow).createCell(1).setCellValue(Status);
        fis.close();
        FileOutputStream fos = new FileOutputStream(new File(CONSTANTS.TestSummaryFile));
        workbook.write(fos);
        fos.close();
    }








}
