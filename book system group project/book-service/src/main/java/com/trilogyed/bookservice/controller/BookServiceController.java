package com.trilogyed.bookservice.controller;

import com.trilogyed.bookservice.dao.BookDao;
import com.trilogyed.bookservice.models.BookViewModel;
import com.trilogyed.bookservice.models.Note;
import com.trilogyed.bookservice.service.BookServiceLayer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookServiceController {
    public static final String EXCHANGE = "queue-demo-exchange";
    public static final String ROUTING_KEY = "note.list.add.book.controller";
    BookServiceLayer bookServiceLayer;
    private RabbitTemplate rabbitTemplate;

    @Autowired
    public BookServiceController(BookServiceLayer bookServiceLayer, RabbitTemplate rabbitTemplate) {
        this.bookServiceLayer = bookServiceLayer;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public BookViewModel createModel(@RequestBody @Valid BookViewModel bookViewModel) {
//        Note msg = new Note();
//        msg.setNote(bookViewModel.getNote().toString());
//        msg.setBookId(bookViewModel.getId());
//        msg.setId(bookViewModel.getNote().get(0).getId());
        BookViewModel returnVal = bookServiceLayer.createModel(bookViewModel);
        bookViewModel.getNote().stream().forEach(n->{
            n.setBookId(returnVal.getId());
            System.out.println("sending message....");
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, n);
            System.out.println("message sent");
            System.out.println("note created");
        });

        return returnVal;
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
        bookViewModel.getNote().stream().forEach(n->{
            System.out.println("sending message....");
            rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, n);
            System.out.println("message sent");
            System.out.println("note created");
        });
        bookServiceLayer.updateModel(bookViewModel);
    }

    @RequestMapping(value = "/books/{id}", method = RequestMethod.DELETE)
    public void deleteModel(@PathVariable int id) {
        bookServiceLayer.deleteModel(id);
    }

}
