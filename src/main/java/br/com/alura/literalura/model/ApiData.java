package br.com.alura.literalura.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ApiData(
    Integer count,
    String next,
    String previous,
    @JsonAlias("results") List<BookDTO> bookList) {
}
