package com.mkyong.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.mkyong.error.validator.Author;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@ApiModel(description = "All details about the Book. ")
public class Book {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The book id")
    private Long id;

    @NotEmpty(message = "Please provide a name")
    @ApiModelProperty(notes = "The book name")
    private String name;

    @Author
    @NotEmpty(message = "Please provide a author")
    @ApiModelProperty(notes = "The book author")
    private String author;

    @NotNull(message = "Please provide a price")
    @DecimalMin("1.00")
    @ApiModelProperty(notes = "The book price")
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

}
