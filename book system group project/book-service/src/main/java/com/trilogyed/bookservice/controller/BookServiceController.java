package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.models.BookViewModel;
import com.trilogyed.bookservice.service.BookServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookServiceController {
    BookServiceLayer bookServiceLayer;

    @Autowired
    public BookServiceController(BookServiceLayer bookServiceLayer) {
        this.bookServiceLayer = bookServiceLayer;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public BookViewModel createModel(@RequestBody @Valid BookViewModel bookViewModel) {
        return bookServiceLayer.createModel(bookViewModel);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.GET)
    public BookViewModel getModelById(@PathVariable int id) {
        return bookServiceLayer.getBook(id);
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<BookViewModel> getAllModels() {
        return bookServiceLayer.getAllBook();
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public void updateModel(@RequestBody @Valid BookViewModel bookViewModel, @PathVariable int id) {
        bookServiceLayer.updateModel(bookViewModel);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public void deleteModel(@PathVariable int id) {
        bookServiceLayer.deleteModel(id);
    }

}
