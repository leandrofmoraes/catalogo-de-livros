package br.com.alura.literalura.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.literalura.model.Language;

import java.util.Optional;

public interface LanguageRepository extends JpaRepository<Language, Long> {
  Optional<Language> findByLanguage(String language);
}
