package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.models.Book;

import java.util.List;

public interface BookDao {
    Book createBook(Book book);
    Book getBookById(int id);
    List<Book> getAllBook();
    void updateBook (Book book);
    void deleteBook(int id);
}
