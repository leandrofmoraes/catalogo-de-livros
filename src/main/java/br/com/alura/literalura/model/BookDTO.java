package br.com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDTO(
    Long id,
    String title,
    List<String> subjects,
    List<String> languages,
    List<PersonDTO> authors,
    @JsonAlias("download_count") Integer downloadCount) {
}
