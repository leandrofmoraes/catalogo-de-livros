# LiterAlura - Catálogo de Livros
Projeto Java que consome uma API [Gutendex](https://gutendex.com) para pesquisa de livros, processa dados JSON e armazena em banco PostgreSQL com interface de console interativa.

[![Java](https://img.shields.io/badge/Java-21+-orange?logo=openjdk)](https://openjdk.org/) - [![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-green?logo=spring)](https://spring.io/) - [![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)](https://www.postgresql.org/) - [![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)](https://www.docker.com/)

## ✨ Funcionalidades
- Busca de livros por título
- Listagem de livros e autores armazenados localmente
- Filtragem por autores vivos em determinado ano
- Pesquisa por livros em idiomas específicos
- Persistencia em banco de dados com PostgreSQL

## Requisitos
- Java 21+
- PostgreSQL 15+
- Docker (opcional, para ambiente isolado)

## 📦 Instalação

1 - Clone o repositório:
```bash
git clone https://github.com/leandrofmoraes/catalogo-de-livros.git literalura && cd literalura
```

2 - Crie um banco de dados PostgreSQL ou utilize o Docker para criar um container:
```bash:
docker-compose --env-file .env up -d
```

3 - Antes de rodar a aplicação, configure as variáveis de ambiente:
```bash
POSTGRES_USER=<seu_usuário>
POSTGRES_PASSWORD=<senha>
POSTGRES_DB=<nome_do_banco>
POSTGRES_PORT=5432
```
4 - Execute a aplicação:
```bash
./mvnw spring-boot:run
```

