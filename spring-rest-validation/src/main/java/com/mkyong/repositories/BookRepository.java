package com.mkyong.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mkyong.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
