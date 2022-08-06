package com.shahian.generatepdf.controller;


import com.shahian.generatepdf.entity.Book;
import com.shahian.generatepdf.service.PDFGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
 private final PDFGeneratorService pdfGeneratorService;

	public BookController(PDFGeneratorService pdfGeneratorService) {
		this.pdfGeneratorService = pdfGeneratorService;
	}
	@GetMapping(value = "/v1/Books")
	public ResponseEntity<?> generatePDF (HttpServletResponse response) throws IOException {
		List<Book> bookList=new ArrayList<>();
		var book1 = new Book("Spring in Action1", "Book description1", "Craig Walls1");
		bookList.add(book1);
		var book2 = new Book("Spring in Action2", "Book description2", "Craig Walls2");
		bookList.add(book2);
		var book3= new Book("Spring in Action3", "Book description3", "Craig Walls3");
		bookList.add(book3);
		ByteArrayInputStream byteArrayInputStream =  this.pdfGeneratorService.exportPdf(response, bookList);
		byte[] targetArray = new byte[byteArrayInputStream.available()];
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=financialStatement.pdf; charset=UTF-8")
				.contentType(MediaType.APPLICATION_PDF)
				.body(targetArray);
	}

	@GetMapping(value = "/v2/Books")
	public ResponseEntity<?> generatePersianPDF (HttpServletResponse response) throws IOException {
		List<Book> bookList=new ArrayList<>();
		var book1 = new Book("آموزش اسپرینگ1", "توضیحات 1", "ناشر 1");
		bookList.add(book1);
		var book2 = new Book("آموزش اسپرینگ2", "توضیحات 2", "ناشر 2");
		bookList.add(book2);
		var book3= new Book("آموزش اسپرینگ3", "توضیحات 3", "ناشر 3");
		bookList.add(book3);
		ByteArrayInputStream byteArrayInputStream =  this.pdfGeneratorService.exportPersianPdf(response, bookList);
		byte[] targetArray = new byte[byteArrayInputStream.available()];
		return ResponseEntity
				.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=financialStatement.pdf; charset=UTF-8")
				.contentType(MediaType.APPLICATION_PDF)
				.body(targetArray);
	}

}
