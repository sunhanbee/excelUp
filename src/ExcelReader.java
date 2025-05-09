import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;


public class ExcelReader {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream(new File(""));
            Workbook workbook = new XSSFWorkbook(fis); //.xlsx용
            Sheet sheet = workbook.getSheetAt(0);

            for(Row row : sheet){
                for(Cell cell : row){
                    //
                    System.out.print("N/A\t");
                }
                System.out.println(); // 줄 바꿈
            }
            // workbook.close();
            fis.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
