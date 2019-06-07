package com.mkyong;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mkyong.error.validator.Author;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty(message = "Please provide a name")
    private String name;

    @Author
    @NotEmpty(message = "Please provide a author")
    private String author;

    @NotNull(message = "Please provide a price")
    @DecimalMin("1.00")
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
