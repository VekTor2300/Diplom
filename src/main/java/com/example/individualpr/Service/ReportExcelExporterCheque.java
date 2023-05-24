package com.example.individualpr.Service;

import com.example.individualpr.Models.Cheque;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReportExcelExporterCheque {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Cheque> listCheque;

    public ReportExcelExporterCheque(List<Cheque> listCheque) {
        this.listCheque = listCheque;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("ReportCheque");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Код чека", style);
        createCell(row, 1, "Количество проданного товара", style);
        createCell(row, 2, "Время подтверждения оплаты", style);
        createCell(row, 3, "Покупатель", style);
        createCell(row, 4, "Продавец", style);
        createCell(row, 5, "ИТОГ", style);

    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Cheque cheque : listCheque) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, cheque.getId().toString(), style);
            createCell(row, columnCount++, cheque.getQuantity().toString(), style);
            createCell(row, columnCount++, cheque.getTimesell().toString(), style);
            createCell(row, columnCount++, cheque.getClient().getUsers().getSurname() + " " + cheque.getClient().getUsers().getName() + " " + cheque.getClient().getUsers().getMiddlename(), style);
            createCell(row, columnCount++, cheque.getEmployees().getUser().getSurname() + " " + cheque.getEmployees().getUser().getName() + " " + cheque.getEmployees().getUser().getMiddlename(), style);
            createCell(row, columnCount++, cheque.getTotalCost().toString(), style);

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }
}
