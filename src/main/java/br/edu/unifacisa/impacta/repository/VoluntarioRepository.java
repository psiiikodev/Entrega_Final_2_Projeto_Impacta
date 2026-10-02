package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
}
