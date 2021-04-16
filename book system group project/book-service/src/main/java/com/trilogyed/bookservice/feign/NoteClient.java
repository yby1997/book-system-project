package com.trilogyed.bookservice.feign;

import com.trilogyed.bookservice.models.Note;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "note-service")
public interface NoteClient {
    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    public Note getNoteById(@PathVariable int id);

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET)
    public List<Note> getNotesByBook(@PathVariable int book_id);

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    public void updateNote(@RequestBody @Valid Note note, @PathVariable int id);

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable int id);

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public Note createNote(@RequestBody @Valid Note note);

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.DELETE)
    public void deleteNoteByBookId(@PathVariable int book_id);
}