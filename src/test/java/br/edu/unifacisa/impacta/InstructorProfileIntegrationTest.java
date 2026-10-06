package br.edu.unifacisa.impacta;

import br.edu.unifacisa.impacta.model.Curso;
import br.edu.unifacisa.impacta.model.Instructor;
import br.edu.unifacisa.impacta.model.Profile;
import br.edu.unifacisa.impacta.service.InstructorService;
import br.edu.unifacisa.impacta.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class InstructorProfileIntegrationTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private InstructorService instructorService;

    @Test
    void deveCriarPerfilEInstructorComRelacionamentoUmParaUm() {
        Profile profile = profileService.criar(new Profile("Maria", "maria@email.com", "https://linkedin.com/in/maria"));
        Instructor instructor = instructorService.criar(profile.getId(), "Tecnologia", List.of("Java", "Spring"));

        assertThat(profileService.buscarPorId(profile.getId())).isPresent();
        assertThat(instructor.getProfile()).isNotNull();
        assertThat(instructor.getProfile().getId()).isEqualTo(profile.getId());
        assertThat(instructor.getCursos()).extracting(Curso::getNome).containsExactly("Java", "Spring");
    }

    @Test
    void deveBuscarInstrutoresAssociadosAoPerfil() {
        Profile profile = profileService.criar(new Profile("João", "joao@email.com", "https://linkedin.com/in/joao"));
        instructorService.criar(profile.getId(), "Design", List.of("UX"));

        List<Instructor> instructors = instructorService.buscarPorProfile(profile.getId());

        assertThat(instructors).hasSize(1);
        assertThat(instructors.getFirst().getProfile().getId()).isEqualTo(profile.getId());
    }

    @Test
    void deveRecusarCriacaoSemPerfilOuComPerfilInexistente() {
        assertThatThrownBy(() -> instructorService.criar(null, "Tecnologia", List.of("Java")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Perfil");

        assertThatThrownBy(() -> instructorService.criar(999L, "Tecnologia", List.of("Java")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Perfil não encontrado");
    }
}
