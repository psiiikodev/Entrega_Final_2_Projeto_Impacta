package br.edu.unifacisa.impacta.repository;
import br.edu.unifacisa.impacta.model.Estudante;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    List<Estudante> findByCursos_Id(Long cursoId);
    boolean existsByEmail (String email);   //n deixa cadastrar 2 estudantes com o msm email
    boolean existsByEmailAndIdNot (String email, Long id);
}
