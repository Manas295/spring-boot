package com.mkyong;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String author;
    private BigDecimal price;

    // avoid this "No default constructor for entity"
    public Book() {
    }

    public Book(Long id, String name, String author, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book(String name, String author, BigDecimal price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

	/*
	 * @Override public String toString() { return "Book{" + "id=" + id + ", name='"
	 * + name + '\'' + ", author='" + author + '\'' + ", price=" + price + '}'; }
	 */

}
