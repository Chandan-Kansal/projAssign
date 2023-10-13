package com.example.first.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.example.first.Entities.Book;

@Component
public interface BookRespository extends CrudRepository<Book, Integer> {
    public Book findById(int id);
}
