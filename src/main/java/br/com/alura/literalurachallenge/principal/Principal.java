package br.com.alura.literalurachallenge.principal;

import java.util.Scanner;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);

    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livros pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos por determinado ano
                    5 - Listar livros em um determinado idioma
                                                           
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

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
        }
    }

    private void buscarLivro() {
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
