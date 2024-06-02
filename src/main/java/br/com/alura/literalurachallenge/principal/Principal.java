package br.com.alura.literalurachallenge.principal;

import br.com.alura.literalurachallenge.dto.LivroDTO;
import br.com.alura.literalurachallenge.dto.ResultsDTO;
import br.com.alura.literalurachallenge.modelo.Autor;
import br.com.alura.literalurachallenge.modelo.Livro;
import br.com.alura.literalurachallenge.repositorio.AutorRepositorio;
import br.com.alura.literalurachallenge.repositorio.LivroRepositorio;
import br.com.alura.literalurachallenge.service.ConsumoAPI;
import br.com.alura.literalurachallenge.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Year;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class Principal {

    private final Scanner LEITURA = new Scanner(System.in);
    private ConsumoAPI CONSUMOAPI = new ConsumoAPI();
    private ConverteDados CONVERTEDADOS = new ConverteDados();

//    private Livro livro = new Livro();
//    private Autor autor = new Autor();

    public LivroRepositorio livroRepositorio;
    public AutorRepositorio autorRepositorio;

    private final String ENDERECO = "http://gutendex.com/books";


    public Principal(LivroRepositorio livroRepositorio, AutorRepositorio autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livros pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos por determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Listar autores por nome
                    \n
                    0 - Sair
                    """;

            try {
                System.out.println(menu);
                opcao = LEITURA.nextInt();
                LEITURA.nextLine();

                switch (opcao) {
                    case 0:
                        System.out.println("Saindo...");
                        System.exit(0);
                        break;
                    case 1:
                        buscarLivro();
                        break;
                    case 2:
                        listarLivros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresPorAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 6:
                        listarAutorPorNome();
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite novamente");
                ;
                LEITURA.nextLine();
            }
        }
    }

    private void buscarLivro() {
        ResultsDTO dados = getDados();
        List<LivroDTO> livroDTOS = new ArrayList<>();
        livroDTOS = dados.livroDTO();

        if (livroDTOS.size() > 0) {
            Livro livro = new Livro(livroDTOS.get(0));

            Autor autor = autorRepositorio.buscarAutorPeloNome(livro.getAutor().getAutor());
            if (autor != null) {
                livro.setAutor(null);
                livro.setAutor(autor);
            }
            livroRepositorio.save(livro);
            System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado");
        }
    }

    private ResultsDTO getDados() {
        System.out.println("Qual livro deseja pesquisar?");
        var nomeLivro = LEITURA.nextLine();
        var json = CONSUMOAPI.obterDados(ENDERECO
                + "/?search="
                + nomeLivro.replace(" ", "+").toLowerCase());
        ResultsDTO resultsDTO = CONVERTEDADOS.obterDados(json, ResultsDTO.class);
        return resultsDTO;
    }

    private void listarLivros() {
        List<Livro> livros = livroRepositorio.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores = autorRepositorio.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresPorAno() {
        try {
            List<Autor> autores = autorRepositorio.findAll();
            autores.forEach(System.out::println);

            System.out.println("Digite o ano: ");
            Integer ano = LEITURA.nextInt();
            LEITURA.nextLine();

            List<Autor> listaAutoresAno = autorRepositorio.procuraAutoresAno(ano);
            listaAutoresAno.forEach(System.out::println);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida, digite um ano válido.");;
            LEITURA.nextLine();
        }
    }


    private void listarLivrosPorIdioma() {
        System.out.println("""
            Escolha um idioma para filtrar:
            en - Inglês
            pt - Português
            """);
        var idiomaEscolha = LEITURA.nextLine();

        List<Livro> livrosIdioma = livroRepositorio.findByIdioma(idiomaEscolha);
        livrosIdioma.forEach(System.out::println);
    }

    private void listarAutorPorNome() {
        System.out.println("Deseja pesquisar por qual nome: ");
        var nomeAutor = LEITURA.nextLine();

        List<Autor> autores = autorRepositorio.procuraPorNomeAutor(nomeAutor);
        autores.forEach(System.out::println);
    }
}