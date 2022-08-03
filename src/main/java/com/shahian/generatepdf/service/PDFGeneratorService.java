package com.shahian.generatepdf.service;

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
import java.util.List;

@Service
public class PDFGeneratorService {

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


}
