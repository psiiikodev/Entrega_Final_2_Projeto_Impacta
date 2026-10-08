package br.edu.unifacisa.impacta.repository;
import br.edu.unifacisa.impacta.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
