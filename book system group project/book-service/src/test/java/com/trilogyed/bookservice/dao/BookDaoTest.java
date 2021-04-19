package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.models.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BookDaoTest {
    @Autowired
    BookDao bookDao;

    @Before
    public void setUp() throws Exception{
        List<Book> books =bookDao.getAllBook() ;
        for(Book book :books){
            bookDao.deleteBook(book.getId());
        }
    }

    @Test
    public void shouldAddGetDeleteBook(){
        Book book1= new Book();
        book1.setTitle("idk");
        book1.setAuthor("larry");
        book1=bookDao.createBook(book1);
        Book book2 = bookDao.getBookById(book1.getId());
        assertEquals(book2,book1);
        bookDao.deleteBook(book1.getId());
        book2=bookDao.getBookById(book1.getId());
        assertNull(book2);
    }



    @Test
    public void getAllBook() {
        Book book1= new Book();
        book1.setTitle("idk");
        book1.setAuthor("larry");
        book1=bookDao.createBook(book1);

        Book book2= new Book();
        book2.setTitle("idk");
        book2.setAuthor("larry");
        book2=bookDao.createBook(book2);

        List<Book> books= bookDao.getAllBook();
        assertEquals(2,books.size());
    }

    @Test
    public void updateBook() {
        Book book1= new Book();
        book1.setTitle("idk");
        book1.setAuthor("larry");
        book1=bookDao.createBook(book1);

        book1.setTitle("idk");
        book1.setAuthor("Kanmani");
        bookDao.updateBook(book1);
        Book book2 = bookDao.getBookById(book1.getId());
        assertEquals(book2,book1);
    }


}