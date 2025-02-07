package DTF98.MaxInXLSX.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ExcelCreator {
    //Метод для тестирования
    public static void createRandomNumbersExcelFile(int numberOfElements, String name) {
        File file = new File(name);

        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Старый файл удален.");
            } else {
                System.out.println("Не удалось удалить старый файл.");
                return;
            }
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Numbers");

            Random random = new Random();
            for (int i = 0; i < numberOfElements; i++) {
                Row row = sheet.createRow(i);
                Cell cell = row.createCell(0);
                int randomNumber = random.nextInt(1000);
                cell.setCellValue(randomNumber);
            }

            try (FileOutputStream fileOut = new FileOutputStream(file)) {
                workbook.write(fileOut);
                System.out.println("Файл успешно создан: " + file.getPath());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при создании файла: " + e.getMessage());
        }

    }
}
