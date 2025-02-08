package DTF98.MaxInXLSX;

import DTF98.MaxInXLSX.service.ExcelService;
import DTF98.MaxInXLSX.util.ExcelCreator;
import DTF98.MaxInXLSX.util.ExcelReader;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExcelServicePerformanceTest {
    private final ExcelService excelService = new ExcelService();

    @SneakyThrows
    @Test
    void testPerformanceFor100Elements() {
        testPerformance("testData100.xlsx", 100, 5);
    }

    @SneakyThrows
    @Test
    void testPerformanceFor1000Elements() {
        testPerformance("testData1000.xlsx", 1000, 5);
    }

    @SneakyThrows
    @Test
    void testPerformanceFor5000Elements() {
        testPerformance("testData5000.xlsx", 5000, 5);
    }

    @SneakyThrows
    @Test
    void testPerformanceFor10000Elements() {
        testPerformance("testData10000.xlsx", 10000, 5);
    }

    @SneakyThrows
    @Test
    void testPerformanceFor100000Elements() {
        testPerformance("testData100000.xlsx", 100000, 5);
    }

    @SneakyThrows
    private void testPerformance(String filePath, int numberOfElements, int n) {
        ExcelCreator.createRandomNumbersExcelFile(numberOfElements,filePath);
        List<Integer> numbers = ExcelReader.readNumbersFromExcel(filePath);

        Method methodSingle = ExcelService.class.getDeclaredMethod("findNthMaxSingleThread", List.class, int.class);
        methodSingle.setAccessible(true);
        long startTimeSingleThread = System.nanoTime();
        int resultSingleThread = (int) methodSingle.invoke(excelService, numbers, n);
        long endTimeSingleThread = System.nanoTime();
        long durationSingleThread = (endTimeSingleThread - startTimeSingleThread) / 1000000;

        Method methodMulti = ExcelService.class.getDeclaredMethod("findNthMaxMultiThread", List.class, int.class);
        methodMulti.setAccessible(true);
        long startTimeMultiThread = System.nanoTime();
        int resultMultiThread = (int) methodMulti.invoke(excelService, numbers, n);
        long endTimeMultiThread = System.nanoTime();
        long durationMultiThread = (endTimeMultiThread - startTimeMultiThread) / 1000000;

        assertEquals(resultSingleThread, resultMultiThread, "Результаты однопоточного и многопоточного методов должны совпадать");

        System.out.println("Количество элементов: " + numberOfElements);
        System.out.println("Однопоточный метод: " + durationSingleThread + " мс");
        System.out.println("Многопоточный метод: " + durationMultiThread + " мс");
        System.out.println("----------------------------------------");
    }
}
