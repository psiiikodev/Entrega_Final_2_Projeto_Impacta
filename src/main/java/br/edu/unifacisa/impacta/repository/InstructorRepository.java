package br.edu.unifacisa.impacta.repository;

import br.edu.unifacisa.impacta.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    List<Instructor> findByProfileId(Long profileId);

    boolean existsByProfileId(Long profileId);
}
