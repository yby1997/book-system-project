package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.models.Note;

import java.util.List;

public interface NoteDao {
    Note createNote (Note note);
    Note getNoteById(int id);
    List<Note> getNotesByBook (int book_id);
    List<Note> getAllNotes();
    void updateNote(Note note);
    void deleteNote(int id);
    void deleteNoteByBookId(int book_id);

}
