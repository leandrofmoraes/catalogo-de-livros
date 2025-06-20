package br.com.alura.literalura.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.alura.literalura.client.ApiClient;
import br.com.alura.literalura.model.ApiData;
import br.com.alura.literalura.model.Book;
import br.com.alura.literalura.model.BookDTO;
import br.com.alura.literalura.model.Language;
import br.com.alura.literalura.model.Person;
import br.com.alura.literalura.repository.BookRepository;
import br.com.alura.literalura.repository.LanguageRepository;
import br.com.alura.literalura.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CLIService {
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private LanguageRepository languageRepository;

  @Autowired
  private ApiClient client;
  private ConverteDados conversor = new ConverteDados();

  public void listarLivros() {
    System.out.println("Lista de livros: ");
    bookRepository.findAll().stream()
        .forEach(System.out::println);
  }

  public void buscarLivroPorTitulo(String nomeLivro) {
    String json = client.getAPIData(nomeLivro);
    ApiData apiData = conversor.convert(json, ApiData.class);

    if (apiData.count() > 0) {
      saveBook(apiData.bookList());

      while (apiData.next() != null) {
        json = client.getAPIData(nomeLivro);
        apiData = conversor.convert(json, ApiData.class);
        saveBook(apiData.bookList());
      }
    }
  }

  private void saveBook(List<BookDTO> books) {
    books.stream().forEach(book -> {
      Book livro = new Book(book);
      Optional<Book> isThisBookRegistered = bookRepository.findByPublicId(livro.getPublicId());
      if (!isThisBookRegistered.isPresent()) {
        try {
          List<Person> autor = livro.getAuthors().stream()
              .map(a -> {
                Optional<Person> isAuthorThere = personRepository.findByName(a.getName());
                if (isAuthorThere.isPresent()) {
                  return isAuthorThere.get();
                } else {
                  personRepository.save(a);
                  return a;
                }
              })
              .collect(Collectors.toList());

          List<Language> idioma = livro.getLanguages().stream()
              .map(lang -> {
                Optional<Language> isLanguageThere = languageRepository.findByLanguage(lang.getLanguage());

                if (isLanguageThere.isPresent()) {
                  return isLanguageThere.get();
                } else {
                  languageRepository.save(lang);
                  return lang;
                }
              }).collect(Collectors.toList());

          livro.setAuthors(autor);
          livro.setLanguages(idioma);

          bookRepository.save(livro);
        } catch (DataIntegrityViolationException e) {
          System.out.println("Livro já existe na base de dados");
        }
      }

      System.out.println(livro);
    });
  }

  public void listarAutores() {
    System.out.println("Lista de autores: ");
    personRepository.findAll().stream().forEach(System.out::println);
  }

  public void listarAutoresVivosEmAnoEspecifico(Integer ano) {
    Optional<List<Person>> authorsAlive = personRepository.findAuthorAliveInSpecifYear(ano);

    if (authorsAlive.isPresent()) {
      authorsAlive.get().forEach(System.out::println);
    } else {
      System.out.println("Não foi encontrado autores vivos no ano pesquisado.");
    }
  }

  public void listAllLanguages() {
    List<Language> languages = languageRepository.findAll();
    languages.stream()
        .forEach(language -> System.out.println("Idioma: " + language.getLanguage()));
  }

  public void listarLivrosEmDeterminadoIdioma(String language) {
    Optional<Language> isThereLanguage = languageRepository.findByLanguage(language);
    if (isThereLanguage.isPresent()) {
      isThereLanguage.get().getBooks().stream()
          .forEach(System.out::println);
    } else {
      System.out.println("Não foram encontrados livros com esse idioma");
    }
  }
}
