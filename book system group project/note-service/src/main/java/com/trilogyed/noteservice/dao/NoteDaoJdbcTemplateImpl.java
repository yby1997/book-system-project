package com.trilogyed.noteservice.dao;

import com.trilogyed.noteservice.models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class NoteDaoJdbcTemplateImpl implements NoteDao{
    private static final String INSERT_NOTE =
            "insert into note (book_id, note) values (?, ?)";
    private static final String SELECT_NOTE_BY_ID=
            "select * from note where note_id = ?";
    private static final String SELECT_NOTE_BY_BOOK=
            "select * from note where book_id = ?";
    private static final String SELECT_NOTE =
            "select * from note";
    private static final String UPDATE_NOTE =
            "update note set book_id = ?, note = ? where note_id = ?";
    private static final String DELETE_NOTE =
            "delete from note where note_id = ?";
    private static final String DELETE_NOTE_BY_BOOK_ID =
            "delete from note where book_id = ?";
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public NoteDaoJdbcTemplateImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Note createNote(Note note) {
        jdbcTemplate.update(INSERT_NOTE,
                note.getBookId(),
                note.getNote());
        int id = jdbcTemplate.queryForObject("select last_insert_id()", Integer.class);
        note.setId(id);
        return note;
    }

    @Override
    public Note getNoteById(int id) {
        try{
            return jdbcTemplate.queryForObject(SELECT_NOTE_BY_ID, this::mapRowToNote, id);

        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    @Override
    public List<Note> getNotesByBook(int book_id) {
        return jdbcTemplate.query(SELECT_NOTE_BY_BOOK,this::mapRowToNote,book_id);
    }

    @Override
    public List<Note> getAllNotes() {
        return jdbcTemplate.query(SELECT_NOTE, this::mapRowToNote);
    }

    @Override
    public void updateNote(Note note) {
        jdbcTemplate.update(UPDATE_NOTE,
                note.getBookId(),
                note.getNote(),
                note.getId());
    }

    @Override
    public void deleteNote(int id) {
        jdbcTemplate.update(DELETE_NOTE, id);
    }

    @Override
    public void deleteNoteByBookId(int book_id) {
        jdbcTemplate.update(DELETE_NOTE_BY_BOOK_ID, book_id);
    }

    private Note mapRowToNote (ResultSet rs, int rowNum) throws SQLException{
        Note note = new Note();
        note.setId(rs.getInt("note_id"));
        note.setBookId(rs.getInt("book_id"));
        note.setNote(rs.getString("note"));
        return note;
    }
}
