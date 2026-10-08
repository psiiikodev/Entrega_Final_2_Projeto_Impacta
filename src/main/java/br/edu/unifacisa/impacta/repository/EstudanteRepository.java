package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.Estudante;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {

    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);

    //Buscar estudantes matriculados em um curso específico
    @Query("SELECT e FROM Estudante e JOIN e.cursos c WHERE c.id = :cursoId")
    List<Estudante> findByCursosId(@Param("cursoId") Long cursoId);
}

