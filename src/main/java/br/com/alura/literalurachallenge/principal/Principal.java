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
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite novamente");;
                LEITURA.nextLine();
            }
        }
    }

    private void buscarLivro() {
        ResultsDTO dados = getDados();
        List<LivroDTO> livroDTOS = new ArrayList<>();
        livroDTOS = dados.livroDTO();

        if (livroDTOS.size() > 0){
        Livro livro = new Livro(livroDTOS.get(0));

            Autor autor = livroRepositorio.buscarAutorPeloNome(livro.getAutor().getAutor());
            if (autor != null){
                livro.setAutor(null);
//                livroRepositorio.save(livro);
                livro.setAutor(autor);
            }
        livroRepositorio.save(livro);
        System.out.println(livro);
        } else {
            System.out.println("Livro não encontrado");
        }
    }

    private ResultsDTO getDados(){
        System.out.println("Qual livro deseja pesquisar?");
        var nomeLivro = LEITURA.nextLine();
        var json = CONSUMOAPI.obterDados(ENDERECO
                + "/?search="
                + nomeLivro.replace(" ", "+").toLowerCase());
        ResultsDTO resultsDTO = CONVERTEDADOS.obterDados(json, ResultsDTO.class);
        return resultsDTO;
    }

    private void listarLivros() {
    }

    private void listarAutores() {
    }

    private void listarAutoresPorAno() {
    }

    private void listarLivrosPorIdioma() {
    }
}
