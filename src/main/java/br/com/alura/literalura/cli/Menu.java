package br.com.alura.literalura.cli;

import java.time.Year;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import br.com.alura.literalura.service.CLIService;

@Component
public class Menu {

  private Scanner leitura = new Scanner(System.in);

  @Autowired
  private CLIService service;

  @Autowired
  private ApplicationContext context;

  public void iniciarModoInterativo() {
    int opcao = -1;

    while (opcao != 0) {
      limparTela();
      exibirMenu();
      try {
        opcao = leitura.nextInt();
        leitura.nextLine();
        menuController(opcao);

      } catch (InputMismatchException e) {
        System.out.println("Digite um número entre 0 e 5");
        leitura.nextLine();
      }
    }
  }

  public void menuController(int opcao) {
    switch (opcao) {
      case 1:
        service.buscarLivroPorTitulo(obterTituloLivro());
        leitura.nextLine();
        break;
      case 2:
        service.listarLivros();
        leitura.nextLine();
        break;
      case 3:
        service.listarLivrosEmDeterminadoIdioma(obterIdiomaLivro());
        leitura.nextLine();
        break;
      case 4:
        service.listarAutores();
        leitura.nextLine();
        break;
      case 5:
        service.listarAutoresVivosEmAnoEspecifico(obterAnoAutor());
        leitura.nextLine();
        break;
      case 0:
        System.out.println("Saindo...");
        int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);
        break;
      default:
        System.out.println("Opção inválida");
    }
  }

  private void exibirMenu() {
    System.out.println("""
        =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        1 - Buscar livro pelo título
        2 - Listar livros no catálogo
        3 - Listar livros em um determinado idioma
        4 - Listar autores registrados
        5 - Listar autores vivos em um determinado ano
        0 - Sair
        =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
        """);
  }

  private String obterTituloLivro() {
    limparTela();
    System.out.println("Digite o nome do livro que deseja buscar: ");
    String livro = leitura.nextLine();
    return livro;
  }

  private String obterIdiomaLivro() {
    limparTela();
    service.listAllLanguages();
    System.out.println("Digite o idioma do livro que deseja buscar: ");
    String idioma = leitura.nextLine();
    return idioma;
  }

  private Integer obterAnoAutor() {
    while (true) {
      try {
        limparTela();
        System.out.println("Digite o ano para buscar autores vivos (0 para cancelar): ");
        System.out.print("Ano: ");

        int input = leitura.nextInt();
        leitura.nextLine();

        if (input == 0) {
          return null;
        } else if (input < 0) {
          System.out.println("O ano não pode ser negativo");
        } else if (input > Year.now().getValue()) {
          System.out.println("Aviso: Ano futuro detectado");
          return input;
        } else {
          return input;
        }
      } catch (InputMismatchException e) {
        System.out.println("Erro: Digite um número válido");
        leitura.nextLine();
      }
      System.out.println("\nPressione Enter para continuar...");
      leitura.nextLine();
    }
  }

  public void limparTela() {
    try {
      String os = System.getProperty("os.name").toLowerCase();
      ProcessBuilder pb;
      if (os.contains("windows")) {
        pb = new ProcessBuilder("cmd", "/c", "cls");
      } else {
        pb = new ProcessBuilder("clear");
      }
      pb.inheritIO().start().waitFor();
    } catch (Exception e) {
      System.out.println("\n".repeat(50));
    }
  }
}
