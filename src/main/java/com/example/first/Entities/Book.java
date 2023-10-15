package com.example.first.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "State")
public class Book {
	@Id
    private int id;
    private char mot;
    private String src;
    private String dest;
	private float price;
    private float timereq;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public char getMot() {
		return mot;
	}
	public void setMot(char mot) {
		this.mot = mot;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getTimereq() {
		return timereq;
	}
	public void setTimereq(float timereq) {
		this.timereq = timereq;
	}    
}
	

