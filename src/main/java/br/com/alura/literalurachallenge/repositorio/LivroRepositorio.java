package br.com.alura.literalurachallenge.repositorio;

import br.com.alura.literalurachallenge.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepositorio extends JpaRepository<Livro, Long> {
}
