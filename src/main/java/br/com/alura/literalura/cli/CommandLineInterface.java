package br.com.alura.literalura.cli;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Profile("!test")
@Component
public class CommandLineInterface implements CommandLineRunner {

  @Autowired
  private Menu menu;

  @Override
  public void run(String... args) {
    menu.iniciarModoInterativo();
  }

}
