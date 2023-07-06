package com.pojo;

import java.util.List;

public class GetProfileBook {

    private String userId;
    private String username;
    private List<BookDetails> books;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookDetails> getBooks() {
        return books;
    }

    public void setBooks(List<BookDetails> books) {
        this.books = books;
    }

}
