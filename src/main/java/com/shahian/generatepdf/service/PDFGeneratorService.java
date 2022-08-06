package com.shahian.generatepdf.service;

import com.itextpdf.text.pdf.BaseFont;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shahian.generatepdf.entity.Book;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.awt.Color.GRAY;

@Service
public class PDFGeneratorService {
    public static final String FONT = "src/main/resources/Font/ARIALUNI.TTF";
    public static final String ARABIC = "\u0627\u0644\u0633\u0639\u0631 \u0627\u0644\u0627\u062c\u0645\u0627\u0644\u064a";

    public static ByteArrayInputStream exportPdf(HttpServletResponse response, List<Book> bookList) throws IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, response.getOutputStream());
        PdfPTable pdfPTable = new PdfPTable(3);
        pdfPTable.setWidthPercentage(60);
        pdfPTable.setWidths(new int[]{1, 3, 3});
        Font fontTitle = FontFactory.getFont(FontFactory.defaultEncoding);
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

        fontTitle.setSize(18);

        Paragraph paragraph = new Paragraph("GeneratePDF", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
        fontParagraph.setSize(12);


        PdfPCell headerCell;
        headerCell = new PdfPCell(new Phrase("Name", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(headerCell);
        headerCell = new PdfPCell(new Phrase("Author", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(headerCell);
        headerCell = new PdfPCell(new Phrase("Description", headFont));
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPTable.addCell(headerCell);
        PdfPCell rowCell;
        for(Book book:  bookList ){
            rowCell = new PdfPCell(new Phrase(book.getName().toString()));
            rowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(rowCell);
            rowCell = new PdfPCell(new Phrase(book.getAuthor().toString()));
            rowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(rowCell);
            rowCell = new PdfPCell(new Phrase(book.getDescription().toString()));
            rowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(rowCell);
        }
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(paragraph);
        document.add(pdfPTable);
        document.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
    public static ByteArrayInputStream exportPersianPdf(HttpServletResponse response, List<Book> bookList) throws IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, response.getOutputStream());
        Font f = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(ARABIC, f));
        Font fontTitle = FontFactory.getFont(FontFactory.defaultEncoding);
        fontTitle.setSize(18);
        PdfPTable pdfPTableTitle =createTitle( phrase, f);
        List<String> headerTitles = new ArrayList<String>();
        headerTitles.addAll(Arrays.asList("نام کتاب", "ناشر", "توضیحات"));
        PdfPTable pdfPTable = createTableHeader(3, headerTitles, phrase, f);
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        PdfPCell rowCell;
        for (Book book : bookList) {
            rowCell = new PdfPCell(new Phrase(book.getName().toString(),f));
            rowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            rowCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            pdfPTable.addCell(rowCell);
            rowCell = new PdfPCell(new Phrase(book.getAuthor().toString(),f));
            rowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            rowCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            pdfPTable.addCell(rowCell);
            rowCell = new PdfPCell(new Phrase(book.getDescription().toString(),f));
            rowCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            rowCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            rowCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            pdfPTable.addCell(rowCell);
        }
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(pdfPTableTitle);
        document.add(pdfPTable);
        document.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private static PdfPTable createTitle(Phrase phrase, Font f) {
        PdfPTable pdfPTableTitle = new PdfPTable(1);
        PdfPCell titleCell = new PdfPCell(phrase);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setVerticalAlignment(Element.ALIGN_TOP);
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setPadding(10);
        //titleCell.setFixedHeight(10.5f);

        pdfPTableTitle.setWidthPercentage(60);
        pdfPTableTitle.setWidths(new int[]{20});
        pdfPTableTitle.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        Paragraph paragraph = new Paragraph("گزارش تستی مربوط به پاراگراف", f);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        titleCell.addElement(paragraph);
        pdfPTableTitle.addCell(titleCell);
        return pdfPTableTitle;
    }

    private static PdfPTable createTableHeader(int i, List<String> headerTitles, Phrase phrase, Font f) {
        PdfPTable pdfPTable = new PdfPTable(i);
        pdfPTable.setWidthPercentage(60);
        pdfPTable.setWidths(new int[]{3, 3, 3});
        pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        headerTitles.stream().forEach(x -> {
            PdfPCell headerCell = new PdfPCell(phrase);
            headerCell = new PdfPCell(new Phrase(x.toString(), f));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(GRAY);
            pdfPTable.addCell(headerCell);
        });
        return pdfPTable;
    }

}
