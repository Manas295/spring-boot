package com.mkyong.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mkyong.domain.Book;
import com.mkyong.error.BookNotFoundException;
import com.mkyong.error.BookUnSupportedFieldPatchException;
import com.mkyong.repositories.BookRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController("bookController")
@Validated
/*
 * If the @Validated is failed, it will trigger a ConstraintViolationException, we can override the error code 
 */
@Api(value = "Book Management System", description = "Operations pertaining to book in Book Management System")
public class BookController {

    @Autowired
    private BookRepository repository;
       // Find
    @ApiOperation(value = "View a list of available books", response = List.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/books")
    List<Book> findAll() {
        return repository.findAll();
    }

    // Save
    @ApiOperation(value = "Add an book")
    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    Book newBook(
     @ApiParam(value = "Book object store in database table", required = true) @Valid @RequestBody Book newBook) {
        return repository.save(newBook);
    }

    // Find
    @ApiOperation(value = "Get an book by Id")
    @GetMapping("/books/{id}")
    Book findOne(
    @ApiParam(value = "Book id from which book object will retrieve", required = true) @PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    // Save or update
    @ApiOperation(value = "Update an book")
    @PutMapping("/books/{id}")
    Book saveOrUpdate(
    	@ApiParam(value = "Update book object", required = true) @Valid @RequestBody Book newBook,
    	@ApiParam(value = "Book Id to update book object", required = true) @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {
                    x.setName(newBook.getName());
                    x.setAuthor(newBook.getAuthor());
                    x.setPrice(newBook.getPrice());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newBook.setId(id);
                    return repository.save(newBook);
                });
    }

    // update author only
    @ApiOperation(value = "Update an book author")
    @PatchMapping("/books/{id}")
    Book patch(
    	@ApiParam(value = "Update book object field", required = true) @RequestBody Map<String, String> update, 
    	@ApiParam(value = "Book Id to update book object param", required = true) @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String author = update.get("author");
                    if (!StringUtils.isEmpty(author)) {
                        x.setAuthor(author);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repository.save(x);
                    } else {
                        throw new BookUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new BookNotFoundException(id);
                });

    }

    @ApiOperation(value = "Delete an book")
    @DeleteMapping("/books/{id}")
    void deleteBook(
    		@ApiParam(value = "Book Id from which book object will delete from database table", required = true) @PathVariable Long id) {
        repository.deleteById(id);
    }

}
