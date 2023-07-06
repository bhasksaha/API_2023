package com.pojo;

import java.util.List;

public class Addbook {
    public String  userId;

    public List<Book> collectionOfBooks;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Book> getCollectionOfIsbns() {
        return collectionOfBooks;
    }

    public void setCollectionOfIsbns(List<Book> collectionOfBooks) {
        this.collectionOfBooks = collectionOfBooks;
    }



}
