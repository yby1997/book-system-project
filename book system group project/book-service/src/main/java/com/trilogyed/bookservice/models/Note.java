package com.trilogyed.bookservice.models;

import java.util.Objects;

public class Note {
    private int id;
    private int bookId;
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note1 = (Note) o;
        return id == note1.id && bookId == note1.bookId && Objects.equals(note, note1.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookId, note);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", note='" + note + '\'' +
                '}';
    }
}
