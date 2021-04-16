package com.trilogyed.noteservice.controller;

import com.trilogyed.noteservice.dao.NoteDao;
import com.trilogyed.noteservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class NoteController {
    NoteDao noteDao;
    @Autowired
    public NoteController(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @RequestMapping(value = "/notes", method = RequestMethod.GET)
    public List<Note> getAllNote(){
        return noteDao.getAllNotes();
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.GET)
    public Note getNoteById(@PathVariable int id){
        return noteDao.getNoteById(id);
    }

    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.GET )
    public List<Note> getNotesByBook(@PathVariable int book_id){
        return noteDao.getNotesByBook(book_id);
    }

    @RequestMapping(value = "/notes", method = RequestMethod.POST)
    public Note createNote(@RequestBody @Valid Note note){
        return noteDao.createNote(note);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.PUT)
    public void updateNote(@PathVariable int id, @RequestBody @Valid Note note){
        noteDao.updateNote(note);
    }

    @RequestMapping(value = "/notes/{id}", method = RequestMethod.DELETE)
    public void deleteNote(@PathVariable int id){
        noteDao.deleteNote(id);
    }


    @RequestMapping(value = "/notes/book/{book_id}", method = RequestMethod.DELETE)
    public void deleteNoteByBookId(@PathVariable int book_id){
        noteDao.deleteNoteByBookId(book_id);
    }

}
