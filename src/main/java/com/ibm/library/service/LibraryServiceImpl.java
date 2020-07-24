package com.ibm.library.service;

import java.util.Collection;
import java.util.Iterator;

//import com.ibm.library.model.Book;
import com.ibm.library.model.BookData;
//import com.ibm.library.repo.LibraryRepo;
import com.ibm.library.endpoint.BookInventoryEndpoint;
import com.ibm.library.exception.BadValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class LibraryServiceImpl implements LibraryService {
	
	@Autowired
//	private LibraryRepo libraryRepo;
	private BookInventoryEndpoint bookInventoryEndpoint;
	
	private final Logger logger = LoggerFactory.getLogger(LibraryServiceImpl.class);


	public LibraryServiceImpl() {
	}

	@Override
	   public BookData getBook(String isbn) {

	      logger.info("Entered LibraryServiceImpl.getBook().  isbn=" + isbn);

	      BookData book = this.bookInventoryEndpoint.getBook(isbn);
//	      BookData book = new BookData("FICTION", isbn, "Some Book", "Some Author");

	      logger.info("Leaving LibraryServiceImpl.getBook().  isbn=" + isbn);
	      return book;
	   }


	@Override
//	public Collection<Book> getBooks() {
	public Collection<BookData> getBooks() {
		logger.info("Entered LibraryServiceImpl.getBooks()");
		
//		Collection<Book> books = this.libraryRepo.getBooks();
		Collection<BookData> books = this.bookInventoryEndpoint.getBooks();
		
//		double lateFee = 0.0;
//		int numDaysLate = 0;
//		Book book = null;
//		Iterator<Book> booksIter = books.iterator();
//		while (booksIter.hasNext()) {
//			book = booksIter.next();
//			if (book.getAuthor().equals("Tom Smith")) {
//				book.setAuthor(book.getAuthor() + "- CHECKED");
//			}
//			
//			if (book.getTitle().length() % 2 == 0) {
//				numDaysLate = -1 * book.getTitle().length();
//			}
//			else {
//				numDaysLate = book.getTitle().length();
//			}
//			
//			try {
//				
//				lateFee = book.calculateLateFee(numDaysLate);
//				book.setNotes("Fee is " + lateFee);
//				
//			} catch (BadValue badValue) {
//				
//				book.setNotes(badValue.getMessage());
//				
//			}
//			
//		}
		logger.info("Leaving LibraryServiceImpl.getBooks()");
		
		return books;
	}
}
