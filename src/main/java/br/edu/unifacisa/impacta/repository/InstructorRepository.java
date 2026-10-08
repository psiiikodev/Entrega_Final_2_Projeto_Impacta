package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findByProfileId(Long profileId);
    boolean existsByProfileId(Long profileId);

    //Buscar cursos de um instrutor com a quantidade de alunos matriculados
    @Query("""
    SELECT new map(c.id as cursoId, c.nome as cursoNome, COUNT(e) as quantidadeAlunos)
    FROM Estudante e
    JOIN e.cursos c
    WHERE c.instructor.id = :instructorId
    GROUP BY c.id, c.nome
    ORDER BY c.nome ASC
    """)
    List<Map<String, Object>> findCursosComQuantidadeAlunos(@Param("instructorId") Long instructorId);
}
