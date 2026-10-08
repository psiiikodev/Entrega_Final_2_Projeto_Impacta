package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.AcaoSocioambiental;
import br.edu.unifacisa.impacta.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface AcaoSocioambientalRepository extends JpaRepository<AcaoSocioambiental, Long> {

    // Buscar voluntários inscritos em uma ação
    @Query("SELECT v FROM AcaoSocioambiental a JOIN a.voluntarios v WHERE a.id = :acaoId")
    List<Voluntario> findVoluntariosByAcaoId(@Param("acaoId") Long acaoId);
}