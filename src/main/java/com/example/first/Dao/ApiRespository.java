package com.example.first.Dao;

import org.springframework.data.repository.CrudRepository;

import com.example.first.Entities.Book;

public interface ApiRespository extends CrudRepository<Book,Integer>{
//	public State findByID(int id)
}
