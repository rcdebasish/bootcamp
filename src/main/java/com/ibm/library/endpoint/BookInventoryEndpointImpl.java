package com.ibm.library.endpoint;

import java.util.Collection;
import java.util.Arrays;
import com.ibm.library.model.BookData;
import com.ibm.library.service.LibraryServiceImpl;

import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;


@Component
public class BookInventoryEndpointImpl implements BookInventoryEndpoint {

   private RestTemplate restTemplate = new RestTemplate();
	
   private final Logger logger = LoggerFactory.getLogger(BookInventoryEndpointImpl.class);

//   private String bookInventoryEndpoint = "localhost:9002";
   @Value("${bookinventory.endpoint}")
   private String bookInventoryEndpoint;


   @Override
   public Collection<BookData> getBooks() {

      Collection<BookData> books = null;

      String bookInventoryRESTRequestURL = "http://" + bookInventoryEndpoint + "/bookinventory/books";

      BookData[] bookDataArray = this.restTemplate.getForObject(bookInventoryRESTRequestURL, BookData[].class);
 
      if ((bookDataArray != null) && (bookDataArray.length != 0)) {
         books = Arrays.asList(bookDataArray);
      }

      return books;
   }
   

      @Override
      @HystrixCommand(fallbackMethod = "getBook_fallBack", commandKey = "endpointGetBook", commandProperties = {
         @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
         @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "4"),
         @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
         @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75") })
      public BookData getBook(String isbn) {
         String bookInventoryRESTRequestURL = "http://" + bookInventoryEndpoint + "/bookinventory/book/" + isbn;
         BookData book = this.restTemplate.getForObject(bookInventoryRESTRequestURL, BookData.class);
         return book;
      }
      
      public BookData getBook_fallBack(String isbn) {
          logger.warn("!!!!!!!!!! IN FALLBACK.  getBook_fallBack isbn=" + isbn);
          BookData book = new BookData("FICTION", "CACHED BOOK DATA", "Some Book", "Some Author");
          return book;
       }



}