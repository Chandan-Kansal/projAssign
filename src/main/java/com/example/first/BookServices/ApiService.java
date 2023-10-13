package com.example.first.BookServices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.first.Dao.ApiRespository;
import com.example.first.Entities.Book;

public class ApiService {
	@Autowired
    private ApiRespository apiRespository;

    public List<Book> findAll() {
        return (List<Book>) this.apiRespository.findAll();
    }
    
    public void addstate(Book data) {
        this.apiRespository.save(data);
    }
}
