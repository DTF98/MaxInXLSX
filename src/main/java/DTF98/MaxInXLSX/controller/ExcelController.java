package DTF98.MaxInXLSX.controller;

import DTF98.MaxInXLSX.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Excel Controller", description = "Контроллер для нахождения максимального N-ого числа в .xlsx")
@RequiredArgsConstructor
public class ExcelController {
    private final ExcelService excelService;

    @Operation(summary = "Получить N-ное максимальное число из файла")
    @GetMapping("/max-number")
    public int getNthMaxNumber(
            @Parameter(description = "Путь к файлу .xlsx") @RequestParam @NotNull String filePath,
            @Parameter(description = "Номер максимального числа (N)") @RequestParam @NotNull @Positive int n) {
        return excelService.getMaxNthFromExcel(filePath, n);
    }
}
