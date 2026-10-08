package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.AcaoSocioambiental;
import br.edu.unifacisa.impacta.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    //Buscar ações nas quais um voluntário está inscrito
    @Query("SELECT a FROM Voluntario v JOIN v.acoes a WHERE v.id = :voluntarioId")
    List<AcaoSocioambiental> findAcoesByVoluntarioId(@Param("voluntarioId") Long voluntarioId);
}
