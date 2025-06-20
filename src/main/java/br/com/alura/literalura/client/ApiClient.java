package br.com.alura.literalura.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.stereotype.Component;

@Component
public class ApiClient {

  private final HttpClient httpClient;
  private final String urlPrefix = "http://gutendex.com/books/";
  private String apiUrl;

  public ApiClient() {
    this.httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_2)
        .connectTimeout(Duration.ofSeconds(20))
        .build();
  }

  public String getAPIData(String nomeLivro) {
    apiUrl = urlPrefix + "?search=" + nomeLivro.replace(" ", "+");

    try {
      return apiRequest(apiUrl);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Error fetching data from API: " + e.getMessage(), e);
    }
  }

  private String apiRequest(String uri) throws IOException, InterruptedException {

    HttpRequest request = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create(uri))
        .header("accept", "application/json")
        .timeout(Duration.ofSeconds(15))
        .build();

    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

    return response.body();
  }
}
