package com.example.first.BookServices;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.first.Dao.BookRespository;
import com.example.first.Entities.Book;

@Component
public class Bookservice {
    @Autowired
    private BookRespository bookrespository;

//    public List<Book> findAll() {
//        return (List<Book>) this.bookrespository.findAll();
//    }
//
//    public Book findById(int id) {
//        Book book = null;
//        try {
//            book = this.bookrespository.findById(id);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return book;
//    }

    public void addBook(Book book) {
        this.bookrespository.save(book);
    }
    
    public List<Book> findAll() {
        return (List<Book>) this.bookrespository.findAll();
    }

	

//    public void deleteBook(int id) {
//        this.bookrespository.deleteById(id);
//    }
//
//    public void updateBook(Book book, int id) {
//        this.bookrespository.save(book);
//    }
}
