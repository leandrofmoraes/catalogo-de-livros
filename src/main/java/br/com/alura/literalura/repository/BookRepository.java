package br.com.alura.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.literalura.model.Book;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
  Optional<Book> findByPublicId(Long publicId);
}
