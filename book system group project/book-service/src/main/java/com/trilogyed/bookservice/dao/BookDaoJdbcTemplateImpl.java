package com.trilogyed.bookservice.dao;

import com.trilogyed.bookservice.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class BookDaoJdbcTemplateImpl implements BookDao{
    private static final String INSERT_BOOK =
            "insert into book (title, author) values (?, ?)";
    private static final String SELECT_BOOK_BY_ID =
            "select * from book where book_id = ?";
    private static final String SELECT_ALL_BOOK =
            "select * from book";
    private static final String UPDATE_BOOK =
            "update book set title = ?, author = ? where book_id = ?";
    private static final String DELETE_BOOK =
            "delete from book where book_id = ?";
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    @Transactional
    public Book createBook(Book book) {
        jdbcTemplate.update(INSERT_BOOK,
                book.getTitle(),
                book.getAuthor());
        int id = jdbcTemplate.queryForObject("select last_insert_id()",Integer.class);
        book.setId(id);
        return book;
    }

    @Override
    public Book getBookById(int id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_BOOK_BY_ID, this::mapRowToBook,id);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Book> getAllBook() {
        return jdbcTemplate.query(SELECT_ALL_BOOK, this::mapRowToBook);
    }

    @Override
    public void updateBook(Book book) {
        jdbcTemplate.update(UPDATE_BOOK,
                book.getTitle(),
                book.getAuthor(),
                book.getId());
    }

    @Override
    public void deleteBook(int id) {
        jdbcTemplate.update(DELETE_BOOK, id);
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException{
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        return book;
    }
}
