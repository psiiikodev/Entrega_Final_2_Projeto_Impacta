package br.edu.unifacisa.impacta.repository;
import br.edu.unifacisa.impacta.model.Estudante;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudanteRepository extends JpaRepository<Estudante, Long> {
    boolean existsByEmail (String email);   //n deixa cadastrar 2 estudantes com o msm email
    boolean existsByEmailAndIdNot (String email, Long id);
}

