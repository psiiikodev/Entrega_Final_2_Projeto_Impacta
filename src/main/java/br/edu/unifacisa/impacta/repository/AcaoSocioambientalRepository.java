package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.AcaoSocioambiental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcaoSocioambientalRepository extends JpaRepository<AcaoSocioambiental, Long> {
}
