package br.com.alura.literalurachallenge.repositorio;

import br.com.alura.literalurachallenge.modelo.Autor;
import br.com.alura.literalurachallenge.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepositorio extends JpaRepository<Livro, Long> {
    @Query("SELECT a FROM Livro l JOIN l.autor a WHERE a.autor = :autor")
    Autor buscarAutorPeloNome(String autor);
}
