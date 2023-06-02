package com.test.maybank.demo.util.file;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PdfGenerator<T> {

    public byte[] generatePdf(List<T> dataList, List<String> headers) throws DocumentException {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Create the PDF document
        PdfWriter.getInstance(document, byteArrayOutputStream);

        // Open the document
        document.open();

        PdfPTable pdfPTable = new PdfPTable(headers.size());
        pdfPTable.setWidthPercentage(100);

        // Add headers to the table
        for (String header : headers) {
            pdfPTable.addCell(header);
        }

        // Add data rows to the table
        IntStream.range(0, dataList.size())
                .forEach(index -> {
                    T dataItem = dataList.get(index);
                    addRowData(pdfPTable, dataItem, headers);
                });

        Font smallFont = new Font(Font.FontFamily.TIMES_ROMAN, 6);

        // Set font and padding for table cells
        for (PdfPRow row : pdfPTable.getRows()) {
            for (PdfPCell cell : row.getCells()) {
                cell.setPadding(5);
                cell.setPhrase(new Paragraph(cell.getPhrase().getContent(), smallFont));
            }
        }

        // Add content to the document
        document.add(pdfPTable);

        // Close the document
        document.close();

        // Return the generated PDF as a byte array
        return byteArrayOutputStream.toByteArray();
    }

    void addRowData(PdfPTable pdfPTable, T dataItem, List<String> headers) {
        Class<?> dataClass = dataItem.getClass();

        for (String header : headers) {
            try {
                Field field = dataClass.getDeclaredField(header);
                field.setAccessible(true);
                Object value = field.get(dataItem);
                pdfPTable.addCell(String.valueOf(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle any exceptions that occur during field access
                e.printStackTrace();
            }
        }
    }
}
