package com.example.individualpr.Service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.individualpr.Models.RequestSupport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<RequestSupport> listOrder;

    public ReportExcelExporter(List<RequestSupport> listOrder) {
        this.listOrder = listOrder;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("ReportsOrder");

        Row row = sheet.createRow(0);//C какой строки начинается запись в екселе

        CellStyle style = workbook.createCellStyle();//стиль
        XSSFFont font = workbook.createFont();//
        font.setBold(true);//
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Код заявки", style);
        createCell(row, 1, "Дата и время заявки", style);
        createCell(row, 2, "Заявитель", style);
        createCell(row, 3, "Номер телефона", style);
        createCell(row, 4, "Адрес заявителя", style);
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

        for (RequestSupport order : listOrder) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, order.getCodeApp().toString(), style);
            createCell(row, columnCount++, order.getDateApp().toString(), style);
            createCell(row, columnCount++, order.getFio(), style);
            createCell(row, columnCount++, order.getNumberPhone().toString(), style);
            createCell(row, columnCount++, order.getAddress().toString(), style);
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
