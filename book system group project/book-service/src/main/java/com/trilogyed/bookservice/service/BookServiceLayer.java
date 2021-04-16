package com.trilogyed.bookservice.service;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.feign.NoteClient;
import com.trilogyed.bookservice.models.Book;
import com.trilogyed.bookservice.models.BookViewModel;
import com.trilogyed.bookservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookServiceLayer {

    private BookDao bookDao;
    private NoteClient noteClient;

    @Autowired
    public BookServiceLayer(BookDao bookDao, NoteClient noteClient) {
        this.bookDao = bookDao;
        this.noteClient = noteClient;
    }

    public BookViewModel getBook(int book_id){
        Book book = bookDao.getBookById(book_id);
        BookViewModel bookViewModel = new BookViewModel();
        bookViewModel.setId(book.getId());
        bookViewModel.setTitle(book.getTitle());
        bookViewModel.setAuthor(book.getAuthor());
        bookViewModel.setNote(noteClient.getNotesByBook(book.getId()));
        return bookViewModel;
    }

    public List<BookViewModel> getAllBook(){
        List<Book> books = bookDao.getAllBook();
        List<BookViewModel> bookViewModels = new ArrayList<>();

        for(Book book :books){
            BookViewModel bookViewModel = new BookViewModel();
            bookViewModel.setId(book.getId());
            bookViewModel.setTitle(book.getTitle());
            bookViewModel.setAuthor(book.getAuthor());
            bookViewModel.setNote(noteClient.getNotesByBook(book.getId()));
            bookViewModels.add(bookViewModel);
        }
        return bookViewModels;
    }

    public BookViewModel createModel(BookViewModel bookViewModel){
        Book book = new Book();
        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());

        book = bookDao.createBook(book);
        bookViewModel.setId(book.getId());
        bookViewModel.setNote(noteClient.getNotesByBook(book.getId()));
        return bookViewModel;
    }

    public void updateModel (BookViewModel bookViewModel){
        Book book = new Book();
        book.setId(bookViewModel.getId());
        book.setTitle(bookViewModel.getTitle());
        book.setAuthor(bookViewModel.getAuthor());
        bookDao.updateBook(book);

       List<Note> notes = noteClient.getNotesByBook(bookViewModel.getId());
       for(Note note :notes){
           noteClient.deleteNoteByBookId(bookViewModel.getId());
       }
       for(int i=0; i<bookViewModel.getNote().size();i++){
           Note a = new Note();
           a.setId(bookViewModel.getNote().get(i).getId());
           a.setBookId(bookViewModel.getId());
           a.setNote(bookViewModel.getNote().get(i).getNote());
           noteClient.createNote(a);
       }
        // call note client to get all notes by book_id
        // call delete note by book_id method
        // call create new note method with notes coming from view model
        // loop if multiple notes
        //ToDo: each note in view model
        //send some request to the queue so that the note is added to the database

    }

    public void deleteModel(int book_id){
        bookDao.deleteBook(book_id);
        noteClient.deleteNoteByBookId(book_id);
    }
}
